package org.zttc.itat.service;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.activation.DataSource;
import javax.activation.URLDataSource;
import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.zttc.itat.dao.IAttachmentDao;
import org.zttc.itat.dao.IMessageDao;
import org.zttc.itat.dao.IUserDao;
import org.zttc.itat.dto.AttachDto;
import org.zttc.itat.model.Attachment;
import org.zttc.itat.model.Message;
import org.zttc.itat.model.Pager;
import org.zttc.itat.model.SystemContext;
import org.zttc.itat.model.User;
import org.zttc.itat.model.UserEmail;
import org.zttc.itat.model.UserMessage;
import org.zttc.itat.util.DocumentUtil;


@Service("messageService")
public class MessageService implements IMessageService {
	private IMessageDao messageDao;
	private IUserDao userDao;
	private IAttachmentDao attachmentDao;
	private JavaMailSender mailSender;
	private TaskExecutor taskExecutor;
	
	
	
	public TaskExecutor getTaskExecutor() {
		return taskExecutor;
	}
	
	@Resource
	public void setTaskExecutor(TaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}
	public JavaMailSender getMailSender() {
		return mailSender;
	}
	@Resource
	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}
	public IAttachmentDao getAttachmentDao() {
		return attachmentDao;
	}
	@Resource
	public void setAttachmentDao(IAttachmentDao attachmentDao) {
		this.attachmentDao = attachmentDao;
	}

	public IUserDao getUserDao() {
		return userDao;
	}
	
	@Resource
	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}
	public IMessageDao getMessageDao() {
		return messageDao;
	}
	@Resource
	public void setMessageDao(IMessageDao messageDao) {
		this.messageDao = messageDao;
	}

	@Override
	public void add(Message msg, Integer[] userIds,AttachDto ad,int isEmail) throws IOException {
		//2、设置该信件的发件人为当前的登录用户
		msg.setUser(SystemContext.getLoginUser());
		msg.setCreateDate(new Date());
		messageDao.add(msg);
		//3、添加一组收件人
		UserMessage um = null;
		//根据用户id获取一组用户信息
		List<User> users = userDao.listByIds(userIds);
		//为这组用户添加相应的信息
		for(User u:users) {
			um = new UserMessage();
			um.setIsRead(0);
			um.setMessage(msg);
			um.setUser(u);
			messageDao.addObj(um);
		}
		String[] newNames = DocumentUtil.addAttach(ad, attachmentDao, msg, null);
		if(isEmail>0) {
			UserEmail ue = userDao.loadUserEmail(SystemContext.getLoginUser().getId());
			//将发送邮件放置到一个线程中，这样就是实现了异步处理
			taskExecutor.execute(new SendMailThread(ue,msg, userIds, ad,newNames,SystemContext.getRealPath()));
		}
	}
	
	private class SendMailThread implements Runnable {
		private Message msg;
		private Integer[] userIds;
		private AttachDto ad;
		private String[] newNames;
		private String realPath;
		private UserEmail ue;
		
		
		public SendMailThread(UserEmail ue,Message msg, Integer[] userIds, AttachDto ad,String[] newNames,String realPath) {
			super();
			this.msg = msg;
			this.userIds = userIds;
			this.ad = ad;
			this.newNames = newNames;
			this.realPath = realPath;
			this.ue = ue;
		}


		public void run() {
			sendMail(ue,msg,userIds,ad,newNames,realPath);
		}
	}
	
	private List<String> listContentImgUrl(String content) {
		Pattern p = Pattern.compile("<img.*?\\s+src=['\"](.*?)['\"].*?>");
		Matcher m = p.matcher(content);
		List<String> srcs = new ArrayList<String>();
		while(m.find()) {
			srcs.add(m.group(1));
		}
		return srcs;
	}
	
	private void sendMail(UserEmail ue,Message msg, Integer[] userIds, AttachDto ad,String[]newNames,String realPath) {
		try {
			System.out.println("----------------开始发送邮件--------------");
			//创建一个org.springframework.mail.javamail.JavaMailSenderImpl
			//完成了JavaMailSender的设置，就可以为不同的用户来设置不同的值
			JavaMailSenderImpl jms = (JavaMailSenderImpl)mailSender;
			jms.setHost(ue.getHost());
			jms.setProtocol(ue.getProtocol());
			jms.setUsername(ue.getUsername());
			jms.setPassword(ue.getPassword());
			
			MimeMessage email = jms.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(email, true,"utf-8");
			helper.setFrom(ue.getUser().getEmail());
			helper.setSubject(msg.getTitle());
			//获取要发送邮件的对象
			List<String> mailTos = userDao.listAllSendEmail(userIds);
			for(String m:mailTos) {
				helper.addTo(m);
			}
			String uploadPath = ad.getUploadPath();
			if(ad.isHasAttach()) {
				File[] atts = ad.getAtts();
				String[] fns = ad.getAttsFilename();
				for(int i=0;i<atts.length;i++) {
					String fn = fns[i];
					helper.addAttachment(MimeUtility.encodeText(fn),new FileSystemResource(uploadPath+"/"+newNames[i]));
				}
			}
			//格式化内容
			String content = msg.getContent();
			//最建议的方式是先把内容设置好，之后再来一次性添加附件
			//先定义一个map来保存cid和图片地址的关系
			Map<String,String> imgMaps = new HashMap<String,String>();
			//必须先设置好内容的值，之后再来设置相应的嵌入附件，否则最后一个附件不会替换，所以就不会显示
			List<String> imgs = listContentImgUrl(content);
			int i = 0;
			//完成内容的替换
			for(String is:imgs) {
				imgMaps.put("x"+i, is);
				content = content.replace(is, "cid:"+("x"+i));
				i++;
			}
			helper.setText(content, true);
			//添加嵌入式图片
			Set<String> keys = imgMaps.keySet();
			//此时key是id，value是图片的地址
			//如果是本地的文件，就是不是以http开头，应该通过FileSystemResource来传递，所以首先得获取Webcontent中的绝对路径
			for(String key:keys) {
				String url = imgMaps.get(key);
				if(url.startsWith("http://")) {
					helper.addInline(key,new URLDataSource(new URL(url)));
				} else {
					//如果是本地文件就通过FileSystemResource来完成传递
					String path = realPath+"/"+url;
					helper.addInline(key, new FileSystemResource(path));
				}
				
			}
			jms.send(email);
			System.out.println("--------------邮件发送成功！！------------");
		} catch (MailException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void deleteReceive(int msgId) {
		User user = SystemContext.getLoginUser();
		String hql = "delete UserMessage um where um.message.id=? and um.user.id=?";
		messageDao.executeByHql(hql, new Object[]{msgId,user.getId()});
	}

	@Override
	public void deleteSend(int msgId) {
		//1、删除所有的已经被接收的id
		String hql = "delete UserMessage um where um.message.id=?";
		messageDao.executeByHql(hql,msgId);
		//删除附件
		List<Attachment> atts = this.listAttachmentByMsg(msgId);
		hql = "delete Attachment att where att.message.id=?";
		messageDao.executeByHql(hql, msgId);
		//2、删除msg对象
		messageDao.delete(msgId);
		String path = SystemContext.getRealPath()+"/upload";
		for(Attachment att:atts) {
			File f = new File(path+"/"+att.getNewName());
			f.delete();
		}
	}

	@Override
	public Message updateRead(int id,int isRead) {
		if(isRead==0) {
			User user = SystemContext.getLoginUser();
			UserMessage um = messageDao.loadUserMessage(user.getId(), id);
			if(um.getIsRead()==0) {
				//如果没有读过，需要更新为已读
				um.setIsRead(1);
				messageDao.updateObj(um);
			}
		}
		return messageDao.load(id);
	}

	@Override
	public Pager<Message> findSendMessage(String con) {
		User user = SystemContext.getLoginUser();
		String hql = "select new Message(msg.id,msg.title,msg.content,msg.createDate) from Message msg where msg.user.id=?";
		if(con!=null&&!"".equals(con.trim())) {
			hql+=" and (msg.title like ? or msg.content like ?) order by msg.createDate desc";
			return messageDao.find(hql,new Object[]{user.getId(),"%"+con+"%","%"+con+"%"});
		}
		hql+=" order by msg.createDate desc";
		return messageDao.find(hql, user.getId());
	}

	@Override
	public Pager<Message> findReceiveMessage(String con, int isRead) {
		User user = SystemContext.getLoginUser();
		String hql = "select um.message from UserMessage um left join fetch um.message.user mu" +
				" left join fetch mu.department where um.isRead=? and um.user.id=?";
		if(con!=null&&!"".equals(con.trim())) {
			hql+=" and (um.message.title like ? or um.message.content like ?) order by um.message.createDate desc";
			return messageDao.find(hql,new Object[]{isRead,user.getId(),"%"+con+"%","%"+con+"%"});
		}
		hql+=" order by um.message.createDate desc";
		return messageDao.find(hql,new Object[]{isRead,user.getId()});
	}
	@Override
	public List<Attachment> listAttachmentByMsg(int msgId) {
		String hql = "from Attachment att where att.message.id=?";
		return attachmentDao.list(hql,msgId);
	}

}

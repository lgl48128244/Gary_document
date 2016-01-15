package org.zttc.itat.action;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.zttc.itat.dto.AttachDto;
import org.zttc.itat.model.Message;
import org.zttc.itat.model.User;
import org.zttc.itat.service.IMessageService;
import org.zttc.itat.service.IUserService;
import org.zttc.itat.util.ActionUtil;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

@SuppressWarnings("serial")
@Controller("messageAction")
@Scope("prototype")
public class MessageAction extends ActionSupport implements ModelDriven<Message> {
	private IMessageService messageService;
	private IUserService userService;
	private Message msg;
	private int isRead;
	private String con;
	private Integer[] sus;
	private File[] atts;
	private String[] attsContentType;
	private String[] attsFileName;
	private int isEmail;

	public int getIsEmail() {
		return isEmail;
	}

	public void setIsEmail(int isEmail) {
		this.isEmail = isEmail;
	}

	public File[] getAtts() {
		return atts;
	}

	public void setAtts(File[] atts) {
		this.atts = atts;
	}

	public String[] getAttsContentType() {
		return attsContentType;
	}

	public void setAttsContentType(String[] attsContentType) {
		this.attsContentType = attsContentType;
	}

	public String[] getAttsFileName() {
		return attsFileName;
	}

	public void setAttsFileName(String[] attsFileName) {
		this.attsFileName = attsFileName;
	}

	public Integer[] getSus() {
		return sus;
	}

	public void setSus(Integer[] sus) {
		this.sus = sus;
	}

	public IUserService getUserService() {
		return userService;
	}

	@Resource
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public String getCon() {
		return con;
	}

	public void setCon(String con) {
		this.con = con;
	}

	public int getIsRead() {
		return isRead;
	}

	public void setIsRead(int isRead) {
		this.isRead = isRead;
	}

	public IMessageService getMessageService() {
		return messageService;
	}

	@Resource
	public void setMessageService(IMessageService messageService) {
		this.messageService = messageService;
	}

	public Message getMsg() {
		return msg;
	}

	public void setMsg(Message msg) {
		this.msg = msg;
	}

	@Override
	public Message getModel() {
		if (msg == null)
			msg = new Message();
		return msg;
	}

	public String addInput() {
		User u = (User) ActionContext.getContext().getSession().get("loginUser");
		ActionContext.getContext().put("us", userService.listAllSendUser(u.getId()));
		return SUCCESS;
	}

	public String add() throws IOException {
		//		System.out.println(atts);
		if (atts == null || atts.length <= 0) {
			messageService.add(msg, sus, new AttachDto(false), isEmail);
		} else {
			//String uploadPath = ServletActionContext.getServletContext().getRealPath("upload");
			String uploadPath = "D:\\teach_source\\class2\\j2EE\\document_project\\document01\\WebContent\\upload";
			messageService.add(msg, sus, new AttachDto(atts, attsContentType, attsFileName, uploadPath), isEmail);
		}

		ActionUtil.setUrl("/message_listSend.action");
		return ActionUtil.REDIRECT;
	}

	public void validateAdd() {
		if (sus == null || sus.length <= 0) {
			this.addFieldError("sus", "必须选择要发送的用户");
		}
		if (msg.getTitle() == null || "".equals(msg.getTitle())) {
			this.addFieldError("title", "私人信件的标题不能为空");
		}
		if (this.hasFieldErrors()) {
			addInput();
		}
	}

	public String show() throws IllegalAccessException, InvocationTargetException {
		Message tm = messageService.updateRead(msg.getId(), isRead);
		BeanUtils.copyProperties(msg, tm);
		ActionContext.getContext().put("atts", messageService.listAttachmentByMsg(msg.getId()));
		return SUCCESS;
	}

	public String listSend() {
		ActionContext.getContext().put("pages", messageService.findSendMessage(con));
		return SUCCESS;
	}

	public String listReceive() {
		ActionContext.getContext().put("pages", messageService.findReceiveMessage(con, isRead));
		return SUCCESS;
	}

	public String deleteSend() {
		messageService.deleteSend(msg.getId());
		ActionUtil.setUrl("/message_listSend.action");
		return ActionUtil.REDIRECT;
	}

	public String deleteReceive() {
		messageService.deleteReceive(msg.getId());
		ActionUtil.setUrl("/message_listReceive.action");
		return ActionUtil.REDIRECT;
	}

}

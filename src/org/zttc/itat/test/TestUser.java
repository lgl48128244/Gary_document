package org.zttc.itat.test;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;

import org.apache.commons.io.FilenameUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zttc.itat.dto.AttachDto;
import org.zttc.itat.model.Message;
import org.zttc.itat.model.Pager;
import org.zttc.itat.model.SystemContext;
import org.zttc.itat.model.User;
import org.zttc.itat.service.IMessageService;
import org.zttc.itat.service.IUserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/beans.xml")
public class TestUser {
	Random ran = new Random();
	
	@Resource(name="userService")
	private IUserService userService;
	
	@Resource(name="messageService")
	private IMessageService messageService;

	@Test
	public void initUser() {
		int[] depIds = {1,2,3,4,5,9,10,11};
		for(int i=0;i<100;i++) {
			User u = new User();
			u.setEmail("user"+i+"@zttc.net");
			u.setNickname(getName());
			u.setUsername("user"+i);
			u.setPassword("123");
			u.setType(0);
			userService.add(u, depIds[ran.nextInt(depIds.length)]);
		}
	}
	
	private String getName() {
		String[] name1 = new String[]{"孔","张","叶","李","叶入","孔令",
				"张立","陈","刘","牛","夏侯","令","令狐","赵","母","穆","倪",
				"张毅","称","程","王","王志","刘金","冬","吴","马","沈"};
		
		String[] name2 = new String[]{"凡","课","颖","页","源","都",
				"浩","皓","西","东","北","南","冲","昊","力","量","妮",
				"敏","捷","杰","坚","名","生","华","鸣","蓝","春","虎","刚","诚"};
		
		String[] name3 = new String[]{"吞","明","敦","刀","备","伟",
				"唯","楚","勇","诠","佺","河","正","震","点","贝","侠",
				"伟","大","凡","琴","青","林","星","集","财"};
		
		boolean two = ran.nextInt(50)>=45?false:true;
		if(two) {
			String n1 = name1[ran.nextInt(name1.length)];
			String n2;
			int n = ran.nextInt(10);
			if(n>5) {
				n2 = name2[ran.nextInt(name2.length)];
			} else {
				n2 = name3[ran.nextInt(name3.length)];
			}
			return n1+n2;
		} else {
			String n1 = name1[ran.nextInt(name1.length)];
			String n2 = name2[ran.nextInt(name2.length)];
			String n3 = name3[ran.nextInt(name3.length)];
			return n1+n2+n3;
		}
	}
	
	@Test
	public void testAddMessage() {
		Message msg = new Message();
		msg.setTitle("唐僧来了");
		msg.setContent("唐僧来了，杀了！");
		User user = new User();
		user.setId(2);
		SystemContext.setLoginUser(user);
		try {
			messageService.add(msg,new Integer[]{11,3,42,25,6,7,8,9,10},new AttachDto(false),0);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testDeleReceiveMsg() {
		User user = new User();
		user.setId(4);
		SystemContext.setLoginUser(user);
		messageService.deleteReceive(3);
	}
	
	@Test
	public void testDeleSendMsg() {
		messageService.deleteSend(3);
	}
	
	@Test
	public void testFindSend() {
		User user = new User();
		user.setId(2);
		SystemContext.setLoginUser(user);
		SystemContext.setPageSize(15);
		SystemContext.setPageOffset(0);
		Pager<Message> pages = messageService.findSendMessage("唐僧");
		for(Message m:pages.getDatas()) {
			System.out.println(m.getContent());
		}
	}
	
	@Test
	public void testFindRevie() {
		User user = new User();
		user.setId(7);
		SystemContext.setLoginUser(user);
		SystemContext.setPageSize(15);
		SystemContext.setPageOffset(0);
		Pager<Message> pages = messageService.findReceiveMessage(null, 0);
		for(Message m:pages.getDatas()) {
			System.out.println(m.getContent());
		}
	}
	
	@Test
	public void testListUserScope() {
		List<User> us = userService.listAllSendUser(103);
		for(User u:us) {
			System.out.println(u.getId()+","+u.getNickname()+","+u.getUsername());
		}
	}
	
	@Test
	public void testListMail() {
		List<String> mail = userService.listAllSendEmail(new Integer[]{1,2,3,4,5,6,7});
		System.out.println(mail);
	}
	
	@Test
	public void testFile() {
		File f = new File("d:/user.log");
		System.out.println(f.length());
		System.out.println(getNewName("aaa.txt"));
	}
	
	private String getNewName(String name) {
		String n = new Long(new Date().getTime()).toString();
		n = n+"."+FilenameUtils.getExtension(name);
		return n;
	}
}

package org.zttc.itat.test;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.activation.URLDataSource;
import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/beans.xml")
public class TestMail {
	@Resource(name="mailSender")
	JavaMailSender mailSender;
	@Test
	public void test01() {
		try {
			//创建mimeMessage
			MimeMessage msg = mailSender.createMimeMessage();
			//通过MimeMessageHelper来完成对邮件信息的创建
			MimeMessageHelper helper = new MimeMessageHelper(msg,true, "utf-8");
			helper.setFrom("kh_itat@sina.com");
			helper.setTo("64831031@qq.com");
			helper.setSubject("通过Spring发的哦!");
			//设置邮件的正文
			helper.setText("<div style='color:red;font-size:15px;'>通过Spring来发送邮件</div>" +
					"<img src='cid:sss'/>kajsdkjsadf<img src='cid:ddd'/>ksjdhfksjdhf<img src='cid:aaa'/>", true);
			//添加附件
			helper.addAttachment(MimeUtility.encodeText("日志"), new FileSystemResource("d:/user.log"));
			//添加邮件内容中信息
			FileSystemResource fsr = new FileSystemResource("d:/02.jpg");
			helper.addInline("sss", fsr);
			helper.addInline("ddd", new URLDataSource(new URL("http://img3.douban.com/lpic/s24494223.jpg")));
			helper.addInline("aaa", new URLDataSource(new URL("http://img3.douban.com/lpic/s11159090.jpg")));
			mailSender.send(msg);
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
	
	
	@Test
	public void testReg() {
		String html = "sadlkfjlaskdjflas<img src='xxxx.jpg'/>askdhfkajsdhfkjsahfd" +
				"<img src='http://ssss.ssjpg'/>asdkfjaksjdhfkajsfd<img src=\"sdkjfskjhfd.hpg\"/>";
		Pattern p = Pattern.compile("<img.*?\\s+src=['\"](.*?)['\"].*?>");
		Matcher m = p.matcher(html);
		while(m.find()) {
			System.out.println(m.group(1));
		}
	}
	
}

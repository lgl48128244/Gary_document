package org.zttc.itat.test;


import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zttc.itat.dto.AttachDto;
import org.zttc.itat.model.Document;
import org.zttc.itat.model.SystemContext;
import org.zttc.itat.model.User;
import org.zttc.itat.service.IDocumentService;
import org.zttc.itat.service.IUserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/beans.xml")
public class TestDocument {
	
	@Resource(name="userService")
	private IUserService userService;
	
	
	@Resource(name="documentService")
	private IDocumentService documentService;
	
	@Before
	public void init() {
		User u = userService.load(2);
		SystemContext.setLoginUser(u);
	}
	
	@Test
	public void testAdd() {
		try {
			Integer[] depIds = new Integer[]{1,2,3,4,5};
			Document doc = new Document();
			doc.setContent("猪八戒");
			doc.setTitle("一头猪八戒");
			documentService.add(doc, depIds, new AttachDto(false));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testRead() {
		User u = userService.load(4);
		SystemContext.setLoginUser(u);
		Document doc = documentService.updateRead(1, 0);
		System.out.println(doc.getContent());
	}
	
	@Test
	public void testGetSendDocument() {
		SystemContext.setPageSize(15);
		SystemContext.setPageOffset(0);
		List<Document> docs = documentService.findSendDocument(2).getDatas();
		for(Document d:docs) {
			System.out.println(d.getContent());
		}
	}
	
	
	@Test
	public void testGetReadDocument() {
		User u = userService.load(4);
		SystemContext.setLoginUser(u);
		SystemContext.setPageSize(15);
		SystemContext.setPageOffset(0);
		List<Document> docs = documentService.findReadDocument("小", 2).getDatas();
		for(Document d:docs) {
			System.out.println(d.getContent());
		}
	}
	
	@Test
	public void testGetNotReadDocument() {
		User u = userService.load(4);
		SystemContext.setLoginUser(u);
		SystemContext.setPageSize(15);
		SystemContext.setPageOffset(0);
		List<Document> docs = documentService.findNotReadDocument("猪", 22).getDatas();
		for(Document d:docs) {
			System.out.println(d.getContent());
		}
	}
	
	@Test
	public void testDelete() {
		documentService.delete(1);
	}

}

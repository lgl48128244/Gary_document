package org.zttc.itat.service;

import java.io.IOException;
import java.util.List;

import org.zttc.itat.dto.AttachDto;
import org.zttc.itat.model.Attachment;
import org.zttc.itat.model.Department;
import org.zttc.itat.model.Document;
import org.zttc.itat.model.Pager;

public interface IDocumentService {
	public void add(Document doc,Integer[] depIds,AttachDto ad)throws IOException;
	public void delete(int id);
	
	public Document updateRead(int id,Integer isRead);
	/**
	 * 获取某个用户所有发送的公文
	 * @param userId
	 * @return
	 */
	public Pager<Document> findSendDocument(int userId);
	/**
	 * 获取某个部门中的已读公文
	 * @param con 查询条件
	 * @param depId 某个部门，如果为0或者null表示所有部门
	 * @return
	 */
	public Pager<Document> findReadDocument(String con,Integer depId);
	/**
	 * 获取某个部门中的未读公文
	 * @param con
	 * @param depId
	 * @return
	 */
	public Pager<Document> findNotReadDocument(String con,Integer depId);
	
	/**
	 * 获取某个公文的附件信息
	 * @param msgId
	 * @return
	 */
	public List<Attachment> listAttachmentByDocument(int docId);
	/**
	 * 加载公文
	 * @param id
	 * @return
	 */
	public Document load(int id);
	/**
	 * 获取某个部门所发送的公文
	 * @param id
	 * @return
	 */
	public List<Department> listDocSendDep(int id);
	
}

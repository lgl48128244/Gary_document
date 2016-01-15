package org.zttc.itat.service;

import java.io.IOException;
import java.util.List;

import org.zttc.itat.dto.AttachDto;
import org.zttc.itat.model.Attachment;
import org.zttc.itat.model.Message;
import org.zttc.itat.model.Pager;

public interface IMessageService {
	/**
	 * 添加私人信件
	 * @param msg 私人信件内容
	 * @param userIds 为那些用户添加私人信件
	 */
	public void add(Message msg,Integer[] userIds,AttachDto at,int isEmail) throws IOException;
	/**
	 * 删除说接收的私人信件，直接删除关联关系
	 * @param msgId
	 */
	public void deleteReceive(int msgId);
	/**
	 * 删除发送的私人信件，首先先删除有接收关系的信件，然后在删除该对象
	 * @param msgId
	 */
	public void deleteSend(int msgId);
	/**
	 * 加载私人信息
	 * @param id
	 * @param isRead 如果isRead==0表示是在已读信件中的，此时需要检查，
	 * 	isRead==1表示在已读信件中使用，此时就不用在检查
	 * @return
	 */
	public Message updateRead(int id,int isRead);
	/**
	 * 通过标题或者内容获取发出的私人信件
	 * @param con 要获取的标题或者内容
	 * @return
	 */
	public Pager<Message> findSendMessage(String con);
	/**
	 * 获取接收的私人信件
	 * @param con 查询条件
	 * @param isRead 是否已读 0 表示未读1表示已读
	 * @return
	 */
	public Pager<Message> findReceiveMessage(String con,int isRead);
	/**
	 * 获取某个私人信件的附件信息
	 * @param msgId
	 * @return
	 */
	public List<Attachment> listAttachmentByMsg(int msgId);
}

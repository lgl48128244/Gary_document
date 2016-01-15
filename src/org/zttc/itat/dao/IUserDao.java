package org.zttc.itat.dao;

import java.util.List;

import org.zttc.itat.model.User;
import org.zttc.itat.model.UserEmail;

public interface IUserDao extends IBaseDao<User>{
	/**
	 * 通过一组用户id来获取一组用户对象
	 * @param userIds
	 * @return
	 */
	public List<User> listByIds(Integer[] userIds);
	/**
	 * 获取某个用户可以发送信息的用户
	 * @param userId
	 * @return
	 */
	public List<User> listAllCanSendUser(int userId);
	
	/**
	 * 通过用户的id获取相应的邮件信息
	 * @param id
	 * @return
	 */
	public UserEmail loadUserEmail(int id);
	/**
	 * 获取一组用户的邮箱信息
	 * @param userIds
	 * @return
	 */
	public List<String> listAllSendEmail(Integer[] userIds);
	
}

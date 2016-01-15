package org.zttc.itat.service;

import java.util.List;

import org.zttc.itat.model.Pager;
import org.zttc.itat.model.User;
import org.zttc.itat.model.UserEmail;

public interface IUserService {
	public void add(User user,int depId);
	/**
	 * 为用户添加相应的邮件信息
	 * @param ue
	 * @param userId
	 */
	public void addUserEmail(UserEmail ue,int userId);
	
	public void updateUserEmail(UserEmail ue);
	
	public void delete(int id);
	
	public void update(User user,int depId);
	
	public void update(User user);
	
	public User load(int id);
	/**
	 * 通过用户的id获取相应的邮件信息
	 * @param id
	 * @return
	 */
	public UserEmail loadUserEmail(int id);
	/**
	 * 根据部门获取用户，如果DepID为了空表示获取所有用户
	 * @param depId
	 * @return
	 */
	public Pager<User> findUserByDep(Integer depId);
	
	public User login(String username,String password);
	/**
	 * 根据用户id获取能够发送私人信息的所有用户
	 * @param userId
	 * @return
	 */
	public List<User> listAllSendUser(int userId);
	
	public List<String> listAllSendEmail(Integer[] userIds);
}

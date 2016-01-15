package org.zttc.itat.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.zttc.itat.dao.IDepartmentDao;
import org.zttc.itat.dao.IUserDao;
import org.zttc.itat.exception.DocumentException;
import org.zttc.itat.model.Department;
import org.zttc.itat.model.Pager;
import org.zttc.itat.model.User;
import org.zttc.itat.model.UserEmail;

@Service("userService")
public class UserService implements IUserService {
	private IUserDao userDao;
	private IDepartmentDao departmentDao;
	
	
	
	public IDepartmentDao getDepartmentDao() {
		return departmentDao;
	}
	@Resource
	public void setDepartmentDao(IDepartmentDao departmentDao) {
		this.departmentDao = departmentDao;
	}

	public IUserDao getUserDao() {
		return userDao;
	}

	@Resource
	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public void add(User user, int depId) {
		if(loadByUsername(user.getUsername())!=null) throw new DocumentException("要添加的用户已经存在不能添加！");
		Department dep = departmentDao.load(depId);
		user.setDepartment(dep);
		userDao.add(user);
	}

	@Override
	public void delete(int id) {
		//TODO 需要加入删除关联对象的代码，需要关联删除文档、消息对象
		userDao.delete(id);
	}

	@Override
	public void update(User user,int depId) {
		Department dep = departmentDao.load(depId);
		user.setDepartment(dep);
		userDao.update(user);
	}

	@Override
	public User load(int id) {
		return userDao.load(id);
	}

	@Override
	public Pager<User> findUserByDep(Integer depId) {
		Pager<User> users = null;
		if(depId==null||depId<=0) {
			users = userDao.find("from User u left join fetch u.department");
		} else {
			users = userDao.find("from User u left join fetch u.department dep where dep.id=?",depId);
		}
		return users;
	}
	
	private User loadByUsername(String username) {
		String hql = "select u from User u where u.username=?";
		return (User)userDao.queryByHql(hql, username);
	}
	@Override
	public User login(String username, String password) {
		User u = this.loadByUsername(username);
		if(u==null) throw new DocumentException("用户名不存在");
		if(!u.getPassword().equals(password)) throw new DocumentException("用户密码不正确");
		return u;
	}
	@Override
	public void update(User user) {
		userDao.update(user);
	}
	@Override
	public List<User> listAllSendUser(int userId) {
		return userDao.listAllCanSendUser(userId);
	}
	@Override
	public void addUserEmail(UserEmail ue, int userId) {
		User u = this.load(userId);
		ue.setUser(u);
		userDao.addObj(ue);
	}
	@Override
	public UserEmail loadUserEmail(int id) {
		return userDao.loadUserEmail(id);
	}
	@Override
	public void updateUserEmail(UserEmail ue) {
		userDao.updateObj(ue);
	}
	@Override
	public List<String> listAllSendEmail(Integer[] userIds) {
		return userDao.listAllSendEmail(userIds);
	}

}

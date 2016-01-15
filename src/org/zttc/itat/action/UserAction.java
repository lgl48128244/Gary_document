package org.zttc.itat.action;

import java.lang.reflect.InvocationTargetException;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.zttc.itat.model.User;
import org.zttc.itat.model.UserEmail;
import org.zttc.itat.service.IDepartmentService;
import org.zttc.itat.service.IUserService;
import org.zttc.itat.util.ActionUtil;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

@Controller("userAction")
@Scope("prototype")
public class UserAction extends ActionSupport implements ModelDriven<User>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private User user;
	private Integer depId;
	private IDepartmentService departmentService;
	private UserEmail ue;
	private int userId;
	private int update;
	
	
	
	
	public int getUpdate() {
		return update;
	}
	public void setUpdate(int update) {
		this.update = update;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public UserEmail getUe() {
		return ue;
	}
	public void setUe(UserEmail ue) {
		this.ue = ue;
	}
	public IDepartmentService getDepartmentService() {
		return departmentService;
	}
	@Resource
	public void setDepartmentService(IDepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	public Integer getDepId() {
		return depId;
	}

	public void setDepId(Integer depId) {
		this.depId = depId;
	}

	private IUserService userService;
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public IUserService getUserService() {
		return userService;
	}
	@Resource
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
	
	public String addEmailInput() throws IllegalAccessException, InvocationTargetException {
		User loginUser = (User)ActionContext.getContext().getSession().get("loginUser");
		UserEmail tue = userService.loadUserEmail(loginUser.getId());
		if(tue==null) {
			//表示没有绑定，直接完成添加,设置一个变量来确定是不是要更新，0表示添加，1表示更新
			update = 0;
		} else {
			update = 1;
			//将已经存在的值设置进去，以便完成修改
			ue = tue;
		}
		return SUCCESS;
	}
	
	public String addEmail() {
		if(update==0) {
			userService.addUserEmail(ue, userId);
		} else {
			//如果邮箱已经存在，需要从数据库中取出这个对象，然后重新设置值，完成更新
			UserEmail tue = userService.loadUserEmail(userId);
			tue.setHost(ue.getHost());
			tue.setPassword(ue.getPassword());
			tue.setProtocol(ue.getProtocol());
			tue.setUsername(ue.getUsername());
			userService.updateUserEmail(tue);
		}
		ActionUtil.setUrl("/user_showSelf.action");
		return ActionUtil.REDIRECT;
	}

	@Override
	public User getModel() {
		if(user==null) user = new User();
		return user;
	}
	
	public String list() {
		ActionContext.getContext().put("ds",departmentService.listAllDep());
		ActionContext.getContext().put("pages", userService.findUserByDep(depId));
		return SUCCESS;
	}
	
	public String addInput() {
		ActionContext.getContext().put("ds",departmentService.listAllDep());
		return SUCCESS;
	}
	
	public String add() {
		userService.add(user, depId);
		ActionUtil.setUrl("/user_list.action");
		return ActionUtil.REDIRECT;
	}
	
	public void validateAdd() {
		if(ActionUtil.isEmpty(user.getUsername())) {
			this.addFieldError("username","用户名称不能为空");
		}
		if(this.hasFieldErrors()) addInput();
	}
	
	public String updateInput() throws IllegalAccessException, InvocationTargetException {
		User tu = userService.load(user.getId());
		BeanUtils.copyProperties(user, tu);
		ActionContext.getContext().put("ds",departmentService.listAllDep());
		return SUCCESS;
	}
	
	public String update() {
		User tu = userService.load(user.getId());
		tu.setEmail(user.getEmail());
		tu.setNickname(user.getNickname());
		tu.setType(user.getType());
		userService.update(tu, depId);
		ActionUtil.setUrl("/user_list.action");
		return ActionUtil.REDIRECT;
	}
	
	public String show() throws IllegalAccessException, InvocationTargetException {
		User tu = userService.load(user.getId());
		BeanUtils.copyProperties(user, tu);
		return SUCCESS;
	}
	
	public String delete() {
		userService.delete(user.getId());
		ActionUtil.setUrl("/user_list.action");
		return ActionUtil.REDIRECT;
	}
	
	public String updateSelfInput() throws IllegalAccessException, InvocationTargetException {
		User tu = (User)ActionContext.getContext().getSession().get("loginUser");
		BeanUtils.copyProperties(user, tu);
		return SUCCESS;
	}
	
	public String updateSelf() {
		User tu = userService.load(user.getId());
		tu.setEmail(user.getEmail());
		tu.setNickname(user.getNickname());
		userService.update(tu);
		ActionContext.getContext().getSession().put("loginUser",tu);
		ActionUtil.setUrl("/user_showSelf.action");
		return ActionUtil.REDIRECT;
	}
	
	public String showSelf() throws IllegalAccessException, InvocationTargetException {
		User tu = (User)ActionContext.getContext().getSession().get("loginUser");
		BeanUtils.copyProperties(user, tu);
		return SUCCESS;
	}
}  

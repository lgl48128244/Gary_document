package org.zttc.itat.action;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.zttc.itat.model.User;
import org.zttc.itat.service.IUserService;
import org.zttc.itat.util.ActionUtil;

import com.opensymphony.xwork2.ActionContext;

@Controller("loginAction")
@Scope("prototype")
public class LoginAction {
	private String username;
	private String password;
	private IUserService userService;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public IUserService getUserService() {
		return userService;
	}

	@Resource
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public String loginInput() {
		return "login";
	}

	public String login() {
		User u = userService.login(username, password);
		ActionContext.getContext().getSession().put("loginUser", u);
		ActionUtil.setUrl("/user_showSelf.action?id=" + u.getId());
		return ActionUtil.REDIRECT;
	}

	public String logout() {
		ActionContext.getContext().getSession().clear();
		ActionUtil.setUrl("/login!loginInput.action");
		return ActionUtil.REDIRECT;
	}

}

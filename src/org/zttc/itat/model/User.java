package org.zttc.itat.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "t_user")
public class User {
	private int id;
	private String username;
	private String password;
	private String nickname;
	private int type;
	private String email;
	private Department department;

	public User() {
	}

	public User(int id) {
		super();
		this.id = id;
	}

	public User(int id, String nickname) {
		super();
		this.id = id;
		this.nickname = nickname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * 用户类型，0表示普通用户，1表示管理员，管理员可以进行部门的添加，权限的分配等管理
	 * @return
	 */
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	@ManyToOne
	@JoinColumn(name = "dep_id")
	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

}

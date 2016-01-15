package org.zttc.itat.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="t_dep_scope")
public class DepScope {
	
	private int id;
	private int depId;
	private Department scopeDep;
	
	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * 要设置的部门id
	 * @return
	 */
	@Column(name="dep_id")
	public int getDepId() {
		return depId;
	}
	public void setDepId(int depId) {
		this.depId = depId;
	}
	/**
	 * 可以发送信息的部门对象
	 * @return
	 */
	@ManyToOne
	@JoinColumn(name="s_dep_id")
	public Department getScopeDep() {
		return scopeDep;
	}
	public void setScopeDep(Department scopeDep) {
		this.scopeDep = scopeDep;
	}
}

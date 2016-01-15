package org.zttc.itat.service;

import java.util.List;

import org.zttc.itat.model.Department;

public interface IDepartmentService {
	public void add(Department dep);
	
	public void update(Department dep);
	
	public void delete(int id);
	
	public Department load(int id);
	/**
	 * 获取所有部门
	 * @return
	 */
	public List<Department> listAllDep();
	/**
	 * 添加某个部门的管理部门
	 * @param depId 要添加的部门
	 * @param sDepId 要添加的管理部门
	 */
	public void addScopeDep(int depId,int sDepId);
	/**
	 * 为某个部门添加一组管理部门
	 * @param dep
	 * @param sDepIds
	 */
	public void addScopeDeps(int dep,int[] sDepIds);
	/**
	 * 删除某个部门的管理部门
	 * @param depId
	 * @param sDepId
	 */
	public void deleteScopeDep(int depId,int sDepId);
	/**
	 * 删除某个部门对应的所有可以发文部门
	 * @param depId
	 */
	public void deleteScopeDep(int depId);
	/**
	 * 获取某个用户所能管理的部门
	 * @param userId
	 * @return
	 */
	public List<Department> listUserDep(int userId);
	/**
	 * 获取某个部门所能发文的部门
	 * @param depId
	 * @return
	 */
	public List<Department> listDepScopeDep(int depId);
	/**
	 * 获取可以发文的一组部门id
	 * @param depId
	 * @return
	 */
	public List<Integer> listDepScopeDepId(int depId);
}

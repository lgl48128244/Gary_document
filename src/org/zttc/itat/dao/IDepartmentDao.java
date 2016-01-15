package org.zttc.itat.dao;

import java.util.List;

import org.zttc.itat.model.Department;

public interface IDepartmentDao extends IBaseDao<Department> {
	/**
	 * 根据部门来获取所有的可以发文的部门id列表
	 * @param depId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List listAllExistIds(int depId);
	
	public List<Department> listByIds(Integer[]ids);
	/**
	 * 通过用户id获取可以发文的部门，使用SQL语句
	 * @param id
	 * @return
	 */
	public List<Department> listSendDepByUser(int id);
}

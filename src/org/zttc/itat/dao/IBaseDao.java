package org.zttc.itat.dao;

import java.util.List;

import org.zttc.itat.model.Pager;

public interface IBaseDao<T> {
	public void add(T t);
	public void addObj(Object obj);
	public void update(T t);
	public void updateObj(Object obj);
	public void delete(Object obj);
	public void delete(int id);
	public T load(int id);
	/**
	 * 通过hql获取一组对象，不进行分页
	 * @param hql
	 * @param args
	 * @return
	 */
	public List<T> list(String hql,Object[] args);
	public List<T> list(String hql);
	public List<T> list(String hql,Object obj);
	
	public List<Object> listByObj(String hql,Object[] args);
	public List<Object> listByObj(String hql);
	public List<Object> listByObj(String hql,Object obj);
	/**
	 * 通过hql获取一组对象，进行分页处理
	 * @param hql
	 * @param args
	 * @return
	 */
	public Pager<T> find(String hql,Object[] args);
	public Pager<T> find(String hql,Object obj);
	public Pager<T> find(String hql);
	
	public Pager<Object> findByObj(String hql,Object[] args);
	public Pager<Object> findByObj(String hql,Object obj);
	public Pager<Object> findByObj(String hql);
	/**
	 * 通过hql获取一个对象
	 * @param hql
	 * @param args
	 * @return
	 */
	public Object queryByHql(String hql,Object[] args);
	public Object queryByHql(String hql,Object arg);
	public Object queryByHql(String hql);
	/**
	 * 调用hql更新一组对象
	 * @param hql
	 * @param args
	 */
	public void executeByHql(String hql,Object[] args);
	public void executeByHql(String hql,Object arg);
	public void executeByHql(String hql);
}	
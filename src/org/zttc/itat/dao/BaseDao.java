package org.zttc.itat.dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.zttc.itat.model.Pager;
import org.zttc.itat.model.SystemContext;

@SuppressWarnings("unchecked")
public class BaseDao<T> extends HibernateDaoSupport implements IBaseDao<T> {
	private Class<T> clz;
	
	@Resource(name="sessionFactory")
	public void setSuperSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	
	private Class<T> getClz() {
		if(clz==null) {
			//获取泛型的Class对象
			clz = ((Class<T>)
					(((ParameterizedType)(this.getClass().getGenericSuperclass())).getActualTypeArguments()[0]));
		}
		return clz;
	}

	@Override
	public void add(T t) {
		this.getHibernateTemplate().save(t);
	}

	@Override
	public void update(T t) {
		this.getHibernateTemplate().update(t);
	}

	@Override
	public void delete(int id) {
		T t = this.load(id);
		this.getHibernateTemplate().delete(t);
	}

	@Override
	public T load(int id) {
		return this.getHibernateTemplate().load(getClz(), id);
	}
	
	private Query setParamterQuery(String hql,Object[] args) {
		Query q = this.getSession().createQuery(hql);
		if(args!=null) {
			for(int i=0;i<args.length;i++) {
				q.setParameter(i, args[i]);
			}
		}
		return q;
	}

	@Override
	public List<T> list(String hql, Object[] args) {
		Query q = setParamterQuery(hql, args);
		return q.list();
	}

	@Override
	public List<T> list(String hql) {
		return this.list(hql, null);
	}

	@Override
	public List<T> list(String hql, Object obj) {
		return this.list(hql,new Object[]{obj});
	}

	@Override
	public Pager<T> find(String hql, Object[] args) {
		int pageSize = SystemContext.getPageSize();
		int pageOffset = SystemContext.getPageOffset();
		if(pageSize<=0) pageSize = 10;
		if(pageOffset<0) pageOffset = 0;
		Query q = setParamterQuery(hql, args);
		q.setFirstResult(pageOffset).setMaxResults(pageSize);
		String cHql = getCountHql(hql);
		Query cq = setParamterQuery(cHql, args);
		Pager<T> pager = new Pager<T>();
		pager.setPageOffset(pageOffset);
		pager.setPageSize(pageSize);
		List<T> datas = q.list();
		pager.setDatas(datas);
		long totalRecord = (Long)cq.uniqueResult();
		pager.setTotalRecord(totalRecord);
		return pager;
	}
	
	private String getCountHql(String hql) {
		String s = hql.substring(0,hql.indexOf("from"));
		if(s==null||s.trim().equals("")) {
			hql = "select count(*) "+hql;
		} else {
			hql = hql.replace(s, "select count(*) ");
		}
		hql = hql.replace("fetch", "");
		return hql;
	}

	@Override
	public Pager<T> find(String hql, Object obj) {
		return this.find(hql, new Object[]{obj});
	}

	@Override
	public Pager<T> find(String hql) {
		return this.find(hql,null);
	}

	@Override
	public Object queryByHql(String hql, Object[] args) {
		Query q = setParamterQuery(hql, args);
		return q.uniqueResult();
	}

	@Override
	public Object queryByHql(String hql, Object arg) {
		return this.queryByHql(hql, new Object[]{arg});
	}

	@Override
	public Object queryByHql(String hql) {
		return this.queryByHql(hql,null);
	}

	@Override
	public void executeByHql(String hql, Object[] args) {
		Query q = setParamterQuery(hql, args);
		q.executeUpdate();
	}

	@Override
	public void executeByHql(String hql, Object arg) {
		this.executeByHql(hql,new Object[]{arg});
	}

	@Override
	public void executeByHql(String hql) {
		this.executeByHql(hql,null);
	}

	@Override
	public List<Object> listByObj(String hql, Object[] args) {
		Query q = setParamterQuery(hql, args);
		return q.list();
	}

	@Override
	public List<Object> listByObj(String hql) {
		return this.listByObj(hql,null);
	}

	@Override
	public List<Object> listByObj(String hql, Object obj) {
		return this.listByObj(hql, new Object[]{obj});
	}

	@Override
	public Pager<Object> findByObj(String hql, Object[] args) {
		int pageSize = SystemContext.getPageSize();
		int pageOffset = SystemContext.getPageOffset();
		if(pageSize<=0) pageSize = 10;
		if(pageOffset<0) pageOffset = 0;
		Query q = setParamterQuery(hql, args);
		q.setFirstResult(pageOffset).setMaxResults(pageSize);
		String cHql = getCountHql(hql);
		Query cq = setParamterQuery(cHql, args);
		Pager<Object> pager = new Pager<Object>();
		pager.setPageOffset(pageOffset);
		pager.setPageSize(pageSize);
		List<Object> datas = q.list();
		pager.setDatas(datas);
		long totalRecord = (Long)cq.uniqueResult();
		pager.setTotalRecord(totalRecord);
		return pager;
	}

	@Override
	public Pager<Object> findByObj(String hql, Object obj) {
		return this.findByObj(hql, new Object[]{obj});
	}

	@Override
	public Pager<Object> findByObj(String hql) {
		return this.findByObj(hql,null);
	}

	@Override
	public void addObj(Object obj) {
		this.getHibernateTemplate().save(obj);
	}

	@Override
	public void updateObj(Object obj) {
		this.getHibernateTemplate().update(obj);
	}

	@Override
	public void delete(Object obj) {
		this.getHibernateTemplate().delete(obj);
	}

}

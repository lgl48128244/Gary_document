package org.zttc.itat.dao;


import java.util.List;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.zttc.itat.model.Department;

@Repository("departmentDao")
@SuppressWarnings("unchecked")
public class DepartmentDao extends BaseDao<Department> implements
		IDepartmentDao {

	@SuppressWarnings({ "rawtypes" })
	@Override
	public List<Integer> listAllExistIds(int depId) {
		String hql = "select ds.scopeDep.id from DepScope ds where ds.depId=?";
		return (List)this.listByObj(hql,depId);
	}

	@Override
	public List<Department> listByIds(Integer[] ids) {
		Query q = this.getSession().createQuery("select new Department(d.id,d.name) from Department d where d.id in (:ids)");
		q.setParameterList("ids",ids);
		return q.list();
	}

	@Override
	public List<Department> listSendDepByUser(int id) {
		String sql = "select dep.id,dep.name from " +
				"t_user user left join t_dep_scope ds on(ds.dep_id=user.dep_id) left join" +
				" t_dep dep on(dep.id=ds.s_dep_id) where user.id=?";
		return this.getSession().createSQLQuery(sql)
			.setResultTransformer(Transformers.aliasToBean(Department.class))
			.setParameter(0, id).list();
	}
}

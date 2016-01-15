package org.zttc.itat.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.zttc.itat.model.Department;
import org.zttc.itat.service.IDepartmentService;
import org.zttc.itat.util.ActionUtil;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

@SuppressWarnings("serial")
@Controller("departmentAction")
@Scope("prototype")
public class DepartmentAction extends ActionSupport implements ModelDriven<Department> {
	private IDepartmentService departmentService;
	private Department dep;
	private int[] sdeps;
	
	
	public int[] getSdeps() {
		return sdeps;
	}
	public void setSdeps(int[] sdeps) {
		this.sdeps = sdeps;
	}
	public IDepartmentService getDepartmentService() {
		return departmentService;
	}
	@Resource
	public void setDepartmentService(IDepartmentService departmentService) {
		this.departmentService = departmentService;
	}
	public Department getDep() {
		return dep;
	}
	public void setDep(Department dep) {
		this.dep = dep;
	}
	@Override
	public Department getModel() {
		if(dep==null) dep = new Department();
		return dep;
	}
	
	public String list() {
		ActionContext.getContext().put("ds", departmentService.listAllDep());
		return SUCCESS;
	}
	
	public String addInput() {
		return SUCCESS;
	}
	
	public String add() {
		departmentService.add(dep);
		ActionUtil.setUrl("/department_list.action");
		return ActionUtil.REDIRECT;
	}
	
	public String delete() {
		departmentService.delete(dep.getId());
		ActionUtil.setUrl("/department_list.action");
		return ActionUtil.REDIRECT;
	}
	
	public void validateAdd() {
		if(ActionUtil.isEmpty(dep.getName())) {
			this.addFieldError("name","部门名称不能为空");
		}
	}
	
	public String updateInput() {
		Department tp = departmentService.load(dep.getId());
		dep.setId(tp.getId());
		dep.setName(tp.getName());
		return SUCCESS;
	}
	
	public String update() {
		Department tp = departmentService.load(dep.getId());
		tp.setName(dep.getName());
		departmentService.update(tp);
		ActionUtil.setUrl("/department_list.action");
		return ActionUtil.REDIRECT;
	}
	
	public void validateUpdate() {
		if(ActionUtil.isEmpty(dep.getName())) {
			this.addFieldError("name","部门名称不能为空");
		}
	}
	
	public String setDepScopeInput() {
		dep = departmentService.load(dep.getId());
		List<Integer> ads = departmentService.listDepScopeDepId(dep.getId());
		ActionContext.getContext().put("ads", ads);
		List<Department> allDep = departmentService.listAllDep();
		//将当前部门移除
		allDep.remove(dep);
		ActionContext.getContext().put("ds", allDep);
		return SUCCESS;
	}
	
	public String setDepScope() {
		departmentService.addScopeDeps(dep.getId(), sdeps);
		ActionUtil.setUrl("/department_setDepScopeInput.action?id="+dep.getId());
		return ActionUtil.REDIRECT;
	}
	
	public String show() {
		dep = departmentService.load(dep.getId());
		ActionContext.getContext().put("ds", departmentService.listDepScopeDep(dep.getId()));
		return SUCCESS;
	}
	
	
}

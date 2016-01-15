package org.zttc.itat.test;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zttc.itat.model.Department;
import org.zttc.itat.service.IDepartmentService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/beans.xml")
public class TestDepartment {
	@Resource
	private IDepartmentService departmentService;
	
	
	@Test
	public void test01() {
		Department dep = new Department();
		dep.setName("食堂");
		departmentService.add(dep);
	}
	
	@Test
	public void testAddDepScope() {
		departmentService.addScopeDeps(5, new int[]{1,2,3,4,6,7});
	}
	
	@Test
	public void testListByDep() {
		List<Department> ds = departmentService.listDepScopeDep(5);
		for(Department d:ds) {
			System.out.println(d.getName());
		}
	}
	
	@Test
	public void testDelScopeDep() {
		departmentService.deleteScopeDep(5, 7);
	}
}

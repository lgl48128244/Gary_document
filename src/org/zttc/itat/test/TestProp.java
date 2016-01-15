package org.zttc.itat.test;

import java.util.Properties;

import org.junit.Test;
import org.zttc.itat.util.ActionUtil;
import org.zttc.itat.util.PropertiesUtil;

public class TestProp {

	@Test
	public void testProp() {
		Properties prop = PropertiesUtil.getAuthProp();
		System.out.println(prop.get("admin"));
	}
	
	@Test
	public void testAuth() {
		System.out.println(ActionUtil.getUserAuth()[0]);
		System.out.println(ActionUtil.getUserNotAuth()[1]);
	}
}

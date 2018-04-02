package com.mooc.house.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.mooc.house.biz.service.UserService;
import com.mooc.house.common.model.User;

@RunWith(SpringRunner.class)
@SpringBootTest // 保证可以使用spring框架的功能
public class AuthTest {

	@Autowired
	private UserService userService;
	@Test
	public void testAuth(){
		User user =	userService.auth("754576021@qq.com", "123456");
		  assert user != null;
		  System.out.println(user.getAboutme());
	}
}

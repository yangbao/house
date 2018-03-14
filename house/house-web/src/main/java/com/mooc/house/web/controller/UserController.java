package com.mooc.house.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mooc.house.biz.service.UserService;
import com.mooc.house.common.model.User;

@RestController
public class UserController {

  @Autowired
  private UserService userService;
  
  @RequestMapping("user")
  public List<User> getUsers(){
	  List<User> users = userService.getUsers();
	  return users;
  }
}

package com.mooc.house.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mooc.house.biz.service.UserService;
import com.mooc.house.common.model.User;
import com.mooc.house.common.result.ResultMsg;

@Controller
public class UserController {

  @Autowired
  private UserService userService;
  
  /**
   * 测试基础框架的mapping
   * @return
   */
  @RequestMapping("user")
  public List<User> getUsers(){
	  List<User> users = userService.getUsers();
	  return users;
  }
//---------------------注册页-------------------------
  /**
   * 注册提交 - 1. 注册验证, 2 发送邮件, 3 验证失败重定向到主页页面
   * 主页页获取: 根据account对象为依据判断是否注册页获取请求
   * @param account
   * @param modelMap
   * @return
   */
  @RequestMapping("/accounts/register")
  public String accountsRegister(User account,ModelMap modelMap){
	  //注册页的请求
	  if (account == null || account.getName() == null) {
	      return "/accounts/register";
	    }
	  //用户验证
    ResultMsg resultMsg = UserHelper.validate(account);
    
    if (resultMsg.isSuccess() && userService.addAccount(account)) {
    	
      modelMap.put("email", account.getEmail());
      	return "/accounts/registerSubmit";
    } else {
    	
      return "redirect:/accounts/register?" + resultMsg.asUrlParams();
    }
  }
  @RequestMapping("accounts/verify")
  public String verify(String key) {
    boolean result = userService.enable(key);
    if (result) {
      return "redirect:/index?" + ResultMsg.successMsg("激活成功").asUrlParams();
    } else {
      return "redirect:/accounts/register?" + ResultMsg.errorMsg("激活失败,请确认链接是否过期");
    }
  }

}

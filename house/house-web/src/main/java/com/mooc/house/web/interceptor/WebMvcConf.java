package com.mooc.house.web.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcConf extends WebMvcConfigurerAdapter {

	@Autowired
	private AuthActionInterceptor authActionInterceptor;
	
	@Autowired
	private AuthInterceptor authInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry){
		//放user的拦截器, 是有顺序的
		 registry.addInterceptor(authInterceptor).excludePathPatterns("/static").addPathPatterns("/**");
		 registry //只是拦截需要登录的请求, 判断是否登录了
		        .addInterceptor(authActionInterceptor).addPathPatterns("/house/toAdd")
		        .addPathPatterns("/accounts/profile").addPathPatterns("/accounts/profileSubmit")
		        .addPathPatterns("/house/bookmarked").addPathPatterns("/house/del")
		        .addPathPatterns("/house/ownlist").addPathPatterns("/house/add")
		        .addPathPatterns("/house/toAdd").addPathPatterns("/agency/agentMsg")
		        .addPathPatterns("/comment/leaveComment").addPathPatterns("/comment/leaveBlogComment");
	    super.addInterceptors(registry);
	}

	
}

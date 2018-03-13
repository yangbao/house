package com.mooc.house.config;

import java.sql.SQLException;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.google.common.collect.Lists;

@Configuration
public class DruidConfig {

	@ConfigurationProperties(prefix="spring.druid") //将配置文件中的外部的配置文件与Java的class绑定
	@Bean(initMethod="init",destroyMethod="close") //启动关闭容器时候 Datasource所调用Druid里面的方法
	public DruidDataSource dataSource(Filter statFilter) throws SQLException{
		DruidDataSource dataSource = new DruidDataSource();
		//Lists 是Google Guaua里面的工具类.
		dataSource.setProxyFilters(Lists.newArrayList(statFilter()));
		return dataSource;
	}
	/**
	 * Druid 自己定义的Fileter, 可以打印出来执行很慢SQL的日志.
	 * @return
	 */
	@Bean
	public Filter statFilter(){
		StatFilter filter = new StatFilter();
		filter.setSlowSqlMillis(5000);
		filter.setLogSlowSql(true);
		filter.setMergeSql(true);
		return filter;
	}
	/**servletRegistrationBean 用来配置Servlet  --- 相当于在在web.xml里面的配置
	 * 
	 * @return
	 */
	@Bean
	public ServletRegistrationBean servletRegistrationBean(){
		return new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
	}
}

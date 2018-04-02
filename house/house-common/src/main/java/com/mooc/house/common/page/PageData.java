package com.mooc.house.common.page;

import java.util.List;


public class PageData<T> {

	private List<T> list; //返回的结果列表,可以是不同类型的
	
	private Pagination pagination;//pageNum,pageSize,page list - 分页的情况
	
	public PageData(Pagination pagination,List<T> list){
		this.pagination = pagination;
		this.list = list;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}
	
	public static  <T> PageData<T> buildPage(List<T> list,Long count,Integer pageSize,Integer pageNum){
		Pagination _pagination = new Pagination(pageSize, pageNum, count);
		return new PageData<T>(_pagination, list);
	}
	
}

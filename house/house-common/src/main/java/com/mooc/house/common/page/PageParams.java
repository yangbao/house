package com.mooc.house.common.page;
/**
 * 封装了分页的信息.
 * @author u6035457
 *  语句1：select * from student limit 9,4
	语句2：slect * from student limit 4 offset 9
	语句1和2均返回表student的第10、11、12、13行  
	语句2中的4表示返回4行，9表示从表的第十行开始
 */
public class PageParams {
	//默认值
	private static final Integer PAGE_SIZE = 3;

	private Integer pageSize; // 每一页显示几条

	private Integer pageNum;  //第几页

	private Integer offset;

	private Integer limit;

	public static PageParams build(Integer pageSize, Integer pageNum) {
		
		if (pageSize == null) {
			pageSize = PAGE_SIZE;
		}
		if (pageNum == null) {
			pageNum = 1;
		}
		return new PageParams(pageSize, pageNum);
	}

	public PageParams() {
		this(PAGE_SIZE, 1);
	}

	public PageParams(Integer pageSize, Integer pageNum) {
		
		this.pageNum = pageNum;
		this.pageSize = pageSize;
		this.offset = pageSize * (pageNum - 1);
		this.limit = pageSize;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Integer getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

}

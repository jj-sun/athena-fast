package com.athena.common.utils;

import com.querydsl.core.QueryResults;
import org.springframework.data.domain.Page;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 分页工具类
 *
 * @author Mr.sun
 */
public class PageUtils implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;
	/**
	 * 总记录数
	 */
	private int totalCount;
	/**
	 * 每页记录数
	 */
	private int pageSize;
	/**
	 * 总页数
	 */
	private int totalPage;
	/**
	 * 当前页数
	 */
	private int currPage;
	/**
	 * 列表数据
	 */
	private List<?> list;
	
	/**
	 * 分页
	 * @param list        列表数据
	 * @param totalCount  总记录数
	 * @param pageSize    每页记录数
	 * @param currPage    当前页数
	 */
	public PageUtils(List<?> list, int totalCount, int pageSize, int currPage) {
		this.list = list;
		this.totalCount = totalCount;
		this.pageSize = pageSize;
		this.currPage = currPage;
		this.totalPage = (int)Math.ceil((double)totalCount/pageSize);
	}

	/**
	 * 分页
	 */
	public PageUtils(Page<?> page) {
		this.list = page.getContent();
		this.totalCount = (int)page.getTotalElements();
		this.pageSize = page.getSize();
		this.currPage = page.getNumber() + 1;
		this.totalPage = page.getTotalPages();
	}

	public PageUtils(QueryResults<?> queryResults) {
		this.list = queryResults.getResults();
		this.totalCount = (int)queryResults.getTotal();
		this.pageSize = (int)queryResults.getLimit();
		this.currPage = (int)queryResults.getOffset() + 1;
		//计算总页数
		long page = Math.floorDiv(queryResults.getTotal(),queryResults.getLimit());
		long pageMod = Math.floorMod(queryResults.getTotal(),queryResults.getLimit());
		this.totalPage = (int)(page + (pageMod > 0 ? 1L : 0L));
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getCurrPage() {
		return currPage;
	}

	public void setCurrPage(int currPage) {
		this.currPage = currPage;
	}

	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}
	
}

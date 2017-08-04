package com.fly.vo;

import java.util.ArrayList;
import java.util.List;

public class PageBean<T> {
	// 商品种类cid(根据商品种类进行分页显示)
	private String cid;
	// 当前页数
	private int currentPage;
	// 总页数
	private int totalPage;
	// 当前页条数
	private int currentCount;
	// 总条数
	private int totalCount;
	// 封装数据
	private List<T> productList = new ArrayList<T>();

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}
	
	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getCurrentCount() {
		return currentCount;
	}

	public void setCurrentCount(int currentCount) {
		this.currentCount = currentCount;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public List<T> getProductList() {
		return productList;
	}

	public void setProductList(List<T> productList) {
		this.productList = productList;
	}

	@Override
	public String toString() {
		return "PageBean [cid=" + cid + ", currentPage=" + currentPage + ", totalPage=" + totalPage + ", currentCount="
				+ currentCount + ", totalCount=" + totalCount + ", productList=" + productList + ", getCid()="
				+ getCid() + ", getCurrentPage()=" + getCurrentPage() + ", getTotalPage()=" + getTotalPage()
				+ ", getCurrentCount()=" + getCurrentCount() + ", getTotalCount()=" + getTotalCount()
				+ ", getProductList()=" + getProductList() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}
	
}

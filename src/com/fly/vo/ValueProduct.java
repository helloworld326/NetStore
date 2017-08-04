package com.fly.vo;

public class ValueProduct {
	private String pname;
	private String is_hot;
	private String cid;

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public String getIs_hot() {
		return is_hot;
	}

	public void setIs_hot(String is_hot) {
		this.is_hot = is_hot;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	@Override
	public String toString() {
		return "ValueProduct [pname=" + pname + ", is_hot=" + is_hot + ", cid=" + cid + ", getPname()=" + getPname()
				+ ", getIs_hot()=" + getIs_hot() + ", getCid()=" + getCid() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}

}

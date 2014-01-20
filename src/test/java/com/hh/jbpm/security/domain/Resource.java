package com.hh.jbpm.security.domain;

public class Resource {
	private int id;
	private String name;
	private String res_type;
	private String res_string;
	private String descn;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRes_type() {
		return res_type;
	}
	public void setRes_type(String res_type) {
		this.res_type = res_type;
	}
	public String getRes_string() {
		return res_string;
	}
	public void setRes_string(String res_string) {
		this.res_string = res_string;
	}
	public String getDescn() {
		return descn;
	}
	public void setDescn(String descn) {
		this.descn = descn;
	}
	
}

package com.dzdp.rs.model;

import java.io.Serializable;

public class Member implements Serializable {
	private int id;
	private String name;
	private String level;
	private int commentnum;
	private String portal;
	private String lastDate;

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

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public int getCommentnum() {
		return commentnum;
	}

	public void setCommentnum(int commentnum) {
		this.commentnum = commentnum;
	}

	public String getPortal() {
		return portal;
	}

	public void setPortal(String portal) {
		this.portal = portal;
	}

	public String getLastDate() {
		return lastDate;
	}

	public void setLastDate(String lastDate) {
		this.lastDate = lastDate;
	}

	
}

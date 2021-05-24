package model;

import java.io.Serializable;

public class User implements Serializable {
	private int id;
	private String loginId;
	private String pass;
	private String name;
	private int role;

	public User() {
	}
	public User(String loginId, String pass, String name, int role) {
		super();
		this.loginId = loginId;
		this.pass = pass;
		this.name = name;
		this.role = role;
	}

	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getRole() {
		return role;
	}
	public void setRole(int role) {
		this.role = role;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

}

package org.kdea.vo;

public class UserVO {
	
	private String email;
	private String nickname;
	private String pwd;
	private String name;
	private String phone;
	private String gender;
	private int point;
	private String authroity;
	private char enabled;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public int getPoint() {
		return point;
	}
	public void setPoint(int point) {
		this.point = point;
	}
	public String getAuthroity() {
		return authroity;
	}
	public void setAuthroity(String authroity) {
		this.authroity = authroity;
	}
	public char getEnabled() {
		return enabled;
	}
	public void setEnabled(char enabled) {
		this.enabled = enabled;
	}
	
}

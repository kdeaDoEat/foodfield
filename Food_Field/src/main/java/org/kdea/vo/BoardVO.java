package org.kdea.vo;

import java.sql.Date;
import java.util.List;

public class BoardVO {
	private int num;
	private String nickname;
	private Date w_date;
	private String title;
	private String contents;
	private int ref;
	private int hit;
	private String photo;
	private int recommend;
	private int grade;
	private String shop_name;
	private String shop_add;
	private List<CommentVO> cmtvo;
	private int cmtnum;
	
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public int getCmtnum() {
		return cmtnum;
	}
	public void setCmtnum(int cmtnum) {
		this.cmtnum = cmtnum;
	}
	public List<CommentVO> getCmtvo() {
		return cmtvo;
	}
	public void setCmtvo(List<CommentVO> cmtvo) {
		this.cmtvo = cmtvo;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public Date getW_date() {
		return w_date;
	}
	public void setW_date(Date w_date) {
		this.w_date = w_date;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getRef() {
		return ref;
	}
	public void setRef(int ref) {
		this.ref = ref;
	}
	public int getHit() {
		return hit;
	}
	public void setHit(int hit) {
		this.hit = hit;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public int getRecommend() {
		return recommend;
	}
	public void setRecommend(int recommend) {
		this.recommend = recommend;
	}
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	public String getShop_name() {
		return shop_name;
	}
	public void setShop_name(String shop_name) {
		this.shop_name = shop_name;
	}
	public String getShop_add() {
		return shop_add;
	}
	public void setShop_add(String shop_add) {
		this.shop_add = shop_add;
	}
	
}

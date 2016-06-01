package org.kdea.vo;

import java.sql.Date;

public class FreeboardVO 
{

	private int num;
	private int ref;
	private String title;
	private String content;
	private Date wdate;
	private String id;
	private boolean success;
	private BoardListPageVO pagevo;
	
	
	
	public BoardListPageVO getPagevo() {
		return pagevo;
	}
	public void setPagevo(BoardListPageVO pagevo) {
		this.pagevo = pagevo;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public int getRef() {
		return ref;
	}
	public void setRef(int ref) {
		this.ref = ref;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getWdate() {
		return wdate;
	}
	public void setWdate(Date wdate) {
		this.wdate = wdate;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	
}

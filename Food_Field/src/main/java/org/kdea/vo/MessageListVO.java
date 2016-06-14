package org.kdea.vo;

import java.util.List;

public class MessageListVO {
	
	private List<MessageVO> list;
	private List<Integer> page;
	private int allpage;
	private int nowpage;
	private String type;
	private String searchType;
	private String searchWord;
	
	public List<MessageVO> getList() {
		return list;
	}
	public void setList(List<MessageVO> list) {
		this.list = list;
	}
	public List<Integer> getPage() {
		return page;
	}
	public void setPage(List<Integer> page) {
		this.page = page;
	}
	public int getAllpage() {
		return allpage;
	}
	public void setAllpage(int allpage) {
		this.allpage = allpage;
	}
	public int getNowpage() {
		return nowpage;
	}
	public void setNowpage(int nowpage) {
		this.nowpage = nowpage;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSearchType() {
		return searchType;
	}
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	public String getSearchWord() {
		return searchWord;
	}
	public void setSearchWord(String searchWord) {
		this.searchWord = searchWord;
	}

}

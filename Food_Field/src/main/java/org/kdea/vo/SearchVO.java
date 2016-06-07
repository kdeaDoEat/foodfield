package org.kdea.vo;

public class SearchVO {
	
	private int page;
	private String searchCategory;
	private String searchContent;
	private boolean bsearch;
	private String word;
	private String type;
	
	
	
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public boolean isBsearch() {
		return bsearch;
	}
	public void setBsearch(boolean bsearch) {
		this.bsearch = bsearch;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public String getSearchCategory() {
		return searchCategory;
	}
	public void setSearchCategory(String searchCategory) {
		this.searchCategory = searchCategory;
	}
	public String getSearchContent() {
		return searchContent;
	}
	public void setSearchContent(String searchContent) {
		this.searchContent = searchContent;
	}
	

}

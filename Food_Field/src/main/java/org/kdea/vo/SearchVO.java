package org.kdea.vo;

public class SearchVO {
	
	private int page;
	private String searchCategory;
	private String searchContent;
	private boolean bsearch;
	
	
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

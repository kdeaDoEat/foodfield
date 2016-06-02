package org.kdea.vo;

public class BoardListPageVO {

	private int firstPage;
	private int lastPage;
	private int totalPage;
	private int divideAllPage;
	private int currentPage;
	private boolean isLeftMore;
	private boolean isRightMore;
	public int getFirstPage() {
		return firstPage;
	}
	public void setFirstPage(int firstPage) {
		this.firstPage = firstPage;
	}
	public int getLastPage() {
		return lastPage;
	}
	public void setLastPage(int lastPage) {
		this.lastPage = lastPage;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getDivideAllPage() {
		return divideAllPage;
	}
	public void setDivideAllPage(int divideAllPage) {
		this.divideAllPage = divideAllPage;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public boolean isLeftMore() {
		return isLeftMore;
	}
	public void setLeftMore(boolean isLeftMore) {
		this.isLeftMore = isLeftMore;
	}
	public boolean isRightMore() {
		return isRightMore;
	}
	public void setRightMore(boolean isRightMore) {
		this.isRightMore = isRightMore;
	}
	
	
}

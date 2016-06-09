package org.kdea.vo;

import java.sql.Date;

public class FreeBoardVO 
{

   private int num;
   private String nickname;
   private Date w_date;
   private String title;
   private String contents;
   private int ref;
   private int views;//조회수
   private String photo;//사진경로
   private int recommend;//추천
   
   private boolean success;
   private BoardListPageVO pagevo;
   
   
   
   public BoardListPageVO getPagevo() {
      return pagevo;
   }
   public void setPagevo(BoardListPageVO pagevo) {
      this.pagevo = pagevo;
   }
   
   public boolean isSuccess() {
      return success;
   }
   public void setSuccess(boolean success) {
      this.success = success;
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
   public String getContents() {
      return contents;
   }
   public void setContents(String contents) {
      this.contents = contents;
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
   public int getViews() {
      return views;
   }
   public void setViews(int views) {
      this.views = views;
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

   
}
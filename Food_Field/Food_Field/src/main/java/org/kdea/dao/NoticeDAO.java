package org.kdea.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.kdea.vo.BoardVO;
import org.kdea.vo.CommentVO;

public interface NoticeDAO {

	public boolean insertBoard(@Param("board") BoardVO board);
	public boolean insertComment(@Param("comment") CommentVO comment);
	public boolean updateBoard(@Param("board") BoardVO board);
	public boolean updateComment(@Param("comment") CommentVO comment);
	public boolean deleteComment(@Param("comment") CommentVO comment);
	public BoardVO selectBoard(@Param("board") BoardVO board);
	public CommentVO selectComment(@Param("cnum") int cnum);
	public int getHit(@Param("board") BoardVO board);
	public boolean setHit(@Param("board") BoardVO board,@Param("hit") int hit);
	public int getRecommend(@Param("board") BoardVO board);
	public boolean setRecommend(@Param("board") BoardVO board,@Param("recommend") int recommend);
	public List<CommentVO> selectComments(@Param("board") BoardVO board);
	public int lastIndex();
	public int commentLastIndex();
	public int getAllCount(@Param("option") String option, @Param("search") String search);
	public int getRnbyBno(@Param("board") BoardVO board, @Param("option") String option, @Param("search") String search);
	public List<BoardVO> getList(@Param("page") int page, @Param("rpp") int rpp, @Param("option") String option, @Param("search") String search);
	public int getParentConfirm(@Param("board") BoardVO board);
	public boolean deleteBoard(@Param("board") BoardVO board);
	
}

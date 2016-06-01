package org.kdea.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.kdea.vo.BoardVO;

public interface NoticeDAO {

	public boolean insertBoard(@Param("board") BoardVO board);
	public boolean updateBoard(@Param("board") BoardVO board);
	public BoardVO selectBoard(@Param("board") BoardVO board);
	public int lastIndex();
	public int getAllCount(@Param("option") String option, @Param("search") String search);
	public int getRnbyBno(@Param("board") BoardVO board, @Param("option") String option, @Param("search") String search);
	public List<BoardVO> getList(@Param("page") int page, @Param("rpp") int rpp, @Param("option") String option, @Param("search") String search);
	public int getParentConfirm(@Param("board") BoardVO board);
	public boolean deleteBoard(@Param("board") BoardVO board);
	
}

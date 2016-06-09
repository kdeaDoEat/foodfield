package org.kdea.qnaboard;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.kdea.vo.BoardVO;

public interface QnADAO {
	
	
	/********************* [ List ] *********************/
	public int getAllCount(@Param("type") String type, @Param("word") String word);
	public int getRnbyBno(@Param("board") BoardVO board, @Param("type") String type, @Param("word") String word);
	public List<BoardVO> getList(@Param("page") int page, @Param("rpp") int rpp, @Param("type") String type, @Param("word") String word);
	
	/********************* [ Write ] *********************/ 
	public boolean insertBoard(@Param("board")BoardVO board);
	public int lastIndex();
	public BoardVO selectBoard(@Param("board")BoardVO board); //With [View]
	
	/********************* [ Modify ] *********************/ 
	public boolean updateBoard(@Param("board")BoardVO board);
	
	/********************* [ Delete ] *********************/ 
	public int getParentConfirm(@Param("board")BoardVO board);
	public boolean deleteBoard(@Param("board")BoardVO board);

}

package org.kdea.reviewboard;

import java.util.List;

import org.kdea.vo.BoardVO;
import org.kdea.vo.CommentVO;

public interface ReviewDAO {
	
	public List<BoardVO> getList(int page);
	public int write(BoardVO vo);
	public BoardVO read(int num);
	public int page();
	public int updateHit(int num);
	public int commentWrite(CommentVO vo);
	public List<CommentVO> getCommentList(int num);
	public int getCommentCount(int page);
	public int commentModify(CommentVO vo);
	public int commentDelete(CommentVO vo);
	public int reviewDelete(BoardVO vo);
	public int reviewModify(BoardVO vo);
	public int reviewBoardCommentDelete(int num);

}

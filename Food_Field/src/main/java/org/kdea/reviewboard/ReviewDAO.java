package org.kdea.reviewboard;

import java.util.List;

import org.kdea.vo.BoardVO;
import org.kdea.vo.CommentVO;
import org.kdea.vo.SearchVO;

public interface ReviewDAO {
	
	public List<BoardVO> getList(int page);
	public List<BoardVO> list();
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
	public int spage(SearchVO vo);
	public List<BoardVO> search(SearchVO vo);
	public int checkRecommend(BoardVO vo);
	public int updateRecommend(BoardVO vo);
	public int updateUserRecommend(BoardVO vo);
	public int userWritePoint(BoardVO vo);
	public int userCommentPoint(CommentVO vo);
	public int getMyNum(BoardVO vo);
	public List<BoardVO> getRecommendList(int page);

}

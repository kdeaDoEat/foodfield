package org.kdea.freeboard;

import java.util.List;

import org.kdea.vo.CommentVO;
import org.kdea.vo.FreeBoardVO;
import org.kdea.vo.SearchVO;

public interface FreeBoardDAO {
	public List<FreeBoardVO> list(int page);

	public int write(FreeBoardVO fbVO);

	public FreeBoardVO read(String id);

	public FreeBoardVO readNum(int num);
	public int cmtWrite(CommentVO comment);

	public List<CommentVO> cmtList(int num);

	public int modify(FreeBoardVO fb);

	public List<FreeBoardVO> beforeDelete(int num);

	public int delete(int num);

	public CommentVO getCommentDetail(int num);

	public int cmtModify(CommentVO comment);
	
	public int cmtDelete(int num);

	public int relpyInput(FreeBoardVO fbVO);

	public List<FreeBoardVO> getSearchList(SearchVO svo);

	public int viewsCtn(int num);

}
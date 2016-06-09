package org.kdea.freeboard;

import java.util.List;

import org.kdea.vo.CommentVO;
import org.kdea.vo.FreeBoardVO;
import org.kdea.vo.SearchVO;

public interface FreeBoardDAO {
	
	public List<FreeBoardVO> list(int page);//리스트 불러오기

	public int write(FreeBoardVO fbVO);

	public FreeBoardVO read(String id);

	public FreeBoardVO readNum(int num);//수정한 내용불러오기

	public int cmtWrite(CommentVO comment);//코멘트달기

	public List<CommentVO> cmtList(int num);//코멘트 불러오기

	public int modify(FreeBoardVO fb);//글수정

	public List<FreeBoardVO> beforeDelete(int num);//삭제전 답글있는지 확인

	public int delete(int num);//내용삭제

	public CommentVO getCommentDetail(int num);//수정전 내용불러오기

	public int cmtModify(CommentVO comment);//코멘트 내용수정

	public int cmtDelete(int num);//코멘트 삭제

	public int relpyInput(FreeBoardVO fbVO);//답글 달기

	public List<FreeBoardVO> getSearchList(SearchVO svo);//검색

	public int viewsCtn(int num);//조회수 카운트



}

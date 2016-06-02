package org.kdea.freeboard;

import java.util.List;

import org.kdea.vo.FreeboardVO;
import org.kdea.vo.SearchVO;

public interface FreeBoardDAO {
	
	public List<FreeboardVO> list(int page);

	public int winput(FreeboardVO fbVO);

	public FreeboardVO getDetail(String id);

	public FreeboardVO getModiDetail(int num);//수정한 내용불러오기

	public int commentsuc(FreeboardVO fb);//코멘트달기

	public List<FreeboardVO> CommentList(int num);//코멘트 불러오기

	public int getModiSuccess(FreeboardVO fb);//글수정

	public List<FreeboardVO> beforeDelete(int num);//삭제전 답글있는지 확인

	public int getDelete(int num);//내용삭제

	public FreeboardVO getCommentDetail(int num);//수정전 내용불러오기

	public int commentModisuc(FreeboardVO fb);//코멘트 내용수정

	public int getCommentDeltet(int num);//코멘트 삭제

	public int relpyinput(FreeboardVO fbVO);//답글 달기

	public List<FreeboardVO> getSearchList(SearchVO svo);//검색



}

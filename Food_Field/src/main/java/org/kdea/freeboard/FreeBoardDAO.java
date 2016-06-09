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

	public int recommend(FreeBoardVO bvo);//추천 누가, 어느 번호에 했는지 입력

	public int recommendCount(FreeBoardVO bvo);//추천수업데이트

	public FreeBoardVO confirmrecommendCtn(FreeBoardVO bvo);//해당 글번호에 추천누른적있는지 확인

	public List<FreeBoardVO> haverecommend(int num);

	public int deleteRecommend(int num);

	public List<FreeBoardVO> haveComment(int num);//코멘트 있는지 확인(상위부모글번호)

	public int deleteAllComment(int num);

	public CommentVO CommentDetail(String nickname);



}

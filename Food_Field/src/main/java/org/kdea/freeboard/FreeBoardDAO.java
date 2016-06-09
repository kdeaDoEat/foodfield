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
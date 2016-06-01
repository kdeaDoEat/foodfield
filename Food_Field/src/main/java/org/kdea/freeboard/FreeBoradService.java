package org.kdea.freeboard;

import java.util.List;

import org.json.simple.JSONObject;
import org.kdea.vo.BoardListPageVO;
import org.kdea.vo.FreeboardVO;
import org.kdea.vo.SearchVO;
import org.mybatis.spring.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;


@Service
public class FreeBoradService {

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	public List<FreeboardVO> getFreebdList(int page){
		FreeBoardDAO fbdao= sqlSessionTemplate.getMapper(FreeBoardDAO.class);
		List<FreeboardVO> list= fbdao.list(page);
		BoardListPageVO pagenavi=list.get(0).getPagevo();
		int rowsPerScreen=10;//한 페이지 게시글 수
		int linksPerScreen=5;//페이지네비게이션 수
		int totalpages=pagenavi.getTotalPage();

		
		int linkGroup=(page-1)/linksPerScreen+1;
		int linkEnd=linkGroup*linksPerScreen;
		int linkBegin= linkEnd-linksPerScreen+1;
		System.out.println("링크 끝: "+linkEnd);
		System.out.println("총 글수: "+totalpages);
		
		if(linkEnd>totalpages)linkEnd=totalpages;
		
		pagenavi.setCurrentPage(page);
		pagenavi.setLeftMore(linkGroup!=1?true:false);
		System.out.println("서비스) 왼쪽: "+pagenavi.isLeftMore());
		pagenavi.setRightMore(linkEnd<totalpages?true:false);
		pagenavi.setFirstPage(linkBegin);
		pagenavi.setLastPage(linkEnd);
		
		return list;
	}

	public FreeboardVO Input(FreeboardVO fbVO) {

		System.out.println("서비스) 제목: "+fbVO.getTitle()+", 내용: "+fbVO.getContent());
		System.out.println("아이디: "+fbVO.getId());
		System.out.println("ref: "+fbVO.getRef());
		
		
		FreeBoardDAO fbdao= sqlSessionTemplate.getMapper(FreeBoardDAO.class);
		if(fbVO.getRef()!=0){
			System.out.println("여기는 reply다는 부분");
			int replysuccess= fbdao.relpyinput(fbVO);
			fbVO.setSuccess(true);
			return fbVO;
		}
		
		int wSuccess=fbdao.winput(fbVO);
		if(wSuccess>0){
			System.out.println("삽입성공");
			fbVO.setSuccess(true);
			return fbVO;
		}
		else{
			fbVO.setSuccess(false);
			return fbVO;
		}
	}

	public FreeboardVO getDetail(int num) {
		// 글번호로 내용 불러올때
		System.out.println("서비스:글번호로 상세내용)글번호: "+num);
		FreeBoardDAO fbdao= sqlSessionTemplate.getMapper(FreeBoardDAO.class);
		
		return fbdao.getModiDetail(num);
	}

	public FreeboardVO getDetail(String id) {
		// 글쓴이로 내용 불러올때(글쓰고 나서 바로 불러오는거)
		
		System.out.println("서비스: 아이디로 상세내용)가져온 아이디: "+id);
		FreeBoardDAO fbdao= sqlSessionTemplate.getMapper(FreeBoardDAO.class);
		return fbdao.getDetail(id);
	}

	public boolean wcomment(FreeboardVO fb) {
	//코멘트달기
		System.out.println("코멘트가 달리는 현재 글번호: "+fb.getRef());
		FreeBoardDAO fbdao= sqlSessionTemplate.getMapper(FreeBoardDAO.class);
		int commentSuccess=fbdao.commentsuc(fb);
		if(commentSuccess>0){
			return true;
		}
		return false;
	}
	public List<FreeboardVO> getCommentList(int num) {
		// 상위 글번호로 코멘트 리스트 불러오기
		FreeBoardDAO fbdao= sqlSessionTemplate.getMapper(FreeBoardDAO.class);
		List<FreeboardVO> list= fbdao.CommentList(num);
	
		return list;
	}

	public boolean modify(FreeboardVO fb) {
		// 글수정
		FreeBoardDAO fbdao= sqlSessionTemplate.getMapper(FreeBoardDAO.class);
		int mdSuccess=fbdao.getModiSuccess(fb);
		if(mdSuccess>0){
			return true;
		}
		return false;
	
	}

	public boolean beforeDelete(int num) {
		// 글을 삭제하기 전에 답글이 있는지 확인(댓글은 노상관)
		System.out.println("상위부모 글번호: "+num);
		FreeBoardDAO fbdao= sqlSessionTemplate.getMapper(FreeBoardDAO.class);
		List<FreeboardVO> list= fbdao.beforeDelete(num);
		if(list.size()!=0){
			System.out.println("답글이 있음");
			return true;
		}else	{
			System.out.println("답글이 없음");
			return false;
			}
	}

	public boolean delete(int num) {
		// 답글 있는지 없는지 확인후 삭제
		FreeBoardDAO fbdao= sqlSessionTemplate.getMapper(FreeBoardDAO.class);
		int deleteSuccess=fbdao.getDelete(num);
		if(deleteSuccess>0){
			return true;
		}
		return false;
	}

	public String getCommentDetail(int num) {
		// 코멘트 수정하기 위해 내용 불러오기
		FreeBoardDAO fbdao= sqlSessionTemplate.getMapper(FreeBoardDAO.class);
		FreeboardVO fb=fbdao.getCommentDetail(num);
		if(fb!=null){
			JSONObject jobj= new JSONObject();
			jobj.put("Ccontent", fb.getContent());
			jobj.put("Cnum", fb.getNum());
			String jobjStr= jobj.toJSONString();
			System.out.println("서비스: "+jobjStr);
			return jobjStr;
		}
		return null;
	}

	public String ComodiSuceess(FreeboardVO fb) {
		//코멘트 수정

		FreeBoardDAO fbdao= sqlSessionTemplate.getMapper(FreeBoardDAO.class);
		int commentModi=fbdao.commentModisuc(fb);
		if(commentModi>0){
			System.out.println("코멘트 수정 성공");
			String sucModidetail=getCommentDetail(fb.getNum());
			System.out.println("글수정 성공하고 불러온 내용: "+sucModidetail);
			return sucModidetail;
		}
		JSONObject jobj= new JSONObject();
		jobj.put("Cmodisuc", false);
		return jobj.toJSONString();
		
		
	}

	public String CoDelSuceess(int num) {
		// 코멘트 삭제
		FreeBoardDAO fbdao= sqlSessionTemplate.getMapper(FreeBoardDAO.class);
		int commentDel=fbdao.getCommentDeltet(num);
		if(commentDel>0){
			System.out.println("코멘트 삭제성공");
			JSONObject jobj= new JSONObject();
			jobj.put("Cdelsuc", true);
			String sucDeleteComment=jobj.toJSONString();
			System.out.println("글삭제 성공하고 불러온 내용: "+sucDeleteComment);
			return sucDeleteComment;
		}
		JSONObject jobj= new JSONObject();
		jobj.put("Cdelsuc", false);
		return jobj.toJSONString();
		
	}

	public List<FreeboardVO> getSearchList(SearchVO svo) {
		// 글 찾기
		FreeBoardDAO fbdao= sqlSessionTemplate.getMapper(FreeBoardDAO.class);
		int page= svo.getPage();
		List<FreeboardVO> list= fbdao.getSearchList(svo);
		BoardListPageVO pagenavi=list.get(0).getPagevo();
		int rowsPerScreen=10;//한 페이지 게시글 수
		int linksPerScreen=5;//페이지네비게이션 수
		int totalpages=pagenavi.getTotalPage();

		
		int linkGroup=(page-1)/linksPerScreen+1;
		int linkEnd=linkGroup*linksPerScreen;
		int linkBegin= linkEnd-linksPerScreen+1;
		System.out.println("링크 끝: "+linkEnd);
		System.out.println("총 글수: "+totalpages);
		
		if(linkEnd>totalpages)linkEnd=totalpages;
		
		pagenavi.setCurrentPage(page);
		pagenavi.setLeftMore(linkGroup!=1?true:false);
		System.out.println("서비스) 왼쪽: "+pagenavi.isLeftMore());
		pagenavi.setRightMore(linkEnd<totalpages?true:false);
		pagenavi.setFirstPage(linkBegin);
		pagenavi.setLastPage(linkEnd);
		if(list.size()>0){
			System.out.println("검색이 성공적으로 이루어짐");
			return list;
		}
		
		return null;
	}
}

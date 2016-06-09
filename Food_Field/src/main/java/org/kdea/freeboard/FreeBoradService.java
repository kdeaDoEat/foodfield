package org.kdea.freeboard;

import java.util.List;

import org.json.simple.JSONObject;
import org.kdea.vo.BoardListPageVO;
import org.kdea.vo.CommentVO;
import org.kdea.vo.FreeBoardVO;
import org.kdea.vo.SearchVO;
import org.mybatis.spring.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;


@Service
public class FreeBoradService {

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	public List<FreeBoardVO> list(int page){
		FreeBoardDAO fbdao= sqlSessionTemplate.getMapper(FreeBoardDAO.class);
		List<FreeBoardVO> list= fbdao.list(page);
		BoardListPageVO pagenavi=list.get(0).getPagevo();
		int rowsPerScreen=10;//�븳 �럹�씠吏� 寃뚯떆湲� �닔
		int linksPerScreen=5;//�럹�씠吏��꽕鍮꾧쾶�씠�뀡 �닔
		int totalpages=pagenavi.getTotalPage();

		
		int linkGroup=(page-1)/linksPerScreen+1;
		int linkEnd=linkGroup*linksPerScreen;
		int linkBegin= linkEnd-linksPerScreen+1;

		if(linkEnd>totalpages)linkEnd=totalpages;
		
		pagenavi.setCurrentPage(page);
		pagenavi.setLeftMore(linkGroup!=1?true:false);
		pagenavi.setRightMore(linkEnd<totalpages?true:false);
		pagenavi.setFirstPage(linkBegin);
		pagenavi.setLastPage(linkEnd);
		
		return list;
	}

	public FreeBoardVO write(FreeBoardVO fbVO) {

		FreeBoardDAO fbdao= sqlSessionTemplate.getMapper(FreeBoardDAO.class);
		if(fbVO.getRef()!=0){
			if(fbVO.getPhoto()==null){fbVO.setPhoto("img");}
			int replysuccess= fbdao.relpyInput(fbVO);
			if(replysuccess>0){
				fbVO.setSuccess(true);
				return fbVO;
				}
		}
		
		if(fbVO.getPhoto()==null){fbVO.setPhoto("img");}
		int wSuccess=fbdao.write(fbVO);
		if(wSuccess>0){
			fbVO.setSuccess(true);
			return fbVO;
		}
		else{
			fbVO.setSuccess(false);
			return fbVO;
		}
	}

	
	public FreeBoardVO read(int num) {

		FreeBoardDAO fbdao= sqlSessionTemplate.getMapper(FreeBoardDAO.class);
		
		return fbdao.readNum(num);
	}
	
	public FreeBoardVO read(String id) {
		// 글쓴이로 내용 불러올때(글쓰고 나서 바로 불러오는거)
		FreeBoardDAO fbdao= sqlSessionTemplate.getMapper(FreeBoardDAO.class);
		return fbdao.read(id);
	}

	public boolean cmtWrite(CommentVO comment){
	//코멘트달기
		System.out.println("코멘트가 달리는 현재 글번호: "+comment.getNum());


		FreeBoardDAO fbdao= sqlSessionTemplate.getMapper(FreeBoardDAO.class);
		int commentSuccess=fbdao.cmtWrite(comment);
		if(commentSuccess>0){
			return true;
		}
		return false;
	}

	public List<CommentVO> cmtList(int num) {
		// 상위 글번호로 코멘트 리스트 불러오기
		FreeBoardDAO fbdao= sqlSessionTemplate.getMapper(FreeBoardDAO.class);
		List<CommentVO> list= fbdao.cmtList(num);
	
		return list;
	}

	public boolean modify(FreeBoardVO fb) {
		// 글수정
		FreeBoardDAO fbdao= sqlSessionTemplate.getMapper(FreeBoardDAO.class);
		int mdSuccess=fbdao.modify(fb);
		if(mdSuccess>0){
			return true;
		}
		return false;
	
	}

	public boolean beforeDelete(int num) {
		// 글을 삭제하기 전에 답글이 있는지 확인(댓글은 노상관)

		FreeBoardDAO fbdao= sqlSessionTemplate.getMapper(FreeBoardDAO.class);
		List<FreeBoardVO> list= fbdao.beforeDelete(num);
		if(list.size()!=0){
			return true;
		}else{

			return false;
			}
	}

	public boolean delete(int num) {
		// �떟湲� �엳�뒗吏� �뾾�뒗吏� �솗�씤�썑 �궘�젣
		FreeBoardDAO fbdao= sqlSessionTemplate.getMapper(FreeBoardDAO.class);
		int deleteSuccess=fbdao.delete(num);
		if(deleteSuccess>0){
			return true;
		}
		return false;
	}

	public String getCommentDetail(int num) {
		// 肄붾찘�듃 �닔�젙�븯湲� �쐞�빐 �궡�슜 遺덈윭�삤湲�
		FreeBoardDAO fbdao= sqlSessionTemplate.getMapper(FreeBoardDAO.class);
		CommentVO comment=fbdao.getCommentDetail(num);
		if(comment!=null){
			JSONObject jobj= new JSONObject();
			jobj.put("Ccontent", comment.getContents());
			jobj.put("Cnum", comment.getNum());
			String jobjStr= jobj.toJSONString();
		
			return jobjStr;
		}
		return null;
	}

	public String cmtModify(CommentVO comment) {
		//코멘트 수정
		FreeBoardDAO fbdao= sqlSessionTemplate.getMapper(FreeBoardDAO.class);
		int commentModi=fbdao.cmtModify(comment);
		if(commentModi>0){
			String sucModidetail=getCommentDetail(comment.getNum());

			return sucModidetail;
		}
		JSONObject jobj= new JSONObject();
		jobj.put("Cmodisuc", false);
		return jobj.toJSONString();
		
		
	}


	public String cmtDelete(int num) {
		// 코멘트 삭제
		FreeBoardDAO fbdao= sqlSessionTemplate.getMapper(FreeBoardDAO.class);
		int commentDel=fbdao.cmtDelete(num);
		if(commentDel>0){
			JSONObject jobj= new JSONObject();
			jobj.put("Cdelsuc", true);
			String sucDeleteComment=jobj.toJSONString();
			return sucDeleteComment;
		}
		JSONObject jobj= new JSONObject();
		jobj.put("Cdelsuc", false);
		return jobj.toJSONString();
		
	}

	public List<FreeBoardVO> getSearchList(SearchVO svo) {
		// 글 찾기
		FreeBoardDAO fbdao= sqlSessionTemplate.getMapper(FreeBoardDAO.class);
		int page= svo.getPage();
		List<FreeBoardVO> list= fbdao.getSearchList(svo);
		BoardListPageVO pagenavi=list.get(0).getPagevo();
		int rowsPerScreen=10;//�븳 �럹�씠吏� 寃뚯떆湲� �닔
		int linksPerScreen=5;//�럹�씠吏��꽕鍮꾧쾶�씠�뀡 �닔
		int totalpages=pagenavi.getTotalPage();
		
		int linkGroup=(page-1)/linksPerScreen+1;
		int linkEnd=linkGroup*linksPerScreen;
		int linkBegin= linkEnd-linksPerScreen+1;
		
		if(linkEnd>totalpages)linkEnd=totalpages;
		
		pagenavi.setCurrentPage(page);
		pagenavi.setLeftMore(linkGroup!=1?true:false);
		pagenavi.setRightMore(linkEnd<totalpages?true:false);
		pagenavi.setFirstPage(linkBegin);
		pagenavi.setLastPage(linkEnd);
		if(list.size()>0){
			return list;
		}
		return null;
	}

	public boolean viewsCtn(int num) {
		// 조회수 카운트
		FreeBoardDAO fbdao= sqlSessionTemplate.getMapper(FreeBoardDAO.class);
		int ViewsCtn=fbdao.viewsCtn(num);
		if(ViewsCtn>0){
			return true;
		}
		return false;
	}
}

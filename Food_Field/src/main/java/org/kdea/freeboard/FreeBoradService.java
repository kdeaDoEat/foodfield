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

	public FreeboardVO Input(FreeboardVO fbVO) {

		FreeBoardDAO fbdao= sqlSessionTemplate.getMapper(FreeBoardDAO.class);
		if(fbVO.getRef()!=0){
			int replysuccess= fbdao.relpyinput(fbVO);
			fbVO.setSuccess(true);
			return fbVO;
		}
		
		int wSuccess=fbdao.winput(fbVO);
		if(wSuccess>0){
			fbVO.setSuccess(true);
			return fbVO;
		}
		else{
			fbVO.setSuccess(false);
			return fbVO;
		}
	}

	public FreeboardVO getDetail(int num) {
		// 湲�踰덊샇濡� �궡�슜 遺덈윭�삱�븣
		FreeBoardDAO fbdao= sqlSessionTemplate.getMapper(FreeBoardDAO.class);
		
		return fbdao.getModiDetail(num);
	}

	public FreeboardVO getDetail(String id) {
		// 湲��벖�씠濡� �궡�슜 遺덈윭�삱�븣(湲��벐怨� �굹�꽌 諛붾줈 遺덈윭�삤�뒗嫄�)
		
		FreeBoardDAO fbdao= sqlSessionTemplate.getMapper(FreeBoardDAO.class);
		return fbdao.getDetail(id);
	}

	public boolean wcomment(FreeboardVO fb) {
	//肄붾찘�듃�떖湲�
		FreeBoardDAO fbdao= sqlSessionTemplate.getMapper(FreeBoardDAO.class);
		int commentSuccess=fbdao.commentsuc(fb);
		if(commentSuccess>0){
			return true;
		}
		return false;
	}
	public List<FreeboardVO> getCommentList(int num) {
		// �긽�쐞 湲�踰덊샇濡� 肄붾찘�듃 由ъ뒪�듃 遺덈윭�삤湲�
		FreeBoardDAO fbdao= sqlSessionTemplate.getMapper(FreeBoardDAO.class);
		List<FreeboardVO> list= fbdao.CommentList(num);
	
		return list;
	}

	public boolean modify(FreeboardVO fb) {
		// 湲��닔�젙
		FreeBoardDAO fbdao= sqlSessionTemplate.getMapper(FreeBoardDAO.class);
		int mdSuccess=fbdao.getModiSuccess(fb);
		if(mdSuccess>0){
			return true;
		}
		return false;
	
	}

	public boolean beforeDelete(int num) {
		// 湲��쓣 �궘�젣�븯湲� �쟾�뿉 �떟湲��씠 �엳�뒗吏� �솗�씤(�뙎湲��� �끂�긽愿�)
		FreeBoardDAO fbdao= sqlSessionTemplate.getMapper(FreeBoardDAO.class);
		List<FreeboardVO> list= fbdao.beforeDelete(num);
		if(list.size()!=0){
			return true;
		}else	{
			return false;
			}
	}

	public boolean delete(int num) {
		// �떟湲� �엳�뒗吏� �뾾�뒗吏� �솗�씤�썑 �궘�젣
		FreeBoardDAO fbdao= sqlSessionTemplate.getMapper(FreeBoardDAO.class);
		int deleteSuccess=fbdao.getDelete(num);
		if(deleteSuccess>0){
			return true;
		}
		return false;
	}

	public String getCommentDetail(int num) {
		// 肄붾찘�듃 �닔�젙�븯湲� �쐞�빐 �궡�슜 遺덈윭�삤湲�
		FreeBoardDAO fbdao= sqlSessionTemplate.getMapper(FreeBoardDAO.class);
		FreeboardVO fb=fbdao.getCommentDetail(num);
		if(fb!=null){
			JSONObject jobj= new JSONObject();
			jobj.put("Ccontent", fb.getContent());
			jobj.put("Cnum", fb.getNum());
			String jobjStr= jobj.toJSONString();
			return jobjStr;
		}
		return null;
	}

	public String ComodiSuceess(FreeboardVO fb) {
		//肄붾찘�듃 �닔�젙

		FreeBoardDAO fbdao= sqlSessionTemplate.getMapper(FreeBoardDAO.class);
		int commentModi=fbdao.commentModisuc(fb);
		if(commentModi>0){
			String sucModidetail=getCommentDetail(fb.getNum());
			return sucModidetail;
		}
		JSONObject jobj= new JSONObject();
		jobj.put("Cmodisuc", false);
		return jobj.toJSONString();
		
		
	}

	public String CoDelSuceess(int num) {
		// 肄붾찘�듃 �궘�젣
		FreeBoardDAO fbdao= sqlSessionTemplate.getMapper(FreeBoardDAO.class);
		int commentDel=fbdao.getCommentDeltet(num);
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

	public List<FreeboardVO> getSearchList(SearchVO svo) {
		// 湲� 李얘린
		FreeBoardDAO fbdao= sqlSessionTemplate.getMapper(FreeBoardDAO.class);
		int page= svo.getPage();
		List<FreeboardVO> list= fbdao.getSearchList(svo);
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
}

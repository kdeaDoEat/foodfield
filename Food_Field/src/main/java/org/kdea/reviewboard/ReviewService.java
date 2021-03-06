package org.kdea.reviewboard;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.plaf.synth.SynthSplitPaneUI;

import org.json.simple.JSONObject;
import org.kdea.vo.BoardVO;
import org.kdea.vo.CommentVO;
import org.kdea.vo.ListVO;
import org.kdea.vo.SearchVO;
import org.kdea.vo.UserVO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import net.sf.json.JSONArray;

@Service
public class ReviewService {
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate; // 占쏙옙占쏙옙占쏙옙占싹울옙 占쏙옙占쏙옙占쏙옙 占쏙옙溝퓸占쏙옙占� 占쏙옙占쏙옙占쏙옙 占쏙옙占쏙옙占쌘놂옙 Setter 占쏙옙占쏙옙 占쌘듸옙占쏙옙占쏙옙 占쏙옙占쏙옙
	
	public List<BoardVO> getList(int page){
		ReviewDAO dao = sqlSessionTemplate.getMapper(ReviewDAO.class);
		List<BoardVO> vo = dao.getList(page);
		for(int i=0;i<vo.size();i++){
			int first = vo.get(i).getContents().indexOf("<img");
			int end = vo.get(i).getContents().indexOf("\" />");
			if(first == -1){
				vo.get(i).setPhoto(null);
			}else{
				vo.get(i).setPhoto(vo.get(i).getContents().substring(first,end+4));
			}
		}
		return vo;
	}

	public String write(BoardVO vo) {
		ReviewDAO dao = sqlSessionTemplate.getMapper(ReviewDAO.class);
		JSONObject jobj = new JSONObject();
		boolean result = false;
		BoardVO nvo = vo;
		nvo.setContents(vo.getContents().replaceAll("\n", ""));
		if(dao.write(nvo)>0){
			dao.userWritePoint(vo);
			result=true;
		}
		jobj.put("ok", result);
		jobj.put("num", dao.getMyNum(vo));
		return jobj.toJSONString();
	}

	public BoardVO read(BoardVO vo) {
		ReviewDAO dao = sqlSessionTemplate.getMapper(ReviewDAO.class);
		int page = vo.getNum();
		BoardVO bvo = dao.read(page);
		bvo.setCmtnum(dao.getCommentCount(page));
		int first = bvo.getContents().indexOf("<img");
		int end = bvo.getContents().indexOf("\" />");
		String d = bvo.getContents().substring(first,end+4);
		return bvo;
	}

	public int pagecount() {
		ReviewDAO dao = sqlSessionTemplate.getMapper(ReviewDAO.class);
		return dao.page();
	}

	public List<Integer> boardPage(int nowPage) {
		ReviewDAO dao = sqlSessionTemplate.getMapper(ReviewDAO.class);
		int fullPage = dao.page();
		List<Integer> pageList = new ArrayList<Integer>();
		if(nowPage>10){
			String tmp = Integer.toString(nowPage).substring(0,Integer.toString(nowPage).length()-1)+0;	// 占쏙옙占쌘몌옙+0
			int firstPage = Integer.parseInt(tmp);
			for(int i=1;i<=10;i++){
				if(firstPage+i>fullPage) break;
				pageList.add(firstPage+i);
			}
		}else{
			if(fullPage>10){
				for(int i=1;i<=10;i++){
					pageList.add(i);
				}
			}else if(fullPage<=10){
				for(int i=1;i<=fullPage;i++){
					pageList.add(i);
				}
			}
		}
		return pageList;
	}

	public void updateHit(int num) {
		ReviewDAO dao = sqlSessionTemplate.getMapper(ReviewDAO.class);
		dao.updateHit(num);
	}

	public String commentWrite(CommentVO vo) {
		ReviewDAO dao = sqlSessionTemplate.getMapper(ReviewDAO.class);
		JSONObject jobj = new JSONObject();
		boolean check = false;
		if(dao.commentWrite(vo) > 0){
			dao.userCommentPoint(vo);
			check = true;
		}
		jobj.put("ok", check);
		return jobj.toJSONString();
	}
	
	public List<CommentVO> commentList(HttpServletRequest request){
		ReviewDAO dao = sqlSessionTemplate.getMapper(ReviewDAO.class);
		return dao.getCommentList(Integer.parseInt(request.getParameter("num")));
	}

	public String commentModify(CommentVO vo) {
		ReviewDAO dao = sqlSessionTemplate.getMapper(ReviewDAO.class);
		JSONObject jobj = new JSONObject();
		boolean check = false;
		if(dao.commentModify(vo) > 0){
			check = true;
		}
		jobj.put("ok", check);
		return jobj.toJSONString();
	}

	public String commentDelete(CommentVO vo) {
		ReviewDAO dao = sqlSessionTemplate.getMapper(ReviewDAO.class);
		JSONObject jobj = new JSONObject();
		boolean check = false;
		if(dao.commentDelete(vo) > 0){
			check = true;
		}
		jobj.put("ok", check);
		return jobj.toJSONString();
	}

	public String reviewModify(BoardVO vo) {
		ReviewDAO dao = sqlSessionTemplate.getMapper(ReviewDAO.class);
		JSONObject jobj = new JSONObject();
		boolean check = false;
		BoardVO nvo = vo;
		nvo.setContents(vo.getContents().replaceAll("\n", ""));
		if(dao.reviewModify(nvo) > 0){
			check = true;
		}
		jobj.put("ok", check);
		return jobj.toJSONString();
	}

	public String reviewDelete(BoardVO vo) {
		ReviewDAO dao = sqlSessionTemplate.getMapper(ReviewDAO.class);
		JSONObject jobj = new JSONObject();
		boolean check = false;
		if(dao.reviewDelete(vo) > 0){
			dao.reviewBoardCommentDelete(vo.getNum());
			check = true;
		}
		jobj.put("ok", check);
		return jobj.toJSONString();
	}


	public String uploadPhoto(FileBean fileBean, Model model, HttpSession session) {
	    String root_path = "C:/img/"; // 占쏙옙占쏙옙占쏙옙 root 占쏙옙占�

	    MultipartFile upload = fileBean.getUpload();
	    String filename = "";
	    String CKEditorFuncNum = "";
	    
	    Date d = new Date();
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	    UserVO uvo = (UserVO)session.getAttribute("userInfo");
	    if (upload != null) {
	        filename = sdf.format(d)+"_"+uvo.getNickname()+"_"+upload.getOriginalFilename();
	        fileBean.setFilename(filename);
	        CKEditorFuncNum = fileBean.getCKEditorFuncNum();
	        try {
	            File file = new File(root_path + filename);
	            upload.transferTo(file);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	    String file_path = filename;
	    model.addAttribute("file_path", "http://192.168.8.28:8088/img/"+file_path);
	    model.addAttribute("CKEditorFuncNum", CKEditorFuncNum);
	    
	    return "review/write";
	}

	public ListVO getSearchList(SearchVO vo) {
		ListVO lvo = new ListVO();
		lvo.setList(boardsearch(vo));
		lvo.setPage(searchPage(vo));
		lvo.setAllpage(searchPagecount(vo));
		lvo.setNowpage(vo.getPage());
		lvo.setType("search");
		lvo.setSearchType(vo.getType());
		lvo.setSearchWord(vo.getWord());
		return lvo;
	}

	private int searchPagecount(SearchVO vo) {
		ReviewDAO dao = sqlSessionTemplate.getMapper(ReviewDAO.class);
		SearchVO svo = new SearchVO();
		svo.setType(vo.getType());
		svo.setWord(vo.getWord());
		return dao.spage(svo);
	}

	private List<Integer> searchPage(SearchVO vo) {
		ReviewDAO dao = sqlSessionTemplate.getMapper(ReviewDAO.class);
		int fullPage = dao.spage(vo);
		List<Integer> pageList = new ArrayList<Integer>();
		if(vo.getPage()>10){
			String tmp = Integer.toString(vo.getPage()).substring(0,Integer.toString(vo.getPage()).length()-1)+0;	// 占쏙옙占쌘몌옙+0
			int firstPage = Integer.parseInt(tmp);
			for(int i=1;i<=10;i++){
				pageList.add(firstPage+i);
			}
		}else{
			if(fullPage>10){
				for(int i=1;i<=10;i++){
					pageList.add(i);
				}
			}else if(fullPage<=10){
				for(int i=1;i<=fullPage;i++){
					pageList.add(i);
				}
			}
		}
		return pageList;
	}

	private List<BoardVO> boardsearch(SearchVO svo) {
		ReviewDAO dao = sqlSessionTemplate.getMapper(ReviewDAO.class);
		List<BoardVO> vo = dao.search(svo);
		
		for(int i=0;i<vo.size();i++){
			int first = vo.get(i).getContents().indexOf("<img");
			int end = vo.get(i).getContents().indexOf("\" />");
			if(first == -1){
				vo.get(i).setPhoto(null);
			}else{
				vo.get(i).setPhoto(vo.get(i).getContents().substring(first,end+4));
			}
		}
		return vo;
	}

	public String recommend(BoardVO vo) {
		ReviewDAO dao = sqlSessionTemplate.getMapper(ReviewDAO.class);
		JSONObject jobj = new JSONObject(); 
		if(dao.checkRecommend(vo) > 0){
			jobj.put("ok", false);
		}else if(dao.updateRecommend(vo) > 0 && dao.updateUserRecommend(vo) > 0){   // 占쌉시뱄옙 占쏙옙천 占시몌옙占쏙옙 && 占쏙옙천 占쏙옙占싱븝옙 占쌩곤옙占싹깍옙
			jobj.put("ok", true);
		}
		return jobj.toJSONString();
	}
	public List<BoardVO> getRecommendList(int page) {
		ReviewDAO dao = sqlSessionTemplate.getMapper(ReviewDAO.class);
		List<BoardVO> vo = dao.getRecommendList(page);
		for(int i=0;i<vo.size();i++){
			int first = vo.get(i).getContents().indexOf("<img");
			int end = vo.get(i).getContents().indexOf("\" />");
			if(first == -1){
				vo.get(i).setPhoto(null);
			}else{
				vo.get(i).setPhoto(vo.get(i).getContents().substring(first,end+4));
			}
		}
		return vo;
	}
	public List<BoardVO> getHitList(int page){
		ReviewDAO dao = sqlSessionTemplate.getMapper(ReviewDAO.class);
		List<BoardVO> vo = dao.getHitList(page);
		for(int i=0;i<vo.size();i++){
			int first = vo.get(i).getContents().indexOf("<img");
			int end = vo.get(i).getContents().indexOf("\" />");
			if(first == -1){
				vo.get(i).setPhoto(null);
			}else{
				vo.get(i).setPhoto(vo.get(i).getContents().substring(first,end+4));
			}
		}
		return vo;
	}
	
}

package org.kdea.reviewboard;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
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
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import net.sf.json.JSONArray;

@Service
public class ReviewService {
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate; // 설정파일에 빈으로 등록되었기 때문에 생성자나 Setter 없이 자동으로 주입
	
	public List<BoardVO> getList(int page){
		ReviewDAO dao = sqlSessionTemplate.getMapper(ReviewDAO.class);
		return dao.getList(page);
	}

	public String write(BoardVO vo) {
		ReviewDAO dao = sqlSessionTemplate.getMapper(ReviewDAO.class);
		JSONObject jobj = new JSONObject();
		boolean result = false;
		BoardVO nvo = vo;
		nvo.setContents(vo.getContents().replaceAll("\n", ""));
		if(dao.write(nvo)>0){
			result=true;
		}
		jobj.put("ok", result);
		
		return jobj.toJSONString();
	}

	public BoardVO read(HttpServletRequest request) {
		ReviewDAO dao = sqlSessionTemplate.getMapper(ReviewDAO.class);
		int page = Integer.parseInt(request.getParameter("num"));
		
		BoardVO vo = dao.read(page);
		vo.setCmtnum(dao.getCommentCount(page));
		return vo;
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
			String tmp = Integer.toString(nowPage).substring(0,Integer.toString(nowPage).length()-1)+0;	// 앞자리+0
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

	public void updateHit(HttpServletRequest request) {
		ReviewDAO dao = sqlSessionTemplate.getMapper(ReviewDAO.class);
		dao.updateHit(Integer.parseInt(request.getParameter("num")));
	}

	public String commentWrite(CommentVO vo) {
		ReviewDAO dao = sqlSessionTemplate.getMapper(ReviewDAO.class);
		JSONObject jobj = new JSONObject();
		boolean check = false;
		if(dao.commentWrite(vo) > 0){
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
		if(dao.reviewModify(vo) > 0){
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


	public String uploadPhoto(FileBean fileBean, Model model) {
	    String root_path = "C:/img/"; // 웹서비스 root 경로

	    MultipartFile upload = fileBean.getUpload();
	    String filename = "";
	    String CKEditorFuncNum = "";
	    if (upload != null) {
	        filename = upload.getOriginalFilename();
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

	/*public BoardVO modify(HttpServletRequest request) {
		ReviewDAO dao = sqlSessionTemplate.getMapper(ReviewDAO.class);
		return null;
	}*/
	
}

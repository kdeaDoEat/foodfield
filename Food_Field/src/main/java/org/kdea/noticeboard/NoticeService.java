package org.kdea.noticeboard;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.simple.JSONObject;
import org.kdea.vo.BoardVO;
import org.kdea.vo.CommentVO;
import org.kdea.vo.PageVO;
import org.kdea.vo.SearchVO;
import org.kdea.vo.UserVO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class NoticeService {

	final int rpp = 10;
	final int ppp = 5;

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	NoticeDAO dao;

	public PageVO setPage(int currpage, String option, String search) {

		PageVO page = new PageVO();
		page.setCurrpage(currpage);
		page.setTotalpage(getTotalPage(option, search));
		page.setPpp(ppp);
		return page;
	}

	public int getTotalPage(String option, String search) {
		
		int allpage;
		int allrecords = dao.getAllCount(option, search);

		if (allrecords % rpp == 0) {

			allpage = allrecords / rpp;

		} else {

			allpage = (int) (((double) (allrecords / rpp)) + 1);

		}
		return allpage;
	}

	public int getPageNobyBno(BoardVO board, String option, String Search) {

		int curRn = dao.getRnbyBno(board, option, Search);
		int curpage;
		if (curRn % rpp == 0) {
			curpage = curRn / rpp;
		} else {
			curpage = (int) (((double) (curRn / rpp)) + 1);
		}
		return curpage;
	}

	public BoardVO insertBoard(BoardVO board, HttpServletRequest request) {

		UserVO user = (UserVO)request.getSession().getAttribute("userInfo");
		board.setNickname(user.getNickname());
		//board.setNickname("tempuser");
		dao = sqlSessionTemplate.getMapper(NoticeDAO.class);
		if (dao.insertBoard(board)) {

			board.setNum(dao.lastIndex() - 1);
			return board;

		}
		;
		return board;

	}
    
	public String fileUploader(HttpServletRequest request, HttpServletResponse response) {
		
		
		
		String return1="";	
		String return2="";
		String return3="";

		String name = "";
		if (ServletFileUpload.isMultipartContent(request)){
			ServletFileUpload uploadHandler = new ServletFileUpload(new DiskFileItemFactory());
			uploadHandler.setHeaderEncoding("UTF-8");
			List<FileItem> items = null;
			try {
				items = uploadHandler.parseRequest(request);
				for (FileItem item : items) {
					if(item.getFieldName().equals("callback")) {
						return1 = item.getString("UTF-8");
					} else if(item.getFieldName().equals("callback_func")) {
						return2 = "?callback_func="+item.getString("UTF-8");
					} else if(item.getFieldName().equals("Filedata")) {
						if(item.getSize() > 0) {
							
			                
			                name = item.getName().substring(item.getName().lastIndexOf(File.separator)+1);
							String filename_ext = name.substring(name.lastIndexOf(".")+1);
							filename_ext = filename_ext.toLowerCase();
						   	String[] allow_file = {"jpg","png","bmp","gif"};
						   	int cnt = 0;
						   	for(int i=0; i<allow_file.length; i++) {
						   		if(filename_ext.equals(allow_file[i])){
						   			cnt++;
						   		}
						   	}
						   	if(cnt == 0) {
						   		return3 = "&errstr="+name;
						   	} else {
						   		
					    		String dftFilePath = request.getSession().getServletContext().getRealPath("/");
					    		String filePath = dftFilePath + "resources" + File.separator +"smart_editor2" + File.separator +"sample" + File.separator + "photo_uploader" + File.separator + "multiupload" + File.separator;
					    		
					    		File file = null;
					    		file = new File(filePath);
					    		if(!file.exists()) {
					    			file.mkdirs();
					    		}
					    		
					    		String realFileNm = "";
					    		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
								String today= formatter.format(new java.util.Date());
								realFileNm = today+UUID.randomUUID().toString() + name.substring(name.lastIndexOf("."));
								
								String rlFileNm = filePath + realFileNm;
								
								InputStream is = item.getInputStream();
								OutputStream os=new FileOutputStream(rlFileNm);
								int numRead;
								byte b[] = new byte[(int)item.getSize()];
								while((numRead = is.read(b,0,b.length)) != -1){
									os.write(b,0,numRead);
								}
								if(is != null) {
									is.close();
								}
								os.flush();
								os.close();
								
					    		
					    		return3 += "&bNewLine=true";
					    		return3 += "&sFileName="+ name;
					    		return3 += "&sFileURL="+"resources/smart_editor2/sample/photo_uploader/multiupload/"+"/editor/upload/"+realFileNm;
						   	}
						}else {
							  return3 += "&errstr=error";
						}
					}
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return "redirect:"+return1+return2+return3;
		
		
	}
	
	
	public String fileUploaderHTML5(HttpServletRequest request) {
		
		String sFileInfo = "";
		String filename = request.getHeader("file-name");
		String filename_ext = filename.substring(filename.lastIndexOf(".")+1);
		filename_ext = filename_ext.toLowerCase();
			
		String[] allow_file = {"jpg","png","bmp","gif"};

		int cnt = 0;
		for(int i=0; i<allow_file.length; i++) {
			if(filename_ext.equals(allow_file[i])){
				cnt++;
			}
		}

		if(cnt == 0) {
			System.out.println("NOTALLOW_"+filename);
			return "NOTALLOW_"+filename;
		} else {
			
		String dftFilePath = request.getSession().getServletContext().getRealPath("/");

		String filePath = dftFilePath + "resources" + File.separator +"smart_editor2" + File.separator +"sample" + File.separator + "photo_uploader" + File.separator + "multiupload" + File.separator;
		System.out.println("uploadhtml5 함수 filePath" + filePath);
		File file = new File(filePath);
		if(!file.exists()) {
			file.mkdirs();
		}
		String realFileNm = "";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String today= formatter.format(new java.util.Date());
		realFileNm = today+UUID.randomUUID().toString() + filename.substring(filename.lastIndexOf("."));
		String rlFileNm = filePath + realFileNm;

		try{
		InputStream is = request.getInputStream();
		OutputStream os=new FileOutputStream(rlFileNm);
		int numRead;
		byte b[] = new byte[Integer.parseInt(request.getHeader("file-size"))];
		while((numRead = is.read(b,0,b.length)) != -1){
			os.write(b,0,numRead);
		}
		if(is != null) {
			is.close();
		}
		os.flush();
		os.close();
		} catch(Exception e){
			e.printStackTrace();
		}

		sFileInfo += "&bNewLine=true";
		sFileInfo += "&sFileName="+ filename;;
		sFileInfo += "&sFileURL="+"resources/smart_editor2/sample/photo_uploader/multiupload/"+realFileNm;
		System.out.println(sFileInfo);
		}
		return sFileInfo;
		
	}
	
	
	public Object recommend(BoardVO board) {

		dao = sqlSessionTemplate.getMapper(NoticeDAO.class);
		int currrec = dao.getRecommend(board);
		JSONObject object = new JSONObject();
		if (dao.setRecommend(board, currrec + 1)) {

			object.put("recommend", currrec + 1);
			object.put("success", true);

		} else {
			object.put("success", false);
		}
		return object;

	}

	public int increaseHit(BoardVO board) {

		int currhit = dao.getHit(board);
		if (dao.setHit(board, currhit + 1)) {

			return currhit + 1;

		}
		return currhit;

	}

	public Object replyBoard(CommentVO comment, HttpServletRequest request) {

		UserVO user = (UserVO)request.getSession().getAttribute("userInfo");
		comment.setNickname(user.getNickname());
		//comment.setNickname("tempuser");
		JSONObject object = new JSONObject();

		dao = sqlSessionTemplate.getMapper(NoticeDAO.class);
		if (dao.insertComment(comment)) {

			object.put("success", true);
			int last = dao.commentLastIndex();
			comment = dao.selectComment(last - 1);
			object.put("nickname", comment.getNickname());
			object.put("w_date", comment.getW_date());
			object.put("contents", comment.getContents());

		} else {
			object.put("success", false);
		}
		return object;

	}

	public List<BoardVO> getBoardListbyPage(int page, Model model, String option, String search) {

		List<BoardVO> boardlist = new ArrayList<BoardVO>();
		dao = sqlSessionTemplate.getMapper(NoticeDAO.class);
		boardlist = dao.getList(page, rpp, option, '%' + search + '%');

		PageVO pageVO = setPage(page, option, '%' + search + '%');
		SearchVO searchVO = new SearchVO();
		searchVO.setWord(search);
		searchVO.setType(option);
		model.addAttribute("search", searchVO);
		model.addAttribute("page", pageVO);
		return boardlist;
	}

	public List<BoardVO> getBoardCurrListbyBno(BoardVO board, Model model, String option, String search) {

		List<BoardVO> boardlist = new ArrayList<BoardVO>();
		dao = sqlSessionTemplate.getMapper(NoticeDAO.class);
		int curpage = getPageNobyBno(board, option, search);
		boardlist = dao.getList(curpage, rpp, option, '%' + search + '%');

		PageVO page = setPage(curpage, option, '%' + search + '%');
		SearchVO searchVO = new SearchVO();
		searchVO.setWord(search);
		searchVO.setType(option);
		model.addAttribute("search", searchVO);
		model.addAttribute("page", page);
		return boardlist;

	}

	public BoardVO updateBoard(BoardVO board, HttpServletRequest request) {

		dao = sqlSessionTemplate.getMapper(NoticeDAO.class);
		if (dao.updateBoard(board)) {

			return board;

		}
		;
		return board;

	}

	public List<CommentVO> selectComments(BoardVO board) {

		List<CommentVO> comments = new ArrayList<CommentVO>();
		dao = sqlSessionTemplate.getMapper(NoticeDAO.class);
		comments = dao.selectComments(board);
		return comments;
	}

	public BoardVO selectBoard(BoardVO board) {

		dao = sqlSessionTemplate.getMapper(NoticeDAO.class);
		board = dao.selectBoard(board);
		return board;

	}

	public BoardVO deleteBoard(BoardVO board) {

		dao = sqlSessionTemplate.getMapper(NoticeDAO.class);
		if (dao.deleteBoard(board)) {

			System.out.println("삭제 성공");

		}
		;
		System.out.println("삭제 실패");

		return board;

	}

	public boolean confirmParent(BoardVO board) {

		dao = sqlSessionTemplate.getMapper(NoticeDAO.class);
		int ref = dao.getParentConfirm(board);
		if (ref == 0) {

			return true;

		}
		return false;

	}

	public Object modifyReply(CommentVO comment) {

		dao = sqlSessionTemplate.getMapper(NoticeDAO.class);
		JSONObject object = new JSONObject();
		if (dao.updateComment(comment)) {
			object.put("success", true);
			object.put("contents", comment.getContents());
		} else {
			object.put("success", false);
		};
		return object;
	}
	
	public Object delReply(CommentVO comment) {

		dao = sqlSessionTemplate.getMapper(NoticeDAO.class);
		JSONObject object = new JSONObject();
		if (dao.deleteComment(comment)) {
			object.put("success", true);			
		} else {
			object.put("success", false);
		};
		return object;
	}

}

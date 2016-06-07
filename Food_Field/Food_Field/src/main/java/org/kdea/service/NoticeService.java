package org.kdea.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.kdea.dao.NoticeDAO;
import org.kdea.vo.BoardVO;
import org.kdea.vo.CommentVO;
import org.kdea.vo.PageVO;
import org.kdea.vo.SearchVO;
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
	public PageVO setPage(int currpage,String option,String search) {

		PageVO page = new PageVO();
		page.setCurrpage(currpage);
		page.setTotalpage(getTotalPage(option,search));
		page.setPpp(ppp);
		return page;
	}

	public int getTotalPage(String option,String search) {
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

		int curRn = dao.getRnbyBno(board , option, Search);
		int curpage;
		if (curRn % rpp == 0) {
			curpage = curRn / rpp;
		} else {
			curpage = (int) (((double) (curRn / rpp)) + 1);
		}
		return curpage;
	}

	public BoardVO insertBoard(BoardVO board, HttpServletRequest request) {

		board.setNickname("tempuser");
		dao = sqlSessionTemplate.getMapper(NoticeDAO.class);
		if (dao.insertBoard(board)) {

			board.setNum(dao.lastIndex() - 1);
			return board;

		};
		return board;

	}
	
	public Object recommend(BoardVO board){
		
		dao = sqlSessionTemplate.getMapper(NoticeDAO.class);
		int currrec = dao.getRecommend(board);
		JSONObject object = new JSONObject();
		if(dao.setRecommend(board,currrec+1)){
		      	
			object.put("recommend", currrec+1);
			object.put("success", true);
			
		}else{
		object.put("success", false);
		}
		return object;
		
	}
	
	public int increaseHit(BoardVO board){
		
		int currhit = dao.getHit(board);
		if(dao.setHit(board,currhit+1)){
			
			return currhit+1;
			
		}
		return currhit;
		
	}

	public Object replyBoard(CommentVO comment, HttpServletRequest request) {
		
		
		comment.setNickname("tempuser");
		JSONObject object = new JSONObject();
		
		dao = sqlSessionTemplate.getMapper(NoticeDAO.class);
		if (dao.insertComment(comment)) {
						
			object.put("success", true);
			int last = dao.commentLastIndex();
			comment = dao.selectComment(last-1);
			object.put("nickname", comment.getNickname());
			object.put("w_date", comment.getW_date());
			object.put("contents", comment.getContents());
			
		}else{
		    object.put("success", false);
		}
		return object;
		
	}

	public List<BoardVO> getBoardListbyPage(int page, Model model, String option, String search) {

		List<BoardVO> boardlist = new ArrayList<BoardVO>();
		dao = sqlSessionTemplate.getMapper(NoticeDAO.class);
		boardlist = dao.getList(page, rpp, option, '%'+search+'%');

		PageVO pageVO = setPage(page,option,'%'+search+'%');
		SearchVO searchVO = new SearchVO();
		searchVO.setWord(search);
		searchVO.setType(option);
		model.addAttribute("search",searchVO);
		model.addAttribute("page", pageVO);
		return boardlist;
	}

	public List<BoardVO> getBoardCurrListbyBno(BoardVO board, Model model, String option, String search) {

		List<BoardVO> boardlist = new ArrayList<BoardVO>();
		dao = sqlSessionTemplate.getMapper(NoticeDAO.class);
		int curpage = getPageNobyBno(board,option,search);
		boardlist = dao.getList(curpage, rpp, option, '%'+search+'%');

		PageVO page = setPage(curpage,option,'%'+search+'%');
		SearchVO searchVO = new SearchVO();
		searchVO.setWord(search);
		searchVO.setType(option);
		model.addAttribute("search",searchVO);
		model.addAttribute("page", page);
		return boardlist;
		
	}

	public BoardVO updateBoard(BoardVO board, HttpServletRequest request) {

		dao = sqlSessionTemplate.getMapper(NoticeDAO.class);
		if (dao.updateBoard(board)) {
            
			return board;

		};
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
		if(dao.deleteBoard(board)){
			
			System.out.println("삭제 성공");
			
		};
		System.out.println("삭제 실패");

		return board;

	}
	
	public boolean confirmParent(BoardVO board) {
		
		dao = sqlSessionTemplate.getMapper(NoticeDAO.class);
		int ref = dao.getParentConfirm(board);
		if(ref == 0){
			
			return true;
			
		}
		return false;
		
	}

}

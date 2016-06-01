package org.kdea.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.kdea.dao.NoticeDAO;
import org.kdea.vo.BoardVO;
import org.kdea.vo.PageVO;
import org.kdea.vo.SearchVO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

@Service
public class NoticeService {

	final int rpp = 10; // RecordsPerPage server/dao 관련
	final int ppp = 5; // PagesPerPage client 표면적 관련

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	NoticeDAO dao;// 초기화가 먼저됨 없을때..Service 선언되자마자 실행되서;;

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
		System.out.println("구해진 rn"+curRn);
		System.out.println("구해진 currpage"+curpage);
		return curpage;
	}

	public BoardVO insertBoard(BoardVO board, HttpServletRequest request) {

		/*board.setNickname((String) request.getSession().getAttribute("ID"));*/
		board.setNickname("tempuser");
		dao = sqlSessionTemplate.getMapper(NoticeDAO.class);
		if (dao.insertBoard(board)) {

			System.out.println("게시판 저장 성공");
			board.setNum(dao.lastIndex() - 1);
			return board;

		}
		;
		System.out.println("게시판 저장 실패");
		return board;

	}

	public BoardVO replyBoard(BoardVO board, HttpServletRequest request) {
		/*board.setNickname((String) request.getSession().getAttribute("ID"));*/
		board.setNickname("tempuser");
		dao = sqlSessionTemplate.getMapper(NoticeDAO.class);
		if (dao.insertBoard(board)) {
			System.out.println("답글 저장 성공");
			board.setNum(dao.lastIndex() - 1);
			return board;

		}
		;
		System.out.println("답글 저장 실패");
		return board;
	}

	public List<BoardVO> getBoardListbyPage(int page, Model model, String option, String search) {

		List<BoardVO> boardlist = new ArrayList<BoardVO>();
		dao = sqlSessionTemplate.getMapper(NoticeDAO.class);
		boardlist = dao.getList(page, rpp, option, '%'+search+'%');//동적 sql사용해보자~

		PageVO pageVO = setPage(page,option,'%'+search+'%');
		SearchVO searchVO = new SearchVO();
		searchVO.setWord(search);
		searchVO.setType(option);
		model.addAttribute("search",searchVO);
		System.out.println("page로 구해진 페이지 " + pageVO.getCurrpage());
		model.addAttribute("page", pageVO);
		return boardlist;
	}

	public List<BoardVO> getBoardCurrListbyBno(BoardVO board, Model model, String option, String search) {

		List<BoardVO> boardlist = new ArrayList<BoardVO>();
		dao = sqlSessionTemplate.getMapper(NoticeDAO.class);
		int curpage = getPageNobyBno(board,option,search);
		boardlist = dao.getList(curpage, rpp, option, '%'+search+'%');

		// board.setRowno(curRn);
		PageVO page = setPage(curpage,option,'%'+search+'%');
		SearchVO searchVO = new SearchVO();
		searchVO.setWord(search);
		searchVO.setType(option);
		model.addAttribute("search",searchVO);
		System.out.println("num로 구해진 페이지 " + page.getCurrpage());
		model.addAttribute("page", page);
		return boardlist;
	}

	public BoardVO updateBoard(BoardVO board, HttpServletRequest request) {

		dao = sqlSessionTemplate.getMapper(NoticeDAO.class);
		System.out.println(board.getNum());
		System.out.println(board.getTitle());
		System.out.println(board.getContents());
		if (dao.updateBoard(board)) {
            
			System.out.println("게시판 수정 성공");
			return board;

		}
		;
		System.out.println("게시판 수정 실패");
		return board;

	}

	public BoardVO selectBoard(BoardVO board) {

		dao = sqlSessionTemplate.getMapper(NoticeDAO.class);
		board = dao.selectBoard(board);
		System.out.println(board);
		// dest.getModel().put("board",board);//wrong grammer
		return board;

	}
	
	public BoardVO deleteBoard(BoardVO board) {
        
		dao = sqlSessionTemplate.getMapper(NoticeDAO.class);
		if(dao.deleteBoard(board)){
			
			System.out.println("삭제 성공");
			
		};
		System.out.println("삭제 실패");

		// dest.getModel().put("board",board);//wrong grammer
		return board;//삭제된 보드객체 그대로 리턴

	}
	
	public boolean confirmParent(BoardVO board) {
		
		dao = sqlSessionTemplate.getMapper(NoticeDAO.class);
		System.out.println("서비스 부모 확인 번호 "+board.getNum());
		int ref = dao.getParentConfirm(board);
		if(ref == 0){
			
			return true;
			
		}
		return false;
		
	}

}

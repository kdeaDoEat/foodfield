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

	final int rpp = 10; // RecordsPerPage server/dao ����
	final int ppp = 5; // PagesPerPage client ǥ���� ����

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	NoticeDAO dao;// �ʱ�ȭ�� ������ ������..Service ������ڸ��� ����Ǽ�;;

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
		System.out.println("������ rn"+curRn);
		System.out.println("������ currpage"+curpage);
		return curpage;
	}

	public BoardVO insertBoard(BoardVO board, HttpServletRequest request) {

		/*board.setNickname((String) request.getSession().getAttribute("ID"));*/
		board.setNickname("tempuser");
		dao = sqlSessionTemplate.getMapper(NoticeDAO.class);
		if (dao.insertBoard(board)) {

			System.out.println("�Խ��� ���� ����");
			board.setNum(dao.lastIndex() - 1);
			return board;

		}
		;
		System.out.println("�Խ��� ���� ����");
		return board;

	}

	public BoardVO replyBoard(BoardVO board, HttpServletRequest request) {
		/*board.setNickname((String) request.getSession().getAttribute("ID"));*/
		board.setNickname("tempuser");
		dao = sqlSessionTemplate.getMapper(NoticeDAO.class);
		if (dao.insertBoard(board)) {
			System.out.println("��� ���� ����");
			board.setNum(dao.lastIndex() - 1);
			return board;

		}
		;
		System.out.println("��� ���� ����");
		return board;
	}

	public List<BoardVO> getBoardListbyPage(int page, Model model, String option, String search) {

		List<BoardVO> boardlist = new ArrayList<BoardVO>();
		dao = sqlSessionTemplate.getMapper(NoticeDAO.class);
		boardlist = dao.getList(page, rpp, option, '%'+search+'%');//���� sql����غ���~

		PageVO pageVO = setPage(page,option,'%'+search+'%');
		SearchVO searchVO = new SearchVO();
		searchVO.setWord(search);
		searchVO.setType(option);
		model.addAttribute("search",searchVO);
		System.out.println("page�� ������ ������ " + pageVO.getCurrpage());
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
		System.out.println("num�� ������ ������ " + page.getCurrpage());
		model.addAttribute("page", page);
		return boardlist;
	}

	public BoardVO updateBoard(BoardVO board, HttpServletRequest request) {

		dao = sqlSessionTemplate.getMapper(NoticeDAO.class);
		System.out.println(board.getNum());
		System.out.println(board.getTitle());
		System.out.println(board.getContents());
		if (dao.updateBoard(board)) {
            
			System.out.println("�Խ��� ���� ����");
			return board;

		}
		;
		System.out.println("�Խ��� ���� ����");
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
			
			System.out.println("���� ����");
			
		};
		System.out.println("���� ����");

		// dest.getModel().put("board",board);//wrong grammer
		return board;//������ ���尴ü �״�� ����

	}
	
	public boolean confirmParent(BoardVO board) {
		
		dao = sqlSessionTemplate.getMapper(NoticeDAO.class);
		System.out.println("���� �θ� Ȯ�� ��ȣ "+board.getNum());
		int ref = dao.getParentConfirm(board);
		if(ref == 0){
			
			return true;
			
		}
		return false;
		
	}

}

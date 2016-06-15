package org.kdea.qnaboard;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.kdea.vo.BoardVO;
import org.kdea.vo.PageVO;
import org.kdea.vo.SearchVO;
import org.kdea.vo.UserVO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class QnaService {
	
	final int rpp = 5; // RecordsPerPage server/dao 관련
	final int ppp = 3; // PagesPerPage client 표면적 관련
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	QnADAO dao;

	public List<BoardVO> getBoardCurrListbyBno(BoardVO board, Model model, String type, String word) {
		System.out.println("Service :: getBoardCurrListbyBno");
		List<BoardVO> boardlist = new ArrayList<BoardVO>();
		dao = sqlSessionTemplate.getMapper(QnADAO.class);
		int curpage = getPageNobyBno(board,type,word);
		boardlist = dao.getList(curpage, rpp, type, '%'+word+'%');

		// board.setRowno(curRn);
		PageVO page = setPage(curpage,type,'%'+word+'%');
		SearchVO searchVO = new SearchVO();
		searchVO.setWord(word);
		searchVO.setType(type);
		model.addAttribute("search",searchVO);
		model.addAttribute("page", page);
		return boardlist;
	}



	public List<BoardVO> getBoardListbyPage(int page, Model model, String type, String word) {
		System.out.println("Service :: getBoardListbyPage");
		List<BoardVO> boardlist = new ArrayList<BoardVO>();
		dao = sqlSessionTemplate.getMapper(QnADAO.class);
		boardlist = dao.getList(page, rpp, type, '%'+word+'%');//동적 sql사용해보자~

		PageVO pageVO = setPage(page,type,'%'+word+'%');
		SearchVO searchVO = new SearchVO();
		searchVO.setWord(word);
		searchVO.setType(type);
		model.addAttribute("search",searchVO);
		model.addAttribute("page", pageVO);
		return boardlist;
	}
	
	/***************************** [ getBoardCurrListbyBno ] *****************************/ 
	private PageVO setPage(int curpage, String type, String string) {
		System.out.println("Service :: setPage");
		PageVO page = new PageVO();
		page.setCurrpage(curpage);
		page.setTotalpage(getTotalPage(type,string));
		page.setPpp(ppp);
		return page;
	}
	
	private int getTotalPage(String type, String string) {
		System.out.println("Service :: getTotalPage");
		int allpage;
		int allrecords = dao.getAllCount(type, string);
		System.out.println("allrecords" + allrecords);
		if (allrecords % rpp == 0) {

			allpage = allrecords / rpp;
			System.out.println("나머지 0 총 페이지 : "+allpage);
		} else {

			allpage = (int) (((double) (allrecords / rpp)) + 1);
			System.out.println("나머지 있음 총 페이지 : "+allpage);
		}
		System.out.println("Total Page : "+allpage);
		return allpage;
	}



	public int getPageNobyBno(BoardVO board, String type, String word) {
		System.out.println("Service :: getPageNobyBno");
		System.out.println(board.getNum());
		int curRn = dao.getRnbyBno(board , type, word);
		int curpage;
		if (curRn % rpp == 0) {
			curpage = curRn / rpp;
		} else {
			curpage = (int) (((double) (curRn / rpp)) + 1);
		}
		System.out.println("Get rn"+curRn);
		System.out.println("Get currpage"+curpage);
		return curpage;
	}



	/********************* [ Write ] *********************/ 
	public BoardVO insertBoard(BoardVO board, HttpServletRequest request) {
		UserVO user = (UserVO)request.getSession().getAttribute("userInfo");
		board.setNickname(user.getNickname());
		//board.setNickname("admin");
		
		System.out.println("service contetns : "+board.getContents());
		
		dao = sqlSessionTemplate.getMapper(QnADAO.class);
		if (dao.insertBoard(board)) {
			System.out.println("Save Success!!!");
			board.setNum(dao.lastIndex() - 1);
			return board;
		}
		System.out.println("Save Fail!!!");
		return board;
	}

	public BoardVO selectBoard(BoardVO board) {
		dao = sqlSessionTemplate.getMapper(QnADAO.class);
		System.out.println(board.getNum());
		board = dao.selectBoard(board);
		//System.out.println(board);
		return board;
	}
	
	

	/********************* [ Modify ] *********************/ 
	public BoardVO updateBoard(BoardVO board, HttpServletRequest request) {
		System.out.println("Service");
		
		// 
		System.out.println("service contetns : "+board.getContents());
		
		dao = sqlSessionTemplate.getMapper(QnADAO.class);
		if (dao.updateBoard(board)) {
			System.out.println("Update Success!!!");
			return board;
		}
		System.out.println("Update Fail!!!");
		return board;
	}


	/********************* [ delete ] *********************/
	public boolean confirmParent(BoardVO board) {
		dao = sqlSessionTemplate.getMapper(QnADAO.class);
		int ref = dao.getParentConfirm(board);
		if(ref == 0){		
			return true;	
		}
		return false;
	}

	public BoardVO deleteBoard(BoardVO board) {
		dao = sqlSessionTemplate.getMapper(QnADAO.class);
		if(dao.deleteBoard(board)){
			System.out.println("Delete Success!!!");
		};
		System.out.println("Delete Fail!!!");
		// dest.getModel().put("board",board);//wrong grammer
		return board;//삭제된 보드객체 그대로 리턴

	}

}

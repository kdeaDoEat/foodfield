package org.kdea.ranking;

import java.util.ArrayList;
import java.util.List;

import org.kdea.noticeboard.NoticeDAO;
import org.kdea.vo.BoardVO;
import org.kdea.vo.PageVO;
import org.kdea.vo.SearchVO;
import org.kdea.vo.UserVO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class RankingService {

	final int rpp = 10;
	final int ppp = 5;
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	RankingDAO dao;
	
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
	
	public PageVO setPage(int currpage, String option, String search) {

		PageVO page = new PageVO();
		page.setCurrpage(currpage);
		page.setTotalpage(getTotalPage(option, search));
		page.setPpp(ppp);
		return page;
	}
	
	public List<UserVO> getBoardListbyPage(int page, Model model, String option, String search) {

		List<UserVO> userlist = new ArrayList<UserVO>();
		dao = sqlSessionTemplate.getMapper(RankingDAO.class);
		userlist = dao.getList(page, rpp, option, '%' + search + '%');

		PageVO pageVO = setPage(page, option, '%' + search + '%');
		SearchVO searchVO = new SearchVO();
		searchVO.setWord(search);
		searchVO.setType(option);
		model.addAttribute("search", searchVO);
		model.addAttribute("page", pageVO);
		return userlist;
	}

}

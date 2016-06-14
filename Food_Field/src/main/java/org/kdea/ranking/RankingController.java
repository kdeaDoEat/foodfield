package org.kdea.ranking;

import java.util.ArrayList;
import java.util.List;

import org.kdea.vo.PageVO;
import org.kdea.vo.SearchVO;
import org.kdea.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "rank")
public class RankingController {

	@Autowired
	RankingService service;

	@RequestMapping(value = { "/list" }, method = RequestMethod.GET)
	public ModelAndView goList(@ModelAttribute("search") SearchVO search, @ModelAttribute("user") UserVO user,
			@ModelAttribute("page") PageVO page, Model model) {

		List<UserVO> userlist = new ArrayList<UserVO>();

		if (search.getType() == null && page.getCurrpage() == 0) {

			page.setCurrpage(1);

		}

		userlist = service.getBoardListbyPage(page.getCurrpage(), model, search.getType(), search.getWord());

		ModelAndView dest = new ModelAndView("ranking/rankingListTemp", "userlist", userlist);
		return dest;

	}

}

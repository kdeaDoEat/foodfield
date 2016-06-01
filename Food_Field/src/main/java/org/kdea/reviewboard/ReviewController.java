package org.kdea.reviewboard;

import javax.servlet.http.HttpServletRequest;

import org.kdea.vo.BoardVO;
import org.kdea.vo.ListVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value="/")
public class ReviewController {

	@Autowired
	private ReviewService rsvc;
	
	@RequestMapping(value="review", method = RequestMethod.GET)
	public ModelAndView review(HttpServletRequest request){
		int page;
		if(request.getParameter("page")==null)	page=1;
		else	page = Integer.parseInt(request.getParameter("page"));	
		if(page>rsvc.pagecount())	page= rsvc.pagecount();
		if(page<=1) page=1;
		ListVO vo = new ListVO();
		vo.setList(rsvc.getList(page));
		vo.setPage(rsvc.boardPage(page));
		vo.setAllpage(rsvc.pagecount());
		vo.setNowpage(page);
		vo.setType("list");
		return new ModelAndView("review/reviewboard","vo",vo);
	}
	
	@RequestMapping(value="review/write",method=RequestMethod.GET)
	public String write(){
		return "review/write";
	}
	
	@RequestMapping(value="review/searchMap",method=RequestMethod.GET)
	public ModelAndView map(HttpServletRequest request){
		return new ModelAndView("review/reviewmap","type",request.getParameter("search"));
	}
	
	@ResponseBody
	@RequestMapping(value="review/wSubmit",method=RequestMethod.POST)
	public String wSubmit(BoardVO vo){
		System.out.println("come");
		return rsvc.write(vo);
	}
	
	@RequestMapping(value="review/read",method=RequestMethod.GET)
	public ModelAndView read(HttpServletRequest request){
		return new ModelAndView("review/read","vo",rsvc.read(request));
		//return "review/read";
	}
}

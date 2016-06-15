package org.kdea.reviewboard;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.kdea.vo.BoardVO;
import org.kdea.vo.CommentVO;
import org.kdea.vo.ListVO;
import org.kdea.vo.SearchVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

@Controller
@RequestMapping(value="/")
public class ReviewController {

	@Autowired
	private ReviewService rsvc;
	
	/*�۸�� �� ���������̼�*/
	@RequestMapping(value="review", method = RequestMethod.GET)
	public ModelAndView review(HttpServletRequest request){
		int page;
		if(request.getParameter("page")==null)	page=1;
		else	page = Integer.parseInt(request.getParameter("page"));	
		if(page>rsvc.pagecount())	page= rsvc.pagecount();
		if(page<=1) page=1;
		
		ListVO vo = new ListVO();
		if(request.getParameter("recommend")==null){
			vo.setList(rsvc.getList(page));
		}else if(request.getParameter("recommend").equals("high")){
			vo.setList(rsvc.getRecommendList(page));
		}
		vo.setPage(rsvc.boardPage(page));
		vo.setAllpage(rsvc.pagecount());
		vo.setNowpage(page);
		vo.setType("list");
		return new ModelAndView("review/reviewboard","vo",vo);
	}
	
	/*�۾�����*/
	@RequestMapping(value="review/write",method=RequestMethod.GET)
	public String write(){
		return "review/write";
	}
	
	/*�۾���� ��*/
	@RequestMapping(value="review/searchMap",method=RequestMethod.GET)
	public ModelAndView map(HttpServletRequest request){
		return new ModelAndView("review/reviewmap","type",request.getParameter("search"));
	}
	
	/*��۸���Ʈ*/
	@RequestMapping(value="review/comment",method=RequestMethod.GET)
	public ModelAndView comment(HttpServletRequest request){
		return new ModelAndView("review/reviewBoardComment","list",rsvc.commentList(request));
	}
	
	/*�۾���Ϸ�*/
	@ResponseBody
	@RequestMapping(value="review/wSubmit",method=RequestMethod.POST)
	public String wSubmit(BoardVO vo){
		return rsvc.write(vo);
	}
	
	/*��۾���Ϸ�*/
	@ResponseBody
	@RequestMapping(value="review/cSubmit",method=RequestMethod.POST)
	public String cSubmit(CommentVO vo){
		return rsvc.commentWrite(vo);
	}
	
	/*���б�*/
	@RequestMapping(value="review/read",method=RequestMethod.GET)
	public ModelAndView read(BoardVO vo){
		rsvc.updateHit(vo.getNum());
		return new ModelAndView("review/read","vo",rsvc.read(vo));
	}
	
	/*��ۼ���*/
	@ResponseBody
	@RequestMapping(value="review/commentModify", method=RequestMethod.POST)
	public String commentModify(CommentVO vo){
		return rsvc.commentModify(vo);
	}
	
	/*��ۻ���*/
	@ResponseBody
	@RequestMapping(value="review/commentDelete", method=RequestMethod.POST)
	public String commentDelete(CommentVO vo){
		return rsvc.commentDelete(vo);
	}
	
	/*�ۼ�����*/
	@RequestMapping(value="review/modify", method=RequestMethod.GET)
	public ModelAndView modify(BoardVO vo){
		return new ModelAndView("review/modify","vo",rsvc.read(vo));
	}
	
	/*�ۼ����Ϸ�*/
	@ResponseBody
	@RequestMapping(value="review/reviewModify", method=RequestMethod.POST)
	public String reviewModify(BoardVO vo){
		return rsvc.reviewModify(vo);
	}
	
	/*�ۻ���*/
	@ResponseBody
	@RequestMapping(value="review/reviewDelete", method=RequestMethod.POST)
	public String reviewDelete(BoardVO vo){
		return rsvc.reviewDelete(vo);
	}
	
	/*������ε�*/
	@RequestMapping(value="review/uploadPhoto")
	public String uploadPhoto(FileBean fileBean, Model model,HttpSession session){
		return rsvc.uploadPhoto(fileBean,model,session);
	}
	
	/*�� �˻�*/
	@RequestMapping(value="review/search")
	public ModelAndView search(SearchVO vo){
		System.out.println("Ÿ�� : "+vo.getType());
		System.out.println("�ɼ� : "+vo.getWord());
		return new ModelAndView("review/reviewboard","vo",rsvc.getSearchList(vo));
	}
	/*��õ�ϱ�*/
	@ResponseBody
	@RequestMapping(value="review/recommend")
	public String recommend(BoardVO vo){
		return rsvc.recommend(vo);
	}
	
}

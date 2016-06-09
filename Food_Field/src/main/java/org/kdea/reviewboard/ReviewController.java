package org.kdea.reviewboard;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.kdea.vo.BoardVO;
import org.kdea.vo.CommentVO;
import org.kdea.vo.ListVO;
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
	/*글목록 및 페이지네이션*/
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
	/*글쓰기폼*/
	@RequestMapping(value="review/write",method=RequestMethod.GET)
	public String write(){
		return "review/write";
	}
	/*글쓰기용 맵*/
	@RequestMapping(value="review/searchMap",method=RequestMethod.GET)
	public ModelAndView map(HttpServletRequest request){
		return new ModelAndView("review/reviewmap","type",request.getParameter("search"));
	}
	/*댓글리스트*/
	@RequestMapping(value="review/comment",method=RequestMethod.GET)
	public ModelAndView comment(HttpServletRequest request){
		return new ModelAndView("review/reviewBoardComment","list",rsvc.commentList(request));
	}
	/*글쓰기완료*/
	@ResponseBody
	@RequestMapping(value="review/wSubmit",method=RequestMethod.POST)
	public String wSubmit(BoardVO vo){
		return rsvc.write(vo);
	}
	/*댓글쓰기완료*/
	@ResponseBody
	@RequestMapping(value="review/cSubmit",method=RequestMethod.POST)
	public String cSubmit(CommentVO vo){
		return rsvc.commentWrite(vo);
	}
	/*글읽기*/
	@RequestMapping(value="review/read",method=RequestMethod.GET)
	public ModelAndView read(HttpServletRequest request){
		rsvc.updateHit(request);
		return new ModelAndView("review/read","vo",rsvc.read(request));
	}
	/*댓글수정*/
	@ResponseBody
	@RequestMapping(value="review/commentModify", method=RequestMethod.POST)
	public String commentModify(CommentVO vo){
		return rsvc.commentModify(vo);
	}
	/*댓글삭제*/
	@ResponseBody
	@RequestMapping(value="review/commentDelete", method=RequestMethod.POST)
	public String commentDelete(CommentVO vo){
		return rsvc.commentDelete(vo);
	}
	/*글수정폼*/
	@RequestMapping(value="review/modify", method=RequestMethod.GET)
	public ModelAndView modify(HttpServletRequest request){
		return new ModelAndView("review/modify","vo",rsvc.read(request));
	}
	/*글수정완료*/
	@ResponseBody
	@RequestMapping(value="review/reviewModify", method=RequestMethod.POST)
	public String reviewModify(BoardVO vo){
		System.out.println(vo.getContents());
		return rsvc.reviewModify(vo);
	}
	/*글삭제*/
	@ResponseBody
	@RequestMapping(value="review/reviewDelete", method=RequestMethod.POST)
	public String reviewDelete(BoardVO vo){
		return rsvc.reviewDelete(vo);
	}
	
	@RequestMapping(value="review/uploadPhoto")
	public String uploadPhoto(FileBean fileBean, Model model){
		return rsvc.uploadPhoto(fileBean,model);
	}
	
	
	
}

package org.kdea.qnaboard;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.kdea.vo.BoardVO;
import org.kdea.vo.DelConfirmVO;
import org.kdea.vo.PageVO;
import org.kdea.vo.SearchVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class QnaController {
	
	@Autowired
	QnaService service;

	@RequestMapping(value="qna",method=RequestMethod.GET)
	public ModelAndView list(@ModelAttribute("search") SearchVO search, @ModelAttribute("board") BoardVO board, @ModelAttribute("page") PageVO page, Model model){
		System.out.println("Controller :: qna");
		List<BoardVO> boardlist = new ArrayList<BoardVO>();
		if(search.getType()==null&&page.getCurrpage()==0){
			page.setCurrpage(1);
		}
		if(board.getNum()>0){
			boardlist = service.getBoardCurrListbyBno(board,model,search.getType(),search.getWord());	
		}
		if(page.getCurrpage()>0){
			boardlist = service.getBoardListbyPage(page.getCurrpage(),model,search.getType(),search.getWord());	
		}
		return new ModelAndView("qna/qnaboard","boardlist",boardlist);
	}
	
	
	/********************* [ Write ] *********************/ 
	@RequestMapping(value="qnawriteForm",method=RequestMethod.GET)
	public String writeForm(){
		return "qna/qnawrite";
	}
	
	@RequestMapping(value="qnainsert",method=RequestMethod.POST)
	public ModelAndView goInsert(@ModelAttribute("board") BoardVO board,BindingResult result, HttpServletRequest request, Model model){
		board = service.insertBoard(board, request);
		
		BoardVO boardinfo = service.selectBoard(board);
		ModelAndView dest = new ModelAndView("qna/qnaview","board",boardinfo);
		return dest;			
	}
	
	/********************* [ View ] *********************/
	@RequestMapping(value="qnaview",method=RequestMethod.GET)
	public ModelAndView goView(@ModelAttribute("board") BoardVO board, Model model){
		BoardVO boardinfo = service.selectBoard(board);
		ModelAndView dest = new ModelAndView("qna/qnaview","board",boardinfo);
		return dest;					
	}
	
	/********************* [ Modify ] *********************/
	@RequestMapping(value="qnamodifyForm",method=RequestMethod.GET)
	public ModelAndView ModifyForm(@ModelAttribute("board") BoardVO board){
		System.out.println("ModifyForm :: Controller");
		return new ModelAndView("qna/qnamodify");
	}
	
	@RequestMapping(value="qnamodify",method=RequestMethod.POST)
	public ModelAndView goModify(@ModelAttribute("board") BoardVO board,BindingResult result, HttpServletRequest request, Model model){
		System.out.println("Modify  :: Controller");
		System.out.println("Controller Contents : "+board.getContents());
		System.out.println("Controller title : "+board.getTitle());
		
		board = service.updateBoard(board, request);
		
		BoardVO boardinfo = service.selectBoard(board);
		ModelAndView dest = new ModelAndView("qna/qnaview","board",boardinfo);
		return dest;				
	}
	
	/********************* [ delete ] *********************/
	@RequestMapping(value="qnadelConfirm",method=RequestMethod.POST)
	@ResponseBody
	public Object godelConfirm(@ModelAttribute("board") BoardVO board,BindingResult result, HttpServletRequest request, Model model){
		DelConfirmVO delconfirm = new DelConfirmVO();
		delconfirm.setParent(service.confirmParent(board));		
		return delconfirm;					
	}
	
	@RequestMapping(value="qnadel",method=RequestMethod.GET)
	public String del(@ModelAttribute("board") BoardVO board,BindingResult result, HttpServletRequest request, Model model){
		DelConfirmVO delconfirm = new DelConfirmVO();
		delconfirm.setParent(service.confirmParent(board));		
		
        board = service.deleteBoard(board);
        
		int num;
		if(board.getNum()-1<=0){
			num = 1;
		}else{
			num = board.getNum()-1;
		}
		return "redirect:qna?num="+num;					
	}

}

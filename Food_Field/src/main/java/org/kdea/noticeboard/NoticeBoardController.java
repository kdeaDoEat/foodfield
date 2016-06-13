
package org.kdea.noticeboard;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.simple.JSONObject;
import org.kdea.noticeboard.FileValidator;
import org.kdea.noticeboard.NoticeService;
import org.kdea.noticeboard.NoticeValidator;
import org.kdea.vo.BoardVO;
import org.kdea.vo.CommentVO;
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
@RequestMapping(value="notice")
public class NoticeBoardController {
	
	@Autowired
	NoticeService service;
	
    @Autowired
    private FileValidator fileValidator;
	
	@ModelAttribute("board")
	public BoardVO rangeModel(HttpServletRequest request, HttpServletResponse response){
		
		return new BoardVO();
		
	}
	
	@RequestMapping(value="/write",method=RequestMethod.GET)
	public String goWriteForm(){
				
		return "noticeboard/writeFormTemp";
		
	}
	
	@RequestMapping(value="/smart_editor2_inputarea",method=RequestMethod.GET)
	public String goSmartEditor2_InputArea(){
				
		return "noticeboard/smart_editor2_inputarea";
		
	}
	
	@RequestMapping(value="/smart_editor2_inputarea_ie8",method=RequestMethod.GET)
	public String goSmartEditor2_InputArea_ie8(){
				
		return "noticeboard/smart_editor2_inputarea_ie8";
		
	}
	
	@RequestMapping(value="/photo_uploader",method=RequestMethod.GET)
	public String goFileuploader(){
		
		return "noticeboard/photo_uploader";
		
	}
	
	@RequestMapping(value="/callback")
	public String goCallbackHtml(){
		
		return "noticeboard/callback";
		
	}
	
	@RequestMapping(value="/file_uploader")
	public String goFileUploader(HttpServletRequest request, HttpServletResponse response){

		return service.fileUploader(request, response);
		
	}
	
	@RequestMapping(value="/file_uploader_html5")
	@ResponseBody
	public Object goFileUploaderHtml5(HttpServletRequest request) {  
         
		return service.fileUploaderHTML5(request);
	}
	
	@RequestMapping(value="/modform",method=RequestMethod.GET)
	public ModelAndView goWriteForm(@ModelAttribute("board") BoardVO board){
		
		return new ModelAndView("noticeboard/modFormTemp");
		
	}
	
	@RequestMapping(value={"/list"},method=RequestMethod.GET)
	public ModelAndView goList(@ModelAttribute("search") SearchVO search, @ModelAttribute("board") BoardVO board, @ModelAttribute("page") PageVO page, Model model){
		
		List<BoardVO> boardlist = new ArrayList<BoardVO>();
		if(search.getType()==null&&page.getCurrpage()==0){
			
			page.setCurrpage(1);
			
		}
		if(board.getNum()>0){
			
			boardlist = service.getBoardCurrListbyBno(board,model,search.getType(),search.getWord());
			
		}
		else if(page.getCurrpage()>0){
			
			boardlist = service.getBoardListbyPage(page.getCurrpage(),model,search.getType(),search.getWord());
			
		}
		
		ModelAndView dest = new ModelAndView("noticeboard/boardListTemp","boardlist",boardlist);
		return dest;
						
	}
	
	
	@RequestMapping(value="/view",method=RequestMethod.GET)
	public ModelAndView goView(@ModelAttribute("board") BoardVO board, Model model){
		
		BoardVO boardinfo = service.selectBoard(board);
		List<CommentVO> comments = service.selectComments(board);
		boardinfo.setHit(service.increaseHit(board));
		ModelAndView dest = new ModelAndView("noticeboard/boardViewTemp","board",boardinfo);
		dest.addObject("comments",comments);
		return dest;
						
	}
	
	@RequestMapping(value="/insert",method=RequestMethod.POST)
	public ModelAndView goInsert(@ModelAttribute("board") BoardVO board,BindingResult result, HttpServletRequest request, Model model){
		
		new NoticeValidator().validate(board,result);
		if (result.hasErrors()) {			
			return new ModelAndView("noticeboard/writeFormTemp");			
		}
		board = service.insertBoard(board, request);
		BoardVO boardinfo = service.selectBoard(board);
		ModelAndView dest = new ModelAndView("noticeboard/boardViewTemp","board",boardinfo);
		return dest;
						
	}
	
	@RequestMapping(value="/recommend",method=RequestMethod.GET)
	@ResponseBody
	public Object getRecommend(@ModelAttribute("board") BoardVO board){
		
		//new NoticeValidator().validate(comment,result);(ajax 요청임)
		return service.recommend(board);
						
	}
	
	@RequestMapping(value="/reply",method=RequestMethod.POST)
	@ResponseBody
	public Object getReply(@ModelAttribute("comment") CommentVO comment,BindingResult result,HttpServletRequest request, Model model){
		
		//new NoticeValidator().validate(comment,result);(ajax 요청임)
		return service.replyBoard(comment, request);
						
	}
	
	@RequestMapping(value="/modify",method=RequestMethod.POST)
	public ModelAndView goModify(@ModelAttribute("board") BoardVO board,BindingResult result, HttpServletRequest request, Model model){
		
		new NoticeValidator().validate(board,result);
		if (result.hasErrors()) {			
			return new ModelAndView("noticeboard/modFormTemp");			
		}
		board = service.updateBoard(board, request);
		
		BoardVO boardinfo = service.selectBoard(board);
		ModelAndView dest = new ModelAndView("noticeboard/boardViewTemp","board",boardinfo);
		return dest;
						
	}
	
	@RequestMapping(value="/delConfirm",method=RequestMethod.POST)
	@ResponseBody
	public Object godelConfirm(@ModelAttribute("board") BoardVO board,BindingResult result, HttpServletRequest request, Model model){
		
		DelConfirmVO delconfirm = new DelConfirmVO();
		delconfirm.setParent(service.confirmParent(board));		
				
		return delconfirm;
						
	}
	
	@RequestMapping(value="/del",method=RequestMethod.GET)
	public String del(@ModelAttribute("board") BoardVO board, BindingResult result, HttpServletRequest request, Model model){
		
		DelConfirmVO delconfirm = new DelConfirmVO();
		delconfirm.setParent(service.confirmParent(board));		
		int page = service.getPageNobyBno(board, null, null);		
        board = service.deleteBoard(board);
        
        return "redirect:list?currpage="+page;	
						
	}
	
	@RequestMapping(value="/replyModify",method=RequestMethod.POST)
	@ResponseBody
	public Object replyModify(@ModelAttribute("comment") CommentVO comment, HttpServletRequest request){
	    
		System.out.println(request.getParameter("_csrf"));
		return service.modifyReply(comment);
						
	}
	
	@RequestMapping(value="/replyDel",method=RequestMethod.POST)
	@ResponseBody
	public Object replyDel(@ModelAttribute("comment") CommentVO comment){
				
		return service.delReply(comment);
						
	}
	
}
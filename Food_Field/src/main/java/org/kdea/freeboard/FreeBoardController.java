package org.kdea.freeboard;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.kdea.vo.CommentVO;
import org.kdea.vo.FreeBoardVO;
import org.kdea.vo.SearchVO;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/")
public class FreeBoardController {
	
	@Autowired
	private FreeBoradService fbService;

	@RequestMapping(value="free", method = RequestMethod.GET)
	public String list(Model model,HttpServletRequest request){
		String spage= request.getParameter("page");
		int page=Integer.parseInt(spage);
		List<FreeBoardVO> list= fbService.list(page);
		SearchVO svo= new SearchVO();
		svo.setBsearch(false);
		model.addAttribute("isSearch", svo);
		model.addAttribute("fbList", list);
		return "freeboard";
	}
	
	@RequestMapping(value="/write",method=RequestMethod.GET)
	public String WriteAndView(){
		
		return "write";
	}
	

	@RequestMapping(value="/winput",method=RequestMethod.POST)
	public String write(@ModelAttribute("input")  FreeBoardVO  fbVO, 
			Model model,HttpServletRequest request){
	
		FreeBoardVO fb= fbService.write(fbVO);
	
		if(fb.isSuccess()){
			model.addAttribute("w", "success");
			return "redirect:detail?id="+fbVO.getNickname();
		}
		else 
		return "redirect:/freeboard/winput";
	}
	
	@RequestMapping(value="detail", method=RequestMethod.GET)
	public String boardDetail(HttpServletRequest request, Model model){
		if(request.getParameter("id")!=null){
		String id=request.getParameter("id");
		
		FreeBoardVO fb= fbService.read(id);
		model.addAttribute("wvalue", fb);
		return "detail";
		}
		else if(request.getParameter("num")!=null){
			int num=Integer.parseInt(request.getParameter("num"));
			boolean viewsctn=fbService.viewsCtn(num);
			if(viewsctn){
			FreeBoardVO fb= fbService.read(num);
			List<CommentVO> fcomment= fbService.cmtList(num);
			model.addAttribute("cvalue", fcomment);
			model.addAttribute("wvalue", fb);
			return "detail";
			}
		}
		return null;
	}


	
	@RequestMapping(value="modify", method=RequestMethod.POST)
	public String modifyForm(@ModelAttribute("input") FreeBoardVO fb,
			Model model){
		FreeBoardVO fb1= fbService.read(fb.getNum());
		model.addAttribute("modi", fb1);
		return "modify";
		
	}
	@RequestMapping(value="modifycontent", method=RequestMethod.POST)
	public String modify(@ModelAttribute("input") FreeBoardVO fb,
			Model model){
		boolean modiSuccess=fbService.modify(fb);
		if(modiSuccess){
			model.addAttribute("m", "success");
		return "redirect:detail?num="+fb.getNum();
		}else
			return "redirect:modifyForm";
	}

	@RequestMapping(value="beforeDelete", method=RequestMethod.POST)
	@ResponseBody
	public String beforeDelete(@ModelAttribute("input") FreeBoardVO fb
			){
		boolean isParents=fbService.beforeDelete(fb.getNum());
		if(isParents){
			JSONObject jobj= new JSONObject();
			jobj.put("isParents", true);
			
		return jobj.toJSONString();
		}else{
			JSONObject jobj= new JSONObject();
			jobj.put("isParents", false);
			
			return jobj.toJSONString();
			}
	}
	@RequestMapping(value="delete", method=RequestMethod.GET)
	public String delete(@RequestParam("num") int num,
			Model model){
		boolean deleteSuccess=fbService.delete(num);
		if(deleteSuccess){
		return "redirect:free?page=1";
		}else
			return "redirect:detail?num="+num;
	}
	
	//댓글//
	@RequestMapping(value="comment", method=RequestMethod.POST)
	public String cmtWrite(@ModelAttribute("cinput") CommentVO comment,
			 Model model){
		boolean commentSuccess=fbService.cmtWrite(comment);
	
		if(commentSuccess){

		return "redirect:detail?num="+comment.getNum();

		}else
			
			return "";
	}

	@RequestMapping(value="commentmodi", method=RequestMethod.POST
			,produces="application/text; charset=utf8")
	@ResponseBody
	public String BeforeCommentmodi(@RequestParam("number") int num
			){
		//코멘트 수정전에 내용 불러오기
		String jobjStr=fbService.getCommentDetail(num);
	
		return jobjStr;
	}
	@RequestMapping(value="comodify", method=RequestMethod.POST
			,produces="application/text; charset=utf8")
	@ResponseBody
	public String cmtModify(@ModelAttribute("cinput") CommentVO comment){
		
		String jobjStr=fbService.cmtModify(comment);
		
		return jobjStr;
	}
	
	@RequestMapping(value="commentDelete", method=RequestMethod.POST
			,produces="application/text; charset=utf8")
	@ResponseBody
	public String cmtDelete(@RequestParam("num") int num){
		
		String jobjStr=fbService.cmtDelete(num);
		
		return jobjStr;
	}


	@RequestMapping(value="replyForm", method=RequestMethod.GET)
	public String Reply(@RequestParam("num") int num,
			Model model){

		return "redirect:write?num="+num;

	}
	
	@RequestMapping(value="search", method=RequestMethod.GET)
	public String Search(@ModelAttribute("search") SearchVO svo,
			Model model){
		
		System.out.println("검색으로 넘어온것? "+svo.getSearchCategory()+", "+svo.getSearchContent()+", "+svo.getPage());
		List<FreeBoardVO> searchList= fbService.getSearchList(svo);
		svo.setBsearch(true);
		model.addAttribute("fbList",searchList);
		model.addAttribute("isSearch", svo);
		return "freeboard";
	}

}

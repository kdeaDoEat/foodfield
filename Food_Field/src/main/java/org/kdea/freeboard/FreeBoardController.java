package org.kdea.freeboard;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.kdea.vo.FreeboardVO;
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
	public String free(Model model,HttpServletRequest request){
		System.out.println("FREE board controller");
		String spage= request.getParameter("page");
		System.out.println("�쁽�옱�럹�씠吏�: "+spage );
		int page=Integer.parseInt(spage);
		List<FreeboardVO> list= fbService.getFreebdList(page);
		System.out.println("由ъ뒪�듃 �겕湲�: "+list.size());
		SearchVO svo= new SearchVO();
		svo.setBsearch(false);
		model.addAttribute("isSearch", svo);
		model.addAttribute("fbList", list);
		return "freeboard";
	}
	
	@RequestMapping(value="/write",method=RequestMethod.GET)
	public String Writeform(){
		
		return "write";
	}
	

	@RequestMapping(value="/winput",method=RequestMethod.POST)
	public String Write(@ModelAttribute("input")  FreeboardVO  fbVO, 
			Model model,HttpServletRequest request){
	
		FreeboardVO fb= fbService.Input(fbVO);
	
		if(fb.isSuccess()){
			model.addAttribute("w", "success");
			return "redirect:detail?id="+fbVO.getId();
		}
		else 
		return "redirect:/freeboard/winput";
	}
	
	@RequestMapping(value="detail", method=RequestMethod.GET)
	public String boardDetail(HttpServletRequest request, Model model){
		if(request.getParameter("id")!=null){
		String id=request.getParameter("id");
		FreeboardVO fb= fbService.getDetail(id);

		System.out.println("상세내용 가져왔냐 "+fb.getContent());
		System.out.println("상세내용)글번호 :"+fb.getNum() );

		System.out.println("占쌜뱄옙호 :"+fb.getNum() );

		model.addAttribute("wvalue", fb);
		return "detail";
		}
		else if(request.getParameter("num")!=null){
			int num=Integer.parseInt(request.getParameter("num"));
			FreeboardVO fb= fbService.getDetail(num);
			List<FreeboardVO> fcomment= fbService.getCommentList(num);
			model.addAttribute("cvalue", fcomment);
			model.addAttribute("wvalue", fb);
			return "detail";
		}
		return null;
	}
	@RequestMapping(value="comment", method=RequestMethod.POST)
	public String commentContent(@ModelAttribute("input") FreeboardVO fb,
			 Model model){
		System.out.println("而⑦듃濡ㅻ윭)湲�踰덊샇1: "+fb.getRef());
		boolean commentSuccess=fbService.wcomment(fb);
	
		if(commentSuccess){

			System.out.println("컨트롤러)글번호2: "+fb.getRef());
		return "redirect:detail?num="+fb.getRef();

		}else
			
			return "";
	}
	
	@RequestMapping(value="modify", method=RequestMethod.POST)
	public String modifyForm(@ModelAttribute("input") FreeboardVO fb,
			Model model){
		FreeboardVO fb1= fbService.getDetail(fb.getNum());
		model.addAttribute("modi", fb1);
		return "modify";
		
	}
	@RequestMapping(value="modifycontent", method=RequestMethod.POST)
	public String modifyContent(@ModelAttribute("input") FreeboardVO fb,
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
	public String Beforedelte(@ModelAttribute("input") FreeboardVO fb
			){
		System.out.println("before delete number: "+fb.getNum());
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
	@RequestMapping(value="commentmodi", method=RequestMethod.POST
			,produces="application/text; charset=utf8")
	@ResponseBody
	public String BeforeCommentmodi(@RequestParam("number") int num
			){
		System.out.println("�닔�젙�궡�슜 遺덈윭�삤湲� �쐞�븳 湲�踰덊샇:  "+num);
		String jobjStr=fbService.getCommentDetail(num);
		
		return jobjStr;
	}
	@RequestMapping(value="comodify", method=RequestMethod.POST
			,produces="application/text; charset=utf8")
	@ResponseBody
	public String Commentmodi(@ModelAttribute("input") FreeboardVO fb){
		System.out.println(fb.getNum()+","+fb.getContent());
		String jobjStr=fbService.ComodiSuceess(fb);
		
		return jobjStr;
	}
	
	@RequestMapping(value="commentDelete", method=RequestMethod.POST
			,produces="application/text; charset=utf8")
	@ResponseBody
	public String CommentDel(@RequestParam("num") int num){
		System.out.println("�궘�젣�븷 肄붾찘�듃 踰덊샇: "+num);
		
		String jobjStr=fbService.CoDelSuceess(num);
		
		return jobjStr;
	}
	
	@RequestMapping(value="delete", method=RequestMethod.GET)
	public String delte(@RequestParam("num") int num,
			Model model){
		System.out.println("delete�븷 踰덊샇: "+num);
		boolean deleteSuccess=fbService.delete(num);
		if(deleteSuccess){
		return "redirect:free?page=1";
		}else
			return "redirect:detail?num="+num;
	}
	@RequestMapping(value="replyForm", method=RequestMethod.GET)
	public String Reply(@RequestParam("num") int num,
			Model model){

		System.out.println("답글달 상위 번호: "+num);
		return "redirect:write?num="+num;

	}
	
	@RequestMapping(value="search", method=RequestMethod.GET)
	public String Search(@ModelAttribute("search") SearchVO svo,
			Model model){
		
		System.out.println("媛믩뱾�씠 �젣��濡� �꽆�뼱�솕�뒗媛�? "+svo.getSearchCategory()+", "+svo.getSearchContent()+", "+svo.getPage());
		List<FreeboardVO> searchList= fbService.getSearchList(svo);
		svo.setBsearch(true);
		model.addAttribute("fbList",searchList);
		model.addAttribute("isSearch", svo);
		return "freeboard";
	}

}

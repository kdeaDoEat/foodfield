package org.kdea.freeboard;

import java.io.*;
import java.util.*;

import javax.servlet.http.*;

import org.json.simple.JSONObject;
import org.kdea.vo.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@Controller
@RequestMapping("/")
public class FreeBoardController {
	
	@Autowired
	private FreeBoradService fbService;

	@RequestMapping(value="/free", method = RequestMethod.GET)
	public String free(Model model,HttpServletRequest request){
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

		String jobjStr=fbService.getCommentDetail(num);

		return jobjStr;
	}
	@RequestMapping(value="comodify", method=RequestMethod.POST
			,produces="application/text; charset=utf8")
	@ResponseBody
	String cmtModify(@ModelAttribute("cinput") CommentVO comment){
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

	@RequestMapping(value="delete", method=RequestMethod.GET)
	public String delte(@RequestParam("num") int num,
			Model model){
		boolean deleteSuccess=fbService.delete(num);
		if(deleteSuccess){
		return "redirect:free?page=1";
		}else
			return "redirect:detail?num="+num;
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
	
	@RequestMapping(value="uploadPhoto",method = RequestMethod.POST)
	  public void communityImageUpload
	  (HttpServletRequest request, HttpServletResponse response,
		@RequestParam MultipartFile upload)
	{
		System.out.println("여기로 넘어오긴 하냐?");
        OutputStream out = null;
        PrintWriter printWriter = null;
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
 
        try{
 
            String fileName = upload.getOriginalFilename();
            byte[] bytes = upload.getBytes();
            String uploadPath = "C:/test/upload/" + fileName;//저장경로
 
            out = new FileOutputStream(new File(uploadPath));
            out.write(bytes);
            String callback = request.getParameter("CKEditorFuncNum");
 
            printWriter = response.getWriter();
            String fileUrl = "C:/test/upload/" + fileName;//url경로
 
            printWriter.println("<script type='text/javascript'>window.parent.CKEDITOR.tools.callFunction("
                    + callback
                    + ",'"
                    + fileUrl
                    + "','이미지를 업로드 하였습니다.'"
                    + ")</script>");
            printWriter.flush();
 
        }catch(IOException e){
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (printWriter != null) {
                    printWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
 
        return;
    }


}

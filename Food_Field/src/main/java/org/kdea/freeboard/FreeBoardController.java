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
import org.springframework.web.servlet.ModelAndView;


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
		for(int i=0;i<list.size();i++){
			if(list.get(i).getRef()!=0){
				String title=list.get(i).getTitle().replaceFirst("ㄴ","<img src='resources/images/re.gif'/>");
				list.get(i).setTitle(title);
			}
		}
		
		SearchVO svo= new SearchVO();
		svo.setBsearch(false);
		model.addAttribute("isSearch", svo);
		model.addAttribute("fbList", list);
		return "free/freeboard";
	}
	
	@RequestMapping(value="/write",method=RequestMethod.GET)
	public String WriteAndView(){
		return "write";
	}
	

	@RequestMapping(value="/winput",method=RequestMethod.POST)
	public String write(FreeBoardVO  fbVO, 
			HttpSession session){
		
		FreeBoardVO fb= fbService.write(fbVO);
	
		if(fb.isSuccess()){
			session.setAttribute("w", "success");
			return "redirect:detail?id="+fbVO.getNickname();
		}
		else 
		return "redirect:/freeboard/winput";
	}
	
	@RequestMapping(value="recommend",method=RequestMethod.POST)
	@ResponseBody
	public String recommend(@RequestParam("num")int num,
			@RequestParam("nickname") String nickname)
	{
		//System.out.println("추천이 달리는 글번호: "+num+", 글쓰는 사람: "+nickname);
		boolean confirmrecommendCtn=fbService.confirmrecommendCtn(num, nickname);
		//해당 글번호에 이 사용자가 글번호를 누른적이 있는가 확인
		if(confirmrecommendCtn){
	
			JSONObject jobj= new JSONObject();
			jobj.put("recommendsuc", false);
			String jsonStr=jobj.toJSONString();
			//System.out.println("글추천이 되어있습니다."+jsonStr);
			return jsonStr; 
		}
		
		String recommendStr=fbService.recommend(num,nickname);
		return recommendStr;
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

	@RequestMapping(value="modify", method=RequestMethod.GET)
	public String modifyForm(@RequestParam("num") int num,
			Model model){
		FreeBoardVO fb1= fbService.read(num);
		model.addAttribute("modi", fb1);
	
		return "modify";
		
	}
	@RequestMapping(value="modifycontent", method=RequestMethod.POST)
	public String modify(@ModelAttribute("input") FreeBoardVO fb,
			HttpSession session){
		boolean modiSuccess=fbService.modify(fb);
		if(modiSuccess){
			session.setAttribute("m", "success");
		return "redirect:detail?num="+fb.getNum();
		}else
			return "redirect:modifyForm";
	}

	@RequestMapping(value="beforeDelete", method=RequestMethod.POST)
	@ResponseBody
	public String beforeDelete(FreeBoardVO fb){
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

	//댓글쓰기//
	@RequestMapping(value="comment", method=RequestMethod.POST)
	@ResponseBody
	public String cmtWrite(@ModelAttribute("cinput") CommentVO comment,
			 Model model){
		//System.out.println("코멘트 컨드롤로러 옴,닉네임: "+comment.getNickname());
		boolean commentSuccess=fbService.cmtWrite(comment);
	
		if(commentSuccess){

			CommentVO cmt= fbService.getCommentDetail(comment.getNickname());
		
			JSONObject jobj= new JSONObject();
			jobj.put("commentsuc", "success");
			jobj.put("cnum", cmt.getCnum());
		return jobj.toJSONString();

		}else
			
			return "";
	}
	//댓글읽기//
	@RequestMapping(value="commentList", method=RequestMethod.POST)
	@ResponseBody
	public String commentRead(@RequestParam("cnum") int cnum,
			 Model model){
			//System.out.println("코멘트 읽기 컨드롤로러 옴");
			String cmt= fbService.getCommentDetail(cnum);
			
		return cmt;

	}
	@RequestMapping(value="aftercomment",method=RequestMethod.GET)
	public ModelAndView comment(@RequestParam("num") int num){
		//System.out.println("코멘트 로드하기 옴");
		return new ModelAndView("free/freeBoardComment","list",fbService.commentList(num));
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
	public String delete(@RequestParam("num") int num,
			HttpSession session){
		boolean deleterecommend=fbService.haverecommend(num);
		boolean deleteComment=fbService.haveComment(num);
		//System.out.println("추천수삭제: "+deleterecommend+",코멘트삭제: "+deleteComment);
		boolean deleteSuccess=false;
		if(deleterecommend&&deleteComment){
		 deleteSuccess=fbService.delete(num);
		}
		if(deleteSuccess){
			session.setAttribute("deletesuc","true");
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

		//System.out.println("검색으로 넘어온것? "+svo.getSearchCategory()+", "+svo.getSearchContent()+", "+svo.getPage());
		List<FreeBoardVO> searchList= fbService.getSearchList(svo);
		
		for(int i=0;i<searchList.size();i++){
			if(searchList.get(i).getRef()!=0){
				String title=searchList.get(i).getTitle().replaceFirst("ㄴ","<img src='resources/images/re.gif'/>");
				searchList.get(i).setTitle(title);
			}
		}
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
		
        OutputStream out = null;
        PrintWriter printWriter = null;
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
 
        try{
 
            String fileName = upload.getOriginalFilename();
            byte[] bytes = upload.getBytes();
            
            String uploadPath = "C:/img/" + fileName;//저장경로
 
            out = new FileOutputStream(new File(uploadPath));
            out.write(bytes);
            String callback = request.getParameter("CKEditorFuncNum");
 
            printWriter = response.getWriter();
            String fileUrl = "http://localhost:8088/img/" + fileName;//url경로
 
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

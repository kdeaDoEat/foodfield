
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
import org.kdea.service.FileValidator;
import org.kdea.service.NoticeService;
import org.kdea.service.NoticeValidator;
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
	public BoardVO rangeModel(HttpServletRequest request){
		
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		
		String return1="";	
		String return2="";
		String return3="";

		String name = "";
		if (ServletFileUpload.isMultipartContent(request)){
			ServletFileUpload uploadHandler = new ServletFileUpload(new DiskFileItemFactory());
			uploadHandler.setHeaderEncoding("UTF-8");
			List<FileItem> items = null;
			try {
				items = uploadHandler.parseRequest(request);
				for (FileItem item : items) {
					if(item.getFieldName().equals("callback")) {
						return1 = item.getString("UTF-8");
					} else if(item.getFieldName().equals("callback_func")) {
						return2 = "?callback_func="+item.getString("UTF-8");
					} else if(item.getFieldName().equals("Filedata")) {
						if(item.getSize() > 0) {
							
			                // 기존 상단 코드를 막고 하단코드를 이용
			                name = item.getName().substring(item.getName().lastIndexOf(File.separator)+1);
							String filename_ext = name.substring(name.lastIndexOf(".")+1);
							filename_ext = filename_ext.toLowerCase();
						   	String[] allow_file = {"jpg","png","bmp","gif"};
						   	int cnt = 0;
						   	for(int i=0; i<allow_file.length; i++) {
						   		if(filename_ext.equals(allow_file[i])){
						   			cnt++;
						   		}
						   	}
						   	if(cnt == 0) {
						   		return3 = "&errstr="+name;
						   	} else {
						   		
						   		//파일 기본경로
					    		String dftFilePath = request.getSession().getServletContext().getRealPath("/");
					    		//파일 기본경로 _ 상세경로
					    		String filePath = dftFilePath + "editor" + File.separator +"upload" + File.separator;
					    		
					    		File file = null;
					    		file = new File(filePath);
					    		if(!file.exists()) {
					    			file.mkdirs();
					    		}
					    		
					    		String realFileNm = "";
					    		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
								String today= formatter.format(new java.util.Date());
								realFileNm = today+UUID.randomUUID().toString() + name.substring(name.lastIndexOf("."));
								
								String rlFileNm = filePath + realFileNm;
								///////////////// 서버에 파일쓰기 ///////////////// 
								InputStream is = item.getInputStream();
								OutputStream os=new FileOutputStream(rlFileNm);
								int numRead;
								byte b[] = new byte[(int)item.getSize()];
								while((numRead = is.read(b,0,b.length)) != -1){
									os.write(b,0,numRead);
								}
								if(is != null) {
									is.close();
								}
								os.flush();
								os.close();
								///////////////// 서버에 파일쓰기 /////////////////
					    		
					    		return3 += "&bNewLine=true";
					    		return3 += "&sFileName="+ name;
					    		return3 += "&sFileURL=/editor/upload/"+realFileNm;
						   	}
						}else {
							  return3 += "&errstr=error";
						}
					}
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return "redirect:"+return1+return2+return3;
		
	}
	
	@RequestMapping(value="/file_uploader_html5")
	@ResponseBody
	public Object goFileUploaderHtml5(HttpServletRequest request) {  
         
		String sFileInfo = "";
		String filename = request.getHeader("file-name");
		String filename_ext = filename.substring(filename.lastIndexOf(".")+1);
		filename_ext = filename_ext.toLowerCase();
			
		String[] allow_file = {"jpg","png","bmp","gif"};

		int cnt = 0;
		for(int i=0; i<allow_file.length; i++) {
			if(filename_ext.equals(allow_file[i])){
				cnt++;
			}
		}

		if(cnt == 0) {
			System.out.println("NOTALLOW_"+filename);
			return "NOTALLOW_"+filename;
		} else {
			
		String dftFilePath = request.getSession().getServletContext().getRealPath("/");

		String filePath = dftFilePath + "resources" + File.separator +"smart_editor2" + File.separator +"sample" + File.separator + "photo_uploader" + File.separator + "multiupload" + File.separator;
		System.out.println("uploadhtml5 파일 filePath" + filePath);
		File file = new File(filePath);
		if(!file.exists()) {
			file.mkdirs();
		}
		String realFileNm = "";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String today= formatter.format(new java.util.Date());
		realFileNm = today+UUID.randomUUID().toString() + filename.substring(filename.lastIndexOf("."));
		String rlFileNm = filePath + realFileNm;

		try{
		InputStream is = request.getInputStream();
		OutputStream os=new FileOutputStream(rlFileNm);
		int numRead;
		byte b[] = new byte[Integer.parseInt(request.getHeader("file-size"))];
		while((numRead = is.read(b,0,b.length)) != -1){
			os.write(b,0,numRead);
		}
		if(is != null) {
			is.close();
		}
		os.flush();
		os.close();
		} catch(Exception e){
			e.printStackTrace();
		}

		sFileInfo += "&bNewLine=true";
		sFileInfo += "&sFileName="+ filename;;
		sFileInfo += "&sFileURL="+"resources/smart_editor2/sample/photo_uploader/multiupload/"+realFileNm;
		System.out.println(sFileInfo);
		}
		return sFileInfo;
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
		boardinfo.setView(service.increaseHit(board));
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
		
		//new NoticeValidator().validate(comment,result);(ajax 요청 상태)
		return service.recommend(board);
						
	}
	
	@RequestMapping(value="/reply",method=RequestMethod.POST)
	@ResponseBody
	public Object getReply(@ModelAttribute("comment") CommentVO comment,BindingResult result,HttpServletRequest request, Model model){
		
		//new NoticeValidator().validate(comment,result);(ajax 요청 상태)
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
	public String del(@ModelAttribute("board") BoardVO board,BindingResult result, HttpServletRequest request, Model model){
		
		DelConfirmVO delconfirm = new DelConfirmVO();
		delconfirm.setParent(service.confirmParent(board));		
		
        board = service.deleteBoard(board);
        
		int boardno;
		if(board.getNum()-1<=0){
			
			boardno = 1;
		}else{
			
			boardno = board.getNum()-1;
		}
		return "redirect:list?num="+boardno;		
						
	}
	
	@RequestMapping(value="/showViewFormat",method=RequestMethod.GET)
	public ModelAndView goViewFormat(@ModelAttribute("board") BoardVO board, Model model){
		
		ModelAndView dest = new ModelAndView("noticeboard/boardViewFormat");
		return dest;
						
	}
	
}
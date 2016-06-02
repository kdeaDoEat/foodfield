
package org.kdea.noticeboard;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.kdea.service.FileValidator;
import org.kdea.service.NoticeService;
import org.kdea.service.NoticeValidator;
import org.kdea.vo.BoardVO;
import org.kdea.vo.DelConfirmVO;
import org.kdea.vo.PageVO;
import org.kdea.vo.SearchVO;
import org.kdea.vo.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
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
		//변경 title 태그에는 원본 파일명을 넣어주어야 하므로
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
							//String name = item.getName().substring(item.getName().lastIndexOf(File.separator)+1);
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
			                                // img 태그의 title 옵션에 들어갈 원본파일명
					    		return3 += "&sFileName="+ name;
					    		return3 += "&sFileURL=/editor/upload/"+realFileNm;
						   	}
						}else {
							  return3 += "&errstr=error";
						}
					}
				}
				/*response.sendRedirect(return1+return2+return3);*/
				
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
         
		//파일정보
		String sFileInfo = "";
		//파일명을 받는다 - 일반 원본파일명
		String filename = request.getHeader("file-name");
		//파일 확장자
		String filename_ext = filename.substring(filename.lastIndexOf(".")+1);
		//확장자를소문자로 변경
		filename_ext = filename_ext.toLowerCase();
			
		//이미지 검증 배열변수
		String[] allow_file = {"jpg","png","bmp","gif"};

		//돌리면서 확장자가 이미지인지 
		int cnt = 0;
		for(int i=0; i<allow_file.length; i++) {
			if(filename_ext.equals(allow_file[i])){
				cnt++;
			}
		}

		//이미지가 아님
		if(cnt == 0) {
			System.out.println("NOTALLOW_"+filename);
			return "NOTALLOW_"+filename;
		} else {
		//이미지이므로 신규 파일로 디렉토리 설정 및 업로드	
		//파일 기본경로
		String dftFilePath = request.getSession().getServletContext().getRealPath("/");
		//파일 기본경로 _ 상세경로
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
		///////////////// 서버에 파일쓰기 ///////////////// 
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
		///////////////// 서버에 파일쓰기 /////////////////

		// 정보 출력
		sFileInfo += "&bNewLine=true";
		//sFileInfo += "&sFileName="+ realFileNm;;
		// img 태그의 title 속성을 원본파일명으로 적용시켜주기 위함
		sFileInfo += "&sFileName="+ filename;;
		sFileInfo += "&sFileURL="+"resources/smart_editor2/sample/photo_uploader/multiupload/"+realFileNm;
		System.out.println(sFileInfo);
		}
		return sFileInfo;
	}
	
	@RequestMapping(value="/modform",method=RequestMethod.GET)
	public ModelAndView goWriteForm(@ModelAttribute("board") BoardVO board){
                                    /*모델에 자동 저장이미 楹~*/				
		return new ModelAndView("noticeboard/modFormTemp");
		
	}
	
	@RequestMapping(value={"/list"},method=RequestMethod.GET)
	public ModelAndView goList(@ModelAttribute("search") SearchVO search, @ModelAttribute("board") BoardVO board, @ModelAttribute("page") PageVO page, Model model){
		
		List<BoardVO> boardlist = new ArrayList<BoardVO>();
		  /*search객체 자체는 null이 아님*/
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
		ModelAndView dest = new ModelAndView("noticeboard/boardViewTemp","board",boardinfo);
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
	
	@RequestMapping(value="/reply",method=RequestMethod.POST)
	public ModelAndView goReply(@ModelAttribute("board") BoardVO board,BindingResult result, HttpServletRequest request, Model model){
		
		new NoticeValidator().validate(board,result);
		if (result.hasErrors()) {
			board = service.selectBoard(board);
			return new ModelAndView("noticeboard/boardView","board",board);			
		}
		board = service.replyBoard(board, request);
		BoardVO boardinfo = service.selectBoard(board);
		ModelAndView dest = new ModelAndView("noticeboard/boardViewTemp","board",boardinfo);
		return dest;
						
	}
	
	@RequestMapping(value="/modify",method=RequestMethod.POST)
	public ModelAndView goModify(@ModelAttribute("board") BoardVO board,BindingResult result, HttpServletRequest request, Model model){
		
		new NoticeValidator().validate(board,result);
		if (result.hasErrors()) {			
			return new ModelAndView("noticeboard/modFormTemp");			
		}
		//나중에 시간나면 true로 바꾸자~
		board = service.updateBoard(board, request);
		
		BoardVO boardinfo = service.selectBoard(board);
		ModelAndView dest = new ModelAndView("noticeboard/boardViewTemp","board",boardinfo);
		return dest;
						
	}
	
	@RequestMapping(value="/delConfirm",method=RequestMethod.POST)
	@ResponseBody
	public Object godelConfirm(@ModelAttribute("board") BoardVO board,BindingResult result, HttpServletRequest request, Model model){
		
		//나중에 시간나면 true로 바꾸자~
		DelConfirmVO delconfirm = new DelConfirmVO();
		delconfirm.setParent(service.confirmParent(board));		
				
		return delconfirm;
						
	}
	
	@RequestMapping(value="/del",method=RequestMethod.GET)
	public String del(@ModelAttribute("board") BoardVO board,BindingResult result, HttpServletRequest request, Model model){
		
		//나중에 시간나면 true로 바꾸자~
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
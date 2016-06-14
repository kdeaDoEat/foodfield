package org.kdea.message;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.kdea.vo.MessageListVO;
import org.kdea.vo.MessageVO;
import org.kdea.vo.SearchVO;
import org.kdea.vo.UserVO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate; // 설정파일에 빈으로 등록되었기 때문에 생성자나 Setter 없이 자동으로 주입

	public int messageCount(HttpSession session) {
		MessageDAO dao = sqlSessionTemplate.getMapper(MessageDAO.class);
		UserVO vo = (UserVO)session.getAttribute("userInfo");
		return dao.getMessageCount(vo);
	}

	public MessageListVO getMessageList(HttpSession session, HttpServletRequest request) {
		MessageDAO dao = sqlSessionTemplate.getMapper(MessageDAO.class);
		UserVO vo = (UserVO)session.getAttribute("userInfo");
		int fullPage = dao.getListpage(vo);
		int page = Integer.parseInt(request.getParameter("page"));
		if(page>fullPage) page = fullPage;
		else if(page<1) page = 1;
		MessageVO mvo = new MessageVO();
		mvo.setReceiver(vo.getNickname());
		mvo.setPage(page);
		
		MessageListVO mlvo = new MessageListVO();
		mlvo.setList(dao.getMessageList(mvo));
		mlvo.setAllpage(fullPage);
		mlvo.setNowpage(page);
		
		List<Integer> pageList = new ArrayList<Integer>();
		if(page==1 || fullPage==0){
			pageList.add(page);
		}else if(page==2){
			pageList.add(page-1);
			pageList.add(page);
		}else if(page==fullPage && page>3){
			pageList.add(page-2);
			pageList.add(page-1);
			pageList.add(page);
		}else{
			pageList.add(page-1);
			pageList.add(page);
			pageList.add(page+1);
		}
			
		mlvo.setPage(pageList);
		mlvo.setType("list");
		
		return mlvo;
	}
	
	public MessageListVO getSearchMessageList(HttpSession session, SearchVO vo) {
		MessageDAO dao = sqlSessionTemplate.getMapper(MessageDAO.class);
		UserVO uvo = (UserVO)session.getAttribute("userInfo");
		String id = uvo.getNickname();
		vo.setReceiver(id);
		int fullPage = dao.getSearchListpage(vo);
		int page = vo.getPage();
		if(page>fullPage) page = fullPage;
		else if(page<1) page = 1;
		
		MessageListVO mlvo = new MessageListVO();
		mlvo.setList(dao.getSearchList(vo));
		mlvo.setAllpage(fullPage);
		mlvo.setNowpage(page);
		
		List<Integer> pageList = new ArrayList<Integer>();
		if(page==1 || fullPage==0){
			pageList.add(page);
		}else if(page==2){
			pageList.add(page-1);
			pageList.add(page);
		}else if(page==fullPage && page>3){
			pageList.add(page-2);
			pageList.add(page-1);
			pageList.add(page);
		}else{
			pageList.add(page-1);
			pageList.add(page);
			pageList.add(page+1);
		}
		
		mlvo.setPage(pageList);
		mlvo.setType("search");
		
		return mlvo;
	}

	public List<UserVO> searchMember(UserVO vo) {
		MessageDAO dao = sqlSessionTemplate.getMapper(MessageDAO.class);
		List<UserVO> luvo = dao.getMemberInfo(vo);
		if(luvo.size() == 0){
			UserVO uvo = new UserVO();
			uvo.setEmail("");
			uvo.setNickname("검색결과 없음");
			uvo.setGender("");
			luvo.add(uvo);
		}
		return luvo;
	}

	public String sendMsg(MessageVO vo) {
		MessageDAO dao = sqlSessionTemplate.getMapper(MessageDAO.class);
		JSONObject jobj = new JSONObject();
		if(dao.sendMsg(vo)>0){
			jobj.put("ok", true);
		}else{
			jobj.put("ok", false);
		}
		return jobj.toJSONString();
	}

	public MessageVO getMessageContents(MessageVO vo) {
		MessageDAO dao = sqlSessionTemplate.getMapper(MessageDAO.class);
		dao.readOk(vo);
		return dao.getMessageContents(vo);
	}

	public MessageListVO getsendList(HttpSession session, HttpServletRequest request) {
		MessageDAO dao = sqlSessionTemplate.getMapper(MessageDAO.class);
		UserVO vo = (UserVO)session.getAttribute("userInfo");
		int fullPage = dao.getsendListpage(vo);
		int page = Integer.parseInt(request.getParameter("page"));
		if(page>fullPage) page = fullPage;
		else if(page<1) page = 1;
		MessageVO mvo = new MessageVO();
		mvo.setSender(vo.getNickname());
		mvo.setPage(page);
		
		
		MessageListVO mlvo = new MessageListVO();
		mlvo.setList(dao.getsendList(mvo));
		mlvo.setAllpage(fullPage);
		mlvo.setNowpage(page);
		
		List<Integer> pageList = new ArrayList<Integer>();
		if(page==1 || fullPage==0){
			pageList.add(page);
		}else if(page==2){
			pageList.add(page-1);
			pageList.add(page);
		}else if(page==fullPage && page>3){
			pageList.add(page-2);
			pageList.add(page-1);
			pageList.add(page);
		}else{
			pageList.add(page-1);
			pageList.add(page);
			pageList.add(page+1);
		}
		
		mlvo.setPage(pageList);
		mlvo.setType("list");
		
		return mlvo;
	}
	
	public MessageListVO getsendSearchList(HttpSession session, SearchVO vo) {
		MessageDAO dao = sqlSessionTemplate.getMapper(MessageDAO.class);
		UserVO uvo = (UserVO)session.getAttribute("userInfo");
		String id = uvo.getNickname();
		vo.setSender(id);
		
		int fullPage = dao.getSendSearchListpage(vo);
		int page = vo.getPage();
		if(page>fullPage) page = fullPage;
		else if(page<1) page = 1;
		
		MessageListVO mlvo = new MessageListVO();
		mlvo.setList(dao.getSendSearchList(vo));
		mlvo.setAllpage(fullPage);
		mlvo.setNowpage(page);
		
		List<Integer> pageList = new ArrayList<Integer>();
		if(page==1 || fullPage==0){
			pageList.add(page);
		}else if(page==2){
			pageList.add(page-1);
			pageList.add(page);
		}else if(page==fullPage && page>3){
			pageList.add(page-2);
			pageList.add(page-1);
			pageList.add(page);
		}else{
			pageList.add(page-1);
			pageList.add(page);
			pageList.add(page+1);
		}
		
		mlvo.setPage(pageList);
		mlvo.setType("search");
		
		return mlvo;
	}

	public String deleteMessage(MessageVO vo) {
		MessageDAO dao = sqlSessionTemplate.getMapper(MessageDAO.class);
		JSONObject jobj = new JSONObject();
		if(dao.deleteMessage(vo)>0){
			jobj.put("ok", true);
		}else{
			jobj.put("ok", false);
		}
		return jobj.toJSONString();
	}
	

}

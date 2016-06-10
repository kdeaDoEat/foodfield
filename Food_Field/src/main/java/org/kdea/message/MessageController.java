package org.kdea.message;

import javax.servlet.http.HttpSession;

import org.kdea.vo.MessageVO;
import org.kdea.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value="/message")
public class MessageController {
	
	@Autowired
	private MessageService msvc;
	
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public ModelAndView messageList(HttpSession session){
		return new ModelAndView("message/message","vo",msvc.getMessageList(session));
	}
	@RequestMapping(value="/write")
	public String write(){
		return "message/messageWrite";
	}
	@RequestMapping(value="/count")
	public ModelAndView messageCount(HttpSession session){
		return new ModelAndView("message/messageIcon","messageCount",msvc.messageCount(session));
	}
	
	@RequestMapping(value="/write/search")
	public ModelAndView searchModal(UserVO vo){
		return new ModelAndView("message/searchModal","vo",msvc.searchMember(vo));
	}
	
	@ResponseBody
	@RequestMapping(value="/sendMsg")
	public String sendMsg(MessageVO vo){
		return msvc.sendMsg(vo);
	}
	
	@RequestMapping(value="/read")
	public ModelAndView read(MessageVO vo){
		return new ModelAndView("message/messageRead","vo",msvc.getMessageContents(vo));
	}
}

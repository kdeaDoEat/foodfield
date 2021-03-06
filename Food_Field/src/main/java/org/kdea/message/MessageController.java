package org.kdea.message;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.kdea.vo.MessageVO;
import org.kdea.vo.SearchVO;
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
	public ModelAndView messageList(HttpSession session, HttpServletRequest request){
		return new ModelAndView("message/message","vo",msvc.getMessageList(session,request));
	}
	
	@RequestMapping(value="/mSearch",method=RequestMethod.POST)
	public ModelAndView searchMessage(HttpSession session, SearchVO vo){
		return new ModelAndView("message/message","vo",msvc.getSearchMessageList(session, vo));
	}
	@RequestMapping(value="/write",method=RequestMethod.GET)
	public ModelAndView write(MessageVO vo){
		return new ModelAndView("message/messageWrite","receiver",vo.getReceiver());
	}
	@RequestMapping(value="/count",method=RequestMethod.GET)
	public ModelAndView messageCount(HttpSession session){
		return new ModelAndView("message/messageIcon","messageCount",msvc.messageCount(session));
	}
	
	@RequestMapping(value="/write/search",method=RequestMethod.GET)
	public ModelAndView searchModal(UserVO vo){
		return new ModelAndView("message/searchModal","vo",msvc.searchMember(vo));
	}
	
	@ResponseBody
	@RequestMapping(value="/sendMsg",method=RequestMethod.POST)
	public String sendMsg(MessageVO vo){
		return msvc.sendMsg(vo);
	}
	
	@RequestMapping(value="/read",method=RequestMethod.GET)
	public ModelAndView read(MessageVO vo){
		return new ModelAndView("message/messageRead","vo",msvc.getMessageContents(vo));
	}
	
	@RequestMapping(value="/sendBox",method=RequestMethod.GET)
	public ModelAndView sendBox(HttpSession session, HttpServletRequest request){
		return new ModelAndView("message/sendBox","vo",msvc.getsendList(session,request));
	}
	
	@RequestMapping(value="/sendSearch",method=RequestMethod.GET)
	public ModelAndView sendSearch(HttpSession session, SearchVO vo){
		return new ModelAndView("message/sendBox","vo",msvc.getsendSearchList(session, vo));
	}
	
	@ResponseBody
	@RequestMapping(value="/del",method=RequestMethod.POST)
	public String del(MessageVO vo){
		return msvc.deleteMessage(vo);
	}
	
}

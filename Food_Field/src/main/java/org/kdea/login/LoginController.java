package org.kdea.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.kdea.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value="/")
public class LoginController {
	@Autowired
	private LoginService loginService;

	@RequestMapping(value = "loginSuccess", method = RequestMethod.GET)
	@ResponseBody
	public String loginSuccess(HttpServletRequest request, Authentication authentication, HttpSession session) {
		User user = (User) authentication.getPrincipal();
		UserVO uservo = loginService.login(user.getUsername());
		session.setAttribute("userInfo", uservo);
		
		System.out.println(user.getUsername() + "/" + user.getPassword() + "/" + user.getAuthorities());
		System.out.println(request.getRequestURI());
		System.out.println("로그인 성공 !");
		return "true";
	}

	@RequestMapping(value = "loginFailure", method = RequestMethod.GET)
	@ResponseBody
	public String loginFailure(@ModelAttribute("user") UserVO user) {
		System.out.println("로그인 실패 !");
		return "loginFail";
		/*if(user.getEnabled() != '0')
			return "emailAuth";*/
	}
	
	@RequestMapping("mypage")
	public String mypage() {
		return "mypage/authUser";
	}
	
	@RequestMapping(value = "authUser", method = RequestMethod.POST)
	public ModelAndView authUser(@ModelAttribute("email") String email, @ModelAttribute("pwd") String pwd) {
		UserVO user = loginService.authUser(email, pwd);
		if(user == null)
			return new ModelAndView("mypage/authUser", "error", true);
		return new ModelAndView("mypage/modify", "user", user);
	}
	
	@RequestMapping(value = "modifyUserInfo")
	@ResponseBody
	public String modifyUserInfo(@ModelAttribute UserVO user, HttpSession session) {
		System.out.println(user.getEmail() + "/" + user.getName() + "/" + user.getNickname());
		if(loginService.modifyUserInfo(user, session))
			return "true";
		return "false";
//			return new ModelAndView("mypage/modify", "user", user);
//		return new ModelAndView("mypage/modify", "user", loginService.login(user.getEmail()));
	}
	
	@RequestMapping(value = "modifySuccess")
	public ModelAndView modifySuccess(HttpSession session) {
		/*System.out.println(user.getEmail() + "/" + user.getName() + "/" + user.getNickname());
		if(loginService.modifyUserInfo(user, session))
			return new ModelAndView("mypage/modify", "user", user);*/
		return new ModelAndView("mypage/modify", "user", session.getAttribute("userInfo"));
	}
	
	@RequestMapping(value = "checkNick", method = RequestMethod.POST)
	@ResponseBody
	public String checkNick(@ModelAttribute UserVO user) {
		System.out.println(user.getEmail());
		if(user.getEmail() == null)
			user.setEmail("a");
		if(loginService.checkNick(user))
			return "true";
		return "false";
	}
	
	@RequestMapping(value = "checkEmail", method = RequestMethod.POST)
	@ResponseBody
	public String checkEmail(@ModelAttribute("email") String email) {
		if(loginService.login(email) == null)
			return "true";
		return "false";
	}
	
	@RequestMapping(value = "reqLogin")
	public String reqLogin() {
		return "login/reqLogin";
	}
}

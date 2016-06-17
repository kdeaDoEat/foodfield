package org.kdea.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.kdea.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	
	@RequestMapping(value = "searchIdForm")
	public String searchIdForm() {
		return "login/searchId";
	}
	
	@RequestMapping(value = "searchId", method = RequestMethod.POST)
	public String searchId(@ModelAttribute UserVO user, Model model) {
		String msg = "아이디가 존재하지 않습니다.";
		String result = loginService.searchId(user).getEmail();
		if(result != null)
			msg = "아이디는 " + result + " 입니다.";
		model.addAttribute("msg", msg);
		return "login/searchResult";
	}
	
	@RequestMapping(value = "searchPwdForm")
	public String searchPwdForm() {
		return "login/searchPwd";
	}
	
	@RequestMapping(value = "searchPwd", method = RequestMethod.POST)
	public String searchPwd(@ModelAttribute UserVO user, Model model) {
		UserVO result = loginService.searchPwd(user);
		if(result == null) {
			String msg = "입력 정보가 일치하지 않습니다.";
			model.addAttribute("msg", msg);
			return "login/searchResult";
		} else {
			model.addAttribute("email", result.getEmail());
			model.addAttribute("name", result.getName());
			return "redirect:/sendPwd";
		}
	}
	
	@RequestMapping(value = "changePwd", method = RequestMethod.POST)
	@ResponseBody
	public String changePwd(@ModelAttribute("email") String email, @ModelAttribute("oldPwd") String oldPwd, @ModelAttribute("newPwd") String newPwd) {
		if(loginService.changePwd(email, oldPwd, newPwd))
			return "true";
		return "false";
	}
}

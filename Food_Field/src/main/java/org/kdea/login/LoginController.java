package org.kdea.login;

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

@Controller
@RequestMapping(value="/")
public class LoginController {
	@Autowired
	private LoginService loginService;

	@RequestMapping(value = "loginSuccess", method = RequestMethod.GET)
	@ResponseBody
	public String loginSuccess(Authentication authentication, HttpSession session) {
		User user = (User) authentication.getPrincipal();
		UserVO uservo = loginService.login(user.getUsername());
		session.setAttribute("userInfo", uservo);
		System.out.println(user.getUsername() + "/" + user.getPassword() + "/" + user.getAuthorities());
		System.out.println("로그인 성공 !");
		return "true";
	}

	@RequestMapping(value = "loginFailure", method = RequestMethod.GET)
	@ResponseBody
	public String loginFailure() {
		System.out.println("로그인 실패 !");
		return "false";
	}
}

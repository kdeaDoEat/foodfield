package org.kdea.login;

import org.kdea.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
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
	
	@RequestMapping(value = "login", method = RequestMethod.GET)
	@ResponseBody
	public String login(@ModelAttribute UserVO user) {
		System.out.println("login");
		
		return "login/loginForm";
	}
	
}

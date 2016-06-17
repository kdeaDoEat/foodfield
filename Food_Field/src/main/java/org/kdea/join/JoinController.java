package org.kdea.join;

import org.kdea.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/")
public class JoinController {
	@Autowired
	private JoinService joinService;
	
	@RequestMapping(value = "join", method = RequestMethod.POST)
	@ResponseBody
	public String join(@ModelAttribute UserVO user) {
		if(joinService.join(user))
			return user.getEmail();
		return "false";
	}
	
	@RequestMapping(value = "sendMail")
	public String sendMail(@ModelAttribute("email") String email) throws Exception {
		System.out.println(email);
		joinService.sendMail(email);
		return "main";
	}
	
	@RequestMapping(value = "emailAuth", method = RequestMethod.GET)
	public String emailAuth(@ModelAttribute("email") String email) {
		boolean result = joinService.emailAuth(email);
		if(result)
			return "redirect:/main";
		return "redirect:/main";
	}
	
	@RequestMapping(value = "sendPwd")
	public String sendPwd(@ModelAttribute("email") String email, @ModelAttribute("name") String name, Model model) throws Exception {
		System.out.println(email + "//" + name);
		joinService.sendPwd(email, name);
		model.addAttribute("msg", "가입하신 이메일로 임시 비밀번호를 발급하였습니다.");
		return "searchResult";
	}
}

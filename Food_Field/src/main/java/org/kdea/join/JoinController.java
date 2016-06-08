package org.kdea.join;

import org.kdea.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/")
public class JoinController {
	@Autowired
	JoinService joinService;
	
	@RequestMapping(value = "join", method = RequestMethod.POST)
	public String join(@ModelAttribute UserVO user) {
		joinService.join(user);
		return "main";
	}
}

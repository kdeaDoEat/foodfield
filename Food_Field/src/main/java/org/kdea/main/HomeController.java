package org.kdea.main;

import java.util.List;
import java.util.Locale;

import org.kdea.vo.BoardVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/")
public class HomeController {
	
	@Autowired
	MainService mainsvc;
	
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		List<BoardVO> bestList=  mainsvc.bestreview();
		return "main";
	}
	
}

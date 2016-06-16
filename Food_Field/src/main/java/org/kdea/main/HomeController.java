package org.kdea.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value="/")
public class HomeController {
	
	@Autowired
	private MainService msvc;
	
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public ModelAndView home() {
		return new ModelAndView("main","highCommend",msvc.getHighCommend());
	}
	
}

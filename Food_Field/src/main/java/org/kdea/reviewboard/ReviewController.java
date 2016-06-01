package org.kdea.reviewboard;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/")
public class ReviewController {

	@RequestMapping(value="review",method=RequestMethod.GET)
	public String list(){
		return "reviewboard";
	}
}

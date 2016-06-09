package org.kdea.doit;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/")
public class DoitController {
	
	@RequestMapping(value="doit")
	public String doit(){
		return "doit/doitboard";
	}

}

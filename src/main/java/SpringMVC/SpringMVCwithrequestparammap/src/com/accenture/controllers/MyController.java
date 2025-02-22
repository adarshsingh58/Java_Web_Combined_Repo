package SpringMVC.SpringMVCwithrequestparammap.src.com.accenture.controllers;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller //This tells that our class here is a controller and will be scrolled by the dispatcher servlet when a new request comes
public class MyController {

	/**
	 * So what @RequestParam("name") does is similar to
	 * HttpRequestServlet.getAttribute("name"). It takes the value from input
	 * tag in the HTML/JSP form with name as 'name' and binds it with the
	 * parameter 'username'
	 * 
	 */
	@RequestMapping("/newPage.htm") //This maps the request name coming from JSP to the method
	public ModelAndView showPage(@RequestParam Map<String,String> inputData)
	{
		ModelAndView modelAndView=new ModelAndView();
		modelAndView.setViewName("Hello"); 
		modelAndView.addObject("name", inputData.get("name")); //Setting data you wanna show on next jsp page in variable message
		modelAndView.addObject("age", inputData.get("age")); 
		return modelAndView; 
	}
}


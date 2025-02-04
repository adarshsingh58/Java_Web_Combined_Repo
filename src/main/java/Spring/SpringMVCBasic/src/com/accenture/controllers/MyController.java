package Spring.SpringMVCBasic.src.com.accenture.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller //This tells that our class here is a controller and will be scrolled by the dispatcher servlet when a new request comes
public class MyController {

	@RequestMapping("/newPage.htm") //This maps the request name coming from JSP to the method
	public ModelAndView showPage()
	{
		ModelAndView modelAndView=new ModelAndView();
		modelAndView.setViewName("/index.jsp"); // Set the name of the jsp that will open next
		modelAndView.addObject("message", "Hi Guys"); //Setting data you wanna show on next jsp page in variable message
		return modelAndView; 
	}
}

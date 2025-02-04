package Spring.SpringMVCForGETPOSTmethod.src.com.accenture.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller //This tells that our class here is a controller and will be scrolled by the dispatcher servlet when a new request comes
public class MyController {

		
	@RequestMapping(value="/newPage.htm",method=RequestMethod.GET) //Now this will only get executed if request is GET type 
	public ModelAndView showPage_GET()
	{
		ModelAndView modelAndView=new ModelAndView();
		modelAndView.setViewName("/index.jsp"); // Set the name of the jsp that will open next
		modelAndView.addObject("message", "Hi Guys. This was a GET request"); //Setting data you wanna show on next jsp page in variable message
		return modelAndView; 
	}
	
	@RequestMapping(value="/newPage.htm",method=RequestMethod.POST) //Now this will only get executed if request is POST type 
	public ModelAndView showPage_POST()
	{
		ModelAndView modelAndView=new ModelAndView();
		modelAndView.setViewName("/index.jsp"); // Set the name of the jsp that will open next
		modelAndView.addObject("message", "Hi Guys. This was a POST request"); //Setting data you wanna show on next jsp page in variable message
		return modelAndView; 
	}
}

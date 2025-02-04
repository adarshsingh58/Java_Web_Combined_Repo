package Spring.SpringMVCwithModelAttribute.src.com.accenture.controllers;

import Spring.SpringMVCwithModelAttribute.src.online.mrwallet.www.beans.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller //This tells that our class here is a controller and will be scrolled by the dispatcher servlet when a new request comes
public class MyController {

	/**
	 *@ModelAttribute will take in the POJO and map all the values from previous JSP into this POJO which have the same names
	 *This also sets this POJO into the modelandview by itself, so it is available in next view. 
	 */
	@RequestMapping("/newPage.htm")
	public ModelAndView showPage(@ModelAttribute User user) //
	{
		ModelAndView modelAndView=new ModelAndView();
		
		System.out.println(user.getUsername());
		
		modelAndView.setViewName("Hello"); // The values from user pojo will be availeble at Hello.jsp
		return modelAndView; 
	}
}

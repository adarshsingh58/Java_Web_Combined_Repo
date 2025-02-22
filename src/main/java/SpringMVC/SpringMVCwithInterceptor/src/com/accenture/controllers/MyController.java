package SpringMVC.SpringMVCwithInterceptor.src.com.accenture.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller //This tells that our class here is a controller and will be scrolled by the dispatcher servlet when a new request comes
public class MyController {

	@ModelAttribute
	public void getCommonData(Model model)
	{
		model.addAttribute("websiteName", "www.mrwallet.onilne");
	}
	
	@RequestMapping("/newPage.htm")
	public ModelAndView showPage(@RequestParam("username") String username,@RequestParam("age") int age) //
	{
		ModelAndView modelAndView=new ModelAndView();
		modelAndView.addObject("username", username);
		modelAndView.addObject("age", age);
		modelAndView.setViewName("Hello"); // The values from user pojo will be availeble at Hello.jsp
		return modelAndView; 
	}
	
}

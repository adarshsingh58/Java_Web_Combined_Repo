package Spring.SpringMVCwithmdoelattibuteatmethodlevel.src.com.accenture.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller //This tells that our class here is a controller and will be scrolled by the dispatcher servlet when a new request comes
public class MyController {

	/**
	 * This method level @ModelAttribute annotation gets executed before any of
	 * the @RequestMapping methods. Its job is to prepare the Model object and
	 * the Model object it prepares persist across all the modelandview in
	 * current conteroller. This way user does not have to define repetitive
	 * attribute again and again. In this case websiteName will be available
	 * acorss all the pages accessewd after calling his controller be it
	 * Hello.jsp or Welcome.jspa
	 * 
	 * @param model
	 */
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
	
	@RequestMapping("/Welcome.htm")
	public ModelAndView showWelcomePage(@RequestParam("course") String courseName) //
	{
		ModelAndView modelAndView=new ModelAndView();
		modelAndView.addObject("course", courseName);
		modelAndView.setViewName("Welcome"); // The values from user pojo will be availeble at Hello.jsp
		return modelAndView; 
	}
	
}

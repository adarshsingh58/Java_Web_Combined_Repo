package Spring.SpringMVCwithInterceptor.src.com.accenture.interceptoes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class MVCInterceptors extends HandlerInterceptorAdapter{
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		if(request.getHeader("referer").contains("localhost"))
		{
			return true;
		}
		response.getWriter().println("Request cpoming from illegal url");
		return false;
	}

}

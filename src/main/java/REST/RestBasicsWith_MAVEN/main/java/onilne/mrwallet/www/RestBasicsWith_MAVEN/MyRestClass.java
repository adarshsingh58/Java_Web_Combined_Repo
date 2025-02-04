package REST.RestBasicsWith_MAVEN.main.java.onilne.mrwallet.www.RestBasicsWith_MAVEN;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/messages")
public class MyRestClass {

	@GET
	public String getName_GET()
	{
		return "adarsh";
	}
}

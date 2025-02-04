package REST.REST_accessing_QueryParameters.main.java.onilne.mrwallet.www.REST_accessing_QueryParameters;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("myresource")
public class MyResource {

	/**
	 * query parameter can be accessed by using @QueryParam tag followed by
	 * keyname of value in query parameter. It can the be mapped to our method
	 * input accordingly.
	 * 
	 * 
	 * Hit
	 * "http://localhost:8080/REST_accessing_QueryParameters/webapi/myresource/query?name=adarsh&pass=ada123"
	 * as Get method to get response as "adarsh:ada123"
	 */
	@Path("query")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getIt(@QueryParam("name") String username, @QueryParam("pass") String password) {
		return username + ":" + password;
	}
}

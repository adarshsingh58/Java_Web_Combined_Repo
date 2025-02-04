package REST.REST_accessing_pathValueORpathParameter.main.java.onilne.mrwallet.www.REST_accessing_pathValueORpathParameter;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("myresource")
public class MyResource {

	/**
	 * We know using @Path at method level will associate that url to the
	 * method. But if the url is user oriented and we want to fetch it in out
	 * method then we will capture it in a variable with {} inside @Path and
	 * then map that variable with our String input value pathValueRetrieved
	 * with the help of @PathParam followed by the name of te variable it is
	 * captured in.
	 * 
	 * This is imp in scenarios when say suer is sending the messge number he
	 * wants to access in url itself and we want to fetch it to retreive the
	 * data corrosponding ti that message number
	 * 
	 * If URL -> "http://localhost:8080/REST_accessing_pathValueORpathParameter/webapi/myresource/yeo"
	 * is accessed with a GET request response will be "yeo" that is the last part of the URL
	 */
	@GET
	@Path("{somePathValue}")
	@Produces(MediaType.TEXT_PLAIN)
	public String getIt(@PathParam("somePathValue") String pathValueRetrieved) {
		return pathValueRetrieved;
	}
}

package REST.REST_Sending_Non_Default_statusCode_in_response.main.java.onilne.mrwallet.www.REST_Sending_Non_Default_statusCode_in_response;

import java.util.Locale;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("myresource")
public class MyResource {

	/**
	 * WHen a post request is received and processed successfully, automatically
	 * response code 200 is sent by default representing SUCCESS.
	 * 
	 * But there are more specific status codes available which can be used to
	 * be more specific in response. e.g. we can send 201 which represent
	 * Successful Creation of resource. So in this way client will have a better
	 * view of wht has happened.
	 * 
	 * To do that we instead of sending data as Strings or objects and stuff , we use Response class and return that.
	 * We set the status code and other detauils in response aong wth the response body ,which we were supposed to sent, as entity.
	 * We can send all other details, like headers, which possibly we see in response and then do a build on response to create a final response.
	 * 
	 * Hit: "http://localhost:8080/REST_Sending_Non_Default_statusCode_in_response/webapi/myresource"
	 * 
	 * RESULT IN RESPONSE HEADER:
	  	content-language →en-CA
		content-length →20
		content-type →text/plain
		date →Thu, 07 Sep 2017 07:36:49 GMT
		myheaderkey →myHeaderValue
		server →Apache-Coyote/1.1
		
	 * RESULT IN RESPONSE BODY:
	 	Hi my name is Adarsh
		
	 * STATUS:
		202 Accepted
	 */
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	public Response getIt() {
		Response response=Response.status(Status.ACCEPTED)
				.entity("Hi my name is Adarsh")//this is the body of response
				.language(Locale.CANADA)	//will set the content-language to canada english
				.header("myHeaderKey", "myHeaderValue") //will set header data
				.build();
		return response;

	}
}

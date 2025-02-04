package REST.REST_accessing_Request_headers.main.java.onilne.mrwallet.www.REST_accessing_Request_headers;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

@Path("myresource")
public class MyResource {

	/**
	 * To fetch headers from request we use an interface called HTTPHeaders as input,
	 * to which jersey will map all the headers details upon using @Context annotation on it.
	 * 
	 * Hit URL-> "http://localhost:8080/REST_accessing_Request_headers/webapi/myresource/" and give any header to it.
	 * All the header values can be extracted by using getHeaderString(KEYNAME) method.
	 */
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.TEXT_PLAIN)
	public String getIt(@Context HttpHeaders httpHeaders) {
		return httpHeaders.getHeaderString("Content-type");
	}
}

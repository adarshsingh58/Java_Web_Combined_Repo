package REST.REST_Using_URI_Info.main.java.onilne.mrwallet.www.REST_Using_URI_Info;

import java.util.List;
import java.util.Map.Entry;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

@Path("myresource")
public class MyResource {

	/**
	 * At times we need the info of the request URI or want to build some uri.
	 * For that case We use class UriInfo with @Context annotation.
	 * We can use it get: BASEURI, ABSOLUTEPATH OF REQUEST, REQUEST URL, PATH AND 
	 * ALSO TO FETCH ALL THE NAME OF QUERY PARAMETERS and PATH PARAMETERS
	 * 
	 * Hit URL-> "http://localhost:8080/REST_Using_URI_Info/webapi/myresource/testPath?name=adarsh&pass=pass213"
	 * 
	 * Result:
	 * 	 Path:myresource/testPath
		 absolutePath:http://localhost:8080/REST_Using_URI_Info/webapi/myresource/testPath
		 BaseUri:http://localhost:8080/REST_Using_URI_Info/webapi/
		 RequestURI:http://localhost:8080/REST_Using_URI_Info/webapi/myresource/testPath?name=adarsh&pass=pass213
		 Query parameters:pass213
		 Query parameters:adarsh
	 * 
	 */
	@Path("testPath")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getIt(@Context UriInfo uriInfo) {
		StringBuilder builder=new StringBuilder();
		builder.append("\n Path:"+uriInfo.getPath()).append("\n absolutePath:"+uriInfo.getAbsolutePath()).append("\n BaseUri:"+uriInfo.getBaseUri())
		.append("\n RequestURI:"+uriInfo.getRequestUri());
		
		for(Entry<String, List<String>> entry:uriInfo.getQueryParameters().entrySet())
		{
			builder.append("\n Query parameters:"+entry.getValue().get(0));
		}
		
		for(Entry<String, List<String>> entry:uriInfo.getPathParameters().entrySet())
		{
			builder.append("\n Path parameter:"+entry.getValue().get(0));
		}
			
		return builder.toString();
	}
}

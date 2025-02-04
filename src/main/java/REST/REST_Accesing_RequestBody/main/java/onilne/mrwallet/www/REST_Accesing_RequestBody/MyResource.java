package REST.REST_Accesing_RequestBody.main.java.onilne.mrwallet.www.REST_Accesing_RequestBody;

import REST.REST_Accesing_RequestBody.main.java.online.mrwallet.www.beans.Student;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("myresource")
public class MyResource {

	/**
	 * Data from req body is aurtomatically mapped to input parameter of the
	 * method, into requstBody in this case. This will happen if the request
	 * body data type and @Consumes matches propeorly. Similarly on sending XML
	 * data in request body using a class annotated with @XMLRootElementas as
	 * input parameter in getIt() will make the ,apping from requrstBody to that
	 * class's object
	 */
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.TEXT_PLAIN)
	public String getIt(String requestBody) {
		return requestBody;
	}
	
	/**
	 * Send a post req with body conatining XML data for Student class like:
	 * <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
		<student>
    	<name>adarsh</name>
		</student>
	 * Make sure to set content-type for request to application/xml 
	 */
	@POST
	@Path("dataXml")
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.APPLICATION_XML)
	public Student getIt_xml(Student student) {
		return student;
	}
}

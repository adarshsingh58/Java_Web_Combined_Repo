package REST.REST_associating_url_with_specific_method.src.main.java.onilne.mrwallet.www.REST_associating_url_with_specific_method;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("myresource")
public class MyResource {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     *We have used @Path at method level to associate the url "myresource/student" specifically fotr getIt() method. 
     *That means upon hit of this URL this method will get executed.
     */
    @GET
    @Path("student")//Url associated with method getIt()
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        return "Got it!";
    }
}

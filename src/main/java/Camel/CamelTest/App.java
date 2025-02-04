package Camel.CamelTest;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		SimpleRouteBuilder builder = new SimpleRouteBuilder();
		CamelContext camelContext = new DefaultCamelContext();
		try {
			camelContext.addRoutes(builder);
			camelContext.start();
			Thread.sleep(2000*60*5);
			camelContext.stop();
		} catch (Exception e) {
			System.out.println("Exception occured "+e.getMessage());
		}
	}
}

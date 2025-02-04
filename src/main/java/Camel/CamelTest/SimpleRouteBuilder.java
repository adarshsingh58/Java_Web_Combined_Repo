package Camel.CamelTest;

import org.apache.camel.builder.RouteBuilder;

public class SimpleRouteBuilder extends RouteBuilder {

	/* (non-Javadoc)
	 * Message or file produced into D:/XX folder 
	 * are then consumed from it into D:/ZZ folder.
	 * 
	 * This is a continuous process and as long the 
	 * application is running, XX folder will act as a producer(or msgs will be consumed from XX folder)
	 * and DD folder will act as a consumer
	 */
	@Override
	public void configure() throws Exception {
		from("file:D:/XX?noop=true").to("file:D:/ZZ");
	}

}

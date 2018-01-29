package camel;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class SetExangePropertieExemplo {
	public static void main(String[] args) throws Exception {
		CamelContext cc = new DefaultCamelContext();

		cc.addRoutes(new RouteBuilder() {

			@Override
			public void configure() throws Exception {
				from("direct:entrada").setProperty("P1", constant("direct:V1"))
				.recipientList(exchangeProperty("P1"));
			
				from("direct:V1").to("stream:out");
			}
		});

		cc.start();
		cc.createProducerTemplate().sendBody("direct:entrada", "corpo !!!!");
		Thread.sleep(3000);
		cc.stop();
	}
}

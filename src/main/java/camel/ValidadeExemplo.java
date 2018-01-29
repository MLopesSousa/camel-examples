package camel;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class ValidadeExemplo {

	public static void main(String[] args) throws Exception {
		CamelContext cc = new DefaultCamelContext();

		cc.addRoutes(new RouteBuilder() {

			@Override
			public void configure() throws Exception {
		
				 from("direct:entrada").validate(header("isOk").isEqualTo("true")).to("stream:out");

			}
		});

		cc.start();
		cc.createProducerTemplate().sendBodyAndHeader("direct:entrada", "---","isOk","false");

		Thread.sleep(3000);
		cc.stop();
	}

}

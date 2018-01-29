package camel;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class LoopExemplo {
	public static void main(String[] args) throws Exception {
		CamelContext cc = new DefaultCamelContext();
		
		cc.addRoutes(new RouteBuilder() {
			
			@Override
			public void configure() throws Exception {
//				from("direct:entrada")
//				.loop(10)
//				.transform(body().append("-"))
//				.to("stream:out")
//				.end();
				
//				from("direct:entrada")
//				.loop(10)
//				.copy()
//				.transform(body().append("-"))
//				.to("stream:out")
//				.end();
				
			
			}
		});
		
		cc.start();
		cc.createProducerTemplate().sendBody("direct:entrada", "A");
		
		Thread.sleep(3000);
		cc.stop();
		
	}
	
	public static class LoopExemploBeanUm {
			public boolean validate() {
				return true;
			}
		
	}
}

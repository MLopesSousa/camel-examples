package camel;

import org.apache.camel.CamelContext;
import org.apache.camel.Header;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class FiltroExemplo {
	public static void main(String[] args) throws Exception {
		CamelContext cc = new DefaultCamelContext();
		
		cc.addRoutes(new RouteBuilder() {
			
			@Override
			public void configure() throws Exception {
				from("direct:entrada").filter().method(FiltroExemploBeanUm.class, "validate").setBody(body().append(simple("Level ${in.header.Level}"))).to("stream:out");
				//from("direct:entrada").filter().method(FiltroExemploBeanUm.class, "validate").to("log:WARN?showHeaders=true");	
			}
		});
		
		cc.start();
		cc.createProducerTemplate().sendBodyAndHeader("direct:entrada", "", "Level", "true");
		Thread.sleep(3000);
		cc.stop();
		
	}
	
	public static class FiltroExemploBeanUm {
			public boolean validate(@Header("Level") String level) {
				return level.equals("true");
			}
		
	}
}

package camel;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Header;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class RecipientListExemplo {
	public static void main(String[] args) throws Exception {
		CamelContext cc = new DefaultCamelContext();
		
		cc.addRoutes(new RouteBuilder() {
			
			@Override
			public void configure() throws Exception {
				//from("direct:entrada").recipientList(header("queue").tokenize(",")).ignoreInvalidEndpoints();
				//from("direct:entrada").recipientList(header("queue").tokenize(",")).parallelProcessing().ignoreInvalidEndpoints();
				//from("direct:entrada").recipientList(header("queue").tokenize(",")).onPrepare(new Processor() {
				//	public void process(Exchange exchange) throws Exception {
				//		exchange.getIn().setBody(body().append("Esta mensagem foi alterada !!!"));
				//	}
				//	
				//}).ignoreInvalidEndpoints();
				
				
				
				from("direct:q1").setBody(body().append("passeiPor q1: ")).setBody(body().append(simple("threadName"))).to("stream:out");
				from("direct:q2").setBody(body().append("passeiPor q2: ")).setBody(body().append(simple("threadName"))).to("stream:out");
				
				//from("direct:q1").setHeader("passeiPor", constant("q1")).to("log:WARN?showHeaders=true");
				//from("direct:q2").setHeader("passeiPor", constant("q2")).to("log:WARN?showHeaders=true");
			}
		});
		
		cc.start();
		cc.createProducerTemplate().sendBodyAndHeader("direct:entrada", "", "queue", "direct:q1,direct:q1");
		cc.createProducerTemplate().sendBodyAndHeader("direct:entrada", "", "queue", "direct:q2");
		
		Thread.sleep(3000);
		cc.stop();
		
	}
	
	public static class FiltroExemploBeanUm {
			public boolean validate(@Header("Level") String level) {
				return level.equals("true");
			}
		
	}
}

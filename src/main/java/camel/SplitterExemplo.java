package camel;

import java.util.ArrayList;
import java.util.List;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.processor.aggregate.AggregationStrategy;

public class SplitterExemplo {
	public static void main(String[] args) throws Exception {
		CamelContext cc = new DefaultCamelContext();

		cc.addRoutes(new RouteBuilder() {

			@Override
			public void configure() throws Exception {
				//from("direct:entrada").split(body().tokenize(",")).to("stream:out");
				//from("direct:entrada").split().method(SplitteExemploBeanUm.class, "splitBody").parallelProcessing().to("stream:out");
//				from("direct:entrada").split().method(SplitteExemploBeanUm.class, "splitBody").parallelProcessing().onPrepare(new Processor() {
//					
//					public void process(Exchange exchange) throws Exception {
//						System.out.println("No processor: " + exchange.getIn().getBody());
//						
//					}
//				}).to("stream:out");
				from("direct:entrada").split(body().tokenize(","), new SplitteExemploAgreggatorUm()).to("stream:out").end();
			}
		});

		cc.start();
		cc.createProducerTemplate().sendBody("direct:entrada", "1,2,3,4,5");
		Thread.sleep(3000);
		cc.stop();

	}

	public static class SplitteExemploAgreggatorUm implements AggregationStrategy  {
		public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
			if(oldExchange == null)
				return newExchange;
			
			if (Integer.parseInt(newExchange.getIn().getBody().toString()) % 2 == 0) {
				oldExchange.getIn().setBody(oldExchange.getIn().getBody().toString() + " + " + newExchange.getIn().getBody().toString());
				return oldExchange;
			} else {
				return newExchange;
			}
			
		}
	}
	
	public static class SplitteExemploBeanUm {
		public List<String> splitBody(String body) {
			List<String> answer = new ArrayList<String>();
			String[] parts = body.split(",");
			for (String part : parts) {
				answer.add(part);
			}
			return answer;
		}

	}
}

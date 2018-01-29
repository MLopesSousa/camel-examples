package camel;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.language.XPath;

public class ContentBaseRouterExemplo {
	public static void main(String[] args) throws Exception {
		CamelContext cc = new DefaultCamelContext();

		cc.addRoutes(new RouteBuilder() {

			@Override
			public void configure() throws Exception {
				// from("direct:entrada").choice().when(xpath("/main/parent/child =
				// 'Marcelo'")).to("stream:out")
				// .otherwise().setBody(body().append(" Error!!!")).to("stream:out");

				// from("direct:entrada").choice().when().xpath("/main/parent/child =
				// 'Marcelo'").to("stream:out")
				// .otherwise().setBody(body().append(" Error!!!")).to("stream:out");

				// from("direct:entrada").choice().when().method(ContentBaseRouterExemploBeanUm.class,
				// "validate").to("stream:out")
				// .otherwise().setBody(body().append(" Error!!!")).to("stream:out");

				from("direct:entrada").choice().when().el("${in.headers.H1} == 'V1'").to("stream:out").otherwise()
						.setBody(body().append(" Error!!!")).to("stream:out");
			}
		});

		cc.start();
		cc.createProducerTemplate().sendBodyAndHeader("direct:entrada",
				"<main><parent><child>Marcelo</child></parent></main>", "H1", "V1");
		Thread.sleep(3000);
		cc.stop();
	}

	public static class ContentBaseRouterExemploBeanUm {
		public boolean validate(@XPath("/main/parent/child") String valor) {
			return valor.equals("Marcelo");
		}

	}
}

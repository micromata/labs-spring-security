package de.micromata.spring.security.example;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SimpleSpringSecurityApplication {

	@Value("${server.port}")
    private int serverPort;

    @Value("${server.http.port}")
    private int serverHttpPort;

	public static void main(String[] args) {
		SpringApplication.run(SimpleSpringSecurityApplication.class, args);
	}

	@Bean
	public EmbeddedServletContainerFactory createAdditionalTomcatConnector() {
		TomcatEmbeddedServletContainerFactory embeddedTomcat = new TomcatEmbeddedServletContainerFactory() {
			@Override
			protected void postProcessContext(Context context) {
				SecurityConstraint securityConstraint = new SecurityConstraint();
				securityConstraint.setUserConstraint("CONFIDENTIAL");
				SecurityCollection collection = new SecurityCollection();
				collection.addPattern("/*");
				securityConstraint.addCollection(collection);
				context.addConstraint(securityConstraint);
			}
		};
		embeddedTomcat.addAdditionalTomcatConnectors(createHttpConnector());
		return embeddedTomcat;
	}

	private Connector createHttpConnector() {
		Connector httpConnector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
		httpConnector.setScheme("http");
		httpConnector.setPort(serverHttpPort);
		httpConnector.setRedirectPort(serverPort);
		return httpConnector;
	}
}
package com.foryousoft.hsking;

import java.net.URL;
import java.security.ProtectionDomain;

import org.apache.log4j.Logger;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.SecureRequestCustomizer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.SslConnectionFactory;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.eclipse.jetty.webapp.WebAppContext;

public class EmbeddedServer {

    public static String HOST = "localhost";
    public static int PORT = 8080;
    public static int SSL_PORT = 9998;
    public static String CONTEXT_PATH ="/hsking";
    public static Logger logger = Logger.getLogger(EmbeddedServer.class);
// http://localhost:9999/hsking/get/users

    /**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

        logger.info("To connect: " + getUrlRoot() + "/get/users");
		Server server = new Server();

		ServerConnector connector = new ServerConnector(server);
		connector.setPort(PORT);
		HttpConfiguration https = new HttpConfiguration();
        https.addCustomizer(new SecureRequestCustomizer());
		SslContextFactory sslContextFactory = new SslContextFactory();
		sslContextFactory.setKeyStorePath(EmbeddedServer.class.getResource("/keystore.jks").toExternalForm());
		sslContextFactory.setKeyStorePassword("123456");
		sslContextFactory.setKeyManagerPassword("123456");
		ServerConnector sslConnector = new ServerConnector(server,
				new SslConnectionFactory(sslContextFactory, "http/1.1"),
				new HttpConnectionFactory(https));
		sslConnector.setPort(SSL_PORT);
		server.setConnectors(new Connector[] { connector, sslConnector });

		WebAppContext context = new WebAppContext();
		context.setServer(server);
		context.setContextPath(CONTEXT_PATH);

		ProtectionDomain protectionDomain = EmbeddedServer.class.getProtectionDomain();
		URL location = protectionDomain.getCodeSource().getLocation();
		context.setWar(location.toExternalForm());

		server.setHandler(context);
		while (true) {
			try {
				server.start();
				break;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			System.in.read();
			server.stop();
			server.join();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(100);
		}
	}

    public static String getUrlRoot() {
        return "http://" + EmbeddedServer.HOST + ":" + EmbeddedServer.PORT + CONTEXT_PATH ;
    }

}

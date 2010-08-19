package servletdi.secret;

import servletdi.ShoppingCart;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.HashMap;
import java.util.Map;

public class MyListener  implements ServletContextListener, HttpSessionListener {
    public static final String APP_SCOPED_CONTAINER = "app-scoped-container";

    public static class ContainerHolder {
	
	    // Maps serves as a surrogate DI container for the sake of discussion.
	    // could be PicoContainer, Spring, Guice (or 20 others) in reality
        private Map container;
        public ContainerHolder(Map container) {
            this.container = container;
        }
        // nothing outside the package (or subclasses) can see this method
        // not static
        Map getContainer() {
            return container;
        }
    }

    public void contextInitialized(ServletContextEvent sce) {

        // setup application-scope, session-scope and request-scope stuff here.

        Map fakeDiContainer = new HashMap();
        // register (one time only) all of the components that can be DI'd in this app:
        fakeDiContainer.put(ShoppingCart.class, new ShoppingCart());

        sce.getServletContext().setAttribute(APP_SCOPED_CONTAINER,
                new ContainerHolder(fakeDiContainer));

    }

    public void contextDestroyed(ServletContextEvent sce) {
    }

    public void sessionCreated(HttpSessionEvent se) {

        // initialize 'store' for session-scoped components here

    }

    public void sessionDestroyed(HttpSessionEvent se) {

        // dispose of 'store' for session-scoped components here

    }

}

package servletdi;

public class ShoppingCart {
	
	// hardly a world class component, but a worthy example
	
	// intended to be accessible via the URL: /servletdi/ShoppingCart/helloWorld
	// http://localhost:8080/servletdi/ShoppingCart/helloWorld for the Maven launched Jetty.
    public String helloWorld() {
        return "something wonderful has happened";
    }

}

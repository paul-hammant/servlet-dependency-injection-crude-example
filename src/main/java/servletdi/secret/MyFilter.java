package servletdi.secret;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public class MyFilter implements Filter {

    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void doFilter(ServletRequest request, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {
        resp.setContentType("text/html");
        PrintWriter writer = resp.getWriter();
        HttpServletRequest req = (HttpServletRequest) request;
        MyListener.ContainerHolder containerHolder = (MyListener.ContainerHolder)
                req.getSession().getServletContext().getAttribute(MyListener.APP_SCOPED_CONTAINER);

        // we are in the same package so can invoke .getContainer(), but to classes outside this package,
        // MyListener.ContainerHolder has no methods: 
        Map fakeContainer = containerHolder.getContainer();

        String url = ((HttpServletRequest) request).getRequestURI();
        String clazz = url.replace("/", ".").substring(1); // turn slash delimited URL into class-name
        try {
            Class<?> type = Class.forName(clazz);
            Object instance = fakeContainer.get(type);
            Method method = type.getMethod(req.getParameter("method")); // no args
            writer.print(method.invoke(instance));
        } catch (ClassNotFoundException e) {
        } catch (NoSuchMethodException e) {
        } catch (InvocationTargetException e) {
        } catch (IllegalAccessException e) {
        }
    }

    public void destroy() {
    }

}

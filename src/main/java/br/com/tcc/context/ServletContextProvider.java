package br.com.tcc.context;

import javax.servlet.ServletContext;
import org.springframework.web.context.ServletContextAware;

public class ServletContextProvider implements ServletContextAware {
    private static ServletContext context;

    public static ServletContext getServletContext() {
        return context;
    }

    @Override
    public void setServletContext(ServletContext sc) {
        context = sc;
    }
}

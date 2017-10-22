package br.com.tcc.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class Interceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest hsr, HttpServletResponse hsr1, Object o) throws Exception {
        return true;
        /*String uri = hsr.getRequestURI();
        System.out.println(uri);
        
        //Publico
        if (uri.endsWith("/") ||
            uri.endsWith("login") ||
            uri.endsWith("logar") ||
            uri.endsWith("cadastro")||
            uri.endsWith("cadastrar") ||
            uri.endsWith("confirmarCadastro")) {
            return true;
        }
        
        HttpSession session = hsr.getSession();
        if (session.getAttribute("logado") != null) {
            System.out.println("a");
            return true;
        }
        
        return false;*/
    }

    @Override
    public void postHandle(HttpServletRequest hsr, HttpServletResponse hsr1, Object o, ModelAndView mav) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest hsr, HttpServletResponse hsr1, Object o, Exception excptn) throws Exception {
    }
    
}

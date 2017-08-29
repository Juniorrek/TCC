package br.com.tcc.controller;

import br.com.tcc.Facade.Facade;
import br.com.tcc.dao.ProjetoDao;
import br.com.tcc.model.Projeto;
import br.com.tcc.model.Usuario;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class InserirServletController {
    @RequestMapping(value = "/InserirServlet", method = RequestMethod.POST)    
    public @ResponseBody void InserirServlet(@RequestParam("qqfile") MultipartFile file/*, HttpServletResponse response*/) {
        System.out.println("a");
        System.out.println(file.getOriginalFilename());
        /*try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text");
            response.getWriter().print("\"success\":true");
            response.flushBuffer();
        } catch (IOException ex) {
            Logger.getLogger(InserirServletController.class.getName()).log(Level.SEVERE, null, ex);
        }*/
    }
}

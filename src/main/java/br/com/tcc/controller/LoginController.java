package br.com.tcc.controller;

import br.com.tcc.dao.UsuarioDao;
import br.com.tcc.model.Cadastro;
import br.com.tcc.model.Login;
import br.com.tcc.model.Usuario;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {
    @RequestMapping("/login")    
    public String login(Model model) {
        model.addAttribute("login", new Login());
        
        return "login";
    }
    
    @RequestMapping("/logar")    
    public String logar(@Valid Login login, BindingResult br, RedirectAttributes ra, HttpSession session, HttpServletRequest request) {
        if (!br.hasErrors()) {
            try {
                int validaLogin = UsuarioDao.validaLogin(login.getEmail(), login.getSenha());
                
                if (validaLogin == 0) {
                    Usuario logado = UsuarioDao.carregar(login.getEmail());
                    session.setAttribute("logado", logado);
                    
                    return "redirect:/principal";
                } else if (validaLogin == 1) {
                    ra.addFlashAttribute("retorno", "toastr.error('Email não confirmado !!!');");
                    Cadastro cadastro = new Cadastro();
                    cadastro.setEmail(login.getEmail());
                    String path = request.getRequestURL().toString().replace(request.getRequestURI(), "");
                    cadastro.enviarEmailConfirmacao(path);
                } else if (validaLogin == 2) {
                    ra.addFlashAttribute("retorno", "toastr.error('Email ou senha incorretos !!!');");
                }
            } catch (SQLException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                ra.addFlashAttribute("retorno", "toastr.error('Erro ao validar email !!!');");
            }
        } else {
            ra.addFlashAttribute("retorno", "toastr.error('Erro ao logar !!!');");
        }
        
        return "redirect:/login";
    }
    
    @RequestMapping("/logout")    
    public String logout(HttpSession session) {
        session.invalidate();
        
        return "redirect:/";
    }
}

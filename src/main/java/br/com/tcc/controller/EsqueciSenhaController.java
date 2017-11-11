package br.com.tcc.controller;

import br.com.tcc.dao.UsuarioDao;
import br.com.tcc.model.Cadastro;
import br.com.tcc.model.Login;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.mail.EmailException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class EsqueciSenhaController {
    @RequestMapping("/esqueci_senha")    
    public String esqueci_senha(Model model) {
        model.addAttribute("login", new Login());
        
        return "esqueci_senha";
    }
    
    @RequestMapping("/esqueci_senha/procurar")    
    public String procurar(Login login, RedirectAttributes ra, HttpServletRequest request) {
        try {
            if (UsuarioDao.existe(login.getEmail())) {
                String url = request.getRequestURL().toString().replace(request.getRequestURI(), "");
                UsuarioDao.recuperarSenha(login.getEmail(), url);
                
                ra.addFlashAttribute("retorno", "toastr.success('Verifique o email para recuperar sua senha !!!', {timeOut: 0});");
            } else {
                ra.addFlashAttribute("retorno", "toastr.error('Email não encontrado !!!');");
            }
        } catch (SQLException | EmailException ex) {
            ra.addFlashAttribute("retorno", "toastr.error('Erro ao recuperar senha, tente novamente mais tarde !!!');");
            Logger.getLogger(EsqueciSenhaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "redirect:/esqueci_senha";
    }
    
    @RequestMapping("/esqueci_senha/redefinir")    
    public String redefinir() {
        return "redefinir_senha";
    }
    
    @RequestMapping("/esqueci_senha/redefinir/confirmar")    
    public String redefinir_confirmar(String email, String token, String senha, RedirectAttributes ra) {
        try {
            if (UsuarioDao.validaRecuperarSenha(email, token)) {
                UsuarioDao.redefinirSenha(email, senha);
                UsuarioDao.apagarToken(email, token);
                ra.addFlashAttribute("retorno", "toastr.success('Senha redefinida com sucesso !!!');");
            } else {
                ra.addFlashAttribute("retorno", "toastr.error('Erro ao redefinir senha, token ou email inválido !!!');");
            }
        } catch (SQLException ex) {
            ra.addFlashAttribute("retorno", "toastr.error('Erro ao redefinir senha, tente novamente mais tarde !!!');");
            Logger.getLogger(EsqueciSenhaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return "redirect:/login";
    }
}

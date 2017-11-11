package br.com.tcc.controller;

import br.com.tcc.dao.UsuarioDao;
import br.com.tcc.model.Cadastro;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CadastroController {
    @RequestMapping("/cadastro")    
    public String cadastro(Model model) {
        model.addAttribute("cadastro", new Cadastro());
        
        return "cadastro";
    }
    
    @RequestMapping("/cadastrar")    
    public String cadastrar(@Valid Cadastro cadastro, BindingResult br, RedirectAttributes ra, HttpServletRequest request) {
        if (!br.hasErrors()) {
            if (cadastro.getSenha().equals(cadastro.getConfirmacaoSenha())) {
                try {
                    String path = request.getRequestURL().toString().replace(request.getRequestURI(), "");
                    UsuarioDao.cadastrar(cadastro);
                    cadastro.enviarEmailConfirmacao(path);
                    return "confirmacaoCadastro";
                } catch (SQLException ex) {
                    ra.addFlashAttribute("retorno", "toastr.error('Erro ao cadastrar !!!');");
                }
            } else {
                ra.addFlashAttribute("retorno", "toastr.error('Senha e confirmação de senha diferentes !!!');");
            }
        } else {
            ra.addFlashAttribute("retorno", "toastr.error('Erro ao cadastrar !!!');");
        }
        
        return "redirect:/cadastro";
    }
    
    @RequestMapping("/confirmarCadastro")    
    public String confirmarCadastro(String email, RedirectAttributes ra) {
        try {
            UsuarioDao.confirmarCadastro(email);
            ra.addFlashAttribute("retorno", "toastr.success('Email ativado com sucesso !!!');");
        } catch (SQLException ex) {
            Logger.getLogger(CadastroController.class.getName()).log(Level.SEVERE, null, ex);
            ra.addFlashAttribute("retorno", "toastr.error('Erro ao ativar email !!!');");
        }
        
        return "redirect:/login";
    }
}

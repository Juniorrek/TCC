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
public class UsuarioController {
    @RequestMapping("/meuPerfil/alterarSenha")    
    public String alterarSenha() {
        return "alterar_senha";
    }
    
    @RequestMapping("/meuPerfil/alterarSenha/confirmar")    
    public String alterarSenha_confirmar(String senhaAtual, String novaSenha, String novaSenhaConfirmacao, HttpSession session, RedirectAttributes ra) {
        Usuario logado = (Usuario) session.getAttribute("logado");
        
        try {
            if (UsuarioDao.validaSenhaAtual(logado, senhaAtual)) {
                if (novaSenha.equals(novaSenhaConfirmacao)) {
                    UsuarioDao.redefinirSenha(logado.getEmail(), novaSenha);
                    ra.addFlashAttribute("retorno", "toastr.success('Senha alterada com sucesso !!!');");
                } else {
                    ra.addFlashAttribute("retorno", "toastr.error('Erro, senha diferente da confirmação !!!');");
                }
            } else {
                ra.addFlashAttribute("retorno", "toastr.error('Erro, senha atual incorreta !!!');");
            }
        } catch (SQLException ex) {
            ra.addFlashAttribute("retorno", "toastr.error('Erro ao alterar senha, tente novamente mais tarde !!!');");
            Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return "redirect:/meuPerfil/alterarSenha";
    }
}

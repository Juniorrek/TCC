package br.com.tcc.controller;

import br.com.tcc.dao.ProjetoDao;
import br.com.tcc.model.Projeto;
import br.com.tcc.model.Usuario;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ProjetoController {
    @RequestMapping("/projetos")    
    public String projetos(Model model, HttpSession session) {
        Usuario logado = (Usuario) session.getAttribute("logado");
        List<Projeto> projetos = null;
        try {
            projetos = ProjetoDao.carregar(logado);
        } catch (SQLException ex) {
            model.addAttribute("retorno", "toastr.error('Erro ao carregar projetos !!!');");
            Logger.getLogger(ProjetoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        model.addAttribute("projeto", new Projeto());
        model.addAttribute("projetos", projetos);
        
        return "projetos";
    }
    
    @RequestMapping("/projetos/adicionar")    
    public String projetosAdicionar(Projeto projeto, HttpSession session, RedirectAttributes ra) {
        Usuario logado = (Usuario) session.getAttribute("logado");
        try {
            ProjetoDao.adicionar(projeto, logado);
            ra.addFlashAttribute("retorno", "toastr.success('Projeto adicionado com sucesso !!!');");
        } catch (SQLException ex) {
            ra.addFlashAttribute("retorno", "toastr.error('Erro ao adicionar projeto !!!');");
            Logger.getLogger(ProjetoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return "redirect:/projetos";
    }
    
    @RequestMapping("/projetos/editar")    
    public String projetosEditar(Projeto projeto, RedirectAttributes ra) {
        try {
            ProjetoDao.editar(projeto);
            ra.addFlashAttribute("retorno", "toastr.success('Projeto editado com sucesso !!!');");
        } catch (SQLException ex) {
            ra.addFlashAttribute("retorno", "toastr.error('Erro ao editar projeto !!!');");
            Logger.getLogger(ProjetoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return "redirect:/projetos";
    }
    
    @RequestMapping("/projetos/deletar")    
    public String projetosDeletar(Projeto projeto, RedirectAttributes ra) {
        try {
            ProjetoDao.deletar(projeto);
            ra.addFlashAttribute("retorno", "toastr.success('Projeto deletado com sucesso !!!');");
        } catch (SQLException ex) {
            ra.addFlashAttribute("retorno", "toastr.error('Erro ao deletar projeto !!!');");
            Logger.getLogger(ProjetoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return "redirect:/projetos";
    }
    
    @RequestMapping(value = "/projetos/vizualizar", method= RequestMethod.POST)    
    public String projetosVizualizarPost(Projeto projeto, Model model, RedirectAttributes ra) {
        ra.addFlashAttribute("projeto", projeto);
        ra.addAttribute("id", projeto.getId());
        
        return "redirect:/projetos/vizualizar";
    }
    
    @RequestMapping(value = "/projetos/vizualizar", method= RequestMethod.GET)    
    public String projetosVizualizarGet(Projeto projeto, Model model) {
        try {
            projeto = ProjetoDao.carregar(projeto.getId());
        } catch (SQLException ex) {
            model.addAttribute("retorno", "toastr.error('Erro ao carregar projeto !!!');");
            Logger.getLogger(ProjetoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        model.addAttribute("projeto", projeto);
        
        return "projeto";
    }
}

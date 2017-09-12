package br.com.tcc.controller;

import br.com.tcc.model.*;
import br.com.tcc.dao.*;
import br.com.tcc.singleton.Singleton;
import br.com.tcc.util.Call;
import com.google.gson.Gson;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.io.FileUtils;
import org.rosuda.REngine.REXPMismatchException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ProjetoController {
    @RequestMapping("/projetos")    
    public String projetos(Model model, HttpSession session, HttpServletRequest request) {
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
        ra.addFlashAttribute("projeto", projeto);
        ra.addAttribute("id", projeto.getId());
        
        return "redirect:/projetos/vizualizar";
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
    public String projetosVizualizarGet(Projeto projeto, Model model, HttpSession session, HttpServletRequest request) {
        try {
            projeto = ProjetoDao.carregar(projeto.getId());
        } catch (SQLException ex) {
            model.addAttribute("retorno", "toastr.error('Erro ao carregar projeto !!!');");
            Logger.getLogger(ProjetoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        model.addAttribute("projeto", projeto);
        
        Usuario logado = (Usuario) session.getAttribute("logado");
        String folderPath = Singleton.UPLOAD_DIR + "/"
                                + logado.getEmail() + "/"
                                + projeto.getId().toString();
        //List<String> arq_nomes = ArquivoDao.carregar(folderPath);
        //model.addAttribute("arq_nomes", arq_nomes);
        File folder = new File(folderPath);
        File[] listOfFiles = folder.listFiles();
        
        model.addAttribute("artigos", listOfFiles);
        
        String path = Singleton.UPLOAD_DIR + "/" + logado.getEmail() + "/" + projeto.getId() + "/";
        Call c = new Call();
        List<Artigo> segmentos_artigos = null;
        try {
            segmentos_artigos = c.articlesAnalysis(path);
        } catch (REXPMismatchException ex) {
            Logger.getLogger(ProjetoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        model.addAttribute("segmentos_artigos", segmentos_artigos);
        
        return "projeto";
    }
    
    @RequestMapping(value = "/projetos/artigos/adicionar", method = RequestMethod.POST)    
    public @ResponseBody String projetosArtigosAdicionar(@RequestParam("qqfile") MultipartFile file, @RequestParam("projeto") Integer id, HttpServletRequest request) {
        try {
            Projeto projeto = ProjetoDao.carregar(id);
            Usuario usuario = UsuarioDao.carregar(projeto);
            
            String filePath = Singleton.UPLOAD_DIR + "/"
                                + usuario.getEmail() + "/"
                                + projeto.getId().toString() + "/"
                                + file.getOriginalFilename();
            ArquivoDao.adicionar(file, projeto, filePath);
        } catch (SQLException | IOException | IllegalStateException ex) {
            Logger.getLogger(ProjetoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return "{\"success\": true}";
    }
    
    @RequestMapping("/projetos/artigos/deletar")
    public String projetosArtigosDeletar(@RequestParam("id") int id, @RequestParam("caminho") String caminho, RedirectAttributes ra) {
        Projeto projeto = new Projeto();
        projeto.setId(id);
        
        try {
            ArquivoDao.deletar(projeto, caminho);
        } catch (SQLException ex) {
            Logger.getLogger(ProjetoController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ProjetoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        ra.addFlashAttribute("projeto", projeto);
        ra.addAttribute("id", projeto.getId());
            
        return "redirect:/projetos/vizualizar";
    }
    
    @RequestMapping("/projetos/artigos/vizualizar")    
    public void projetosArtigosVizualizar(@RequestParam("artigo") String nome, @RequestParam("projeto") Integer id, HttpServletResponse response) {
        try {
            Projeto projeto = ProjetoDao.carregar(id);
            Usuario usuario = UsuarioDao.carregar(projeto);
            
            String filePath = Singleton.UPLOAD_DIR + "/" + usuario.getEmail() + "/" + projeto.getId() + "/" + nome;
            
            try {
                File file = new File(filePath);
                FileUtils fu = new FileUtils();
                byte[] bytes = fu.readFileToByteArray(file);

                response.setContentType("application/pdf");
                OutputStream ops = response.getOutputStream();
                ops.write(bytes);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ProjetoController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ProjetoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProjetoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @RequestMapping(value = "/projetos/analisar", method = RequestMethod.GET)    
    public @ResponseBody String projetosAnalisar(@RequestParam("analise") Integer analise, @RequestParam("projeto") Integer id, HttpSession session) {
        Gson g = new Gson();
        List<Artigo> artigos = null;
        if (analise.equals(1)) {
            Call call = new Call();
            try {
                Usuario logado = (Usuario) session.getAttribute("logado");
                String path = Singleton.UPLOAD_DIR + "/" + logado.getEmail() + "/" + id + "/";
                artigos = call.abstractObjective(path);
            } catch (REXPMismatchException ex) {
                Logger.getLogger(ProjetoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return g.toJson(artigos);
    }
    
    @RequestMapping("/projetos/tfidf")    
    public String projetosTfidf(@RequestParam("projeto") Integer id, HttpServletResponse response, Model model) throws REXPMismatchException {
        try {
            Projeto projeto = ProjetoDao.carregar(id);
            Usuario usuario = UsuarioDao.carregar(projeto);
            
            String folderPath = Singleton.UPLOAD_DIR + usuario.getEmail() + "/" + projeto.getId();
            
            
            Call c = new Call();
            List<Texto> lista = c.abstractTfIdf(folderPath);
            model.addAttribute("Lista", lista);
            List<Artigo> lista2 = c.abstractObjective(folderPath);
            model.addAttribute("Lista2", lista2);
            
            String filePath = Singleton.UPLOAD_DIR + usuario.getEmail() + "/" + projeto.getId() + "/" + "resultadoTfidf.pdf";
            try {
                File file = new File(filePath);
                FileUtils fu = new FileUtils();
                byte[] bytes = fu.readFileToByteArray(file);

                response.setContentType("application/pdf");
                OutputStream ops = response.getOutputStream();
                ops.write(bytes);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ProjetoController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ProjetoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProjetoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "resultado";
    }
}

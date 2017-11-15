package br.com.tcc.controller;

import br.com.tcc.model.*;
import br.com.tcc.dao.*;
import br.com.tcc.singleton.Singleton;
import br.com.tcc.util.Call;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.text.Normalizer;
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
import org.rosuda.REngine.REngineException;
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
            String path = Singleton.UPLOAD_DIR + "/" + projeto.getId() + "/";
            ProjetoDao.deletar(path, projeto);
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
    public String projetosVizualizarGet(Projeto projeto, Model model, HttpServletRequest request, HttpSession session) throws REngineException, IOException, FileNotFoundException, ClassNotFoundException, SQLException {
        Gson g = new Gson();
        
        try {
            projeto = ProjetoDao.carregar(projeto.getId());
        } catch (SQLException ex) {
            model.addAttribute("retorno", "toastr.error('Erro ao carregar projeto !!!');");
            Logger.getLogger(ProjetoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        model.addAttribute("projeto", projeto);
        model.addAttribute("sinonimosObjetivoJson", g.toJson(projeto.getSinonimosObjetivo()));
        model.addAttribute("sinonimosMetodologiaJson", g.toJson(projeto.getSinonimosMetodologia()));
        model.addAttribute("sinonimosResultadoJson", g.toJson(projeto.getSinonimosResultado()));
        Usuario lider = UsuarioDao.carregar(projeto);
        Usuario logado = (Usuario) session.getAttribute("logado");
        
        if (logado.getEmail().equals(lider.getEmail())) 
            projeto.setLider(1);
        else 
            projeto.setLider(0);
        
        String path = Singleton.UPLOAD_DIR + "/" + projeto.getId() + "/";
        Call c = new Call();
        List<Artigo> segmentos_artigos = null;
        ArrayList<String> tfidf = new ArrayList<String>();
        try {
            Pesquisa p = new Pesquisa();
            p.setUsuario(lider);
            p.setProjeto(projeto);
            if(projeto.getSinonimosObjetivo().size() > 0 && projeto.getSinonimosMetodologia().size() > 0 && projeto.getSinonimosResultado().size() > 0){
                segmentos_artigos = c.articlesAnalysis(path, p);
                tfidf = c.graphicTfIdf(path, p);
            }
            else{
                tfidf = null;
                model.addAttribute("retornoSinonimos", 1);
            }
        } catch (REXPMismatchException ex) {
            Logger.getLogger(ProjetoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        model.addAttribute("segmentos_artigos", segmentos_artigos);
        
        if (tfidf != null) {
            model.addAttribute("tfidfWord", tfidf.get(0));
            model.addAttribute("tfidfBigram", tfidf.get(1));
            model.addAttribute("tfidfTrigram", tfidf.get(2));
        }
                
        return "projeto";
    }
    
    @RequestMapping(value = "/projetos/artigos/adicionar", method = RequestMethod.POST)    
    public @ResponseBody String projetosArtigosAdicionar(@RequestParam("qqfile") MultipartFile file, @RequestParam("projeto") Integer id, HttpServletRequest request) {
        try {
            System.out.println("projetosArtigosAdicionar called");
            Projeto projeto = ProjetoDao.carregar(id);
            Usuario usuario = UsuarioDao.carregar(projeto);
            
            String filePath = Singleton.UPLOAD_DIR + "/"
                                + projeto.getId().toString() + "/";
            String nomeArquivo = Normalizer.normalize(file.getOriginalFilename(), Normalizer.Form.NFD).replaceAll("[^a-zA-Z\\d_ ]", "");
            nomeArquivo = nomeArquivo.substring(0, nomeArquivo.length()-3) + ".pdf";
            ArquivoDao.adicionar(file, projeto, filePath, nomeArquivo);
        } catch (SQLException | IOException | IllegalStateException ex) {
            Logger.getLogger(ProjetoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return "{\"success\": true}";
    }
    
    @RequestMapping("/projetos/artigos/deletar")
    public String projetosArtigosDeletar(@RequestParam("id") Integer id, @RequestParam("projeto_id") Integer projeto_id, RedirectAttributes ra) {
        Projeto projeto = new Projeto();
        projeto.setId(projeto_id);
        
        try {
            String filePath = ArquivoDao.carregarFilePath(id);
            ArquivoDao.deletar(filePath, id);
        } catch (SQLException ex) {
            Logger.getLogger(ProjetoController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ProjetoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        ra.addFlashAttribute("projeto", projeto);
        ra.addAttribute("id", projeto.getId());
            
        return "redirect:/projetos/vizualizar";
    }
    
    @RequestMapping("/projetos/artigos/visualizar")    
    public void projetosArtigosVisualizar(@RequestParam("id") Integer id, HttpServletResponse response) {
        try {
            String filePath = ArquivoDao.carregarFilePath(id);
            
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
    
    @RequestMapping(value = "/projetos/ordenar", method = RequestMethod.GET)    
    public @ResponseBody String projetosOrdenar(@RequestParam("projeto") Integer id,
                                                @RequestParam("segment") String segment,
                                                @RequestParam("keywords") String keywords) throws IOException, REngineException, ClassNotFoundException {
        Gson g = new Gson();
        Call call = new Call();
        Type listType = new TypeToken<ArrayList<Tag>>(){}.getType();
        List<Tag> tags = g.fromJson(keywords, listType);
        List<Artigo> artigos = null;
        
        try {
            Projeto projeto = ProjetoDao.carregar(id);
            Usuario lider = UsuarioDao.carregar(projeto);
            String path = Singleton.UPLOAD_DIR + "/" + id + "/";
            Pesquisa p = new Pesquisa();
            p.setUsuario(lider);
            p.setProjeto(projeto);
            artigos = call.ordenar(segment, tags, p, path);
        } catch (REXPMismatchException ex) {
            Logger.getLogger(ProjetoController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ProjetoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return g.toJson(artigos);
    }
    
    /*@RequestMapping("/projetos/artigos/agrupar")    
    public String projetosAgrupar(@RequestParam("grupos") int quant, @RequestParam("forma") String forma, @RequestParam("id") int id, Model model, HttpSession session, HttpServletRequest request) {
        Projeto projeto=null;
        try {
            projeto = ProjetoDao.carregar(id);
        } catch (SQLException ex) {
            model.addAttribute("retorno", "toastr.error('Erro ao carregar projeto !!!');");
            Logger.getLogger(ProjetoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        model.addAttribute("projeto", projeto);
        
        Usuario logado = (Usuario) session.getAttribute("logado");
        String path = Singleton.UPLOAD_DIR + "/" + projeto.getId() + "/";
        List<Grupo> grupos = null;
        Call c = new Call();
        try {
            grupos = c.toGroups(path, forma, quant);
        } catch (REXPMismatchException ex) {
            Logger.getLogger(ProjetoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        model.addAttribute("Agrupamentos", grupos);
        return "mostra";
    }*/
    
    @RequestMapping(value = "/projetos/artigos/agrupar", method = RequestMethod.GET)    
    public @ResponseBody String projetosAgrupar(@RequestParam("grupos") int quant, @RequestParam("forma") String forma, @RequestParam("id") int id) throws IOException, REngineException, SQLException, ClassNotFoundException {
        Projeto projeto=null;
        Gson g = new Gson();
        try {
            projeto = ProjetoDao.carregar(id);
        } catch (SQLException ex) {
            Logger.getLogger(ProjetoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Usuario lider = UsuarioDao.carregar(projeto);
        String path = Singleton.UPLOAD_DIR + "/" + projeto.getId() + "/";
        List<Grupo> grupos = null;
        Call c = new Call();
        try {
            Pesquisa p = new Pesquisa();
            p.setUsuario(lider);
            p.setProjeto(projeto);
            p = ProjetoDao.carregarPesquisa(p, 2);
            grupos = c.toGroups(forma, quant, p, path);
        } catch (REXPMismatchException ex) {
            Logger.getLogger(ProjetoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return g.toJson(grupos);
    }
    
    @RequestMapping(value = "/projetos/usuarios", method = RequestMethod.GET)    
    public @ResponseBody String projetosUsuarios(@RequestParam("projeto") Integer id) {
        List<Usuario> usuarios = null;
        Gson g = new Gson();
        
        try {
            usuarios = UsuarioDao.carregar(id);
        } catch (SQLException ex) {
            Logger.getLogger(ProjetoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return g.toJson(usuarios);
    }
    
    @RequestMapping(value = "/projetos/usuarios/adicionar", method = RequestMethod.POST)    
    public @ResponseBody String projetosUsuariosAdicionar(@RequestParam("projeto") Integer id, @RequestParam("email") String email) {
        Gson g = new Gson();
        Usuario usuario = null;
        
        try {
            usuario = UsuarioDao.carregar(email);
            
            if (usuario == null || ProjetoDao.jaTem(id, usuario)) return g.toJson(null);
            
            if (usuario != null)
                ProjetoDao.adicionar(id, usuario);
        } catch (SQLException ex) {
            Logger.getLogger(ProjetoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return g.toJson(usuario);
    }
    
    @RequestMapping(value = "/projetos/usuarios/deletar", method = RequestMethod.POST)    
    public @ResponseBody String projetosUsuariosDeletar(@RequestParam("projeto") Integer id, @RequestParam("email") String email) {
        Gson g = new Gson();
        Usuario usuario = null;
        
        try {
            usuario = UsuarioDao.carregar(email);
            
            ProjetoDao.deletar(id, usuario);
        } catch (SQLException ex) {
            Logger.getLogger(ProjetoController.class.getName()).log(Level.SEVERE, null, ex);
            return g.toJson(null);
        }
        
        return g.toJson(usuario);
    }
}

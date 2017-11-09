package br.com.tcc.controller;

import br.com.tcc.dao.ArtigoDao;
import br.com.tcc.model.Observacao;
import br.com.tcc.model.Usuario;
import com.google.gson.Gson;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ArtigoController {
    @RequestMapping("/artigos")    
    public String artigos() {
        return "artigos";
    }
    
    @RequestMapping(value = "/artigos/observacao", method = RequestMethod.GET)    
    public @ResponseBody String artigosObservacao(@RequestParam("artigo") Integer id, HttpSession session) {
        Gson g = new Gson();
        Usuario logado = (Usuario) session.getAttribute("logado");
        String observacao = "";
        List<Observacao> observacoes = new ArrayList<Observacao>();
        
        try {
            observacao = ArtigoDao.getObservacao(id, logado);
            
            observacoes = ArtigoDao.getObservacoes(id, logado);
        } catch (SQLException ex) {
            Logger.getLogger(ArtigoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return "{\"observacao\":\"" + observacao + "\",\"observacoes\":" + g.toJson(observacoes) + "}";
    }
    
    @RequestMapping(value = "/artigos/observacao/editar", method = RequestMethod.GET)    
    public @ResponseBody void artigosObservacaoEditar(@RequestParam("artigo") Integer id, @RequestParam("observacao") String observacao, HttpSession session) {
        Usuario logado = (Usuario) session.getAttribute("logado");
        
        try {
            ArtigoDao.editarObservacao(id, logado, observacao);
        } catch (SQLException ex) {
            Logger.getLogger(ArtigoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

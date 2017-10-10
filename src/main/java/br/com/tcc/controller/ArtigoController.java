package br.com.tcc.controller;

import br.com.tcc.dao.ArquivoDao;
import br.com.tcc.dao.UsuarioDao;
import br.com.tcc.model.Usuario;
import com.google.gson.Gson;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    
    @RequestMapping(value = "/artigos/usuarios", method = RequestMethod.GET)
    public @ResponseBody String artigos_usuarios(@RequestParam("art_id") Integer id) throws SQLException {
        Gson g = new Gson();
        
        List<Usuario> usuarios = ArquivoDao.carregarUsuarios(id);
        
        return g.toJson(usuarios);
    }
    
    @RequestMapping(value = "/artigos/usuarios/adicionar", method = RequestMethod.GET)
    public @ResponseBody String artigos_usuarios_adicionar(@RequestParam("art_id") Integer id, @RequestParam("usu_email") String email) throws SQLException {
        Gson g = new Gson();
        
        if (UsuarioDao.existe(email)) {
            ArquivoDao.adicionarUsuario(email, id);
            return g.toJson(UsuarioDao.carregar(email));
        } else {
            return g.toJson("false");
        }
    }
    
    @RequestMapping(value = "/artigos/usuarios/deletar", method = RequestMethod.GET)
    public @ResponseBody String artigos_usuarios_deletar(@RequestParam("art_id") Integer id, @RequestParam("usu_email") String email) throws SQLException {
        Gson g = new Gson();
        String retorno = "true";
        
        ArquivoDao.deletarUsuario(email, id);
        
        return g.toJson(retorno);
    }
}

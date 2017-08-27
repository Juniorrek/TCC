package br.com.tcc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ArtigoController {
    @RequestMapping("/artigos")    
    public String artigos(Model model) {
        return "artigos";
    }
}

package br.com.tcc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ResultadoController {
    @RequestMapping("/resultado")    
    public String resultado() {
        return "resultado";
    }
}

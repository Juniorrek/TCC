package br.com.tcc.model;

import java.util.List;

public class Grupo implements java.io.Serializable {
    private List<Artigo> artigos;
    private List<String> keywords;
    private int numero;

    public Grupo() {
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }
     
    public List<Artigo> getArtigos() {
        return artigos;
    }

    public void setArtigos(List<Artigo> artigos) {
        this.artigos = artigos;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }
    
    
}

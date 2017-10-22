package br.com.tcc.model;

import java.util.List;

public class Projeto implements java.io.Serializable {
    private Integer id;
    private String nome;
    private String descricao;
    private List<Artigo> artigos;
    private List<String> sinonimosObjetivo;
    private List<String> sinonimosMetodologia;
    private List<String> sinonimosResultado;
    private int lider;
    private String email;
    
    public Projeto() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<Artigo> getArtigos() {
        return artigos;
    }

    public void setArtigos(List<Artigo> artigos) {
        this.artigos = artigos;
    }

    public List<String> getSinonimosObjetivo() {
        return sinonimosObjetivo;
    }

    public void setSinonimosObjetivo(List<String> sinonimosObjetivo) {
        this.sinonimosObjetivo = sinonimosObjetivo;
    }

    public List<String> getSinonimosMetodologia() {
        return sinonimosMetodologia;
    }

    public void setSinonimosMetodologia(List<String> sinonimosMetodologia) {
        this.sinonimosMetodologia = sinonimosMetodologia;
    }

    public List<String> getSinonimosResultado() {
        return sinonimosResultado;
    }

    public void setSinonimosResultado(List<String> sinonimosResultado) {
        this.sinonimosResultado = sinonimosResultado;
    }
    
    public String getSinonimosToR(String segmento) {
        String retorno = "";
        
        if ("objetivo".equals(segmento)) {
            for (String s : this.sinonimosObjetivo) {
                retorno += "\"" + s + "\",";
            }
            if (!"".equals(retorno)) retorno = retorno.substring(0, retorno.length() - 1);
        } else if ("metodologia".equals(segmento)) {
            for (String s : this.sinonimosMetodologia) {
                retorno += "\"" + s + "\",";
            }
            if (!"".equals(retorno)) retorno = retorno.substring(0, retorno.length() - 1);
            
        } else if ("resultado".equals(segmento)) {
            for (String s : sinonimosResultado) {
                retorno += "\"" + s + "\",";
            }
            if (!"".equals(retorno)) retorno = retorno.substring(0, retorno.length() - 1);
        }
        
        return retorno;
    }

    public int getLider() {
        return lider;
    }

    public void setLider(int lider) {
        this.lider = lider;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

package br.com.tcc.model;

import java.util.List;

public class Pesquisa implements java.io.Serializable{
    private List<Artigo> lista;
    private List<String> termosRelevantes; 
    private String sinonimosObjetivo, sinonimosMetodologia, sinonimosResultado;
    private Usuario usuario;
    private Projeto projeto;

    public Pesquisa() {}

    public List<Artigo> getLista() {
        return lista;
    }

    public void setLista(List<Artigo> lista) {
        this.lista = lista;
    }

    public List<String> getTermosRelevantes() {
        return termosRelevantes;
    }

    public void setTermosRelevantes(List<String> termosRelevantes) {
        this.termosRelevantes = termosRelevantes;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Projeto getProjeto() {
        return projeto;
    }

    public void setProjeto(Projeto projeto) {
        this.projeto = projeto;
    }

    public String getSinonimosObjetivo() {
        return sinonimosObjetivo;
    }

    public void setSinonimosObjetivo(String sinonimosObjetivo) {
        this.sinonimosObjetivo = sinonimosObjetivo;
    }

    public String getSinonimosMetodologia() {
        return sinonimosMetodologia;
    }

    public void setSinonimosMetodologia(String sinonimosMetodologia) {
        this.sinonimosMetodologia = sinonimosMetodologia;
    }

    public String getSinonimosResultado() {
        return sinonimosResultado;
    }

    public void setSinonimosResultado(String sinonimosResultado) {
        this.sinonimosResultado = sinonimosResultado;
    }
    
    
}

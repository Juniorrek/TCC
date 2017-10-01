package br.com.tcc.model;

import java.util.List;

public class Projeto implements java.io.Serializable {
    private Integer id;
    private String nome;
    private String descricao;
    private List<String> sinonimosObjetivo;
    private List<String> sinonimosMetodologia;
    private List<String> sinonimosResultado;
    
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
}

package br.com.tcc.model;

public class Observacao implements java.io.Serializable {
    private String usuario_nome;
    private String observacao;

    public Observacao() {}

    public String getUsuario_nome() {
        return usuario_nome;
    }

    public void setUsuario_nome(String usuario_nome) {
        this.usuario_nome = usuario_nome;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
}

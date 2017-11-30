package br.com.tcc.model;

public class Cadastro extends Usuario implements java.io.Serializable {
    private String confirmacaoSenha;
    
    public Cadastro() {}

    public String getConfirmacaoSenha() {
        return confirmacaoSenha;
    }

    public void setConfirmacaoSenha(String confirmacaoSenha) {
        this.confirmacaoSenha = confirmacaoSenha;
    }
}

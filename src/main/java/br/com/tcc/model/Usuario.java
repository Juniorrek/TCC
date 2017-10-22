package br.com.tcc.model;

import org.hibernate.validator.constraints.Email;

public class Usuario implements java.io.Serializable {
    @Email
    private String email;
    private String nome;
    private String senha;
    
    public Usuario() {}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}

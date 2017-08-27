package br.com.tcc.model;

import org.hibernate.validator.constraints.Email;

public class Login implements java.io.Serializable {
    @Email
    private String email;
    private String senha;
    
    public Login() {}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}

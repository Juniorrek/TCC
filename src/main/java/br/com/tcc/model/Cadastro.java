package br.com.tcc.model;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

public class Cadastro extends Usuario implements java.io.Serializable {
    private String confirmacaoSenha;
    
    public Cadastro() {}

    public String getConfirmacaoSenha() {
        return confirmacaoSenha;
    }

    public void setConfirmacaoSenha(String confirmacaoSenha) {
        this.confirmacaoSenha = confirmacaoSenha;
    }
    
    public void enviarEmailConfirmacao() {
        try {
            Email email = new SimpleEmail();
            email.setHostName("smtp.googlemail.com");
            email.setSmtpPort(465);
            email.setAuthenticator(new DefaultAuthenticator("tritomus2017@gmail.com", "2017tritomus"));
            email.setSSLOnConnect(true);
            email.setFrom("tritomus2017@gmail.com");
            email.setSubject("TestMail");
            email.setMsg("http://localhost:8084/TCC/confirmarCadastro?email=" + this.getEmail());
            email.addTo(this.getEmail());
            email.send();
        } catch (EmailException ex) {
            Logger.getLogger(Cadastro.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

package br.com.tcc.dao;

import br.com.tcc.factory.ConnectionFactory;
import br.com.tcc.model.Cadastro;
import br.com.tcc.model.Projeto;
import br.com.tcc.model.Usuario;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

public class UsuarioDao {
    public static void cadastrar(Cadastro cadastro) throws SQLException {
        Connection connection = new ConnectionFactory().getConnection();
        PreparedStatement stmt = null;
        String senha = "";
        
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(cadastro.getSenha().getBytes(), 0, cadastro.getSenha().length());
            senha = new BigInteger(1, md.digest()).toString(16);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            stmt = connection.prepareStatement("INSERT INTO Usuario (email, nome, senha, confirmacao) "
                                                + "VALUES (?, ?, ?, ?)");
            stmt.setString(1, cadastro.getEmail());
            stmt.setString(2, cadastro.getNome());
            stmt.setString(3, senha);
            stmt.setInt(4, 0);
            
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        } finally {
            if (stmt != null)
                try { stmt.close(); }
                catch (SQLException ex) { Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex); }
            if (connection != null)
                try { connection.close(); }
                catch (SQLException ex) { Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex); }
        }
    }
    
    public static void confirmarCadastro(String email) throws SQLException {
        Connection connection = new ConnectionFactory().getConnection();
        PreparedStatement stmt = null;
        
        try {
            stmt = connection.prepareStatement("UPDATE Usuario "
                                                + "SET confirmacao = ? "
                                                + "WHERE email = ?");
            stmt.setInt(1, 1);
            stmt.setString(2, email);
            
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        } finally {
            if (stmt != null)
                try { stmt.close(); }
                catch (SQLException ex) { Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex); }
            if (connection != null)
                try { connection.close(); }
                catch (SQLException ex) { Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex); }
        }
    }
    
    public static List<Usuario> carregar(Integer projetoId) throws SQLException {
        Connection connection = new ConnectionFactory().getConnection();
        PreparedStatement stmt = null;
        List<Usuario> usuarios = new ArrayList<Usuario>();
        
        try {
            stmt = connection.prepareStatement("SELECT u.* FROM Rel_Usu_Pro r "
                                                + "JOIN Usuario u ON u.email = r.usu_email "
                                                + "WHERE r.pro_id = ?");
            stmt.setInt(1, projetoId);
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setEmail(rs.getString("email"));
                usuario.setNome(rs.getString("nome"));
                
                usuarios.add(usuario);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProjetoDao.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        } finally {
            if (stmt != null)
                try { stmt.close(); }
                catch (SQLException ex) { Logger.getLogger(ProjetoDao.class.getName()).log(Level.SEVERE, null, ex); }
            if (connection != null)
                try { connection.close(); }
                catch (SQLException ex) { Logger.getLogger(ProjetoDao.class.getName()).log(Level.SEVERE, null, ex); }
        }
        
        return usuarios;
    }
    
    public static Usuario carregar(Projeto projeto) throws SQLException {
        Connection connection = new ConnectionFactory().getConnection();
        PreparedStatement stmt = null;
        
        try {
            stmt = connection.prepareStatement("SELECT u.* FROM Usuario u "
                                                + "JOIN Projeto p ON p.email = u.email "
                                                + "WHERE p.id = ?");
            stmt.setInt(1, projeto.getId());
            
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setEmail(rs.getString("email"));
                usuario.setNome(rs.getString("nome"));
                
                return usuario;
            } else {
                return null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProjetoDao.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        } finally {
            if (stmt != null)
                try { stmt.close(); }
                catch (SQLException ex) { Logger.getLogger(ProjetoDao.class.getName()).log(Level.SEVERE, null, ex); }
            if (connection != null)
                try { connection.close(); }
                catch (SQLException ex) { Logger.getLogger(ProjetoDao.class.getName()).log(Level.SEVERE, null, ex); }
        }
    }
    
    public static int validaLogin(String email, String senha) throws SQLException {
        Connection connection = new ConnectionFactory().getConnection();
        PreparedStatement stmt = null;
        
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(senha.getBytes(), 0, senha.length());
            senha = new BigInteger(1, md.digest()).toString(16);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            stmt = connection.prepareStatement("SELECT * FROM Usuario "
                                                + "WHERE email = ? AND senha = ?");
            stmt.setString(1, email);
            stmt.setString(2, senha);
            
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                if (rs.getInt("confirmacao") != 0) {
                    return 0;
                } else {
                    return 1;
                }
            } else {
                return 2;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        } finally {
            if (stmt != null)
                try { stmt.close(); }
                catch (SQLException ex) { Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex); }
            if (connection != null)
                try { connection.close(); }
                catch (SQLException ex) { Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex); }
        }
    }
    
    public static Usuario carregar(String email) throws SQLException {
        Connection connection = new ConnectionFactory().getConnection();
        PreparedStatement stmt = null;
        
        try {
            stmt = connection.prepareStatement("SELECT * FROM Usuario "
                                                + "WHERE email = ?");
            stmt.setString(1, email);
            
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Usuario usuario = new Usuario();
                
                usuario.setEmail(rs.getString("email"));
                usuario.setNome(rs.getString("nome"));
                
                return usuario;
            } else {
                return null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        } finally {
            if (stmt != null)
                try { stmt.close(); }
                catch (SQLException ex) { Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex); }
            if (connection != null)
                try { connection.close(); }
                catch (SQLException ex) { Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex); }
        }
    }
    
    public static boolean existe(String email) throws SQLException {
        Connection connection = new ConnectionFactory().getConnection();
        PreparedStatement stmt = null;
        
        try {
            stmt = connection.prepareStatement("SELECT * FROM Usuario "
                                                + "WHERE email = ?");
            stmt.setString(1, email);
            
            ResultSet rs = stmt.executeQuery();
            
            return rs.next();
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        } finally {
            if (stmt != null)
                try { stmt.close(); }
                catch (SQLException ex) { Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex); }
            if (connection != null)
                try { connection.close(); }
                catch (SQLException ex) { Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex); }
        }
    }
    
    public static boolean confirmado(String email) throws SQLException {
        Connection connection = new ConnectionFactory().getConnection();
        PreparedStatement stmt = null;
        
        try {
            stmt = connection.prepareStatement("SELECT * FROM Usuario "
                                                + "WHERE email = ? AND confirmacao = 1");
            stmt.setString(1, email);
            
            ResultSet rs = stmt.executeQuery();
            
            return rs.next();
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        } finally {
            if (stmt != null)
                try { stmt.close(); }
                catch (SQLException ex) { Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex); }
            if (connection != null)
                try { connection.close(); }
                catch (SQLException ex) { Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex); }
        }
    }
    
    public static void enviarEmailConfirmacao(String email, String url) throws SQLException, EmailException {
        String token = UUID.randomUUID().toString().replace("-", "").toUpperCase();
        Connection connection = new ConnectionFactory().getConnection();
        PreparedStatement stmt = null;
        
        try {
            stmt = connection.prepareStatement("INSERT INTO ConfirmarEmailToken (email, token) "
                                                + "VALUES (?, ?)");
            stmt.setString(1, email);
            stmt.setString(2, token);
            stmt.executeUpdate();
            
            Email e = new SimpleEmail();
            e.setHostName("smtp.googlemail.com");
            e.setSmtpPort(465);
            e.setAuthenticator(new DefaultAuthenticator("tritomus2017@gmail.com", "2017tritomus"));
            e.setSSLOnConnect(true);
            e.setFrom("tritomus2017@gmail.com");
            e.setSubject("Confirmar email");
            e.setMsg(url + "/TCC/confirmarCadastro?token=" + token + "&email=" + email);
            e.addTo(email);
            String aa = e.send();
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        } catch (EmailException ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        } finally {
            if (stmt != null)
                try { stmt.close(); }
                catch (SQLException ex) { Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex); }
            if (connection != null)
                try { connection.close(); }
                catch (SQLException ex) { Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex); }
        }
    }
    
    public static void recuperarSenha(String email, String url) throws SQLException, EmailException {
        String token = UUID.randomUUID().toString().replace("-", "").toUpperCase();
        Connection connection = new ConnectionFactory().getConnection();
        PreparedStatement stmt = null;
        
        try {
            stmt = connection.prepareStatement("INSERT INTO RecuperarSenhaToken (email, token) "
                                                + "VALUES (?, ?)");
            stmt.setString(1, email);
            stmt.setString(2, token);
            stmt.executeUpdate();
            
            Email e = new SimpleEmail();
            e.setHostName("smtp.googlemail.com");
            e.setSmtpPort(465);
            e.setAuthenticator(new DefaultAuthenticator("tritomus2017@gmail.com", "2017tritomus"));
            e.setSSLOnConnect(true);
            e.setFrom("tritomus2017@gmail.com");
            e.setSubject("Redefinir senha");
            e.setMsg(url + "/TCC/esqueci_senha/redefinir?token=" + token + "&email=" + email);
            e.addTo(email);
            e.send();
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        } catch (EmailException ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        } finally {
            if (stmt != null)
                try { stmt.close(); }
                catch (SQLException ex) { Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex); }
            if (connection != null)
                try { connection.close(); }
                catch (SQLException ex) { Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex); }
        }
    }
    
    public static boolean validaRecuperarSenha(String email, String token) throws SQLException {
        Connection connection = new ConnectionFactory().getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            stmt = connection.prepareStatement("SELECT * FROM RecuperarSenhaToken "
                                                + "WHERE email = ? AND token = ?");
            stmt.setString(1, email);
            stmt.setString(2, token);
            rs = stmt.executeQuery();
            
            return rs.next();
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        } finally {
            if (rs != null)
                try { rs.close(); }
                catch (SQLException ex) { Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex); }
            if (stmt != null)
                try { stmt.close(); }
                catch (SQLException ex) { Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex); }
            if (connection != null)
                try { connection.close(); }
                catch (SQLException ex) { Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex); }
        }
    }
    
    public static boolean validarConfirmarEmail(String email, String token) throws SQLException {
        Connection connection = new ConnectionFactory().getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            stmt = connection.prepareStatement("SELECT * FROM ConfirmarEmailToken "
                                                + "WHERE email = ? AND token = ?");
            stmt.setString(1, email);
            stmt.setString(2, token);
            rs = stmt.executeQuery();
            
            return rs.next();
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        } finally {
            if (rs != null)
                try { rs.close(); }
                catch (SQLException ex) { Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex); }
            if (stmt != null)
                try { stmt.close(); }
                catch (SQLException ex) { Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex); }
            if (connection != null)
                try { connection.close(); }
                catch (SQLException ex) { Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex); }
        }
    }
    
    public static void apagarTokenRecuperacao(String email, String token) throws SQLException {
        Connection connection = new ConnectionFactory().getConnection();
        PreparedStatement stmt = null;
        
        try {
            //APAGA O TOKEN GERADO
            stmt = connection.prepareStatement("DELETE FROM RecuperarSenhaToken "
                                                + "WHERE email = ? AND token = ?");
            stmt.setString(1, email);
            stmt.setString(2, token);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        } finally {
            if (stmt != null)
                try { stmt.close(); }
                catch (SQLException ex) { Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex); }
            if (connection != null)
                try { connection.close(); }
                catch (SQLException ex) { Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex); }
        }
    }
    
    public static void apagarTokenConfirmacao(String email, String token) throws SQLException {
        Connection connection = new ConnectionFactory().getConnection();
        PreparedStatement stmt = null;
        
        try {
            //APAGA O TOKEN GERADO
            stmt = connection.prepareStatement("DELETE FROM ConfirmarEmailToken "
                                                + "WHERE email = ? AND token = ?");
            stmt.setString(1, email);
            stmt.setString(2, token);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        } finally {
            if (stmt != null)
                try { stmt.close(); }
                catch (SQLException ex) { Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex); }
            if (connection != null)
                try { connection.close(); }
                catch (SQLException ex) { Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex); }
        }
    }
    
    public static void redefinirSenha(String email, String senha) throws SQLException {
        Connection connection = new ConnectionFactory().getConnection();
        PreparedStatement stmt = null;
        
        //CRIPTOGRAFA
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(senha.getBytes(), 0, senha.length());
            senha = new BigInteger(1, md.digest()).toString(16);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            //ALTERA SENHA
            stmt = connection.prepareStatement("UPDATE Usuario SET senha = ? WHERE email = ?");
            stmt.setString(1, senha);
            stmt.setString(2, email);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        } finally {
            if (stmt != null)
                try { stmt.close(); }
                catch (SQLException ex) { Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex); }
            if (connection != null)
                try { connection.close(); }
                catch (SQLException ex) { Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex); }
        }
    }
    
    public static boolean validaSenhaAtual(Usuario logado, String novaSenha) throws SQLException {
        Connection connection = new ConnectionFactory().getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        //CRIPTOGRAFA
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(novaSenha.getBytes(), 0, novaSenha.length());
            novaSenha = new BigInteger(1, md.digest()).toString(16);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            stmt = connection.prepareStatement("SELECT senha FROM Usuario WHERE email = ? AND senha = ?");
            stmt.setString(1, logado.getEmail());
            stmt.setString(2, novaSenha);
            rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        } finally {
            if (rs != null)
                try { rs.close(); }
                catch (SQLException ex) { Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex); }
            if (stmt != null)
                try { stmt.close(); }
                catch (SQLException ex) { Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex); }
            if (connection != null)
                try { connection.close(); }
                catch (SQLException ex) { Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex); }
        }
    }
}

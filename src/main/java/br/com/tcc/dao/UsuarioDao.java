package br.com.tcc.dao;

import br.com.tcc.factory.ConnectionFactory;
import br.com.tcc.model.Cadastro;
import br.com.tcc.model.Usuario;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
}

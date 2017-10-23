package br.com.tcc.dao;

import br.com.tcc.factory.ConnectionFactory;
import br.com.tcc.model.Observacao;
import br.com.tcc.model.Projeto;
import br.com.tcc.model.Usuario;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.web.multipart.MultipartFile;

public class ArtigoDao {
    public static String getObservacao(Integer id, Usuario usuario) throws SQLException {
        Connection connection = new ConnectionFactory().getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            stmt = connection.prepareStatement("SELECT * FROM Rel_Usu_Art WHERE art_id = ? AND usu_email = ?");
            stmt.setInt(1, id);
            stmt.setString(2, usuario.getEmail());
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getString("observacoes");
            } else {
                return "";
            }
        } catch (SQLException ex) {
            Logger.getLogger(ArtigoDao.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        } finally {
            if (rs != null)
                try { rs.close(); }
                catch (SQLException ex) { Logger.getLogger(ArtigoDao.class.getName()).log(Level.SEVERE, null, ex); }
            if (stmt != null)
                try { stmt.close(); }
                catch (SQLException ex) { Logger.getLogger(ArtigoDao.class.getName()).log(Level.SEVERE, null, ex); }
            if (connection != null)
                try { connection.close(); }
                catch (SQLException ex) { Logger.getLogger(ArtigoDao.class.getName()).log(Level.SEVERE, null, ex); }
        }
    }
    
    public static List<Observacao> getObservacoes(Integer id, Usuario usuario) throws SQLException {
        Connection connection = new ConnectionFactory().getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Observacao> observacoes = new ArrayList<Observacao>();
        
        try {
            stmt = connection.prepareStatement("SELECT a.observacoes, b.nome FROM Rel_Usu_Art a JOIN Usuario b ON b.email = a.usu_email WHERE art_id = ? AND usu_email != ?");
            stmt.setInt(1, id);
            stmt.setString(2, usuario.getEmail());
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Observacao obs = new Observacao();
                obs.setObservacao(rs.getString("observacoes"));
                obs.setUsuario_nome(rs.getString("nome"));
                
                observacoes.add(obs);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ArtigoDao.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        } finally {
            if (rs != null)
                try { rs.close(); }
                catch (SQLException ex) { Logger.getLogger(ArtigoDao.class.getName()).log(Level.SEVERE, null, ex); }
            if (stmt != null)
                try { stmt.close(); }
                catch (SQLException ex) { Logger.getLogger(ArtigoDao.class.getName()).log(Level.SEVERE, null, ex); }
            if (connection != null)
                try { connection.close(); }
                catch (SQLException ex) { Logger.getLogger(ArtigoDao.class.getName()).log(Level.SEVERE, null, ex); }
        }
        
        return observacoes;
    }
    
    public static void editarObservacao(Integer id, Usuario usuario, String observacao) throws SQLException {
        Connection connection = new ConnectionFactory().getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            if (observacao != null && observacao != "") {
                stmt = connection.prepareStatement("INSERT INTO Rel_Usu_Art (art_id, usu_email, observacoes) VALUES (?, ?, ?) "
                                                    + "ON DUPLICATE KEY UPDATE observacoes = ?");
                stmt.setInt(1, id);
                stmt.setString(2, usuario.getEmail());
                stmt.setString(3, observacao);
                stmt.setString(4, observacao);
                stmt.executeUpdate();
            } else {
                stmt = connection.prepareStatement("DELETE FROM Rel_Usu_Art WHERE art_id = ? AND usu_email = ?");
                stmt.setInt(1, id);
                stmt.setString(2, usuario.getEmail());
                stmt.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ArtigoDao.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        } finally {
            if (rs != null)
                try { rs.close(); }
                catch (SQLException ex) { Logger.getLogger(ArtigoDao.class.getName()).log(Level.SEVERE, null, ex); }
            if (stmt != null)
                try { stmt.close(); }
                catch (SQLException ex) { Logger.getLogger(ArtigoDao.class.getName()).log(Level.SEVERE, null, ex); }
            if (connection != null)
                try { connection.close(); }
                catch (SQLException ex) { Logger.getLogger(ArtigoDao.class.getName()).log(Level.SEVERE, null, ex); }
        }
    }
}

package br.com.tcc.dao;

import br.com.tcc.factory.ConnectionFactory;
import br.com.tcc.model.Projeto;
import br.com.tcc.model.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProjetoDao {
    public static void adicionar(Projeto projeto, Usuario usuario) throws SQLException {
        Connection connection = new ConnectionFactory().getConnection();
        PreparedStatement stmt = null;
        
        try {
            stmt = connection.prepareStatement("INSERT INTO Projeto (nome, descricao, email) "
                                                + "VALUES (?, ?, ?)");
            stmt.setString(1, projeto.getNome());
            stmt.setString(2, projeto.getDescricao());
            stmt.setString(3, usuario.getEmail());
            
            stmt.executeUpdate();
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
    
    public static List<Projeto> carregar(Usuario usuario) throws SQLException {
        Connection connection = new ConnectionFactory().getConnection();
        PreparedStatement stmt = null;
        List<Projeto> projetos = new ArrayList<Projeto>();
        
        try {
            stmt = connection.prepareStatement("SELECT * FROM Projeto "
                                                + "WHERE email = ?");
            stmt.setString(1, usuario.getEmail());
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Projeto projeto = new Projeto();
                projeto.setId(rs.getInt("id"));
                projeto.setNome(rs.getString("nome"));
                projeto.setDescricao(rs.getString("descricao"));
                
                projetos.add(projeto);
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
        
        return projetos;
    }
    
    public static Projeto carregar(Integer id) throws SQLException {
        Connection connection = new ConnectionFactory().getConnection();
        PreparedStatement stmt = null;
        
        try {
            stmt = connection.prepareStatement("SELECT * FROM Projeto "
                                                + "WHERE id = ?");
            stmt.setInt(1, id);
            
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Projeto projeto = new Projeto();
                projeto.setId(rs.getInt("id"));
                projeto.setNome(rs.getString("nome"));
                projeto.setDescricao(rs.getString("descricao"));
                
                return projeto;
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
    
    public static void editar(Projeto projeto) throws SQLException {
        Connection connection = new ConnectionFactory().getConnection();
        PreparedStatement stmt = null;
        
        try {
            stmt = connection.prepareStatement("UPDATE Projeto "
                                                + "SET nome = ?, descricao = ? "
                                                + "WHERE id = ?");
            stmt.setString(1, projeto.getNome());
            stmt.setString(2, projeto.getDescricao());
            stmt.setInt(3, projeto.getId());
            
            stmt.executeUpdate();
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
    
    public static void deletar(Projeto projeto) throws SQLException {
        Connection connection = new ConnectionFactory().getConnection();
        PreparedStatement stmt = null;
        
        try {
            stmt = connection.prepareStatement("DELETE FROM Projeto "
                                                + "WHERE id = ?");
            stmt.setInt(1, projeto.getId());
            
            stmt.executeUpdate();
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
}

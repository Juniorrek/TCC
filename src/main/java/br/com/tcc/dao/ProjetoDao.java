package br.com.tcc.dao;

import br.com.tcc.factory.ConnectionFactory;
import br.com.tcc.model.Artigo;
import br.com.tcc.model.Projeto;
import br.com.tcc.model.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProjetoDao {
    public static String[] defaultSinonimosObjetivo = new String[]{"ambition", "aspiration", "intent", "purpose", "propose", "mission", "target", "desing", "mission", "object", "end in view", "ground zero", "wish", "goal", " aim ", " mind ", "meaning", " mark ", " gaol ", "final ", "reach"};
    public static String[] defaultSinonimosMetodologia = new String[]{" mode ", "procedure", "technique", "approach", "channels", "design", "manner", " plan ", "practice", "process", "program", " way ", "method", "conduct", "measure", "operation", "proceeding", "scheme", "strategy", "step", " form ", "arrangement"};
    public static String[] defaultSinonimosResultado = new String[]{"closure", "complet", "consequen", "denouement", "development", "ending", "result", "culmination", "finaliz", "fulfillment", "windup", "outcome", "conclu", "reaction", "achievement", "attainment", "realization", "succes"};
    
    public static void adicionar(Projeto projeto, Usuario usuario) throws SQLException {
        Connection connection = new ConnectionFactory().getConnection();
        PreparedStatement stmt = null;
        
        try {
            stmt = connection.prepareStatement("INSERT INTO Projeto (nome, descricao, email) "
                                                + "VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, projeto.getNome());
            stmt.setString(2, projeto.getDescricao());
            stmt.setString(3, usuario.getEmail());
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    projeto.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
            
            //SINONIMOS DEFAULT
            stmt = connection.prepareStatement("INSERT INTO Rel_Sin_Pro (pro_id, sinonimo, segmento) "
                                                + "VALUES (?, ?, ?)");
            for (String s : defaultSinonimosObjetivo) {
                stmt.setInt(1, projeto.getId());
                stmt.setString(2, s);
                stmt.setInt(3, 1);
                stmt.addBatch();
            }
            for (String s : defaultSinonimosMetodologia) {
                stmt.setInt(1, projeto.getId());
                stmt.setString(2, s);
                stmt.setInt(3, 2);
                stmt.addBatch();
            }
            for (String s : defaultSinonimosResultado) {
                stmt.setInt(1, projeto.getId());
                stmt.setString(2, s);
                stmt.setInt(3, 3);
                stmt.addBatch();
            }
            stmt.executeBatch();
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
        ResultSet rs = null;
        List<Projeto> projetos = new ArrayList<Projeto>();
        
        try {
            stmt = connection.prepareStatement("SELECT * FROM Projeto "
                                                + "WHERE email = ?");
            stmt.setString(1, usuario.getEmail());
            
            rs = stmt.executeQuery();
            
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
            if (rs != null)
                try { rs.close(); }
                catch (SQLException ex) { Logger.getLogger(ProjetoDao.class.getName()).log(Level.SEVERE, null, ex); }
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
        ResultSet rs = null;
        List<String> sinonimosObjetivo = new ArrayList<String>();
        List<String> sinonimosMetodologia = new ArrayList<String>();
        List<String> sinonimosResultado = new ArrayList<String>();
        
        try {
            stmt = connection.prepareStatement("SELECT * FROM Projeto "
                                                + "WHERE id = ?");
            stmt.setInt(1, id);
            
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                Projeto projeto = new Projeto();
                projeto.setId(rs.getInt("id"));
                projeto.setNome(rs.getString("nome"));
                projeto.setDescricao(rs.getString("descricao"));
                
                //ARTIGOS
                stmt = connection.prepareStatement("SELECT * FROM Rel_Arq_Pro WHERE pro_id = ?");
                stmt.setInt(1, projeto.getId());
                rs = stmt.executeQuery();
                List<Artigo> artigos = new ArrayList<Artigo>();
                while (rs.next()) {
                    Artigo artigo = new Artigo();
                    artigo.setId(rs.getInt("id"));
                    artigo.setNome(rs.getString("arq_nome"));
                    artigos.add(artigo);
                }
                projeto.setArtigos(artigos);
                
                //SEGMENTOS
                stmt = connection.prepareStatement("SELECT * FROM Rel_Sin_Pro WHERE pro_id = ? AND segmento = ?");
                
                stmt.setInt(1, projeto.getId());
                stmt.setInt(2, 1);
                rs = stmt.executeQuery();
                while (rs.next()) {
                    sinonimosObjetivo.add(rs.getString("sinonimo"));
                }
                projeto.setSinonimosObjetivo(sinonimosObjetivo);
                
                stmt.setInt(1, projeto.getId());
                stmt.setInt(2, 2);
                rs = stmt.executeQuery();
                while (rs.next()) {
                    sinonimosMetodologia.add(rs.getString("sinonimo"));
                }
                projeto.setSinonimosMetodologia(sinonimosMetodologia);
                
                stmt.setInt(1, projeto.getId());
                stmt.setInt(2, 3);
                rs = stmt.executeQuery();
                while (rs.next()) {
                    sinonimosResultado.add(rs.getString("sinonimo"));
                }
                projeto.setSinonimosResultado(sinonimosResultado);
                
                return projeto;
            } else {
                return null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProjetoDao.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        } finally {
            if (rs != null)
                try { rs.close(); }
                catch (SQLException ex) { Logger.getLogger(ProjetoDao.class.getName()).log(Level.SEVERE, null, ex); }
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
            connection.setAutoCommit(false);
            stmt = connection.prepareStatement("UPDATE Projeto "
                                                + "SET nome = ?, descricao = ? "
                                                + "WHERE id = ?");
            stmt.setString(1, projeto.getNome());
            stmt.setString(2, projeto.getDescricao());
            stmt.setInt(3, projeto.getId());
            stmt.executeUpdate();
            
            //SINONIMOS
            stmt = connection.prepareStatement("DELETE FROM Rel_Sin_Pro WHERE pro_id = ?");
            stmt.setInt(1, projeto.getId());
            stmt.executeUpdate();
            
            if (projeto.getSinonimosObjetivo() != null || projeto.getSinonimosMetodologia() != null || projeto.getSinonimosResultado() != null) {
                stmt = connection.prepareStatement("INSERT INTO Rel_Sin_Pro (pro_id, sinonimo, segmento) VALUES (?, ?, ?)");
                stmt.setInt(1, projeto.getId());
                if (projeto.getSinonimosObjetivo() != null) {
                    for (String s : projeto.getSinonimosObjetivo()) {
                        stmt.setString(2, s);
                        stmt.setInt(3, 1);
                        stmt.addBatch();
                    }
                }
                if (projeto.getSinonimosMetodologia() != null) {
                    for (String s : projeto.getSinonimosMetodologia()) {
                        stmt.setString(2, s);
                        stmt.setInt(3, 2);
                        stmt.addBatch();
                    }
                }
                if (projeto.getSinonimosResultado() != null) {
                    for (String s : projeto.getSinonimosResultado()) {
                        stmt.setString(2, s);
                        stmt.setInt(3, 3);
                        stmt.addBatch();
                    }
                }
                stmt.executeBatch();
            }
            
            connection.commit();
        } catch (SQLException ex) {
            connection.rollback();
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
            
            stmt = connection.prepareStatement("DELETE FROM Rel_Sin_Pro "
                                                + "WHERE pro_id = ?");
            stmt.setInt(1, projeto.getId());
            stmt.executeUpdate();
            
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

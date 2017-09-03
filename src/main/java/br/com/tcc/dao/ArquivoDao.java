package br.com.tcc.dao;

import br.com.tcc.factory.ConnectionFactory;
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

public class ArquivoDao {
    public static void adicionar(MultipartFile file, Projeto projeto, String filePath) throws SQLException, IOException {
        Connection connection = new ConnectionFactory().getConnection();
        PreparedStatement stmt = null;
        
        try {
            stmt = connection.prepareStatement("INSERT INTO Rel_Arq_Pro (pro_id, arq_caminho) "
                                                + "VALUES (?, ?)");
            stmt.setInt(1, projeto.getId());
            stmt.setString(2, filePath);
            
            stmt.executeUpdate();
            
            java.io.File dest = new java.io.File(filePath);
            dest.mkdirs();
            file.transferTo(dest);
        } catch (SQLException | IOException | IllegalStateException ex) {
            Logger.getLogger(ArquivoDao.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        } finally {
            if (stmt != null)
                try { stmt.close(); }
                catch (SQLException ex) { Logger.getLogger(ArquivoDao.class.getName()).log(Level.SEVERE, null, ex); }
            if (connection != null)
                try { connection.close(); }
                catch (SQLException ex) { Logger.getLogger(ArquivoDao.class.getName()).log(Level.SEVERE, null, ex); }
        }
    }
    
    public static void deletar(Projeto projeto, String filePath) throws SQLException, IOException {
        Connection connection = new ConnectionFactory().getConnection();
        PreparedStatement stmt = null;
        
        try {
            stmt = connection.prepareStatement("DELETE FROM Rel_Arq_Pro "
                                                + "WHERE pro_id = ? AND arq_caminho = ?");
            stmt.setInt(1, projeto.getId());
            stmt.setString(2, filePath);
            
            int retorno = stmt.executeUpdate();
            if (retorno == 1) {
                java.io.File dest = new java.io.File(filePath);
                dest.delete();
            }
        } catch (SQLException | IllegalStateException ex) {
            Logger.getLogger(ArquivoDao.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        } finally {
            if (stmt != null)
                try { stmt.close(); }
                catch (SQLException ex) { Logger.getLogger(ArquivoDao.class.getName()).log(Level.SEVERE, null, ex); }
            if (connection != null)
                try { connection.close(); }
                catch (SQLException ex) { Logger.getLogger(ArquivoDao.class.getName()).log(Level.SEVERE, null, ex); }
        }
    }
    
    public static List<String> carregar(String folderPath) {
        File folder = new File(folderPath);
        File[] listOfFiles = folder.listFiles();
        List<String> arq_nomes = new ArrayList<String>();
        
        for (File f : listOfFiles) {
            if(f.isFile() && f.getName().endsWith(".pdf"))
                arq_nomes.add(f.getName());
        }
        
        return arq_nomes;
    }
}

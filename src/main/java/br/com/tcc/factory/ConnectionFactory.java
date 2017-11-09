package br.com.tcc.factory;

import br.com.tcc.singleton.Singleton;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Component;

@Component
public class ConnectionFactory {
    public Connection getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection(Singleton.DB_URL, Singleton.DB_USER, Singleton.DB_PASSWORD);
        } catch (SQLException | ClassNotFoundException ex) { 
            Logger.getLogger(ConnectionFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
}
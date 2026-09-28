package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class Utilitaire {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/gestion_pharmacie";
    private static final String USER = "root";
    private static final String PASSWORD = "";

	private static Connection conn;

    static {
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connexion MySQL reussie !");
        } catch (ClassNotFoundException e) {
            System.out.println("Pilote MySQL introuvable : " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Erreur de connexion : " + e.getMessage());
        }
    }

    public static Connection getConnection() {
        return conn;
    }
    
   
}

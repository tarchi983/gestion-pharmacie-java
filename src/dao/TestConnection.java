package dao;

import java.sql.Connection;

public class TestConnection {
    public static void main(String[] args) {
        Connection c = Utilitaire.getConnection();

        if (c != null) {
            System.out.println("Connexion réussie !");
        } else {
            System.out.println("Connexion échouée !");
        }
    }
}   
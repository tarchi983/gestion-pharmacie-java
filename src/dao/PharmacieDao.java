package dao;

import model.Pharmacie;
import java.sql.*;

public class PharmacieDao {

    public void ajouter(Pharmacie p) {
        String sql = "INSERT INTO Pharmacie (nom, adresse, telephone, email, responsable) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = Utilitaire.getConnection().prepareStatement(sql)) {
            ps.setString(1, p.getNom());
            ps.setString(2, p.getAdresse());
            ps.setString(3, p.getTelephone());
            ps.setString(4, p.getEmail());
            ps.setString(5, p.getResponsable());
            ps.executeUpdate();
            System.out.println("Pharmacie ajoutée !");
        } catch (SQLException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }

    public Pharmacie get() {
        String sql = "SELECT * FROM Pharmacie LIMIT 1";
        try (Statement st = Utilitaire.getConnection().createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) {
                return new Pharmacie(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getString("adresse"),
                    rs.getString("telephone"),
                    rs.getString("email"),
                    rs.getString("responsable")
                );
            }
        } catch (SQLException e) {
            System.out.println(" Erreur : " + e.getMessage());
        }
        return null;
    }

    public void modifier(Pharmacie p) {
        String sql = "UPDATE Pharmacie SET nom=?, adresse=?, telephone=?, email=?, responsable=? WHERE id=?";
        try (PreparedStatement ps = Utilitaire.getConnection().prepareStatement(sql)) {
            ps.setString(1, p.getNom());
            ps.setString(2, p.getAdresse());
            ps.setString(3, p.getTelephone());
            ps.setString(4, p.getEmail());
            ps.setString(5, p.getResponsable());
            ps.setInt(6, p.getId());
            ps.executeUpdate();
            System.out.println(" Pharmacie modifiée !");
        } catch (SQLException e) {
            System.out.println(" Erreur : " + e.getMessage());
        }
    }
}

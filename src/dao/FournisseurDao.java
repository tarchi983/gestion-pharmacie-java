package dao;

import model.Fournisseur;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FournisseurDao {

    public void ajouter(Fournisseur f) {
        String sql = "INSERT INTO Fournisseur (nomSociete, contact, adresse) VALUES (?, ?, ?)";
        try (PreparedStatement ps = Utilitaire.getConnection().prepareStatement(sql)) {
            ps.setString(1, f.getNomSociete());
            ps.setString(2, f.getContact());
            ps.setString(3, f.getAdresse());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(" Erreur : " + e.getMessage());
        }
    }

    public List<Fournisseur> getAll() {
        List<Fournisseur> list = new ArrayList<>();
        String sql = "SELECT * FROM Fournisseur";
        try (Statement st = Utilitaire.getConnection().createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Fournisseur(
                    rs.getInt("id"),
                    rs.getString("nomSociete"),
                    rs.getString("contact"),
                    rs.getString("adresse")
                ));
            }
        } catch (SQLException e) {
            System.out.println(" Erreur : " + e.getMessage());
        }
        return list;
    }
    
    public void supprimer(int id) {
        String sql = "DELETE FROM Fournisseur WHERE id=?";
        try (PreparedStatement ps = Utilitaire.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(" Erreur : " + e.getMessage());
        }
    }
    
    public void modifier(Fournisseur f) throws SQLException {
        
        String sql = "UPDATE Fournisseur SET nomSociete=?, adresse=?  , contact=? WHERE id=?";
        PreparedStatement ps = Utilitaire.getConnection().prepareStatement(sql);
        ps.setString(1, f.getNomSociete());
        ps.setString(2, f.getAdresse());
        ps.setString(3, f.getContact());
        ps.setInt(4, f.getId()); 
        ps.executeUpdate();
    }
}

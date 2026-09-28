package dao;

import model.Medicament;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicamentDao {

    public void ajouter(Medicament m) {
        String sql = "INSERT INTO Medicament (nomCommercial, composition, forme, dosage, prix, quantiteStock, datePeremption, seuilAlerte) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = Utilitaire.getConnection().prepareStatement(sql)) {
            ps.setString(1, m.getNomCommercial());
            ps.setString(2, m.getComposition());
            ps.setString(3, m.getForme());
            ps.setString(4, m.getDosage());
            ps.setDouble(5, m.getPrix());
            ps.setInt(6, m.getQuantiteStock());
            ps.setDate(7, Date.valueOf(m.getDatePeremption()));
            ps.setInt(8, m.getSeuilAlerte());
            ps.executeUpdate();
            System.out.println("Médicament ajouté !");
        } catch (SQLException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }

    public List<Medicament> getAll() {
        List<Medicament> list = new ArrayList<>();
        String sql = "SELECT * FROM Medicament";
        try (Statement st = Utilitaire.getConnection().createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Medicament m = new Medicament(
                    rs.getInt("id"),
                    rs.getString("nomCommercial"),
                    rs.getString("composition"),
                    rs.getString("forme"),
                    rs.getString("dosage"),
                    rs.getDouble("prix"),
                    rs.getInt("quantiteStock"),
                    rs.getDate("datePeremption").toLocalDate(),
                    rs.getInt("seuilAlerte")
                );
                list.add(m);
            }
        } catch (SQLException e) {
            System.out.println("❌ Erreur : " + e.getMessage());
        }
        return list;
    }

    public void modifier(Medicament m) {
        String sql = "UPDATE Medicament SET nomCommercial=?, composition=?, forme=?, dosage=?, prix=?, quantiteStock=?, datePeremption=?, seuilAlerte=? WHERE id=?";
        try (PreparedStatement ps = Utilitaire.getConnection().prepareStatement(sql)) {
            ps.setString(1, m.getNomCommercial());
            ps.setString(2, m.getComposition());
            ps.setString(3, m.getForme());
            ps.setString(4, m.getDosage());
            ps.setDouble(5, m.getPrix());
            ps.setInt(6, m.getQuantiteStock());
            ps.setDate(7, Date.valueOf(m.getDatePeremption()));
            ps.setInt(8, m.getSeuilAlerte());
            ps.setInt(9, m.getId());
            ps.executeUpdate();
            System.out.println("Médicament modifié !");
        } catch (SQLException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }

    public void supprimer(int id) {
        String sql = "DELETE FROM Medicament WHERE id=?";
        try (PreparedStatement ps = Utilitaire.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println(" Médicament supprimé !");
        } catch (SQLException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }

    public List<Medicament> getStockFaible() {
        List<Medicament> list = new ArrayList<>();
        String sql = "SELECT * FROM Medicament WHERE quantiteStock <= seuilAlerte";
        try (Statement st = Utilitaire.getConnection().createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Medicament m = new Medicament(
                    rs.getInt("id"),
                    rs.getString("nomCommercial"),
                    rs.getString("composition"),
                    rs.getString("forme"),
                    rs.getString("dosage"),
                    rs.getDouble("prix"),
                    rs.getInt("quantiteStock"),
                    rs.getDate("datePeremption").toLocalDate(),
                    rs.getInt("seuilAlerte")
                );
                list.add(m);
            }
        } catch (SQLException e) {
            System.out.println(" Erreur : " + e.getMessage());
        }
        return list;
    }

   
    public static void updateStock(int idMedicament, int quantite) {
        String sql = "UPDATE Medicament SET quantiteStock = quantiteStock + ? WHERE id = ?";
        try (PreparedStatement ps = Utilitaire.getConnection().prepareStatement(sql)) {
            ps.setInt(1, quantite);
            ps.setInt(2, idMedicament);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }
}

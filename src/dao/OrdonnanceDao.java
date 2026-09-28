package dao;


import model.Ordonnance;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrdonnanceDao {

    public void ajouter(Ordonnance o) {
        String sql = "INSERT INTO Ordonnance (idClient, medecin, dateOrdonnance, notes) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = Utilitaire.getConnection().prepareStatement(sql)) {
            ps.setInt(1, o.getIdClient());
            ps.setString(2, o.getMedecin());
            ps.setDate(3, Date.valueOf(o.getDateOrdonnance()));
            ps.setString(4, o.getNotes());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("❌ Erreur : " + e.getMessage());
        }
    }

    public List<Ordonnance> getAll() {
        List<Ordonnance> list = new ArrayList<>();
        String sql = "SELECT * FROM Ordonnance";
        try (Statement st = Utilitaire.getConnection().createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Ordonnance(
                    rs.getInt("id"),
                    rs.getInt("idClient"),
                    rs.getString("medecin"),
                    rs.getDate("dateOrdonnance").toLocalDate(),
                    rs.getString("notes")
                ));
            }
        } catch (SQLException e) {
            System.out.println("❌ Erreur : " + e.getMessage());
        }
        return list;
    }
}

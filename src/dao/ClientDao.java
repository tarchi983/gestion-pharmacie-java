package dao;
import java.util.*;
import model.Client;
import java.sql.*;


public class ClientDao {
	
	
	public void ajouter(Client c) {
	        String sql = "INSERT INTO Client (nom, prenom, telephone, adresse) VALUES (?, ?, ?, ?)";
	        try (PreparedStatement ps = Utilitaire.getConnection().prepareStatement(sql)) {
	            ps.setString(1, c.getNom());
	            ps.setString(2, c.getPrenom());
	            ps.setString(3, c.getTelephone());
	            ps.setString(4, c.getAdresse());
	            ps.executeUpdate();
	        } catch (SQLException e) {
	            System.out.println(" Erreur : " + e.getMessage());
	        }
	    }

	    public List<Client> getAll() {
	        List<Client> list = new ArrayList<>();
	        String sql = "SELECT * FROM Client";
	        try (Statement st = Utilitaire.getConnection().createStatement();
	             ResultSet rs = st.executeQuery(sql)) {
	            while (rs.next()) {
	                list.add(new Client(
	                    rs.getInt("id"),
	                    rs.getString("nom"),
	                    rs.getString("prenom"),
	                    rs.getString("telephone"),
	                    rs.getString("adresse")
	                ));
	            }
	        } catch (SQLException e) {
	            System.out.println(" Erreur : " + e.getMessage());
	        }
	        return list;
	    }

	    public void supprimer(int id) {
	        String sql = "DELETE FROM Client WHERE id=?";
	        try (PreparedStatement ps = Utilitaire.getConnection().prepareStatement(sql)) {
	            ps.setInt(1, id);
	            ps.executeUpdate();
	        } catch (SQLException e) {
	            System.out.println(" Erreur : " + e.getMessage());
	        }
	    }
	    
	    public void modifier(Client c) throws SQLException {
	        
	        String sql = "UPDATE Client SET nom=?, prenom=?, telephone=?, adresse=? WHERE id=?";
	        PreparedStatement ps = Utilitaire.getConnection().prepareStatement(sql);
	        ps.setString(1, c.getNom());
	        ps.setString(2, c.getPrenom());
	        ps.setString(3, c.getTelephone());
	        ps.setString(4, c.getAdresse());
	        ps.setInt(5, c.getId()); 
	        ps.executeUpdate();
	    }
	
	}


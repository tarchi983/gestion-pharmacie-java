package dao;

import model.Vente;
import model.Client;
import model.LigneVente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VenteDao {

    public void ajouter(Vente v) {
        String sql = "INSERT INTO Vente (idClient, idOrdonnance, dateVente) VALUES (?, ?, ?)";
        try (PreparedStatement ps = Utilitaire.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, v.getIdClient());
            ps.setObject(2, v.getIdOrdonnance() == 0 ? null : v.getIdOrdonnance());
            ps.setDate(3, Date.valueOf(v.getDateVente()));
            ps.executeUpdate();

            int idVente = 0;
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    idVente = rs.getInt(1);
                    v.setId(idVente);
                }
            }
            
        }catch (SQLException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }
    
    public void enregistrerLigne(LigneVente ligne) {
    		String insertLigne = "INSERT INTO LigneVente (idVente, idMedicament, quantite, prixUnitaire) VALUES (?, ?, ?, ?)";
        try (PreparedStatement psLigne = Utilitaire.getConnection().prepareStatement(insertLigne)) {
            psLigne.setInt(1, ligne.getIdVente());
            psLigne.setInt(2, ligne.getIdMedicament());
            psLigne.setInt(3, ligne.getQuantite());
            psLigne.setDouble(4, ligne.getPrixUnitaire());
            psLigne.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    	
    }
    public void enregistrerVente(Vente v) {
    				for(LigneVente ligne : v.getLignes()) {
    	
                String update = "UPDATE Medicament SET quantiteStock = quantiteStock - ? WHERE id = ?";
                try (PreparedStatement ps2 = Utilitaire.getConnection().prepareStatement(update)) {
                    ps2.setInt(1, ligne.getQuantite());
                    ps2.setInt(2, ligne.getIdMedicament());
                    ps2.executeUpdate();
                }
                catch (SQLException e) {
                    System.out.println("Erreur : " + e.getMessage());
                }
            System.out.println("Vente enregistrée et stock mis à jour !");
        }
    }
    
	public void annulerVente(Vente v) {
		 String sql2 = "DELETE FROM lignevente WHERE idVente=?";
	        try (PreparedStatement ps2 = Utilitaire.getConnection().prepareStatement(sql2)) {
	            ps2.setInt(1,v.getId());
	            ps2.executeUpdate();
	        }
	        catch (SQLException e) {
	            System.out.println("Erreur : " + e.getMessage());
	        }
		
        String sql = "DELETE FROM vente WHERE id=?";
        try (PreparedStatement ps = Utilitaire.getConnection().prepareStatement(sql)) {
            ps.setInt(1, v.getId());
            ps.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
	}
	public void annulerligne(Vente v) {
		String sql2 = "DELETE FROM lignevente WHERE idVente=?";
        try (PreparedStatement ps2 = Utilitaire.getConnection().prepareStatement(sql2)) {
            ps2.setInt(1,v.getId());
            ps2.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
	}
        
       

    public List<Vente> getAll() {
        List<Vente> list = new ArrayList<>();
        String sql = "SELECT * FROM Vente";
        try (Statement st = Utilitaire.getConnection().createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Vente vente = new Vente( 
                			rs.getInt("id"),
                        rs.getInt("idClient"),
                        rs.getInt("idOrdonnance"),
                        rs.getDate("dateVente").toLocalDate()
                    );
                vente.setLignes(getLignesVente(vente.getId()));
                list.add(vente);
                }
        } catch (SQLException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
        return list;
    }

    private List<model.LigneVente> getLignesVente(int idVente) {
        List<model.LigneVente> lignes = new ArrayList<>();
        String sql = "SELECT * FROM LigneVente WHERE idVente=?";
        try (PreparedStatement ps = Utilitaire.getConnection().prepareStatement(sql)) {
            ps.setInt(1, idVente);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lignes.add(new LigneVente(
                            rs.getInt("idVente"),
                            rs.getInt("idMedicament"),
                            rs.getInt("quantite"),
                            rs.getDouble("prixUnitaire")
                    ));
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
        return lignes;
    }
    public void modifierOrdo(Vente v)  {     
        String sql = "UPDATE Vente SET idOrdonnance=? WHERE id=?";
        try(PreparedStatement ps = Utilitaire.getConnection().prepareStatement(sql)){
        ps.setInt(1,v.getIdOrdonnance());
        ps.setInt(2, v.getId()); 
        ps.executeUpdate();
        } catch (SQLException e) {
        		System.out.println("Erreur : " + e.getMessage());
        }
    }
    
}
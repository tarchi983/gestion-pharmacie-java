package dao;


import model.Commande;
import model.LigneCommande;
import model.Vente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommandeDao {

    public void ajouter(Commande c) {
        String sql = "INSERT INTO Commande (idFournisseur, dateCommande, statut) VALUES (?, ?, ?)";
        try (PreparedStatement ps = Utilitaire.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, c.getIdFournisseur());
            ps.setDate(2, Date.valueOf(c.getDateCommande()));
            ps.setString(3, c.getStatut());
            ps.executeUpdate();

            int idCommande = 0;
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    idCommande = rs.getInt(1);
                    c.setIdCommande(idCommande);
                }
            }
        }
        catch (SQLException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }
    
    public void enregistrerLigneC (LigneCommande ligne) {
        		String insertLigne = "INSERT INTO LigneCommande (idCommande, idMedicament, quantiteCommandee, prixAchat) VALUES (?, ?, ?, ?)";
            try (PreparedStatement psLigne = Utilitaire.getConnection().prepareStatement(insertLigne)) {
                psLigne.setInt(1, ligne.getIdCommande());
                psLigne.setInt(2, ligne.getIdMedicament());
                psLigne.setInt(3, ligne.getQuantiteCommandee());
                psLigne.setDouble(4, ligne.getPrixAchat());
                psLigne.executeUpdate();
            }
            catch (SQLException e) {
                System.out.println(" Erreur : " + e.getMessage());
            }   
     }
    	public void annulerCommande(Commande c) {
    			String sql2 = "DELETE FROM lignecommande WHERE idCommande=?";
            try (PreparedStatement ps2 = Utilitaire.getConnection().prepareStatement(sql2)) {
                ps2.setInt(1, c.getIdCommande());
                ps2.executeUpdate();
            }
            catch (SQLException e) {
                System.out.println("Erreur : " + e.getMessage());
            }
            String sql = "DELETE FROM commande WHERE	id=?";
            try (PreparedStatement ps = Utilitaire.getConnection().prepareStatement(sql)) {
                ps.setInt(1, c.getIdCommande());
                ps.executeUpdate();
            }
            catch (SQLException e) {
                System.out.println("Erreur : " + e.getMessage());
            }
    
    	}
    	public void annulerligne(Commande c) {
    		String sql2 = "DELETE FROM lignecommande WHERE idCommande=?";
            try (PreparedStatement ps2 = Utilitaire.getConnection().prepareStatement(sql2)) {
                ps2.setInt(1, c.getIdCommande());
                ps2.executeUpdate();
            }
            catch (SQLException e) {
                System.out.println("Erreur : " + e.getMessage());
            }
    	}
            
    public void updateStatut(int id, String statut) {
        String sql = "UPDATE Commande SET statut=? WHERE id=?";
        try (PreparedStatement ps = Utilitaire.getConnection().prepareStatement(sql)) {
            ps.setString(1, statut);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(" Erreur : " + e.getMessage());
        }
    }

    public List<Commande> getAll() {
        List<Commande> list = new ArrayList<>();
        String sql = "SELECT * FROM Commande";
        try (Statement st = Utilitaire.getConnection().createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Commande commande = new Commande(
                        rs.getInt("id"),
                        rs.getInt("idFournisseur"),
                        rs.getDate("dateCommande").toLocalDate(),
                        rs.getString("statut")
                    );
                commande.setLignes(getLignesCommande(commande.getIdCommande()));
                list.add(commande);
            }
        } catch (SQLException e) {
            System.out.println(" Erreur : " + e.getMessage());
        }
        return list;
    }

    private List<LigneCommande> getLignesCommande(int idCommande) {
        List<LigneCommande> lignes = new ArrayList<>();
        String sql = "SELECT  idCommande , quantiteCommandee, prixAchat, idMedicament FROM lignecommande WHERE idCommande=?";
        try (PreparedStatement ps = Utilitaire.getConnection().prepareStatement(sql)) {
            ps.setInt(1, idCommande);
            try (ResultSet rs = ps.executeQuery()) {
            		while (rs.next()) { 
                    lignes.add(new LigneCommande(
                            rs.getInt("idCommande"),
                            rs.getInt("idMedicament"),
                            rs.getInt("quantiteCommandee"),
                            rs.getDouble("prixAchat")
                    ));
            		}
            }
        } catch (SQLException e) {
            System.out.println(" Erreur : " + e.getMessage());
        }
        return lignes;
    }
    
}
package controller;

import dao.ClientDao;
import dao.VenteDao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Client;
import model.Vente;

public class ClientController {

    private ClientDao clientDao;
    private VenteDao venteDao;

    public ClientController() {
        clientDao = new ClientDao();
        venteDao = new VenteDao();
    }
    
    
    public boolean ajouterClient(String nom, String prenom, String telephone, String adresse) {

        if (nom == null || nom.isBlank()) {
            return false;
        }

        if (telephone == null || telephone.isBlank()) {
            return false;
        }

        Client client = new Client(nom, prenom, telephone, adresse);

        clientDao.ajouter(client);

        return true;
    }

    // =========================
    // LISTER CLIENTS
    // =========================
    public List<Client> listerClients() {

        return clientDao.getAll();
    }
    
    // =========================
    // SUPPRIMER CLIENT
    // =========================
    public boolean supprimerClient(int id) {

        if (id <= 0) {
            return false;
        }

        try {

            clientDao.supprimer(id);

            return true;

        } catch (Exception e) {

            System.out.println("Erreur suppression : " + e.getMessage());

            return false;
        }
    }
    
    // =========================
    // MODIFIER CLIENT
    // =========================
    public boolean modifierClient(int id, String nom, String prenom, String telephone, String adresse) {

        if (id <= 0) {
            return false;
        }

        if (nom == null || nom.isBlank()) {
            return false;
        }


        if (telephone == null || telephone.isBlank()) {
            return false;
        }

        Client client = new Client(id, nom, prenom, telephone, adresse);

        try {

            clientDao.modifier(client);

            return true;

        } catch (SQLException e) {

            System.out.println("Erreur modification client : " + e.getMessage());

            return false;
        }
    }
    

    // =========================
    // CHERCHER CLIENT PAR ID
    // =========================
    public Client chercherClientParId(int id) {

        if (id <= 0) {
            return null;
        }

        List<Client> liste = clientDao.getAll();

        for (Client client : liste) {

            if (client.getId() == id) {
                return client;
            }
        }

        return null;
    }


    // =========================
    // HISTORIQUE DES ACHATS     
    // =========================
    public List<Vente> listerHistoriqueAchats(int idClient) {

        List<Vente> historique = new ArrayList<>();

        if (idClient <= 0) {
            return historique;
        }

        List<Vente> ventes = venteDao.getAll();

        for (Vente vente : ventes) {

            if (vente.getIdClient() == idClient) {

                historique.add(vente);
            }
        }

        return historique;
    }
    
   
}
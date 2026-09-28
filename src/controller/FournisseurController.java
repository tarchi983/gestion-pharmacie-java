package controller;

import dao.FournisseurDao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Fournisseur;

public class FournisseurController {

    private final FournisseurDao fournisseurDao;

    public FournisseurController() {
        this(new FournisseurDao());
    }

    public FournisseurController(FournisseurDao fournisseurDao) {
        this.fournisseurDao = fournisseurDao;
    }

    public void ajouterFournisseur(String nomSociete, String contact, String adresse) {
        Fournisseur fournisseur = new Fournisseur(0, nomSociete, contact, adresse);
        ajouterFournisseur(fournisseur);
    }

    public void ajouterFournisseur(Fournisseur fournisseur) {
        fournisseurDao.ajouter(fournisseur);
    }

    public List<Fournisseur> listerFournisseurs() {
        return fournisseurDao.getAll();
    }

    public Fournisseur chercherFournisseurParId(int id) {
        if (id <= 0) {
            return null;
        }

        for (Fournisseur fournisseur : fournisseurDao.getAll()) {
            if (fournisseur.getId() == id) {
                return fournisseur;
            }
        }
        return null;
    }

    public List<Fournisseur> chercherFournisseursParNom(String motCle) {
        List<Fournisseur> resultats = new ArrayList<>();
        if (motCle == null) {
            return resultats;
        }

        String recherche = motCle.toLowerCase();
        for (Fournisseur fournisseur : fournisseurDao.getAll()) {
            String nomSociete = fournisseur.getNomSociete();
            if (nomSociete.toLowerCase().contains(recherche)) {
                resultats.add(fournisseur);
            }
        }
        return resultats;
    }
    
    public boolean supprimerFournisseur(int id) {

        if (id <= 0) 
            return false;
        try {
            fournisseurDao.supprimer(id);
            return true;

        } catch (Exception e) {
            System.out.println("Erreur suppression : " + e.getMessage());
            return false;
        }
    }
    
    public boolean modifierFournisseur(int id, String nomSociete, String contact, String adresse) {

        if (id <= 0) 
            return false;
        
        if (nomSociete == null || nomSociete.isBlank()) 
            return false;
        
        if (contact == null || contact.isBlank()) 
            return false;
        
        Fournisseur fournisseur  = new Fournisseur(id, nomSociete, contact, adresse);   
        
        try {
        		fournisseurDao.modifier(fournisseur);
            return true;

        } catch (SQLException e) {

            System.out.println("Erreur modification fournisseur : " + e.getMessage());

            return false;
        }
    }

}
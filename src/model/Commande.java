package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Commande {

    private int idCommande;

    private LocalDate dateCommande;

    private String statut;

    private int idFournisseur;

    private List<LigneCommande> lignes;

    public Commande() {

        lignes = new ArrayList<>();
    }

    public Commande(int idCommande,int fournisseur, LocalDate dateCommande, String statut ) {

        this.idCommande = idCommande;
        this.dateCommande = dateCommande;
        this.statut = statut;
        this.idFournisseur = fournisseur;

        lignes = new ArrayList<>();
    }
   


    public int getIdCommande() {
        return idCommande;
    }

    public void setIdCommande(int idCommande) {
        this.idCommande = idCommande;
    }

    public LocalDate getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(LocalDate dateCommande) {
        this.dateCommande = dateCommande;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public int getIdFournisseur() {
        return idFournisseur;
    }

    public void setFournisseur(int fournisseur) {
        this.idFournisseur = fournisseur;
    }

    public List<LigneCommande> getLignes() {
        return lignes;
    }

    public void setLignes(List<LigneCommande> lignes) {
        this.lignes = lignes;
    }
    
    public void addlignes(LigneCommande ligne ) {
    	lignes.add(ligne);
    }
}
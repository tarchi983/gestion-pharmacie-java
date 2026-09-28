package model;

public class LigneVente {

    private int idvente;

    private int idMedicament;

    private int quantite;

    private double prixUnitaire;

    public LigneVente() {
    }

    public LigneVente(int idvente, int medicament, int quantite, double prixUnitaire) {
        this.idvente = idvente;
        this.idMedicament = medicament;
        this.quantite = quantite;
        this.prixUnitaire = prixUnitaire;
    }
    
    

    public double getTotal() {
        return quantite * prixUnitaire;
    }

    public int getIdVente() {
        return idvente;
    }

    public void setIdVente(int id) {
        this.idvente = id;
    }


    public int getIdMedicament() {
        return idMedicament;
    }

    public void setMedicament(int medicament) {
        this.idMedicament = medicament;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public double getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(double prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }
}
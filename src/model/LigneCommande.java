package model;

public class LigneCommande {

    private int idcommande;

    private int idmedicament;

    private int quantiteCommandee;

    private double prixAchat;
    

    public LigneCommande() {
    }

    public LigneCommande(int idcommande, int idmedicament, int quantiteCommandee , double prixAchat) {

        this.idcommande = idcommande;
        this.idmedicament = idmedicament;
        this.quantiteCommandee = quantiteCommandee;
        this.prixAchat = prixAchat ; 
    }
    
    public double getTotal() {
		return prixAchat * quantiteCommandee ;
    }
    
    public int getIdCommande() {
        return idcommande;
    }

    public void setIdCommande(int id) {
        this.idcommande = id;
    }


    public int getIdMedicament() {
        return idmedicament;
    }

    public void setIdMedicament(int idmedicament) {
        this.idmedicament = idmedicament;
    }

    public int getQuantiteCommandee() {
        return quantiteCommandee;
    }

    public void setQuantiteCommandee(int quantiteCommandee) {
        this.quantiteCommandee = quantiteCommandee;
    }

	public double getPrixAchat() {
		return prixAchat;
	}

	public void setPrixAchat(double prixAchat) {
		this.prixAchat = prixAchat;
	}

    
}
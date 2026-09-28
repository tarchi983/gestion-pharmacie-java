package model;

public class Fournisseur {

    private int id;
    private String nomSociete;
    private String contact;
    private String adresse;

    public Fournisseur() {
    }

    public Fournisseur(int id, String nomSociete, String contact, String adresse) {
        this.id = id;
        this.nomSociete = nomSociete;
        this.contact = contact;
        this.adresse = adresse;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getNomSociete() {
        return nomSociete;
    }

    public void setNomSociete(String nomSociete) {
        this.nomSociete = nomSociete;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
}
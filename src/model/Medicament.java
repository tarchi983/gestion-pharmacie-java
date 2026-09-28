package model;

import java.time.LocalDate;

public class Medicament {

    private int id;
    private String nomCommercial;
    private String composition;
    private String forme;
    private String dosage;
    private double prix;
    private int quantiteStock;
    private LocalDate datePeremption;
    private int seuilAlerte;
    
	public Medicament(int id, String nomCommercial, String composition, String forme, String dosage, double prix, int quantiteStock, LocalDate datePeremption, int seuilAlert) {
		this.id = id;
		this.nomCommercial = nomCommercial;
		this.composition = composition;
		this.forme = forme;
		this.dosage = dosage;
		this.prix = prix;
		this.quantiteStock = quantiteStock;
		this.datePeremption = datePeremption;
		this.seuilAlerte = seuilAlert;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNomCommercial() {
		return nomCommercial;
	}
	public void setNomCommercial(String nomCommercial) {
		this.nomCommercial = nomCommercial;
	}
	public String getComposition() {
		return composition;
	}
	public void setComposition(String composition) {
		this.composition = composition;
	}
	public String getForme() {
		return forme;
	}
	public void setForme(String forme) {
		this.forme = forme;
	}
	public String getDosage() {
		return dosage;
	}
	public void setDosage(String dosage) {
		this.dosage = dosage;
	}
	public double getPrix() {
		return prix;
	}
	public void setPrix(double prix) {
		this.prix = prix;
	}
	public int getQuantiteStock() {
		return quantiteStock;
	}
	public void setQuantiteStock(int quantiteStock) {
		this.quantiteStock = quantiteStock;
	}
	public LocalDate getDatePeremption() {
		return datePeremption;
	}
	public void setDatePeremption(LocalDate datePeremption) {
		this.datePeremption = datePeremption;
	}
	public int getSeuilAlerte() {
		return seuilAlerte;
	}
	public void setSeuilAlert(int seuilAlert) {
		this.seuilAlerte = seuilAlert;
	}

   
}
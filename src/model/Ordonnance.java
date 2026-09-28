package model;

import java.time.LocalDate;



public class Ordonnance {

    private int id;

    private int idClient;
    
    private String medecin;

    private LocalDate dateOrdonnance;
    
    private String notes;
    
    

	public Ordonnance(int id, int idClient, String medecin, LocalDate dateOrdonnance, String notes) {
		this.id = id;
		this.idClient = idClient;
		this.medecin = medecin;
		this.dateOrdonnance = dateOrdonnance;
		this.notes = notes;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdClient() {
		return idClient;
	}

	public void setIdClient(int idClient) {
		this.idClient = idClient;
	}

	public String getMedecin() {
		return medecin;
	}

	public void setMedcin(String medecin) {
		this.medecin = medecin;
	}

	public LocalDate getDateOrdonnance() {
		return dateOrdonnance;
	}

	public void setDateOrdonnance(LocalDate dateOrdonnance) {
		this.dateOrdonnance = dateOrdonnance;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
    
    

    
}
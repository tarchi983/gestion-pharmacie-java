package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Vente {

    private int id;
    
    private int idClient;
    
    private int idOrdonnance;
    
    private LocalDate dateVente;

    private List<LigneVente> lignes;

    public Vente() {

        lignes = new ArrayList<>();
    }
    
    public Vente(int id, int idClient, int idOrdonnance, LocalDate dateVente, List<LigneVente> lignes) {
		this.id = id;
		this.idClient = idClient;
		this.idOrdonnance = idOrdonnance;
		this.dateVente = dateVente;
		this.lignes = lignes;
	}
    public Vente(int id, int idClient, int idOrdonnance, LocalDate dateVente) {
		this.id = id;
		this.idClient = idClient;
		this.idOrdonnance = idOrdonnance;
		this.dateVente = dateVente;
		lignes = new ArrayList<>();
		
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

	public int getIdOrdonnance() {
		return idOrdonnance;
	}

	public void setIdOrdonnance(int idOrdonnance) {
		this.idOrdonnance = idOrdonnance;
	}

	public LocalDate getDateVente() {
		return dateVente; 
	}

	public void setDateVente(LocalDate dateVente) {
		this.dateVente = dateVente;
	}

	public List<LigneVente> getLignes() {
		return lignes;
	}

	public void setLignes(List<LigneVente> lignes) {
		this.lignes = lignes;
	}

	public void addLignes(LigneVente lignevente) {
		lignes.add(lignevente);
	}

    
}
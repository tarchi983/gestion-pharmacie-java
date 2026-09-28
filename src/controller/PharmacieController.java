package controller;

import dao.PharmacieDao;
import model.Pharmacie;

public class PharmacieController {

    private final PharmacieDao pharmacieDao;

    public PharmacieController() {
        this(new PharmacieDao());
    }

    public PharmacieController(PharmacieDao pharmacieDao) {
        this.pharmacieDao = pharmacieDao;
    }

    public boolean enregistrerPharmacie(String nom, String adresse, String telephone,
            String email, String responsable) {
        Pharmacie pharmacie = new Pharmacie(0, nom, adresse, telephone, email, responsable);
        return enregistrerPharmacie(pharmacie);
    }

    public boolean enregistrerPharmacie(Pharmacie pharmacie) {
        Pharmacie existante = pharmacieDao.get();
        if (existante == null) {
            pharmacieDao.ajouter(pharmacie);
        } else {
            pharmacie.setId(existante.getId());
            pharmacieDao.modifier(pharmacie);
        }
        return true;
    }

    public Pharmacie consulterPharmacie() {
        return pharmacieDao.get();
    }

    public boolean modifierPharmacie(Pharmacie pharmacie) {
        
        pharmacieDao.modifier(pharmacie);
        return true;
    }

   
}

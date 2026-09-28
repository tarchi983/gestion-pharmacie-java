package controller;

import dao.OrdonnanceDao;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import model.Ordonnance;

public class OrdonnanceController {

    private final OrdonnanceDao ordonnanceDao;

    public OrdonnanceController() {
        this(new OrdonnanceDao());
    }

    public OrdonnanceController(OrdonnanceDao ordonnanceDao) {
        this.ordonnanceDao = ordonnanceDao;
    }

    public boolean ajouterOrdonnance(int idClient, String medecin, LocalDate dateOrdonnance, String notes) {
        Ordonnance ordonnance = new Ordonnance(0, idClient, medecin, dateOrdonnance, notes);
        return ajouterOrdonnance(ordonnance);
    }

    public boolean ajouterOrdonnance(Ordonnance ordonnance) {
        if (!ordonnanceValide(ordonnance, false)) {
            return false;
        }

        ordonnanceDao.ajouter(ordonnance);
        return true;
    }

    public List<Ordonnance> listerOrdonnances() {
        return ordonnanceDao.getAll();
    }

    public Ordonnance chercherOrdonnanceParId(int id) {
        if (id <= 0) {
            return null;
        }

        for (Ordonnance ordonnance : ordonnanceDao.getAll()) {
            if (ordonnance.getId() == id) {
                return ordonnance;
            }
        }
        return null;
    }

    public List<Ordonnance> listerOrdonnancesParClient(int idClient) {
        List<Ordonnance> resultats = new ArrayList<>();
        if (idClient <= 0) {
            return resultats;
        }

        for (Ordonnance ordonnance : ordonnanceDao.getAll()) {
            if (ordonnance.getIdClient() == idClient) {
                resultats.add(ordonnance);
            }
        }
        return resultats;
    }

    public boolean ordonnanceAppartientAuClient(int idOrdonnance, int idClient) {
        if (idOrdonnance == 0) {
            return true;
        }
        if (idOrdonnance < 0 || idClient <= 0) {
            return false;
        }

        Ordonnance ordonnance = chercherOrdonnanceParId(idOrdonnance);
        return ordonnance != null && ordonnance.getIdClient() == idClient;
    }

    public boolean ordonnanceEncoreValide(int idOrdonnance, int validiteEnJours) {
        if (idOrdonnance <= 0 || validiteEnJours < 0) {
            return false;
        }

        Ordonnance ordonnance = chercherOrdonnanceParId(idOrdonnance);
        if (ordonnance == null || ordonnance.getDateOrdonnance() == null) {
            return false;
        }

        LocalDate dateLimite = ordonnance.getDateOrdonnance().plusDays(validiteEnJours);
        return !LocalDate.now().isAfter(dateLimite);
    }

    private boolean ordonnanceValide(Ordonnance ordonnance, boolean idObligatoire) {
        if (ordonnance == null) {
            return false;
        }
        if (idObligatoire && ordonnance.getId() <= 0) {
            return false;
        }
        return ordonnance.getIdClient() > 0
                && texteValide(ordonnance.getMedecin())
                && ordonnance.getDateOrdonnance() != null
                && !ordonnance.getDateOrdonnance().isAfter(LocalDate.now());
    }

    private boolean texteValide(String valeur) {
        return valeur != null && !valeur.isBlank();
    }
}

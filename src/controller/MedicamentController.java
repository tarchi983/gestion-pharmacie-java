package controller;

import dao.MedicamentDao;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import model.Medicament;

public class MedicamentController {

    private final MedicamentDao medicamentDao;

    public MedicamentController() {
        this(new MedicamentDao());
    }

    public MedicamentController(MedicamentDao medicamentDao) {
        this.medicamentDao = medicamentDao;
    }

    public void ajouterMedicament(String nomCommercial, String composition, String forme,
            String dosage, double prix, int quantiteStock, LocalDate datePeremption, int seuilAlerte) {
        Medicament medicament = new Medicament(0, nomCommercial, composition, forme, dosage, prix, quantiteStock, datePeremption, seuilAlerte);
         ajouterMedicament(medicament);
    }

    public void ajouterMedicament(Medicament medicament) {
        medicamentDao.ajouter(medicament);
        
    }

    public List<Medicament> listerMedicaments() {
        return medicamentDao.getAll();
    }

    public Medicament chercherMedicamentParId(int id) {
        if (id <= 0) {
            return null;
        }

        for (Medicament medicament : medicamentDao.getAll()) {
            if (medicament.getId() == id) {
                return medicament;
            }
        }
        return null;
    }

    public List<Medicament> chercherMedicamentsParNom(String motCle) {
        List<Medicament> resultats = new ArrayList<>();
        if (motCle == null) {
            return resultats;
        }

        String recherche = motCle.toLowerCase();
        for (Medicament medicament : medicamentDao.getAll()) {
            String nom = medicament.getNomCommercial();
            if (nom.toLowerCase().contains(recherche)) {
                resultats.add(medicament);
            }
        }
        return resultats;
    }

    public void modifierMedicament(int id, String nomCommercial, String composition, String forme,
            String dosage, double prix, int quantiteStock, LocalDate datePeremption, int seuilAlerte) {
        Medicament medicament = new Medicament(id, nomCommercial, composition, forme, dosage,
                prix, quantiteStock, datePeremption, seuilAlerte);
        modifierMedicament(medicament);
    }

    public void modifierMedicament(Medicament medicament) {
        medicamentDao.modifier(medicament);
    }

    public boolean supprimerMedicament(int id) {
        if (id <= 0) {
            return false;
        }

        medicamentDao.supprimer(id);
        return true;
    }

    public List<Medicament> listerStockFaible() {
        return medicamentDao.getStockFaible();
    }

    public List<Medicament> listerMedicamentsPerimes() {
        List<Medicament> resultats = new ArrayList<>();
        LocalDate aujourdhui = LocalDate.now();

        for (Medicament medicament : medicamentDao.getAll()) {
            LocalDate datePeremption = medicament.getDatePeremption();
            if ( datePeremption.isBefore(aujourdhui)) {
                resultats.add(medicament);
            }
        }
        return resultats;
    }

    public List<Medicament> listerMedicamentsProchesPeremption(int joursAvantPeremption) {
        List<Medicament> resultats = new ArrayList<>();
        if (joursAvantPeremption < 0) {
            return resultats;
        }

        LocalDate aujourdhui = LocalDate.now();
        LocalDate limite = aujourdhui.plusDays(joursAvantPeremption);
        for (Medicament medicament : medicamentDao.getAll()) {
            LocalDate datePeremption = medicament.getDatePeremption();
            if (!datePeremption.isBefore(aujourdhui) && !datePeremption.isAfter(limite)) {
                resultats.add(medicament);
            }
        }
        return resultats;
    }

    public boolean estDisponible(int idMedicament, int quantiteDemandee) {
        if (idMedicament <= 0 || quantiteDemandee <= 0) {
            return false;
        }

        Medicament medicament = chercherMedicamentParId(idMedicament);
        return medicament != null && medicament.getQuantiteStock() >= quantiteDemandee;
    }

    public boolean ajouterStock(int idMedicament, int quantite) {
        if (idMedicament <= 0 || quantite <= 0 || chercherMedicamentParId(idMedicament) == null) {
            return false;
        }

        MedicamentDao.updateStock(idMedicament, quantite);
        return true;
    }

    public boolean retirerStock(int idMedicament, int quantite) {
        if (!estDisponible(idMedicament, quantite)) {
            return false;
        }

        MedicamentDao.updateStock(idMedicament, -quantite);
        return true;
    }

    
}

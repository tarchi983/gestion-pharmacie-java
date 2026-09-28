package controller;

import java.util.ArrayList;
import java.util.List;
import model.Medicament;

public class StockController {

    private final MedicamentController medicamentController;

    public StockController() {
        this(new MedicamentController());
    }

    public StockController(MedicamentController medicamentController) {
        this.medicamentController = medicamentController;
    }

    public List<Medicament> consulterStock() {
        return medicamentController.listerMedicaments();
    }

    public Medicament chercherMedicament(int idMedicament) {
        return medicamentController.chercherMedicamentParId(idMedicament);
    }

    public boolean stockSuffisant(int idMedicament, int quantiteDemandee) {
        return medicamentController.estDisponible(idMedicament, quantiteDemandee);
    }

    public boolean entreeStock(int idMedicament, int quantite) {
        return medicamentController.ajouterStock(idMedicament, quantite);
    }

    public boolean sortieStock(int idMedicament, int quantite) {
        return medicamentController.retirerStock(idMedicament, quantite);
    }

    public List<Medicament> listerStockFaible() {
        return medicamentController.listerStockFaible();
    }

    public List<Medicament> listerMedicamentsPerimes() {
        return medicamentController.listerMedicamentsPerimes();
    }

    public List<Medicament> listerMedicamentsProchesPeremption(int joursAvantPeremption) {
        return medicamentController.listerMedicamentsProchesPeremption(joursAvantPeremption);
    }

    public double calculerValeurStock() {
        double valeur = 0;
        for (Medicament medicament : medicamentController.listerMedicaments()) {
            valeur += medicament.getPrix() * medicament.getQuantiteStock();
        }
        return valeur;
    }

    public List<String> genererAlertes(int joursAvantPeremption) {
        List<String> alertes = new ArrayList<>();

        for (Medicament medicament : listerStockFaible()) {
            alertes.add("Stock faible: " + medicament.getNomCommercial()
                    + " (" + medicament.getQuantiteStock() + " restant)");
        }

        for (Medicament medicament : listerMedicamentsPerimes()) {
            alertes.add("Produit perime: " + medicament.getNomCommercial()
                    + " depuis " + medicament.getDatePeremption());
        }

        for (Medicament medicament : listerMedicamentsProchesPeremption(joursAvantPeremption)) {
            alertes.add("Peremption proche: " + medicament.getNomCommercial()
                    + " le " + medicament.getDatePeremption());
        }

        return alertes;
    }
}

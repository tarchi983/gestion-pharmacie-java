package controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Client;
import model.LigneVente;
import model.Medicament;
import model.Vente;

public class DashboardController {

    private final VenteController venteController;
    private final StockController stockController;
    private final ClientController clientController;
    private final MedicamentController medicamentController;

    public DashboardController() {
        this.venteController = new VenteController();
        this.stockController = new StockController();
        this.clientController = new ClientController();
        this.medicamentController = new MedicamentController();
    }

    public Map<String, Integer> getVentesParProduit() {
        Map<String, Integer> stats = new HashMap<>();
        List<Vente> ventes = venteController.listerVentes();
        if (ventes != null) {
            for (Vente vente : ventes) {
                if (vente.getLignes() != null) {
                    for (LigneVente ligne : vente.getLignes()) {
                        Medicament m = medicamentController.chercherMedicamentParId(ligne.getIdMedicament());
                        if (m != null) {
                            String nom = m.getNomCommercial();
                            stats.put(nom, stats.getOrDefault(nom, 0) + ligne.getQuantite());
                        }
                    }
                }
            }
        }
        return stats;
    }

    public Map<String, Double> getVentesParClient() {
        Map<String, Double> stats = new HashMap<>();
        List<Vente> ventes = venteController.listerVentes();
        if (ventes != null) {
            for (Vente vente : ventes) {
                Client c = clientController.chercherClientParId(vente.getIdClient());
                if (c != null) {
                    String nomClient = c.getNom() + " " + c.getPrenom();
                    double total = venteController.calculerTotal(vente.getLignes());
                    stats.put(nomClient, stats.getOrDefault(nomClient, 0.0) + total);
                }
            }
        }
        return stats;
    }
    
    public double getTotalVentesPeriode(LocalDate debut, LocalDate fin) {
        double total = 0;
        List<Vente> ventes = venteController.listerVentesParPeriode(debut, fin);
        if (ventes != null) {
            for(Vente v : ventes) {
                total += venteController.calculerTotal(v.getLignes());
            }
        }
        return total;
    }
    
    public List<Map.Entry<String, Integer>> getProduitsLesPlusDemandes() {
        Map<String, Integer> ventes = getVentesParProduit();
        List<Map.Entry<String, Integer>> list = new ArrayList<>(ventes.entrySet());
        list.sort((a, b) -> b.getValue().compareTo(a.getValue()));
        return list;
    }
}
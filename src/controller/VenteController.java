package controller;

import dao.VenteDao;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import model.LigneVente;
import model.Medicament;
import model.Vente;

public class VenteController {

    private final VenteDao venteDao;
    private final MedicamentController medicamentController;
    private final OrdonnanceController ordonnanceController;

    public VenteController() {
        this(new VenteDao(), new MedicamentController(), new OrdonnanceController());
    }

    public VenteController(VenteDao venteDao, MedicamentController medicamentController,
            OrdonnanceController ordonnanceController) {
        this.venteDao = venteDao;
        this.medicamentController = medicamentController;
        this.ordonnanceController = ordonnanceController;
    }
    
    public void creerVente(int idClient, int idOrdonnance ) {
    		creerVente(idClient, idOrdonnance , LocalDate.now() );
   }

   public void creerVente(int idClient, int idOrdonnance ,LocalDate datevente  ) {
	   Vente vente = new Vente(0, idClient , idOrdonnance, datevente); 
	   venteDao.ajouter(vente);
   }
   
    public void creerLigneVente(int idvente ,int idMedicament, int quantite) {
       
    	LigneVente lingnevente = null;
        Medicament medicament = medicamentController.chercherMedicamentParId(idMedicament);
        if (medicament == null) {
        		System.out.println("Medicament n'existe pas");
        		return ;
        }
        
        	for (Vente vente : venteDao.getAll()) {
        			if (vente.getId() == idvente) {
        				lingnevente = new LigneVente(idvente, idMedicament, quantite, medicament.getPrix());
        				vente.addLignes(lingnevente);
        			}
        		}
        	
        	venteDao.enregistrerLigne(lingnevente);
     }

    
    public boolean enregistrerVente(Vente vente) {
        
        if (!ordonnanceController.ordonnanceAppartientAuClient(vente.getIdOrdonnance(), vente.getIdClient())) {
            return false;
        }
        if (!stockSuffisant(vente.getLignes())) {
            return false;
        }

        venteDao.enregistrerVente(vente);
        return true;
    }
    
    public void annulerVente(Vente v) {
		venteDao.annulerVente(v);
	}
    
    public void annulerLigne(Vente v) {
		venteDao.annulerligne(v);
	}
    
    public List<Vente> listerVentes() {
        return venteDao.getAll();
    }
    public void modifierOrdo(Vente v) {
		venteDao.modifierOrdo(v);
    }

    public List<Vente> listerVentesParClient(int idClient) {
        List<Vente> resultats = new ArrayList<>();
        if (idClient <= 0) {
            return resultats;
        }

        for (Vente vente : venteDao.getAll()) {
            if (vente.getIdClient() == idClient) {
                resultats.add(vente);
            }
        }
        return resultats;
    }

    public List<Vente> listerVentesParPeriode(LocalDate dateDebut, LocalDate dateFin) {
        List<Vente> resultats = new ArrayList<>();
        if (dateDebut == null || dateFin == null || dateFin.isBefore(dateDebut)) {
            return resultats;
        }

        for (Vente vente : venteDao.getAll()) {
            LocalDate dateVente = vente.getDateVente();
            if (dateVente != null && dateVente.isBefore(dateFin) && dateVente.isAfter(dateDebut)) {
                resultats.add(vente);
            }
        }
        return resultats;
    }

    public double calculerTotal(List<LigneVente> lignes) {
        double total = 0;
        if (lignes == null) {
            return total;
        }

        for (LigneVente ligne : lignes) {
            if (ligne != null) {
                total += ligne.getTotal();
            }
        }
        return total;
    }
    
    

public void genererEtEnregistrerFacture(Vente vente) {
        if (vente == null) {
            return ;
        }
        
        // 1. Génération du contenu textuel (Votre code existant)
        StringBuilder facture = new StringBuilder();
        facture.append("==========================================\n");
        facture.append("            FACTURE PHARMACIE             \n");
        facture.append("==========================================\n");
        facture.append("Facture N° : ").append(vente.getId()).append('\n');
        facture.append("Client ID  : ").append(vente.getIdClient()).append('\n');
        facture.append("Date       : ").append(vente.getDateVente()).append('\n');
        facture.append("Ordonnance : ")
               .append(vente.getIdOrdonnance() == 0 ? "Sans ordonnance" : "N° " + vente.getIdOrdonnance())
               .append('\n');
        facture.append("------------------------------------------\n");
        facture.append("Détails des achats :\n");
        
        List<LigneVente> lignes = vente.getLignes();
        if (lignes != null) {
            for (LigneVente ligne : lignes) {
                if (ligne != null) {
                    facture.append("- Médicament ID: ").append(ligne.getIdMedicament())
                           .append(" | Qté: ").append(ligne.getQuantite())
                           .append(" | Prix Unitaire: ").append(ligne.getPrixUnitaire()).append(" DH")
                           .append(" | Sous-total: ").append(ligne.getTotal()).append(" DH\n");
                }
            }
        }
        facture.append("------------------------------------------\n");
        facture.append("TOTAL À PAYER : ").append(calculerTotal(lignes)).append(" DH\n");
        facture.append("==========================================\n");

        // 2. Écriture physique du fichier sur le PC
        try {
            // Crée un dossier "factures" là où le logiciel est exécuté s'il n'existe pas
            File dossier = new File("factures");
            if (!dossier.exists()) {
                dossier.mkdir();
            }

            // Nom du fichier unique par vente (ex: factures/facture_42.txt)
            File fichierFacture = new File(dossier, "facture_" + vente.getId() + ".txt");
            
            // Écriture du contenu
            try (FileWriter writer = new FileWriter(fichierFacture)) {
                writer.write(facture.toString());
            }
            
        } catch (IOException e) {
            System.err.println("Erreur lors de l'enregistrement de la facture: " + e.getMessage());
            
        }
    }

    private boolean stockSuffisant(List<LigneVente> lignes) {
        for (LigneVente ligne : lignes) {
            if (!medicamentController.estDisponible(ligne.getIdMedicament(), ligne.getQuantite())) {
                return false;
            }
        }
        return true;
    }

}
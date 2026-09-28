package controller;

import dao.CommandeDao;
import dao.MedicamentDao;
import java.time.LocalDate;
import java.util.List;
import model.Commande;
import model.LigneCommande;
import model.Medicament;


public class CommandeController {

    private final CommandeDao commandeDao;
    private final MedicamentController medicamentController;

    public CommandeController() {
        this(new CommandeDao());
    }

    public CommandeController(CommandeDao commandeDao) {
        this.commandeDao = commandeDao;
		this.medicamentController = new MedicamentController();
    }

    public void creerCommande(int idFournisseur) {
         creerCommande(idFournisseur, LocalDate.now(), "En attente");
    }

    public void creerCommande(int idFournisseur, LocalDate dateCommande, String statut ) {
        Commande commande = new Commande(0, idFournisseur, dateCommande, statut);
         creerCommande(commande);
    }

    public void creerCommande(Commande commande) {
        commandeDao.ajouter(commande);
    }
    
    public void creerLigneCommande(int idcommande ,int idMedicament, int quantite , double prixAchat) {
    	LigneCommande lignecommande = null;
        Medicament medicament = medicamentController.chercherMedicamentParId(idMedicament);
        if (medicament == null) {
        		System.out.println("Medicament n'existe pas");
        		return ;
        }
        
        	for (Commande commande : commandeDao.getAll()) {
        			if (commande.getIdCommande() == idcommande) {
        				lignecommande = new LigneCommande(idcommande, idMedicament, quantite,prixAchat );
        				commande.addlignes(lignecommande);
        			}
        		}
        	
        	commandeDao.enregistrerLigneC(lignecommande);
     }
	public void annulerCommande(Commande c) {
		commandeDao.annulerCommande(c);
	}

    public void annulerLigne(Commande c) {
        commandeDao.annulerligne(c);
    }
    
    public List<Commande> listerCommandes() {
        return commandeDao.getAll();
    }

   

    public Commande chercherCommandeParId(int idCommande) {
        if (idCommande <= 0) {
            return null;
        }

        for (Commande commande : commandeDao.getAll()) {
            if (commande.getIdCommande() == idCommande) {
                return commande;
            }
        }
        return null;
    }

    public boolean changerStatut(int idCommande, String statut) {
        if (idCommande <= 0 ) {
            return false;
        }

        commandeDao.updateStatut(idCommande, statut);
        return true;
    }

    public boolean recevoirLivraison(Commande commande) {
        if (commande == null) {
            return false;
        }
        for (LigneCommande ligne : commande.getLignes()) {
            MedicamentDao.updateStock(ligne.getIdMedicament(), ligne.getQuantiteCommandee());
        }
        commandeDao.updateStatut(commande.getIdCommande(), "Livree");
        return true;
    }

    public double calculerTotal(Commande commande) {
        if (commande == null) {
            return 0;
        }
        return calculerTotal(commande.getLignes());
    }

    public double calculerTotal(List<LigneCommande> lignes) {
        double total = 0;
        if (lignes == null) {
            return total;
        }

        for (LigneCommande ligne : lignes) {
            if (ligne != null) {
                total += ligne.getTotal();
            }
        }
        return total;
    }
    
    
}

package view;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

public class GestionPharmacieView extends JFrame {

    public GestionPharmacieView() {
        // Etape 1: preparer la fenetre principale.
        setTitle("Gestion Pharmacie");
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Etape 2: creer les onglets.
        JTabbedPane onglets = new JTabbedPane();
        onglets.addTab("Pharmacie", new PharmaciePanel());
        onglets.addTab("Clients", new ClientPanel());
        onglets.addTab("Medicaments", new MedicamentPanel());
        onglets.addTab("Fournisseurs", new FournisseurPanel());
        onglets.addTab("Ordonnances", new OrdonnancePanel());
        onglets.addTab("Ventes", new VentePanel());
        onglets.addTab("Commandes", new CommandePanel());
        onglets.addTab("Stock", new StockPanel());
        onglets.addTab("Dashboard", new DashboardPanel());

        // Etape 3: afficher les onglets dans la fenetre.
        add(onglets, BorderLayout.CENTER);
    }
}






/*this is the code of the main panel for the program 
(# that it pop up when we open the prpogram) */

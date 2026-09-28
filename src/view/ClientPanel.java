package view;

import controller.ClientController;
import controller.VenteController;

import java.awt.*;                 /*awt = it's the library used to build GUI(buttons , windows , colors ...) */
import java.awt.event.ActionEvent;              // howa li kay3ref ina button clickiti 3liha ( and also carries informations about that click )
import java.awt.event.ActionListener;           // hia li katb9a hadia l button wach tclickat 3lih wla ba9i ila yes , kayw9a3 "ActionEvent" => kaysme3 noise w ymchi yappliquer l code li 3tinah  
import java.util.List;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import model.Client;
import model.Vente;

public class ClientPanel extends JPanel implements ActionListener, ListSelectionListener {

    private ClientController controller = new ClientController();
    private VenteController controllervente = new VenteController();

    JTextField id, nom, prenom, telephone, adresse;
    JButton btnAjouter, btnModifier, btnSupprimer, btnVider, btnActualiser, btnHistorique;
    JPanel sidebar, content, boutons;
    DefaultTableModel model;
    JTable table;

    public ClientPanel() {
        setLayout(new BorderLayout());

        // Sidebar gauche 
        sidebar = new JPanel(new BorderLayout());
        sidebar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        sidebar.setPreferredSize(new Dimension(220, 0));
       

        // Formulaire (haut)
        JPanel form = new JPanel(new GridLayout(0, 1, 0, 5));
        

        form.add(new JLabel("Fiche client"));
        form.add(new JLabel("ID"));        id        = new JTextField(); form.add(id);
        form.add(new JLabel("Nom"));       nom       = new JTextField(); form.add(nom);
        form.add(new JLabel("Prénom"));    prenom    = new JTextField(); form.add(prenom);
        form.add(new JLabel("Téléphone")); telephone = new JTextField(); form.add(telephone);
        form.add(new JLabel("Adresse"));   adresse   = new JTextField(); form.add(adresse);

        // Boutons (bas)
        boutons = new JPanel(new GridLayout(2, 2, 5, 5));
        btnAjouter   = new JButton("Ajouter");
        boutons.add(btnAjouter);
        btnModifier  = new JButton("Modifier");
        boutons.add(btnModifier);
        btnSupprimer = new JButton("Supprimer");
        boutons.add(btnSupprimer);
        btnVider     = new JButton("Vider");
        boutons.add(btnVider);

        sidebar.add(form, BorderLayout.NORTH);
        sidebar.add(boutons, BorderLayout.SOUTH);

        // Contenu droit 
        content = new JPanel(new BorderLayout(5, 5));
        content.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel header = new JPanel(new BorderLayout());
        header.add(new JLabel("Liste des clients"), BorderLayout.WEST);
        JPanel headerBtns = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnHistorique = new JButton("Historique"); headerBtns.add(btnHistorique);
        btnActualiser = new JButton("Actualiser"); headerBtns.add(btnActualiser);
        header.add(headerBtns, BorderLayout.EAST);

        model = new DefaultTableModel(
            new String[]{"ID", "Nom", "Prénom", "Téléphone", "Adresse"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(model);

        content.add(header, BorderLayout.NORTH);
        content.add(new JScrollPane(table), BorderLayout.CENTER);

        add(sidebar, BorderLayout.WEST);
        add(content, BorderLayout.CENTER);

        // Actions_Button 
        btnAjouter.addActionListener(this);
        btnModifier.addActionListener(this);
        btnSupprimer.addActionListener(this);
        btnVider.addActionListener(this);
        btnActualiser.addActionListener(this);
        btnHistorique.addActionListener(this);
        table.getSelectionModel().addListSelectionListener(this);
        
        chargerClients();
    }
    
    @Override
	public void actionPerformed(ActionEvent e) {
    	if(e.getSource()== btnAjouter) {
    		ajouterClient();
    	}
    	else if(e.getSource()== btnModifier) {
    		modifierClient();
    		
    	}else if(e.getSource()== btnSupprimer) {
    		supprimerClient();
    		
    	}else if(e.getSource()== btnVider) {
    		viderChamps();
    		
    	}else if(e.getSource()== btnHistorique) {
    		afficherHistorique();
    	}else {
    		chargerClients();
    	}
		
	}
    
    @Override
	public void valueChanged(ListSelectionEvent e) {
    		if(!e.getValueIsAdjusting()) {
            remplirFormulaireDepuisTable();
    		}
	}

    // Fonctions
    private void chargerClients() {
        try {
            model.setRowCount(0);
            for (Client c : controller.listerClients())
                model.addRow(new Object[]{ c.getId(), c.getNom(), c.getPrenom(), c.getTelephone(), c.getAdresse() });
        } catch (Exception e) {
            ViewUtil.afficherErreur(this, "Erreur chargement: " + e.getMessage());
        }
    }

    private void ajouterClient() {
        try {
            boolean ok = controller.ajouterClient(nom.getText(), prenom.getText(), telephone.getText(), adresse.getText());
            ViewUtil.afficherResultat(this, ok, "Client ajouté.");
            chargerClients(); viderChamps();
        } catch (Exception e) { ViewUtil.afficherErreur(this, "Erreur ajout: " + e.getMessage()); }
    }

    private void modifierClient() {
        try {
            boolean ok = controller.modifierClient(ViewUtil.lireInt(id, "id"), nom.getText(), prenom.getText(), telephone.getText(), adresse.getText());
            ViewUtil.afficherResultat(this, ok, "Client modifié.");
            chargerClients();
        } catch (Exception e) { ViewUtil.afficherErreur(this, "Erreur modification: " + e.getMessage()); }
    }

    private void supprimerClient() {
        try {
            boolean ok = controller.supprimerClient(ViewUtil.lireInt(id, "id"));
            ViewUtil.afficherResultat(this, ok, "Client supprimé.");
            chargerClients(); viderChamps();
        } catch (Exception e) { ViewUtil.afficherErreur(this, "Erreur suppression: " + e.getMessage()); }
    }

    private void afficherHistorique() {
        try {
            String idText = id.getText().trim();
            if (idText.isEmpty()) { ViewUtil.afficherErreur(this, "Sélectionnez un client."); return; }

            int idClient = Integer.parseInt(idText);
            List<Vente> historique = controller.listerHistoriqueAchats(idClient);
            if (historique.isEmpty()) { ViewUtil.afficherErreur(this, "Aucun achat pour ce client."); return; }

            DefaultTableModel hModel = new DefaultTableModel(new String[]{"Id Vente", "Date", "Prix"}, 0) {
                public boolean isCellEditable(int r, int c) { return false; }
            };
            for (Vente v : historique)
                hModel.addRow(new Object[]{ v.getId(), v.getDateVente(), controllervente.calculerTotal(v.getLignes()) });

            JFrame frame = new JFrame("Historique - Client #" + idClient);
            frame.add(new JScrollPane(new JTable(hModel)));
            frame.setSize(500, 300);
            frame.setLocationRelativeTo(this);
            frame.setVisible(true);
        } catch (Exception e) { ViewUtil.afficherErreur(this, "Erreur historique: " + e.getMessage()); }
    }

    private void remplirFormulaireDepuisTable() {
        int ligne = table.getSelectedRow();
        if (ligne < 0) return;
        id.setText(ViewUtil.texte(model.getValueAt(ligne, 0)));
        nom.setText(ViewUtil.texte(model.getValueAt(ligne, 1)));
        prenom.setText(ViewUtil.texte(model.getValueAt(ligne, 2)));
        telephone.setText(ViewUtil.texte(model.getValueAt(ligne, 3)));
        adresse.setText(ViewUtil.texte(model.getValueAt(ligne, 4)));
    }

    private void viderChamps() {
        id.setText(""); nom.setText(""); prenom.setText(""); telephone.setText(""); adresse.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Gestion des Clients");
            frame.setContentPane(new ClientPanel());
            frame.setSize(900, 550);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
	

	
}


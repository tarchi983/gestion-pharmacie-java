package view;

import controller.StockController;
import model.Medicament;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class StockPanel extends JPanel implements ActionListener, ListSelectionListener {

    private StockController controller = new StockController();

    JTextField idMedicament, quantite, joursPeremption;
    JButton btnEntree, btnSortie, btnChercher, btnStockFaible, btnPerimes, btnProches, btnValeur, btnActualiser;
    JPanel sidebar, content, boutons;
    DefaultTableModel model;
    JTable table;
    JTextArea alertesArea;
    JLabel valeurStockLabel;

    public StockPanel() {
        setLayout(new BorderLayout());

        // Sidebar gauche 
        sidebar = new JPanel(new BorderLayout());
        sidebar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        sidebar.setPreferredSize(new Dimension(220, 0));

        // Formulaire (haut)
        JPanel form = new JPanel(new GridLayout(0, 1, 0, 5));
        form.add(new JLabel("Opérations Stock"));
        form.add(new JLabel("ID Médicament")); idMedicament = new JTextField(); form.add(idMedicament);
        form.add(new JLabel("Quantité")); quantite = new JTextField(); form.add(quantite);
        form.add(new JLabel("Jours avant péremption")); joursPeremption = new JTextField(); form.add(joursPeremption);

        // Boutons (bas)
        boutons = new JPanel(new GridLayout(0, 1, 5, 5));
        btnEntree = new JButton("Entrée Stock"); boutons.add(btnEntree);
        btnSortie = new JButton("Sortie Stock"); boutons.add(btnSortie);
        btnChercher = new JButton("Chercher"); boutons.add(btnChercher);
        btnStockFaible = new JButton("Stock Faible"); boutons.add(btnStockFaible);
        btnPerimes = new JButton("Produits Périmés"); boutons.add(btnPerimes);
        btnProches = new JButton("Péremption Proche"); boutons.add(btnProches);
        btnValeur = new JButton("Valeur Stock"); boutons.add(btnValeur);

        sidebar.add(form, BorderLayout.NORTH);
        sidebar.add(boutons, BorderLayout.SOUTH);

        // Contenu droit 
        content = new JPanel(new BorderLayout(5, 5));
        content.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel header = new JPanel(new BorderLayout());
        header.add(new JLabel("Gestion du Stock"), BorderLayout.WEST);
        JPanel headerBtns = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnActualiser = new JButton("Actualiser"); headerBtns.add(btnActualiser);
        header.add(headerBtns, BorderLayout.EAST);

        model = new DefaultTableModel(
            new String[]{"ID", "Nom", "Stock", "Prix", "Seuil", "Péremption"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(model);

        JPanel bas = new JPanel(new BorderLayout(5, 5));
        valeurStockLabel = new JLabel("Valeur stock : 0 DH");
        alertesArea = new JTextArea(5, 20);
        alertesArea.setEditable(false);
        bas.add(valeurStockLabel, BorderLayout.NORTH);
        bas.add(new JScrollPane(alertesArea), BorderLayout.CENTER);

        content.add(header, BorderLayout.NORTH);
        content.add(new JScrollPane(table), BorderLayout.CENTER);
        content.add(bas, BorderLayout.SOUTH);

        add(sidebar, BorderLayout.WEST);
        add(content, BorderLayout.CENTER);

        // Actions_Button 
        btnEntree.addActionListener(this);
        btnSortie.addActionListener(this);
        btnChercher.addActionListener(this);
        btnStockFaible.addActionListener(this);
        btnPerimes.addActionListener(this);
        btnProches.addActionListener(this);
        btnValeur.addActionListener(this);
        btnActualiser.addActionListener(this);
        table.getSelectionModel().addListSelectionListener(this);
        
        chargerStock();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btnEntree) {
            entreeStock();
        } else if(e.getSource() == btnSortie) {
            sortieStock();
        } else if(e.getSource() == btnChercher) {
            chercherMedicament();
        } else if(e.getSource() == btnStockFaible) {
            afficherStockFaible();
        } else if(e.getSource() == btnPerimes) {
            afficherPerimes();
        } else if(e.getSource() == btnProches) {
            afficherProchesPeremption();
        } else if(e.getSource() == btnValeur) {
            afficherValeurStock();
        } else if(e.getSource() == btnActualiser) {
            chargerStock();
        }
    }
    
    @Override
    public void valueChanged(ListSelectionEvent e) {
        if(!e.getValueIsAdjusting()) {
            remplirFormulaireDepuisTable();
        }
    }

    // Fonctions
    private void chargerTable(List<Medicament> medicaments) {
        model.setRowCount(0);
        for(Medicament m : medicaments) {
            model.addRow(new Object[]{
                m.getId(), m.getNomCommercial(), m.getQuantiteStock(),
                m.getPrix(), m.getSeuilAlerte(), m.getDatePeremption()
            });
        }
    }

    private void chargerStock() {
        try {
            chargerTable(controller.consulterStock());
            valeurStockLabel.setText("Valeur stock : " + controller.calculerValeurStock() + " DH");
            alertesArea.setText("");
            List<String> alertes = controller.genererAlertes(30);
            for(String alerte : alertes) {
                alertesArea.append("• " + alerte + "\n");
            }
        } catch (Exception e) {
            ViewUtil.afficherErreur(this, "Erreur chargement: " + e.getMessage());
        }
    }

    private void entreeStock() {
        try {
            int id = ViewUtil.lireInt(idMedicament, "ID");
            int qte = ViewUtil.lireInt(quantite, "Quantité");
            boolean ok = controller.entreeStock(id, qte);
            ViewUtil.afficherResultat(this, ok, "Stock ajouté.");
            chargerStock();
        } catch (Exception e) {
            ViewUtil.afficherErreur(this, "Erreur entrée: " + e.getMessage());
        }
    }

    private void sortieStock() {
        try {
            int id = ViewUtil.lireInt(idMedicament, "ID");
            int qte = ViewUtil.lireInt(quantite, "Quantité");
            boolean ok = controller.sortieStock(id, qte);
            ViewUtil.afficherResultat(this, ok, "Stock retiré.");
            chargerStock();
        } catch (Exception e) {
            ViewUtil.afficherErreur(this, "Erreur sortie: " + e.getMessage());
        }
    }

    private void chercherMedicament() {
        try {
            int id = ViewUtil.lireInt(idMedicament, "ID");
            Medicament m = controller.chercherMedicament(id);
            if(m == null) {
                ViewUtil.afficherErreur(this, "Médicament introuvable");
                return;
            }
            model.setRowCount(0);
            model.addRow(new Object[]{
                m.getId(), m.getNomCommercial(), m.getQuantiteStock(),
                m.getPrix(), m.getSeuilAlerte(), m.getDatePeremption()
            });
        } catch (Exception e) {
            ViewUtil.afficherErreur(this, "Erreur recherche: " + e.getMessage());
        }
    }

    private void afficherStockFaible() {
        chargerTable(controller.listerStockFaible());
    }

    private void afficherPerimes() {
        chargerTable(controller.listerMedicamentsPerimes());
    }

    private void afficherProchesPeremption() {
        try {
            int jours = ViewUtil.lireInt(joursPeremption, "Jours");
            chargerTable(controller.listerMedicamentsProchesPeremption(jours));
        } catch (Exception e) {
            ViewUtil.afficherErreur(this, "Veuillez spécifier le nombre de jours.");
        }
    }

    private void afficherValeurStock() {
        double valeur = controller.calculerValeurStock();
        JOptionPane.showMessageDialog(this, "Valeur totale du stock : " + valeur + " DH");
    }

    private void remplirFormulaireDepuisTable() {
        int ligne = table.getSelectedRow();
        if (ligne < 0) return;
        idMedicament.setText(ViewUtil.texte(model.getValueAt(ligne, 0)));
        // on ne met pas à jour la quantité ni les jours de péremption
        quantite.setText("");
        joursPeremption.setText("");
    }
}
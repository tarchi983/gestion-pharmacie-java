package view;

import controller.CommandeController;
import controller.FournisseurController;
import controller.MedicamentController;

import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.Commande;
import model.Fournisseur;
import model.Medicament;

public class CommandePanel extends JPanel implements ActionListener {

    private final CommandeController    commandeController    = new CommandeController();
    private final FournisseurController fournisseurController = new FournisseurController();
    private final MedicamentController  medController         = new MedicamentController();

    JComboBox<String>  cbFournisseur, cbMedicament;
    JSpinner           spQuantite = new JSpinner(new SpinnerNumberModel(1, 1, 99999, 1));
    JTextField         txtPrixAchat = new JTextField();
    JButton            btnAjouterLigne, btnViderPanier, btnValider, btnAnnuler;
    JButton            btnChangerStatut, btnReceptionner, btnActualiser;
    DefaultTableModel  panierModel, historiqueModel;
    JTable             historiqueTable;
    JLabel             lblTotal;

    List<Fournisseur>  fournisseurs;
    List<Medicament>   medicaments;
    int                idCommandeEnCours = -1;

    public CommandePanel() {
        setLayout(new BorderLayout());
        chargerDonnees();

        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
            buildFormulairePanel(), buildBasPanel());
        split.setResizeWeight(0.0);
        split.setDividerSize(5);
        split.setBorder(null);
        add(split, BorderLayout.CENTER);
        chargerHistorique();
    }

    // ── Formulaire ────────────────────────────────────────────────────
    private JPanel buildFormulairePanel() {
        JPanel p = new JPanel(new GridBagLayout());
        p.setBorder(BorderFactory.createEmptyBorder(10, 14, 10, 14));
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(4, 6, 4, 6);
        g.fill   = GridBagConstraints.HORIZONTAL;

        g.gridx = 0; g.gridy = 0; g.gridwidth = 6; g.weightx = 1;
        JLabel titre = new JLabel("Nouvelle commande");
        titre.setFont(titre.getFont().deriveFont(Font.BOLD, 14f));
        p.add(titre, g); g.gridwidth = 1;

        // Fournisseur | Médicament
        g.gridy = 1; g.gridx = 0; g.weightx = 0; p.add(new JLabel("Fournisseur"), g);
        cbFournisseur = new JComboBox<>();
        if (fournisseurs != null)
            for (Fournisseur f : fournisseurs) cbFournisseur.addItem(f.getNomSociete());
        g.gridx = 1; g.weightx = 0.35; p.add(cbFournisseur, g);

        g.gridx = 2; g.weightx = 0; p.add(new JLabel("Médicament"), g);
        cbMedicament = new JComboBox<>();
        if (medicaments != null)
            for (Medicament m : medicaments)
                cbMedicament.addItem(m.getNomCommercial() + " | stock " + m.getQuantiteStock());
        g.gridx = 3; g.weightx = 0.65; g.gridwidth = 3; p.add(cbMedicament, g); g.gridwidth = 1;

        // Quantité | Prix | boutons
        g.gridy = 2; g.gridx = 0; g.weightx = 0; p.add(new JLabel("Quantité"), g);
        g.gridx = 1; g.weightx = 0.15; p.add(spQuantite, g);
        g.gridx = 2; g.weightx = 0;    p.add(new JLabel("Prix achat (DH)"), g);
        g.gridx = 3; g.weightx = 0.20; p.add(txtPrixAchat, g);

        btnAjouterLigne = new JButton("Ajouter ligne");
        btnViderPanier  = new JButton("Vider panier");
        g.gridx = 4; g.weightx = 0.15; p.add(btnAjouterLigne, g);
        g.gridx = 5; g.weightx = 0.15; p.add(btnViderPanier, g);

        btnAjouterLigne.addActionListener(this);
        btnViderPanier.addActionListener(this);
        return p;
    }

    // ── Bas ───────────────────────────────────────────────────────────
    private JComponent buildBasPanel() {
        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
            buildPanierPanel(), buildHistoriquePanel());
        split.setResizeWeight(0.4);
        split.setDividerSize(5);
        split.setBorder(null);
        return split;
    }

    private JPanel buildPanierPanel() {
        JPanel p = new JPanel(new BorderLayout(0, 6));
        p.setBorder(BorderFactory.createEmptyBorder(8, 14, 8, 14));

        p.add(new JLabel("Panier de la commande"), BorderLayout.NORTH);

        panierModel = new DefaultTableModel(
            new String[]{"Médicament", "Quantité", "Prix achat", "Sous-total"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        p.add(new JScrollPane(new JTable(panierModel)), BorderLayout.CENTER);

        // Total + boutons
        JPanel barre = new JPanel(new BorderLayout());
        lblTotal = new JLabel("0.00 DH");
        lblTotal.setFont(lblTotal.getFont().deriveFont(Font.BOLD, 15f));
        barre.add(lblTotal, BorderLayout.WEST);

        JPanel btns = new JPanel(new FlowLayout(FlowLayout.RIGHT, 6, 0));
        btnAnnuler = new JButton("Annuler"); btnAnnuler.setEnabled(false);
        btnValider = new JButton("Valider"); btnValider.setEnabled(false);
        btns.add(btnAnnuler); btns.add(btnValider);
        barre.add(btns, BorderLayout.EAST);
        p.add(barre, BorderLayout.SOUTH);

        btnAnnuler.addActionListener(this);
        btnValider.addActionListener(this);
        return p;
    }

    private JPanel buildHistoriquePanel() {
        JPanel p = new JPanel(new BorderLayout(0, 6));
        p.setBorder(BorderFactory.createEmptyBorder(8, 14, 8, 14));

        JPanel header = new JPanel(new BorderLayout());
        header.add(new JLabel("Historique des commandes"), BorderLayout.WEST);

        JPanel headerBtns = new JPanel(new FlowLayout(FlowLayout.RIGHT, 6, 0));
        btnChangerStatut = new JButton("Changer statut");
        btnReceptionner  = new JButton("Réceptionner");
        btnActualiser    = new JButton("Actualiser");
        headerBtns.add(btnChangerStatut);
        headerBtns.add(btnReceptionner);
        headerBtns.add(btnActualiser);
        header.add(headerBtns, BorderLayout.EAST);
        p.add(header, BorderLayout.NORTH);

        historiqueModel = new DefaultTableModel(
            new String[]{"ID", "Fournisseur", "Date", "Statut", "Total"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        historiqueTable = new JTable(historiqueModel);
        historiqueTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        p.add(new JScrollPane(historiqueTable), BorderLayout.CENTER);

        btnChangerStatut.addActionListener(this);
        btnReceptionner.addActionListener(this);
        btnActualiser.addActionListener(this);
        return p;
    }

    // ── ActionListener ────────────────────────────────────────────────
    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if      (src == btnAjouterLigne)  ajouterLigne();
        else if (src == btnViderPanier)   viderPanier();
        else if (src == btnValider)       validerCommande();
        else if (src == btnAnnuler)       annulerCommande();
        else if (src == btnChangerStatut) changerStatut();
        else if (src == btnReceptionner)  receptionnerLivraison();
        else                              chargerHistorique();
    }

    // ── Actions ───────────────────────────────────────────────────────

    private void ajouterLigne() {
        try {
            int idxMed = cbMedicament.getSelectedIndex();
            if (idxMed < 0) { ViewUtil.afficherErreur(this, "Sélectionnez un médicament."); return; }
            double prixAchat = ViewUtil.lireDouble(txtPrixAchat, "prix achat");
            Medicament med   = medicaments.get(idxMed);
            int qte          = (int) spQuantite.getValue();

            if (idCommandeEnCours < 0) {
                commandeController.creerCommande(obtenirIdFournisseur(), LocalDate.now(), "En attente");
                List<Commande> all = commandeController.listerCommandes();
                if (!all.isEmpty()) idCommandeEnCours = all.get(all.size() - 1).getIdCommande();
            }
            commandeController.creerLigneCommande(idCommandeEnCours, med.getId(), qte, prixAchat);
            panierModel.addRow(new Object[]{
                med.getNomCommercial(), qte,
                String.format(Locale.US, "%.2f", prixAchat),
                String.format(Locale.US, "%.2f", prixAchat * qte)
            });
            rafraichirTotal();
            mettreAJourBoutons();
        } catch (Exception e) { ViewUtil.afficherErreur(this, e.getMessage()); }
    }

    private void validerCommande() {
        if (panierModel.getRowCount() == 0) { ViewUtil.afficherErreur(this, "Le panier est vide."); return; }
        commandeController.changerStatut(idCommandeEnCours, "Validée");
        ViewUtil.afficherResultat(this, true, "Commande #" + idCommandeEnCours + " validée.");
        reinitialiserPanier(); chargerHistorique(); rechargerComboMedicaments();
    }

    private void annulerCommande() {
        if (idCommandeEnCours < 0) return;
        if (JOptionPane.showConfirmDialog(this, "Annuler la commande #" + idCommandeEnCours + " ?",
                "Confirmation", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) return;
        Commande c = commandeController.chercherCommandeParId(idCommandeEnCours);
        if (c != null) commandeController.annulerCommande(c);
        reinitialiserPanier();
    }

    private void viderPanier() {
        if (panierModel.getRowCount() == 0) return;
        if (JOptionPane.showConfirmDialog(this, "Vider le panier ?",
                "Confirmation", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) return;
        Commande c = commandeController.chercherCommandeParId(idCommandeEnCours);
        commandeController.annulerLigne(c);
        panierModel.setRowCount(0);
        }

    private void changerStatut() {
        int ligne = historiqueTable.getSelectedRow();
        if (ligne < 0) { ViewUtil.afficherErreur(this, "Sélectionnez une commande."); return; }
        int    id     = (int) historiqueModel.getValueAt(ligne, 0);
        String actuel = ViewUtil.texte(historiqueModel.getValueAt(ligne, 3));
        if (estLivree(actuel)) { ViewUtil.afficherErreur(this, "Commande livrée — statut non modifiable."); return; }

        String[] statuts = {"En attente", "Validée", "En cours", "Annulée"};
        String choix = (String) JOptionPane.showInputDialog(this,
            "Statut actuel : " + actuel, "Changer statut #" + id,
            JOptionPane.PLAIN_MESSAGE, null, statuts, actuel);
        if (choix == null || choix.equals(actuel)) return;
        ViewUtil.afficherResultat(this, commandeController.changerStatut(id, choix), "Statut mis à jour.");
        chargerHistorique();
    }

    private void receptionnerLivraison() {
        int ligne = historiqueTable.getSelectedRow();
        if (ligne < 0) { ViewUtil.afficherErreur(this, "Sélectionnez une commande."); return; }
        int    id     = (int) historiqueModel.getValueAt(ligne, 0);
        String statut = ViewUtil.texte(historiqueModel.getValueAt(ligne, 3));
        if (estLivree(statut)) { ViewUtil.afficherErreur(this, "Stock déjà mis à jour."); return; }
        Commande c = commandeController.chercherCommandeParId(id);
        if (c == null) { ViewUtil.afficherErreur(this, "Commande introuvable."); return; }
        ViewUtil.afficherResultat(this, commandeController.recevoirLivraison(c), "Livraison reçue — stock mis à jour.");
        chargerHistorique(); rechargerComboMedicaments();
    }

    private void chargerHistorique() {
        historiqueModel.setRowCount(0);
        for (Commande c : commandeController.listerCommandes()) {
            historiqueModel.addRow(new Object[]{
                c.getIdCommande(), nomFournisseurParId(c.getIdFournisseur()),
                c.getDateCommande(), c.getStatut(),
                String.format(Locale.US, "%.2f", commandeController.calculerTotal(c))
            });
        }
    }

    // ── Helpers ───────────────────────────────────────────────────────

    private void reinitialiserPanier() {
        panierModel.setRowCount(0); idCommandeEnCours = -1; rafraichirTotal(); mettreAJourBoutons();
    }

    private void rafraichirTotal() {
        double total = 0;
        for (int i = 0; i < panierModel.getRowCount(); i++)
            try { total += Double.parseDouble(panierModel.getValueAt(i, 3).toString()); }
            catch (NumberFormatException ignored) {}
        lblTotal.setText(String.format(Locale.US, "%.2f DH", total));
    }

    private void mettreAJourBoutons() {
        boolean actif = panierModel.getRowCount() > 0;
        btnValider.setEnabled(actif); btnAnnuler.setEnabled(actif);
    }

    private void chargerDonnees() {
        try { fournisseurs = fournisseurController.listerFournisseurs(); } catch (Exception ignored) {}
        try { medicaments  = medController.listerMedicaments();          } catch (Exception ignored) {}
    }

    private void rechargerComboMedicaments() {
        try {
            medicaments = medController.listerMedicaments();
            cbMedicament.removeAllItems();
            for (Medicament m : medicaments)
                cbMedicament.addItem(m.getNomCommercial() + " | stock " + m.getQuantiteStock());
        } catch (Exception ignored) {}
    }

    private int obtenirIdFournisseur() {
        int idx = cbFournisseur.getSelectedIndex();
        return (idx >= 0 && fournisseurs != null && idx < fournisseurs.size())
            ? fournisseurs.get(idx).getId() : 0;
    }

    private String nomFournisseurParId(int id) {
        if (fournisseurs == null) return "Inconnu";
        for (Fournisseur f : fournisseurs) if (f.getId() == id) return f.getNomSociete();
        return "Inconnu";
    }

    private boolean estLivree(String statut) {
        return statut.equalsIgnoreCase("Livrée") || statut.equalsIgnoreCase("Livree");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Gestion des Commandes");
            frame.setContentPane(new CommandePanel());
            frame.setSize(1100, 800);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
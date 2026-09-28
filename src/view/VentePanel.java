package view;

import controller.ClientController;
import controller.MedicamentController;
import controller.OrdonnanceController;
import controller.VenteController;

import java.awt.*;
import java.awt.event.*;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import model.Client;
import model.Medicament;
import model.Ordonnance;
import model.Vente;

public class VentePanel extends JPanel implements ActionListener {

    private final VenteController      venteController  = new VenteController();
    private final ClientController     clientController = new ClientController();
    private final MedicamentController  medController         = new MedicamentController();
    private final OrdonnanceController  ordonnanceController  = new OrdonnanceController();

    JComboBox<String>  cbClient, cbProduit;
    JSpinner           spQuantite;
    JCheckBox  chkOrdonnance = new JCheckBox("Ordonnance presente");
    JTextField txtMedecin    = new JTextField();
    JTextField txtDateOrd    = new JTextField(LocalDate.now().toString());
    JTextField txtNotes      = new JTextField();
    JButton            btnAjouterLigne, btnViderPanier, btnValider, btnAnnuler;
    DefaultTableModel  panierModel, historiqueModel;
    JLabel             lblTotal;

    List<Client>       clients;
    List<Medicament>   medicaments;
    int                idVenteEnCours = -1;

    public VentePanel() {
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

        // Titre
        g.gridx = 0; g.gridy = 0; g.gridwidth = 6; g.weightx = 1;
        JLabel titre = new JLabel("Nouvelle vente");
        titre.setFont(titre.getFont().deriveFont(Font.BOLD, 14f));
        p.add(titre, g); g.gridwidth = 1;

        // Client | Produit
        g.gridy = 1; g.gridx = 0; g.weightx = 0; p.add(new JLabel("Client"), g);
        cbClient = new JComboBox<>();
        cbClient.addItem("Client anonyme");
        if (clients != null) for (Client c : clients) cbClient.addItem(c.getNom() + " " + c.getPrenom());
        g.gridx = 1; g.weightx = 0.35; p.add(cbClient, g);

        g.gridx = 2; g.weightx = 0; p.add(new JLabel("Produit"), g);
        cbProduit = new JComboBox<>();
        if (medicaments != null) for (Medicament m : medicaments)
            cbProduit.addItem(m.getNomCommercial() + " | stock " + m.getQuantiteStock());
        g.gridx = 3; g.weightx = 0.65; g.gridwidth = 3; p.add(cbProduit, g); g.gridwidth = 1;

        // Quantité | Ajouter | Vider
        g.gridy = 2; g.gridx = 0; g.weightx = 0; p.add(new JLabel("Quantite"), g);
        spQuantite = creerSpinnerLatinDigits(1, 1, 9999, 1);
        g.gridx = 1; g.weightx = 0.35; p.add(spQuantite, g);

        btnAjouterLigne = new JButton("Ajouter ligne");
        g.gridx = 2; g.weightx = 0.32; g.gridwidth = 2; p.add(btnAjouterLigne, g); g.gridwidth = 1;
        btnViderPanier = new JButton("Vider panier");
        g.gridx = 4; g.weightx = 0.33; g.gridwidth = 2; p.add(btnViderPanier, g); g.gridwidth = 1;

        // Ordonnance checkbox
        g.gridy = 3; g.gridx = 0; g.gridwidth = 6; g.weightx = 1;
        p.add(chkOrdonnance, g); g.gridwidth = 1;

        // Champs ordonnance (masqués par défaut)
        JPanel panelOrd = new JPanel(new GridLayout(0, 2, 6, 4));
        panelOrd.add(new JLabel("Médecin"));    panelOrd.add(txtMedecin);
        panelOrd.add(new JLabel("Date (yyyy-MM-dd)")); panelOrd.add(txtDateOrd);
        panelOrd.add(new JLabel("Notes"));      panelOrd.add(txtNotes);
        panelOrd.setVisible(false);

        g.gridy = 4; g.gridx = 0; g.gridwidth = 6; g.weightx = 1;
        p.add(panelOrd, g); g.gridwidth = 1;

        chkOrdonnance.addActionListener(e -> {
            panelOrd.setVisible(chkOrdonnance.isSelected());
            if (!chkOrdonnance.isSelected()) {
                txtMedecin.setText(""); txtDateOrd.setText(LocalDate.now().toString()); txtNotes.setText("");
            }
            p.revalidate(); p.repaint();
        });



        btnAjouterLigne.addActionListener(this);
        btnViderPanier.addActionListener(this);
        return p;
    }

    // ── Bas ───────────────────────────────────────────────────────────
    private JComponent buildBasPanel() {
        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
            buildPanierPanel(), buildHistoriquePanel());
        split.setResizeWeight(0.45);
        split.setDividerSize(5);
        split.setBorder(null);
        return split;
    }

    private JPanel buildPanierPanel() {
        JPanel p = new JPanel(new BorderLayout(0, 6));
        p.setBorder(BorderFactory.createEmptyBorder(8, 14, 8, 14));

        p.add(new JLabel("Panier de la vente"), BorderLayout.NORTH);

        panierModel = new DefaultTableModel(
            new String[]{"Médicament", "Quantité", "Prix unitaire", "Sous-total"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        p.add(new JScrollPane(new JTable(panierModel)), BorderLayout.CENTER);

        // Barre : Total | Annuler | Valider
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
        p.add(new JLabel("Historique des ventes"), BorderLayout.NORTH);

        historiqueModel = new DefaultTableModel(
            new String[]{"ID", "Date", "Client", "Ordonnance", "Total"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        p.add(new JScrollPane(new JTable(historiqueModel)), BorderLayout.CENTER);
        return p;
    }

    // ── ActionListener ────────────────────────────────────────────────
    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if      (src == btnAjouterLigne) ajouterLigne();
        else if (src == btnViderPanier)  viderPanier();
        else if (src == btnValider)      validerVente();
        else if (src == btnAnnuler)      annulerVente();
    }

    // ── Actions ───────────────────────────────────────────────────────

    private void ajouterLigne() {
        int idxMed = cbProduit.getSelectedIndex();
        if (idxMed < 0 || medicaments == null || medicaments.isEmpty()) {
            ViewUtil.afficherErreur(this, "Sélectionnez un produit."); return;
        }
        Medicament med = medicaments.get(idxMed);
        int qte = (int) spQuantite.getValue();

        if (qte > med.getQuantiteStock()) {
            ViewUtil.afficherErreur(this, "Stock insuffisant (disponible : " + med.getQuantiteStock() + ")."); return;
        }

        if (idVenteEnCours < 0) {
            // Ordonnance créée à la validation, pas maintenant
            venteController.creerVente(obtenirIdClient(), 0, LocalDate.now());
            List<Vente> ventes = venteController.listerVentes();
            if (!ventes.isEmpty()) idVenteEnCours = ventes.get(ventes.size() - 1).getId();
        }

        venteController.creerLigneVente(idVenteEnCours, med.getId(), qte);
        panierModel.addRow(new Object[]{
            med.getNomCommercial(), qte,
            String.format(Locale.US, "%.2f", med.getPrix()),
            String.format(Locale.US, "%.2f", med.getPrix() * qte)
        });
        rafraichirTotal();
        mettreAJourBoutons();
    }

    private void validerVente() {
        if (panierModel.getRowCount() == 0) { ViewUtil.afficherErreur(this, "Le panier est vide."); return; }

        Vente venteAValider = null;
        for (Vente v : venteController.listerVentes())
            if (v.getId() == idVenteEnCours) { venteAValider = v; break; }
        if (venteAValider == null) { ViewUtil.afficherErreur(this, "Vente introuvable."); return; }

        // ✅ Créer l'ordonnance maintenant (si cochée) et l'associer à la vente
       // erreur déjà affichée
        else if(chkOrdonnance.isSelected()) {
        	int idOrd = enregistrerOrdonnanceSiPresente(venteAValider.getIdClient());
        	venteAValider.setIdOrdonnance(idOrd);
        	venteController.modifierOrdo(venteAValider);
        }
        

        boolean ok = venteController.enregistrerVente(venteAValider);
        if (!ok) { ViewUtil.afficherErreur(this, "Stock insuffisant ou ordonnance invalide."); return; }

        ViewUtil.afficherResultat(this, true, "Vente #" + idVenteEnCours + " validée.\nTotal : " + lblTotal.getText());
        reinitialiserPanier();
        chargerHistorique();
        rechargerComboMedicaments();

        venteController.genererEtEnregistrerFacture(venteAValider); 
    }

    private void annulerVente() {
        if (idVenteEnCours < 0) return;
        if (JOptionPane.showConfirmDialog(this, "Annuler la vente #" + idVenteEnCours + " ?",
                "Confirmation", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) return;
        for (Vente v : venteController.listerVentes())
            if (v.getId() == idVenteEnCours) { venteController.annulerVente(v); break; }
        reinitialiserPanier();
    }

    private void viderPanier() {
        if (panierModel.getRowCount() == 0) return;
        if (JOptionPane.showConfirmDialog(this, "Vider le panier ?",
                "Confirmation", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) return;
        for (Vente v : venteController.listerVentes())
            if (v.getId() == idVenteEnCours) { venteController.annulerLigne(v); break; }
        panierModel.setRowCount(0);
        rafraichirTotal();
        mettreAJourBoutons();
    }

    private void chargerHistorique() {
        historiqueModel.setRowCount(0);
        for (Vente v : venteController.listerVentes())
            historiqueModel.addRow(new Object[]{
                v.getId(), v.getDateVente(), nomClientParId(v.getIdClient()),
                v.getIdOrdonnance() != 0 ? "Oui" : "Non",
                String.format(Locale.US, "%.2f", venteController.calculerTotal(v.getLignes()))
            });
    }

    // ── Helpers ───────────────────────────────────────────────────────

    private void reinitialiserPanier() {
        panierModel.setRowCount(0); idVenteEnCours = -1; rafraichirTotal(); mettreAJourBoutons();
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
        try { clients     = clientController.listerClients();  } catch (Exception ignored) {}
        try { medicaments = medController.listerMedicaments(); } catch (Exception ignored) {}
    }

    private void rechargerComboMedicaments() {
        try {
            medicaments = medController.listerMedicaments();
            cbProduit.removeAllItems();
            for (Medicament m : medicaments)
                cbProduit.addItem(m.getNomCommercial() + " | stock " + m.getQuantiteStock());
        } catch (Exception ignored) {}
    }

    private int obtenirIdClient() {
        int idx = cbClient.getSelectedIndex();
        return (idx <= 0 || clients == null || idx - 1 >= clients.size()) ? 0 : clients.get(idx - 1).getId();
    }

    /**
     * Si la checkbox est cochée, crée l'ordonnance en BDD et retourne son ID.
     * Sinon retourne 0 (vente sans ordonnance).
     */
    private int enregistrerOrdonnanceSiPresente(int idClient) {
        

        String medecin = txtMedecin.getText().trim();
        String notes   = txtNotes.getText().trim();
        LocalDate date;
        try {
            date = LocalDate.parse(txtDateOrd.getText().trim());
        } catch (Exception ex) {
            ViewUtil.afficherErreur(this, "Date ordonnance invalide (format : yyyy-MM-dd).");
            return 0;
        }

        if (medecin.isEmpty()) {
            ViewUtil.afficherErreur(this, "Le nom du médecin est obligatoire.");
            return 0;
        }
        if (idClient == 0) {
            ViewUtil.afficherErreur(this, "Sélectionnez un client pour enregistrer l'ordonnance.");
            return 0;
        }

        boolean ok = ordonnanceController.ajouterOrdonnance(idClient, medecin, date, notes);
        if (!ok) { ViewUtil.afficherErreur(this, "Erreur lors de l'enregistrement de l'ordonnance."); return 0; }

        // Récupérer l'ID de l'ordonnance tout juste créée
        List<Ordonnance> ords = ordonnanceController.listerOrdonnancesParClient(idClient);
        return ords.isEmpty() ? 0 : ords.get(ords.size() - 1).getId();
    }

    private String nomClientParId(int idClient) {
        if (clients == null) return String.valueOf(idClient);
        for (Client c : clients) if (c.getId() == idClient) return c.getNom() + " " + c.getPrenom();
        return "Client #" + idClient;
    }

    private JSpinner creerSpinnerLatinDigits(int valeur, int min, int max, int pas) {
        JSpinner spinner = new JSpinner(new SpinnerNumberModel(valeur, min, max, pas));
        NumberFormat fmt = NumberFormat.getIntegerInstance(Locale.US);
        fmt.setGroupingUsed(false);
        NumberFormatter formatter = new NumberFormatter(fmt);
        formatter.setValueClass(Integer.class);
        formatter.setMinimum(min); formatter.setMaximum(max);
        formatter.setAllowsInvalid(false);
        JSpinner.NumberEditor editor = new JSpinner.NumberEditor(spinner);
        editor.getTextField().setFormatterFactory(new DefaultFormatterFactory(formatter));
        editor.getTextField().setHorizontalAlignment(JTextField.LEFT);
        spinner.setEditor(editor);
        return spinner;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Gestion des Ventes");
            frame.setContentPane(new VentePanel());
            frame.setSize(1100, 800);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
package view;

import controller.MedicamentController;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import model.Medicament;

public class MedicamentPanel extends JPanel implements ActionListener, ListSelectionListener {

    private MedicamentController controller = new MedicamentController();
    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // Champs du formulaire
    JTextField id, nomCommercial, composition, forme, dosage;
    JTextField prix, quantiteStock, datePeremption, seuilAlerte;
    JTextField searchField;

    // Boutons
    JButton btnAjouter, btnModifier, btnSupprimer, btnVider, btnActualiser, btnRechercher;

    // Table
    DefaultTableModel model;
    JTable table;

    public MedicamentPanel() {
        setLayout(new BorderLayout());

        // ── Sidebar gauche ────────────────────────────────────────────
        JPanel sidebar = new JPanel(new BorderLayout());
        sidebar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        sidebar.setPreferredSize(new Dimension(240, 0));

        JPanel form = new JPanel(new GridLayout(0, 1, 0, 4));
        form.add(new JLabel("Fiche médicament"));

        form.add(new JLabel("ID"));
        id = new JTextField(); id.setEditable(false); form.add(id);

        form.add(new JLabel("Nom commercial"));
        nomCommercial = new JTextField(); form.add(nomCommercial);

        form.add(new JLabel("Composition"));
        composition = new JTextField(); form.add(composition);

        form.add(new JLabel("Forme"));
        forme = new JTextField(); form.add(forme);

        form.add(new JLabel("Dosage"));
        dosage = new JTextField(); form.add(dosage);

        form.add(new JLabel("Prix (MAD)"));
        prix = new JTextField(); form.add(prix);

        form.add(new JLabel("Quantité en stock"));
        quantiteStock = new JTextField(); form.add(quantiteStock);

        form.add(new JLabel("Date péremption (yyyy-MM-dd)"));
        datePeremption = new JTextField(); form.add(datePeremption);

        form.add(new JLabel("Seuil alerte"));
        seuilAlerte = new JTextField(); form.add(seuilAlerte);

        // Boutons CRUD
        JPanel boutons = new JPanel(new GridLayout(2, 2, 5, 5));
        btnAjouter   = new JButton("Ajouter");   boutons.add(btnAjouter);
        btnModifier  = new JButton("Modifier");  boutons.add(btnModifier);
        btnSupprimer = new JButton("Supprimer"); boutons.add(btnSupprimer);
        btnVider     = new JButton("Vider");     boutons.add(btnVider);

        sidebar.add(form,    BorderLayout.NORTH);
        sidebar.add(boutons, BorderLayout.SOUTH);

        // ── Contenu droit ─────────────────────────────────────────────
        JPanel content = new JPanel(new BorderLayout(5, 5));
        content.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // En-tête
        JPanel header = new JPanel(new BorderLayout(5, 0));
        header.add(new JLabel("Liste des médicaments"), BorderLayout.WEST);

        JPanel headerBtns = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        searchField   = new JTextField(12);
        btnRechercher = new JButton("Rechercher");
        btnActualiser = new JButton("Actualiser");
        headerBtns.add(searchField);
        headerBtns.add(btnRechercher);
        headerBtns.add(btnActualiser);
        header.add(headerBtns, BorderLayout.EAST);

        // Table
        model = new DefaultTableModel(
            new String[]{"ID", "Nom commercial", "Composition", "Forme",
                         "Dosage", "Prix", "Stock", "Date péremption", "Seuil alerte"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        content.add(header,                BorderLayout.NORTH);
        content.add(new JScrollPane(table), BorderLayout.CENTER);

        add(sidebar, BorderLayout.WEST);
        add(content, BorderLayout.CENTER);

        // ── Listeners ────────────────────────────────────────────────
        btnAjouter.addActionListener(this);
        btnModifier.addActionListener(this);
        btnSupprimer.addActionListener(this);
        btnVider.addActionListener(this);
        btnActualiser.addActionListener(this);
        btnRechercher.addActionListener(this);
        table.getSelectionModel().addListSelectionListener(this);

        chargerMedicaments();
    }

    // ── ActionListener ───────────────────────────────────────────────
    @Override
    public void actionPerformed(ActionEvent e) {
        if      (e.getSource() == btnAjouter)    ajouterMedicament();
        else if (e.getSource() == btnModifier)   modifierMedicament();
        else if (e.getSource() == btnSupprimer)  supprimerMedicament();
        else if (e.getSource() == btnVider)      viderChamps();
        else if (e.getSource() == btnRechercher) rechercherMedicament();
        else                                     chargerMedicaments(); // btnActualiser
    }

    // ── ListSelectionListener ────────────────────────────────────────
    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            remplirFormulaireDepuisTable();
        }
    }

    // ── Fonctions métier ─────────────────────────────────────────────

    private void chargerMedicaments() {
        try {
            model.setRowCount(0);
            for (Medicament m : controller.listerMedicaments())
                model.addRow(toRow(m));
        } catch (Exception e) {
            ViewUtil.afficherErreur(this, "Erreur chargement : " + e.getMessage());
        }
    }

    private void ajouterMedicament() {
        try {
            controller.ajouterMedicament(
                nomCommercial.getText(),
                composition.getText(),
                forme.getText(),
                dosage.getText(),
                lireDouble("prix"),
                lireInt("quantiteStock"),
                lireDate("datePeremption"),
                lireInt("seuilAlerte")
            );
            ViewUtil.afficherResultat(this, true, "Médicament ajouté.");
            chargerMedicaments();
            viderChamps();
        } catch (Exception e) {
            ViewUtil.afficherErreur(this, "Erreur ajout : " + e.getMessage());
        }
    }

    private void modifierMedicament() {
        try {
            controller.modifierMedicament(
                ViewUtil.lireInt(id, "id"),
                nomCommercial.getText(),
                composition.getText(),
                forme.getText(),
                dosage.getText(),
                lireDouble("prix"),
                lireInt("quantiteStock"),
                lireDate("datePeremption"),
                lireInt("seuilAlerte")
            );
            ViewUtil.afficherResultat(this, true, "Médicament modifié.");
            chargerMedicaments();
        } catch (Exception e) {
            ViewUtil.afficherErreur(this, "Erreur modification : " + e.getMessage());
        }
    }

    private void supprimerMedicament() {
        try {
            boolean ok = controller.supprimerMedicament(ViewUtil.lireInt(id, "id"));
            ViewUtil.afficherResultat(this, ok, "Médicament supprimé.");
            chargerMedicaments();
            viderChamps();
        } catch (Exception e) {
            ViewUtil.afficherErreur(this, "Erreur suppression : " + e.getMessage());
        }
    }

    private void rechercherMedicament() {
        try {
            String motCle = searchField.getText().trim();
            List<Medicament> resultats = motCle.isEmpty()
                ? controller.listerMedicaments()
                : controller.chercherMedicamentsParNom(motCle);

            model.setRowCount(0);
            for (Medicament m : resultats)
                model.addRow(toRow(m));

            if (resultats.isEmpty())
                ViewUtil.afficherErreur(this, "Aucun médicament trouvé pour : " + motCle);
        } catch (Exception e) {
            ViewUtil.afficherErreur(this, "Erreur recherche : " + e.getMessage());
        }
    }

    private void remplirFormulaireDepuisTable() {
        int ligne = table.getSelectedRow();
        if (ligne < 0) return;
        id.setText(ViewUtil.texte(model.getValueAt(ligne, 0)));
        nomCommercial.setText(ViewUtil.texte(model.getValueAt(ligne, 1)));
        composition.setText(ViewUtil.texte(model.getValueAt(ligne, 2)));
        forme.setText(ViewUtil.texte(model.getValueAt(ligne, 3)));
        dosage.setText(ViewUtil.texte(model.getValueAt(ligne, 4)));
        prix.setText(ViewUtil.texte(model.getValueAt(ligne, 5)));
        quantiteStock.setText(ViewUtil.texte(model.getValueAt(ligne, 6)));
        datePeremption.setText(ViewUtil.texte(model.getValueAt(ligne, 7)));
        seuilAlerte.setText(ViewUtil.texte(model.getValueAt(ligne, 8)));
    }

    private void viderChamps() {
        id.setText(""); nomCommercial.setText(""); composition.setText("");
        forme.setText(""); dosage.setText(""); prix.setText("");
        quantiteStock.setText(""); datePeremption.setText(""); seuilAlerte.setText("");
        searchField.setText("");
    }

    // ── Helpers de lecture ───────────────────────────────────────────

    private double lireDouble(String champNom) {
        JTextField champ = champNom.equals("prix") ? prix : null;
        if (champ == null) throw new IllegalArgumentException("Champ inconnu : " + champNom);
        String txt = champ.getText().trim();
        if (txt.isEmpty()) throw new IllegalArgumentException("Le champ '" + champNom + "' est vide.");
        try { return Double.parseDouble(txt); }
        catch (NumberFormatException ex) { throw new IllegalArgumentException("'" + champNom + "' doit être un nombre décimal."); }
    }

    private int lireInt(String champNom) {
        JTextField champ;
        switch (champNom) {
            case "quantiteStock": champ = quantiteStock; break;
            case "seuilAlerte":   champ = seuilAlerte;   break;
            default: throw new IllegalArgumentException("Champ inconnu : " + champNom);
        }
        String txt = champ.getText().trim();
        if (txt.isEmpty()) throw new IllegalArgumentException("Le champ '" + champNom + "' est vide.");
        try { return Integer.parseInt(txt); }
        catch (NumberFormatException ex) { throw new IllegalArgumentException("'" + champNom + "' doit être un entier."); }
    }

    private LocalDate lireDate(String champNom) {
        String txt = datePeremption.getText().trim();
        if (txt.isEmpty()) throw new IllegalArgumentException("La date de péremption est vide.");
        try { return LocalDate.parse(txt, DATE_FMT); }
        catch (DateTimeParseException ex) { throw new IllegalArgumentException("Format de date invalide. Utilisez yyyy-MM-dd."); }
    }

    /** Convertit un Medicament en ligne de tableau. */
    private Object[] toRow(Medicament m) {
        return new Object[]{
            m.getId(), m.getNomCommercial(), m.getComposition(), m.getForme(),
            m.getDosage(), m.getPrix(), m.getQuantiteStock(),
            m.getDatePeremption() != null ? m.getDatePeremption().format(DATE_FMT) : "",
            m.getSeuilAlerte()
        };
    }

    // ── Point d'entrée autonome (test) ───────────────────────────────
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Gestion des Médicaments");
            frame.setContentPane(new MedicamentPanel());
            frame.setSize(1100, 600);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
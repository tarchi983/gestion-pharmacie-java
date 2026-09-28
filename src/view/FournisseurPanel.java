//package view;
//
//import controller.FournisseurController;
//import java.awt.BorderLayout;
//import java.awt.FlowLayout;
//import java.awt.GridLayout;
//import java.util.List;
//import javax.swing.BorderFactory;
//import javax.swing.JButton;
//import javax.swing.JPanel;
//import javax.swing.JScrollPane;
//import javax.swing.JTable;
//import javax.swing.JTextField;
//import javax.swing.table.DefaultTableModel;
//import model.Fournisseur;
//
//public class FournisseurPanel extends JPanel {
//
//    private FournisseurController controller = new FournisseurController();
//
//    private JTextField id;
//    private JTextField nomSociete;
//    private JTextField contact;
//    private JTextField adresse;
//    private DefaultTableModel model;
//    private JTable table;
//
//    public FournisseurPanel() {
//        // Etape 1: preparer l'organisation du panel.
//        setLayout(new BorderLayout(10, 10));
//        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
//
//        // Etape 2: creer le formulaire.
//        JPanel formulaire = new JPanel(new GridLayout(0, 2, 5, 5));
//        id = ViewUtil.ajouterChamp(formulaire, "Id");
//        nomSociete = ViewUtil.ajouterChamp(formulaire, "Nom societe");
//        contact = ViewUtil.ajouterChamp(formulaire, "Contact");
//        adresse = ViewUtil.ajouterChamp(formulaire, "Adresse");
//
//        // Etape 3: creer le tableau.
//        model = ViewUtil.creerModel(new String[] {"Id", "Nom societe", "Contact", "Adresse"});
//        table = new JTable(model);
//        ViewUtil.ajouterSelectionTable(table, new Runnable() {
//            public void run() {
//                remplirFormulaireDepuisTable();
//            }
//        });
//
//        // Etape 4: creer les boutons.
//        JButton boutonAjouter = new JButton("Ajouter");
//        JButton boutonActualiser = new JButton("Actualiser");
//        JPanel boutons = new JPanel(new FlowLayout(FlowLayout.LEFT));
//        boutons.add(boutonAjouter);
//        boutons.add(boutonActualiser);
//
//        // Etape 5: relier les boutons aux methodes.
//        ViewUtil.ajouterAction(boutonAjouter, new Runnable() {
//            public void run() {
//                ajouterFournisseur();
//            }
//        });
//        ViewUtil.ajouterAction(boutonActualiser, new Runnable() {
//            public void run() {
//                chargerFournisseurs();
//            }
//        });
//
//        add(formulaire, BorderLayout.NORTH);
//        add(new JScrollPane(table), BorderLayout.CENTER);
//        add(boutons, BorderLayout.SOUTH);
//        chargerFournisseurs();
//    }
//
//    private void chargerFournisseurs() {
//        try {
//            // Etape 1: vider le tableau.
//            model.setRowCount(0);
//
//            // Etape 2: lire les fournisseurs et les ajouter au tableau.
//            List<Fournisseur> fournisseurs = controller.listerFournisseurs();
//            for (Fournisseur fournisseur : fournisseurs) {
//                model.addRow(new Object[] {
//                        fournisseur.getId(),
//                        fournisseur.getNomSociete(),
//                        fournisseur.getContact(),
//                        fournisseur.getAdresse()
//                });
//            }
//        } catch (Exception e) {
//            ViewUtil.afficherErreur(this, "Erreur chargement fournisseurs: " + e.getMessage());
//        }
//    }
//
//    private void ajouterFournisseur() {
//        try {
//            // Etape 1: ajouter le fournisseur.
//            boolean ok = controller.ajouterFournisseur(nomSociete.getText(), contact.getText(), adresse.getText());
//
//            // Etape 2: actualiser l'affichage.
//            ViewUtil.afficherResultat(this, ok, "Fournisseur ajoute.");
//            chargerFournisseurs();
//            viderChamps();
//        } catch (Exception e) {
//            ViewUtil.afficherErreur(this, "Erreur ajout fournisseur: " + e.getMessage());
//        }
//    }
//
//    private void remplirFormulaireDepuisTable() {
//        // Etape 1: lire la ligne selectionnee.
//        int ligne = table.getSelectedRow();
//        if (ligne < 0) {
//            return;
//        }
//
//        // Etape 2: copier la ligne dans le formulaire.
//        id.setText(ViewUtil.texte(model.getValueAt(ligne, 0)));
//        nomSociete.setText(ViewUtil.texte(model.getValueAt(ligne, 1)));
//        contact.setText(ViewUtil.texte(model.getValueAt(ligne, 2)));
//        adresse.setText(ViewUtil.texte(model.getValueAt(ligne, 3)));
//    }
//
//    private void viderChamps() {
//        id.setText("");
//        nomSociete.setText("");
//        contact.setText("");
//        adresse.setText("");
//    }
//}
package view;

import controller.FournisseurController;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import model.Fournisseur;

public class FournisseurPanel extends JPanel implements ActionListener, ListSelectionListener {

    private FournisseurController controller = new FournisseurController();

    JTextField id, nomSociete, contact, adresse;
    JTextField searchField;
    JButton btnAjouter, btnModifier, btnSupprimer, btnVider, btnActualiser, btnRechercher;
    JPanel sidebar, content, boutons;
    DefaultTableModel model;
    JTable table;

    public FournisseurPanel() {
        setLayout(new BorderLayout());

        // ── Sidebar gauche ────────────────────────────────────────────
        sidebar = new JPanel(new BorderLayout());
        sidebar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        sidebar.setPreferredSize(new Dimension(220, 0));

        // Formulaire
        JPanel form = new JPanel(new GridLayout(0, 1, 0, 5));

        form.add(new JLabel("Fiche fournisseur"));
        form.add(new JLabel("ID"));           id        = new JTextField(); form.add(id);
        id.setEditable(false); // L'ID est en lecture seule (généré par la BDD)
        form.add(new JLabel("Nom société"));  nomSociete = new JTextField(); form.add(nomSociete);
        form.add(new JLabel("Contact"));      contact    = new JTextField(); form.add(contact);
        form.add(new JLabel("Adresse"));      adresse    = new JTextField(); form.add(adresse);

        // Boutons CRUD
        boutons = new JPanel(new GridLayout(2, 2, 5, 5));
        btnAjouter   = new JButton("Ajouter");   boutons.add(btnAjouter);
        btnModifier  = new JButton("Modifier");  boutons.add(btnModifier);
        btnSupprimer = new JButton("Supprimer"); boutons.add(btnSupprimer);
        btnVider     = new JButton("Vider");     boutons.add(btnVider);

        sidebar.add(form,    BorderLayout.NORTH);
        sidebar.add(boutons, BorderLayout.SOUTH);

        // ── Contenu droit ─────────────────────────────────────────────
        content = new JPanel(new BorderLayout(5, 5));
        content.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // En-tête : titre + barre de recherche + actualiser
        JPanel header = new JPanel(new BorderLayout(5, 0));
        header.add(new JLabel("Liste des fournisseurs"), BorderLayout.WEST);

        JPanel headerBtns = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        searchField    = new JTextField(12);
        btnRechercher  = new JButton("Rechercher");
        btnActualiser  = new JButton("Actualiser");
        headerBtns.add(searchField);
        headerBtns.add(btnRechercher);
        headerBtns.add(btnActualiser);
        header.add(headerBtns, BorderLayout.EAST);

        // Table
        model = new DefaultTableModel(
            new String[]{"ID", "Nom société", "Contact", "Adresse"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        content.add(header,               BorderLayout.NORTH);
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

        chargerFournisseurs();
    }

    // ── ActionListener ───────────────────────────────────────────────
    @Override
    public void actionPerformed(ActionEvent e) {
        if      (e.getSource() == btnAjouter)    ajouterFournisseur();
        else if (e.getSource() == btnModifier)   modifierFournisseur();
        else if (e.getSource() == btnSupprimer)  supprimerFournisseur();
        else if (e.getSource() == btnVider)      viderChamps();
        else if (e.getSource() == btnRechercher) rechercherFournisseur();
        else                                     chargerFournisseurs(); // btnActualiser
    }

    // ── ListSelectionListener ────────────────────────────────────────
    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            remplirFormulaireDepuisTable();
        }
    }

    // ── Fonctions métier ─────────────────────────────────────────────

    private void chargerFournisseurs() {
        try {
            model.setRowCount(0);
            for (Fournisseur f : controller.listerFournisseurs())
                model.addRow(new Object[]{ f.getId(), f.getNomSociete(), f.getContact(), f.getAdresse() });
        } catch (Exception e) {
            ViewUtil.afficherErreur(this, "Erreur chargement : " + e.getMessage());
        }
    }

    private void ajouterFournisseur() {
        try {
            controller.ajouterFournisseur(nomSociete.getText(), contact.getText(), adresse.getText());
            ViewUtil.afficherResultat(this, true, "Fournisseur ajouté.");
            chargerFournisseurs();
            viderChamps();
        } catch (Exception e) {
            ViewUtil.afficherErreur(this, "Erreur ajout : " + e.getMessage());
        }
    }

    private void modifierFournisseur() {
        try {
            boolean ok = controller.modifierFournisseur(
                ViewUtil.lireInt(id, "id"),
                nomSociete.getText(),
                contact.getText(),
                adresse.getText()
            );
            ViewUtil.afficherResultat(this, ok, "Fournisseur modifié.");
            chargerFournisseurs();
        } catch (Exception e) {
            ViewUtil.afficherErreur(this, "Erreur modification : " + e.getMessage());
        }
    }

    private void supprimerFournisseur() {
        try {
            boolean ok = controller.supprimerFournisseur(ViewUtil.lireInt(id, "id"));
            ViewUtil.afficherResultat(this, ok, "Fournisseur supprimé.");
            chargerFournisseurs();
            viderChamps();
        } catch (Exception e) {
            ViewUtil.afficherErreur(this, "Erreur suppression : " + e.getMessage());
        }
    }

    private void rechercherFournisseur() {
        try {
            String motCle = searchField.getText().trim();
            List<Fournisseur> resultats = motCle.isEmpty()
                ? controller.listerFournisseurs()
                : controller.chercherFournisseursParNom(motCle);

            model.setRowCount(0);
            for (Fournisseur f : resultats)
                model.addRow(new Object[]{ f.getId(), f.getNomSociete(), f.getContact(), f.getAdresse() });

            if (resultats.isEmpty())
                ViewUtil.afficherErreur(this, "Aucun fournisseur trouvé pour : " + motCle);
        } catch (Exception e) {
            ViewUtil.afficherErreur(this, "Erreur recherche : " + e.getMessage());
        }
    }

    private void remplirFormulaireDepuisTable() {
        int ligne = table.getSelectedRow();
        if (ligne < 0) return;
        id.setText(ViewUtil.texte(model.getValueAt(ligne, 0)));
        nomSociete.setText(ViewUtil.texte(model.getValueAt(ligne, 1)));
        contact.setText(ViewUtil.texte(model.getValueAt(ligne, 2)));
        adresse.setText(ViewUtil.texte(model.getValueAt(ligne, 3)));
    }

    private void viderChamps() {
        id.setText("");
        nomSociete.setText("");
        contact.setText("");
        adresse.setText("");
        searchField.setText("");
    }

    // ── Point d'entrée autonome (test) ───────────────────────────────
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Gestion des Fournisseurs");
            frame.setContentPane(new FournisseurPanel());
            frame.setSize(900, 550);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
package view;

import controller.PharmacieController;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import model.Pharmacie;

public class PharmaciePanel extends JPanel implements ActionListener {

    private PharmacieController controller = new PharmacieController();

    private JTextField nom, adresse, telephone, email, responsable;
    private JButton boutonCharger, boutonEnregistrer;

    public PharmaciePanel() {
        // Configuration de l'organisation du panel principal
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Création du formulaire (Haut)
        JPanel formulaire = new JPanel(new GridLayout(0, 2, 5, 5));
        
        formulaire.add(new JLabel("Nom"));          nom = new JTextField();         formulaire.add(nom);
        formulaire.add(new JLabel("Adresse"));      adresse = new JTextField();     formulaire.add(adresse);
        formulaire.add(new JLabel("Téléphone"));    telephone = new JTextField();   formulaire.add(telephone);
        formulaire.add(new JLabel("Email"));        email = new JTextField();       formulaire.add(email);
        formulaire.add(new JLabel("Responsable"));  responsable = new JTextField(); formulaire.add(responsable);

        // Création des boutons (Bas)
        boutonCharger = new JButton("Charger");
        boutonEnregistrer = new JButton("Enregistrer");
        
        JPanel boutons = new JPanel(new FlowLayout(FlowLayout.LEFT));
        boutons.add(boutonCharger);
        boutons.add(boutonEnregistrer);

        // Ajout des composants au panel principal
        add(formulaire, BorderLayout.NORTH);
        add(boutons, BorderLayout.CENTER);

        // Liaison des écouteurs d'événements (Actions_Button)
        boutonCharger.addActionListener(this);
        boutonEnregistrer.addActionListener(this);

        // Chargement initial des données
        chargerPharmacie();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == boutonCharger) {
            chargerPharmacie();
        } else if (e.getSource() == boutonEnregistrer) {
            enregistrerPharmacie();
        }
    }

    // Fonctions de gestion des données
    private void chargerPharmacie() {
        try {
            // Demander les informations au controller
            Pharmacie pharmacie = controller.consulterPharmacie();

            // Afficher les informations trouvées
            if (pharmacie != null) {
                nom.setText(pharmacie.getNom());
                adresse.setText(pharmacie.getAdresse());
                telephone.setText(pharmacie.getTelephone());
                email.setText(pharmacie.getEmail());
                responsable.setText(pharmacie.getResponsable());
            }
        } catch (Exception e) {
            ViewUtil.afficherErreur(this, "Erreur chargement pharmacie: " + e.getMessage());
        }
    }

    private void enregistrerPharmacie() {
        try {
            // Envoyer les valeurs au controller
            boolean ok = controller.enregistrerPharmacie(
                    nom.getText(),
                    adresse.getText(),
                    telephone.getText(),
                    email.getText(),
                    responsable.getText()
            );

            // Afficher le résultat
            ViewUtil.afficherResultat(this, ok, "Pharmacie enregistrée.");
        } catch (Exception e) {
            ViewUtil.afficherErreur(this, "Erreur enregistrement pharmacie: " + e.getMessage());
        }
    }
}


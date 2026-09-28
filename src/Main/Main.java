package Main;

import javax.swing.SwingUtilities;
import view.GestionPharmacieView;

public class Main {

    public static void main(String[] args) {
        // Etape 1: lancer Swing correctement.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // Etape 2: creer et afficher la fenetre principale.
                GestionPharmacieView fenetre = new GestionPharmacieView();
                fenetre.setVisible(true);
            }
        });
    }
}

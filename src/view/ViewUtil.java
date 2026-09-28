package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

class ViewUtil {

    

    static DefaultTableModel creerModel(String[] colonnes) {
        // Crée un modèle de tableau non éditable avec les colonnes spécifiées
        return new DefaultTableModel(colonnes, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }

    
    
    static int lireInt(JTextField champ, String nomChamp) {
        // Etape simple: convertir un texte en entier.
        try {
            return Integer.parseInt(champ.getText().trim());
        } catch (Exception e) {
            throw new IllegalArgumentException("Le champ " + nomChamp + " doit etre un entier.");
        }
    }

    static double lireDouble(JTextField champ, String nomChamp) {
        // Etape simple: convertir un texte en nombre decimal.
        try {
            return Double.parseDouble(champ.getText().trim());
        } catch (Exception e) {
            throw new IllegalArgumentException("Le champ " + nomChamp + " doit etre un nombre.");
        }
    }

    static LocalDate lireDate(JTextField champ, String nomChamp) {
        // Etape simple: convertir un texte en date au format yyyy-mm-dd.
        try {
            return LocalDate.parse(champ.getText().trim());
        } catch (Exception e) {
            throw new IllegalArgumentException("Le champ " + nomChamp + " doit etre au format yyyy-mm-dd.");
        }
    }

    static String texte(Object valeur) {
        // Etape simple: eviter les erreurs quand une valeur de la base est NULL.
        if (valeur == null) {
            return "";
        }
        return valeur.toString();
    }

    static void afficherResultat(JPanel panel, boolean ok, String message) {
        // Etape simple: afficher un message selon le resultat.
        if (ok) {
            JOptionPane.showMessageDialog(panel, message);
        } else {
            afficherErreur(panel, "Operation refusee. Verifiez les champs.");
        }
    }

    static void afficherErreur(JPanel panel, String message) {
        // Etape simple: afficher une erreur dans une boite de dialogue.
        JOptionPane.showMessageDialog(panel, message, "Erreur", JOptionPane.ERROR_MESSAGE);
    }
}






/*had lclass hia b7al wahed l boite li fiiha les mothods wajdin
  li gha y3ayto 3lihom les class lokhrin dyal lviw , 3iwad manb9aw f kola panel 
  nktbo nafs l code bzzf d lmarat had lclass kakhtaser 3lina tri9*/

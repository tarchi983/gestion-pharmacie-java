package view;

import controller.OrdonnanceController;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import model.Ordonnance;

public class OrdonnancePanel extends JPanel implements ActionListener {

    private OrdonnanceController controller = new OrdonnanceController(); 
    private DefaultTableModel model;
    JButton boutonActualiser = null;

    public OrdonnancePanel() {
        // Etape 1: preparer l'organisation du panel.
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
  
        model = new DefaultTableModel(
                new String[]{"ID", "Id client", "Medecin", "Date", "Notes"}, 0) {
                public boolean isCellEditable(int r, int c) { return false; }
            };
        JTable table = new JTable(model);
        
        boutonActualiser = new JButton("Actualiser");
        JPanel boutons = new JPanel(new FlowLayout(FlowLayout.LEFT));
        boutons.add(boutonActualiser);

        add(new JScrollPane(table), BorderLayout.CENTER);
        add(boutons, BorderLayout.SOUTH);
        chargerOrdonnances();
    }
    @Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == boutonActualiser)
			chargerOrdonnances();
		
	}

    private void chargerOrdonnances() {
        try {
            // Etape 1: vider le tableau.
            model.setRowCount(0);

            // Etape 2: lire les ordonnances et les ajouter au tableau.
            List<Ordonnance> ordonnances = controller.listerOrdonnances();
            for (Ordonnance ordonnance : ordonnances) {
                model.addRow(new Object[] {
                        ordonnance.getId(),
                        ordonnance.getIdClient(),
                        ordonnance.getMedecin(),
                        ordonnance.getDateOrdonnance(),
                        ordonnance.getNotes()
                });
            }
        } catch (Exception e) {
            ViewUtil.afficherErreur(this, "Erreur chargement ordonnances: " + e.getMessage());
        }
    }

	
}
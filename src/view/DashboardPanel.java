package view;

import controller.DashboardController;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import model.Medicament;

public class DashboardPanel extends JPanel implements ActionListener {

    private DashboardController controller = new DashboardController();
    
    private JTextField txtDateDebut, txtDateFin, txtResultatPeriode;
    private JButton btnCalculerPeriode, btnActualiser;
    
    private JTable tableProduit, tableClient, tableDemandes, tableStock;
    private DefaultTableModel modelProduit, modelClient, modelDemandes, modelStock;

    public DashboardPanel() {
        setLayout(new BorderLayout());

        // Header - Ventes par période
        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        header.setBorder(BorderFactory.createTitledBorder("Analyse par période (yyyy-mm-dd)"));
        
        header.add(new JLabel("Début:"));
        txtDateDebut = new JTextField(10);
        header.add(txtDateDebut);
        
        header.add(new JLabel("Fin:"));
        txtDateFin = new JTextField(10);
        header.add(txtDateFin);
        
        btnCalculerPeriode = new JButton("Calculer CA");
        header.add(btnCalculerPeriode);
        
        header.add(new JLabel("Résultat:"));
        txtResultatPeriode = new JTextField(10);
        txtResultatPeriode.setEditable(false);
        header.add(txtResultatPeriode);
        
        btnActualiser = new JButton("Actualiser tout");
        header.add(btnActualiser);

        add(header, BorderLayout.NORTH);

        // Center - Onglets pour les rapports
        JTabbedPane tabbedPane = new JTabbedPane();
        
        // 1. Ventes par produit
        modelProduit = ViewUtil.creerModel(new String[]{"Produit", "Quantité Vendue"});
        tableProduit = new JTable(modelProduit);
        tabbedPane.addTab("Ventes par Produit", new JScrollPane(tableProduit));
        
        // 2. Ventes par client
        modelClient = ViewUtil.creerModel(new String[]{"Client", "Total Acheté (Dhs)"});
        tableClient = new JTable(modelClient);
        tabbedPane.addTab("Ventes par Client", new JScrollPane(tableClient));
        
        // 3. Produits les plus demandés
        modelDemandes = ViewUtil.creerModel(new String[]{"Produit", "Quantité"});
        tableDemandes = new JTable(modelDemandes);
        tabbedPane.addTab("Produits les plus demandés", new JScrollPane(tableDemandes));
        
        
        
        add(tabbedPane, BorderLayout.CENTER);

        // Events
        btnCalculerPeriode.addActionListener(this);
        btnActualiser.addActionListener(this);
        
        chargerDonnees();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnCalculerPeriode) {
            calculerPeriode();
        } else if (e.getSource() == btnActualiser) {
            chargerDonnees();
        }
    }
    
    private void calculerPeriode() {
        try {
            LocalDate debut = ViewUtil.lireDate(txtDateDebut, "Début");
            LocalDate fin = ViewUtil.lireDate(txtDateFin, "Fin");
            double total = controller.getTotalVentesPeriode(debut, fin);
            txtResultatPeriode.setText(String.valueOf(total));
        } catch (Exception ex) {
            ViewUtil.afficherErreur(this, "Erreur période: " + ex.getMessage());
        }
    }
    
    private void chargerDonnees() {
        try {
            // Ventes par Produit
            modelProduit.setRowCount(0);
            Map<String, Integer> vp = controller.getVentesParProduit();
            for (Map.Entry<String, Integer> entry : vp.entrySet()) {
                modelProduit.addRow(new Object[]{entry.getKey(), entry.getValue()});
            }
            
            // Ventes par Client
            modelClient.setRowCount(0);
            Map<String, Double> vc = controller.getVentesParClient();
            for (Map.Entry<String, Double> entry : vc.entrySet()) {
                modelClient.addRow(new Object[]{entry.getKey(), entry.getValue()});
            }
            
            // Produits les plus demandés
            modelDemandes.setRowCount(0);
            List<Map.Entry<String, Integer>> top = controller.getProduitsLesPlusDemandes();
            for (Map.Entry<String, Integer> entry : top) {
                modelDemandes.addRow(new Object[]{entry.getKey(), entry.getValue()});
            }
            
        }
            
          catch (Exception ex) {
            ViewUtil.afficherErreur(this, "Erreur chargement des données: " + ex.getMessage());
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Dashboard Analytique");
            frame.setContentPane(new DashboardPanel());
            frame.setSize(900, 600);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
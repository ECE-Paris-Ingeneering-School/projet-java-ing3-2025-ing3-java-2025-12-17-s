package vue;

import dao.RemiseDAOImpl;
import modele.Remise;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class FenetreRemisesAdmin extends JFrame {

    private JTable table;
    private DefaultTableModel model;
    private RemiseDAOImpl remiseDAO;

    public FenetreRemisesAdmin() {
        setTitle("Gestion des remises");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        remiseDAO = new RemiseDAOImpl();

        model = new DefaultTableModel(new String[]{"ID", "ID Article", "Code", "Pourcentage", "Début", "Fin"}, 0);
        table = new JTable(model);
        loadRemises();

        JButton btnAjouter = new JButton("Ajouter");
        JButton btnModifier = new JButton("Modifier");
        JButton btnSupprimer = new JButton("Supprimer");

        btnAjouter.addActionListener(e -> ajouterRemise());
        btnModifier.addActionListener(e -> modifierRemise());
        btnSupprimer.addActionListener(e -> supprimerRemise());

        JPanel btnPanel = new JPanel();
        btnPanel.add(btnAjouter);
        btnPanel.add(btnModifier);
        btnPanel.add(btnSupprimer);

        add(new JScrollPane(table), BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void loadRemises() {
        model.setRowCount(0);
        List<Remise> remises = remiseDAO.getToutesLesRemises();
        for (Remise r : remises) {
            model.addRow(new Object[]{
                    r.getId(), r.getIdArticle(), r.getCode(),
                    r.getPourcentage(), r.getDateDebut(), r.getDateFin()
            });
        }
    }

    private void ajouterRemise() {
        int idArticle = Integer.parseInt(JOptionPane.showInputDialog(this, "ID de l'article :"));
        String code = JOptionPane.showInputDialog(this, "Code :");
        int pourcentage = Integer.parseInt(JOptionPane.showInputDialog(this, "Pourcentage :"));
        LocalDate debut = LocalDate.parse(JOptionPane.showInputDialog(this, "Date début (AAAA-MM-JJ) :"));
        LocalDate fin = LocalDate.parse(JOptionPane.showInputDialog(this, "Date fin (AAAA-MM-JJ) :"));

        Remise remise = new Remise(0, idArticle, code, pourcentage, debut, fin);
        if (remiseDAO.ajouterRemise(remise)) {
            JOptionPane.showMessageDialog(this, "Remise ajoutée !");
            loadRemises();
        } else {
            JOptionPane.showMessageDialog(this, "Erreur ajout remise !");
        }
    }

    private void modifierRemise() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Sélectionnez une remise.");
            return;
        }

        int id = (int) model.getValueAt(row, 0);
        int idArticle = Integer.parseInt(JOptionPane.showInputDialog(this, "ID Article :", model.getValueAt(row, 1)));
        String code = JOptionPane.showInputDialog(this, "Code :", model.getValueAt(row, 2));
        int pourcentage = Integer.parseInt(JOptionPane.showInputDialog(this, "Pourcentage :", model.getValueAt(row, 3)));
        LocalDate debut = LocalDate.parse(JOptionPane.showInputDialog(this, "Date début (AAAA-MM-JJ) :", model.getValueAt(row, 4)));
        LocalDate fin = LocalDate.parse(JOptionPane.showInputDialog(this, "Date fin (AAAA-MM-JJ) :", model.getValueAt(row, 5)));

        Remise remise = new Remise(id, idArticle, code, pourcentage, debut, fin);
        if (remiseDAO.modifierRemise(remise)) {
            JOptionPane.showMessageDialog(this, "Remise modifiée !");
            loadRemises();
        } else {
            JOptionPane.showMessageDialog(this, "Erreur modification !");
        }
    }

    private void supprimerRemise() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Sélectionnez une remise.");
            return;
        }

        int id = (int) model.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Supprimer cette remise ?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (remiseDAO.supprimerRemise(id)) {
                JOptionPane.showMessageDialog(this, "Remise supprimée !");
                loadRemises();
            } else {
                JOptionPane.showMessageDialog(this, "Erreur suppression !");
            }
        }
    }
}

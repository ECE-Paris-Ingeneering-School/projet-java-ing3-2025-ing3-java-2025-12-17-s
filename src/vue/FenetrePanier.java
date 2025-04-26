package vue;

import dao.PanierDAO;
import dao.PanierDAOImpl;
import modele.Article;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Fenêtre affichant le panier du client
 */
public class FenetrePanier extends JFrame {

    private JTable table;
    private JLabel lblTotal;
    private int idClient;
    private List<Article> articles;

    public FenetrePanier(int idClient) {
        this.idClient = idClient;

        setTitle("Mon panier");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        lblTotal = new JLabel("Total : 0.00 €");
        lblTotal.setFont(new Font("Arial", Font.BOLD, 16));
        bottomPanel.add(lblTotal, BorderLayout.WEST);

        JPanel btnPanel = new JPanel();
        JButton btnSupprimer = new JButton("🗑 Supprimer l'article");
        JButton btnCommander = new JButton("Valider la commande");

        btnPanel.add(btnSupprimer);
        btnPanel.add(btnCommander);
        bottomPanel.add(btnPanel, BorderLayout.EAST);

        add(bottomPanel, BorderLayout.SOUTH);

        chargerPanier();

        btnSupprimer.addActionListener(e -> supprimerArticleSelectionne());

        btnCommander.addActionListener(e -> {
            if (articles.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Votre panier est vide.");
                return;
            }

            List<String> lignes = new ArrayList<>();
            double total = 0;

            for (Article a : articles) {
                double prix = a.getPrixUnitaire();
                String message = "";

                // 🔥 Appliquer prix vrac si nécessaire
                if (a.getQuantite() >= a.getQuantiteVrac()) {
                    prix = a.getPrixVrac();
                    message = " (prix vrac appliqué)";
                }

                double sousTotal = prix * a.getQuantite();
                total += sousTotal;

                String ligne = "Article " + a.getId() + " : " + String.format("%.2f€", prix) + message + " - " + a.getNom() + " x" + a.getQuantite();
                lignes.add(ligne);
            }

            // ✅ Lancer validation commande
            new ValidationCommandeView(lignes, total, idClient);
            dispose();
        });

        setVisible(true);
    }

    private void chargerPanier() {
        PanierDAO panierDAO = new PanierDAOImpl();
        articles = panierDAO.getArticlesPanier(idClient);

        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"Nom", "Marque", "Prix", "Quantité", "Sous-total"}, 0
        );

        double total = 0;
        for (Article a : articles) {
            double prix = a.getPrixUnitaire();
            String message = "";

            // 🔥 Appliquer prix vrac si nécessaire
            if (a.getQuantite() >= a.getQuantiteVrac()) {
                prix = a.getPrixVrac();
                message = " (vrac)";
            }

            double sousTotal = prix * a.getQuantite();
            total += sousTotal;

            model.addRow(new Object[]{
                    a.getNom(),
                    a.getMarque(),
                    String.format("%.2f €", prix) + message,
                    a.getQuantite(),
                    String.format("%.2f €", sousTotal)
            });
        }

        table.setModel(model);
        lblTotal.setText("Total : " + String.format("%.2f €", total));
    }

    private void supprimerArticleSelectionne() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            Article article = articles.get(row);
            PanierDAO panierDAO = new PanierDAOImpl();
            if (panierDAO.supprimerDuPanier(idClient, article.getId())) {
                JOptionPane.showMessageDialog(this, "Article supprimé.");
                chargerPanier();
            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors de la suppression.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Sélectionnez un article à supprimer.");
        }
    }
}

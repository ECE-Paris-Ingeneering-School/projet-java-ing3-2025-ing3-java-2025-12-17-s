package vue;

import dao.ArticleDAOImpl;
import modele.Article;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Fenêtre d'administration permettant de gérer les articles du catalogue :
 * ajout, modification et suppression.
 */
public class FenetreArticlesAdmin extends JFrame {

    private JTable table;
    private DefaultTableModel model;
    private ArticleDAOImpl articleDAO;

    /**
     * Constructeur : initialise la fenêtre de gestion des articles.
     */
    public FenetreArticlesAdmin() {
        setTitle("Gestion des articles");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        articleDAO = new ArticleDAOImpl();

        // Table pour afficher les articles
        model = new DefaultTableModel(new String[]{"ID", "Nom", "Marque", "Prix unitaire", "Prix vrac", "Quantité vrac", "Stock"}, 0);
        table = new JTable(model);
        loadArticles();

        // Boutons d'action
        JButton btnAjouter = new JButton("Ajouter");
        JButton btnModifier = new JButton("Modifier");
        JButton btnSupprimer = new JButton("Supprimer");

        // Actions des boutons
        btnAjouter.addActionListener(e -> ajouterArticle());
        btnModifier.addActionListener(e -> modifierArticle());
        btnSupprimer.addActionListener(e -> supprimerArticle());

        JPanel btnPanel = new JPanel();
        btnPanel.add(btnAjouter);
        btnPanel.add(btnModifier);
        btnPanel.add(btnSupprimer);

        // Placement des composants
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    /**
     * Charge les articles depuis la base de données et les affiche dans la table.
     */
    private void loadArticles() {
        model.setRowCount(0); // Nettoyer l'ancien contenu
        List<Article> articles = articleDAO.getTousLesArticles();
        for (Article a : articles) {
            model.addRow(new Object[]{
                    a.getId(), a.getNom(), a.getMarque(), a.getPrixUnitaire(),
                    a.getPrixVrac(), a.getQuantiteVrac(), a.getStock()
            });
        }
    }

    /**
     * Ajoute un nouvel article en base de données.
     */
    private void ajouterArticle() {
        try {
            String nom = JOptionPane.showInputDialog(this, "Nom :");
            String marque = JOptionPane.showInputDialog(this, "Marque :");
            double prixUnitaire = Double.parseDouble(JOptionPane.showInputDialog(this, "Prix unitaire :"));
            double prixVrac = Double.parseDouble(JOptionPane.showInputDialog(this, "Prix vrac :"));
            int quantiteVrac = Integer.parseInt(JOptionPane.showInputDialog(this, "Quantité vrac :"));
            int stock = Integer.parseInt(JOptionPane.showInputDialog(this, "Stock :"));

            Article article = new Article(nom, marque, prixUnitaire, prixVrac, quantiteVrac, stock);
            if (articleDAO.ajouterArticle(article)) {
                JOptionPane.showMessageDialog(this, "Article ajouté !");
                loadArticles();
            } else {
                JOptionPane.showMessageDialog(this, "Erreur ajout article !");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur saisie : " + e.getMessage());
        }
    }

    /**
     * Modifie un article sélectionné dans la table.
     */
    private void modifierArticle() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Sélectionnez un article.");
            return;
        }

        try {
            int id = (int) model.getValueAt(row, 0);
            String nom = JOptionPane.showInputDialog(this, "Nom :", model.getValueAt(row, 1));
            String marque = JOptionPane.showInputDialog(this, "Marque :", model.getValueAt(row, 2));
            double prixUnitaire = Double.parseDouble(JOptionPane.showInputDialog(this, "Prix unitaire :", model.getValueAt(row, 3)));
            double prixVrac = Double.parseDouble(JOptionPane.showInputDialog(this, "Prix vrac :", model.getValueAt(row, 4)));
            int quantiteVrac = Integer.parseInt(JOptionPane.showInputDialog(this, "Quantité vrac :", model.getValueAt(row, 5)));
            int stock = Integer.parseInt(JOptionPane.showInputDialog(this, "Stock :", model.getValueAt(row, 6)));

            Article article = new Article(id, nom, marque, prixUnitaire, prixVrac, quantiteVrac, stock);
            if (articleDAO.modifierArticle(article)) {
                JOptionPane.showMessageDialog(this, "Article modifié !");
                loadArticles();
            } else {
                JOptionPane.showMessageDialog(this, "Erreur modification article !");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur saisie : " + e.getMessage());
        }
    }

    /**
     * Supprime un article sélectionné après confirmation.
     */
    private void supprimerArticle() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Sélectionnez un article.");
            return;
        }

        int id = (int) model.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Supprimer l'article ?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (articleDAO.supprimerArticle(id)) {
                JOptionPane.showMessageDialog(this, "Article supprimé !");
                loadArticles();
            } else {
                JOptionPane.showMessageDialog(this, "Erreur suppression article !");
            }
        }
    }
}

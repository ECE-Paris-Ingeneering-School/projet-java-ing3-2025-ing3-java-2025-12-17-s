package vue;

import dao.ArticleDAOImpl;
import modele.Article;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Vue du catalogue affichant tous les articles disponibles
 */
public class CatalogueView {

    private JFrame frame;
    private DefaultListModel<String> articleListModel;

    public CatalogueView() {
        frame = new JFrame("Catalogue d'articles");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        // Titre
        JLabel titleLabel = new JLabel("Catalogue", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        frame.add(titleLabel, BorderLayout.NORTH);

        // Liste des articles
        articleListModel = new DefaultListModel<>();
        JList<String> articleList = new JList<>(articleListModel);
        articleList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane listScroller = new JScrollPane(articleList);
        frame.add(listScroller, BorderLayout.CENTER);

        // Bouton ajouter au panier
        JButton addToCartButton = new JButton("Ajouter au panier");
        frame.add(addToCartButton, BorderLayout.SOUTH);

        // Charger les articles depuis la BDD
        loadArticles();

        // Action pour le bouton "Ajouter au panier"
        addToCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedArticle = articleList.getSelectedValue();
                if (selectedArticle != null) {
                    JOptionPane.showMessageDialog(frame, selectedArticle + " ajouté au panier.");
                    // À intégrer avec le panier une fois qu'il est développé
                } else {
                    JOptionPane.showMessageDialog(frame, "Veuillez sélectionner un article !");
                }
            }
        });

        frame.setVisible(true);
    }

    private void loadArticles() {
        ArticleDAOImpl articleDAO = new ArticleDAOImpl();
        List<Article> articles = articleDAO.getTousLesArticles();

        for (Article article : articles) {
            articleListModel.addElement(article.getNom() + " - " + article.getPrixUnitaire() + "€");
        }
    }

    public static void main(String[] args) {
        new CatalogueView();
    }
}

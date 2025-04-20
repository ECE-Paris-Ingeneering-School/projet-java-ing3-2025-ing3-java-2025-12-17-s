package vue;

import dao.ArticleDAO;
import dao.ArticleDAOImpl;
import dao.PanierDAO;
import dao.PanierDAOImpl;
import modele.Article;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CatalogueView {

    private JFrame frame;
    private DefaultListModel<String> articleListModel;
    private JList<String> articleList;
    private List<Article> articles;
    private int idClient;

    public CatalogueView(int idClient) {
        this.idClient = idClient;

        frame = new JFrame("Catalogue d'articles");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Catalogue", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        frame.add(titleLabel, BorderLayout.NORTH);

        articleListModel = new DefaultListModel<>();
        articleList = new JList<>(articleListModel);
        articleList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane listScroller = new JScrollPane(articleList);
        frame.add(listScroller, BorderLayout.CENTER);

        JButton addToCartButton = new JButton("Ajouter au panier");
        frame.add(addToCartButton, BorderLayout.SOUTH);

        loadArticles();

        addToCartButton.addActionListener(e -> {
            int selectedIndex = articleList.getSelectedIndex();
            if (selectedIndex != -1) {
                Article article = articles.get(selectedIndex);

                String input = JOptionPane.showInputDialog(frame,
                        "Quantité à ajouter pour " + article.getNom() + " (Stock dispo : " + article.getStock() + ")", "1");

                if (input != null) {
                    try {
                        int quantite = Integer.parseInt(input);
                        if (quantite <= 0 || quantite > article.getStock()) {
                            JOptionPane.showMessageDialog(frame, "Quantité invalide.");
                            return;
                        }

                        // Mise à jour du panier
                        PanierDAO panierDAO = new PanierDAOImpl();
                        boolean success = panierDAO.ajouterAuPanier(idClient, article.getId(), quantite);

                        // Mise à jour du stock
                        if (success) {
                            ArticleDAO articleDAO = new ArticleDAOImpl();
                            article.setStock(article.getStock() - quantite);
                            articleDAO.modifierArticle(article);

                            JOptionPane.showMessageDialog(frame, "Article ajouté !");
                            loadArticles(); // mise à jour visuelle
                        } else {
                            JOptionPane.showMessageDialog(frame, "Erreur lors de l'ajout.");
                        }

                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(frame, "Quantité invalide.");
                    }
                }

            } else {
                JOptionPane.showMessageDialog(frame, "Sélectionne un article !");
            }
        });

        frame.setVisible(true);
    }

    private void loadArticles() {
        ArticleDAO articleDAO = new ArticleDAOImpl();
        articles = articleDAO.getTousLesArticles();

        articleListModel.clear();
        for (Article article : articles) {
            articleListModel.addElement(String.format("Article %d : %s - %.2f€ (%d en stock)",
                    article.getId(), article.getNom(), article.getPrixUnitaire(), article.getStock()));
        }
    }
}

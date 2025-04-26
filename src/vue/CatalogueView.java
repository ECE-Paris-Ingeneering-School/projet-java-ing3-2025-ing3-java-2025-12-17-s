package vue;

import dao.ArticleDAO;
import dao.ArticleDAOImpl;
import dao.PanierDAO;
import dao.PanierDAOImpl;
import modele.Article;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Fenêtre d'affichage du catalogue d'articles pour le client.
 * Permet l'ajout d'articles au panier.
 */
public class CatalogueView {

    private JFrame frame;
    private DefaultListModel<String> articleListModel;
    private JList<String> articleList;
    private List<Article> articles;
    private int idClient;

    /**
     * Constructeur : initialise le catalogue et l'interface utilisateur.
     *
     * @param idClient l'identifiant du client connecté
     */
    public CatalogueView(int idClient) {
        this.idClient = idClient;

        frame = new JFrame("Catalogue d'articles");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        // Titre
        JLabel titleLabel = new JLabel("Catalogue", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        frame.add(titleLabel, BorderLayout.NORTH);

        // Liste des articles
        articleListModel = new DefaultListModel<>();
        articleList = new JList<>(articleListModel);
        articleList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane listScroller = new JScrollPane(articleList);
        frame.add(listScroller, BorderLayout.CENTER);

        // Bouton "Ajouter au panier"
        JButton addToCartButton = new JButton("Ajouter au panier");
        frame.add(addToCartButton, BorderLayout.SOUTH);

        // Charger les articles au lancement
        loadArticles();

        // Action lors du clic sur "Ajouter au panier"
        addToCartButton.addActionListener(e -> ajouterAuPanier());

        frame.setVisible(true);
    }

    /**
     * Charge la liste des articles depuis la base et l'affiche.
     */
    private void loadArticles() {
        ArticleDAO articleDAO = new ArticleDAOImpl();
        articles = articleDAO.getTousLesArticles();

        articleListModel.clear();
        for (Article article : articles) {
            articleListModel.addElement(String.format("Article %d : %s - %.2f€ (%d en stock)",
                    article.getId(), article.getNom(), article.getPrixUnitaire(), article.getStock()));
        }
    }

    /**
     * Ajoute l'article sélectionné au panier du client après confirmation de la quantité.
     */
    private void ajouterAuPanier() {
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

                    // Mise à jour du stock si succès
                    if (success) {
                        ArticleDAO articleDAO = new ArticleDAOImpl();
                        article.setStock(article.getStock() - quantite);
                        articleDAO.modifierArticle(article);

                        JOptionPane.showMessageDialog(frame, "Article ajouté !");
                        loadArticles(); // Rafraîchir l'affichage
                    } else {
                        JOptionPane.showMessageDialog(frame, "Erreur lors de l'ajout.");
                    }

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Quantité invalide.");
                }
            }

        } else {
            JOptionPane.showMessageDialog(frame, "Sélectionnez un article !");
        }
    }
}

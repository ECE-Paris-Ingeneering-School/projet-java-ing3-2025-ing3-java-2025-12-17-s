package vue;
import java.time.LocalDateTime;
import dao.CommandeDAOImpl;
import dao.LigneCommandeDAOImpl;
import modele.Commande;
import modele.LigneCommande;
import javax.swing.JFrame;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Vue de la validation de commande
 */
public class ValidationCommandeView extends JFrame {

    private JFrame frame;
    private List<String> panierArticles;
    private double totalCommande;

    public ValidationCommandeView(List<String> panierArticles, double totalCommande) {
        this.panierArticles = panierArticles;
        this.totalCommande = totalCommande;

        frame = new JFrame("Validation de commande");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        // Titre
        JLabel titleLabel = new JLabel("Confirmer votre commande", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        frame.add(titleLabel, BorderLayout.NORTH);

        // Affichage des articles dans le panier
        DefaultListModel<String> panierListModel = new DefaultListModel<>();
        for (String article : panierArticles) {
            panierListModel.addElement(article);
        }
        JList<String> panierList = new JList<>(panierListModel);
        JScrollPane listScroller = new JScrollPane(panierList);
        frame.add(listScroller, BorderLayout.CENTER);

        // Affichage du total de la commande
        JTextField totalField = new JTextField("Total : " + totalCommande + "€");
        totalField.setEditable(false);
        frame.add(totalField, BorderLayout.SOUTH);

        // Bouton pour confirmer la commande
        JButton confirmButton = new JButton("Confirmer la commande");
        frame.add(confirmButton, BorderLayout.EAST);

        // Action pour valider la commande
        confirmButton.addActionListener(e -> {
            // Créer une commande (idUtilisateur = 1 pour cet exemple)
            // Création de la commande (idUtilisateur = 1 pour cet exemple, dateCommande = LocalDateTime.now(), statut = "en cours")
            Commande commande = new Commande(1, totalCommande, LocalDateTime.now(), "en cours");
            CommandeDAOImpl commandeDAO = new CommandeDAOImpl();
            if (commandeDAO.ajouterCommande(commande)) {
                // Ajouter les articles de la commande dans la table ligne_commande
                for (String article : panierArticles) {
                    // Extraction de l'ID de l'article et de la quantité (exemple "Article 1 - 10€")
                    String[] parts = article.split(" - ");
                    int idArticle = Integer.parseInt(parts[0].split(" ")[1]); // Extraire l'ID de l'article
                    int quantite = 1; // Par défaut, quantité = 1 (modifiable selon ton interface)

                    // Créer une ligne de commande pour chaque article du panier
                    LigneCommande ligneCommande = new LigneCommande(commande.getId(), idArticle, quantite);
                    LigneCommandeDAOImpl ligneCommandeDAO = new LigneCommandeDAOImpl();
                    ligneCommandeDAO.ajouterLigneCommande(ligneCommande);
                }
                JOptionPane.showMessageDialog(frame, "Commande validée avec succès !");
                frame.dispose();  // Fermer la fenêtre après validation
            } else {
                JOptionPane.showMessageDialog(frame, "Erreur lors de la validation de la commande !");
            }
        });

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        // Exemple de panier pour tester
        List<String> panierArticles = List.of("Article 1 - 10€", "Article 2 - 20€");
        double totalCommande = 30.0;
        new ValidationCommandeView(panierArticles, totalCommande);
    }
}

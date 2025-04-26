package vue;

import dao.*;
import modele.Article;
import modele.Commande;
import modele.LigneCommande;
import modele.Remise;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Fenêtre de validation de commande.
 */
public class ValidationCommandeView extends JFrame {

    private final double[] totalCommande = new double[1];
    private final JTextArea textArea;
    private List<Article> articlesPanier;

    public ValidationCommandeView(List<String> panierArticles, double totalInitial, int idUtilisateur) {
        this.totalCommande[0] = totalInitial;

        setTitle("Validation de la commande");
        setSize(450, 320);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());

        textArea = new JTextArea();
        for (String ligne : panierArticles) {
            textArea.append(ligne + "\n");
        }
        textArea.append("\nTotal : " + String.format("%.2f", totalCommande[0]) + " €");

        panel.add(new JScrollPane(textArea), BorderLayout.CENTER);

        JPanel southPanel = new JPanel();

        JButton btnAppliquer = new JButton("Modifier code promo");
        JButton btnConfirmer = new JButton("Confirmer la commande");

        southPanel.add(btnAppliquer);
        southPanel.add(btnConfirmer);

        panel.add(southPanel, BorderLayout.SOUTH);

        add(panel);
        setVisible(true);

        // Charger les articles du panier pour avoir prixVrac et quantiteVrac
        PanierDAO panierDAO = new PanierDAOImpl();
        articlesPanier = panierDAO.getArticlesPanier(idUtilisateur);

        // À l'ouverture, proposer de saisir un code promo
        SwingUtilities.invokeLater(() -> appliquerCodePromo(panierArticles));

        // Action du bouton "Modifier code promo"
        btnAppliquer.addActionListener(e -> appliquerCodePromo(panierArticles));

        // Action du bouton "Confirmer la commande"
        btnConfirmer.addActionListener(e -> {
            CommandeDAO commandeDAO = new CommandeDAOImpl();
            Commande commande = new Commande(idUtilisateur, totalCommande[0], LocalDateTime.now(), "en cours");

            if (commandeDAO.ajouterCommande(commande)) {
                int idCommande = commande.getId();
                LigneCommandeDAO ligneDAO = new LigneCommandeDAOImpl();

                for (Article article : articlesPanier) {
                    try {
                        double prix = article.getPrixUnitaire();

                        //  Appliquer prix vrac si besoin
                        if (article.getQuantite() >= article.getQuantiteVrac()) {
                            prix = article.getPrixVrac();
                        }

                        LigneCommande ligneCommande = new LigneCommande(idCommande, article.getId(), article.getQuantite());
                        ligneDAO.ajouterLigneCommande(ligneCommande);
                    } catch (Exception ex) {
                        System.err.println("Erreur ajout ligne commande : " + ex.getMessage());
                    }
                }

                //  Vider le panier après validation
                panierDAO.viderPanier(idUtilisateur);

                JOptionPane.showMessageDialog(this, "Commande validée avec succès !");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors de la validation de la commande.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    /**
     * Méthode pour appliquer un code promo saisi par l'utilisateur.
     */
    private void appliquerCodePromo(List<String> panierArticles) {
        String code = JOptionPane.showInputDialog(this, "Entrez un code promo :");

        if (code != null && !code.trim().isEmpty()) {
            RemiseDAO remiseDAO = new RemiseDAOImpl();
            Remise remise = remiseDAO.getRemiseValide(code.trim());

            if (remise != null) {
                double remiseMontant = totalCommande[0] * remise.getPourcentage() / 100;
                totalCommande[0] -= remiseMontant;

                // Mise à jour de l'affichage avec remise appliquée
                textArea.setText("");
                for (String ligne : panierArticles) {
                    textArea.append(ligne + "\n");
                }
                textArea.append("\nCode promo appliqué : " + remise.getPourcentage() + "% (-" + String.format("%.2f", remiseMontant) + "€)");
                textArea.append("\nTotal : " + String.format("%.2f", totalCommande[0]) + " €");
            } else {
                JOptionPane.showMessageDialog(this, "Code promo invalide ou expiré.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

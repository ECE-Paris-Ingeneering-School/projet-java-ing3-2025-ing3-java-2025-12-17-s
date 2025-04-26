package vue;

import dao.CommandeDAOImpl;
import dao.LigneCommandeDAOImpl;
import modele.Commande;
import modele.LigneCommande;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Fenêtre de paiement pour valider une commande.
 */
public class FenetrePaiement extends JFrame {

    /**
     * Constructeur de la fenêtre de paiement.
     *
     * @param panierArticles liste des articles du panier
     * @param totalCommande  montant total de la commande
     * @param idUtilisateur  identifiant du client
     */
    public FenetrePaiement(List<String> panierArticles, double totalCommande, int idUtilisateur) {
        setTitle("Paiement");
        setSize(500, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(6, 2, 10, 10));

        // Champs de saisie
        JTextField nomField = new JTextField();
        JTextField prenomField = new JTextField();
        JTextField adresseField = new JTextField();
        JComboBox<String> paiementBox = new JComboBox<>(new String[]{"Carte bancaire", "PayPal", "Espèces"});

        // Labels pour afficher les totaux
        JLabel totalHTLabel = new JLabel("Total HT : " + String.format("%.2f", totalCommande * 0.8) + " €");
        JLabel totalTTCLabel = new JLabel("Total TTC : " + totalCommande + " €");

        // Bouton de validation
        JButton validerButton = new JButton("Payer");

        // Ajout des composants graphiques
        add(new JLabel("Nom :"));
        add(nomField);
        add(new JLabel("Prénom :"));
        add(prenomField);
        add(new JLabel("Adresse de livraison :"));
        add(adresseField);
        add(new JLabel("Mode de paiement :"));
        add(paiementBox);
        add(totalHTLabel);
        add(totalTTCLabel);
        add(new JLabel()); // Espace vide
        add(validerButton);

        // Action du bouton "Payer"
        validerButton.addActionListener(e -> {
            // Créer la commande
            Commande commande = new Commande(idUtilisateur, totalCommande, LocalDateTime.now(), "terminée");

            CommandeDAOImpl commandeDAO = new CommandeDAOImpl();
            if (commandeDAO.ajouterCommande(commande)) {
                // Ajouter les lignes de commande associées
                LigneCommandeDAOImpl ligneDAO = new LigneCommandeDAOImpl();
                for (String article : panierArticles) {
                    try {
                        String[] parts = article.split(" ");
                        int idArticle = Integer.parseInt(parts[1]); // Extrait l'ID de "Article <ID> ..."
                        ligneDAO.ajouterLigneCommande(new LigneCommande(commande.getId(), idArticle, 1));
                    } catch (Exception ex) {
                        System.err.println("Erreur lors de l'ajout d'une ligne commande : " + ex.getMessage());
                    }
                }

                // Confirmation
                JOptionPane.showMessageDialog(this, "Paiement validé. Merci pour votre commande !");
                dispose(); // Fermer la fenêtre de paiement
                new FenetreCommandes(idUtilisateur).setVisible(true); // Ouvrir l'historique des commandes
            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors de l'enregistrement de la commande.");
            }
        });

        setVisible(true);
    }
}

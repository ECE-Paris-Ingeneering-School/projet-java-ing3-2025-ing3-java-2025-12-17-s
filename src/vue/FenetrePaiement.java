package vue;

import dao.CommandeDAOImpl;
import dao.LigneCommandeDAOImpl;
import modele.Commande;
import modele.LigneCommande;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.List;

public class FenetrePaiement extends JFrame {

    public FenetrePaiement(List<String> panierArticles, double totalCommande, int idUtilisateur) {
        setTitle("Paiement");
        setSize(500, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(6, 2, 10, 10));

        // Champs
        JTextField nomField = new JTextField();
        JTextField prenomField = new JTextField();
        JTextField adresseField = new JTextField();
        JComboBox<String> paiementBox = new JComboBox<>(new String[]{"Carte bancaire", "PayPal", "Espèces"});

        JLabel totalHTLabel = new JLabel("Total HT : " + String.format("%.2f", totalCommande * 0.8) + " €");
        JLabel totalTTCLabel = new JLabel("Total TTC : " + totalCommande + " €");

        JButton validerButton = new JButton("Payer");

        // Ajout des composants
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

        // Action bouton "Payer"
        validerButton.addActionListener(e -> {
            // Enregistrement de la commande
            Commande commande = new Commande(idUtilisateur, LocalDateTime.now(), "terminée");
            CommandeDAOImpl commandeDAO = new CommandeDAOImpl();
            if (commandeDAO.ajouterCommande(commande)) {
                LigneCommandeDAOImpl ligneDAO = new LigneCommandeDAOImpl();
                for (String article : panierArticles) {
                    // Hypothèse : article sous forme "Article <ID> - nom - prix€"
                    try {
                        String[] parts = article.split(" ");
                        int idArticle = Integer.parseInt(parts[1]); // "Article 1" => 1
                        ligneDAO.ajouterLigneCommande(new LigneCommande(commande.getId(), idArticle, 1));
                    } catch (Exception ex) {
                        System.err.println("Erreur ligne panier : " + ex.getMessage());
                    }
                }

                JOptionPane.showMessageDialog(this, "Paiement validé. Merci pour votre commande !");
                dispose();
                new FenetreCommandes(idUtilisateur).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors de l'enregistrement de la commande.");
            }
        });

        setVisible(true);
    }
}

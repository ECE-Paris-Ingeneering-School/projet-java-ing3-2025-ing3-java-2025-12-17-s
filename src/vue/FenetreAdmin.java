package vue;

import javax.swing.*;
import java.awt.*;

/**
 * Fenêtre principale pour l'interface administrateur.
 */
public class FenetreAdmin extends JFrame {

    public FenetreAdmin(String nomAdmin) {
        setTitle("Interface Administrateur - " + nomAdmin);
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(5, 1, 10, 10)); // 5 lignes pour 5 boutons

        // Création des boutons avec emojis
        JButton btnProduits = new JButton("📦 Gérer les articles");
        JButton btnRemises = new JButton("🎁 Gérer les remises");
        JButton btnStats = new JButton("📊 Voir les statistiques");
        JButton btnVoirAvis = new JButton("⭐ Voir les avis");
        JButton btnDeconnexion = new JButton("🚪 Déconnexion");

        // Ajout des actions
        btnProduits.addActionListener(e -> new FenetreArticlesAdmin());
        btnRemises.addActionListener(e -> new FenetreRemisesAdmin());
        btnStats.addActionListener(e -> new FenetreStatistiquesAdmin());
        btnVoirAvis.addActionListener(e -> new FenetreAvisAdmin());
        btnDeconnexion.addActionListener(e -> {
            dispose(); // Fermer cette fenêtre
            new ConnexionView(); // Retour à la page de connexion
        });

        // Ajout des boutons à la fenêtre
        add(btnProduits);
        add(btnRemises);
        add(btnStats);
        add(btnVoirAvis);
        add(btnDeconnexion);

        setVisible(true);
    }
}

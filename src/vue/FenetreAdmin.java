package vue;

import javax.swing.*;
import java.awt.*;

public class FenetreAdmin extends JFrame {

    public FenetreAdmin(String nomAdmin) {
        setTitle("Interface Administrateur - " + nomAdmin);
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(4, 1, 10, 10));

        JButton btnProduits = new JButton("Gérer les articles");
        JButton btnRemises = new JButton("Gérer les remises");
        JButton btnStats = new JButton("Voir les statistiques");
        JButton btnDeconnexion = new JButton("Déconnexion"); // Nouveau bouton

        btnProduits.addActionListener(e -> new FenetreArticlesAdmin());
        btnRemises.addActionListener(e -> new FenetreRemisesAdmin());
        btnStats.addActionListener(e -> new FenetreStatistiquesAdmin());
        btnDeconnexion.addActionListener(e -> {
            dispose(); // Fermer cette fenêtre
            new ConnexionView(); // Retour à la fenêtre de connexion
        });

        add(btnProduits);
        add(btnRemises);
        add(btnStats);
        add(btnDeconnexion); // Ajout du bouton à la fenêtre

        setVisible(true);
    }
}

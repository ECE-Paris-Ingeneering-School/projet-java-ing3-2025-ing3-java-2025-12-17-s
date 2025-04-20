package vue;

import javax.swing.*;
import java.awt.*;

public class FenetreClient extends JFrame {

    private int idUtilisateur;

    public FenetreClient(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;

        setTitle("Espace client");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton btnCatalogue = new JButton("🛍 Consulter le catalogue");
        JButton btnCommandes = new JButton("📦 Mes commandes");
        JButton btnPanier = new JButton("🛒 Voir mon panier");
        JButton btnDeconnexion = new JButton("🚪 Déconnexion");

        panel.add(btnCatalogue);
        panel.add(btnCommandes);
        panel.add(btnPanier);
        panel.add(btnDeconnexion);

        add(panel);

        // Action pour consulter le catalogue
        btnCatalogue.addActionListener(e -> {
            new CatalogueView(idUtilisateur);  // tu pourras lui passer l'idUtilisateur si nécessaire
        });

        // Action pour voir les commandes
        btnCommandes.addActionListener(e -> {
            new FenetreCommandes(idUtilisateur).setVisible(true);
        });

        // Action pour voir le panier
        btnPanier.addActionListener(e -> {
            new FenetrePanier(idUtilisateur);  // remplace le message par la vraie fenêtre
        });

        // Action pour se déconnecter
        btnDeconnexion.addActionListener(e -> {
            dispose(); // Ferme la fenêtre actuelle
            new ConnexionView(); // Retour à la connexion
        });

        setVisible(true); // n'oublie pas d'afficher la fenêtre !
    }

    public static void main(String[] args) {
        new FenetreClient(1).setVisible(true); // pour tester
    }
}

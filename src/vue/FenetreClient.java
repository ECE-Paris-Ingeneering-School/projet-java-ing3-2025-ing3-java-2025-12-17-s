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

        // Actions des boutons
        btnCatalogue.addActionListener(e -> {
            new CatalogueView();  // tu pourras lui passer l'idUtilisateur si nécessaire
        });

        btnCommandes.addActionListener(e -> {
            new FenetreCommandes(idUtilisateur).setVisible(true);
        });

        btnPanier.addActionListener(e -> {
            // à créer ensuite : FenetrePanier
            JOptionPane.showMessageDialog(this, "Fenêtre panier à implémenter.");
        });

        btnDeconnexion.addActionListener(e -> {
            dispose(); // Ferme la fenêtre actuelle
            new ConnexionView(); // Retour à la connexion
        });
    }

    public static void main(String[] args) {
        new FenetreClient(1).setVisible(true); // pour tester
    }
}

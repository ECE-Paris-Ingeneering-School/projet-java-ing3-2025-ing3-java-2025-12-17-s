package vue;

import javax.swing.*;
import java.awt.*;

public class FenetreAdmin extends JFrame {

    public FenetreAdmin(String nomAdmin) {
        setTitle("Interface Administrateur - " + nomAdmin);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(3, 1, 10, 10));

        JButton btnProduits = new JButton("Gérer les articles");
        JButton btnRemises = new JButton("Gérer les remises");
        JButton btnStats = new JButton("Voir les statistiques");

        btnProduits.addActionListener(e -> new FenetreArticlesAdmin());
        btnRemises.addActionListener(e -> new FenetreRemisesAdmin());
        btnStats.addActionListener(e -> new FenetreStatistiquesAdmin());

        add(btnProduits);
        add(btnRemises);
        add(btnStats);

        setVisible(true);
    }
}

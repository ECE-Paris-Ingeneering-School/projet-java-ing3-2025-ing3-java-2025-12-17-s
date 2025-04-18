package vue;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class FenetrePanier extends JFrame {
    private List<String> panierArticles;
    private double totalCommande;
    private int idUtilisateur;

    public FenetrePanier(List<String> panierArticles, double totalCommande, int idUtilisateur) {
        this.panierArticles = panierArticles;
        this.totalCommande = totalCommande;
        this.idUtilisateur = idUtilisateur;

        setTitle("Panier");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Zone liste des articles
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (String article : panierArticles) {
            listModel.addElement(article);
        }
        JList<String> articleList = new JList<>(listModel);
        add(new JScrollPane(articleList), BorderLayout.CENTER);

        // Total
        JLabel totalLabel = new JLabel("Total : " + totalCommande + " €");
        totalLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(totalLabel, BorderLayout.NORTH);

        // Bouton Valider
        JButton btnValider = new JButton("Valider la commande");
        btnValider.addActionListener(e -> {
            new ValidationCommandeView(panierArticles, totalCommande).setVisible(true);
            dispose(); // Ferme la fenêtre panier
        });

        add(btnValider, BorderLayout.SOUTH);
    }
}

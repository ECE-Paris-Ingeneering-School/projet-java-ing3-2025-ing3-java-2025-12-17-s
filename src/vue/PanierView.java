package vue;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Vue du panier affichant les articles ajoutés au panier
 */
public class PanierView {

    private JFrame frame;
    private DefaultListModel<String> panierListModel;
    private List<String> panierArticles;
    private JTextField totalField;

    public PanierView() {
        panierArticles = new ArrayList<>();
        frame = new JFrame("Panier");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        // Titre
        JLabel titleLabel = new JLabel("Mon Panier", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        frame.add(titleLabel, BorderLayout.NORTH);

        // Liste des articles dans le panier
        panierListModel = new DefaultListModel<>();
        JList<String> panierList = new JList<>(panierListModel);
        panierList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane listScroller = new JScrollPane(panierList);
        frame.add(listScroller, BorderLayout.CENTER);

        // Zone pour afficher le total
        totalField = new JTextField("Total: 0€");
        totalField.setEditable(false);
        frame.add(totalField, BorderLayout.SOUTH);

        // Bouton pour valider la commande
        JButton validerCommandeButton = new JButton("Valider la commande");
        frame.add(validerCommandeButton, BorderLayout.EAST);

        // Ajouter des articles au panier
        addArticleToPanier("Article 1 - 10€");
        addArticleToPanier("Article 2 - 20€");
        addArticleToPanier("Article 3 - 15€");

        // Action pour le bouton "Valider la commande"
        validerCommandeButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "Commande validée ! Total: " + getTotal() + "€");
        });

        frame.setVisible(true);
    }

    // Ajouter un article au panier
    public void addArticleToPanier(String article) {
        panierArticles.add(article);
        panierListModel.addElement(article);
        updateTotal();
    }

    // Calculer le total du panier
    private void updateTotal() {
        double total = 0;
        for (String article : panierArticles) {
            String[] parts = article.split(" - ");
            total += Double.parseDouble(parts[1].replace("€", ""));
        }
        totalField.setText("Total: " + total + "€");
    }

    // Calculer le total du panier
    private double getTotal() {
        double total = 0;
        for (String article : panierArticles) {
            String[] parts = article.split(" - ");
            total += Double.parseDouble(parts[1].replace("€", ""));
        }
        return total;
    }

    public static void main(String[] args) {
        new PanierView();
    }
}

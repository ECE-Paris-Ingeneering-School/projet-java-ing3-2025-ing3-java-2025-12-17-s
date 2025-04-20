package vue;

import dao.CommandeDAO;
import dao.CommandeDAOImpl;
import dao.LigneCommandeDAO;
import dao.LigneCommandeDAOImpl;
import dao.RemiseDAO;
import dao.RemiseDAOImpl;
import modele.Commande;
import modele.LigneCommande;
import modele.Remise;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.List;

public class ValidationCommandeView extends JFrame {
    private final double[] totalCommande = new double[1];
    private final JTextArea textArea;

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
        textArea.append("\nTotal : " + totalCommande[0] + " €");

        panel.add(new JScrollPane(textArea), BorderLayout.CENTER);

        JPanel southPanel = new JPanel();

        JButton btnAppliquer = new JButton("Modifier code promo");
        JButton btnConfirmer = new JButton("Confirmer la commande");

        southPanel.add(btnAppliquer);
        southPanel.add(btnConfirmer);

        panel.add(southPanel, BorderLayout.SOUTH);

        add(panel);
        setVisible(true);

        // ✅ Demander le code promo à l'ouverture
        SwingUtilities.invokeLater(() -> appliquerCodePromo(panierArticles));

        // ✅ Bouton "Modifier code promo"
        btnAppliquer.addActionListener(e -> appliquerCodePromo(panierArticles));

        // ✅ Valider la commande
        btnConfirmer.addActionListener(e -> {
            CommandeDAO commandeDAO = new CommandeDAOImpl();
            Commande commande = new Commande(idUtilisateur, totalCommande[0], LocalDateTime.now(), "en cours");

            if (commandeDAO.ajouterCommande(commande)) {
                int idCommande = commande.getId();
                LigneCommandeDAO ligneDAO = new LigneCommandeDAOImpl();

                for (String ligne : panierArticles) {
                    try {
                        String[] parts = ligne.split(" x");
                        if (parts.length < 2) continue;

                        int quantite = Integer.parseInt(parts[1].trim());
                        String leftPart = parts[0]; // Ex: "Article 1 : 1,50€ - Stylo"
                        int idArticle = Integer.parseInt(leftPart.split(":")[0].replace("Article", "").trim());

                        LigneCommande ligneCommande = new LigneCommande(idCommande, idArticle, quantite);
                        ligneDAO.ajouterLigneCommande(ligneCommande);
                    } catch (Exception ex) {
                        System.err.println("Erreur ligne commande : " + ex.getMessage());
                    }
                }

                JOptionPane.showMessageDialog(this, "Commande validée !");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors de la validation.");
            }
        });
    }

    private void appliquerCodePromo(List<String> panierArticles) {
        String code = JOptionPane.showInputDialog(this, "Entrez un code promo :");

        if (code != null && !code.trim().isEmpty()) {
            RemiseDAO remiseDAO = new RemiseDAOImpl();
            Remise remise = remiseDAO.getRemiseValide(code.trim());

            if (remise != null) {
                double remiseMontant = totalCommande[0] * remise.getPourcentage() / 100;
                totalCommande[0] -= remiseMontant;

                // 🔄 Mise à jour de l'affichage
                textArea.setText("");
                for (String ligne : panierArticles) {
                    textArea.append(ligne + "\n");
                }
                textArea.append("\nCode promo appliqué : " + remise.getPourcentage() + "% (-" + String.format("%.2f", remiseMontant) + "€)");
                textArea.append("\nTotal : " + String.format("%.2f", totalCommande[0]) + " €");
            } else {
                JOptionPane.showMessageDialog(this, "Code promo invalide ou expiré.");
            }
        }
    }
}

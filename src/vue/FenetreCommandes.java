package vue;

import dao.ArticleDAOImpl;
import dao.CommandeDAOImpl;
import dao.LigneCommandeDAOImpl;
import modele.Article;
import modele.Commande;
import modele.LigneCommande;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class FenetreCommandes extends JFrame {

    public FenetreCommandes(int idUtilisateur) {
        setTitle("Mes commandes");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel titre = new JLabel("Historique des commandes de l'utilisateur n°" + idUtilisateur);
        titre.setHorizontalAlignment(SwingConstants.CENTER);
        titre.setFont(new Font("Arial", Font.BOLD, 18));
        add(titre, BorderLayout.NORTH);

        // Zone d'affichage des commandes
        JTextArea commandesArea = new JTextArea();
        commandesArea.setEditable(false);
        commandesArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        JScrollPane scrollPane = new JScrollPane(commandesArea);
        add(scrollPane, BorderLayout.CENTER);

        // DAO
        CommandeDAOImpl commandeDAO = new CommandeDAOImpl();
        LigneCommandeDAOImpl ligneDAO = new LigneCommandeDAOImpl();
        ArticleDAOImpl articleDAO = new ArticleDAOImpl();

        List<Commande> commandes = commandeDAO.getCommandesParUtilisateur(idUtilisateur);

        StringBuilder affichage = new StringBuilder();

        for (Commande commande : commandes) {
            affichage.append("🧾 Commande n°").append(commande.getId())
                    .append(" - ").append(commande.getDateCommande())
                    .append(" - Statut : ").append(commande.getStatut())
                    .append("\n");

            List<LigneCommande> lignes = ligneDAO.getLignesParCommande(commande.getId());
            for (LigneCommande ligne : lignes) {
                Article article = articleDAO.getArticleParId(ligne.getIdArticle());
                if (article != null) {
                    affichage.append("   - ").append(article.getNom())
                            .append(" x").append(ligne.getQuantite()).append("\n");
                }
            }
            affichage.append("\n");
        }

        commandesArea.setText(affichage.toString());
    }
}

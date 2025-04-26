package vue;

import dao.AvisDAOImpl;
import dao.UtilisateurDAOImpl;
import dao.ArticleDAOImpl;
import modele.Avis;
import modele.Utilisateur;
import modele.Article;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Fenêtre pour afficher tous les avis pour l'administrateur.
 */
public class FenetreAvisAdmin extends JFrame {

    public FenetreAvisAdmin() {
        setTitle("Liste des avis clients");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel titre = new JLabel("Tous les avis des clients", SwingConstants.CENTER);
        titre.setFont(new Font("Arial", Font.BOLD, 20));
        add(titre, BorderLayout.NORTH);

        // Table pour afficher les avis
        JTable tableAvis = new JTable();
        JScrollPane scrollPane = new JScrollPane(tableAvis);
        add(scrollPane, BorderLayout.CENTER);

        chargerAvis(tableAvis);

        setVisible(true);
    }

    /**
     * Charge les avis depuis la base et les insère dans la table.
     */
    private void chargerAvis(JTable tableAvis) {
        AvisDAOImpl avisDAO = new AvisDAOImpl();
        UtilisateurDAOImpl utilisateurDAO = new UtilisateurDAOImpl();
        ArticleDAOImpl articleDAO = new ArticleDAOImpl();

        List<Avis> listeAvis = avisDAO.getToutesLesAvis(); // méthode qu'on va ajouter juste après

        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"Article", "Client", "Note", "Commentaire", "Date"}, 0
        );

        for (Avis avis : listeAvis) {
            Article article = articleDAO.getArticleParId(avis.getIdArticle());
            Utilisateur utilisateur = utilisateurDAO.getUtilisateurParId(avis.getIdUtilisateur());

            String nomArticle = (article != null) ? article.getNom() : "Inconnu";
            String nomClient = (utilisateur != null) ? utilisateur.getPrenom() + " " + utilisateur.getNom() : "Inconnu";

            model.addRow(new Object[]{
                    nomArticle,
                    nomClient,
                    avis.getNote() + "/5",
                    avis.getCommentaire(),
                    avis.getDateAvis().toLocalDate()
            });
        }

        tableAvis.setModel(model);
    }
}

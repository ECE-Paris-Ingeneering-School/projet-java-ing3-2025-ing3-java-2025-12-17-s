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

/**
 * Fenêtre d'affichage des commandes passées par un utilisateur,
 * avec possibilité de laisser un avis sur chaque article acheté.
 */
public class FenetreCommandes extends JFrame {

    private int idUtilisateur;
    private JPanel commandesPanel;

    /**
     * Constructeur de la fenêtre d'affichage des commandes.
     *
     * @param idUtilisateur l'identifiant de l'utilisateur
     */
    public FenetreCommandes(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;

        setTitle("Mes commandes");
        setSize(700, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel titre = new JLabel("Historique des commandes de l'utilisateur n°" + idUtilisateur);
        titre.setHorizontalAlignment(SwingConstants.CENTER);
        titre.setFont(new Font("Arial", Font.BOLD, 18));
        add(titre, BorderLayout.NORTH);

        commandesPanel = new JPanel();
        commandesPanel.setLayout(new BoxLayout(commandesPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(commandesPanel);
        add(scrollPane, BorderLayout.CENTER);

        afficherCommandes();
        setVisible(true);
    }

    /**
     * Charge et affiche les commandes de l'utilisateur.
     */
    private void afficherCommandes() {
        CommandeDAOImpl commandeDAO = new CommandeDAOImpl();
        LigneCommandeDAOImpl ligneDAO = new LigneCommandeDAOImpl();
        ArticleDAOImpl articleDAO = new ArticleDAOImpl();

        List<Commande> commandes = commandeDAO.getCommandesParUtilisateur(idUtilisateur);

        for (Commande commande : commandes) {
            JPanel panelCommande = new JPanel(new BorderLayout());
            panelCommande.setBorder(BorderFactory.createTitledBorder(
                    "Commande n°" + commande.getId() + " - " + commande.getDateCommande() + " - Statut : " + commande.getStatut()));

            JPanel panelArticles = new JPanel();
            panelArticles.setLayout(new BoxLayout(panelArticles, BoxLayout.Y_AXIS));

            List<LigneCommande> lignes = ligneDAO.getLignesParCommande(commande.getId());
            for (LigneCommande ligne : lignes) {
                Article article = articleDAO.getArticleParId(ligne.getIdArticle());
                if (article != null) {
                    JPanel panelArticle = new JPanel(new FlowLayout(FlowLayout.LEFT));
                    JLabel lblArticle = new JLabel(article.getNom() + " x" + ligne.getQuantite());

                    JButton btnAvis = new JButton("Donner un avis");
                    btnAvis.addActionListener(e -> {
                        new FenetreAvis(idUtilisateur, article.getId());
                    });

                    panelArticle.add(lblArticle);
                    panelArticle.add(btnAvis);
                    panelArticles.add(panelArticle);
                }
            }

            panelCommande.add(panelArticles, BorderLayout.CENTER);
            commandesPanel.add(panelCommande);
        }

        commandesPanel.revalidate();
        commandesPanel.repaint();
    }
}

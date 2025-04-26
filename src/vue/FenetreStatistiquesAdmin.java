package vue;

import utils.StatistiquesUtils;

import javax.swing.*;

/**
 * Fenêtre permettant à l'administrateur de visualiser
 * différentes statistiques liées aux commandes et aux articles.
 */
public class FenetreStatistiquesAdmin extends JFrame {

    /**
     * Constructeur de la fenêtre des statistiques administrateur.
     * Configure les onglets pour afficher différents graphiques.
     */
    public FenetreStatistiquesAdmin() {
        setTitle("Statistiques - Admin");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Création des onglets
        JTabbedPane tabs = new JTabbedPane();

        // Onglet : Articles les plus commandés
        tabs.addTab("Articles les plus commandés", StatistiquesUtils.creerHistogrammeArticlesCommandes());

        // Onglet : Chiffre d'affaires par article
        tabs.addTab("Chiffre d'affaires par article", StatistiquesUtils.creerHistogrammeCAparArticle());

        // Onglet : Nombre de commandes par mois
        tabs.addTab("Commandes par mois", StatistiquesUtils.creerCourbeCommandesParMois());

        // Ajout des onglets à la fenêtre
        add(tabs);

        setVisible(true);
    }
}

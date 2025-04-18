package vue;

import utils.StatistiquesUtils;

import javax.swing.*;

public class FenetreStatistiquesAdmin extends JFrame {

    public FenetreStatistiquesAdmin() {
        setTitle("Statistiques - Admin");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JTabbedPane tabs = new JTabbedPane();

        tabs.addTab("Articles les plus commandés", StatistiquesUtils.creerHistogrammeArticlesCommandes());
        tabs.addTab("Chiffre d'affaires par article", StatistiquesUtils.creerHistogrammeCAparArticle());
        tabs.addTab("Commandes par mois", StatistiquesUtils.creerCourbeCommandesParMois());

        add(tabs);
        setVisible(true);
    }
}

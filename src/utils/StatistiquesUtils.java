package utils;

import dao.ArticleDAOImpl;
import dao.CommandeDAOImpl;
import dao.LigneCommandeDAOImpl;
import modele.Article;
import modele.Commande;
import modele.LigneCommande;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Classe utilitaire pour générer des statistiques graphiques sur les articles et les commandes.
 */
public class StatistiquesUtils {

    /**
     * Crée un histogramme montrant la quantité totale commandée pour chaque article.
     *
     * @return un JPanel contenant l'histogramme
     */
    public static JPanel creerHistogrammeArticlesCommandes() {
        LigneCommandeDAOImpl ligneDAO = new LigneCommandeDAOImpl();
        ArticleDAOImpl articleDAO = new ArticleDAOImpl();
        List<LigneCommande> lignes = ligneDAO.getToutesLesLignesCommandes();

        Map<Integer, Integer> quantites = new HashMap<>();

        // Calcul de la quantité totale commandée par article
        for (LigneCommande ligne : lignes) {
            quantites.merge(ligne.getIdArticle(), ligne.getQuantite(), Integer::sum);
        }

        // Remplissage du dataset pour le graphique
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map.Entry<Integer, Integer> entry : quantites.entrySet()) {
            Article a = articleDAO.getArticleParId(entry.getKey());
            if (a != null) {
                dataset.addValue(entry.getValue(), "Quantité", a.getNom());
            }
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Articles les plus commandés",
                "Article",
                "Quantité totale",
                dataset
        );

        return new ChartPanel(chart);
    }

    /**
     * Crée un histogramme montrant le chiffre d'affaires réalisé par article.
     *
     * @return un JPanel contenant l'histogramme
     */
    public static JPanel creerHistogrammeCAparArticle() {
        LigneCommandeDAOImpl ligneDAO = new LigneCommandeDAOImpl();
        ArticleDAOImpl articleDAO = new ArticleDAOImpl();
        List<LigneCommande> lignes = ligneDAO.getToutesLesLignesCommandes();

        Map<Integer, Double> chiffreAffaires = new HashMap<>();

        // Calcul du chiffre d'affaires pour chaque article
        for (LigneCommande ligne : lignes) {
            Article a = articleDAO.getArticleParId(ligne.getIdArticle());
            if (a != null) {
                double total = a.getPrixUnitaire() * ligne.getQuantite();
                chiffreAffaires.merge(a.getId(), total, Double::sum);
            }
        }

        // Remplissage du dataset pour le graphique
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map.Entry<Integer, Double> entry : chiffreAffaires.entrySet()) {
            Article a = articleDAO.getArticleParId(entry.getKey());
            if (a != null) {
                dataset.addValue(entry.getValue(), "Chiffre d'affaires (€)", a.getNom());
            }
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Chiffre d'affaires par article",
                "Article",
                "Montant (€)",
                dataset
        );

        return new ChartPanel(chart);
    }

    /**
     * Crée une courbe montrant l'évolution du nombre de commandes par mois.
     *
     * @return un JPanel contenant la courbe
     */
    public static JPanel creerCourbeCommandesParMois() {
        CommandeDAOImpl commandeDAO = new CommandeDAOImpl();
        List<Commande> commandes = commandeDAO.getToutesLesCommandes();

        int[] nbParMois = new int[12];

        // Comptage du nombre de commandes par mois
        for (Commande c : commandes) {
            int mois = c.getDateCommande().getMonthValue() - 1;
            nbParMois[mois]++;
        }

        // Remplissage du dataset pour la courbe
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 0; i < 12; i++) {
            dataset.addValue(nbParMois[i], "Commandes", Month.of(i + 1).name());
        }

        JFreeChart chart = ChartFactory.createLineChart(
                "Commandes par mois",
                "Mois",
                "Nombre de commandes",
                dataset
        );

        return new ChartPanel(chart);
    }
}

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

public class StatistiquesUtils {

    // 📦 Quantité totale commandée par article
    public static JPanel creerHistogrammeArticlesCommandes() {
        LigneCommandeDAOImpl ligneDAO = new LigneCommandeDAOImpl();
        ArticleDAOImpl articleDAO = new ArticleDAOImpl();
        List<LigneCommande> lignes = ligneDAO.getToutesLesLignesCommandes();

        Map<Integer, Integer> quantites = new HashMap<>();

        for (LigneCommande ligne : lignes) {
            quantites.merge(ligne.getIdArticle(), ligne.getQuantite(), Integer::sum);
        }

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

    // 💰 Chiffre d'affaires par article
    public static JPanel creerHistogrammeCAparArticle() {
        LigneCommandeDAOImpl ligneDAO = new LigneCommandeDAOImpl();
        ArticleDAOImpl articleDAO = new ArticleDAOImpl();
        List<LigneCommande> lignes = ligneDAO.getToutesLesLignesCommandes();

        Map<Integer, Double> chiffreAffaires = new HashMap<>();

        for (LigneCommande ligne : lignes) {
            Article a = articleDAO.getArticleParId(ligne.getIdArticle());
            if (a != null) {
                double total = a.getPrixUnitaire() * ligne.getQuantite();
                chiffreAffaires.merge(a.getId(), total, Double::sum);
            }
        }

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

    // 📈 Courbe du nombre de commandes par mois
    public static JPanel creerCourbeCommandesParMois() {
        CommandeDAOImpl commandeDAO = new CommandeDAOImpl();
        List<Commande> commandes = commandeDAO.getToutesLesCommandes();

        int[] nbParMois = new int[12];

        for (Commande c : commandes) {
            int mois = c.getDateCommande().getMonthValue() - 1;
            nbParMois[mois]++;
        }

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

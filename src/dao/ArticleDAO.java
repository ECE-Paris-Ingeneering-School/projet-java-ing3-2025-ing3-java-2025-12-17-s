package dao;

import modele.Article;
import java.util.List;

/**
 * Interface DAO pour les articles
 */
public interface ArticleDAO {

    // Ajouter un article
    boolean ajouterArticle(Article article);

    // Modifier un article
    boolean modifierArticle(Article article);

    // Supprimer un article par ID
    boolean supprimerArticle(int id);

    // Obtenir un article par ID
    Article getArticleParId(int id);

    // Obtenir tous les articles
    List<Article> getTousLesArticles();
}

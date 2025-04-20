package dao;

import modele.Article;
import java.util.List;

public interface PanierDAO {

    List<Article> getArticlesPanier(int idClient);

    boolean ajouterAuPanier(int idClient, int idArticle, int quantite);

    boolean supprimerDuPanier(int idClient, int idArticle);

    boolean viderPanier(int idClient);
}

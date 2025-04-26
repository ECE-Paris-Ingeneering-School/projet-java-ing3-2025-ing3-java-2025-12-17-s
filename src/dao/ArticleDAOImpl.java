package dao;

import modele.Article;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implémentation DAO pour la gestion des articles dans la base de données.
 * <p>
 * Cette classe fournit les opérations CRUD (Create, Read, Update, Delete)
 * pour les objets {@link Article} en utilisant JDBC.
 */
public class ArticleDAOImpl implements ArticleDAO {

    /**
     * Ajoute un nouvel article à la base de données.
     *
     * @param article l'article à ajouter
     * @return true si l'ajout a réussi, false sinon
     */
    @Override
    public boolean ajouterArticle(Article article) {
        String sql = "INSERT INTO article (nom, marque, prixUnitaire, prixVrac, quantiteVrac, stock) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnexionBDD.getConnexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, article.getNom());
            ps.setString(2, article.getMarque());
            ps.setDouble(3, article.getPrixUnitaire());
            ps.setDouble(4, article.getPrixVrac());
            ps.setInt(5, article.getQuantiteVrac());
            ps.setInt(6, article.getStock());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Erreur ajout article : " + e.getMessage());
            return false;
        }
    }

    /**
     * Modifie un article existant dans la base de données.
     *
     * @param article l'article avec les nouvelles informations
     * @return true si la modification a réussi, false sinon
     */
    @Override
    public boolean modifierArticle(Article article) {
        String sql = "UPDATE article SET nom = ?, marque = ?, prixUnitaire = ?, prixVrac = ?, quantiteVrac = ?, stock = ? WHERE id = ?";

        try (Connection conn = ConnexionBDD.getConnexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, article.getNom());
            ps.setString(2, article.getMarque());
            ps.setDouble(3, article.getPrixUnitaire());
            ps.setDouble(4, article.getPrixVrac());
            ps.setInt(5, article.getQuantiteVrac());
            ps.setInt(6, article.getStock());
            ps.setInt(7, article.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Erreur modification article : " + e.getMessage());
            return false;
        }
    }

    /**
     * Supprime un article de la base de données à partir de son identifiant.
     *
     * @param id l'identifiant de l'article à supprimer
     * @return true si la suppression a réussi, false sinon
     */
    @Override
    public boolean supprimerArticle(int id) {
        String sql = "DELETE FROM article WHERE id = ?";

        try (Connection conn = ConnexionBDD.getConnexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Erreur suppression article : " + e.getMessage());
            return false;
        }
    }

    /**
     * Récupère un article à partir de son identifiant.
     *
     * @param id l'identifiant de l'article
     * @return l'article correspondant ou null si non trouvé
     */
    @Override
    public Article getArticleParId(int id) {
        String sql = "SELECT * FROM article WHERE id = ?";

        try (Connection conn = ConnexionBDD.getConnexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Article(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("marque"),
                        rs.getDouble("prixUnitaire"),
                        rs.getDouble("prixVrac"),
                        rs.getInt("quantiteVrac"),
                        rs.getInt("stock")
                );
            }

        } catch (SQLException e) {
            System.err.println("Erreur getArticleParId : " + e.getMessage());
        }
        return null;
    }

    /**
     * Récupère tous les articles présents dans la base de données.
     *
     * @return une liste d'articles
     */
    @Override
    public List<Article> getTousLesArticles() {
        List<Article> liste = new ArrayList<>();
        String sql = "SELECT * FROM article";

        try (Connection conn = ConnexionBDD.getConnexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Article a = new Article(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("marque"),
                        rs.getDouble("prixUnitaire"),
                        rs.getDouble("prixVrac"),
                        rs.getInt("quantiteVrac"),
                        rs.getInt("stock")
                );
                liste.add(a);
            }

        } catch (SQLException e) {
            System.err.println("Erreur getTousLesArticles : " + e.getMessage());
        }
        return liste;
    }
}

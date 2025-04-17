package dao;

import modele.Article;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implémentation DAO pour la gestion des articles
 */
public class ArticleDAOImpl implements ArticleDAO {

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

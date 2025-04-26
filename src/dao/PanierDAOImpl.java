package dao;

import modele.Article;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implémentation DAO pour la gestion du panier client.
 * <p>
 * Permet d'ajouter, supprimer, récupérer les articles du panier,
 * et de vider complètement un panier.
 */
public class PanierDAOImpl implements PanierDAO {

    /**
     * Ajoute un article au panier d'un client.
     * Si l'article existe déjà, sa quantité est mise à jour.
     *
     * @param idClient  l'identifiant du client
     * @param idArticle l'identifiant de l'article
     * @param quantite  la quantité à ajouter
     * @return true si l'opération réussit, false sinon
     */
    @Override
    public boolean ajouterAuPanier(int idClient, int idArticle, int quantite) {
        String checkSQL = "SELECT quantite FROM panier WHERE id_client = ? AND id_article = ?";
        String insertSQL = "INSERT INTO panier (id_client, id_article, quantite) VALUES (?, ?, ?)";
        String updateSQL = "UPDATE panier SET quantite = quantite + ? WHERE id_client = ? AND id_article = ?";

        try (Connection conn = ConnexionBDD.getConnexion()) {
            PreparedStatement psCheck = conn.prepareStatement(checkSQL);
            psCheck.setInt(1, idClient);
            psCheck.setInt(2, idArticle);
            ResultSet rs = psCheck.executeQuery();

            if (rs.next()) {
                PreparedStatement psUpdate = conn.prepareStatement(updateSQL);
                psUpdate.setInt(1, quantite);
                psUpdate.setInt(2, idClient);
                psUpdate.setInt(3, idArticle);
                psUpdate.executeUpdate();
            } else {
                PreparedStatement psInsert = conn.prepareStatement(insertSQL);
                psInsert.setInt(1, idClient);
                psInsert.setInt(2, idArticle);
                psInsert.setInt(3, quantite);
                psInsert.executeUpdate();
            }

            return true;

        } catch (SQLException e) {
            System.err.println("Erreur ajouterAuPanier : " + e.getMessage());
            return false;
        }
    }

    /**
     * Supprime un article spécifique du panier d'un client.
     *
     * @param idClient  l'identifiant du client
     * @param idArticle l'identifiant de l'article à supprimer
     * @return true si la suppression réussit, false sinon
     */
    @Override
    public boolean supprimerDuPanier(int idClient, int idArticle) {
        String sql = "DELETE FROM panier WHERE id_client = ? AND id_article = ?";

        try (Connection conn = ConnexionBDD.getConnexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idClient);
            ps.setInt(2, idArticle);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Erreur suppression article du panier : " + e.getMessage());
            return false;
        }
    }

    /**
     * Vide complètement le panier d'un client.
     *
     * @param idClient l'identifiant du client
     * @return true si l'opération réussit, false sinon
     */
    @Override
    public boolean viderPanier(int idClient) {
        String sql = "DELETE FROM panier WHERE id_client = ?";

        try (Connection conn = ConnexionBDD.getConnexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idClient);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Erreur vider panier : " + e.getMessage());
            return false;
        }
    }

    /**
     * Récupère tous les articles présents dans le panier d'un client.
     *
     * @param idClient l'identifiant du client
     * @return la liste des articles dans son panier
     */
    @Override
    public List<Article> getArticlesPanier(int idClient) {
        List<Article> articles = new ArrayList<>();
        String sql = "SELECT a.id, a.nom, a.description, a.marque, a.prixUnitaire, a.prixVrac, a.quantiteVrac, a.stock, p.quantite " +
                "FROM article a JOIN panier p ON a.id = p.id_article WHERE p.id_client = ?";

        try (Connection conn = ConnexionBDD.getConnexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idClient);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Article a = new Article(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("description"),
                        rs.getString("marque"),
                        rs.getDouble("prixUnitaire"),
                        rs.getInt("stock")
                );
                a.setPrixVrac(rs.getDouble("prixVrac"));
                a.setQuantiteVrac(rs.getInt("quantiteVrac"));
                a.setQuantite(rs.getInt("quantite"));
                articles.add(a);
            }

        } catch (SQLException e) {
            System.err.println("Erreur getArticlesPanier : " + e.getMessage());
        }

        return articles;
    }

    /**
     * Supprime un article spécifique du panier d'un client.
     * (Alias méthode identique à supprimerDuPanier)
     *
     * @param idClient  l'identifiant du client
     * @param idArticle l'identifiant de l'article à supprimer
     * @return true si la suppression réussit, false sinon
     */
    public boolean supprimerArticleDuPanier(int idClient, int idArticle) {
        String sql = "DELETE FROM panier WHERE id_client = ? AND id_article = ?";

        try (Connection conn = ConnexionBDD.getConnexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idClient);
            ps.setInt(2, idArticle);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Erreur suppression article panier : " + e.getMessage());
            return false;
        }
    }
}

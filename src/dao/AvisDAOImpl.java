package dao;

import modele.Avis;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implémentation DAO pour la gestion des avis sur les articles.
 * <p>
 * Cette classe permet d'ajouter, modifier, supprimer et récupérer des avis
 * d'utilisateurs concernant les articles du catalogue.
 */
public class AvisDAOImpl implements AvisDAO {

    /**
     * Ajoute un nouvel avis dans la base de données.
     *
     * @param avis l'avis à ajouter
     * @return true si l'ajout a réussi, false sinon
     */
    @Override
    public boolean ajouterAvis(Avis avis) {
        String sql = "INSERT INTO avis (id_utilisateur, id_article, note, commentaire, date_avis) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConnexionBDD.getConnexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, avis.getIdUtilisateur());
            ps.setInt(2, avis.getIdArticle());
            ps.setInt(3, avis.getNote());
            ps.setString(4, avis.getCommentaire());
            ps.setTimestamp(5, Timestamp.valueOf(avis.getDateAvis()));

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Erreur ajout avis : " + e.getMessage());
            return false;
        }
    }

    /**
     * Modifie un avis existant dans la base de données.
     *
     * @param avis l'avis à modifier
     * @return true si la modification a réussi, false sinon
     */
    @Override
    public boolean modifierAvis(Avis avis) {
        String sql = "UPDATE avis SET note = ?, commentaire = ?, date_avis = ? WHERE id = ?";

        try (Connection conn = ConnexionBDD.getConnexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, avis.getNote());
            ps.setString(2, avis.getCommentaire());
            ps.setTimestamp(3, Timestamp.valueOf(avis.getDateAvis()));
            ps.setInt(4, avis.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Erreur modification avis : " + e.getMessage());
            return false;
        }
    }

    /**
     * Supprime un avis de la base de données en fonction de son identifiant.
     *
     * @param id l'identifiant de l'avis à supprimer
     * @return true si la suppression a réussi, false sinon
     */
    @Override
    public boolean supprimerAvis(int id) {
        String sql = "DELETE FROM avis WHERE id = ?";

        try (Connection conn = ConnexionBDD.getConnexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Erreur suppression avis : " + e.getMessage());
            return false;
        }
    }

    /**
     * Récupère un avis spécifique par son identifiant.
     *
     * @param id l'identifiant de l'avis
     * @return l'avis correspondant ou null si non trouvé
     */
    @Override
    public Avis getAvisParId(int id) {
        String sql = "SELECT * FROM avis WHERE id = ?";

        try (Connection conn = ConnexionBDD.getConnexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Avis(
                        rs.getInt("id"),
                        rs.getInt("id_utilisateur"),
                        rs.getInt("id_article"),
                        rs.getInt("note"),
                        rs.getString("commentaire"),
                        rs.getTimestamp("date_avis").toLocalDateTime()
                );
            }

        } catch (SQLException e) {
            System.err.println("Erreur récupération avis par ID : " + e.getMessage());
        }
        return null;
    }

    public List<Avis> getToutesLesAvis() {
        List<Avis> avisList = new ArrayList<>();
        String sql = "SELECT * FROM avis";

        try (Connection conn = ConnexionBDD.getConnexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Avis avis = new Avis(
                        rs.getInt("id"),
                        rs.getInt("id_utilisateur"),
                        rs.getInt("id_article"),
                        rs.getInt("note"),
                        rs.getString("commentaire"),
                        rs.getTimestamp("date_avis").toLocalDateTime()
                );
                avisList.add(avis);
            }

        } catch (SQLException e) {
            System.err.println("Erreur récupération de tous les avis : " + e.getMessage());
        }

        return avisList;
    }


    /**
     * Récupère tous les avis associés à un article spécifique.
     *
     * @param idArticle l'identifiant de l'article
     * @return la liste des avis pour cet article
     */
    @Override
    public List<Avis> getAvisParArticle(int idArticle) {
        List<Avis> avisList = new ArrayList<>();
        String sql = "SELECT * FROM avis WHERE id_article = ?";

        try (Connection conn = ConnexionBDD.getConnexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idArticle);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Avis avis = new Avis(
                        rs.getInt("id"),
                        rs.getInt("id_utilisateur"),
                        rs.getInt("id_article"),
                        rs.getInt("note"),
                        rs.getString("commentaire"),
                        rs.getTimestamp("date_avis").toLocalDateTime()
                );
                avisList.add(avis);
            }

        } catch (SQLException e) {
            System.err.println("Erreur récupération avis par article : " + e.getMessage());
        }

        return avisList;
    }

    /**
     * Récupère tous les avis donnés par un utilisateur spécifique.
     *
     * @param idUtilisateur l'identifiant de l'utilisateur
     * @return la liste des avis de cet utilisateur
     */
    @Override
    public List<Avis> getAvisParUtilisateur(int idUtilisateur) {
        List<Avis> avisList = new ArrayList<>();
        String sql = "SELECT * FROM avis WHERE id_utilisateur = ?";

        try (Connection conn = ConnexionBDD.getConnexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idUtilisateur);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Avis avis = new Avis(
                        rs.getInt("id"),
                        rs.getInt("id_utilisateur"),
                        rs.getInt("id_article"),
                        rs.getInt("note"),
                        rs.getString("commentaire"),
                        rs.getTimestamp("date_avis").toLocalDateTime()
                );
                avisList.add(avis);
            }

        } catch (SQLException e) {
            System.err.println("Erreur récupération avis par utilisateur : " + e.getMessage());
        }

        return avisList;
    }
}

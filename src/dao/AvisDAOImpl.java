package dao;

import modele.Avis;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implémentation DAO pour la gestion des avis
 */
public class AvisDAOImpl implements AvisDAO {

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

package dao;

import modele.Remise;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

/**
 * Implémentation DAO pour la gestion des remises promotionnelles.
 * <p>
 * Permet d'ajouter, modifier, supprimer et rechercher des remises
 * appliquées aux articles de la boutique.
 */
public class RemiseDAOImpl implements RemiseDAO {

    /**
     * Ajoute une nouvelle remise à la base de données.
     *
     * @param remise la remise à ajouter
     * @return true si l'ajout a réussi, false sinon
     */
    @Override
    public boolean ajouterRemise(Remise remise) {
        String sql = "INSERT INTO remise (id_article, code, pourcentage, date_debut, date_fin) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConnexionBDD.getConnexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, remise.getIdArticle());
            ps.setString(2, remise.getCode());
            ps.setInt(3, remise.getPourcentage());
            ps.setDate(4, Date.valueOf(remise.getDateDebut()));
            ps.setDate(5, Date.valueOf(remise.getDateFin()));

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Erreur ajout remise : " + e.getMessage());
            return false;
        }
    }

    /**
     * Modifie une remise existante.
     *
     * @param remise la remise à modifier
     * @return true si la modification a réussi, false sinon
     */
    @Override
    public boolean modifierRemise(Remise remise) {
        String sql = "UPDATE remise SET id_article = ?, code = ?, pourcentage = ?, date_debut = ?, date_fin = ? WHERE id = ?";

        try (Connection conn = ConnexionBDD.getConnexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, remise.getIdArticle());
            ps.setString(2, remise.getCode());
            ps.setInt(3, remise.getPourcentage());
            ps.setDate(4, Date.valueOf(remise.getDateDebut()));
            ps.setDate(5, Date.valueOf(remise.getDateFin()));
            ps.setInt(6, remise.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Erreur modification remise : " + e.getMessage());
            return false;
        }
    }

    /**
     * Supprime une remise de la base de données.
     *
     * @param id l'identifiant de la remise à supprimer
     * @return true si la suppression a réussi, false sinon
     */
    @Override
    public boolean supprimerRemise(int id) {
        String sql = "DELETE FROM remise WHERE id = ?";

        try (Connection conn = ConnexionBDD.getConnexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Erreur suppression remise : " + e.getMessage());
            return false;
        }
    }

    /**
     * Récupère une remise par son identifiant.
     *
     * @param id l'identifiant de la remise
     * @return la remise correspondante, ou null si non trouvée
     */
    @Override
    public Remise getRemiseParId(int id) {
        String sql = "SELECT * FROM remise WHERE id = ?";

        try (Connection conn = ConnexionBDD.getConnexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Remise(
                        rs.getInt("id"),
                        rs.getInt("id_article"),
                        rs.getString("code"),
                        rs.getInt("pourcentage"),
                        rs.getDate("date_debut").toLocalDate(),
                        rs.getDate("date_fin").toLocalDate()
                );
            }

        } catch (SQLException e) {
            System.err.println("Erreur récupération remise par ID : " + e.getMessage());
        }
        return null;
    }

    /**
     * Récupère toutes les remises existantes.
     *
     * @return une liste de toutes les remises
     */
    @Override
    public List<Remise> getToutesLesRemises() {
        List<Remise> liste = new ArrayList<>();
        String sql = "SELECT * FROM remise";

        try (Connection conn = ConnexionBDD.getConnexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Remise r = new Remise(
                        rs.getInt("id"),
                        rs.getInt("id_article"),
                        rs.getString("code"),
                        rs.getInt("pourcentage"),
                        rs.getDate("date_debut").toLocalDate(),
                        rs.getDate("date_fin").toLocalDate()
                );
                liste.add(r);
            }

        } catch (SQLException e) {
            System.err.println("Erreur récupération remises : " + e.getMessage());
        }

        return liste;
    }

    /**
     * Récupère toutes les remises appliquées à un article spécifique.
     *
     * @param idArticle l'identifiant de l'article
     * @return une liste des remises associées
     */
    @Override
    public List<Remise> getRemisesParArticle(int idArticle) {
        List<Remise> remises = new ArrayList<>();
        String sql = "SELECT * FROM remise WHERE id_article = ?";

        try (Connection conn = ConnexionBDD.getConnexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idArticle);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Remise r = new Remise(
                        rs.getInt("id"),
                        rs.getInt("id_article"),
                        rs.getString("code"),
                        rs.getInt("pourcentage"),
                        rs.getDate("date_debut").toLocalDate(),
                        rs.getDate("date_fin").toLocalDate()
                );
                remises.add(r);
            }

        } catch (SQLException e) {
            System.err.println("Erreur récupération remises par article : " + e.getMessage());
        }

        return remises;
    }

    /**
     * Récupère une remise spécifique grâce à son code.
     *
     * @param code le code promotionnel
     * @return la remise correspondante, ou null si non trouvée
     */
    @Override
    public Remise getRemiseParCode(String code) {
        String sql = "SELECT * FROM remise WHERE code = ?";

        try (Connection conn = ConnexionBDD.getConnexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, code);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Remise(
                        rs.getInt("id"),
                        rs.getInt("id_article"),
                        rs.getString("code"),
                        rs.getInt("pourcentage"),
                        rs.getDate("date_debut").toLocalDate(),
                        rs.getDate("date_fin").toLocalDate()
                );
            }

        } catch (SQLException e) {
            System.err.println("Erreur récupération remise par code : " + e.getMessage());
        }

        return null;
    }

    /**
     * Vérifie si un code promo est encore valide à la date actuelle.
     *
     * @param code le code promotionnel à vérifier
     * @return la remise si elle est valide, null sinon
     */
    @Override
    public Remise getRemiseValide(String code) {
        String sql = "SELECT * FROM remise WHERE code = ? AND date_fin >= ?";

        try (Connection conn = ConnexionBDD.getConnexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, code);
            ps.setDate(2, Date.valueOf(LocalDate.now()));
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Remise(
                        rs.getInt("id"),
                        rs.getInt("id_article"),
                        rs.getString("code"),
                        rs.getInt("pourcentage"),
                        rs.getDate("date_debut").toLocalDate(),
                        rs.getDate("date_fin").toLocalDate()
                );
            }

        } catch (SQLException e) {
            System.err.println("Erreur récupération remise valide : " + e.getMessage());
        }

        return null;
    }
}

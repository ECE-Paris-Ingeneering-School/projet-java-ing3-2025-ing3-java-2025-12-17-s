package dao;

import modele.Remise;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implémentation DAO pour la gestion des remises
 */
public class RemiseDAOImpl implements RemiseDAO {

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
}

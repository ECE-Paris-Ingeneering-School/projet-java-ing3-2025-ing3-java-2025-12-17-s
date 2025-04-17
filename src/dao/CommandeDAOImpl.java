package dao;

import modele.Commande;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Implémentation DAO pour les commandes
 */
public class CommandeDAOImpl implements CommandeDAO {

    @Override
    public boolean ajouterCommande(Commande commande) {
        String sql = "INSERT INTO commande (id_utilisateur, date_commande, statut) VALUES (?, ?, ?)";

        try (Connection conn = ConnexionBDD.getConnexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, commande.getIdUtilisateur());
            ps.setTimestamp(2, Timestamp.valueOf(commande.getDateCommande()));
            ps.setString(3, commande.getStatut());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Erreur ajout commande : " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean modifierCommande(Commande commande) {
        String sql = "UPDATE commande SET id_utilisateur = ?, date_commande = ?, statut = ? WHERE id = ?";

        try (Connection conn = ConnexionBDD.getConnexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, commande.getIdUtilisateur());
            ps.setTimestamp(2, Timestamp.valueOf(commande.getDateCommande()));
            ps.setString(3, commande.getStatut());
            ps.setInt(4, commande.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Erreur modification commande : " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean supprimerCommande(int id) {
        String sql = "DELETE FROM commande WHERE id = ?";

        try (Connection conn = ConnexionBDD.getConnexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Erreur suppression commande : " + e.getMessage());
            return false;
        }
    }

    @Override
    public Commande getCommandeParId(int id) {
        String sql = "SELECT * FROM commande WHERE id = ?";

        try (Connection conn = ConnexionBDD.getConnexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Commande(
                        rs.getInt("id"),
                        rs.getInt("id_utilisateur"),
                        rs.getTimestamp("date_commande").toLocalDateTime(),
                        rs.getString("statut")
                );
            }

        } catch (SQLException e) {
            System.err.println("Erreur récupération commande par ID : " + e.getMessage());
        }

        return null;
    }

    @Override
    public List<Commande> getToutesLesCommandes() {
        List<Commande> liste = new ArrayList<>();
        String sql = "SELECT * FROM commande";

        try (Connection conn = ConnexionBDD.getConnexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Commande c = new Commande(
                        rs.getInt("id"),
                        rs.getInt("id_utilisateur"),
                        rs.getTimestamp("date_commande").toLocalDateTime(),
                        rs.getString("statut")
                );
                liste.add(c);
            }

        } catch (SQLException e) {
            System.err.println("Erreur récupération commandes : " + e.getMessage());
        }

        return liste;
    }

    @Override
    public List<Commande> getCommandesParUtilisateur(int idUtilisateur) {
        List<Commande> liste = new ArrayList<>();
        String sql = "SELECT * FROM commande WHERE id_utilisateur = ?";

        try (Connection conn = ConnexionBDD.getConnexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idUtilisateur);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Commande c = new Commande(
                        rs.getInt("id"),
                        rs.getInt("id_utilisateur"),
                        rs.getTimestamp("date_commande").toLocalDateTime(),
                        rs.getString("statut")
                );
                liste.add(c);
            }

        } catch (SQLException e) {
            System.err.println("Erreur récupération commandes par utilisateur : " + e.getMessage());
        }

        return liste;
    }
}

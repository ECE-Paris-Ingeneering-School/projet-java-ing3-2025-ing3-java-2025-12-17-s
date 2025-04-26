package dao;

import modele.Commande;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Implémentation DAO pour la gestion des commandes.
 * <p>
 * Cette classe permet d'ajouter, modifier, supprimer et récupérer des commandes
 * passées par les utilisateurs dans la base de données.
 */
public class CommandeDAOImpl implements CommandeDAO {

    /**
     * Ajoute une nouvelle commande à la base de données.
     * Récupère l'identifiant généré pour la commande.
     *
     * @param commande la commande à ajouter
     * @return true si l'ajout a réussi, false sinon
     */
    @Override
    public boolean ajouterCommande(Commande commande) {
        String sql = "INSERT INTO commande (id_utilisateur, montant, date_commande, statut) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConnexionBDD.getConnexion();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, commande.getIdUtilisateur());
            ps.setDouble(2, commande.getMontant());
            ps.setTimestamp(3, Timestamp.valueOf(commande.getDateCommande()));
            ps.setString(4, commande.getStatut());

            int result = ps.executeUpdate();

            // Récupération de l'ID généré pour la commande
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                commande.setId(rs.getInt(1));
            }

            return result > 0;

        } catch (SQLException e) {
            System.err.println("Erreur ajout commande : " + e.getMessage());
            return false;
        }
    }

    /**
     * Modifie une commande existante dans la base de données.
     *
     * @param commande la commande à modifier
     * @return true si la modification a réussi, false sinon
     */
    @Override
    public boolean modifierCommande(Commande commande) {
        String sql = "UPDATE commande SET id_utilisateur = ?, montant = ?, date_commande = ?, statut = ? WHERE id = ?";

        try (Connection conn = ConnexionBDD.getConnexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, commande.getIdUtilisateur());
            ps.setDouble(2, commande.getMontant());
            ps.setTimestamp(3, Timestamp.valueOf(commande.getDateCommande()));
            ps.setString(4, commande.getStatut());
            ps.setInt(5, commande.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Erreur modification commande : " + e.getMessage());
            return false;
        }
    }

    /**
     * Supprime une commande de la base de données.
     *
     * @param id l'identifiant de la commande à supprimer
     * @return true si la suppression a réussi, false sinon
     */
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

    /**
     * Récupère une commande par son identifiant.
     *
     * @param id l'identifiant de la commande
     * @return la commande correspondante, ou null si non trouvée
     */
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
                        rs.getDouble("montant"),
                        rs.getTimestamp("date_commande").toLocalDateTime(),
                        rs.getString("statut")
                );
            }

        } catch (SQLException e) {
            System.err.println("Erreur récupération commande par ID : " + e.getMessage());
        }

        return null;
    }

    /**
     * Récupère toutes les commandes présentes dans la base de données.
     *
     * @return une liste de toutes les commandes
     */
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
                        rs.getDouble("montant"),
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

    /**
     * Récupère toutes les commandes passées par un utilisateur spécifique.
     *
     * @param idUtilisateur l'identifiant de l'utilisateur
     * @return une liste des commandes passées par cet utilisateur
     */
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
                        rs.getDouble("montant"),
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

package dao;

import modele.Utilisateur;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implémentation des méthodes DAO pour les utilisateurs
 */
public class UtilisateurDAOImpl implements UtilisateurDAO {

    @Override
    public boolean ajouterUtilisateur(Utilisateur utilisateur) {
        String sql = "INSERT INTO utilisateur (nom, prenom, email, motDePasse, role) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConnexionBDD.getConnexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, utilisateur.getNom());
            ps.setString(2, utilisateur.getPrenom());
            ps.setString(3, utilisateur.getEmail());
            ps.setString(4, utilisateur.getMotDePasse());
            ps.setString(5, utilisateur.getRole());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Erreur ajout utilisateur : " + e.getMessage());
            return false;
        }
    }

    @Override
    public Utilisateur getUtilisateurParId(int id) {
        String sql = "SELECT * FROM utilisateur WHERE id = ?";
        try (Connection conn = ConnexionBDD.getConnexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Utilisateur(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("motDePasse"),
                        rs.getString("role")
                );
            }

        } catch (SQLException e) {
            System.err.println("Erreur getUtilisateurParId : " + e.getMessage());
        }
        return null;
    }

    @Override
    public Utilisateur getUtilisateurParEmailEtMotDePasse(String email, String motDePasse) {
        String sql = "SELECT * FROM utilisateur WHERE email = ? AND motDePasse = ?";
        try (Connection conn = ConnexionBDD.getConnexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, motDePasse);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Utilisateur(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("motDePasse"),
                        rs.getString("role")
                );
            }

        } catch (SQLException e) {
            System.err.println("Erreur connexion utilisateur : " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Utilisateur> getTousLesUtilisateurs() {
        List<Utilisateur> liste = new ArrayList<>();
        String sql = "SELECT * FROM utilisateur";

        try (Connection conn = ConnexionBDD.getConnexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Utilisateur u = new Utilisateur(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("motDePasse"),
                        rs.getString("role")
                );
                liste.add(u);
            }

        } catch (SQLException e) {
            System.err.println("Erreur récupération utilisateurs : " + e.getMessage());
        }
        return liste;
    }

    @Override
    public boolean supprimerUtilisateur(int id) {
        String sql = "DELETE FROM utilisateur WHERE id = ?";
        try (Connection conn = ConnexionBDD.getConnexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Erreur suppression utilisateur : " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean modifierUtilisateur(Utilisateur utilisateur) {
        String sql = "UPDATE utilisateur SET nom = ?, prenom = ?, email = ?, motDePasse = ?, role = ? WHERE id = ?";
        try (Connection conn = ConnexionBDD.getConnexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, utilisateur.getNom());
            ps.setString(2, utilisateur.getPrenom());
            ps.setString(3, utilisateur.getEmail());
            ps.setString(4, utilisateur.getMotDePasse());
            ps.setString(5, utilisateur.getRole());
            ps.setInt(6, utilisateur.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Erreur modification utilisateur : " + e.getMessage());
            return false;
        }
    }
}

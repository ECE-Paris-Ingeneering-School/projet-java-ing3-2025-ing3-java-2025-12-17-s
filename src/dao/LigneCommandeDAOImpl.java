package dao;

import modele.LigneCommande;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implémentation DAO pour les lignes de commande
 */
public class LigneCommandeDAOImpl implements LigneCommandeDAO {

    @Override
    public boolean ajouterLigneCommande(LigneCommande ligne) {
        String sql = "INSERT INTO ligne_commande (id_commande, id_article, quantite) VALUES (?, ?, ?)";

        try (Connection conn = ConnexionBDD.getConnexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, ligne.getIdCommande());
            ps.setInt(2, ligne.getIdArticle());
            ps.setInt(3, ligne.getQuantite());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Erreur ajout ligne commande : " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean supprimerLigneCommande(int idCommande, int idArticle) {
        String sql = "DELETE FROM ligne_commande WHERE id_commande = ? AND id_article = ?";

        try (Connection conn = ConnexionBDD.getConnexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idCommande);
            ps.setInt(2, idArticle);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Erreur suppression ligne commande : " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<LigneCommande> getLignesParCommande(int idCommande) {
        List<LigneCommande> lignes = new ArrayList<>();
        String sql = "SELECT * FROM ligne_commande WHERE id_commande = ?";

        try (Connection conn = ConnexionBDD.getConnexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idCommande);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                LigneCommande lc = new LigneCommande(
                        rs.getInt("id_commande"),
                        rs.getInt("id_article"),
                        rs.getInt("quantite")
                );
                lignes.add(lc);
            }

        } catch (SQLException e) {
            System.err.println("Erreur récupération lignes de commande : " + e.getMessage());
        }

        return lignes;
    }

    @Override
    public boolean supprimerLignesParCommande(int idCommande) {
        String sql = "DELETE FROM ligne_commande WHERE id_commande = ?";

        try (Connection conn = ConnexionBDD.getConnexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idCommande);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Erreur suppression lignes commande : " + e.getMessage());
            return false;
        }
    }
}

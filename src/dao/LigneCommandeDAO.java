package dao;

import modele.LigneCommande;

import java.util.List;

/**
 * Interface DAO pour les lignes de commande
 */
public interface LigneCommandeDAO {

    boolean ajouterLigneCommande(LigneCommande ligne);

    boolean supprimerLigneCommande(int idCommande, int idArticle);

    List<LigneCommande> getLignesParCommande(int idCommande);

    boolean supprimerLignesParCommande(int idCommande);

    // 🔥 Méthode ajoutée pour récupérer toutes les lignes de commandes
    List<LigneCommande> getToutesLesLignesCommandes();
}

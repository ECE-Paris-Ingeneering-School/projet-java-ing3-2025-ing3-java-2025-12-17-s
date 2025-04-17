package dao;

import modele.LigneCommande;
import java.util.List;

/**
 * Interface DAO pour les lignes de commande
 */
public interface LigneCommandeDAO {

    // Ajouter une ligne de commande
    boolean ajouterLigneCommande(LigneCommande ligne);

    // Supprimer une ligne (utile si suppression d'article ou commande)
    boolean supprimerLigneCommande(int idCommande, int idArticle);

    // Récupérer toutes les lignes d'une commande
    List<LigneCommande> getLignesParCommande(int idCommande);

    // Supprimer toutes les lignes liées à une commande
    boolean supprimerLignesParCommande(int idCommande);
}

package dao;

import modele.Commande;
import java.util.List;

/**
 * Interface DAO pour les commandes
 */
public interface CommandeDAO {

    // Ajouter une commande
    boolean ajouterCommande(Commande commande);

    // Modifier le statut d'une commande
    boolean modifierCommande(Commande commande);

    // Supprimer une commande par ID
    boolean supprimerCommande(int id);

    // Obtenir une commande par son ID
    Commande getCommandeParId(int id);

    // Obtenir toutes les commandes
    List<Commande> getToutesLesCommandes();

    // Obtenir les commandes d’un utilisateur
    List<Commande> getCommandesParUtilisateur(int idUtilisateur);
}

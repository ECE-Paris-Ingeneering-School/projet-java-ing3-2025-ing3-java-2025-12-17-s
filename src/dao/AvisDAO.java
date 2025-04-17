package dao;

import modele.Avis;
import java.util.List;

/**
 * Interface DAO pour les avis
 */
public interface AvisDAO {

    // Ajouter un avis
    boolean ajouterAvis(Avis avis);

    // Modifier un avis
    boolean modifierAvis(Avis avis);

    // Supprimer un avis par ID
    boolean supprimerAvis(int id);

    // Obtenir un avis par ID
    Avis getAvisParId(int id);

    // Obtenir tous les avis d'un article
    List<Avis> getAvisParArticle(int idArticle);

    // Obtenir tous les avis d'un utilisateur
    List<Avis> getAvisParUtilisateur(int idUtilisateur);
}

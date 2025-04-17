package dao;

import modele.Remise;
import java.util.List;

/**
 * Interface DAO pour les remises sur articles
 */
public interface RemiseDAO {

    // Ajouter une remise
    boolean ajouterRemise(Remise remise);

    // Modifier une remise
    boolean modifierRemise(Remise remise);

    // Supprimer une remise par ID
    boolean supprimerRemise(int id);

    // Obtenir une remise par ID
    Remise getRemiseParId(int id);

    // Obtenir toutes les remises
    List<Remise> getToutesLesRemises();

    // Obtenir les remises d'un article
    List<Remise> getRemisesParArticle(int idArticle);
}

package dao;

import modele.Utilisateur;
import java.util.List;

/**
 * Interface DAO pour les utilisateurs
 */
public interface UtilisateurDAO {

    // Ajouter un utilisateur
    boolean ajouterUtilisateur(Utilisateur utilisateur);

    // Récupérer un utilisateur par son ID
    Utilisateur getUtilisateurParId(int id);

    // Récupérer un utilisateur par email/mot de passe (connexion)
    Utilisateur getUtilisateurParEmailEtMotDePasse(String email, String motDePasse);

    // Lister tous les utilisateurs
    List<Utilisateur> getTousLesUtilisateurs();

    // Supprimer un utilisateur
    boolean supprimerUtilisateur(int id);

    // Mettre à jour un utilisateur
    boolean modifierUtilisateur(Utilisateur utilisateur);


}

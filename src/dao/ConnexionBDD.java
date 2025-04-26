package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe utilitaire pour établir une connexion avec la base de données MySQL.
 * <p>
 * Utilise JDBC pour se connecter à la base 'shopping' en local.
 */
public class ConnexionBDD {

    // URL de la base de données
    private static final String URL = "jdbc:mysql://localhost:3306/shopping";

    // Identifiant de connexion
    private static final String UTILISATEUR = "root";

    // Mot de passe (laisser vide si aucun mot de passe en local)
    private static final String MDP = ""; // ← Mets ton mot de passe WAMP ici si nécessaire

    /**
     * Obtient une connexion JDBC active à la base de données.
     *
     * @return une connexion ouverte vers la base de données
     * @throws SQLException si la connexion échoue
     */
    public static Connection getConnexion() throws SQLException {
        return DriverManager.getConnection(URL, UTILISATEUR, MDP);
    }
}

package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnexionBDD {
    private static final String URL = "jdbc:mysql://localhost:3306/shopping";
    private static final String UTILISATEUR = "root";
    private static final String MDP = ""; // ← Mets ton mot de passe WAMP ici si nécessaire

    public static Connection getConnexion() throws SQLException {
        return DriverManager.getConnection(URL, UTILISATEUR, MDP);
    }
}

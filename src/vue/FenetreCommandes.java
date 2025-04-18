package vue;

import javax.swing.*;

public class FenetreCommandes extends JFrame {

    public FenetreCommandes(int idUtilisateur) {
        setTitle("Mes commandes");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // TODO : Afficher les commandes du client via idUtilisateur
        JLabel label = new JLabel("Historique des commandes de l'utilisateur : " + idUtilisateur);
        add(label);
    }
}

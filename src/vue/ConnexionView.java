package vue;

import dao.UtilisateurDAOImpl;
import modele.Utilisateur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Vue de la connexion pour les utilisateurs
 */
public class ConnexionView {

    private JFrame frame;
    private JTextField emailField;
    private JPasswordField motDePasseField;

    public ConnexionView() {
        frame = new JFrame("Connexion");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(4, 2));

        // Titre
        JLabel titleLabel = new JLabel("Connexion", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        frame.add(titleLabel);

        // Email
        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField();
        frame.add(emailLabel);
        frame.add(emailField);

        // Mot de passe
        JLabel motDePasseLabel = new JLabel("Mot de passe:");
        motDePasseField = new JPasswordField();
        frame.add(motDePasseLabel);
        frame.add(motDePasseField);

        // Bouton de connexion
        JButton loginButton = new JButton("Se connecter");
        frame.add(new JLabel()); // Empty label for spacing
        frame.add(loginButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText();
                String motDePasse = new String(motDePasseField.getPassword());

                UtilisateurDAOImpl utilisateurDAO = new UtilisateurDAOImpl();
                Utilisateur utilisateur = utilisateurDAO.getUtilisateurParEmailEtMotDePasse(email, motDePasse);

                if (utilisateur != null) {
                    JOptionPane.showMessageDialog(frame, "Bienvenue, " + utilisateur.getNom() + " !");
                    // Pour la suite, rediriger vers le catalogue, par exemple.
                } else {
                    JOptionPane.showMessageDialog(frame, "Identifiants incorrects !", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new ConnexionView();
    }
}

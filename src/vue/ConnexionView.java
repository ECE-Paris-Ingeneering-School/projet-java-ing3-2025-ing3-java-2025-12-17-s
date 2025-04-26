package vue;

import dao.UtilisateurDAOImpl;
import modele.Utilisateur;

import javax.swing.*;
import java.awt.*;

/**
 * Fenêtre de connexion pour les utilisateurs
 */
public class ConnexionView extends JFrame {

    private JTextField emailField;
    private JPasswordField motDePasseField;
    private JButton btnConnexion;
    private JButton btnCreerCompte;

    public ConnexionView() {
        setTitle("Connexion");
        setSize(400, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel titre = new JLabel("Se connecter");
        titre.setFont(new Font("Arial", Font.BOLD, 22));
        titre.setHorizontalAlignment(SwingConstants.CENTER);
        add(titre, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10)); // 4 lignes maintenant
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        formPanel.add(new JLabel("Email :"));
        emailField = new JTextField();
        formPanel.add(emailField);

        formPanel.add(new JLabel("Mot de passe :"));
        motDePasseField = new JPasswordField();
        formPanel.add(motDePasseField);

        btnConnexion = new JButton("Connexion");
        btnCreerCompte = new JButton("Créer un compte");

        formPanel.add(btnConnexion);
        formPanel.add(btnCreerCompte);

        add(formPanel, BorderLayout.CENTER);

        // Action pour le bouton "Connexion"
        btnConnexion.addActionListener(e -> {
            String email = emailField.getText().trim();
            String mdp = new String(motDePasseField.getPassword()).trim();

            UtilisateurDAOImpl utilisateurDAO = new UtilisateurDAOImpl();
            Utilisateur utilisateur = utilisateurDAO.getUtilisateurParEmailEtMotDePasse(email, mdp);

            if (utilisateur != null) {
                JOptionPane.showMessageDialog(this, "Bienvenue " + utilisateur.getPrenom() + " !");
                dispose(); // ferme la fenêtre de connexion

                if (utilisateur.getRole().equalsIgnoreCase("admin")) {
                    new FenetreAdmin(utilisateur.getNom());
                } else {
                    new FenetreClient(utilisateur.getId()).setVisible(true);
                }

            } else {
                JOptionPane.showMessageDialog(this, "Identifiants incorrects", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Action pour le bouton "Créer un compte"
        btnCreerCompte.addActionListener(e -> {
            dispose(); // fermer la connexion
            new FenetreInscription(); // ouvrir l'inscription
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new ConnexionView();
    }
}

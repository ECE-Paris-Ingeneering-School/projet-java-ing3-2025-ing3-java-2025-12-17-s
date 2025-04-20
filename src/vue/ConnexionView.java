package vue;

import dao.UtilisateurDAOImpl;
import modele.Utilisateur;

import javax.swing.*;
import java.awt.*;

public class ConnexionView extends JFrame {

    private JTextField emailField;
    private JPasswordField motDePasseField;

    public ConnexionView() {
        setTitle("Connexion");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel titre = new JLabel("Se connecter");
        titre.setFont(new Font("Arial", Font.BOLD, 22));
        titre.setHorizontalAlignment(SwingConstants.CENTER);
        add(titre, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        formPanel.add(new JLabel("Email :"));
        emailField = new JTextField();
        formPanel.add(emailField);

        formPanel.add(new JLabel("Mot de passe :"));
        motDePasseField = new JPasswordField();
        formPanel.add(motDePasseField);

        JButton btnConnexion = new JButton("Connexion");
        formPanel.add(new JLabel()); // espace vide
        formPanel.add(btnConnexion);

        add(formPanel, BorderLayout.CENTER);

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
                    System.out.println("Connexion client réussie, ouverture FenetreClient");
                    new FenetreClient(utilisateur.getId()).setVisible(true);
                }

            } else {
                JOptionPane.showMessageDialog(this, "Identifiants incorrects", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new ConnexionView();
    }
}

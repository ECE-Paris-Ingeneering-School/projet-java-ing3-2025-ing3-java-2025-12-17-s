package vue;

import dao.UtilisateurDAO;
import dao.UtilisateurDAOImpl;
import modele.Utilisateur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Fenêtre d'inscription d'un nouveau client.
 */
public class FenetreInscription extends JFrame {

    private JTextField nomField;
    private JTextField prenomField;
    private JTextField emailField;
    private JPasswordField motDePasseField;
    private JButton inscrireButton;
    private JButton retourButton;
    private UtilisateurDAO utilisateurDAO;

    public FenetreInscription() {
        super("Inscription Client");
        utilisateurDAO = new UtilisateurDAOImpl();

        // Création des composants
        nomField = new JTextField(20);
        prenomField = new JTextField(20);
        emailField = new JTextField(20);
        motDePasseField = new JPasswordField(20);
        inscrireButton = new JButton("S'inscrire");
        retourButton = new JButton("Retour à la connexion");

        // Mise en page
        setLayout(new GridLayout(6, 2, 10, 10));
        add(new JLabel("Nom :"));
        add(nomField);
        add(new JLabel("Prénom :"));
        add(prenomField);
        add(new JLabel("Email :"));
        add(emailField);
        add(new JLabel("Mot de passe :"));
        add(motDePasseField);
        add(inscrireButton);
        add(retourButton);

        // Action bouton "S'inscrire"
        inscrireButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inscrireClient();
            }
        });

        // Action bouton "Retour à la connexion"
        retourButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new ConnexionView(); // Retour à la fenêtre de connexion
            }
        });

        // Paramètres fenêtre
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Inscrit un nouveau client en base de données.
     */
    private void inscrireClient() {
        String nom = nomField.getText();
        String prenom = prenomField.getText();
        String email = emailField.getText();
        String motDePasse = new String(motDePasseField.getPassword());

        if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || motDePasse.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Création d'un nouvel utilisateur
        Utilisateur nouvelUtilisateur = new Utilisateur();
        nouvelUtilisateur.setNom(nom);
        nouvelUtilisateur.setPrenom(prenom);
        nouvelUtilisateur.setEmail(email);
        nouvelUtilisateur.setMotDePasse(motDePasse);
        nouvelUtilisateur.setRole("client");

        // Enregistrement en base de données
        boolean success = utilisateurDAO.ajouterUtilisateur(nouvelUtilisateur);
        if (success) {
            JOptionPane.showMessageDialog(this, "Inscription réussie ! Vous pouvez vous connecter.");
            dispose();
            new ConnexionView();
        } else {
            JOptionPane.showMessageDialog(this, "Erreur lors de l'inscription. Essayez un autre email.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}

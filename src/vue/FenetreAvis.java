package vue;

import dao.AvisDAO;
import dao.AvisDAOImpl;
import modele.Avis;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;

/**
 * Fenêtre pour laisser un avis sur un article acheté.
 */
public class FenetreAvis extends JFrame {

    private int idUtilisateur;
    private int idArticle;

    private JComboBox<Integer> comboNote;
    private JTextArea textCommentaire;

    /**
     * Constructeur de la fenêtre d'avis.
     *
     * @param idUtilisateur l'identifiant de l'utilisateur
     * @param idArticle     l'identifiant de l'article
     */
    public FenetreAvis(int idUtilisateur, int idArticle) {
        this.idUtilisateur = idUtilisateur;
        this.idArticle = idArticle;

        setTitle("Laisser un avis");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panelCentre = new JPanel(new GridLayout(3, 1, 10, 10));

        JLabel lblNote = new JLabel("Note (1 à 5 étoiles) :");
        comboNote = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5});
        panelCentre.add(lblNote);
        panelCentre.add(comboNote);

        JLabel lblCommentaire = new JLabel("Commentaire :");
        textCommentaire = new JTextArea(5, 20);
        JScrollPane scrollCommentaire = new JScrollPane(textCommentaire);
        panelCentre.add(lblCommentaire);
        panelCentre.add(scrollCommentaire);

        add(panelCentre, BorderLayout.CENTER);

        JButton btnEnvoyer = new JButton("Envoyer l'avis");
        add(btnEnvoyer, BorderLayout.SOUTH);

        btnEnvoyer.addActionListener(e -> enregistrerAvis());

        setVisible(true);
    }

    /**
     * Enregistre l'avis dans la base de données.
     */
    private void enregistrerAvis() {
        int note = (Integer) comboNote.getSelectedItem();
        String commentaire = textCommentaire.getText().trim();

        if (commentaire.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez entrer un commentaire.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Avis avis = new Avis(0, idUtilisateur, idArticle, note, commentaire, LocalDateTime.now());
        AvisDAO avisDAO = new AvisDAOImpl();

        if (avisDAO.ajouterAvis(avis)) {
            JOptionPane.showMessageDialog(this, "Merci pour votre avis !");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Erreur lors de l'enregistrement.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}

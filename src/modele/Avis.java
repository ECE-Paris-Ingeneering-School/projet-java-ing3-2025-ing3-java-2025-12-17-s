package modele;

import java.time.LocalDateTime;

/**
 * Classe représentant un avis (note et commentaire) laissé par un utilisateur sur un article
 */
public class Avis {
    private int id;
    private int idUtilisateur;
    private int idArticle;
    private int note; // de 1 à 5
    private String commentaire;
    private LocalDateTime dateAvis;

    /**
     * Constructeur complet
     */
    public Avis(int id, int idUtilisateur, int idArticle, int note, String commentaire, LocalDateTime dateAvis) {
        this.id = id;
        this.idUtilisateur = idUtilisateur;
        this.idArticle = idArticle;
        this.note = note;
        this.commentaire = commentaire;
        this.dateAvis = dateAvis;
    }

    /**
     * Constructeur sans ID (insertion)
     */
    public Avis(int idUtilisateur, int idArticle, int note, String commentaire, LocalDateTime dateAvis) {
        this.idUtilisateur = idUtilisateur;
        this.idArticle = idArticle;
        this.note = note;
        this.commentaire = commentaire;
        this.dateAvis = dateAvis;
    }

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdUtilisateur() { return idUtilisateur; }
    public void setIdUtilisateur(int idUtilisateur) { this.idUtilisateur = idUtilisateur; }

    public int getIdArticle() { return idArticle; }
    public void setIdArticle(int idArticle) { this.idArticle = idArticle; }

    public int getNote() { return note; }
    public void setNote(int note) { this.note = note; }

    public String getCommentaire() { return commentaire; }
    public void setCommentaire(String commentaire) { this.commentaire = commentaire; }

    public LocalDateTime getDateAvis() { return dateAvis; }
    public void setDateAvis(LocalDateTime dateAvis) { this.dateAvis = dateAvis; }

    @Override
    public String toString() {
        return "Avis (" + note + "/5) : " + commentaire + " - Article #" + idArticle + " - Utilisateur #" + idUtilisateur;
    }
}

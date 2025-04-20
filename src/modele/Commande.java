package modele;

import java.time.LocalDateTime;

public class Commande {
    private int id;
    private int idUtilisateur;
    private double montant;
    private LocalDateTime dateCommande;
    private String statut;

    public Commande(int id, int idUtilisateur, double montant, LocalDateTime dateCommande, String statut) {
        this.id = id;
        this.idUtilisateur = idUtilisateur;
        this.montant = montant;
        this.dateCommande = dateCommande;
        this.statut = statut;
    }

    public Commande(int idUtilisateur, double montant, LocalDateTime dateCommande, String statut) {
        this.idUtilisateur = idUtilisateur;
        this.montant = montant;
        this.dateCommande = dateCommande;
        this.statut = statut;
    }

    // Getter & Setter
    public int getId() {
        return id;
    }

    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public double getMontant() {
        return montant;
    }

    public LocalDateTime getDateCommande() {
        return dateCommande;
    }

    public String getStatut() {
        return statut;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public void setDateCommande(LocalDateTime dateCommande) {
        this.dateCommande = dateCommande;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    @Override
    public String toString() {
        return "Commande n°" + id + " - " + dateCommande + " - Montant : " + montant + "€ - Statut : " + statut;
    }
}

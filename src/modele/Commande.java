package modele;

import java.time.LocalDateTime;

public class Commande {

    private int id;
    private int idUtilisateur;
    private double totalCommande;
    private LocalDateTime dateCommande;
    private String statut;

    // Constructeur
    public Commande(int idUtilisateur, double totalCommande, LocalDateTime dateCommande, String statut) {
        this.idUtilisateur = idUtilisateur;
        this.totalCommande = totalCommande;
        this.dateCommande = dateCommande;
        this.statut = statut;
    }

    // Getter pour id
    public int getId() {
        return id;
    }

    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public double getTotalCommande() {
        return totalCommande;
    }

    public LocalDateTime getDateCommande() {
        return dateCommande;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }
    public void setId(int id) {
        this.id = id;
    }
}

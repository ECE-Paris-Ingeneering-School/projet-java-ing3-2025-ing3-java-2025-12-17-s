package modele;

import java.time.LocalDate;

/**
 * Classe représentant une remise appliquée sur un article
 */
public class Remise {
    private int id;
    private int idArticle;
    private String code;
    private int pourcentage; // entre 0 et 100
    private LocalDate dateDebut;
    private LocalDate dateFin;

    /**
     * Constructeur complet
     */
    public Remise(int id, int idArticle, String code, int pourcentage, LocalDate dateDebut, LocalDate dateFin) {
        this.id = id;
        this.idArticle = idArticle;
        this.code = code;
        this.pourcentage = pourcentage;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    /**
     * Constructeur sans ID (insertion)
     */
    public Remise(int idArticle, String code, int pourcentage, LocalDate dateDebut, LocalDate dateFin) {
        this.idArticle = idArticle;
        this.code = code;
        this.pourcentage = pourcentage;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdArticle() { return idArticle; }
    public void setIdArticle(int idArticle) { this.idArticle = idArticle; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public int getPourcentage() { return pourcentage; }
    public void setPourcentage(int pourcentage) { this.pourcentage = pourcentage; }

    public LocalDate getDateDebut() { return dateDebut; }
    public void setDateDebut(LocalDate dateDebut) { this.dateDebut = dateDebut; }

    public LocalDate getDateFin() { return dateFin; }
    public void setDateFin(LocalDate dateFin) { this.dateFin = dateFin; }

    @Override
    public String toString() {
        return code + " (-" + pourcentage + "% du " + dateDebut + " au " + dateFin + ")";
    }
}

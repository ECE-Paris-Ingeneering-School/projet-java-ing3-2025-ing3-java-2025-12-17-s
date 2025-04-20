package modele;

/**
 * Classe représentant un article en vente
 */
public class Article {
    private int id;
    private String nom;
    private String description;
    private String marque;
    private double prixUnitaire;
    private double prixVrac;
    private int quantiteVrac;
    private int stock;

    private int quantite; // utilisé pour le panier

    // Constructeur complet (avec ID)
    public Article(int id, String nom, String marque, double prixUnitaire, double prixVrac, int quantiteVrac, int stock) {
        this.id = id;
        this.nom = nom;
        this.marque = marque;
        this.prixUnitaire = prixUnitaire;
        this.prixVrac = prixVrac;
        this.quantiteVrac = quantiteVrac;
        this.stock = stock;
    }

    // Constructeur sans ID (insertion)
    public Article(String nom, String marque, double prixUnitaire, double prixVrac, int quantiteVrac, int stock) {
        this.nom = nom;
        this.marque = marque;
        this.prixUnitaire = prixUnitaire;
        this.prixVrac = prixVrac;
        this.quantiteVrac = quantiteVrac;
        this.stock = stock;
    }

    // ✅ Constructeur pour PanierDAOImpl : utilisé pour récupérer un article avec tous ses attributs (version requise dans l’erreur)
    public Article(int id, String nom, double prixUnitaire, String marque, int stock, String description) {
        this.id = id;
        this.nom = nom;
        this.prixUnitaire = prixUnitaire;
        this.marque = marque;
        this.stock = stock;
        this.description = description;
    }

    // Constructeur pour le panier (utilisé précédemment)
    public Article(int id, String nom, String description, String marque, double prixUnitaire, int stock) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.marque = marque;
        this.prixUnitaire = prixUnitaire;
        this.stock = stock;
    }

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getMarque() { return marque; }
    public void setMarque(String marque) { this.marque = marque; }

    public double getPrixUnitaire() { return prixUnitaire; }
    public void setPrixUnitaire(double prixUnitaire) { this.prixUnitaire = prixUnitaire; }

    public double getPrixVrac() { return prixVrac; }
    public void setPrixVrac(double prixVrac) { this.prixVrac = prixVrac; }

    public int getQuantiteVrac() { return quantiteVrac; }
    public void setQuantiteVrac(int quantiteVrac) { this.quantiteVrac = quantiteVrac; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public int getQuantite() { return quantite; }
    public void setQuantite(int quantite) { this.quantite = quantite; }

    @Override
    public String toString() {
        return nom + " (" + marque + ") - " + prixUnitaire + "€ / " + prixVrac + "€ pour " + quantiteVrac;
    }
}

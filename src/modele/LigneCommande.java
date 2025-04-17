package modele;

/**
 * Classe représentant une ligne de commande (un article avec sa quantité dans une commande)
 */
public class LigneCommande {
    private int idCommande;
    private int idArticle;
    private int quantite;

    /**
     * Constructeur complet
     */
    public LigneCommande(int idCommande, int idArticle, int quantite) {
        this.idCommande = idCommande;
        this.idArticle = idArticle;
        this.quantite = quantite;
    }

    // Getters & Setters
    public int getIdCommande() { return idCommande; }
    public void setIdCommande(int idCommande) { this.idCommande = idCommande; }

    public int getIdArticle() { return idArticle; }
    public void setIdArticle(int idArticle) { this.idArticle = idArticle; }

    public int getQuantite() { return quantite; }
    public void setQuantite(int quantite) { this.quantite = quantite; }

    @Override
    public String toString() {
        return "Commande #" + idCommande + " - Article #" + idArticle + " - Quantité : " + quantite;
    }
}

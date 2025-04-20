package modele;

public class LigneCommande {
    private int idCommande;
    private int idArticle;
    private int quantite;

    public LigneCommande() {
    }

    public LigneCommande(int idCommande, int idArticle, int quantite) {
        this.idCommande = idCommande;
        this.idArticle = idArticle;
        this.quantite = quantite;
    }

    public int getIdCommande() {
        return idCommande;
    }

    public void setIdCommande(int idCommande) {
        this.idCommande = idCommande;
    }

    public int getIdArticle() {
        return idArticle;
    }

    public void setIdArticle(int idArticle) {
        this.idArticle = idArticle;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }
}

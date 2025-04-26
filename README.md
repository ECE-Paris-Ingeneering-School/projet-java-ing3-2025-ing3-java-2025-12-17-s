# 🛒 *Projet Java ING3 - Shopping App*

Bienvenue dans le projet de panier d’achat pour ING3 2025 !  
Ce dépôt contient l'application **Java** (MVC + DAO + JDBC) permettant aux clients de commander des articles et aux administrateurs de gérer l'inventaire, les remises et les statistiques.

---

## 🧑‍💻 *Équipe de développement*

| Prénom    | Branche Git |
|-----------|-------------|
| Nicolas   | `nicolas`   |
| Wandrille | `wandrille` |
| Gilles    | `gilles`    |
| Benjamin  | `benjamin`  |

---

## 📌 *Règles de collaboration Git*

1. **Ne jamais travailler directement sur** `main`
2. **Travailler uniquement sur votre branche personnelle**
3. **Faire des commits réguliers et clairs**
4. **Demander un merge vers `main` une fois le travail terminé**
5. **Avant un merge, vérifier que votre travail :**
   - Compile sans erreur ✅
   - Respecte l'architecture MVC / DAO 🏛️
   - A été testé manuellement 🔎

---

## 🗂️ *Architecture du projet*

- `src/modele` ➔ Classes métiers (ex: Client, Article, Commande…)
- `src/vue` ➔ Interfaces graphiques Swing
- `src/dao` ➔ Accès aux données (Base de données via JDBC)
- `src/utils` ➔ Fonctions utilitaires et génération de statistiques (JFreeChart)

---

## 🎯 *Fonctionnalités finales*

- 🔵 **Connexion et inscription client**
   - Interface sécurisée de connexion et création de compte
   - Vérification d'email unique

- 🔵 **Gestion complète du panier**
   - Ajout d'articles au panier avec quantité choisie
   - Contrôle automatique du stock
   - Suppression d’articles
   - Application automatique du prix vrac selon quantité

- 🔵 **Validation de commande**
   - Récapitulatif de la commande
   - Choix du mode de paiement (Carte, PayPal, Espèces)
   - Enregistrement sécurisé de la commande

- 🔵 **Gestion des remises et promotions**
   - Application automatique d’un code promo valide
   - Calcul dynamique des réductions

- 🔵 **Gestion de l'inventaire**
   - Mise à jour automatique du stock après achat
   - Ajout, modification et suppression d'articles par un administrateur

- 🔵 **Gestion des avis clients**
   - Ajout d'une note et d'un commentaire sur les articles achetés
   - Consultation et modification des avis existants

- 🔵 **Statistiques dynamiques (JFreeChart)**
   - Histogramme des articles les plus commandés
   - Chiffre d’affaires par article
   - Évolution du nombre de commandes par mois

- 🔵 **Interface graphique Swing responsive**
   - Navigation fluide et ergonomique
   - Mise à jour dynamique des vues et des données

---

## 🎯 *Objectif initial*

> Créer une **application e-commerce Java** complète basée sur **MVC / DAO / JDBC**, avec une **interface Swing**, une **gestion de base de données MySQL** et une **génération de statistiques visuelles**.

---

## ✅ *Projet finalisé avec succès !* 🚀

# AbonTracker  - Système de Suivi de Paiements par Abonnement
développement d'une application de gestion des abonnement avec java, programmation fonctionnelle, persistance a la base de données

##  Contexte
La multiplication des abonnements (personnels et professionnels) rend leur suivi complexe : 
- dates d’échéances multiples,
- paiements manqués,
- budget difficile à anticiper.

**SubTrack** propose une solution centralisée pour gérer les abonnements, suivre les paiements et générer des rapports financiers clairs.

---

##  Fonctionnalités
- Création et gestion des abonnements (avec ou sans engagement).
- Génération automatique des échéances de paiement.
- Enregistrement, modification et suppression des paiements.
- Détection des impayés et calcul du montant total non réglé.
- Affichage des paiements par abonnement.
- Rapports financiers :
  - Mensuels
  - Annuels
  - Liste des impayés
- Affichage de la somme payée par abonnement.
- Historique des 5 derniers paiements.
- Interface utilisateur en mode console.

---

##  Architecture
L’application suit une architecture en couches :
1. **UI (console/menu)** : interaction avec l’utilisateur.
2. **Services métier** : logique (génération d’échéances, détection d’impayés, rapports).
3. **Entities** : objets persistants (`Abonnement`, `Paiement`).
4. **DAO (JDBC + MySQL/PostgreSQL)** : persistance et accès aux données.
5. **Utilitaires** : gestion des dates, validations, formatage.

---

##  Technologies utilisées
- **Java 8**
- **JDBC**
- **MySQL** ou **PostgreSQL**
- **Stream API & Lambda**
- **Git/GitHub** pour le contrôle de version

---

##  Base de données
### Tables principales
- **Abonnement** : `id`, `nomService`, `montantMensuel`, `dateDebut`, `dateFin`, `statut`, `typeAbonnement`, `dureeEngagementMois`.
- **Paiement** : `idPaiement`, `idAbonnement`, `dateEcheance`, `datePaiement`, `typePaiement`, `statut`.

Relation **1..n** entre Abonnement et Paiement.

---

##  Critères de performance
- Application fonctionnelle et conforme aux exigences.
- Code propre, clair et bien commenté.
- Conformité avec les conventions Java.
- README complet et clair.
- Utilisation régulière de Git (commits significatifs).

---

##  Bonus prévu
- Sauvegarde des logs dans un fichier.
- Export des rapports en **CSV/JSON**.

---

##  Auteur
Projet développé par **[Nmissi Nadia]** dans le cadre d’un exercice sur la gestion des abonnements et paiements en Java.

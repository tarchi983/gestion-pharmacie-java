# Systeme de gestion d'une pharmacie

Application Java desktop permettant de gerer les operations principales d'une pharmacie : medicaments, clients, fournisseurs, ordonnances, ventes, commandes fournisseurs et suivi du stock.

## Informations du projet

| Element | Detail |
| --- | --- |
| Sujet | Sujet 4 : Systeme de gestion d'une pharmacie |
| Annee universitaire | 2025/2026 |
| Encadrant | Pr. Said El Kafhali |
| Type d'application | Application desktop |
| Langage | Java |
| Interface graphique | Java Swing |
| Base de donnees | MySQL |
| Acces aux donnees | JDBC |
| Architecture | Vue - Controleur - DAO - Modele |
| Classe principale | `Main.Main` |
| Fenetre principale | `view.GestionPharmacieView` |
| Module Java | `gestion.pharmacie` |
| Driver MySQL | `lib/mysql-connector-j-9.7.0.jar` |

## Membres du groupe

| Nom complet |
| --- |
| Oussama Tarchi |
| Ali Zouine |
| Taha Rajel |
| Abdessamad Lyamani |


## Presentation du sujet

La gestion d'une pharmacie demande une organisation precise entre plusieurs activites : la vente de medicaments, la gestion des stocks, le suivi des ordonnances, la relation avec les clients, les commandes fournisseurs et l'analyse des ventes. Une pharmacie doit connaitre en permanence les produits disponibles, les produits proches de la peremption, les medicaments en rupture ou en stock faible, ainsi que l'historique des transactions.

L'objectif de ce projet est de developper une application permettant de centraliser ces operations dans une interface graphique simple. Le pharmacien peut ainsi enregistrer les medicaments, suivre le stock, creer des ventes, gerer les ordonnances et commander les produits necessaires chez les fournisseurs.

Le systeme repond aux besoins suivants :

- enregistrer les informations generales de la pharmacie ;
- gerer les clients ;
- gerer les medicaments et leurs informations essentielles ;
- suivre la quantite disponible en stock ;
- detecter les produits en stock faible ;
- detecter les produits perimes ou proches de la peremption ;
- enregistrer les fournisseurs ;
- saisir les ordonnances ;
- creer des ventes avec ou sans ordonnance ;
- mettre a jour automatiquement le stock apres validation d'une vente ;
- creer et suivre les commandes fournisseurs ;
- mettre a jour le stock apres reception d'une livraison ;
- consulter les historiques des ventes et des commandes ;
- generer automatiquement une facture texte apres validation d'une vente ;
- analyser les ventes dans un tableau de bord dedie.

## Objectifs du projet

### Objectif general

L'objectif general est de fournir une application de gestion de pharmacie capable de simplifier le travail quotidien du pharmacien en automatisant les operations principales : gestion des produits, ventes, ordonnances, clients, fournisseurs et stock.

### Objectifs specifiques

- Concevoir une base de donnees relationnelle adaptee au domaine pharmaceutique.
- Creer une interface graphique Java Swing claire et exploitable.
- Separer le projet en couches pour faciliter la maintenance.
- Utiliser JDBC pour connecter l'application a MySQL.
- Permettre les operations CRUD sur les entites principales.
- Assurer le controle du stock pendant les ventes.
- Integrer les ordonnances dans le processus de vente.
- Gerer les commandes fournisseurs et la reception de livraisons.
- Generer une facture texte pour chaque vente validee.
- Proposer un module Dashboard pour analyser les ventes par produit, client et periode.

## Fonctionnalites principales

### Gestion de la pharmacie

Le module pharmacie permet d'enregistrer les informations propres a l'etablissement :

- nom ;
- adresse ;
- telephone ;
- email ;
- responsable.

Ces informations sont stockees dans la table `Pharmacie`. Le controleur verifie s'il existe deja une fiche pharmacie. Si elle n'existe pas, il cree un nouvel enregistrement. Si elle existe deja, il met a jour les informations existantes.

### Gestion des clients

Le module clients permet d'ajouter, modifier, supprimer et consulter les clients.

Informations gerees :

- identifiant ;
- nom ;
- prenom ;
- telephone ;
- adresse.

Fonctionnalites :

- ajouter un client ;
- modifier un client ;
- supprimer un client ;
- afficher la liste des clients ;
- chercher un client par identifiant ;
- consulter l'historique d'achats d'un client.

### Gestion des medicaments

Le module medicaments gere les produits vendus par la pharmacie. Chaque medicament possede les informations demandees par le sujet :

- nom commercial ;
- composition ;
- forme pharmaceutique ;
- dosage ;
- prix ;
- quantite en stock ;
- date de peremption ;
- seuil d'alerte.

Fonctionnalites :

- ajouter un medicament ;
- modifier un medicament ;
- supprimer un medicament ;
- rechercher un medicament par nom ;
- afficher tous les medicaments ;
- afficher les medicaments en stock faible ;
- afficher les medicaments perimes ;
- afficher les medicaments proches de la peremption ;
- augmenter ou diminuer le stock.

### Gestion des fournisseurs

Les fournisseurs representent les entreprises ou personnes qui approvisionnent la pharmacie.

Informations gerees :

- identifiant ;
- nom de societe ;
- contact ;
- adresse.

Fonctionnalites :

- ajouter un fournisseur ;
- modifier un fournisseur ;
- supprimer un fournisseur ;
- rechercher un fournisseur ;
- afficher la liste des fournisseurs.


### Gestion des ventes

Une vente peut etre faite avec ou sans ordonnance. Une vente contient :

- identifiant ;
- client ;
- ordonnance optionnelle ;
- date de vente ;
- lignes de vente.

Une ligne de vente contient :

- identifiant de la vente ;
- identifiant du medicament ;
- quantite ;
- prix unitaire ;
- total calcule.

Fonctionnalites :

- creer une vente ;
- ajouter des medicaments dans le panier ;
- calculer le total ;
- valider la vente ;
- annuler une vente en cours ;
- vider le panier ;
- verifier le stock disponible ;
- mettre a jour le stock apres validation ;
- generer une facture textuelle ;
- afficher l'historique des ventes.
- enregistrer automatiquement une facture dans le dossier `factures/`.

### Gestion des commandes fournisseurs

Une commande fournisseur permet de reapprovisionner la pharmacie.

Une commande contient :

- identifiant ;
- fournisseur ;
- date de commande ;
- statut ;
- lignes de commande.

Une ligne de commande contient :

- identifiant de la commande ;
- identifiant du medicament ;
- quantite commandee ;
- prix d'achat ;
- total calcule.

Fonctionnalites :

- creer une commande ;
- ajouter des lignes de commande ;
- calculer le total ;
- valider la commande ;
- changer le statut ;
- annuler une commande ;
- receptionner une livraison ;
- augmenter automatiquement le stock apres reception ;
- afficher l'historique des commandes.

### Gestion du stock

Le module stock permet de suivre l'etat global des medicaments.

Fonctionnalites :

- consulter le stock ;
- chercher un medicament par identifiant ;
- effectuer une entree de stock ;
- effectuer une sortie de stock ;
- afficher les medicaments en stock faible ;
- afficher les medicaments perimes ;
- afficher les medicaments proches de la date de peremption ;
- calculer la valeur totale du stock ;
- generer des alertes.

### Dashboard analytique

Le projet contient un module `Dashboard` qui repond a la partie analyse et reporting du sujet. Il centralise plusieurs indicateurs construits a partir des ventes deja enregistrees.

Fonctionnalites :

- calculer le chiffre d'affaires sur une periode choisie ;
- afficher les quantites vendues par produit ;
- afficher le total achete par client ;
- classer les produits les plus demandes ;
- actualiser les donnees depuis l'interface.


## Technologies utilisees

| Technologie | Role |
| --- | --- |
| Java | Langage principal |
| Java Swing | Interface graphique |
| JDBC | Communication avec MySQL |
| MySQL | Stockage des donnees |
| MySQL Connector/J | Driver JDBC |


## Structure du projet

```text
gestion_pharmacie2/
|-- src/
|   |-- Main/
|   |   `-- Main.java
|   |-- controller/
|   |   |-- ClientController.java
|   |   |-- CommandeController.java
|   |   |-- DashboardController.java
|   |   |-- FournisseurController.java
|   |   |-- MedicamentController.java
|   |   |-- OrdonnanceController.java
|   |   |-- PharmacieController.java
|   |   |-- StockController.java
|   |   `-- VenteController.java
|   |-- dao/
|   |   |-- ClientDao.java
|   |   |-- CommandeDao.java
|   |   |-- FournisseurDao.java
|   |   |-- MedicamentDao.java
|   |   |-- OrdonnanceDao.java
|   |   |-- PharmacieDao.java
|   |   |-- TestConnection.java
|   |   |-- Utilitaire.java
|   |   `-- VenteDao.java
|   |-- model/
|   |   |-- Client.java
|   |   |-- Commande.java
|   |   |-- Fournisseur.java
|   |   |-- LigneCommande.java
|   |   |-- LigneVente.java
|   |   |-- Medicament.java
|   |   |-- Ordonnance.java
|   |   |-- Pharmacie.java
|   |   `-- Vente.java
|   |-- view/
|   |   |-- ClientPanel.java
|   |   |-- CommandePanel.java
|   |   |-- DashboardPanel.java
|   |   |-- FournisseurPanel.java
|   |   |-- GestionPharmacieView.java
|   |   |-- MedicamentPanel.java
|   |   |-- OrdonnancePanel.java
|   |   |-- PharmaciePanel.java
|   |   |-- StockPanel.java
|   |   |-- VentePanel.java
|   |   `-- ViewUtil.java
|   `-- module-info.java
|-- lib/
|   `-- mysql-connector-j-9.7.0.jar
|-- factures/
|   `-- facture_<id>.txt
|-- bin/
|-- build-test/
|-- .classpath
|-- .project
|-- gestion_pharmacie2.iml
|-- somestuff.txt
`-- README.md
```

## Architecture generale

Le projet suit une architecture en couches. Cette organisation rend le code plus clair et facilite la maintenance.

| Couche | Package | Role |
| --- | --- | --- |
| Vue | `view` | Affichage, formulaires, tableaux et boutons |
| Controleur | `controller` | Validation et logique metier |
| DAO | `dao` | Requetes SQL et communication avec MySQL |
| Modele | `model` | Representation des donnees du domaine |
| Main | `Main` | Point d'entree de l'application |

## Description des packages

### Package `model`

Ce package contient les classes qui representent les donnees principales.

| Classe | Description |
| --- | --- |
| `Client` | Client de la pharmacie |
| `Medicament` | Produit vendu avec prix, stock et peremption |
| `Fournisseur` | Fournisseur de medicaments |
| `Pharmacie` | Informations generales de la pharmacie |
| `Ordonnance` | Prescription medicale associee a un client |
| `Vente` | Vente effectuee par un client |
| `LigneVente` | Detail d'un medicament vendu |
| `Commande` | Commande fournisseur |
| `LigneCommande` | Detail d'un medicament commande |

### Package `dao`

Ce package contient les classes responsables des operations SQL.

| Classe | Role |
| --- | --- |
| `Utilitaire` | Ouvre la connexion MySQL |
| `ClientDao` | CRUD clients |
| `MedicamentDao` | CRUD medicaments et mise a jour stock |
| `FournisseurDao` | CRUD fournisseurs |
| `PharmacieDao` | Lecture et mise a jour de la pharmacie |
| `OrdonnanceDao` | Ajout et lecture des ordonnances |
| `VenteDao` | Ajout ventes, lignes de vente et mise a jour stock |
| `CommandeDao` | Ajout commandes, lignes et statut |
| `TestConnection` | Test simple de connexion |
| `Test` | Classe de test simple |

### Package `controller`

Les controleurs contiennent les regles metier et evitent de mettre la logique directement dans les vues.

| Controleur | Responsabilite |
| --- | --- |
| `ClientController` | Gestion client et historique |
| `MedicamentController` | Gestion medicament, stock et peremption |
| `FournisseurController` | Gestion fournisseur |
| `PharmacieController` | Enregistrement de la fiche pharmacie |
| `OrdonnanceController` | Gestion et verification des ordonnances |
| `VenteController` | Creation, validation, facture et historique des ventes |
| `CommandeController` | Creation, statut, reception et stock des commandes |
| `StockController` | Alertes et operations de stock |
| `DashboardController` | Statistiques de ventes par produit, client et periode |

### Package `view`

Le package `view` contient l'interface Swing.

| Classe | Role |
| --- | --- |
| `GestionPharmacieView` | Fenetre principale avec onglets |
| `PharmaciePanel` | Formulaire pharmacie |
| `ClientPanel` | Gestion des clients |
| `MedicamentPanel` | Gestion des medicaments |
| `FournisseurPanel` | Gestion des fournisseurs |
| `OrdonnancePanel` | Gestion des ordonnances |
| `VentePanel` | Gestion des ventes |
| `CommandePanel` | Gestion des commandes |
| `StockPanel` | Suivi du stock |
| `DashboardPanel` | Tableaux d'analyse des ventes et produits les plus demandes |
| `ViewUtil` | Methodes utilitaires pour l'interface |


## UML - Diagramme de classes principal

```
class Client {
  -int id
  -String nom
  -String prenom
  -String telephone
  -String adresse
}

class Medicament {
  -int id
  -String nomCommercial
  -String composition
  -String forme
  -String dosage
  -double prix
  -int quantiteStock
  -LocalDate datePeremption
  -int seuilAlerte
}

class Fournisseur {
  -int id
  -String nomSociete
  -String contact
  -String adresse
}

class Pharmacie {
  -int id
  -String nom
  -String adresse
  -String telephone
  -String email
  -String responsable
}

class Ordonnance {
  -int id
  -int idClient
  -String medecin
  -LocalDate dateOrdonnance
  -String notes
}

class Vente {
  -int id
  -int idClient
  -int idOrdonnance
  -LocalDate dateVente
  -List~LigneVente~ lignes
  +addLignes(LigneVente)
}

class LigneVente {
  -int idVente
  -int idMedicament
  -int quantite
  -double prixUnitaire
  +getTotal() double
}

class Commande {
  -int idCommande
  -LocalDate dateCommande
  -String statut
  -int idFournisseur
  -List~LigneCommande~ lignes
  +addlignes(LigneCommande)
}

class LigneCommande {
  -int idCommande
  -int idMedicament
  -int quantiteCommandee
  -double prixAchat
  +getTotal() double
}

```

## UML - Diagramme Entite-Relation

```
    CLIENT {
      int id PK
      varchar nom
      varchar prenom
      varchar telephone
      varchar adresse
    }

    MEDICAMENT {
      int id PK
      varchar nomCommercial
      varchar composition
      varchar forme
      varchar dosage
      double prix
      int quantiteStock
      date datePeremption
      int seuilAlerte
    }

    FOURNISSEUR {
      int id PK
      varchar nomSociete
      varchar contact
      varchar adresse
    }

    PHARMACIE {
      int id PK
      varchar nom
      varchar adresse
      varchar telephone
      varchar email
      varchar responsable
    }

    ORDONNANCE {
      int id PK
      int idClient FK
      varchar medecin
      date dateOrdonnance
      text notes
    }

    VENTE {
      int id PK
      int idClient FK
      int idOrdonnance FK
      date dateVente
    }

    LIGNE_VENTE {
      int idVente FK
      int idMedicament FK
      int quantite
      double prixUnitaire
    }

    COMMANDE {
      int id PK
      int idFournisseur FK
      date dateCommande
      varchar statut
    }

    LIGNE_COMMANDE {
      int idCommande FK
      int idMedicament FK
      int quantiteCommandee
      double prixAchat
    }
```

## Etapes de realisation du projet

### 1. Analyse du besoin

Nous avons commence par analyser le sujet afin de determiner les modules obligatoires : medicaments, clients, ordonnances, ventes, fournisseurs, commandes et stock. Cette etape a permis d'identifier les entites principales et les operations attendues.

### 2. Conception du modele

Les classes du package `model` ont ete creees a partir des objets du domaine. Chaque classe contient les attributs et methodes necessaires pour representer une donnee importante du systeme.

### 3. Conception de la base de donnees

La base `gestion_pharmacie` a ete construite avec une table pour chaque entite principale. Les relations ont ete definies avec des cles etrangeres : un client peut avoir plusieurs ordonnances, une vente contient plusieurs lignes de vente, une commande contient plusieurs lignes de commande.

### 4. Creation de la couche DAO

Les DAO ont ete developpes pour isoler les requetes SQL. Cette couche contient les operations `INSERT`, `SELECT`, `UPDATE` et `DELETE`.

### 5. Creation des controleurs

Les controleurs ont ete ajoutes pour valider les donnees et appliquer la logique metier : verification du stock, verification de l'ordonnance, calcul des totaux, recherche, filtrage et mise a jour.

### 6. Creation de l'interface Swing

L'interface a ete realisee avec Java Swing. La fenetre principale contient plusieurs onglets, chacun correspondant a un module fonctionnel.

Les onglets actuels sont : `Pharmacie`, `Clients`, `Medicaments`, `Fournisseurs`, `Ordonnances`, `Ventes`, `Commandes`, `Stock` et `Dashboard`.

### 7. Integration

L'integration a consiste a connecter les vues aux controleurs, les controleurs aux DAO et les DAO a MySQL. Les interactions critiques ont ete testees, surtout les ventes et les commandes.

### 8. Tests et corrections

Les tests ont permis de verifier la compilation, la connexion MySQL, l'ajout et la modification des donnees, la validation des ventes, la generation de factures texte, la mise a jour du stock, la reception des commandes fournisseurs et l'affichage des donnees du dashboard.

## Configuration de la base de donnees

Les parametres de connexion se trouvent dans :

```text
src/dao/Utilitaire.java
```

Configuration actuelle :

```java
private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
private static final String URL = "jdbc:mysql://localhost:3306/gestion_pharmacie";
private static final String USER = "root";
private static final String PASSWORD = "";
```

Si votre serveur MySQL utilise un mot de passe, il faut modifier la constante `PASSWORD`.

## Script SQL de creation

```sql
CREATE DATABASE IF NOT EXISTS gestion_pharmacie;
USE gestion_pharmacie;

CREATE TABLE IF NOT EXISTS Client (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100),
    telephone VARCHAR(30) NOT NULL,
    adresse VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS Fournisseur (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nomSociete VARCHAR(150) NOT NULL,
    contact VARCHAR(100) NOT NULL,
    adresse VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS Pharmacie (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(150),
    adresse VARCHAR(255),
    telephone VARCHAR(30),
    email VARCHAR(120),
    responsable VARCHAR(120)
);

CREATE TABLE IF NOT EXISTS Medicament (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nomCommercial VARCHAR(150) NOT NULL,
    composition VARCHAR(255),
    forme VARCHAR(100),
    dosage VARCHAR(100),
    prix DOUBLE NOT NULL DEFAULT 0,
    quantiteStock INT NOT NULL DEFAULT 0,
    datePeremption DATE NOT NULL,
    seuilAlerte INT NOT NULL DEFAULT 0
);

CREATE TABLE IF NOT EXISTS Ordonnance (
    id INT AUTO_INCREMENT PRIMARY KEY,
    idClient INT NOT NULL,
    medecin VARCHAR(150) NOT NULL,
    dateOrdonnance DATE NOT NULL,
    notes TEXT,
    CONSTRAINT fk_ordonnance_client
        FOREIGN KEY (idClient) REFERENCES Client(id)
);

CREATE TABLE IF NOT EXISTS Vente (
    id INT AUTO_INCREMENT PRIMARY KEY,
    idClient INT NULL,
    idOrdonnance INT NULL,
    dateVente DATE NOT NULL,
    CONSTRAINT fk_vente_client
        FOREIGN KEY (idClient) REFERENCES Client(id),
    CONSTRAINT fk_vente_ordonnance
        FOREIGN KEY (idOrdonnance) REFERENCES Ordonnance(id)
);

CREATE TABLE IF NOT EXISTS LigneVente (
    idVente INT NOT NULL,
    idMedicament INT NOT NULL,
    quantite INT NOT NULL,
    prixUnitaire DOUBLE NOT NULL,
    CONSTRAINT fk_lignevente_vente
        FOREIGN KEY (idVente) REFERENCES Vente(id),
    CONSTRAINT fk_lignevente_medicament
        FOREIGN KEY (idMedicament) REFERENCES Medicament(id)
);

CREATE TABLE IF NOT EXISTS Commande (
    id INT AUTO_INCREMENT PRIMARY KEY,
    idFournisseur INT NOT NULL,
    dateCommande DATE NOT NULL,
    statut VARCHAR(50) NOT NULL,
    CONSTRAINT fk_commande_fournisseur
        FOREIGN KEY (idFournisseur) REFERENCES Fournisseur(id)
);

CREATE TABLE IF NOT EXISTS LigneCommande (
    idCommande INT NOT NULL,
    idMedicament INT NOT NULL,
    quantiteCommandee INT NOT NULL,
    prixAchat DOUBLE NOT NULL,
    CONSTRAINT fk_lignecommande_commande
        FOREIGN KEY (idCommande) REFERENCES Commande(id),
    CONSTRAINT fk_lignecommande_medicament
        FOREIGN KEY (idMedicament) REFERENCES Medicament(id)
);
```

## Donnees de test

```sql
USE gestion_pharmacie;

INSERT INTO Client (nom, prenom, telephone, adresse)
VALUES
('Ali', 'Karim', '0600000000', 'Casablanca'),
('Nadia', 'Amrani', '0611111111', 'Rabat');

INSERT INTO Fournisseur (nomSociete, contact, adresse)
VALUES
('Med Supply SARL', '0522000000', 'Casablanca'),
('Pharma Distribution', '0537000000', 'Rabat');

INSERT INTO Medicament
(nomCommercial, composition, forme, dosage, prix, quantiteStock, datePeremption, seuilAlerte)
VALUES
('Doliprane', 'Paracetamol', 'Comprime', '500mg', 20.00, 100, '2027-12-31', 20),
('Amoxicilline', 'Amoxicilline', 'Gelule', '1g', 45.00, 50, '2027-06-30', 10),
('Vitamine C', 'Acide ascorbique', 'Comprime', '1000mg', 30.00, 8, '2026-11-15', 10);
```

## Compilation et execution

### Prerequis

- JDK 17 ou JDK 21 ;
- MySQL Server ;
- base de donnees `gestion_pharmacie` ;
- connecteur MySQL dans `lib/mysql-connector-j-9.7.0.jar`.

### Execution depuis Eclipse

1. Ouvrir Eclipse.
2. Choisir `File > Import > Existing Projects into Workspace`.
3. Selectionner le dossier du projet.
4. Verifier le Build Path.
5. Ajouter le fichier `lib/mysql-connector-j-9.7.0.jar`.
6. Lancer la classe `Main.Main`.

## Scenarios d'utilisation

### Ajouter un medicament

1. Ouvrir l'onglet `Medicaments`.
2. Saisir les informations du medicament.
3. Cliquer sur `Ajouter`.
4. Le medicament est enregistre dans MySQL et affiche dans le tableau.

### Effectuer une vente

1. Ouvrir l'onglet `Ventes`.
2. Selectionner un client.
3. Selectionner un medicament.
4. Choisir la quantite.
5. Cliquer sur `Ajouter ligne`.
6. La vente est creee automatiquement si elle n'existe pas encore.
7. Ajouter d'autres lignes si necessaire.
8. Cocher ordonnance si le client presente une ordonnance.
9. Saisir les informations de l'ordonnance.
10. Cliquer sur `Valider`.
11. Le stock est mis a jour.
12. La vente apparait dans l'historique.
13. Une facture texte est creee automatiquement dans `factures/facture_<id>.txt`.

### Creer une commande fournisseur

1. Ouvrir l'onglet `Commandes`.
2. Selectionner un fournisseur.
3. Selectionner un medicament.
4. Saisir la quantite et le prix d'achat.
5. Ajouter la ligne.
6. Valider la commande.
7. A la livraison, cliquer sur `Receptionner`.
8. Le stock est augmente automatiquement.

### Controler le stock

1. Ouvrir l'onglet `Stock`.
2. Consulter la liste des medicaments.
3. Cliquer sur `Stock Faible` pour voir les alertes de rupture.
4. Cliquer sur `Produits Perimes` pour voir les produits expires.
5. Cliquer sur `Peremption Proche` pour voir les produits a surveiller.
6. Cliquer sur `Valeur Stock` pour calculer la valeur globale.

### Consulter le Dashboard

1. Ouvrir l'onglet `Dashboard`.
2. Consulter l'onglet `Ventes par Produit` pour voir les quantites vendues par medicament.
3. Consulter l'onglet `Ventes par Client` pour voir le total achete par client.
4. Consulter l'onglet `Produits les plus demandes` pour identifier les medicaments les plus vendus.
5. Saisir une date de debut et une date de fin au format `yyyy-mm-dd`.
6. Cliquer sur `Calculer CA` pour calculer le chiffre d'affaires de la periode.
7. Cliquer sur `Actualiser tout` pour recharger les statistiques depuis la base.



## Limites et ameliorations possibles

- Ameliorer le dashboard avec des graphiques visuels.
- Exporter les factures en PDF.
- Ajouter une authentification.
- Ajouter des roles : pharmacien, responsable, administrateur.
- Ameliorer la mise en page et l'impression des factures.
- Ajouter des tests unitaires et tests d'integration.
- Utiliser un fichier de configuration pour les parametres MySQL.
- Ameliorer la gestion des messages d'erreur.

## Depannage

### Base `gestion_pharmacie` introuvable

Creer la base :

```sql
CREATE DATABASE gestion_pharmacie;
```

Puis executer le script SQL des tables.

### Acces refuse pour l'utilisateur MySQL

Modifier le mot de passe dans :

```text
src/dao/Utilitaire.java
```

## Conclusion

Ce projet met en place une application complete de gestion de pharmacie en Java Swing et MySQL. Il couvre les operations principales demandees dans le sujet : gestion des medicaments, suivi du stock, clients, ordonnances, ventes, commandes fournisseurs, historiques, factures et dashboard analytique.

L'architecture en couches rend le projet plus clair. Les vues gerent l'affichage, les controleurs appliquent les regles metier, les DAO communiquent avec MySQL et les modeles representent les donnees. Les problemes rencontres pendant le developpement ont permis d'ameliorer l'integration entre la base de donnees, les controleurs et les vues, surtout pour les ordonnances, les ventes, la generation de factures et le reporting.

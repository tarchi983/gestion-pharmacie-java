# Système de Gestion d'une Pharmacie

Application Desktop développée en **Java Swing** avec une base de données **MySQL**, permettant de gérer l'ensemble des opérations quotidiennes d'une pharmacie : gestion des médicaments, des stocks, des ventes, des ordonnances, des clients, des fournisseurs, ainsi qu'un tableau de bord analytique.

---

## 📌 Informations du Projet

| Élément | Détail |
| :--- | :--- |
| **Sujet** | Sujet 4 : Système de gestion d'une pharmacie |
| **Année universitaire** | 2025 / 2026 |
| **Encadrant** | Pr. Said El Kafhali |
| **Type d'application** | Application Desktop (GUI) |
| **Langage** | Java (JDK 17 / 21) |
| **Interface Graphique** | Java Swing |
| **Base de Données** | MySQL |
| **Accès aux Données** | JDBC |
| **Architecture** | MVC + DAO (Vue - Contrôleur - DAO - Modèle) |
| **Classe Principale** | `Main.Main` |
| **Fenêtre Principale** | `view.GestionPharmacieView` |
| **Module Java** | `gestion.pharmacie` |
| **Driver MySQL** | `lib/mysql-connector-j-9.7.0.jar` |

---

## 👥 Membres du Groupe

- **Oussama Tarchi**
- **Ali Zouine**
- **Taha Rajel**
- **Abdessamad Lyamani**

---

## 📄 Présentation du Sujet

La gestion d'une pharmacie demande une organisation précise entre plusieurs activités : la vente de médicaments, la gestion des stocks, le suivi des ordonnances, la relation avec les clients, les commandes fournisseurs et l'analyse des ventes. Une pharmacie doit connaître en permanence les produits disponibles, les produits proches de la péremption, les médicaments en rupture ou en stock faible, ainsi que l'historique des transactions.

L'objectif de ce projet est de développer une application permettant de centraliser ces opérations dans une interface graphique simple. Le pharmacien peut ainsi enregistrer les médicaments, suivre le stock, créer des ventes, gérer les ordonnances et commander les produits nécessaires chez les fournisseurs.

Le système répond aux besoins suivants :
- Enregistrer les informations générales de la pharmacie ;
- Gérer les clients et les fournisseurs ;
- Gérer les médicaments et leurs informations essentielles ;
- Suivre la quantité disponible en stock et générer des alertes (stock faible, péremption) ;
- Saisir les ordonnances et créer des ventes avec ou sans ordonnance ;
- Mettre à jour automatiquement le stock après validation d'une vente ou réception d'une commande ;
- Consulter les historiques des ventes et des commandes ;
- Générer automatiquement une facture texte après validation d'une vente ;
- Analyser les ventes dans un tableau de bord dédié (Dashboard).

---

## 🎯 Fonctionnalités Principales

### 🏢 Gestion de la Pharmacie
- Configuration et mise à jour des informations de l'établissement (Nom, Adresse, Téléphone, Email, Responsable).

### 👥 Gestion des Clients
- Operations CRUD (Ajouter, Modifier, Supprimer, Afficher) et consultation de l'historique d'achats d'un client.

### 💊 Gestion des Médicaments
- Suivi du nom commercial, composition, forme, dosage, prix, quantité en stock, date de péremption et seuil d'alerte.

### 🚚 Gestion des Fournisseurs
- Gestion de la liste des fournisseurs (Société, Contact, Adresse).

### 🛒 Gestion des Ventes
- Création de ventes avec/sans ordonnance, ajout des médicaments au panier, calcul automatique du total, vérification du stock, génération de factures textuelles dans `factures/` et mise à jour automatique du stock.

### 📦 Gestion des Commandes Fournisseurs
- Enregistrement des commandes, suivi des statuts, annulation et mise à jour automatique du stock dès la réception d'une livraison.

### 📊 Suivi du Stock & Dashboard
- Détection des ruptures de stock, des produits périmés ou proches de la péremption, calcul de la valeur globale du stock, suivi du chiffre d'affaires par période et analyse des produits les plus demandés.

---

## 🏗️ Architecture du Projet

Le projet suit une architecture en couches (MVC + DAO) :

```text
gestion_pharmacie2/
├── src/
│   ├── Main/           # Point d'entrée de l'application (Main.java)
│   ├── controller/     # Validation et logique métier
│   ├── dao/            # Requêtes SQL et communication avec MySQL
│   ├── model/          # Entities / Représentation des données
│   └── view/           # Interfaces graphiques Java Swing
├── lib/                # Driver MySQL (mysql-connector-j-9.7.0.jar)
├── factures/           # Dossier de sauvegarde des factures générées (.txt)
├── bin/                # Fichiers compilés (.class)
└── README.md

# Application Angular - Liste de Livres

Cette application Angular permet d'afficher et de gérer une liste de livres avec pagination, recherche et actions CRUD, en utilisant les composants Angular Material.

## Fonctionnalités
- Affichage d'une liste de livres paginée.
- Recherche de livres par titre ou auteur.
- Consultation, modification et suppression de livres avec confirmation.
- Pagination avec état persistant : mémorisation de la page et du terme de recherche.

## Stack Technologique
- Angular 20+
- Angular Material (Table, Paginator, Champs de formulaire, Boutons, Icônes, Spinner)
- TypeScript
- Jasmine/Karma pour les tests unitaires

## Structure du Projet
- `src/app/models` — Interfaces et modèles TypeScript pour les livres et la pagination.
- `src/app/services` — Services pour récupérer les livres et gérer l'état de la pagination.
- `src/app/components/book-list` — Composant principal pour la liste des livres.
- `src/app/directives` — Directives personnalisées (ex. cellules cliquables).
- `src/app` — Routage et modules globaux.

## Installation et Lancement
### Prérequis :
- Node.js 20.19
- Angular CLI 


### Installer les dépendances :
```bash
npm install
```

### Lancer l'application en local :
```bash
ng serve
```

Ouvrir le navigateur et accéder à `http://localhost:4200/`.

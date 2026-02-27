# language: fr
Fonctionnalité: Gestion des livres (CRUD)

  Contexte:
    Etant donné je suis connecté avec "test" et "password"

  Scénario: Parcours complet d'un livre (Création, Modification, Suppression)
    Etant donné je clique sur "Ajouter un livre"
    Quand je remplis le formulaire avec "Livre Cucumber", "Robot", "2024"
    Et je valide le formulaire
    Alors le livre "Livre Cucumber" est présent dans la liste

    Quand je clique sur "Modifier" pour le livre "Livre Cucumber"
    Et je remplis le formulaire avec "Livre Cucumber Mis à jour", "Robot", "2025"
    Et je valide le formulaire
    Alors le livre "Livre Cucumber Mis à jour" est présent dans la liste

    Quand je clique sur "Supprimer" pour le livre "Livre Cucumber Mis à jour"
    Alors le livre "Livre Cucumber Mis à jour" n'est plus présent dans la liste

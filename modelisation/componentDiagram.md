1.  **Etudiant Management (Gestion des Étudiants) :** Un composant de la couche présentation responsable de la gestion des informations sur les étudiants (par exemple, créer, mettre à jour, supprimer des enregistrements d'étudiants). Il est connecté à `IEtudiantService` (service) et `EtudiantDataAccess` (couche d'accès aux données).

2.  **IEtudiantService :** Une interface définissant les opérations liées aux étudiants que le système prend en charge (par exemple, obtenir les détails d'un étudiant, ajouter des étudiants, mettre à jour des étudiants). Cette interface masque l'implémentation concrète du composant `Etudiant Management`.

3.  **Generation Fiche Recapitulatiive (Génération de Fiche Récapitulative) :** Un composant qui génère des rapports récapitulatifs sur les étudiants. Il utilise `IEtudiantService` pour accéder aux données des étudiants.

4.  **IcalculMoyenneService :** Interface définissant le contrat utilisé par le composant calculMoyenne.

5.  **Calcul Moyenne :** Un composant qui calcule les moyennes des étudiants en fonction des évaluations. Il implémente `IcalculMoyenneService`.

6.  **Enseignant Management (Gestion des Enseignants) :** Un composant de la couche présentation pour la gestion des informations sur les enseignants. Il se connecte à `IEnseignantServices` (service) et `EnseignantDataAccess` (couche d'accès aux données).

7.  **IEnseignantServices :** Une interface définissant les opérations liées aux enseignants.

8.  **Authentication (Authentification) :** Gère l'authentification des utilisateurs. Il dépend de l'interface `IAuthenticationServices`.

9.  **IAuthenticationServices :** Une interface pour les opérations liées à l'authentification.

10. **Module Management (Gestion des Modules) :** Un composant de la couche présentation pour la gestion des informations sur les modules.

11. **IModuleServices :** Une interface définissant les opérations liées aux modules.

12. **Evaluation Management (Gestion des Évaluations) :** Un composant de la couche présentation pour les tâches et les opérations liées aux évaluations.

13. **IevaluationServices :** Interface utilisée par le composant calculMoyenne.

14. **Data Access Layer (Couche d'Accès aux Données) :** Un composant central qui fait abstraction de toutes les interactions avec la base de données. Il fournit des services d'accès aux données pour les étudiants, les enseignants, les modules et les évaluations.

15. **EtudiantDataAccess :** Un composant qui gère la récupération et le stockage des données des étudiants à partir de la base de données.

16. **EnseignantDataAccess :** Un composant qui gère la récupération et le stockage des données des enseignants à partir de la base de données.

17. **ModuleDataAccess :** Un composant qui gère la récupération et le stockage des données des modules à partir de la base de données.

18. **EvaluationDataAccess :** Un composant qui gère la récupération et le stockage des données des évaluations à partir de la base de données.

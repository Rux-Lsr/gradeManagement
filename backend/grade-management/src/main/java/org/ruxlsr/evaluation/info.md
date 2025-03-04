# Composant "Gestion des Évaluations"

Responsabilité : Gérer les évaluations (TP, CC, SN) et les notes associées.

## Services fournis :

- Créer une évaluation (avec note maximale, coefficient, etc.).

- Supprimer une évaluation.

- Modifier les informations d'une évaluation.

- Enregistrer les notes des étudiants pour une évaluation.

## Services requis :

Accès à la base de données pour stocker/retrouver les évaluations.

Accès aux informations des étudiants pour associer les notes.







## METHODES

- CreateEvaluation(String date,float coef,float max,String type) : cree une nouvelle evaluation avec sa date et heure ,son coefficient(pourcentage de la note finale), sa note maximale et son type
- -DeleteEvaluation(List<Evaluation> evaluations,int choix): permet de supprimer une evaluation de la liste d'evaluation en fonction de son index
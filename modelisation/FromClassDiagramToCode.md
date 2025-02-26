**Classe Etudiant :**

- **Attributs :**
  - `nom` : String
  - `prenom` : String
  - `matricule` : String
  - `id` : int
  - `modules` : `List<Modules>` (car un étudiant est associé à 0..\* Modules)
  - `evaluations` : `List<Evaluation>` (car un étudiant est associé à 0..\* Évaluations, via la relation avec Note)

**Classe Modules :**

- **Attributs :**
  - `id` : int
  - `nom` : String
  - `description` : String
  - `credit` : int
  - `enseignants` : `List<Enseignant>` (car un module est associé à 0..\* Enseignants)
  - `evaluations` : `List<Evaluation>` (car un module est associé à 0..\* Évaluations)

**Classe Enseignant :**

- **Attributs :**
  - `nom` : String
  - `prenom` : String
  - `password` : String
  - `id` : int
  - `modules` : `List<Modules>` (car un enseignant est associé à 0..\* Modules)
  - `evaluations` : `List<Evaluation>` (car un enseignant est associé à 0..\* Évaluations)

**Classe Evaluation :**

- **Attributs :**
  - `id` : int
  - `date` : LocalDateTime
  - `coef` : float
  - `max` : float
  - `typeEvaluation` : `TypeEvaluation` (car une Evaluation est associée à 1 TypeEvaluation)
  - `etudiants` : `List<Etudiant>` (car une Evaluation est associé a plusieurs Etudiant par Evaluation -> Note -> Etudiant)

**Classe Note :**

- **Attributs :**
  - `note` : float
  - `etudiant` : `Etudiant` (car une Note est associée à 1 Etudiant)
  - `evaluation` : `Evaluation` (car une Note est associée à 1 Evaluation)

**Énumération TypeEvaluation :**

- **Valeurs (pas d'attributs, juste les valeurs) :**
  - `TP`
  - `CC`
  - `SN`
  - `Rattrapage`

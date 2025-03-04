CREATE TABLE Etudiant (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(255),
    prenom VARCHAR(255),
    matricule VARCHAR(255)
);

CREATE TABLE Modules (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(255),
    description TEXT,
    credit INT
);

CREATE TABLE Enseignant (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(255),
    prenom VARCHAR(255),
    password VARCHAR(255)
);

CREATE TABLE Evaluation (
    id INT PRIMARY KEY AUTO_INCREMENT,
    moduleId INT,
    date DATETIME,
    coef FLOAT,
    max FLOAT,
    typeEvaluation ENUM('TP', 'CC', 'SN', 'Rattrapage'),
    FOREIGN KEY (moduleId) REFERENCES Modules(id)
);

CREATE TABLE Note (
    evaluationId INT,
    etudiantId INT,
    note FLOAT,
    PRIMARY KEY (evaluationId, etudiantId) ,
    FOREIGN KEY (evaluationId) REFERENCES Evaluation(id),
    FOREIGN KEY (etudiantId) REFERENCES Etudiant(id)
);

-- Linking table for Module - Enseignant (Many-to-Many)
CREATE TABLE ModuleEnseignant (
  moduleId INT AUTO_INCREMENT,
  enseignantId INT,
  PRIMARY KEY (moduleId, enseignantId),
  FOREIGN KEY (moduleId) REFERENCES Modules(id),
  FOREIGN KEY (enseignantId) REFERENCES Enseignant(id)
);

# Instructions d'exécution du projet

Frontend (React + Vite)

```bash

# Se déplacer dans le dossier frontend

cd frontend/recap-notes

# Installer les dépendances

npm install

# Démarrer le serveur de développement

npm run dev
```

Le frontend sera accessible par défaut sur http://localhost:5173

# Backend (Spark Java)

Adapter les parametres de connexion a la bd dans dossier `utils` de `dataaccess` pour votre SGBD de donnee en modifiant les variable `URL, PASSWORD, USER`
Le backend sera accessible par défaut sur http://127.0.0.1:8000 comme indiqué dans api-endpoint.md

```bash
# Se déplacer dans le dossier backend
cd backend/grade-management

# Compiler le projet avec Maven
mvn clean package

# Exécuter le jar généré
java -jar target/grade-management-1.0-SNAPSHOT-jar-with-dependencies.jar
```

Prérequis
Frontend :

Node.js et npm
Backend :

Java JDK 21 (selon le pom.xml)
Maven
MySQL Server
backup Base de données :

Configurer la base de données MySQL selon le script dans bd.sql
Note importante
Les deux parties (frontend et backend) doivent être exécutées simultanément dans des terminaux séparés pour que l'application fonctionne correctement.

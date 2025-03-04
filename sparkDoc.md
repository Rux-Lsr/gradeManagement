Voici une liste exhaustive des **méthodes statiques** de **SparkJava** (classe `spark.Spark`) ainsi que les **méthodes des objets `Request` et `Response`** utilisés dans les routes et les filtres.

---

## **Méthodes statiques de SparkJava (`spark.Spark`)**

### **1. Configuration du serveur**

- **`port(int port)`** : Définit le port sur lequel le serveur écoute (par défaut : 4567).
- **`ipAddress(String ipAddress)`** : Définit l'adresse IP sur laquelle le serveur écoute (par défaut : `0.0.0.0`).
- **`threadPool(int maxThreads)`** : Définit la taille du pool de threads (par défaut : 8 threads).
- **`staticFiles.location(String location)`** : Définit l'emplacement des fichiers statiques dans le classpath.
- **`externalStaticFileLocation(String externalFolder)`** : Définit un dossier externe pour les fichiers statiques.
- **`awaitInitialization()`** : Attend que le serveur soit initialisé.
- **`stop()`** : Arrête le serveur.
- **`awaitStop()`** : Attend que le serveur soit complètement arrêté.
- **`init()`** : Initialise le serveur (appelé automatiquement, sauf si vous utilisez `awaitInitialization`).
- **`initExceptionHandler(Consumer<Exception> initExceptionHandler)`** : Définit un gestionnaire d'exceptions pour les erreurs d'initialisation.

---

### **2. Définition des routes**

- **`get(String path, Route route)`** : Définit une route pour les requêtes GET.
- **`post(String path, Route route)`** : Définit une route pour les requêtes POST.
- **`put(String path, Route route)`** : Définit une route pour les requêtes PUT.
- **`delete(String path, Route route)`** : Définit une route pour les requêtes DELETE.
- **`patch(String path, Route route)`** : Définit une route pour les requêtes PATCH.
- **`head(String path, Route route)`** : Définit une route pour les requêtes HEAD.
- **`options(String path, Route route)`** : Définit une route pour les requêtes OPTIONS.
- **`trace(String path, Route route)`** : Définit une route pour les requêtes TRACE.
- **`connect(String path, Route route)`** : Définit une route pour les requêtes CONNECT.
- **`webSocket(String path, Class<?> handler)`** : Définit un endpoint WebSocket.

---

### **3. Filtres**

- **`before(String path, Filter filter)`** : Exécute un filtre avant les requêtes correspondant au chemin.
- **`before(Filter filter)`** : Exécute un filtre avant toutes les requêtes.
- **`after(String path, Filter filter)`** : Exécute un filtre après les requêtes correspondant au chemin.
- **`after(Filter filter)`** : Exécute un filtre après toutes les requêtes.
- **`afterAfter(String path, Filter filter)`** : Exécute un filtre après les filtres `after`.
- **`afterAfter(Filter filter)`** : Exécute un filtre après les filtres `after` pour toutes les requêtes.

---

### **4. Gestion des erreurs**

- **`notFound(Route route)`** : Définit une réponse pour les routes non trouvées (404).
- **`internalServerError(Route route)`** : Définit une réponse pour les erreurs internes du serveur (500).
- **`exception(Class<? extends Exception> exceptionClass, ExceptionHandler handler)`** : Définit un gestionnaire pour une exception spécifique.

---

### **5. Sessions**

- **`sessionTimeout(long timeout)`** : Définit le délai d'expiration des sessions (en secondes).

---

### **6. Sécurité**

- **`secure(String keystoreFile, String keystorePassword, String truststoreFile, String truststorePassword)`** : Active HTTPS avec un certificat.
- **`redirect.any(String from, String to)`** : Redirige toutes les requêtes de `from` vers `to`.

---

## **Méthodes de l'objet `Request`**

L'objet `Request` représente la requête HTTP reçue par le serveur. Voici ses principales méthodes :

### **1. Informations sur la requête**

- **`requestMethod()`** : Retourne la méthode HTTP (GET, POST, etc.).
- **`scheme()`** : Retourne le schéma (http ou https).
- **`host()`** : Retourne le nom d'hôte.
- **`pathInfo()`** : Retourne le chemin de la requête.
- **`url()`** : Retourne l'URL complète de la requête.
- **`uri()`** : Retourne l'URI de la requête.
- **`contentType()`** : Retourne le type de contenu de la requête.
- **`ip()`** : Retourne l'adresse IP du client.
- **`userAgent()`** : Retourne l'agent utilisateur (User-Agent).

### **2. Paramètres de la requête**

- **`queryParams(String queryParam)`** : Retourne la valeur d'un paramètre de requête.
- **`queryMap()`** : Retourne une carte (Map) de tous les paramètres de requête.
- **`params(String param)`** : Retourne la valeur d'un paramètre de route.
- **`paramsMap()`** : Retourne une carte (Map) de tous les paramètres de route.

### **3. Corps de la requête**

- **`body()`** : Retourne le corps de la requête sous forme de chaîne.
- **`headers(String header)`** : Retourne la valeur d'un en-tête HTTP.
- **`headersMap()`** : Retourne une carte (Map) de tous les en-têtes HTTP.

### **4. Sessions**

- **`session()`** : Retourne la session associée à la requête.
- **`session(boolean create)`** : Retourne la session, en la créant si nécessaire.

---

## **Méthodes de l'objet `Response`**

L'objet `Response` représente la réponse HTTP envoyée par le serveur. Voici ses principales méthodes :

### **1. Configuration de la réponse**

- **`status(int statusCode)`** : Définit le code de statut HTTP (par exemple, 200, 404).
- **`type(String contentType)`** : Définit le type de contenu de la réponse (par exemple, `application/json`).
- **`header(String header, String value)`** : Ajoute un en-tête HTTP à la réponse.
- **`redirect(String location)`** : Redirige vers une autre URL.
- **`redirect(String location, int statusCode)`** : Redirige vers une autre URL avec un code de statut spécifique.

### **2. Corps de la réponse**

- **`body(String body)`** : Définit le corps de la réponse.
- **`body()`** : Retourne le corps de la réponse.
- **`raw()`** : Retourne le flux de sortie brut (OutputStream).

### **3. Cookies**

- **`cookie(String name, String value)`** : Ajoute un cookie à la réponse.
- **`cookie(String name, String value, int maxAge)`** : Ajoute un cookie avec une durée de vie.
- **`cookie(String name, String value, int maxAge, boolean secured)`** : Ajoute un cookie sécurisé.
- **`removeCookie(String name)`** : Supprime un cookie.

### **4. Sessions**

- **`session()`** : Retourne la session associée à la réponse.
- **`session(boolean create)`** : Retourne la session, en la créant si nécessaire.

---

## **Exemple complet**

Voici un exemple qui utilise plusieurs de ces méthodes :

```java
import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {
        port(8080); // Configurer le port
        ipAddress("127.0.0.1"); // Configurer l'adresse IP

        get("/hello/:name", (req, res) -> {
            String name = req.params("name"); // Récupérer un paramètre de route
            res.type("application/json"); // Définir le type de contenu
            return "{ \"message\": \"Hello, " + name + "!\" }"; // Retourner un JSON
        });

        post("/submit", (req, res) -> {
            String body = req.body(); // Récupérer le corps de la requête
            res.status(201); // Définir le code de statut
            return "Received: " + body;
        });

        after((req, res) -> {
            res.header("X-Custom-Header", "SparkJava"); // Ajouter un en-tête personnalisé
        });
    }
}
```

---

### **Conclusion**

SparkJava propose une API simple et puissante pour créer des serveurs web en Java. Les méthodes statiques permettent une configuration rapide, tandis que les objets `Request` et `Response` donnent un contrôle total sur les requêtes et les réponses. Utilisez ces méthodes pour créer des API RESTful, des services web ou des applications complètes.

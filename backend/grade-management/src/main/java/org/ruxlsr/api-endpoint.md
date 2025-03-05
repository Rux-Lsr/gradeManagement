
# API Documentation for Ruxlsr Backend

This document describes the API endpoints available in the Ruxlsr backend.  The backend is built using Spark Java and uses JSON for request and response bodies.  All responses are returned with the `Content-Type: application/json` header.

**Base URL:** `http://127.0.0.1:8000` (or as configured)

## 1. Enseignant (Teacher) Endpoints

### 1.1. Create a New Enseignant

* **Endpoint:** `POST /enseignants`
* **Description:** Creates a new teacher record.
* **Request Method:** POST
* **Request Headers:** `Content-Type: application/json`
* **Request Body:**
  ```json
  {
    "nom": "string",   // Teacher's name
    "prenom": "string",  
  "password": "string"
  }
  ```
* **Response Codes:**
    * `200 OK`: Successful creation.
* **Response Body (Success):**
  ```json
  {
    "message": "Enseignant created",
    "rowsAffected": 1   // Indicates one row was affected
  }
  ```

### 1.2. Delete an Enseignant

* **Endpoint:** `DELETE /enseignants`
* **Description:** Deletes an existing teacher record.
* **Request Method:** DELETE
* **Request Headers:** `Content-Type: application/json`
* **Request Body:**
  ```json
  {
    "id": "integer" //The ID of the enseignant to delete
  }
  ```
* **Response Codes:**
    * `200 OK`: Successful deletion.
* **Response Body (Success):**
  ```json
  {
    "message": "Enseignant deleted",
    "rowsAffected": 1   // Indicates one row was affected
  }
  ```

### 1.3. Get All Enseignants

* **Endpoint:** `GET /enseignants`
* **Description:** Retrieves a list of all teachers.
* **Request Method:** GET
* **Request Headers:** (None)
* **Response Codes:**
    * `200 OK`: Successful retrieval.
* **Response Body (Success):**
  ```json
  [
    {
      "id": "integer",     //Teacher ID
      "nom": "string",   // Teacher's name
      "specialite": "string"  // Teacher's specialty
    },
    {
      "id": "integer",     //Teacher ID
      "nom": "string",   // Teacher's name
      "specialite": "string"  // Teacher's specialty
    }
    // ... more enseignants
  ]
  ```

## 2. Etudiant (Student) Endpoints

### 2.1. Create a New Etudiant

* **Endpoint:** `POST /etudiants`
* **Description:** Creates a new student record.
* **Request Method:** POST
* **Request Headers:** `Content-Type: application/json`
* **Request Body:**
  ```json
  {
    "nom": "string",   // Student's name
    "prenom": "string",  // Student's first name
    "matricule": "string",    // Student's email
    "moduleId": "integer"
  }
  ```
* **Response Codes:**
    * `200 OK`: Successful creation.
* **Response Body (Success):**
  ```json
  {
    "message": "Etudiant created",
    "rowsAffected": 1   // Indicates one row was affected
  }
  ```

### 2.2. Delete an Etudiant

* **Endpoint:** `DELETE /etudiants`
* **Description:** Deletes an existing student record.
* **Request Method:** DELETE
* **Request Headers:** `Content-Type: application/json`
* **Request Body:**
  ```json
  {
    "id": "integer" //The ID of the etudiant to delete
  }
  ```
* **Response Codes:**
    * `200 OK`: Successful deletion.
* **Response Body (Success):**
  ```json
  {
    "message": "Etudiant deleted",
    "rowsAffected": 1   // Indicates one row was affected
  }
  ```

### 2.3. Update an Etudiant

* **Endpoint:** `PUT /etudiants`
* **Description:** Updates an existing student record.
* **Request Method:** PUT
* **Request Headers:** `Content-Type: application/json`
* **Request Body:**
  ```json
  {
    "id":23,
    "nom":"ruxlsr",
    "prenom":"sonwa",
    "matricule":"E11223"
  }
  ```
* **Response Codes:**
    * `200 OK`: Successful update.
* **Response Body (Success):**
  ```json
  {
    "message": "Etudiant updated",
    "rowsAffected": 1   // Indicates one row was affected
  }
  ```

### 2.4. Get All Etudiants

* **Endpoint:** `GET /etudiants`
* **Description:** Retrieves a list of all students.
* **Request Method:** GET
* **Request Headers:** (None)
* **Response Codes:**
    * `200 OK`: Successful retrieval.
* **Response Body (Success):**
  ```json
  [
    {
      "id": "integer",    // Student ID
      "nom": "string",   // Student's name
      "prenom": "string",  // Student's first name
      "email": "string"    // Student's email
    },
    {
      "id": "integer",    // Student ID
      "nom": "string",   // Student's name
      "prenom": "string",  // Student's first name
      "email": "string"    // Student's email
    }
    // ... more etudiants
  ]
  ```

## 3. Evaluation Endpoints

### 3.1. Create a New Evaluation

* **Endpoint:** `POST /evaluations`
* **Description:** Creates a new evaluation record.
* **Request Method:** POST
* **Request Headers:** `Content-Type: application/json`
* **Request Body:**
  ```json
  {
    "nom": "string",       // Evaluation Name
    "date": "string",      // Evaluation Date (ISO 8601 format)
    "description": "string"  // Evaluation description
  }
  ```
* **Response Codes:**
    * `200 OK`: Successful creation.
* **Response Body (Success):**
  ```json
  {
    "message": "Evaluation created",
    "rowsAffected": 1   // Indicates one row was affected
  }
  ```

### 3.2. Delete an Evaluation

* **Endpoint:** `DELETE /evaluations`
* **Description:** Deletes an existing evaluation record.
* **Request Method:** DELETE
* **Request Headers:** `Content-Type: application/json`
* **Request Body:**
  ```json
  {
    "id": "integer" //The ID of the evaluation to delete
  }
  ```
* **Response Codes:**
    * `200 OK`: Successful deletion.
* **Response Body (Success):**
  ```json
  {
    "message": "Evaluation deleted",
    "rowsAffected": 1   // Indicates one row was affected
  }
  ```

### 3.3. Update an Evaluation

* **Endpoint:** `PUT /evaluations`
* **Description:** Updates an existing evaluation record.
* **Request Method:** PUT
* **Request Headers:** `Content-Type: application/json`
* **Request Body:**
  ```json
  {
    "id": "integer",       // Evaluation ID to update
    "nom": "string",       // Evaluation Name
    "date": "string",      // Evaluation Date (ISO 8601 format)
    "description": "string"  // Evaluation description
  }
  ```
* **Response Codes:**
    * `200 OK`: Successful update.
* **Response Body (Success):**
  ```json
  {
    "message": "Evaluation updated",
    "rowsAffected": 1   // Indicates one row was affected
  }
  ```

### 3.4. Get All Evaluations

* **Endpoint:** `GET /evaluations`
* **Description:** Retrieves a list of all evaluations.
* **Request Method:** GET
* **Request Headers:** (None)
* **Response Codes:**
    * `200 OK`: Successful retrieval.
* **Response Body (Success):**
  ```json
  [
    {
      "id": "integer",     // Evaluation ID
      "nom": "string",       // Evaluation Name
      "date": "string",      // Evaluation Date (ISO 8601 format)
      "description": "string"  // Evaluation description
    },
    {
      "id": "integer",     // Evaluation ID
      "nom": "string",       // Evaluation Name
      "date": "string",      // Evaluation Date (ISO 8601 format)
      "description": "string"  // Evaluation description
    }
    // ... more evaluations
  ]
  ```

## 4. Note Endpoints

### 4.1. Create a New Note

* **Endpoint:** `POST /notes`
* **Description:** Creates a new note record.
* **Request Method:** POST
* **Request Headers:** `Content-Type: application/json`
* **Request Body:**
  ```json
  {
    "etudiantId": "integer", // Student ID
    "evaluationId": "integer",// Evaluation ID
    "valeur": "number"    // The grade
  }
  ```
* **Response Codes:**
    * `200 OK`: Successful creation.
* **Response Body (Success):**
  ```json
  {
    "message": "Note created",
    "rowsAffected": 1   // Indicates one row was affected
  }
  ```

### 4.2. Get All Notes

* **Endpoint:** `GET /notes`
* **Description:** Retrieves a list of all notes.
* **Request Method:** GET
* **Request Headers:** (None)
* **Response Codes:**
    * `200 OK`: Successful retrieval.
* **Response Body (Success):**
  ```json
  [
    {
      "id": "integer",     // Note ID
      "etudiantId": "integer", // Student ID
      "evaluationId": "integer",// Evaluation ID
      "valeur": "number"    // The grade
    },
    {
      "id": "integer",     // Note ID
      "etudiantId": "integer", // Student ID
      "evaluationId": "integer",// Evaluation ID
      "valeur": "number"    // The grade
    }
    // ... more notes
  ]
  ```

### 4.3. Update a Note

* **Endpoint:** `PUT /notes`
* **Description:** Updates an existing note record.
* **Request Method:** PUT
* **Request Headers:** `Content-Type: application/json`
* **Request Body:**
  ```json
  {
    "id": "integer",    // Note ID to update
    "etudiantId": "integer", // Student ID
    "evaluationId": "integer",// Evaluation ID
    "valeur": "number"    // The grade
  }
  ```
* **Response Codes:**
    * `200 OK`: Successful update.
* **Response Body (Success):**
  ```json
  {
    "message": "Note updated",
    "rowsAffected": 1   // Indicates one row was affected
  }
  ```

### 4.4. Delete a Note

* **Endpoint:** `DELETE /notes`
* **Description:** Deletes an existing note record.
* **Request Method:** DELETE
* **Request Headers:** `Content-Type: application/json`
* **Request Body:**
  ```json
  {
    "id": "integer"  // Note ID to delete
  }
  ```
* **Response Codes:**
    * `200 OK`: Successful deletion.
* **Response Body (Success):**
  ```json
  {
    "message": "Note deleted",
    "rowsAffected": 1   // Indicates one row was affected
  }
  ```

## 5. Module Endpoints

### 5.1. Create a New Module

* **Endpoint:** `POST /modules`
* **Description:** Creates a new module record.
* **Request Method:** POST
* **Request Headers:** `Content-Type: application/json`
* **Request Body:**
  ```json
  {
    "nom": "string",          // Module name
    "description": "string",   // Module description
    "credit": "integer"       // Module credit
  }
  ```
* **Response Codes:**
    * `200 OK`: Successful creation.
* **Response Body (Success):**
  ```json
  {
    "message": "Module created",
    "rowsAffected": 1   // Indicates success
  }
  ```

### 5.2. Delete a Module

* **Endpoint:** `DELETE /modules`
* **Description:** Deletes an existing module record.
* **Request Method:** DELETE
* **Request Headers:** `Content-Type: application/json`
* **Request Body:**
  ```json
  {
      "id": "integer" //The ID of the module to delete
  }
  ```
* **Response Codes:**
    * `200 OK`: Successful deletion.
* **Response Body (Success):**
  ```json
  {
    "message": "Module deleted",
    "rowsAffected": 1   // Indicates success
  }
  ```

### 5.3. Update a Module

* **Endpoint:** `PUT /modules/:id`
* **Description:** Updates an existing module record.
* **Request Method:** PUT
* **Request Headers:** `Content-Type: application/json`
* **Request Parameters:**
    * `:id`: The ID of the module to update.
* **Request Body:**
  ```json
  {
    "nom": "string",          // Module name
    "description": "string",   // Module description
    "credit": "integer"       // Module credit
  }
  ```
* **Response Codes:**
    * `200 OK`: Successful update.
* **Response Body (Success):**
  ```json
  {
    "message": "Module updated",
    "rowsAffected": 1   // Indicates success
  }
  ```

### 5.4. Get a Module by ID

* **Endpoint:** `GET /modules/:id`
* **Description:** Retrieves a module by its ID.
* **Request Method:** GET
* **Request Parameters:**
    * `:id`: The ID of the module to retrieve.
* **Response Codes:**
    * `200 OK`: Successful retrieval.
* **Response Body (Success):**
  ```json
  {
    "id": "integer",          // Module ID
    "nom": "string",          // Module name
    "description": "string",   // Module description
    "credit": "integer"       // Module credit
  }
  ```

## 6. General Endpoints

### 6.1. Home

* **Endpoint:** `GET /home`
* **Description:** A simple test endpoint.
* **Request Method:** GET
* **Response Codes:**
    * `200 OK`: Always returns success.
* **Response Body:**
  ```
  "Hello the world"
  ```

## 7. Error Handling

In case of errors, the API returns a JSON response with a `500 Internal Server Error` status code.  The response body will be:

```json
{
  "error": "Error message describing the issue."
}
```

**Notes:**

* Replace `"string"`, `"integer"`, and `"number"` with the actual data types.
* This documentation assumes a basic understanding of RESTful API principles.
* This documentation reflects the code provided, but may need adjustments based on the specific implementations of your services.  Specifically, the  `id`  fields are often generated by the database upon insertion, so you may not include them in the  `POST`  request bodies. The database will generate an  `id`  automatically and assign it to the entry. Make sure your code works with that database response! Also confirm the exact types of values in your objects.

This documentation should help you understand how to interact with the Ruxlsr backend API.  Remember to replace the mock service implementations with your real code to make the API fully functional.


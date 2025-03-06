package org.ruxlsr;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.ruxlsr.dataaccess.services.DataBaseOperation;
import org.ruxlsr.dataaccess.services.impl.*;
import org.ruxlsr.enseignant.model.Enseignant;
import org.ruxlsr.enseignant.services.IEnseignantService;
import org.ruxlsr.enseignant.services.impl.EnseignantServiceImpl;
import org.ruxlsr.etudiant.model.Etudiant;
import org.ruxlsr.etudiant.service.IEtudiantServices;
import org.ruxlsr.etudiant.service.impl.EtudiantServices;
import org.ruxlsr.evaluation.model.Evaluation;
import org.ruxlsr.evaluation.model.EvaluationType;
import org.ruxlsr.evaluation.model.Note;
import org.ruxlsr.evaluation.services.IEvaluationService;
import org.ruxlsr.evaluation.services.INotesService;
import org.ruxlsr.evaluation.services.impl.EvaluationService;
import org.ruxlsr.evaluation.services.impl.NoteService;
import org.ruxlsr.generationficherecapitulative.services.IRecapService;
import org.ruxlsr.generationficherecapitulative.services.impl.RecapService;
import org.ruxlsr.module.services.IModuleServices;
import org.ruxlsr.module.model.Module;
import org.ruxlsr.module.services.impl.ModuleServices;

import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

import static spark.Spark.*;

public class Main {

    private static final Gson gson = new Gson();
    private static final DataBaseOperation<Enseignant> enseignantDataBaseOperation = new EnseignantDataBaseOperation();
    private static final DataBaseOperation<Etudiant> etudiantDataBaseOperation = new EtudiantDatabaseOperation();
    private static final DataBaseOperation<Evaluation> evaluationDataBaseOperation = new EvaluationDataBaseOperation();
    private static final DataBaseOperation<Note> noteDataBaseOperation = new NoteDataBaseOperation();
    private static final DataBaseOperation<Module> moduleDataBaseOperation = new ModuleDataBaseOperation();

    private static final IEnseignantService enseignantService = new EnseignantServiceImpl(enseignantDataBaseOperation);
    private static final IEtudiantServices etudiantService = new EtudiantServices(etudiantDataBaseOperation);
    private static final IEvaluationService evaluationService = new EvaluationService(evaluationDataBaseOperation);
    private static final INotesService notesService = new NoteService(noteDataBaseOperation);
    private static final IModuleServices moduleService = new ModuleServices(moduleDataBaseOperation);

    private static final IRecapService recapService = new RecapService(
            moduleDataBaseOperation,
            noteDataBaseOperation,
            evaluationDataBaseOperation);

    public static void main(String[] args) {
        port(8000);
        ipAddress("127.0.0.1");

        // Enable CORS
        options("/*", (request, response) -> {
            String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }

            String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }

            return "OK";
        });

        before((request, response) -> response.header("Access-Control-Allow-Origin", "*"));

        // Enseignant Routes
        post("/enseignants", (req, res) -> {
            Enseignant enseignant = gson.fromJson(req.body(), Enseignant.class);
            int rowsAffected = enseignantService.enregistrerEnseignant(enseignant);
            res.type("application/json");
            return gson.toJson(new Response("Enseignant created", rowsAffected));
        });

        delete("/enseignants", (req, res) -> {
            Enseignant enseignant = gson.fromJson(req.body(), Enseignant.class);
            int rowsAffected = enseignantService.supprimerEnseignant(enseignant);
            res.type("application/json");
            return gson.toJson(new Response("Enseignant deleted", rowsAffected));
        });

        get("/enseignants", (req, res) -> {
            res.type("application/json");
            return gson.toJson(enseignantService.recupererListeEnseignants());
        });

        // Etudiant Routes
        post("/etudiants", (req, res) -> {
            Etudiant etudiant = gson.fromJson(req.body(), Etudiant.class);
            System.out.println("POST on etudiants" + etudiant);
            etudiantService.ajouterEtudiant(etudiant);
            res.type("application/json");
            return gson.toJson(new Response("Etudiant created", 1)); // Assuming success
        });

        delete("/etudiants", (req, res) -> {
            Etudiant etudiant = gson.fromJson(req.body(), Etudiant.class);
            etudiantService.supprimerEtudiant(etudiant);
            res.type("application/json");
            return gson.toJson(new Response("Etudiant deleted", 1)); // Assuming success
        });

        put("/etudiants", (req, res) -> {
            Etudiant etudiant = gson.fromJson(req.body(), Etudiant.class);
            System.out.println("PUT on etudiants" + etudiant);
            etudiantService.updateEtudiant(etudiant);
            res.type("application/json");
            return gson.toJson(new Response("Etudiant updated", 1)); // Assuming success
        });

        get("/etudiants", (req, res) -> {
            res.type("application/json");
            return gson.toJson(etudiantService.getEtudiants());
        });

        // Evaluation Routes
        post("/evaluations", (req, res) -> {
            DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

            JsonElement eval = JsonParser.parseString(req.body());
            JsonObject e = eval.getAsJsonObject();
            OffsetDateTime offsetDateTime = OffsetDateTime.parse(e.get("date").getAsString(), formatter);
            Evaluation evaluation = new Evaluation(null,
                    e.get("moduleId").getAsInt(),
                    new Timestamp(offsetDateTime.toInstant().toEpochMilli()), e.get("coef").getAsFloat(),
                    e.get("max").getAsFloat(),
                    EvaluationType.valueOf(e.get("evaluationType").getAsString()));
            System.out.println(e);
            int rowsAffected = evaluationService.addEvaluation(evaluation);
            res.type("application/json");
            return gson.toJson(new Response("Evaluation created", rowsAffected));
        });

        delete("/evaluations", (req, res) -> {
            Evaluation evaluation = gson.fromJson(req.body(), Evaluation.class);
            int rowsAffected = evaluationService.delete(evaluation);
            res.type("application/json");
            return gson.toJson(new Response("Evaluation deleted", rowsAffected));
        });

        put("/evaluations", (req, res) -> {
            Evaluation evaluation = gson.fromJson(req.body(), Evaluation.class);
            int rowsAffected = evaluationService.update(evaluation);
            res.type("application/json");
            return gson.toJson(new Response("Evaluation updated", rowsAffected));
        });

        get("/evaluations", (req, res) -> {
            res.type("application/json");
            return gson.toJson(evaluationService.getEvaluation());
        });

        // Note Routes
        post("/notes", (req, res) -> {
            Note note = gson.fromJson(req.body(), Note.class);
            int rowsAffected = notesService.createNote(note);
            res.type("application/json");
            return gson.toJson(new Response("Note created", rowsAffected));
        });

        get("/notes", (req, res) -> {
            res.type("application/json");
            return gson.toJson(notesService.getNote());
        });

        put("/notes", (req, res) -> {
            Note note = gson.fromJson(req.body(), Note.class);
            int rowsAffected = notesService.update(note);
            res.type("application/json");
            return gson.toJson(new Response("Note updated", rowsAffected));
        });

        delete("/notes", (req, res) -> {
            Note note = gson.fromJson(req.body(), Note.class);
            int rowsAffected = notesService.delete(note);
            res.type("application/json");
            return gson.toJson(new Response("Note deleted", rowsAffected));
        });

        // Module Routes
        post("/modules", (req, res) -> {
            // Assuming request body is JSON with nom, description, credit
            ModuleCreationRequest moduleRequest = gson.fromJson(req.body(), ModuleCreationRequest.class);
            boolean success = moduleService.creerModule(moduleRequest.nom, moduleRequest.description,
                    moduleRequest.credit);
            res.type("application/json");
            return gson.toJson(new Response("Module created", success ? 1 : 0));
        });

        delete("/modules", (req, res) -> {
            Module module = gson.fromJson(req.body(), Module.class);
            boolean success = moduleService.supprimerModule(module);
            res.type("application/json");
            return gson.toJson(new Response("Module deleted", success ? 1 : 0));
        });

        put("/modules/:id", (req, res) -> {
            int id = Integer.parseInt(req.params(":id"));
            ModuleCreationRequest moduleRequest = gson.fromJson(req.body(), ModuleCreationRequest.class);
            boolean success = moduleService.modifierInfoModule(id, moduleRequest.nom, moduleRequest.description,
                    moduleRequest.credit);
            res.type("application/json");
            return gson.toJson(new Response("Module updated", success ? 1 : 0));
        });

        get("/modules/:id", (req, res) -> {
            int id = Integer.parseInt(req.params(":id"));
            Module module = moduleService.getModule(id);
            res.type("application/json");
            return gson.toJson(module);
        });

        get("/modules", (req, res) -> {
            System.out.println("get on module: " + req.userAgent());
            Set<Module> modules = moduleService.getModules();
            res.type("application/json");
            return gson.toJson(modules);
        });

        get("/modules/:id/recap", (req, res) -> {
            int moduleId = Integer.parseInt(req.params(":id"));
            var recap = recapService.genererRecapModule(moduleId);
            res.type("application/json");
            return gson.toJson(recap);
        });

        get("/modules/:id/recap/csv", (req, res) -> {
            int moduleId = Integer.parseInt(req.params(":id"));
            var recap = recapService.genererRecapModule(moduleId);

            // Build CSV content
            StringBuilder csv = new StringBuilder();
            csv.append("Nom,Prenom,Matricule,CC,TP,SN,Moyenne Generale\n");

            for (var r : recap) {
                csv.append(String.format("%s,%s,%s,%.2f,%.2f,%.2f,%.2f\n",
                        r.etudiant().nom(),
                        r.etudiant().prenom(),
                        r.etudiant().matricule(),
                        r.moyenneCC(),
                        r.moyenneTP(),
                        r.moyenneSN(),
                        r.moyenneGenerale()));
            }

            res.type("text/csv");
            res.header("Content-Disposition", "attachment; filename=recap-module-" + moduleId + ".csv");
            return csv.toString();
        });

        exception(Exception.class, (exception, request, response) -> {
            response.status(500);
            response.body(gson.toJson(new ErrorResponse(exception.getMessage())));
        });
    }

    // Helper classes for responses
    static class Response {
        public String message;
        public int rowsAffected;

        public Response(String message, int rowsAffected) {
            this.message = message;
            this.rowsAffected = rowsAffected;
        }

        public Response(String message, boolean success) {
            this.message = message;
            this.rowsAffected = success ? 1 : 0;
        }
    }

    static class ErrorResponse {
        public String error;

        public ErrorResponse(String error) {
            this.error = error;
        }
    }

    static class ModuleCreationRequest {
        public String nom;
        public String description;
        public int credit;
    }
}

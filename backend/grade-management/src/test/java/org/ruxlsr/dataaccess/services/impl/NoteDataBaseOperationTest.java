package org.ruxlsr.dataaccess.services.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.ruxlsr.dataaccess.services.DataBaseOperation;
import org.ruxlsr.etudiant.model.Etudiant;
import org.ruxlsr.evaluation.model.Evaluation;
import org.ruxlsr.evaluation.model.Note;
import org.ruxlsr.evaluation.model.EvaluationType;

import java.security.Timestamp;
import java.util.*;

class NoteDataBaseOperationTest {
    static DataBaseOperation<Note> noteDbOp ;
    static DataBaseOperation<Evaluation> evalutionOp;
    static DataBaseOperation<Etudiant> etudiantOp;

    @BeforeAll
    static void init(){
        noteDbOp = new NoteDataBaseOperation();
        evalutionOp = new EvaluationDataBaseOperation();
        etudiantOp = new EtudiantDatabaseOperation();

    }

    @Test
    @Order(1)
    void createEntities() {
        DataBaseOperation<Note> noteDbOp = new NoteDataBaseOperation();
        int rowCount = 0;
        List<Evaluation> evaluationList = new ArrayList<>(evalutionOp.getRecords());
        List<Etudiant> etudiantList = new ArrayList<>(etudiantOp.getRecords());
        etudiantOp.createEntities(new Etudiant(null, "neuhane", "blitz", "zzzzzzz"));
        // Créer une nouvelle Note
        Note newNote = new Note(evaluationList.getLast().id(), etudiantList.getLast().id(), 15.5f);

        // Appel de la méthode createEntities
        rowCount = noteDbOp.createEntities(newNote);

        // Vérifier que l'insertion a bien eu lieu (ligne affectée)
        Assertions.assertEquals(1, rowCount, "should return one row added for one Note");
    }

    @Test
    @Order(2)
    void getRecords() {
        DataBaseOperation<Note> noteDbOp = new NoteDataBaseOperation();

        // Récupérer les notes
        Set<Note> notes = noteDbOp.getRecords();

        // Vérifier que la taille du résultat n'est pas vide
        Assertions.assertNotEquals(0, notes.size(), "The set of notes should not be empty.");
    }

    @Test
    @Order(3)
    void update() {
        DataBaseOperation<Note> noteDbOp = new NoteDataBaseOperation();
        List<Note> notes = new ArrayList<>(noteDbOp.getRecords());

        // Mettre à jour la Note
        Note updatedNote = new Note(notes.getLast().evaluationId(), notes.getLast().etudiantId(), 18.0f);
        int rowCount = noteDbOp.update(updatedNote);

        // Vérifier qu'une ligne a bien été mise à jour
        Assertions.assertNotEquals(0, rowCount, "The note should have been updated successfully.");
    }

    @Test
    @Order(4)
    void delete() {
        DataBaseOperation<Note> noteDbOp = new NoteDataBaseOperation();
        List<Note> notes = new ArrayList<>(noteDbOp.getRecords());

        // Ajouter une Note à supprimer
        Note newNote = new Note(notes.getLast().evaluationId(), notes.getLast().etudiantId(), 0.0f);

        // Supprimer la Note
        int rowCount = noteDbOp.delete(newNote);

        // Vérifier que la ligne a bien été supprimée
        Assertions.assertNotEquals(0, rowCount, "The note should have been deleted successfully.");
    }
}

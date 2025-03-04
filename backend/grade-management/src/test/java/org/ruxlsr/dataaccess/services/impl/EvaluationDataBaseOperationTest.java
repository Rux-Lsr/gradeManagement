package org.ruxlsr.dataaccess.services.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.ruxlsr.dataaccess.services.DataBaseOperation;
import org.ruxlsr.evaluation.model.Evaluation;
import org.ruxlsr.evaluation.model.EvaluationType;
import org.ruxlsr.module.model.Module;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

class EvaluationDataBaseOperationTest {

    @Test
    @Order(1)
    void createEntities() {
        DataBaseOperation<Evaluation> evaluationDbOp = new EvaluationDataBaseOperation();
        int rowCount = 0;
        DataBaseOperation<Module> moduleDbOp = new ModuleDataBaseOperation();

        List<Module> moduleList = new ArrayList<>(moduleDbOp.getRecords());

        // Créer une nouvelle Evaluation
        Evaluation newEvaluation = new Evaluation(0, moduleList.getFirst().id(), new Timestamp(System.currentTimeMillis()), 2.5f, 20.0f, EvaluationType.SN);

        // Appel de la méthode createEntities
        rowCount = evaluationDbOp.createEntities(newEvaluation);

        // Vérifier que l'insertion a bien eu lieu (ligne affectée)
        Assertions.assertEquals(1, rowCount, "should return one row added for one Evaluation");
    }

    @Test
    @Order(2)
    void getRecords() {
        DataBaseOperation<Evaluation> evaluationDbOp = new EvaluationDataBaseOperation();

        // Ajouter une Evaluation avant de récupérer
        Evaluation newEvaluation = new Evaluation(0, 2, new Timestamp(System.currentTimeMillis()), 3.0f, 30.0f, EvaluationType.CC);
        evaluationDbOp.createEntities(newEvaluation);

        // Récupérer les evaluations
        Set<Evaluation> evaluations = evaluationDbOp.getRecords();

        // Vérifier que la taille du résultat n'est pas vide
        Assertions.assertNotEquals(0, evaluations.size(), "The set of evaluations should not be empty.");
    }

    @Test
    @Order(3)
    void update() {
        DataBaseOperation<Evaluation> evaluationDbOp = new EvaluationDataBaseOperation();
        List<Evaluation> evaluations = new ArrayList<>(evaluationDbOp.getRecords());

        DataBaseOperation<Module> moduleDbOp = new ModuleDataBaseOperation();
        List<Module> moduleList = new ArrayList<>(moduleDbOp.getRecords());

        // Mettre à jour l'Evaluation
        Evaluation updatedEvaluation = new Evaluation(evaluations.getLast().id(), moduleList.getLast().id(), new Timestamp(System.currentTimeMillis()), 4.0f, 40.0f, EvaluationType.SN);
        int rowCount = evaluationDbOp.update(updatedEvaluation);

        // Vérifier qu'une ligne a bien été mise à jour
        Assertions.assertNotEquals(0, rowCount, "The evaluation should have been updated successfully.");
    }

    @Test
    @Order(4)
    void delete() {
        DataBaseOperation<Evaluation> evaluationDbOp = new EvaluationDataBaseOperation();
        List<Evaluation> evaluations = new ArrayList<>(evaluationDbOp.getRecords());

        DataBaseOperation<Module> moduleDbOp = new ModuleDataBaseOperation();
        List<Module> moduleList = new ArrayList<>(moduleDbOp.getRecords());

        // Ajouter une Evaluation à supprimer
        Evaluation newEvaluation = new Evaluation(evaluations.getLast().id(), null, new Timestamp(System.currentTimeMillis()), 1.5f, 15.0f, EvaluationType.SN);

        // Supprimer l'Evaluation
        int rowCount = evaluationDbOp.delete(newEvaluation);

        // Vérifier que la ligne a bien été supprimée
        Assertions.assertNotEquals(0, rowCount, "The evaluation should have been deleted successfully.");
    }
}

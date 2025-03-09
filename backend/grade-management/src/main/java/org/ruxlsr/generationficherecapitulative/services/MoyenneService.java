package org.ruxlsr.generationficherecapitulative.services;

import org.ruxlsr.generationficherecapitulative.model.Moyenne;
import org.ruxlsr.dataaccess.services.DataBaseOperation;
import org.ruxlsr.evaluation.model.Evaluation;
import org.ruxlsr.evaluation.model.EvaluationType;
import org.ruxlsr.evaluation.note.model.Note;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class MoyenneService {
    private final DataBaseOperation<Note> noteDbOp;
    private final DataBaseOperation<Evaluation> evaluationDbOp;

    public MoyenneService(DataBaseOperation<Note> noteDbOp, DataBaseOperation<Evaluation> evaluationDbOp) {
        this.noteDbOp = noteDbOp;
        this.evaluationDbOp = evaluationDbOp;
    }

    public Moyenne calculerMoyenne(int idEtudiant, int moduleId) {
        // Récupérer toutes les notes et évaluations
        Set<Note> notes = noteDbOp.getRecords();
        Set<Evaluation> evaluations = evaluationDbOp.getRecords();

        // Filtrer les notes de l'étudiant et les associer aux évaluations
        Map<EvaluationType, List<Float>> notesByType = notes.stream()
                .filter(note -> note.etudiantId() == idEtudiant)
                .filter(note -> {
                    // Filtrer les notes par moduleId
                    Evaluation eval = evaluations.stream()
                            .filter(e -> e.id().equals(note.evaluationId()))
                            .findFirst()
                            .orElse(null);
                    return eval != null && eval.moduleId() == moduleId;
                })
                .map(note -> {
                    // Associer chaque note avec son type d'évaluation
                    Evaluation eval = evaluations.stream()
                            .filter(e -> e.id().equals(note.evaluationId()))
                            .findFirst()
                            .orElse(null);
                    return new Object() {
                        final EvaluationType type = eval.evaluationType();
                        final float noteValue = (note.note() / eval.max()) * 20; // Normalisation sur 20
                        final float coef = eval.coef();
                        final float noteFinale = noteValue * coef;
                    };
                })
                .collect(Collectors.groupingBy(
                        o -> o.type,
                        Collectors.mapping(o -> o.noteFinale, Collectors.toList())));

        // Calculer les moyennes par type
        float moyenneCC = calculateAverage(notesByType.getOrDefault(EvaluationType.CC, List.of()));
        float moyenneTP = calculateAverage(notesByType.getOrDefault(EvaluationType.TP, List.of()));
        float moyenneSN = calculateAverage(notesByType.getOrDefault(EvaluationType.SN, List.of()));

        return new Moyenne(idEtudiant, moyenneCC, moyenneTP, moyenneSN);
    }

    private float calculateAverage(List<Float> notes) {
        if (notes.isEmpty())
            return 0f;
        return (float) notes.stream()
                .mapToDouble(Float::doubleValue)
                .average()
                .orElse(0.0);
    }
}
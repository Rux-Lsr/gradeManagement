package org.ruxlsr.generationficherecapitulative;

import java.util.List;

import org.ruxlsr.dataaccess.services.DataBaseOperation;
import org.ruxlsr.dataaccess.services.impl.EvaluationDataBaseOperation;
import org.ruxlsr.dataaccess.services.impl.ModuleDataBaseOperation;
import org.ruxlsr.dataaccess.services.impl.NoteDataBaseOperation;
import org.ruxlsr.evaluation.model.Evaluation;
import org.ruxlsr.evaluation.model.Note;
import org.ruxlsr.generationficherecapitulative.model.RecapModule;
import org.ruxlsr.generationficherecapitulative.services.impl.RecapService;

public class Main {
    public static void main(String[] args) {
        // Example usage
        DataBaseOperation moduleDbOp = new ModuleDataBaseOperation();
        DataBaseOperation<Note> noteDbOp = new NoteDataBaseOperation();
        DataBaseOperation<Evaluation> evalDbOp = new EvaluationDataBaseOperation();

        RecapService recapService = new RecapService(moduleDbOp, noteDbOp, evalDbOp);
        List<RecapModule> recap = recapService.genererRecapModule(15);

        // Print the recap
        recap.forEach(r -> System.out.printf(
                "Étudiant: %s %s - CC: %.2f, TP: %.2f, SN: %.2f, Moyenne: %.2f%n",
                r.etudiant().nom(),
                r.etudiant().prenom(),
                r.moyenneCC(),
                r.moyenneTP(),
                r.moyenneSN(),
                r.moyenneGenerale()));
    }
}

package org.ruxlsr.calculmoyenne;

import org.ruxlsr.calculmoyenne.model.Moyenne;
import org.ruxlsr.calculmoyenne.services.MoyenneService;
import org.ruxlsr.dataaccess.services.DataBaseOperation;
import org.ruxlsr.dataaccess.services.impl.EvaluationDataBaseOperation;
import org.ruxlsr.dataaccess.services.impl.NoteDataBaseOperation;
import org.ruxlsr.evaluation.model.Evaluation;
import org.ruxlsr.evaluation.model.Note;

public class Main {
    public static void main(String[] args) {
        // Example usage
        DataBaseOperation<Note> noteDbOp = new NoteDataBaseOperation();
        DataBaseOperation<Evaluation> evalDbOp = new EvaluationDataBaseOperation();
        MoyenneService moyenneService = new MoyenneService(noteDbOp, evalDbOp);

        Moyenne moyenne = moyenneService.calculerMoyenne(19, 15);
        System.out.printf("CC: %.2f, TP: %.2f, SN: %.2f%n",
                moyenne.noteCC(), moyenne.noteTP(), moyenne.noteSN());
    }
}

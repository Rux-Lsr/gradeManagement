package org.ruxlsr.evaluation.services.impl;

import java.util.Set;

import org.ruxlsr.dataaccess.services.DataBaseOperation;
import org.ruxlsr.evaluation.model.Evaluation;
import org.ruxlsr.evaluation.model.Note;
import org.ruxlsr.evaluation.services.IEvaluationService;

public class EvaluationService implements IEvaluationService{
    DataBaseOperation<Evaluation> evalDbOp;

    EvaluationService(DataBaseOperation<Evaluation> dataBaseOperation){
        evalDbOp = dataBaseOperation;
    }

    @Override
    public int addEvaluation(Evaluation evaluation) {
        return evalDbOp.createEntities(evaluation);
    }

    @Override
    public int delete(Evaluation evaluation) {
        return evalDbOp.delete(evaluation);
    }

    @Override
    public int update(Evaluation evaluation) {
        return evalDbOp.update(evaluation);
    }

    @Override
    public Set<Evaluation> getEvaluation() {
        return evalDbOp.getRecords();
    }
}

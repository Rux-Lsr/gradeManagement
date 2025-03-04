package org.ruxlsr.evaluation.services;

import java.util.List;
import java.util.Set;

import org.ruxlsr.evaluation.model.Evaluation;
import org.ruxlsr.evaluation.model.Note;

public interface IEvaluationService {
    
    public int addEvaluation(Evaluation evaluation);
    public int delete(Evaluation evaluation);
    public int update(Evaluation evaluation);
    public Set<Evaluation> getEvaluation();
}

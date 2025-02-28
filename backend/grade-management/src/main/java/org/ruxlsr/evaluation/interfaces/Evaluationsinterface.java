package org.ruxlsr.evaluation.interfaces;

import java.util.List;

import org.ruxlsr.evaluation.model.Evaluation;

public interface Evaluationsinterface {
    
    public Evaluation CreateEvaluation(String date,float coef,float max,String type);
    public void DeleteEvaluation(List<Evaluation> evaluations,int choix);
    public void ModifyEvaluation(List<Evaluation> evaluations,int index,String date,float coef,float max,String type);
    public void SaveNote();
}

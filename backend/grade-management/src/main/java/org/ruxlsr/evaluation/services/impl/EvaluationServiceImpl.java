package org.ruxlsr.evaluation.services.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.ruxlsr.evaluation.model.Evaluation;
import org.ruxlsr.evaluation.services.IEvaluationService;

public class EvaluationServiceImpl implements IEvaluationService{
    

    public EvaluationServiceImpl(){};

    
    @Override
    public  Evaluation CreateEvaluation(String date,float coef,float max,String type){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");//definition du format de la date
        Evaluation evaluation = new Evaluation(LocalDateTime.parse(date, formatter), coef, max, type);
        return evaluation;
    };
    
    @Override
    public void DeleteEvaluation(List<Evaluation> evaluations,int choix){
        evaluations.remove(choix);
        System.out.println("Evaluation retiree");
    };

    
        public void ModifyEvaluation(List<Evaluation> evaluations,int index,String date,float coef,float max,String type){
            evaluations.set(index,CreateEvaluation(date, coef, max, type));
        };




        public void SaveNote(){
            
        };

}

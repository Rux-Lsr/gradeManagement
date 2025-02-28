package org.ruxlsr.evaluation.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.ruxlsr.evaluation.interfaces.Evaluationsinterface;
import org.ruxlsr.evaluation.model.Evaluation;

public class Evaluationcontroller implements Evaluationsinterface{
    

    public Evaluationcontroller(){};

    
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

    
        public void ModifyEvaluation(){};
        public void SaveNote(){}

}

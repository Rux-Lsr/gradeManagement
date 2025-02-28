package org.ruxlsr.evaluation.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.ruxlsr.evaluation.services.Evaluationsinterface;




public class Evaluation {

    private  LocalDateTime date;
    private  float coef;
    private  float max;
    private  String type;


    
public List<String> typeEvaluation = new ArrayList<String>() {{add("CC");add("TP");add("SN");add("Rattrapage");}};

    public Evaluation(){};
    public Evaluation(LocalDateTime date, float coef, float max,String type){
      if(typeEvaluation.contains(type)){
        this.date = date;
        this.coef = coef;
        this.max  = max;
        this.type = type;
        System.out.println("Evaluation cree");
      }else{
        System.out.println("le type "+type+" n'existe pas");
      }
    };

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public float getCoef() {
        return coef;
    }

    public void setCoef(float coef) {
        this.coef = coef;
    }

    public float getMax() {
        return max;
    }

    public String getType() {
        return type;
    }

    public void setMax(float max) {
        this.max = max;
    }

    public void setType(String type) {
        this.type = type;
    }












    
}


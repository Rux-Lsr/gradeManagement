package org.ruxlsr.evaluation.model;



import java.sql.Timestamp;

public record Evaluation(Integer id ,Integer moduleId, Timestamp date, float coef, float max, EvaluationType evaluationType) {

}


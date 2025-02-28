package org.ruxlsr.evaluation.model;

import org.ruxlsr.etudiant.model.Etudiant;

public class Note {

    private Evaluation evaluation;
    private float note;
    //private Etudiant etudiant;


    public Note(/*Etudiant etudiant, */Evaluation evaluation, float note) {
        //this.evaluation = evaluation;
        this.evaluation = evaluation;
        this.note = note;
    }


    public Evaluation getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(Evaluation evaluation) {
        this.evaluation = evaluation;
    }

    public float getNote() {
        return note;
    }

    public void setNote(float note) {
        this.note = note;
    }

/*
    public Etudiant getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }
*/

}

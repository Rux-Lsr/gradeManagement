package org.ruxlsr.enseignant.model;

//package org.ruxlsr.module.Modules;
//package org.ruxlsr.evaluation.Evaluation;

import java.util.ArrayList;
import java.util.List;

public class Enseignant {



    private int id;
    private String nom;
    private String prenom;
    private String password;
    // private List<Module> modules = new ArrayList<>();
    //private List<Evaluation> evaluations = new ArrayList<>();

    public Enseignant() {
    }

    public Enseignant(String nom, String prenom, String password) {
        this.nom = nom;
        this.prenom = prenom;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
//
//    public List<Modules> getModules() {
//        return modules;
//    }
//
//    public void setModules(List<Modules> modules) {
//        this.modules = modules;
//    }
//
//    public List<Evaluation> getEvaluations() {
//        return evaluations;
//    }
//
//    public void setEvaluations(List<Evaluation> evaluations) {
//        this.evaluations = evaluations;
//    }
}

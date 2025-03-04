package org.ruxlsr.module.model;

import org.ruxlsr.etudiant.model.Etudiant;
import org.ruxlsr.evaluation.model.Evaluation;

import java.util.Set;

public class Module {
    private Integer id;
    private String nom;
    private String description;
    private int credit;
    private Set<Evaluation> evaluationSet;
    private Set<Etudiant> etudiantSet;

    // Constructeur
    public Module(Integer id, String nom, String description, int credit, Set<Evaluation> evaluationSet, Set<Etudiant> etudiantSet) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.credit = credit;
        this.evaluationSet = evaluationSet;
        this.etudiantSet = etudiantSet;
    }

    // Getters et Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public Set<Evaluation> getEvaluationSet() {
        return evaluationSet;
    }

    public void setEvaluationSet(Set<Evaluation> evaluationSet) {
        this.evaluationSet = evaluationSet;
    }

    public Set<Etudiant> getEtudiantSet() {
        return etudiantSet;
    }

    public void setEtudiantSet(Set<Etudiant> etudiantSet) {
        this.etudiantSet = etudiantSet;
    }

    // toString pour affichage
    @Override
    public String toString() {
        return "Module{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", credit=" + credit +
                ", evaluationSet=" + evaluationSet +
                ", etudiantSet=" + etudiantSet +
                '}';
    }
}

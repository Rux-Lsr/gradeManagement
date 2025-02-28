package org.ruxlsr.module.model;

import org.ruxlsr.dataaccess.services.DataBaseOperation;
import org.ruxlsr.etudiant.model.Etudiant;

import java.util.Set;

public class Module {
    private int id;

    private String nom;

    private String description;
    private int credit;
    private Set<Etudiant> listeEtudiant;
    public Module(String nom, String description, int credit, Set<Etudiant> etudiants) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.credit = credit;
        this.listeEtudiant = etudiants;

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

    public Set<Etudiant> getListeEtudiant() {
        return listeEtudiant;
    }

    public void setListeEtudiant(Set<Etudiant> listeEtudiant) {
        this.listeEtudiant = listeEtudiant;
    }


}

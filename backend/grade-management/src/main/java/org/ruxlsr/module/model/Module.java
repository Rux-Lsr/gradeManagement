package org.ruxlsr.module.model;

import org.ruxlsr.dataaccess.services.DataBaseOperation;

public class Module {
    private int id;
    private String nom;
    private String description;
    private int credit;

    public Module(int id, String nom, String description, int credit) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.credit = credit;

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


}

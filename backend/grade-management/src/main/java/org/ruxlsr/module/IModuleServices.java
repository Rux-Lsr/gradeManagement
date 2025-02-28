package org.ruxlsr.module;

import org.ruxlsr.etudiant.model.Etudiant;
import org.ruxlsr.module.model.Module;

import java.util.Set;

public interface IModuleServices {

    public boolean creerModule(String nom, String description, int credit, Set<Etudiant> etudiants);
    public boolean supprimerModule(int idModule);
    public boolean modifierInfoModule(int idModule, String nom, String description, int credit, Set<Etudiant> etudiants);
    public Set<Etudiant> getListEtudiantModule(int idModule);
    public Module getModule(int idModule);
}

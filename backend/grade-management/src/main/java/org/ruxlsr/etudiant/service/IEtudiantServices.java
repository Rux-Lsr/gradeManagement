package org.ruxlsr.etudiant.service;

import org.ruxlsr.etudiant.model.Etudiant;

import java.util.Set;

public interface IEtudiantServices {
    void ajouterEtudiant(Etudiant etudiant);
    void supprimerEtudiant(Etudiant etudiant);
    void updateEtudiant(Etudiant etudiant);
    Set<Etudiant> getEtudiants();
}

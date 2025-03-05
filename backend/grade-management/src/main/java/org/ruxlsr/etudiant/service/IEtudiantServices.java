package org.ruxlsr.etudiant.service;

import org.ruxlsr.etudiant.model.Etudiant;

import java.util.Set;

public interface IEtudiantServices {
    int ajouterEtudiant(Etudiant etudiant);
    int supprimerEtudiant(Etudiant etudiant);
    int updateEtudiant(Etudiant etudiant);
    Set<Etudiant> getEtudiants();
}

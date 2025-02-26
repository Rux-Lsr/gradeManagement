package org.ruxlsr.etudiant.service.impl;

import org.ruxlsr.etudiant.model.Etudiant;
import org.ruxlsr.etudiant.service.IEtudiantServices;

import java.util.Set;

public class EtudiantServices implements IEtudiantServices {
    @Override
    public void ajouterEtudiant(Etudiant etudiant) {
        System.out.println("Ajout de l'etudiant");
    }

    @Override
    public void supprimerEtudiant(Etudiant etudiant) {
        System.out.println("suppresison de l'etudiant de l'etudiant");
    }

    @Override
    public void updateEtudiant(Etudiant etudiant) {
        System.out.println("modification de l'etudiant");
    }

    @Override
    public Set<Etudiant> getEtudiants() {
        System.out.println("obtention des l'etudiant");
        return null;
    }
}

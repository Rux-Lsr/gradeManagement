package org.ruxlsr.enseignant.services;

import org.ruxlsr.enseignant.model.Enseignant;

import java.util.List;
import java.util.Set;

public interface IEnseignantService {

    int enregistrerEnseignant(Enseignant enseignant);  // Retourne le nombre de lignes affecte
    int supprimerEnseignant(Enseignant enseignant);    // Retourne le nombre de lignes affectées
    Set<Enseignant> recupererListeEnseignants();
}

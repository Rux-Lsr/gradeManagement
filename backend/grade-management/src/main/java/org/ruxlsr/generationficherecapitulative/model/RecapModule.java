package org.ruxlsr.generationficherecapitulative.model;

import org.ruxlsr.etudiant.model.Etudiant;

public record RecapModule(
        Etudiant etudiant,
        float moyenneCC,
        float moyenneTP,
        float moyenneSN,
        float moyenneGenerale) {
}

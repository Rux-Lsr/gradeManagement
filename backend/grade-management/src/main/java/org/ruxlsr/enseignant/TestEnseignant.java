package org.ruxlsr.enseignant;

import org.ruxlsr.dataaccess.services.impl.EnseignantDataBaseOperation;
import org.ruxlsr.enseignant.model.Enseignant;
import org.ruxlsr.enseignant.services.IEnseignantService;
import org.ruxlsr.enseignant.services.impl.EnseignantServiceImpl;

public class TestEnseignant {



    public static void main(String[] args) {
        // Création des objets nécessaires
        EnseignantDataBaseOperation<Enseignant> dbOps = new EnseignantDataBaseOperation<>();
        IEnseignantService service = new EnseignantServiceImpl(dbOps);

        // Test d'enregistrement d'un enseignant
        Enseignant prof = new Enseignant("kamga", "marilane", "password123");
        int result = service.enregistrerEnseignant(prof);
        System.out.println("Résultat de l'enregistrement : " + result + " ligne(s) affectée(s)");

        // Test de récupération des enseignants
        System.out.println("Liste des enseignants : ");
        for (Enseignant e : service.recupererListeEnseignants()) {
            System.out.println(e.getNom() + " " + e.getPrenom());
        }

        // Test de suppression
        result = service.supprimerEnseignant(prof);
        System.out.println("Résultat de la suppression : " + result + " ligne(s) affectée(s)");
    }


}

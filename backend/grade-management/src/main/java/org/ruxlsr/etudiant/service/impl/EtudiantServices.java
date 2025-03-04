package org.ruxlsr.etudiant.service.impl;

import org.ruxlsr.dataaccess.services.DataBaseOperation;
import org.ruxlsr.etudiant.model.Etudiant;
import org.ruxlsr.etudiant.service.IEtudiantServices;

import java.util.Set;

public class EtudiantServices implements IEtudiantServices {
    DataBaseOperation<Etudiant> etudiantDbOp;
    EtudiantServices(DataBaseOperation<Etudiant> etudiantDbOp){
        this.etudiantDbOp = etudiantDbOp;
    }
    @Override
    public void ajouterEtudiant(Etudiant etudiant) {
         etudiantDbOp.createEntities(etudiant);
    }

    @Override
    public void supprimerEtudiant(Etudiant etudiant) {
         etudiantDbOp.delete(etudiant);
    }

    @Override
    public void updateEtudiant(Etudiant etudiant) {
          etudiantDbOp.update(etudiant);
    }

    @Override
    public Set<Etudiant> getEtudiants() {
        return etudiantDbOp.getRecords();
    }
}

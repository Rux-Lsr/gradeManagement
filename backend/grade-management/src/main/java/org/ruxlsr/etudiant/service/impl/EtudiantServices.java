package org.ruxlsr.etudiant.service.impl;

import org.ruxlsr.dataaccess.services.DataBaseOperation;
import org.ruxlsr.etudiant.model.Etudiant;
import org.ruxlsr.etudiant.service.IEtudiantServices;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class EtudiantServices implements IEtudiantServices {
    DataBaseOperation<Etudiant> etudiantDbOp;
    public EtudiantServices(DataBaseOperation<Etudiant> etudiantDbOp){
        this.etudiantDbOp = etudiantDbOp;
    }
    @Override
    public int ajouterEtudiant(Etudiant etudiant) {
        List<Etudiant> etudiants = new ArrayList<>(getEtudiants());
        AtomicInteger res = new AtomicInteger(1);
        etudiants.forEach(e -> {
            if(Objects.equals(e.matricule(), etudiant.matricule())){
                res.set(0);
            }
        });
        if(res.get() == 0){
            return 0;
        }
        return etudiantDbOp.createEntities(etudiant);
    }

    @Override
    public int supprimerEtudiant(Etudiant etudiant) {
        return etudiantDbOp.delete(etudiant);
    }

    @Override
    public int updateEtudiant(Etudiant etudiant) {
        return etudiantDbOp.update(etudiant);
    }

    @Override
    public Set<Etudiant> getEtudiants() {
        return etudiantDbOp.getRecords();
    }
}

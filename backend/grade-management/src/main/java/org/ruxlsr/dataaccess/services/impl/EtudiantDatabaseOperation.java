package org.ruxlsr.dataaccess.services.impl;

import org.ruxlsr.dataaccess.services.DataBaseOperation;

import java.util.Set;

public class EtudiantDatabaseOperation<Etudiant> implements DataBaseOperation<Etudiant> {

    @Override
    public Set<Etudiant> getRecords() {
        return Set.of();
    }

    @Override
    public int createEntities(Etudiant etudiant) {
        return 0;
    }

    @Override
    public int update(Etudiant etudiant) {
        return 0;
    }

    @Override
    public int delete(Etudiant etudiant) {
        return 0;
    }
}

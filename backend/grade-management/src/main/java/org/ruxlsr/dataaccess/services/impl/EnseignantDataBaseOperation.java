package org.ruxlsr.dataaccess.services.impl;

import org.ruxlsr.dataaccess.services.DataBaseOperation;

import java.util.Set;

public class EnseignantDataBaseOperation<Enseignant> implements DataBaseOperation<Enseignant> {


    @Override
    public Set<Enseignant> getRecords() {
        return Set.of();
    }

    @Override
    public int createEntities(Enseignant enseignant) {
        return 0;
    }

    @Override
    public int update(Enseignant enseignant) {
        return 0;
    }

    @Override
    public int delete(Enseignant enseignant) {
        return 0;
    }
}

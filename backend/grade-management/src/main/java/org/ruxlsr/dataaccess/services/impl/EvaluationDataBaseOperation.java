package org.ruxlsr.dataaccess.services.impl;

import org.ruxlsr.dataaccess.services.DataBaseOperation;

import java.util.Set;

public class EvaluationDataBaseOperation<Evaluation> implements DataBaseOperation<Evaluation> {

    @Override
    public Set<Evaluation> getRecords() {
        return Set.of();
    }

    @Override
    public int createEntities(Evaluation evaluation) {
        return 0;
    }

    @Override
    public int update(Evaluation evaluation) {
        return 0;
    }

    @Override
    public int delete(Evaluation evaluation) {
        return 0;
    }
}

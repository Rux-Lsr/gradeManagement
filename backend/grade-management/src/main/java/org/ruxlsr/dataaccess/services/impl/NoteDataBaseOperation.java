package org.ruxlsr.dataaccess.services.impl;

import org.ruxlsr.dataaccess.services.DataBaseOperation;

import java.util.Set;

public class NoteDataBaseOperation<Note> implements DataBaseOperation<Note> {

    @Override
    public Set<Note> getRecords() {
        return Set.of();
    }

    @Override
    public int createEntities(Note Note) {
        return 0;
    }

    @Override
    public int update(Note Note) {
        return 0;
    }

    @Override
    public int delete(Note Note) {
        return 0;
    }
}

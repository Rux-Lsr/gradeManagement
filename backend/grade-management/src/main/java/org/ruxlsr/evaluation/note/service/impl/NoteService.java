package org.ruxlsr.evaluation.note.service.impl;

import org.ruxlsr.dataaccess.services.DataBaseOperation;
import org.ruxlsr.evaluation.note.model.Note;
import org.ruxlsr.evaluation.note.service.INotesService;

import java.util.Set;

public class NoteService implements INotesService {
    DataBaseOperation<Note> noteDbOp;

    public NoteService(DataBaseOperation<Note> dataBaseOperation){
        noteDbOp = dataBaseOperation;
    }

    @Override
    public int createNote(Note note) {
        return noteDbOp.createEntities(note);
    }

    @Override
    public Set<Note> getNote() {
        return noteDbOp.getRecords();
    }

    @Override
    public int update(Note note) {
        return noteDbOp.update(note);
    }

    @Override
    public int delete(Note note) {
        return noteDbOp.delete(note);
    }
}

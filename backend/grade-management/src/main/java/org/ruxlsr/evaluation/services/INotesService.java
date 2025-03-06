package org.ruxlsr.evaluation.services;

import java.util.Set;

import org.ruxlsr.evaluation.model.Note;

public interface INotesService {
    public int createNote(Note note);
    public Set<Note> getNote();
    public int update(Note note);
    public int delete(Note note);
}


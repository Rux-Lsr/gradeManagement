package org.ruxlsr.evaluation.note.service;

import org.ruxlsr.evaluation.note.model.Note;

import java.util.Set;

public interface INotesService {
    public int createNote(Note note);
    public Set<Note> getNote();
    public int update(Note note);
    public int delete(Note note);
}


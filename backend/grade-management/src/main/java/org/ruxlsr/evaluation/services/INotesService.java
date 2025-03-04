package org.ruxlsr.evaluation.services;

import java.util.List;

import org.ruxlsr.evaluation.model.Evaluation;
import org.ruxlsr.evaluation.services.impl.NoteServiceImpl;

public interface INotesService {
    public NoteServiceImpl SaveNote(/*Etudiant etudiant,*/Evaluation evaluation ,float note);
}
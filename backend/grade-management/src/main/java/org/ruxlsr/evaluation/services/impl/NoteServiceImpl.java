package org.ruxlsr.evaluation.services.impl;

import org.ruxlsr.evaluation.model.Evaluation;
import org.ruxlsr.evaluation.model.Note;
import org.ruxlsr.evaluation.services.INotesService;

public class NoteServiceImpl  implements INotesService {

    public NoteServiceImpl(){};
    
    
    @Override
    public Note SaveNote(Etudiant etudiant,Evaluation evaluation ,float note){
        
        Note note = new Note(etudiant,evaluation ,note);
        return note;
    };
    

}

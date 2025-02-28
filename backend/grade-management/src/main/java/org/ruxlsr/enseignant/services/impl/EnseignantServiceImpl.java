package org.ruxlsr.enseignant.services.impl;

import org.ruxlsr.dataaccess.services.DataBaseOperation;
import org.ruxlsr.dataaccess.services.impl.EnseignantDataBaseOperation;
import org.ruxlsr.enseignant.model.Enseignant;
import org.ruxlsr.enseignant.services.IEnseignantService;

import java.util.Set;

public class EnseignantServiceImpl  implements IEnseignantService {

    private final DataBaseOperation<Enseignant> dbOperation;

    // Constructeur qui reçoit l'objet pour interagir avec ma base de données
    public EnseignantServiceImpl(DataBaseOperation<Enseignant> dbOperation) {
        this.dbOperation = dbOperation;
    }

    @Override
    public int enregistrerEnseignant(Enseignant enseignant) {
        if (enseignant.id() == 0) {
            // Nouvel enseignant
            return dbOperation.createEntities(enseignant);
        } else {
            // Mise à jour d'un enseignant existant
            return dbOperation.update(enseignant);
        }
    }

    @Override
    public int supprimerEnseignant(Enseignant enseignant) {
        return dbOperation.delete(enseignant);
    }

    @Override
    public Set<Enseignant> recupererListeEnseignants() {
        return dbOperation.getRecords();
    }


}

package org.ruxlsr.enseignant.services.impl;

import org.ruxlsr.dataaccess.services.DataBaseOperation;
import org.ruxlsr.enseignant.model.Enseignant;
import org.ruxlsr.enseignant.services.IEnseignantService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class EnseignantServiceImpl  implements IEnseignantService {

    private final DataBaseOperation<Enseignant> dbOperation;

    // Constructeur qui reçoit l'objet pour interagir avec ma base de données
    public EnseignantServiceImpl(DataBaseOperation<Enseignant> dbOperation) {
        this.dbOperation = dbOperation;
    }

    @Override
    public int enregistrerEnseignant(Enseignant enseignant) {
        List<Enseignant> enseignantList = new ArrayList<>(recupererListeEnseignants());
        AtomicInteger res = new AtomicInteger(1  );

        enseignantList.forEach((e -> {
            if(e.nom().equals(enseignant.nom())  && e.prenom().equals(enseignant.prenom()) ){
                res.set(0);
                System.out.println(e);
            }

        }));
        if(res.get() == 0)
            return 0;
        return dbOperation.createEntities(enseignant);
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

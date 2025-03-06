package org.ruxlsr.generationficherecapitulative.services.impl;

import org.ruxlsr.calculmoyenne.model.Moyenne;
import org.ruxlsr.calculmoyenne.services.MoyenneService;
import org.ruxlsr.dataaccess.services.DataBaseOperation;
import org.ruxlsr.etudiant.model.Etudiant;
import org.ruxlsr.evaluation.model.Evaluation;
import org.ruxlsr.evaluation.model.Note;
import org.ruxlsr.generationficherecapitulative.model.RecapModule;
import org.ruxlsr.generationficherecapitulative.services.IRecapService;
import org.ruxlsr.module.model.Module;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class RecapService implements IRecapService {

        private final DataBaseOperation<Module> moduleDbOp;
        private final MoyenneService moyenneService;

        public RecapService(DataBaseOperation<Module> moduleDbOp,
                        DataBaseOperation<Note> noteDbOp,
                        DataBaseOperation<Evaluation> evalDbOp) {
                this.moduleDbOp = moduleDbOp;
                this.moyenneService = new MoyenneService(noteDbOp, evalDbOp);
        }

        @Override
        public List<RecapModule> genererRecapModule(int moduleId) {
                // Récupérer le module et ses étudiants
                Module module = moduleDbOp.getRecords().stream()
                                .filter(m -> m.getId() == moduleId)
                                .findFirst()
                                .orElseThrow(() -> new IllegalArgumentException("Module non trouvé"));

                Set<Etudiant> etudiants = module.getEtudiantSet();

                // Pour chaque étudiant, calculer ses moyennes
                return etudiants.stream()
                                .map(etudiant -> {
                                        Moyenne moyenne = moyenneService.calculerMoyenne(etudiant.id(), moduleId);

                                        // Calculer la moyenne générale (moyenne pondérée des trois types)
                                        float moyenneGenerale = (moyenne.noteCC() * 0.4f +
                                                        moyenne.noteTP() * 0.2f +
                                                        moyenne.noteSN() * 0.4f);

                                        return new RecapModule(
                                                        etudiant,
                                                        moyenne.noteCC(),
                                                        moyenne.noteTP(),
                                                        moyenne.noteSN(),
                                                        moyenneGenerale);
                                })
                                .sorted((r1, r2) -> Float.compare(r2.moyenneGenerale(), r1.moyenneGenerale()))
                                .collect(Collectors.toList());
        }
}
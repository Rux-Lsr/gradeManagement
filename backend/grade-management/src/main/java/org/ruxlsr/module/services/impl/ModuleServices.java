package org.ruxlsr.module.services.impl;

import org.ruxlsr.dataaccess.services.DataBaseOperation;
import org.ruxlsr.enseignant.model.Enseignant;
import org.ruxlsr.etudiant.model.Etudiant;
import org.ruxlsr.module.IModuleServices;
import org.ruxlsr.module.model.Module;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ModuleServices implements IModuleServices {

    private final DataBaseOperation<Module> moduleDAO;

    public ModuleServices(DataBaseOperation<Module> moduleDAO) {
        this.moduleDAO = moduleDAO;
    }



    @Override
    public boolean creerModule(String nom, String description, int credit) {
        Module module = new Module(null, nom, description, credit, null, null);
        int reponse = moduleDAO.createEntities(module);

        return reponse != 0;
    }

    @Override
    public boolean supprimerModule(Module module) {
        int res = 0;
        res = moduleDAO.delete(module);

        return res != 0;
    }

    @Override
    public boolean modifierInfoModule(int idModule, String nom, String description, int credit) {
        int res = 0;
       res = moduleDAO.update(new Module(idModule, nom, description, credit, null, null));
       return res != 0;
    }


    @Override
    public Module getModule(int idModule) {
        List<Module> modules = new ArrayList<>(moduleDAO.getRecords());
        Module modTmp = null;
        for(Module module : modules){
            modTmp = module;
            if(module.getId() == idModule){
                break;
            }
        }
        return modTmp;
    }
}

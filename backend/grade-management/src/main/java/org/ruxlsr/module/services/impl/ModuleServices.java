package org.ruxlsr.module.services.impl;

import org.ruxlsr.dataaccess.services.DataBaseOperation;
import org.ruxlsr.etudiant.model.Etudiant;
import org.ruxlsr.module.IModuleServices;
import org.ruxlsr.module.model.Module;

import java.util.Set;

public class ModuleServices implements IModuleServices {

    private DataBaseOperation moduleDAO;
    private Set<Module> modules;

    public ModuleServices(DataBaseOperation moduleDAO) {
        this.moduleDAO = moduleDAO;
        this.upDateData();
    }

    private void upDateData(){
        this.modules = moduleDAO.getRecords();
    }

    @Override
    public boolean creerModule(String nom, String description, int credit, Set<Etudiant> etudiants) {
        Module module = new Module(nom, description, credit, etudiants);
        int reponse = moduleDAO.createEntities(module);
        if(reponse == 0){
            this.upDateData();
            return true;
        }
        return false;
    }

    @Override
    public boolean supprimerModule(int idModule) {
        Module modTmp = this.getModule(idModule);
        if(modTmp != null){
            int reponse = moduleDAO.delete(modTmp);

            if(reponse == 0){
                this.upDateData();
                return  true;
            }
        }

        return false;
    }

    @Override
    public boolean modifierInfoModule(int idModule, String nom, String description, int credit, Set<Etudiant> etudiants) {
        Module modTmp = this.getModule(idModule);
        if(modTmp != null){
            modTmp.setNom(nom);
            modTmp.setDescription(description);
            modTmp.setCredit(credit);
            modTmp.setListeEtudiant(etudiants);

            int reponse = moduleDAO.update(modTmp);
            if(reponse == 0){
                this.upDateData();
                return  true;
            }
        }
        return false;
    }

    @Override
    public Set<Etudiant> getListEtudiantModule(int idModule) {
        Module modTmp = this.getModule(idModule);
        Set<Etudiant> etudiants = Set.of();
        if(modTmp != null){
            etudiants = modTmp.getListeEtudiant();
        }
        return etudiants;
    }

    @Override
    public Module getModule(int idModule) {

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

package org.ruxlsr.module.services;

import org.ruxlsr.module.model.Module;

import java.util.Set;

public interface IModuleServices {

    public boolean creerModule(String nom, String description, int credit);
    public boolean supprimerModule(Module module);
    public boolean modifierInfoModule(int idModule, String nom, String description, int credit);
    public Module getModule(int idModule);
    public Set<Module> getModules();
}

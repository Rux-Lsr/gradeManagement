package org.ruxlsr.module.services.impl;

import org.ruxlsr.module.IModuleServices;

public class ModuleServices implements IModuleServices {
    @Override
    public void creerModule(String nom, String description, int credit) {

    }

    @Override
    public boolean supprimerModule(int idModule) {
        return false;
    }

    @Override
    public boolean modifierInfoModule(int idModule, String nom, String description, int credit) {
        return false;
    }
}

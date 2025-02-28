package org.ruxlsr.module;

public interface IModuleServices {

    public void creerModule(String nom, String description, int credit);
    public boolean supprimerModule(int idModule);
    public boolean modifierInfoModule(int idModule, String nom, String description, int credit);
}

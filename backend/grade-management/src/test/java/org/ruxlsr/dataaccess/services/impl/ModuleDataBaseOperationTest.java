package org.ruxlsr.dataaccess.services.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.ruxlsr.dataaccess.services.DataBaseOperation;
import org.ruxlsr.module.model.Module;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ModuleDataBaseOperationTest {

    @Test
    @Order(1)
    void createEntities() {
        DataBaseOperation<Module> moduleDbOp = new ModuleDataBaseOperation();
        int rowCount = 0;
        Module newModule = new Module(0, "Mathématiques", "Cours de mathématiques avancées", 5);
        rowCount = moduleDbOp.createEntities(newModule);

        // Assert que la ligne a bien été ajoutée
        Assertions.assertEquals(1, rowCount, "should return one row added for one Module");
    }

    @Test
    @Order(2)
    void getRecords() {
        DataBaseOperation<Module> moduleDbOp = new ModuleDataBaseOperation();

        // Ajouter un module avant de récupérer
        Module newModule = new Module(0, "Informatique", "Cours d'introduction à l'informatique", 4);
        moduleDbOp.createEntities(newModule);

        Set<Module> modules = moduleDbOp.getRecords();

        // Vérifier que la taille du résultat n'est pas vide
        Assertions.assertNotEquals(0, modules.size(), "The set of modules should not be empty.");
    }

    @Test
    @Order(3)
    void update() {
        DataBaseOperation<Module> moduleDbOp = new ModuleDataBaseOperation();

        // Ajouter un module à modifier
        Module newModule = new Module(2, "Physique", "Cours de physique générale", 4);
        moduleDbOp.createEntities(newModule);

        // Mettre à jour le module
        Module updatedModule = new Module(newModule.id(), "Physique avancée", "Cours de physique avancée", 6);
        int rowCount = moduleDbOp.update(updatedModule);

        // Vérifier qu'une ligne a été mise à jour
        Assertions.assertNotEquals(0, rowCount, "The module should have been updated successfully.");
    }

    @Test
    @Order(4)
    void delete() {
        DataBaseOperation<Module> moduleDbOp = new ModuleDataBaseOperation();

        // Ajouter un module à supprimer
        Module newModule = new Module(3, "Chimie", "Cours de chimie organique", 3);
        moduleDbOp.createEntities(newModule);

        // Supprimer le module
        int rowCount = moduleDbOp.delete(newModule);

        // Vérifier que la ligne a bien été supprimée
        Assertions.assertNotEquals(0, rowCount, "The module should have been deleted successfully.");
    }
}

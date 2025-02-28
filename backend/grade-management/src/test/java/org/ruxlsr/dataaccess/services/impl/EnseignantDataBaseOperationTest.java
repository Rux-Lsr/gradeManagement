package org.ruxlsr.dataaccess.services.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.ruxlsr.dataaccess.services.DataBaseOperation;
import org.ruxlsr.enseignant.model.Enseignant;

import static org.junit.jupiter.api.Assertions.*;

class EnseignantDataBaseOperationTest {

    @Test
    @Order(1)
    void createEntities() {
        DataBaseOperation<Enseignant> enseignantDbOp = new EnseignantDataBaseOperation();
        int rowCount = 0;
        rowCount = enseignantDbOp.createEntities(new Enseignant(3, "neuhane", "blitz", "lekoubou"));
        Assertions.assertEquals(1, rowCount, "should return one row added for one Enseignant");
    }

    @Test
    @Order(2)
    void getRecords() {
        DataBaseOperation<Enseignant> enseignantDbOp = new EnseignantDataBaseOperation();
        int rowCount = 0;
        rowCount = enseignantDbOp.getRecords().size();
        Assertions.assertNotEquals(-1, rowCount);
    }

    @Test
    @Order(3)
    void update() {
        DataBaseOperation<Enseignant> enseignantDbOp = new EnseignantDataBaseOperation();
        int rowCount = 0;
        rowCount = enseignantDbOp.update(new Enseignant(6, "neuhane", "lekoubou", "blitz"));
        Assertions.assertNotEquals(0, rowCount);
    }

    @Test
    @Order(4)
    void delete() {
        DataBaseOperation<Enseignant> enseignantDbOp = new EnseignantDataBaseOperation();
        int rowCount = 0;
        rowCount = enseignantDbOp.delete(new Enseignant(5, null, null, null));
        Assertions.assertNotEquals(0, rowCount);
    }
}
package org.ruxlsr.dataaccess.services.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.ruxlsr.dataaccess.services.DataBaseOperation;
import org.ruxlsr.enseignant.model.Enseignant;
import org.ruxlsr.etudiant.model.Etudiant;

import static org.junit.jupiter.api.Assertions.*;

class EtudiantDatabaseOperationTest {

    @Test
    @Order(1)
    void createEntities() {
        DataBaseOperation<Etudiant> etudiantDbOp = new EtudiantDatabaseOperation();
        int rowCount = 0;
        rowCount = etudiantDbOp.createEntities(new Etudiant(null, "neuhane", "blitz", "zzzzzzz"));
        Assertions.assertEquals(1, rowCount, "should return one row added for one Etudiant");
    }

    @Test
    @Order(2)
    void getRecords() {
        DataBaseOperation<Etudiant> etudiantDbOp = new EtudiantDatabaseOperation();
        int rowCount = 0;
        rowCount = etudiantDbOp.getRecords().size();
        Assertions.assertNotEquals(-1, rowCount, "should return the size of the studentList");
    }

    @Test
    @Order(3)
    void update() {
        DataBaseOperation<Etudiant> etudiantDbOp = new EtudiantDatabaseOperation();
        int rowCount = 0;
        rowCount = etudiantDbOp.update(new Etudiant(5, "neuhane", "blitz", "kkkkk"));
        Assertions.assertEquals(1, rowCount, "should return one row added for one Etudiant");
    }

    @Test
    @Order(4)
    void delete() {
        DataBaseOperation<Etudiant> etudiantDbOp = new EtudiantDatabaseOperation();
        int rowCount = 0;
        rowCount = etudiantDbOp.delete(new Etudiant(6, "neuhane", "blitz", "zzzzzzz"));
        Assertions.assertEquals(1, rowCount, "should return one row added for one Etudiant");
    }
}
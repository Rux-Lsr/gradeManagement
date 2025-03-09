package org.ruxlsr.dataaccess.services.impl;

import org.ruxlsr.dataaccess.services.DataBaseOperation;
import org.ruxlsr.dataaccess.util.MysqlDbConnection;
import org.ruxlsr.etudiant.model.Etudiant;
import org.ruxlsr.evaluation.model.Evaluation;
import org.ruxlsr.evaluation.model.EvaluationType;
import org.ruxlsr.module.model.Module;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ModuleDataBaseOperation implements DataBaseOperation<Module> {
    private static final Logger LOGGER = Logger.getLogger(ModuleDataBaseOperation.class.getName());

    @Override
    public int createEntities(Module module) {
        String sql = "INSERT INTO Modules (nom, description, credit) VALUES (?, ?, ?)";

        try (Connection con = MysqlDbConnection.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, module.getNom());
            stmt.setString(2, module.getDescription());
            stmt.setInt(3, module.getCredit());

            return stmt.executeUpdate();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Insertion du module échouée : " + e.getLocalizedMessage(), e);
        }
        return 0;
    }

    @Override
    public Set<Module> getRecords() {
        Map<Integer, Module> moduleMap = new HashMap<>();

        String sql = """
                SELECT 
                    m.id AS moduleId, m.nom AS moduleNom, m.description AS moduleDesc, m.credit AS moduleCredit,
                    
                    e.id AS evalId, e.date AS evalDate, e.coef AS evalCoef, e.max AS evalMax, e.typeEvaluation AS evalType,
                    
                    etu.id AS etudiantId, etu.nom AS etudiantNom, etu.prenom AS etudiantPrenom, etu.matricule AS etudiantMatricule
                
                FROM Modules m
                LEFT JOIN Evaluation e ON m.id = e.moduleId
                LEFT JOIN Etudiant etu ON m.id = etu.moduleId
                LEFT JOIN Note n ON etu.id = n.etudiantId AND e.id = n.evaluationId
                """;

        try (Connection con = MysqlDbConnection.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int moduleId = rs.getInt("moduleId");

                // Vérifier si le module existe déjà, sinon l’ajouter
                Module module = moduleMap.computeIfAbsent(moduleId, id -> {
                    try {
                        return new Module(
                                id,
                                rs.getString("moduleNom"),
                                rs.getString("moduleDesc"),
                                rs.getInt("moduleCredit"),
                                new HashSet<>(),
                                new HashSet<>()
                        );
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });

                // Ajouter l'évaluation si elle existe
                if (rs.getObject("evalId") != null) {
                    Evaluation evaluation = new Evaluation(
                            rs.getInt("evalId"),
                            moduleId,
                            rs.getTimestamp("evalDate"),
                            rs.getFloat("evalCoef"),
                            rs.getFloat("evalMax"),
                            EvaluationType.valueOf(rs.getString("evalType"))
                    );
                    module.getEvaluationSet().add(evaluation);
                }

                // Ajouter l'étudiant si il existe
                if (rs.getObject("etudiantId") != null) {
                    Etudiant etudiant = new Etudiant(
                            rs.getInt("etudiantId"),
                            rs.getString("etudiantNom"),
                            rs.getString("etudiantPrenom"),
                            rs.getString("etudiantMatricule"),
                            moduleId
                    );
                    module.getEtudiantSet().add(etudiant);
                }
            }

            return new HashSet<>(moduleMap.values());

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Récupération des modules échouée : " + e.getLocalizedMessage(), e);
            return Set.of(); // Important: Return an empty set in case of error
        }
    }

    @Override
    public int update(Module module) {
        String sql = "UPDATE Modules SET nom = ?, description = ?, credit = ? WHERE id = ?";

        try (Connection con = MysqlDbConnection.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, module.getNom());
            stmt.setString(2, module.getDescription());
            stmt.setInt(3, module.getCredit());
            stmt.setInt(4, module.getId());

            return stmt.executeUpdate();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Mise à jour du module échouée : " + e.getLocalizedMessage(), e);
        }
        return 0;
    }

    @Override
    public int delete(Module module) {
        String sql = "DELETE FROM Modules WHERE id = ?";
        String sql2 = "UPDATE  Etudiant set moduleId = NULL";

        try (Connection con = MysqlDbConnection.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
                con.prepareStatement(sql2).executeUpdate();
            stmt.setInt(1, module.getId());
            return stmt.executeUpdate();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Suppression du module échouée : " + e.getLocalizedMessage(), e);
        }
        return 0;
    }
}

package org.ruxlsr.dataaccess.services.impl;

import org.ruxlsr.dataaccess.services.DataBaseOperation;
import org.ruxlsr.dataaccess.util.DatabaseConnection;
import org.ruxlsr.evaluation.model.Evaluation;
import org.ruxlsr.evaluation.model.EvaluationType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EvaluationDataBaseOperation implements DataBaseOperation<Evaluation> {
    private static final Logger LOGGER = Logger.getLogger(EvaluationDataBaseOperation.class.getName());

    @Override
    public int createEntities(Evaluation evaluation) {
        String sql = "INSERT INTO Evaluation (moduleId, date, coef, max, typeEvaluation) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, evaluation.moduleId());
            stmt.setTimestamp(2, evaluation.date());
            stmt.setFloat(3, evaluation.coef());
            stmt.setFloat(4, evaluation.max());
            stmt.setString(5, evaluation.typeEvaluation().name());

            return stmt.executeUpdate();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Insertion d'évaluation échouée : " + e.getLocalizedMessage(), e);
        }
        return 0;
    }

    @Override
    public Set<Evaluation> getRecords() {
        Set<Evaluation> evaluations = new HashSet<>();
        String sql = "SELECT * FROM Evaluation";

        try (Connection con = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet result = stmt.executeQuery()) {

            while (result.next()) {
                Evaluation evaluation = new Evaluation(
                        result.getInt("id"),
                        result.getInt("moduleId"),
                        result.getTimestamp("date"),
                        result.getFloat("coef"),
                        result.getFloat("max"),
                        EvaluationType.valueOf(result.getString("typeEvaluation"))
                );
                evaluations.add(evaluation);
            }
            return evaluations;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Récupération des évaluations échouée : " + e.getLocalizedMessage(), e);
        }
        return Set.of();
    }

    @Override
    public int update(Evaluation evaluation) {
        String sql = "UPDATE Evaluation SET moduleId = ?, date = ?, coef = ?, max = ?, typeEvaluation = ? WHERE id = ?";

        try (Connection con = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, evaluation.moduleId());
            stmt.setTimestamp(2, evaluation.date());
            stmt.setFloat(3, evaluation.coef());
            stmt.setFloat(4, evaluation.max());
            stmt.setString(5, evaluation.typeEvaluation().name());
            stmt.setInt(6, evaluation.id());

            return stmt.executeUpdate();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Mise à jour de l'évaluation échouée : " + e.getLocalizedMessage(), e);
        }
        return 0;
    }

    @Override
    public int delete(Evaluation evaluation) {
        String sql = "DELETE FROM Evaluation WHERE id = ?";

        try (Connection con = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, evaluation.id());
            return stmt.executeUpdate();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Suppression de l'évaluation échouée : " + e.getLocalizedMessage(), e);
        }
        return 0;
    }
}

package org.ruxlsr.dataaccess.services.impl;

import org.ruxlsr.dataaccess.services.DataBaseOperation;
import org.ruxlsr.dataaccess.util.MysqlDbConnection;
import org.ruxlsr.evaluation.note.model.Note;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NoteDataBaseOperation implements DataBaseOperation<Note> {
    private static final Logger LOGGER = Logger.getLogger(NoteDataBaseOperation.class.getName());

    @Override
    public int createEntities(Note note) {
        String sql = "INSERT INTO Note (evaluationId, etudiantId, note) VALUES (?, ?, ?)";

        try (Connection con = MysqlDbConnection.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, note.evaluationId());
            stmt.setInt(2, note.etudiantId());
            stmt.setFloat(3, note.note());

            return stmt.executeUpdate();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Insertion de la note échouée : " + e.getLocalizedMessage(), e);
        }
        return 0;
    }

    @Override
    public Set<Note> getRecords() {
        Set<Note> notes = new HashSet<>();
        String sql = "SELECT * FROM Note";

        try (Connection con = MysqlDbConnection.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet result = stmt.executeQuery()) {

            while (result.next()) {
                Note note = new Note(
                        result.getInt("evaluationId"),
                        result.getInt("etudiantId"),
                        result.getFloat("note")
                );
                notes.add(note);
            }
            return notes;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Récupération des notes échouée : " + e.getLocalizedMessage(), e);
        }
        return Set.of();
    }

    @Override
    public int update(Note note) {
        String sql = "UPDATE Note SET note = ? WHERE evaluationId = ? AND etudiantId = ?";

        try  {

            Connection con = MysqlDbConnection.getInstance().getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setFloat(1, note.note());
            stmt.setInt(2, note.evaluationId());
            stmt.setInt(3, note.etudiantId());

            return stmt.executeUpdate();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Mise à jour de la note échouée : " + e.getLocalizedMessage(), e);
        }
        return 0;
    }

    @Override
    public int delete(Note note) {
        String sql = "DELETE FROM Note WHERE evaluationId = ? AND etudiantId = ?";

        try (Connection con = MysqlDbConnection.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, note.evaluationId());
            stmt.setInt(2, note.etudiantId());

            return stmt.executeUpdate();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Suppression de la note échouée : " + e.getLocalizedMessage(), e);
        }
        return 0;
    }
}

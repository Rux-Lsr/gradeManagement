package org.ruxlsr.dataaccess.services.impl;

import org.ruxlsr.dataaccess.services.DataBaseOperation;
import org.ruxlsr.dataaccess.util.DatabaseConnection;
import org.ruxlsr.module.model.Module;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ModuleDataBaseOperation implements DataBaseOperation<Module> {
    private static final Logger LOGGER = Logger.getLogger(ModuleDataBaseOperation.class.getName());

    @Override
    public int createEntities(Module module) {
        String sql = "INSERT INTO Modules (nom, description, credit) VALUES (?, ?, ?)";

        try (Connection con = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, module.nom());
            stmt.setString(2, module.description());
            stmt.setInt(3, module.credit());

            return stmt.executeUpdate();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Insertion du module échouée : " + e.getLocalizedMessage(), e);
        }
        return 0;
    }

    @Override
    public Set<Module> getRecords() {
        Set<Module> modules = new HashSet<>();
        String sql = "SELECT * FROM Modules";

        try (Connection con = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet result = stmt.executeQuery()) {

            while (result.next()) {
                Module module = new Module(
                        result.getInt("id"),
                        result.getString("nom"),
                        result.getString("description"),
                        result.getInt("credit")
                );
                modules.add(module);
            }
            return modules;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Récupération des modules échouée : " + e.getLocalizedMessage(), e);
        }
        return Set.of();
    }

    @Override
    public int update(Module module) {
        String sql = "UPDATE Modules SET nom = ?, description = ?, credit = ? WHERE id = ?";

        try (Connection con = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, module.nom());
            stmt.setString(2, module.description());
            stmt.setInt(3, module.credit());
            stmt.setInt(4, module.id());

            return stmt.executeUpdate();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Mise à jour du module échouée : " + e.getLocalizedMessage(), e);
        }
        return 0;
    }

    @Override
    public int delete(Module module) {
        String sql = "DELETE FROM Modules WHERE id = ?";

        try (Connection con = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1,  module.id());
            return stmt.executeUpdate();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Suppression du module échouée : " + e.getLocalizedMessage(), e);
        }
        return 0;
    }
}

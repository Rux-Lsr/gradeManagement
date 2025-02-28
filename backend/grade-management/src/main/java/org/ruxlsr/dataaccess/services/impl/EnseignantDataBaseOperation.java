package org.ruxlsr.dataaccess.services.impl;

import org.ruxlsr.dataaccess.services.DataBaseOperation;
import org.ruxlsr.dataaccess.util.MysqlDbConnection;
import org.ruxlsr.enseignant.model.Enseignant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EnseignantDataBaseOperation implements DataBaseOperation<Enseignant> {
    private static final Logger LOGGER = Logger.getLogger(EnseignantDataBaseOperation.class.getName());
    @Override
    public int createEntities(Enseignant enseignant) {
        String sql = "INSERT INTO Enseignant ( nom, prenom, password) values ( ?, ?, ?);";
        try(Connection con = MysqlDbConnection.getInstance().getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);){
            stmt.setString(1, enseignant.nom());
            stmt.setString(2, enseignant.prenom());
            stmt.setString(3, enseignant.password());

            return stmt.executeUpdate();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Insertion failed: cause :"+e.getCause()+"\nLocalised message: "+e.getLocalizedMessage());
        }
        return 0;
    }

    @Override
    public Set<Enseignant> getRecords() {
        Set<Enseignant> enseignants = new HashSet<>();
        String sql = "SELECT * FROM Enseignant;";
        try(Connection con = MysqlDbConnection.getInstance().getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet result = stmt.executeQuery();){

                while (result.next()){
                    Enseignant enseignant = new Enseignant(
                            result.getInt("id"),
                            result.getString("nom"),
                            result.getString("prenom"),
                            result.getString("password")
                    );
                enseignants.add(enseignant);
                }

            return enseignants;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Get enseignant failed : cause :"+e.getCause()+"\nLocalised message: "+e.getLocalizedMessage());
        }
        return  Set.of();
    }


    @Override
    public int update(Enseignant enseignant) {
        String sql = "UPDATE Enseignant SET nom = ?, prenom = ?, password = ? WHERE id = ? ";
        try(Connection con = MysqlDbConnection.getInstance().getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);
            ){
            stmt.setString(1, enseignant.nom());
            stmt.setString(2, enseignant.prenom());
            stmt.setString(3, enseignant.password());
            stmt.setInt(4, enseignant.id());

            return stmt.executeUpdate();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Update enseignant failed : cause :"+e.getCause()+"\nLocalised message: "+e.getLocalizedMessage());
        }
        return 0;
    }

    @Override
    public int delete(Enseignant enseignant) {
        String sql = "DELETE FROM Enseignant where id=? ";
        try(Connection con = MysqlDbConnection.getInstance().getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);
        ){
            stmt.setInt(1, enseignant.id());

            return stmt.executeUpdate();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "DElete enseignant failed : cause :"+e.getCause()+"\nLocalised message: "+e.getLocalizedMessage());
        }
        return 0;
    }
}

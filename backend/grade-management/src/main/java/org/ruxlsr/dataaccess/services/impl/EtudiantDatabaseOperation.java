package org.ruxlsr.dataaccess.services.impl;

import org.ruxlsr.dataaccess.services.DataBaseOperation;
import org.ruxlsr.dataaccess.util.MysqlDbConnection;
import org.ruxlsr.etudiant.model.Etudiant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EtudiantDatabaseOperation implements DataBaseOperation<Etudiant> {

    private static final Logger LOGGER = Logger.getLogger(EtudiantDatabaseOperation.class.getName());
    @Override
    public int createEntities(Etudiant etudiant) {
        String sql = "INSERT INTO Etudiant ( nom, prenom, matricule, moduleId) values ( ?, ?, ?, ?);";
        try(Connection con = MysqlDbConnection.getInstance().getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);){
            stmt.setString(1, etudiant.nom());
            stmt.setString(2, etudiant.prenom());
            stmt.setString(3, etudiant.matricule());
            stmt.setInt(4, etudiant.moduleId());

            return stmt.executeUpdate();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Insertion failed: cause :"+e.getCause()+"\nLocalised message: "+e.getLocalizedMessage());
        }
        return 0;
    }

    @Override
    public Set<Etudiant> getRecords() {
        Set<Etudiant> etudiants = new HashSet<>();
        String sql = "SELECT * FROM Etudiant;";
        try(Connection con = MysqlDbConnection.getInstance().getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet result = stmt.executeQuery();){

            while (result.next()){
                Etudiant etudiant = new Etudiant(
                        result.getInt("id"),
                        result.getString("nom"),
                        result.getString("prenom"),
                        result.getString("matricule"),result.getInt("moduleId")
                );
                etudiants.add(etudiant);
            }

            return etudiants;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Get etudiant failed : cause :"+e.getCause()+"\nLocalised message: "+e.getLocalizedMessage());
        }
        return  Set.of();
    }


    @Override
    public int update(Etudiant etudiant) {
        String sql = "UPDATE Etudiant SET nom = ?, prenom = ?, matricule = ? WHERE id = ? ";
        try(Connection con = MysqlDbConnection.getInstance().getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);
        ){
            stmt.setString(1, etudiant.nom());
            stmt.setString(2, etudiant.prenom());
            stmt.setString(3, etudiant.matricule());
            stmt.setInt(4, etudiant.id());

            return stmt.executeUpdate();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Update etudiant failed : cause :"+e.getCause()+"\nLocalised message: "+e.getLocalizedMessage());
        }
        return 0;
    }

    @Override
    public int delete(Etudiant etudiant) {
        String sql = "DELETE FROM Etudiant where id=? ";
        try(Connection con = MysqlDbConnection.getInstance().getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);
        ){
            stmt.setInt(1, etudiant.id());

            return stmt.executeUpdate();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "DElete etudiant failed : cause :"+e.getCause()+"\nLocalised message: "+e.getLocalizedMessage());
        }
        return 0;
    }
}

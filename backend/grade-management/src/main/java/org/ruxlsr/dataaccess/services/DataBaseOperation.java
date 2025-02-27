package org.ruxlsr.dataaccess.services;

import java.util.Set;
//interface generique a implementer pour les differente table de la BD
public interface DataBaseOperation<T>{
    Set<T> getRecords(); // recuperer une liste de tous les enregistrements d'une table precise
    int createEntities(T t); // inserer un objet dans une ligne de la base de donnee
    int update(T t); // mettre a jour un element de la base de donnee
    int delete(T t); // supprimer un enregistrement de la base de donnee
}

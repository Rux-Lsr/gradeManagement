package org.ruxlsr.module.model;

pblic record Module(Integer id, String nom, String description, int credit, Set<Etudiant> listeEtudiant) {
}

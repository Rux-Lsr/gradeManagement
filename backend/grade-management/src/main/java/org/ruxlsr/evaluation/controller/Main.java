package org.ruxlsr.evaluation.controller;

import org.ruxlsr.evaluation.interfaces.Evaluationsinterface;
import org.ruxlsr.evaluation.controller.Evaluationcontroller;
import org.ruxlsr.evaluation.model.Evaluation;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Evaluationcontroller evaluatiocontroller = new Evaluationcontroller();
        Scanner scanner = new Scanner(System.in);
        int choix;

        List<Evaluation> evaluations = new ArrayList<Evaluation>(){{}};

        do {
            System.out.println("=== Menu des Services ===\n\n1. Créer une évaluation\n2. Supprimer une évaluation\n3. Modifier les informations d'une évaluation\n4. Enregistrer les notes des étudiants pour une évaluation\n0. Quitter\nVeuillez entrer votre choix : ");

            try {
                choix = Integer.parseInt(scanner.nextLine());

                switch (choix) {
                    case 1:
                        Evaluation eva = evaluatiocontroller.CreateEvaluation("19/03/2003 11:20:30", 2,20, "CC");
                        evaluations.add(eva);
                        System.out.println("Evaluation Cree avec succes");
                        break;
                        
                        
                        
                    case 2:
                        if (!evaluations.isEmpty()) {
                            int i = 0;
                            // Afficher la liste des évaluations
                            for (Evaluation evaluation : evaluations) {
                                System.out.println((i + 1) + "\t" + evaluation.getDate() + "\t" + evaluation.getType());
                                i++;
                            }
                    
                            // Demander à l'utilisateur de choisir une évaluation
                            System.out.print("Veuillez entrer le numéro de l'évaluation à supprimer : ");
                            try {
                                int n = Integer.parseInt(scanner.nextLine());
                    
                                
                                if (n >= 1 && n <= evaluations.size()) {
                                    evaluatiocontroller.DeleteEvaluation(evaluations, n-1);
                                    
                                } else {
                                    System.out.println("Numéro invalide. Veuillez réessayer.");
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Erreur : Veuillez entrer un nombre valide.");
                            }
                        } else {
                            System.out.println("La liste est vide");
                        }
                        break;
                        
                        
                        
                        
                    
                    
                    case 3:
                        if (!evaluations.isEmpty()) {
                            int i = 0;
                            // Afficher la liste des évaluations
                            for (Evaluation evaluation : evaluations) {
                                System.out.println((i + 1) + "\t" + evaluation.getDate() + "\t" + evaluation.getType());
                                i++;
                            }
                    
                            // Demander à l'utilisateur de choisir une évaluation
                            System.out.print("Veuillez entrer le numéro de l'évaluation Modifier : ");
                            try {
                                int n = Integer.parseInt(scanner.nextLine());

                                
                                if (n >= 1 && n <= evaluations.size()) {

                                    System.out.print("Veuillez entrer la nouvelle date au format dd/MM/yyyy HH:mm:ss");
                                    String date = scanner.nextLine();
    
                                    System.out.print("\nVeuillez entrer le nouveuau coefficient : ");
                                    float coef = Float.parseFloat(scanner.nextLine());
    
                                    System.out.print("Veuillez entrer la nouvelle note maximale : ");
                                    float note = Float.parseFloat(scanner.nextLine());
    
                                    System.out.print("Veuillez entrer le nouveau type de l'examin (CC, TP,SN,Rattrapage) : ");
                                    String type = scanner.nextLine();


                                    evaluatiocontroller.ModifyEvaluation(evaluations, n-1 , date , coef , note, type);
                                    
                                } else {
                                    System.out.println("Numéro invalide. Veuillez réessayer.");
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Erreur : Veuillez entrer un nombre valide.");
                            }
                        } else {
                            System.out.println("La liste est vide");
                        }
                        break;
                    
                    
                        
                        
                    
                    
                    case 4:
                    //enregistrerNotes();
                    break;
                    case 0:
                        System.out.println("Au revoir !");
                        break;
                    default:
                        System.out.println("Choix invalide. Veuillez réessayer.");
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Erreur : Veuillez entrer un nombre valide.");
                choix = -1;
            }
                        
        } while (choix != 0);
                        
        scanner.close();
    }
                        
                        
                        
    
}

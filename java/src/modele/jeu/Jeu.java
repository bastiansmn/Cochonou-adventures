package modele.jeu;

import modele.jeu.bonus.Bombe;
import modele.jeu.bonus.ColorChange;
import modele.jeu.bonus.Firework;
import modele.jeu.grille.Grille;
import modele.outils.Joueur;
import modele.outils.erreurs.CSVNotValidException;
import modele.outils.erreurs.Erreur;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;

public class Jeu {

   private final Joueur joueur;
   private final Plateau plateau;

   public Jeu(String nom) {
      this.joueur = new Joueur(nom);
      this.plateau = new Plateau();
   }

   public int run(String[] args) throws InvocationTargetException, InterruptedException {
      // Test pour ajouter des niveaux (à enlever plus tard) et ajouter les bonus du joueur:
      Joueur.bonus[0] = new Firework();
      Joueur.bonus[1] = new ColorChange();
      Joueur.bonus[2] = new Bombe();
      Joueur.bonus[3] = new Bombe();
      Joueur.bonus[4] = new ColorChange();

      try {
         plateau.ajouterNiveau(new Niveau(1, new Grille().getCSV("niveaux/nv1.csv", ";")));
         plateau.ajouterNiveau(new Niveau(2, new Grille().getCSV("niveaux/nv1.csv", ";")));
         plateau.ajouterNiveau(new Niveau(3, new Grille().getCSV("niveaux/nv1.csv", ";")));
         plateau.ajouterNiveau(new Niveau(4, new Grille().getCSV("niveaux/nv1.csv", ";")));
      } catch (CSVNotValidException e) {
         e.printStackTrace();
      }


      if (args.length == 2 && (args[1].equals("-g") || args[0].equals("--graphical"))) {
         return this.runGraphical();
      } else if (args.length == 2 && (args[1].equals("-t") || args[0].equals("--textual"))) {
         return this.runTextual();
      }
      displayHelp();
      return 1;
   }

   public int runGraphical() throws InvocationTargetException, InterruptedException {
      SwingUtilities.invokeAndWait(() -> {
         // TODO : Instancier la fenetre
      });

      return 0;
   }

   public int runTextual() throws InterruptedException {

      // Affichage des titre et phrases de bases du jeu
      afficherTitreJeu();
      System.out.println("\n");
      Thread.sleep(3000);

      // TODO : Initialisation du plateau. (si partie existante, charger et commencer depuis la partie en question, sinon créer nvelle)

      System.out.println("Bienvenue sur Cochonou-adventures " + this.joueur.getNom() + " !\n\n");
      Thread.sleep(3000);

      String reponse;
      do {
         plateau.afficher();
         System.out.println("[? pour afficher les règles]\nUtilisez z/s pour chager de niveau ou entrez le numéro d'un niveau :");
         reponse = new Scanner(System.in).next();

         if (reponse.matches("[0-9]+")) {
            jouerNiveauParNumero(Integer.parseInt(reponse));
         } else {
            switch (reponse) {
               case "z" -> plateau.deplacerGauche();
               case "s" -> plateau.deplacerDroite();
               case "d" -> plateau.getNiveaux().get(plateau.getIndexNiveauActuel()).run();
               case "?" -> System.out.println(reglesDuJeu());
               default -> Erreur.afficher("Euuuh ... " + joueur.getNom() + " ? Je ne comprend pas trop ce que vous cherchez à me dire");
            }
         }

         clear();
      } while (!reponse.equals("q"));

      return 0;
   }

   private void jouerNiveauParNumero(int num) {
      if (num < plateau.getNiveaux().size()) {
         if (plateau.getIndexNiveauActuel() > num)
            while (plateau.getIndexNiveauActuel() > num)
               plateau.deplacerGauche();
         else if (plateau.getIndexNiveauActuel() < num)
            while (plateau.getIndexNiveauActuel() < num)
               plateau.deplacerDroite();

         plateau.getNiveaux().get(num).run();
      } else
         Erreur.afficher(num + " : numéro de niveau invalide");
   }

   private void afficherTitreJeu() {
      System.out.println("""
              _____           _                                                  _                 _                      \s
             / ____|         | |                                                | |               | |                     \s
            | |     ___   ___| |__   ___  _ __   ___  _   _   ______    __ _  __| |_   _____ _ __ | |_ _   _ _ __ ___  ___\s
            | |    / _ \\ / __| '_ \\ / _ \\| '_ \\ / _ \\| | | | |______|  / _` |/ _` \\ \\ / / _ \\ '_ \\| __| | | | '__/ _ \\/ __|
            | |___| (_) | (__| | | | (_) | | | | (_) | |_| |          | (_| | (_| |\\ V /  __/ | | | |_| |_| | | |  __/\\__ \\
             \\_____\\___/ \\___|_| |_|\\___/|_| |_|\\___/ \\__,_|           \\__,_|\\__,_| \\_/ \\___|_| |_|\\__|\\__,_|_|  \\___||___/
                                                                                                                          \s
                                                                                                                          \s""".indent(1));
   }

   private void clear() {
      System.out.print("\033[H\033[2J");
      System.out.flush();
   }

   public static void displayHelp() {
      System.out.println("""
               Aide du jeu : 
                  Veuillez lancer le jeu avec un mode (graphique ou terminal)
                  Pour cela :
                     - MODE GRAPHIQUE : utilisez l'argument "-g" ou "--graphical"
                     - MODE TERMINAL : utilisez l'argument "-t" ou "--textual"
                     
                  Pour le lien vers le projet : 
                  https://github.com/bastiansmn/Chochonou-adventures
            """);
   }

   public static String reglesDuJeu() {
      return """
           But du jeu :
               - Dans chaques niveau, sauvez tous les animaux.
               - Cassez les blocs pour les faire descendre jusqu'en bas.
               - Utilisez vos bonus lorsque vous êtes coincés. 
               
           Comment jouer :
               - Cliquez ou entrez la position du bloc que vous souhaitez casser, ses blocs adjacents de même
           couleur seront détruits aussi.
               - Chaque animal est représenté par une lettre différente :
                  * Chat : c
                  * Chien : C
                  * Oiseau : o
                  * Panda : p
            """;
   }

   public Plateau getPlateau() {
      return plateau;
   }

   public Joueur getJoueur() {
      return joueur;
   }
}

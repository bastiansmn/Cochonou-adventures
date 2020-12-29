package modele.jeu;

import modele.jeu.bonus.Bombe;
import modele.jeu.bonus.ColorChange;
import modele.jeu.bonus.Firework;
import modele.jeu.grille.Grille;
import modele.outils.Joueur;
import modele.outils.erreurs.CSVNotValidException;
import java.lang.reflect.InvocationTargetException;


public class Jeu {

   public static Joueur joueur = null;
   public static Plateau plateau = null;
   public static String type = null;

   public Jeu(String[] args) {
      type = args[1];
      joueur = new Joueur(args[0]);
      plateau = new Plateau();
   }

   public int run() throws InvocationTargetException, InterruptedException {
      // TODO : Initialisation du plateau. (si partie existante, charger et commencer depuis la partie en question, sinon créer nvelle)
      
      // Test pour ajouter des niveaux (à enlever plus tard) et ajouter les bonus du joueur:
      Joueur.bonus[0] = new Firework();
      Joueur.bonus[1] = new ColorChange();
      Joueur.bonus[2] = new Bombe();
      Joueur.bonus[3] = new Bombe();
      Joueur.bonus[4] = new ColorChange();

      try {
         plateau.ajouterNiveau(new Niveau(1, new Grille().getCSV("niveaux/nv1.csv", ";"), true));
         plateau.ajouterNiveau(new Niveau(2, new Grille().getCSV("niveaux/nv1.csv", ";"), false));
         plateau.ajouterNiveau(new Niveau(3, new Grille().getCSV("niveaux/nv1.csv", ";"), false));
         plateau.ajouterNiveau(new Niveau(4, new Grille().getCSV("niveaux/nv1.csv", ";"), false));
      } catch (CSVNotValidException e) {
         e.printStackTrace();
      }


      if ((type.equals("-g") || joueur.getNom().equals("--graphical"))) {
         return new vue.graphique.Launcher().runGraphical();
      } else if ((type.equals("-t") || joueur.getNom().equals("--textual"))) {
         return new vue.terminal.Launcher().runTextual();
      }
      vue.terminal.Launcher.displayHelp();
      return 1;
   }
}

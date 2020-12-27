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

   public Jeu(String nom) {
      joueur = new Joueur(nom);
      plateau = new Plateau();
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
         return new vue.graphique.Launcher().runGraphical();
      } else if (args.length == 2 && (args[1].equals("-t") || args[0].equals("--textual"))) {
         return new vue.terminal.Launcher().runTextual();
      }
      vue.terminal.Launcher.displayHelp();
      return 1;
   }
}

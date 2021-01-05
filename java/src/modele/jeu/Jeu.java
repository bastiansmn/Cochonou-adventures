package modele.jeu;

import modele.jeu.bonus.*;
import modele.jeu.grille.CSVImport;
import modele.outils.Joueur;
import modele.outils.erreurs.CSVNotValidException;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;


public class Jeu {

   public static Joueur joueur = null;
   public static Plateau plateau = null;
   public static String type = null;
   public static boolean serialisation;
   public static boolean slowMode;

   public Jeu(String[] args) {
      type = args[1];
      joueur = new Joueur(args[0]);
      plateau = new Plateau();
      serialisation = (args.length == 3 && (args[2].equals("--serial") || args[2].equals("-s")));
      slowMode = (args.length == 3 || args.length == 4 && args[3].equals("--slow-mode"));
   }

   public void run() throws InvocationTargetException, InterruptedException {
      if (new File("ser/jeu.ser").exists() && serialisation) {
         try (FileInputStream fis = new FileInputStream("ser/jeu.ser");
              ObjectInputStream ois = new ObjectInputStream(fis)) {
            plateau = (Plateau) ois.readObject();
            joueur = (Joueur) ois.readObject();
         } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
            initJoueur();
            initPlateau();
         }
      } else {
         initJoueur();
         initPlateau();
      }

      if ((type.equals("-g") || type.equals("--graphical"))) {
         new vue.graphique.Launcher().runGraphical();
      } else if ((type.equals("-t") || type.equals("--textual"))) {
         new vue.terminal.Launcher().runTextual();
      } else if ((type.equals("-b") || type.equals("--bot"))) {
         new vue.BotPlayer().runBot();
      } else {
         vue.terminal.Launcher.displayHelp();
      }

   }


   private void initJoueur() {
      Jeu.joueur.bonus[0] = new Firework();
      Jeu.joueur.bonus[1] = new ColorChange();
      Jeu.joueur.bonus[2] = new Bombe();
      Jeu.joueur.bonus[3] = new Marteau();
      Jeu.joueur.bonus[4] = new LineBreaker();
   }

   private void initPlateau() {
      try {
         File[] list = new File("niveaux").listFiles();
         for (int i = 0; i < Objects.requireNonNull(list).length; i++) {
            plateau.ajouterNiveau(new Niveau(CSVImport.getCSV("niveaux/nv" + (i + 1) + ".csv", ";"), i == 0));
         }
      } catch (CSVNotValidException error) {
         error.printStackTrace();
      }
   }
}

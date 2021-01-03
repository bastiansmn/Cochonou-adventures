package modele.jeu;

import modele.jeu.bonus.Bombe;
import modele.jeu.bonus.ColorChange;
import modele.jeu.bonus.Firework;
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

   public Jeu(String[] args) {
      type = args[1];
      joueur = new Joueur(args[0]);
      plateau = new Plateau();
   }

   public int run() throws InvocationTargetException, InterruptedException {
      File serialJoueur = new File("ser/jeu.ser");

      if (serialJoueur.exists()) {
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

      if ((type.equals("-g") || joueur.getNom().equals("--graphical"))) {
         return new vue.graphique.Launcher().runGraphical();
      } else if ((type.equals("-t") || joueur.getNom().equals("--textual"))) {
         return new vue.terminal.Launcher().runTextual();
      }
      vue.terminal.Launcher.displayHelp();
      return 1;
   }


   private void initJoueur() {
      Jeu.joueur.bonus[0] = new Firework();
      Jeu.joueur.bonus[1] = new ColorChange();
      Jeu.joueur.bonus[2] = new Bombe();
      Jeu.joueur.bonus[3] = new Firework();
      Jeu.joueur.bonus[4] = new ColorChange();
   }

   private void initPlateau() {
      try {
         File[] list = new File("niveaux").listFiles();
         for (int i = 0; i < Objects.requireNonNull(list).length; i++) {
            plateau.ajouterNiveau(new Niveau(CSVImport.getCSV("niveaux/nv"+(i+1)+".csv", ";"), i == 0));
         }
      } catch (CSVNotValidException error) {
         error.printStackTrace();
      }
   }
}

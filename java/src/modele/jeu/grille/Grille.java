package modele.jeu.grille;

import modele.jeu.Jeu;
import modele.jeu.animaux.*;
import modele.jeu.bonus.Bonus;
import modele.jeu.grille.blocs.*;
import modele.outils.Joueur;
import modele.outils.erreurs.CSVNotValidException;
import modele.outils.erreurs.Erreur;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;


public class Grille {

   private Container[][] cases;

   private int largeur, longueur;
   private String fileName;
   private String separator;
   private int score = 0;
   private int animauxSauvee = 0;
   private int animauxRestants;
   private int nombreLimiteDeCoup;
   private boolean perdu = false;

   public void actionOuvertureGrille(int i, int j) {
      if (perdu)
         perdu = false;
      if (i >= 0 && i < cases.length && j >= 0 && j < cases[i].length) {
         if (cases[i][j] != null && cases[i][j].getContent() instanceof Ouvrable) {
            this.score += (int) (1000 * Math.pow(((Ouvrable) cases[i][j].getContent()).open(this, i, j), 2));
            nettoyerGrille();
            nombreLimiteDeCoup--;
            if (nombreLimiteDeCoup == 0) {
               perdu = true;
               Jeu.joueur.perdreUneVie();
               try {
                  this.getCSV(fileName, separator);
               } catch (CSVNotValidException ignored) {}
            }
            // TODO : sérialiser le plateau à chaque mouvement ou au moins à chaque fin de jeu (if (gagne()))
         }
      }
   }

   public void actionBonus(String s) {
      for (Bonus bonusInitiales : Joueur.bonus)
         if (s.equals(bonusInitiales.getInit())) {
            bonusInitiales.utiliser();
            return;
         }
   }

   public void nettoyerGrille() {
      for (int i = 0; i < cases[cases.length - 1].length; i++) {
         for (int j = cases.length - 2; j >= 0; j--) {
            int k = j;
            while (k < cases.length - 1 && cases[k][i] != null && cases[k][i].getContent() instanceof Deplacable && cases[k + 1][i] != null && cases[k + 1][i].getContent() == null) {
               cases[k + 1][i] = cases[k][i];
               cases[k][i] = new Case(null);
               if (cases[k + 1][i].getContent() instanceof Animal && k == cases.length - 2) {
                  cases[k + 1][i] = new Case(null);
                  animauxSauvee++;
                  this.score += 5000;
               }
               k++;
            }
         }
      }
   }

   public boolean gagne() {
      if (animauxSauvee == animauxRestants) {
         Jeu.joueur.gagner(this.score);
         return true;
      }
      return false;
   }

   public boolean perdu() {
      return perdu;
   }

   public void exportToCSV() throws FileNotFoundException {
      PrintWriter printWriter = new PrintWriter("myNewLevel.csv");

      for (int i = 0; i < longueur; i++) {
         for (int j = 0; j < largeur; j++) {
            if (this.cases[i][j] == null) {
               printWriter.write("null" + ((j == largeur - 1) ? "" : ";"));
            } else if (this.cases[i][j].getContent() == null) {
               printWriter.write("" + ((j == largeur - 1) ? "" : ";"));
            } else if (this.cases[i][j].getContent() instanceof Chat) {
               printWriter.write("chat" + ((j == largeur - 1) ? "" : ";"));
            } else if (this.cases[i][j].getContent() instanceof Chien) {
               printWriter.write("chien" + ((j == largeur - 1) ? "" : ";"));
            } else if (this.cases[i][j].getContent() instanceof Panda) {
               printWriter.write("panda" + ((j == largeur - 1) ? "" : ";"));
            } else if (this.cases[i][j].getContent() instanceof Oiseau) {
               printWriter.write("oiseau" + ((j == largeur - 1) ? "" : ";"));
            } else if (this.cases[i][j].getContent() instanceof BlocCouleur) {
               printWriter.write(((BlocCouleur) this.cases[i][j].getContent()).getColorName() + ((j == largeur - 1) ? "" : ";"));
            } else if (this.cases[i][j].getContent() instanceof BlocObstacle) {
               printWriter.write("obstacle" + ((j == largeur - 1) ? "" : ";"));
            } else if (this.cases[i][j].getContent() instanceof BlocBombe) {
               printWriter.write("bombe" + ((j == largeur - 1) ? "" : ";"));
            }
         }
         printWriter.write("\n");
      }

      printWriter.close();
   }

   private void compteAnimaux() {
      for (Container[] cont : cases)
         for (Container c : cont)
            if (c != null && c.getContent() instanceof Animal)
               animauxRestants++;
   }

   public int getScore() {
      return score;
   }

   public int getAnimauxSauvee() {
      return animauxSauvee;
   }

   public int getAnimauxRestants() {
      return animauxRestants;
   }

   public int getNombreLimiteDeCoup() {
      return nombreLimiteDeCoup;
   }

   public int getLargeur() {
      return largeur;
   }

   public int getLongueur() {
      return longueur;
   }

   public Grille getCSV(String fileName) throws CSVNotValidException {
      return getCSV(fileName, ",");
   }

   public Grille getCSV(String fileName, String separator) throws CSVNotValidException {
      CSVImport csv = new CSVImport(fileName, separator);

      this.fileName = fileName;
      this.separator = separator;
      this.cases = csv.getCases();
      this.nombreLimiteDeCoup = csv.getMaxMoves();
      compteAnimaux();

      this.longueur = this.cases.length;
      this.largeur = this.cases[0].length;

      return this;
   }

   public Container[][] getCases() {
      return cases;
   }

   public void viderCase(int i, int j) {
      this.cases[i][j] = new Case(null);
   }
}

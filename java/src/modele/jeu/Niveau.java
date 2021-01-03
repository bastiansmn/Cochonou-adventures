package modele.jeu;

import modele.jeu.animaux.Animal;
import modele.jeu.bonus.Bonus;
import modele.jeu.grille.CSVImport;
import modele.jeu.grille.Case;
import modele.jeu.grille.CaseType;
import modele.jeu.grille.Container;
import modele.jeu.grille.blocs.Deplacable;
import modele.jeu.grille.blocs.Ouvrable;
import modele.outils.erreurs.CSVNotValidException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Niveau implements Serializable {

   private int numNiveau;

   private static int nbrNiveau = 0;

   private boolean isGagne = false;
   private boolean canPlay;

   private Grille grille;


   public Niveau(Niveau.Grille g, boolean canPlay) {
      this.numNiveau = nbrNiveau;
      nbrNiveau++;
      this.grille = g;
      this.canPlay = canPlay;
   }

   public Niveau() {

   }

   public void afficher() {
      System.out.println("Niveau {" + numNiveau + "}" + (isGagne ? " *" : ""));
   }

   public void marquerCommeGagne() {
      isGagne = true;
      Jeu.plateau.goToNextLevel();
      if (numNiveau + 1 < Jeu.plateau.getNiveaux().size()) {
         Jeu.plateau.getNiveaux().get(numNiveau + 1).setGagne();
      }
   }

   public class Grille implements Serializable {
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
                     grille = CSVImport.getCSV(fileName, separator);
                  } catch (CSVNotValidException ignored) {}
               }
               try(FileOutputStream fos = new FileOutputStream("ser/jeu.ser");
                   ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                  oos.writeObject(Jeu.plateau);
                  oos.writeObject(Jeu.joueur);
               } catch (IOException ignored) {

               }
            }
         }
      }

      public void actionBonus(String s, int i, int j) {
         for (Bonus bonusInitiales : Jeu.joueur.bonus)
            if (s.equals(bonusInitiales.getInit())) {
               bonusInitiales.utiliser(i, j);
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
            marquerCommeGagne();
            return true;
         }
         return false;
      }

      public boolean perdu() {
         if (nombreLimiteDeCoup == 0) {
            perdu = true;
            Jeu.joueur.perdreUneVie();
            try {
               grille = CSVImport.getCSV(fileName, separator);
            } catch (CSVNotValidException ignored) {}
            return true;
         }
         return false;
      }

      public void compteAnimaux() {
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

      public void setAnimauxRestants(int animauxRestants) {
         this.animauxRestants = animauxRestants;
      }

      public void setAnimauxSauvee(int animauxSauvee) {
         this.animauxSauvee = animauxSauvee;
      }

      public void setCases(Container[][] cases) {
         this.cases = cases;
      }

      public void setFileName(String fileName) {
         this.fileName = fileName;
      }

      public void setLargeur(int largeur) {
         this.largeur = largeur;
      }

      public void setLongueur(int longueur) {
         this.longueur = longueur;
      }

      public void setNombreLimiteDeCoup(int nombreLimiteDeCoup) {
         this.nombreLimiteDeCoup = nombreLimiteDeCoup;
      }

      public void setScore(int score) {
         this.score = score;
      }

      public void setSeparator(String separator) {
         this.separator = separator;
      }

      public Container[][] getCases() {
         return cases;
      }

      public boolean isPerdu() {
         return perdu;
      }

      public void viderCase(int i, int j) {
         remplirCase(i, j, null);
      }

      public void remplirCase(int i, int j, CaseType c) {
         this.cases[i][j] = new Case(c);
      }
   }

   public void setGagne() {
      canPlay = true;
   }

   public boolean canPlay() {
      return canPlay;
   }

   public int getNumNiveau() {
      return numNiveau;
   }

   public Grille getGrille() {
      return grille;
   }

   public boolean isGagne() {
      return isGagne;
   }
}

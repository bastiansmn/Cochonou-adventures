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

   private final int numNiveau;
   private static int nbrNiveau = 0;
   private boolean isGagne = false;
   private boolean isPerdu = false;
   private boolean canPlay = false;
   private Niveau.Grille grille;

   public Niveau(Niveau.Grille g, boolean canPlay) {
      this.numNiveau = nbrNiveau;
      nbrNiveau++;
      this.grille = g;
      this.canPlay = canPlay;
   }

   public Niveau() {
      numNiveau = nbrNiveau;
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

      public void actionOuvertureGrille(int i, int j) {
         if (i >= 0 && i < cases.length && j >= 0 && j < cases[i].length) {
            if (cases[i][j] != null && cases[i][j].getContent() instanceof Ouvrable) {
               this.score += (int) (1000 * Math.pow(((Ouvrable) cases[i][j].getContent()).open(this, i, j), 2));
               apresCoup();
               if (Jeu.serialisation) {
                  try(FileOutputStream fos = new FileOutputStream("ser/jeu.ser");
                      ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                     oos.writeObject(Jeu.plateau);
                     oos.writeObject(Jeu.joueur);
                  } catch (IOException ignored) {}
               }
            }
         }
      }

      public void actionBonus(String s, int i, int j) {
         for (Bonus bonusInitiales : Jeu.joueur.bonus)
            if (s.equals(bonusInitiales.getInit())) {
               bonusInitiales.utiliser(i, j);
               apresCoup();
               return;
            }
      }

      public void apresCoup() {
         nettoyerGrille();
         nombreLimiteDeCoup--;
         if (isPerdu())
            perdre();
         if (isGagne())
            gagner();
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
               /*
               try {
                  if (cases[k-1][i] != null && cases[k-1][i].getContent() instanceof Deplacable && cases[k][i-1] != null && cases[k][i-1].getContent() == null && cases[k-1][i-1] != null && cases[k-1][i-1].getContent() == null) {
                     cases[k][i-1] = cases[k-1][i];
                     cases[k-1][i] = new Case(null);
                  }
               } catch (ArrayIndexOutOfBoundsException ignored) {}
               try {
                  if (cases[k-1][i] != null && cases[k-1][i].getContent() instanceof Deplacable && cases[k][i+1] != null && cases[k][i+1].getContent() == null && cases[k-1][i+1] != null && cases[k-1][i+1].getContent() == null) {
                     cases[k][i+1] = cases[k-1][i];
                     cases[k-1][i] = new Case(null);
                  }
               } catch (ArrayIndexOutOfBoundsException ignored) {}
                */
            }
         }
      }

      public boolean isPerdu() {
         return nombreLimiteDeCoup == 0;
      }

      public boolean isGagne() {
         return animauxSauvee == animauxRestants;
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

      public void setSeparator(String separator) {
         this.separator = separator;
      }

      public Container[][] getCases() {
         return cases;
      }

      public void viderCase(int i, int j) {
         remplirCase(i, j, null);
      }

      public void remplirCase(int i, int j, CaseType c) {
         this.cases[i][j] = new Case(c);
      }
   }

   public void gagner() {
      if (grille.isGagne()) {
         Jeu.joueur.gagner(grille.score);
         isGagne = true;
         Jeu.plateau.goToNextLevel();
         if (numNiveau + 1 < Jeu.plateau.getNiveaux().size()) {
            Jeu.plateau.getNiveaux().get(numNiveau + 1).setJouable();
         }
      }
   }

   public void perdre() {
      if (isPerdu) {
         Jeu.joueur.perdreUneVie();
         try {
            grille = CSVImport.getCSV(grille.fileName, grille.separator);
         } catch (CSVNotValidException ignored) {}
         isPerdu = true;
      }
   }

   public void setJouable() {
      canPlay = true;
   }

   public void setGrille(Grille grille) {
      this.grille = grille;
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
}

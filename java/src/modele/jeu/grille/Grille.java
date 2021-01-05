package modele.jeu.grille;

import modele.jeu.Jeu;
import modele.jeu.animaux.Animal;
import modele.jeu.bonus.Bonus;
import modele.jeu.grille.blocs.Deplacable;
import modele.jeu.grille.blocs.Ouvrable;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Grille implements Serializable {

   private Container[][] cases;
   private int largeur, longueur;
   private String fileName;
   private String separator;
   private int score = 0;
   private int animauxSauvee = 0;
   private int animauxRestants;
   private int nombreLimiteDeCoup;

   public Grille() {
   }

   public Grille(Grille g) {
      this.cases = g.cases;
      this.largeur = g.largeur;
      this.longueur = g.longueur;
      this.fileName = g.fileName;
      this.separator = g.separator;
      this.score = 0;
      this.animauxSauvee = 0;
      this.animauxRestants = g.getAnimauxRestants();
      this.nombreLimiteDeCoup = g.getNombreLimiteDeCoup();
   }

   public void actionOuvertureGrille(int i, int j) {
      if (i >= 0 && i < cases.length && j >= 0 && j < cases[i].length) {
         if (cases[i][j] != null && cases[i][j].getContent() instanceof Ouvrable) {
            this.score += (int) (1000 * Math.pow(((Ouvrable) cases[i][j].getContent()).open(this, i, j), 2));
            apresCoup();
            if (Jeu.serialisation) {
               try (FileOutputStream fos = new FileOutputStream("ser/jeu.ser");
                    ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                  oos.writeObject(Jeu.plateau);
                  oos.writeObject(Jeu.joueur);
               } catch (IOException ignored) {
               }
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
      if (isGagne())
         Jeu.plateau.getNiveaux().get(Jeu.plateau.getIndexNiveauActuel()).gagner();
      if (isPerdu())
         Jeu.plateau.getNiveaux().get(Jeu.plateau.getIndexNiveauActuel()).perdre();
   }

   public void nettoyerGrille() {
      for (int j = 0; j < cases[cases.length - 1].length; j++) {
         faireTomber(j);
         if (j < cases[cases.length - 1].length - 1)
            balayageGauche(j + 1);
         // faireTomber(j);
      }
   }

   public void faireTomber(int j) {
      for (int i = cases.length - 2; i >= 0; i--) {
         int toGoDown = canMoveDown(i, j, 0, 0);
         if (cases[i][j] != null && cases[i][j].getContent() instanceof Deplacable && toGoDown != 0 && i + toGoDown < cases.length) {
            cases[i + toGoDown][j] = cases[i][j];
            cases[i][j] = new Case(null);
         }
         checkAnimauxSauvees();
      }
   }

   public void balayageGauche(int j) {
      int i = cases.length - 1;
      int k = 0;
      while (i >= 0 && cases[i][j] != null && cases[i][j].getContent() instanceof Deplacable) {
         k++;
         i--;
      }

      int col = j;
      while (col > 0 && k != 0 && peutDeplacerColonne(col, k)) {
         for (i = 0; i < k; i++) {
            cases[cases.length - 1 - i][col - 1] = cases[cases.length - 1 - i][col];
            cases[cases.length - 1 - i][col] = new Case(null);
         }
         col--;
      }
   }

   public boolean peutDeplacerColonne(int j, int k) {
      for (int i = 0; i < k; i++)
         if (cases[cases.length - 1 - i][j - 1] == null || cases[cases.length - 1 - i][j - 1].getContent() != null)
            return false;
      return true;
   }

   public int canMoveDown(int i, int j, int surePos, int nbrCall) {
      if (i + 1 < cases.length)
         if (cases[i + 1][j] == null)
            surePos = canMoveDown(i + 1, j, surePos, nbrCall + 1);
         else if (cases[i + 1][j].getContent() == null)
            surePos = canMoveDown(i + 1, j, nbrCall + 1, nbrCall + 1);
         else
            return surePos;
      return surePos;
   }

   public void checkAnimauxSauvees() {
      for (int j = 0; j < cases[cases.length - 1].length; j++) {
         int lowestFloor = cases.length - 1;
         while (lowestFloor > 0 && cases[lowestFloor][j] == null) {
            lowestFloor--;
         }
         if (cases[lowestFloor][j] != null && cases[lowestFloor][j].getContent() instanceof Animal) {
            cases[lowestFloor][j] = new Case(null);
            animauxSauvee++;
            this.score += 5000;
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

   public String getFileName() {
      return fileName;
   }

   public String getSeparator() {
      return separator;
   }
}
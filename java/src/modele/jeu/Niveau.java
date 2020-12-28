package modele.jeu;

import modele.jeu.grille.Grille;

public class Niveau {

   private final int numNiveau;

   private static int nbrNiveau = 0;

   private boolean isGagne = false;

   private final int difficulte;
   private final Grille grille;

   public Niveau(int difficulte, Grille g) {
      this.numNiveau = nbrNiveau;
      nbrNiveau++;
      this.difficulte = difficulte;
      this.grille = g;
   }

   public void afficher() {
      System.out.println("Niveau {" + numNiveau + "}" + (isGagne ? " *" : ""));
   }

   public void marquerCommeGagne() {
      isGagne = true;
   }

   public int getNumNiveau() {
      return numNiveau;
   }

   public Grille getGrille() {
      return grille;
   }

   public int getDifficulte() {
      return difficulte;
   }

   public boolean isGagne() {
      return isGagne;
   }
}

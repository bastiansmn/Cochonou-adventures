package modele.jeu;

import modele.jeu.grille.Grille;

public class Niveau {

   private final int numNiveau;

   private static int nbrNiveau = 0;

   private boolean isGagne = false;
   private boolean canPlay = false;

   private final int difficulte;
   private final Grille grille;

   public Niveau(int difficulte, Grille g, boolean canPlay) {
      this.numNiveau = nbrNiveau;
      nbrNiveau++;
      this.difficulte = difficulte;
      this.grille = g;
      this.canPlay = canPlay;
   }

   public void afficher() {
      System.out.println("Niveau {" + numNiveau + "}" + (isGagne ? " *" : ""));
   }

   public void marquerCommeGagne() {
      isGagne = true;
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

   public int getDifficulte() {
      return difficulte;
   }

   public boolean isGagne() {
      return isGagne;
   }
}

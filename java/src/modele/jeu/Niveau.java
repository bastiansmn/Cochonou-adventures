package modele.jeu;

import modele.jeu.grille.Grille;

public class Niveau {

   private final int numNiveau;

   private static int nbrNiveau = 0;

   private final int difficulte;
   private final Grille grille;

   public Niveau(int difficulte, Grille g) {
      this.numNiveau = nbrNiveau;
      nbrNiveau++;
      this.difficulte = difficulte;
      this.grille = g;
   }

   public void run() {
      // Lancer un niveau (afficher la grille, faire monter les scores etc)
      try {
         System.out.println("Le niveau va commencer ...");
         Thread.sleep(1000);
      } catch (InterruptedException e) {
         e.printStackTrace();
      }
      System.out.println("\n");
      this.grille.run();
      clear();
   }


   public void afficher() {
      System.out.println("Niveau {" + numNiveau + "}");
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

   public void clear() {
      System.out.print("\033[H\033[2J");
      System.out.flush();
   }
}

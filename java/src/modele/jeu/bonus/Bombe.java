package modele.jeu.bonus;

import modele.jeu.Jeu;

public class Bombe extends Bonus {

   private int nbrRestant;

   public Bombe() {
      super("bo");
      this.nbrRestant = 0;
   }

   @Override
   public int getNombreRestant() {
      return this.nbrRestant;
   }

   @Override
   public void utiliser() {
      if (nbrRestant > 0)
         nbrRestant--;

      if (Jeu.type.equals("-t") || Jeu.type.equals("--textual")) {

      }
   }
}

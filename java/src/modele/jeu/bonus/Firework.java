package modele.jeu.bonus;

public class Firework extends Bonus {

   private int nbrRestant;

   public Firework() {
      super("fw");
      this.nbrRestant = 3;
   }


   @Override
   public int getNombreRestant() {
      return this.nbrRestant;
   }

   @Override
   public void utiliser() {
      if (nbrRestant > 0)
         nbrRestant--;

      // TODO : Demander une case où placer le feu d'artifice, toutes les cases au dessus seront détruites
   }
}

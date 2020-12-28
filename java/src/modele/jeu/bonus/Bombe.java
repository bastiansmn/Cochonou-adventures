package modele.jeu.bonus;

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

      // TODO : demander dans quelle case détruire. Ce bonus détruit toutes les cases dans un rayon de 1 bloc.
   }
}

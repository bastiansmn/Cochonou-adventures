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
      this.nbrRestant--;
   }
}

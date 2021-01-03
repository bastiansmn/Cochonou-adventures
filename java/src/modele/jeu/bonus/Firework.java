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
   public void utiliser(int i, int j) {
      if (nbrRestant > 0)
         nbrRestant--;


   }
}

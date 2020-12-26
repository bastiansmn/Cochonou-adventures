package modele.jeu.bonus;

public class ColorChange extends Bonus{

   private int nbrRestant;

   public ColorChange() {
      super("co");
      this.nbrRestant = 0;
   }

   @Override
   public int getNombreRestant() {
      return this.nbrRestant;
   }
}

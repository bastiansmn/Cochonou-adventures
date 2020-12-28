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

   @Override
   public void utiliser() {
      if (nbrRestant > 0)
         nbrRestant--;

      // TODO : demander où utiliser, le bonus change la couleur d'un bloc.
   }
}

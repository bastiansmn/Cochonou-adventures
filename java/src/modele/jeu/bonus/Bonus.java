package modele.jeu.bonus;

public abstract class Bonus {

   private final String init;

   protected Bonus(String init) {
      this.init = init;
   }

   public String toString() {
      return this.init;
   }

   public abstract int getNombreRestant();

   // TODO : Implementer les bonus pour leur donner des actions.
   public abstract void utiliser();

   public String getInit() {
      return init;
   }
}

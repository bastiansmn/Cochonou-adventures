package modele.jeu.bonus;

import java.io.Serializable;

public abstract class Bonus implements Serializable {

   private final String init;
   protected int nbrRestant;

   protected Bonus(String init) {
      this.init = init;
   }

   public String toString() {
      return this.init;
   }

   public abstract int getNombreRestant();

   public abstract void utiliser(int i, int j);

   public String getInit() {
      return init;
   }
}

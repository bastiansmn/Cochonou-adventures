package modele.jeu.animaux;

public class Cochon extends Animal{
   public Cochon(String nom, String initiale) {
      super(nom, initiale);
   }

   @Override
   public String toString() {
      return "Cochon {" +
            "nom = '" + getNom() + '\'' +
            '}';
   }
}

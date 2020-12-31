package modele.jeu.animaux;

public class Cochon extends Animal{
   public Cochon(String nom) {
      super(nom, "Co");
   }

   @Override
   public String toString() {
      return "Cochon {" +
            "nom = '" + getNom() + '\'' +
            '}';
   }
}

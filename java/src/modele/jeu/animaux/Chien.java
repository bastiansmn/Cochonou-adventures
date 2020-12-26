package modele.jeu.animaux;

public class Chien extends Animal {

   public Chien(String nom) {
      super(nom, "C");
   }

   @Override
   public String toString() {
      return "Chien {" +
            "nom='" + getNom() + '\'' +
            '}';
   }
}

package modele.jeu.animaux;

public class Oiseau extends Animal {

   public Oiseau(String nom) {
      super(nom, "o");
   }

   @Override
   public String toString() {
      return "Oiseau {" +
            "nom='" + getNom() + '\'' +
            '}';
   }
}

package modele.jeu.animaux;

public class Chat extends Animal {

   public Chat(String nom) {
      super(nom, "c");
   }

   @Override
   public String toString() {
      return "Chat {" +
            "nom='" + getNom() + '\'' +
            '}';
   }
}

package modele.jeu.animaux;

import modele.jeu.grille.CaseType;
import modele.jeu.grille.blocs.Deplacable;

public class Animal extends CaseType implements Animaux, Deplacable {

   private String nom;
   private final String initiale;


   public Animal(String nom, String initiale) {
      this.nom = nom;
      this.initiale = initiale;
   }

   public String  getInitiale() {
      return this.initiale;
   }

   @Override
   public String toString() {
      return "Animal {" +
            "nom='" + nom + '\'' +
            '}';
   }

   public String getNom() {
      return nom;
   }
}

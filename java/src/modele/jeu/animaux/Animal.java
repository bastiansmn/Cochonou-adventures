package modele.jeu.animaux;

import modele.jeu.grille.CaseType;
import modele.jeu.grille.blocs.Deplacable;
import modele.outils.coordonnees.Coordonnees;

public class Animal extends CaseType implements Animaux, Deplacable {

   private String nom;
   private final String initiale;

   public Coordonnees position = null;

   public Animal(String nom, String initiale) {
      this.nom = nom;
      this.initiale = initiale;
   }

   public Animal(String nom, Coordonnees c, String initiale) {
      this.nom = nom;
      this.position = c;
      this.initiale = initiale;
   }


   @Override
   public void deplacer(Coordonnees c) {
      this.position = c;
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

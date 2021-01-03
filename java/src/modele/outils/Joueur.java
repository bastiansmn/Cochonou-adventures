package modele.outils;

import modele.jeu.bonus.Bonus;

import java.io.Serializable;

public class Joueur implements Serializable {

   private final String nom;
   private int vie;
   private int score;

   public Bonus[] bonus = new Bonus[5];


   public Joueur(String nom) {
      this.nom = nom;
      this.vie = 5;
      this.score = 0;
   }

   @Override
   public String toString() {
      return "Joueur{" +
            "nom='" + nom + '\'' +
            ", vie=" + vie +
            ", score=" + score +
            '}';
   }

   public void perdreUneVie() {
      if (!plusDeVie())
         this.vie--;
   }

   public void gagner(int n) {
      this.score += n;
   }

   public boolean plusDeVie() {
      return this.vie == 0;
   }

   public int getScore() {
      return score;
   }

   public int getVie() {
      return vie;
   }

   public String getNom() {
      return nom;
   }

   public Bonus[] getBonus() {
      return bonus;
   }

   public void setScore(int score) {
      this.score = score;
   }
}

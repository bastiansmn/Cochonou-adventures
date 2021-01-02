package modele.outils;

import modele.jeu.bonus.Bonus;

public class Joueur {

   private final String nom;
   private int vie;
   private int score;

   public static final Bonus[] bonus = new Bonus[5];


   public Joueur(String nom) {
      this.nom = nom;
      this.vie = 5;
      this.score = 0;
   }


   public boolean perdreUneVie() {
      if (plusDeVie()) {
         return false;
      }
      this.vie--;
      return true;
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

package modele.outils;

import modele.jeu.bonus.Bonus;

public class Joueur {

   private final int ID;

   private static int nbrJoueur = 0;

   private final String nom;
   private int vie;
   private int argent;

   public static final Bonus[] bonus = new Bonus[5];


   public Joueur(String nom) {
      this.ID = nbrJoueur;
      nbrJoueur++;
      this.nom = nom;
      this.vie = 5;
      this.argent = 3000;
   }


   public boolean perdreUneVie() {
      if (plusDeVie()) {
         return false;
      }
      this.vie--;
      return true;
   }

   public void gagnerArgent(int n) {
      this.argent += n;
   }

   public boolean plusDeVie() {
      return this.vie == 0;
   }

   public int getArgent() {
      return argent;
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
}

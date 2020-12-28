package modele.jeu;

import modele.outils.erreurs.Erreur;

import java.util.LinkedList;

public class Plateau {

   private final LinkedList<Niveau> niveaux;
   private int indexNiveauActuel = 0;

   public Plateau() {
      this.niveaux = new LinkedList<>();
   }


   public void ajouterNiveau(Niveau n) {
      this.niveaux.add(n);
   }

   public void afficher() {
      for (int i = 0; i < niveaux.size(); i++) {
         if (i == indexNiveauActuel) {
            System.out.print(" -> ");
         } else {
            System.out.print("    ");
         }
         niveaux.get(i).afficher();
      }
   }

   public Niveau jouerNiveauParNumero(int num) {
      if (num < niveaux.size()) {
         if (indexNiveauActuel > num)
            while (indexNiveauActuel > num)
               deplacerGauche();
         else if (indexNiveauActuel < num)
            while (indexNiveauActuel < num)
               deplacerDroite();

        return niveaux.get(num);
      }
      return null;
   }

   public void deplacerDroite() {
      if (this.indexNiveauActuel < this.niveaux.size() - 1) {
         this.indexNiveauActuel++;
      } else {
         Erreur.afficher("Immpossible de se déplacer à droite");
      }
   }

   public void deplacerGauche() {
      if (this.indexNiveauActuel > 0) {
         this.indexNiveauActuel--;
      } else {
         Erreur.afficher("Impossible de se déplacer à gauche");
      }
   }

   public void goToNextLevel() {
      for (Niveau n : niveaux) {
         if (n.isGagne()) {
            indexNiveauActuel = n.getNumNiveau() + 1;
            return;
         }
      }
   }

   public int getIndexNiveauActuel() {
      return indexNiveauActuel;
   }

   public LinkedList<Niveau> getNiveaux() {
      return niveaux;
   }
}

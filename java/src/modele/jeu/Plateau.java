package modele.jeu;

import modele.outils.erreurs.Erreur;

import java.io.Serializable;
import java.util.LinkedList;

public class Plateau implements Serializable {

   private final LinkedList<Niveau> niveaux;
   private int indexNiveauActuel = 0;

   public Plateau() {
      this.niveaux = new LinkedList<>();
   }


   public void ajouterNiveau(Niveau n) {
      this.niveaux.add(n);
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
      if (indexNiveauActuel + 1 < this.niveaux.size())
         indexNiveauActuel++;
   }

   public int getIndexNiveauActuel() {
      return indexNiveauActuel;
   }

   public LinkedList<Niveau> getNiveaux() {
      return niveaux;
   }

   public void setNiveau(int index, Niveau n) {
      this.niveaux.set(index, n);
   }
}

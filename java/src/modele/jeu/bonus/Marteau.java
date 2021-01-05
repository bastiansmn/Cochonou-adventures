package modele.jeu.bonus;

import modele.jeu.Jeu;
import modele.jeu.Niveau;
import modele.jeu.grille.Grille;
import modele.jeu.grille.blocs.Ouvrable;

public class Marteau extends Bonus {


   public Marteau() {
      super("ma");
      this.nbrRestant = 9;
   }

   @Override
   public int getNombreRestant() {
      return this.nbrRestant;
   }

   @Override
   public void utiliser(int i, int j) {
      if (nbrRestant > 0) {
         Grille g = Jeu.plateau.getNiveaux().get(Jeu.plateau.getIndexNiveauActuel()).getGrille();

         if (g.getCases()[i][j] != null && g.getCases()[i][j].getContent() instanceof Ouvrable) {
            nbrRestant--;
            g.viderCase(i, j);
         }
      }
   }
}

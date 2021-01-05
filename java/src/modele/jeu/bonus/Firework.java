package modele.jeu.bonus;

import modele.jeu.Jeu;
import modele.jeu.grille.Grille;
import modele.jeu.grille.blocs.BlocBombe;
import modele.jeu.grille.blocs.Ouvrable;

public class Firework extends Bonus {

   public Firework() {
      super("fw");
      this.nbrRestant = 3;
   }

   @Override
   public int getNombreRestant() {
      return this.nbrRestant;
   }

   @Override
   public void utiliser(int i, int j) {
      if (nbrRestant > 0) {
         Grille g = Jeu.plateau.getNiveaux().get(Jeu.plateau.getIndexNiveauActuel()).getGrille();
         nbrRestant--;
         for (int k = g.getLongueur() - 1; k >= 0; k--) {
            if (g.getCases()[k][j] != null && g.getCases()[k][j].getContent() instanceof Ouvrable) {
               if (g.getCases()[k][j].getContent() instanceof BlocBombe)
                  ((BlocBombe) g.getCases()[k][j].getContent()).open(g, k, j);
               else if (g.getCases()[k][j].getContent() instanceof Ouvrable)
                  g.viderCase(k, j);
            }
         }
      }

   }
}

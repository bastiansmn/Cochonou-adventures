package modele.jeu.bonus;

import modele.jeu.Jeu;
import modele.jeu.grille.Grille;
import modele.jeu.grille.blocs.BlocBombe;
import modele.jeu.grille.blocs.Ouvrable;

public class LineBreaker extends Bonus {
   public LineBreaker() {
      super("li");
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
         for (int k = 0; k < g.getLargeur(); k++) {
            if (g.getCases()[i][k] != null && g.getCases()[i][k].getContent() instanceof Ouvrable) {
               if (g.getCases()[i][k].getContent() instanceof BlocBombe)
                  ((BlocBombe) g.getCases()[i][k].getContent()).open(g, i, k);
               else if (g.getCases()[i][k].getContent() instanceof Ouvrable)
                  g.viderCase(i, k);
            }
         }
      }
   }
}

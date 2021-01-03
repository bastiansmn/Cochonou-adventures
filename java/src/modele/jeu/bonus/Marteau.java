package modele.jeu.bonus;

import modele.jeu.Jeu;
import modele.jeu.Niveau;
import modele.jeu.grille.blocs.Ouvrable;

public class Marteau extends Bonus {


   public Marteau() {
      super("ma");
      this.nbrRestant = 10;
   }

   @Override
   public int getNombreRestant() {
      return this.nbrRestant;
   }

   @Override
   public void utiliser(int i, int j) {
      if (nbrRestant > 0)
         nbrRestant--;

      Niveau.Grille g = Jeu.plateau.getNiveaux().get(Jeu.plateau.getIndexNiveauActuel()).getGrille();

      if (g.getCases()[i][j] != null && g.getCases()[i][j].getContent() instanceof Ouvrable)
         g.viderCase(i, j);
   }
}

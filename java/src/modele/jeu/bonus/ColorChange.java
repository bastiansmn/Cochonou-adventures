package modele.jeu.bonus;

import modele.jeu.Jeu;
import modele.jeu.Niveau;
import modele.jeu.grille.blocs.BlocCouleur;

import java.awt.*;
import java.util.Random;

public class ColorChange extends Bonus{

   public ColorChange() {
      super("co");
      this.nbrRestant = 7;
   }

   @Override
   public int getNombreRestant() {
      return this.nbrRestant;
   }

   @Override
   public void utiliser(int i, int j) {
      if (nbrRestant > 0) {
         nbrRestant--;

         Niveau.Grille g = Jeu.plateau.getNiveaux().get(Jeu.plateau.getIndexNiveauActuel()).getGrille();

         Color[] rand = new Color[] {Color.YELLOW, Color.RED, Color.GREEN, Color.BLUE, Color.PINK};

         if (g.getCases()[i][j] != null && g.getCases()[i][j].getContent() instanceof BlocCouleur) {
            Color c;
            do {
               c = rand[new Random().nextInt(5)];
            } while (c == ((BlocCouleur) g.getCases()[i][j].getContent()).getColor());
            changerCouleur(g, i, j, ((BlocCouleur) g.getCases()[i][j].getContent()).getColor(), true, c);
         }
      }
   }

   public void changerCouleur(Niveau.Grille g, int i, int j, Color c, boolean premiereOuverture, Color aChanger) {
      try {
         if (g.getCases()[i][j] != null && g.getCases()[i][j].getContent() instanceof BlocCouleur)
            if (((BlocCouleur) g.getCases()[i][j].getContent()).comboPossible(g, i, j, c) || ((BlocCouleur) g.getCases()[i][j].getContent()).getColor() == c && !premiereOuverture) {
               g.remplirCase(i, j, new BlocCouleur(aChanger));
               changerCouleur(g, i - 1, j, c, false, aChanger);
               changerCouleur(g, i, j - 1, c, false, aChanger);
               changerCouleur(g, i + 1, j, c, false, aChanger);
               changerCouleur(g, i, j + 1, c, false, aChanger);
            }
      } catch (ArrayIndexOutOfBoundsException ignored) {}
   }

}

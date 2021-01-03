package modele.jeu.bonus;

import modele.jeu.Jeu;
import modele.jeu.Niveau;
import modele.jeu.grille.blocs.BlocCouleur;
import modele.jeu.grille.blocs.Ouvrable;

import java.awt.*;

public class ColorChange extends Bonus{

   private int nbrRestant;

   public ColorChange() {
      super("co");
      this.nbrRestant = 0;
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

         if (g.getCases()[i][j] != null && g.getCases()[i][j].getContent() instanceof BlocCouleur) {
            changerCouleur(g, i, j, ((BlocCouleur) g.getCases()[i][j].getContent()).getColor(), true);
         }
      }
   }

   public void changerCouleur(Niveau.Grille g, int i, int j, Color c, boolean premiereOuverture) {
      try {
         if (g.getCases()[i][j] != null && g.getCases()[i][j].getContent() instanceof BlocCouleur)
            if (comboPossible(g, i, j, c) || ((BlocCouleur) g.getCases()[i][j].getContent()).getColor() == c && !premiereOuverture) {
               g.remplirCase(i, j, new BlocCouleur(Color.PINK));
               changerCouleur(g, i - 1, j, c, false);
               changerCouleur(g, i, j - 1, c, false);
               changerCouleur(g, i + 1, j, c, false);
               changerCouleur(g, i, j + 1, c, false);
            }
      } catch (ArrayIndexOutOfBoundsException ignored) {}
   }

   public boolean comboPossible(Niveau.Grille g, int i, int j, Color c) {
      if (g.getCases()[i][j].getContent() instanceof BlocCouleur && ((BlocCouleur) g.getCases()[i][j].getContent()).getColor() == c) {
         try {
            if (g.getCases()[i][j + 1] != null && g.getCases()[i][j + 1].getContent() instanceof BlocCouleur && ((BlocCouleur) g.getCases()[i][j + 1].getContent()).getColor() == c)
               return true;
         } catch (ArrayIndexOutOfBoundsException ignored) {}
         try {
            if (g.getCases()[i][j - 1] != null && g.getCases()[i][j - 1].getContent() instanceof BlocCouleur && ((BlocCouleur) g.getCases()[i][j - 1].getContent()).getColor() == c)
               return true;
         } catch (ArrayIndexOutOfBoundsException ignored) {}
         try {
            if (g.getCases()[i - 1][j] != null && g.getCases()[i - 1][j].getContent() instanceof BlocCouleur && ((BlocCouleur) g.getCases()[i - 1][j].getContent()).getColor() == c)
               return true;
         } catch (ArrayIndexOutOfBoundsException ignored) {}
         try {
            if (g.getCases()[i + 1][j] != null && g.getCases()[i + 1][j].getContent() instanceof BlocCouleur && ((BlocCouleur) g.getCases()[i + 1][j].getContent()).getColor() == c)
               return true;
         } catch (ArrayIndexOutOfBoundsException ignored) {}
      }
      return false;
   }
}

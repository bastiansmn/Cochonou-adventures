package modele.jeu.grille.blocs;

import modele.jeu.Niveau;
import modele.jeu.grille.Grille;

public class BlocBombe extends Bloc implements Deplacable, Ouvrable {
   @Override
   public int open(Grille g, int i, int j) {
      int summ = 0;
      g.viderCase(i, j);
      try {
         if (g.getCases()[i][j+1].getContent() instanceof BlocBombe)
            ((BlocBombe) g.getCases()[i][j+1].getContent()).open(g, i, j+1);
         else if (g.getCases()[i][j+1].getContent() instanceof Ouvrable)
            g.viderCase(i, j+1);
         summ++;
      } catch (NullPointerException | ArrayIndexOutOfBoundsException ignored) {}
      try {
         if (g.getCases()[i][j-1].getContent() instanceof BlocBombe)
            ((BlocBombe) g.getCases()[i][j-1].getContent()).open(g, i, j-1);
         else if (g.getCases()[i][j-1].getContent() instanceof Ouvrable)
            g.viderCase(i, j-1);
         summ++;
      } catch (NullPointerException | ArrayIndexOutOfBoundsException ignored) {}
      try {
         if (g.getCases()[i-1][j-1].getContent() instanceof BlocBombe)
            ((BlocBombe) g.getCases()[i-1][j-1].getContent()).open(g, i-1, j-1);
         else if (g.getCases()[i-1][j-1].getContent() instanceof Ouvrable)
            g.viderCase(i-1, j-1);
         summ++;
      } catch (NullPointerException | ArrayIndexOutOfBoundsException ignored) {}
      try {
         if (g.getCases()[i-1][j].getContent() instanceof BlocBombe)
            ((BlocBombe) g.getCases()[i-1][j].getContent()).open(g, i-1, j);
         else if (g.getCases()[i-1][j].getContent() instanceof Ouvrable)
            g.viderCase(i-1, j);
         summ++;
      } catch (NullPointerException | ArrayIndexOutOfBoundsException ignored) {}
      try {
         if (g.getCases()[i-1][j+1].getContent() instanceof BlocBombe)
            ((BlocBombe) g.getCases()[i-1][j+1].getContent()).open(g, i-1, j+1);
         else if (g.getCases()[i-1][j+1].getContent() instanceof Ouvrable)
            g.viderCase(i-1, j+1);
         summ++;
      } catch (NullPointerException | ArrayIndexOutOfBoundsException ignored) {}
      try {
         if (g.getCases()[i+1][j-1].getContent() instanceof BlocBombe)
            ((BlocBombe) g.getCases()[i+1][j-1].getContent()).open(g, i+1, j-1);
         else if (g.getCases()[i+1][j-1].getContent() instanceof Ouvrable)
            g.viderCase(i+1, j-1);
         summ++;
      } catch (NullPointerException | ArrayIndexOutOfBoundsException ignored) {}
      try {
         if (g.getCases()[i+1][j].getContent() instanceof BlocBombe)
            ((BlocBombe) g.getCases()[i+1][j].getContent()).open(g, i+1, j);
         else if (g.getCases()[i+1][j].getContent() instanceof Ouvrable)
            g.viderCase(i+1, j);
         summ++;
      } catch (NullPointerException | ArrayIndexOutOfBoundsException ignored) {}
      try {
         if (g.getCases()[i+1][j+1].getContent() instanceof BlocBombe)
            ((BlocBombe) g.getCases()[i+1][j+1].getContent()).open(g, i+1, j+1);
         else if (g.getCases()[i+1][j+1].getContent() instanceof Ouvrable)
            g.viderCase(i+1, j+1);
         summ++;
      } catch (NullPointerException | ArrayIndexOutOfBoundsException ignored) {}

      return summ;
   }
}

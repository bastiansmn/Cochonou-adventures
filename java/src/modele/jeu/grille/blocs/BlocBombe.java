package modele.jeu.grille.blocs;

import modele.jeu.grille.Grille;

public class BlocBombe extends Bloc implements Deplacable, Ouvrable {
   @Override
   public int open(Grille g, int i, int j) {
      int summ = 0;
      try {
         if (g.getCases()[i][j+1].getContent() instanceof Ouvrable) {
            g.viderCase(i, j+1);
            summ++;
         }
      } catch (NullPointerException | ArrayIndexOutOfBoundsException ignored) {}
      try {
         if (g.getCases()[i][j-1].getContent() instanceof Ouvrable) {
            g.viderCase(i, j-1);
            summ++;
         }
      } catch (NullPointerException | ArrayIndexOutOfBoundsException ignored) {}
      try {
         if (g.getCases()[i-1][j-1].getContent() instanceof Ouvrable) {  g.viderCase(i-1, j-1);
            summ++;
         }
      } catch (NullPointerException | ArrayIndexOutOfBoundsException ignored) {}
      try {
         if (g.getCases()[i-1][j].getContent() instanceof Ouvrable) {  g.viderCase(i-1, j);
            summ++;
         }
      } catch (NullPointerException | ArrayIndexOutOfBoundsException ignored) {}
      try {
         if (g.getCases()[i-1][j+1].getContent() instanceof Ouvrable) {
            g.viderCase(i-1, j+1);
            summ++;
         }
      } catch (NullPointerException | ArrayIndexOutOfBoundsException ignored) {}
      try {
         if (g.getCases()[i+1][j-1].getContent() instanceof Ouvrable) {  g.viderCase(i+1, j-1);
            summ++;
         }
      } catch (NullPointerException | ArrayIndexOutOfBoundsException ignored) {}
      try {
         if (g.getCases()[i+1][j].getContent() instanceof Ouvrable) {  g.viderCase(i+1, j);
            summ++;
         }
      } catch (NullPointerException | ArrayIndexOutOfBoundsException ignored) {}
      try {
         if (g.getCases()[i+1][j+1].getContent() instanceof Ouvrable) {  g.viderCase(i+1, j+1);
            summ++;
         }
      } catch (NullPointerException | ArrayIndexOutOfBoundsException ignored) {}

      g.viderCase(i, j);

      return summ;
   }
}

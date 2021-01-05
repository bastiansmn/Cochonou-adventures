package modele.jeu.bonus;

import modele.jeu.Jeu;
import modele.jeu.Niveau;
import modele.jeu.grille.Grille;
import modele.jeu.grille.blocs.BlocBombe;
import modele.jeu.grille.blocs.Ouvrable;

public class Bombe extends Bonus {

   public Bombe() {
      super("bo");
      this.nbrRestant = 5;
   }

   @Override
   public int getNombreRestant() {
      return this.nbrRestant;
   }

   @Override
   public void utiliser(int i, int j) {
      if (nbrRestant > 0)
         nbrRestant--;

      Grille g = Jeu.plateau.getNiveaux().get(Jeu.plateau.getIndexNiveauActuel()).getGrille();

      g.viderCase(i, j);
      try {
         if (g.getCases()[i][j+1].getContent() instanceof BlocBombe)
            ((BlocBombe) g.getCases()[i][j+1].getContent()).open(g, i, j+1);
         else if (g.getCases()[i][j+1].getContent() instanceof Ouvrable)
            g.viderCase(i, j+1);
      } catch (NullPointerException | ArrayIndexOutOfBoundsException ignored) {}
      try {
         if (g.getCases()[i][j-1].getContent() instanceof BlocBombe)
            ((BlocBombe) g.getCases()[i][j-1].getContent()).open(g, i, j-1);
         else if (g.getCases()[i][j-1].getContent() instanceof Ouvrable)
            g.viderCase(i, j-1);
      } catch (NullPointerException | ArrayIndexOutOfBoundsException ignored) {}
      try {
         if (g.getCases()[i-1][j-1].getContent() instanceof BlocBombe)
            ((BlocBombe) g.getCases()[i-1][j-1].getContent()).open(g, i-1, j-1);
         else if (g.getCases()[i-1][j-1].getContent() instanceof Ouvrable)
            g.viderCase(i-1, j-1);
      } catch (NullPointerException | ArrayIndexOutOfBoundsException ignored) {}
      try {
         if (g.getCases()[i-1][j].getContent() instanceof BlocBombe)
            ((BlocBombe) g.getCases()[i-1][j].getContent()).open(g, i-1, j);
         else if (g.getCases()[i-1][j].getContent() instanceof Ouvrable)
            g.viderCase(i-1, j);
      } catch (NullPointerException | ArrayIndexOutOfBoundsException ignored) {}
      try {
         if (g.getCases()[i-1][j+1].getContent() instanceof BlocBombe)
            ((BlocBombe) g.getCases()[i-1][j+1].getContent()).open(g, i-1, j+1);
         else if (g.getCases()[i-1][j+1].getContent() instanceof Ouvrable)
            g.viderCase(i-1, j+1);
      } catch (NullPointerException | ArrayIndexOutOfBoundsException ignored) {}
      try {
         if (g.getCases()[i+1][j-1].getContent() instanceof BlocBombe)
            ((BlocBombe) g.getCases()[i+1][j-1].getContent()).open(g, i+1, j-1);
         else if (g.getCases()[i+1][j-1].getContent() instanceof Ouvrable)
            g.viderCase(i+1, j-1);
      } catch (NullPointerException | ArrayIndexOutOfBoundsException ignored) {}
      try {
         if (g.getCases()[i+1][j].getContent() instanceof BlocBombe)
            ((BlocBombe) g.getCases()[i+1][j].getContent()).open(g, i+1, j);
         else if (g.getCases()[i+1][j].getContent() instanceof Ouvrable)
            g.viderCase(i+1, j);
      } catch (NullPointerException | ArrayIndexOutOfBoundsException ignored) {}
      try {
         if (g.getCases()[i+1][j+1].getContent() instanceof BlocBombe)
            ((BlocBombe) g.getCases()[i+1][j+1].getContent()).open(g, i+1, j+1);
         else if (g.getCases()[i+1][j+1].getContent() instanceof Ouvrable)
            g.viderCase(i+1, j+1);
      } catch (NullPointerException | ArrayIndexOutOfBoundsException ignored) {}
   }
}

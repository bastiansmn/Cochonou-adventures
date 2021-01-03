package modele.jeu.grille.blocs;

import modele.jeu.Niveau;

import java.awt.*;

public class BlocCouleur extends Bloc implements Deplacable, Ouvrable {

   public final String ANSI_RESET = "\u001B[0m";
   private final String termColor;

   private final Color color;
   private final String colorName;

   public BlocCouleur(Color color) {
      this.color = color;
      if (color.equals(Color.GREEN)) {
         termColor = "\u001B[32m";
         colorName = "vert";
      } else if (color.equals(Color.RED)) {
         termColor = "\u001B[31m";
         colorName = "rouge";
      } else if (color.equals(Color.YELLOW)) {
         termColor = "\u001B[33m";
         colorName = "jaune";
      } else if (color.equals(Color.BLUE)) {
         termColor = "\u001B[34m";
         colorName = "bleu";
      } else if (color.equals(Color.PINK)) {
         termColor = "\u001B[35m";
         colorName = "rose";
      } else {
         termColor = "";
         colorName = "";
      }
   }

   public Color getColor() {
      return color;
   }

   public String getColorName() {
      return colorName;
   }

   public void printColorInTerminal() {
      System.out.print(termColor + "██" + ANSI_RESET);
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

   public int ouvrirCase(Niveau.Grille g, int i, int j, Color c, boolean premiereOuverture) {
      int summ = 0;
      try {
         if (g.getCases()[i][j] != null && g.getCases()[i][j].getContent() instanceof BlocCouleur)
            if (comboPossible(g, i, j, c) || ((BlocCouleur) g.getCases()[i][j].getContent()).getColor() == c && !premiereOuverture) {
               g.viderCase(i, j);
               summ += ouvrirCase(g, i - 1, j, c, false);
               summ += ouvrirCase(g, i, j - 1, c, false);
               summ += ouvrirCase(g, i + 1, j, c, false);
               summ += ouvrirCase(g, i, j + 1, c, false);
               summ++;
            }
      } catch (ArrayIndexOutOfBoundsException ignored) {}
      return summ;
   }

   @Override
   public int open(Niveau.Grille g, int i, int j) {
      return ouvrirCase(g, i, j, this.getColor(), true);
   }
}

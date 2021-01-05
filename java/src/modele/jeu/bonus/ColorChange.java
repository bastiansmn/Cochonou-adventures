package modele.jeu.bonus;

import modele.jeu.Jeu;
import modele.jeu.Niveau;
import modele.jeu.grille.Grille;
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
         Grille g = Jeu.plateau.getNiveaux().get(Jeu.plateau.getIndexNiveauActuel()).getGrille();

         Color[] rand = new Color[] {Color.YELLOW, Color.RED, Color.GREEN, Color.BLUE, Color.PINK};

         if (g.getCases()[i][j] != null && g.getCases()[i][j].getContent() instanceof BlocCouleur) {
            nbrRestant--;
            Color c;
            do {
               c = rand[new Random().nextInt(5)];
            } while (c == ((BlocCouleur) g.getCases()[i][j].getContent()).getColor());
            changerCouleur(g, i, j, ((BlocCouleur) g.getCases()[i][j].getContent()).getColor(), c);
         }
      }
   }

   public void changerCouleur(Grille g, int i, int j, Color c, Color aChanger) {
      try {
         if (g.getCases()[i][j] != null && g.getCases()[i][j].getContent() instanceof BlocCouleur)
            if (((BlocCouleur) g.getCases()[i][j].getContent()).getColor() == c) {
               g.remplirCase(i, j, new BlocCouleur(aChanger));
               changerCouleur(g, i - 1, j, c, aChanger);
               changerCouleur(g, i, j - 1, c, aChanger);
               changerCouleur(g, i + 1, j, c, aChanger);
               changerCouleur(g, i, j + 1, c, aChanger);
            }
      } catch (ArrayIndexOutOfBoundsException ignored) {}
   }

}

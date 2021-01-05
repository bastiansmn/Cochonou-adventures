package modele.jeu;

import modele.jeu.grille.*;
import modele.outils.erreurs.CSVNotValidException;

import java.io.Serializable;

public class Niveau implements Serializable {

   private final int numNiveau;
   private static int nbrNiveau = 0;
   private boolean isGagne = false;
   private boolean canPlay = false;
   private Grille grille;

   public Niveau(Grille g, boolean canPlay) {
      this.numNiveau = nbrNiveau;
      nbrNiveau++;
      this.grille = g;
      this.canPlay = canPlay;
   }

   public Niveau() {
      numNiveau = Jeu.plateau.getIndexNiveauActuel();
   }

   public void gagner() {
      if (grille.isGagne()) {
         Jeu.joueur.gagner(grille.getScore());
         isGagne = true;
         try {
            this.grille = CSVImport.getCSV(grille.getFileName(), grille.getSeparator());
            Jeu.plateau.setNiveau(numNiveau, this);
         } catch (CSVNotValidException ignored) {}
         Jeu.plateau.setNiveau(numNiveau, this);
         if (numNiveau + 1 < Jeu.plateau.getNiveaux().size()) {
            Jeu.plateau.getNiveaux().get(numNiveau + 1).setJouable();
         }
         Jeu.plateau.deplacerDroite();
      }
   }

   public boolean isGagne() {
      return isGagne;
   }

   public void perdre() {
      if (grille.isPerdu()) {
         Jeu.joueur.perdreUneVie();
         try {
            this.grille = CSVImport.getCSV(grille.getFileName(), grille.getSeparator());
            Jeu.plateau.setNiveau(numNiveau, this);
         } catch (CSVNotValidException ignored) {}
      }
   }

   public void setJouable() {
      canPlay = true;
   }

   public void setGrille(Grille grille) {
      this.grille = grille;
   }

   public boolean canPlay() {
      return canPlay;
   }

   public int getNumNiveau() {
      return numNiveau;
   }

   public Grille getGrille() {
      return grille;
   }
}

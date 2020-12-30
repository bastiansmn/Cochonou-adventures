package modele.jeu.grille;

import modele.jeu.animaux.Chat;
import modele.jeu.animaux.Chien;
import modele.jeu.animaux.Oiseau;
import modele.jeu.animaux.Panda;
import modele.jeu.grille.blocs.BlocBombe;
import modele.jeu.grille.blocs.BlocCouleur;
import modele.jeu.grille.blocs.BlocObstacle;
import modele.outils.csv.CSVObject;
import modele.outils.erreurs.CSVNotValidException;

import java.awt.*;

public class CSVImport extends CSVObject {

   private Container[][] cases;

   public CSVImport(String fileName) {
      super(fileName);
      try {
         convertToGrid();
      } catch (CSVNotValidException e) {
         e.printStackTrace();
      }
   }

   public CSVImport(String fileName, String separator) {
      super(fileName, separator);
      try {
         convertToGrid();
      } catch (CSVNotValidException e) {
         e.printStackTrace();

      }
   }

   public CSVImport(String[][] sTab) {
      super(sTab);
      try {
         convertToGrid();
      } catch (CSVNotValidException e) {
         e.printStackTrace();
      }
   }

   public int getMaxMoves() {
      return Integer.parseInt(this.get(0, 0));
   }

   private void convertToGrid() throws CSVNotValidException {
      String[][] temp = this.getArray();
      this.cases = new Container[temp.length -1][temp[1].length];
      for (int i = 1; i < temp.length; i++) {
         for (int j = 0; j < temp[i].length; j++) {
            switch (this.get(i, j)) {
               case "null" -> this.cases[i-1][j] = null;
               case "" -> this.cases[i-1][j] = new Case(null);
               case "oiseau" -> this.cases[i-1][j] = new Case(new Oiseau(""));
               case "chat" -> this.cases[i-1][j] = new Case(new Chat(""));
               case "chien" -> this.cases[i-1][j] = new Case(new Chien(""));
               case "panda" -> this.cases[i-1][j] = new Case(new Panda(""));
               case "bombe" -> this.cases[i-1][j] = new Case(new BlocBombe());
               case "obstacle" -> this.cases[i-1][j] = new Case(new BlocObstacle());
               case "vert" -> this.cases[i-1][j] = new Case(new BlocCouleur(Color.GREEN));
               case "bleu" -> this.cases[i-1][j] = new Case(new BlocCouleur(Color.BLUE));
               case "rouge" -> this.cases[i-1][j] = new Case(new BlocCouleur(Color.RED));
               case "jaune" -> this.cases[i-1][j] = new Case(new BlocCouleur(Color.YELLOW));
               case "rose" -> this.cases[i-1][j] = new Case(new BlocCouleur(Color.PINK));

               default -> throw new CSVNotValidException(this.get(i, j) + " n'est pas un nom valide. \nVeuillez revoir le fichier CSV (" + this.getFile() + ")");
            }
         }
      }
   }

   public Container[][] getCases() {
      return cases;
   }
}

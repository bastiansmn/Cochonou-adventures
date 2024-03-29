package modele.jeu.grille;

import modele.jeu.Niveau;
import modele.jeu.animaux.*;
import modele.jeu.grille.blocs.BlocBombe;
import modele.jeu.grille.blocs.BlocCouleur;
import modele.jeu.grille.blocs.BlocObstacle;
import modele.outils.csv.CSVObject;
import modele.outils.erreurs.CSVNotValidException;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class CSVImport extends CSVObject {

   private Container[][] cases;

   public CSVImport(String fileName) {
      super(fileName, ",");
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
               case "null": this.cases[i-1][j] = null; break;
               case "": this.cases[i-1][j] = new Case(null); break;
               case "oiseau":  this.cases[i-1][j] = new Case(new Oiseau("")); break;
               case "chat":  this.cases[i-1][j] = new Case(new Chat("")); break;
               case "chien":  this.cases[i-1][j] = new Case(new Chien("")); break;
               case "cochon":  this.cases[i-1][j] = new Case(new Cochon("")); break;
               case "panda":  this.cases[i-1][j] = new Case(new Panda("")); break;
               case "bombe":  this.cases[i-1][j] = new Case(new BlocBombe()); break;
               case "obstacle":  this.cases[i-1][j] = new Case(new BlocObstacle()); break;
               case "vert":  this.cases[i-1][j] = new Case(new BlocCouleur(Color.GREEN)); break;
               case "bleu":  this.cases[i-1][j] = new Case(new BlocCouleur(Color.BLUE)); break;
               case "rouge":  this.cases[i-1][j] = new Case(new BlocCouleur(Color.RED)); break;
               case "jaune":  this.cases[i-1][j] = new Case(new BlocCouleur(Color.YELLOW)); break;
               case "rose":  this.cases[i-1][j] = new Case(new BlocCouleur(Color.PINK)); break;

               default:  throw new CSVNotValidException(this.get(i, j) + " n'est pas un nom valide. \nVeuillez revoir le fichier CSV (" + this.getFile() + ")");
            }
         }
      }
   }


   public void exportToCSV(Grille g) throws FileNotFoundException {
      PrintWriter printWriter = new PrintWriter("myNewLevel.csv");

      for (int i = 0; i < g.getLongueur(); i++) {
         for (int j = 0; j < g.getLargeur(); j++) {
            if (g.getCases()[i][j] == null) {
               printWriter.write("null" + ((j == g.getLargeur() - 1) ? "" : ";"));
            } else if (g.getCases()[i][j].getContent() == null) {
               printWriter.write("" + ((j == g.getLargeur() - 1) ? "" : ";"));
            } else if (g.getCases()[i][j].getContent() instanceof Chat) {
               printWriter.write("chat" + ((j == g.getLargeur() - 1) ? "" : ";"));
            } else if (g.getCases()[i][j].getContent() instanceof Chien) {
               printWriter.write("chien" + ((j == g.getLargeur() - 1) ? "" : ";"));
            } else if (g.getCases()[i][j].getContent() instanceof Panda) {
               printWriter.write("panda" + ((j == g.getLargeur() - 1) ? "" : ";"));
            } else if (g.getCases()[i][j].getContent() instanceof Oiseau) {
               printWriter.write("oiseau" + ((j == g.getLargeur() - 1) ? "" : ";"));
            } else if (g.getCases()[i][j].getContent() instanceof BlocCouleur) {
               printWriter.write(((BlocCouleur) g.getCases()[i][j].getContent()).getColorName() + ((j == g.getLargeur() - 1) ? "" : ";"));
            } else if (g.getCases()[i][j].getContent() instanceof BlocObstacle) {
               printWriter.write("obstacle" + ((j == g.getLargeur() - 1) ? "" : ";"));
            } else if (g.getCases()[i][j].getContent() instanceof BlocBombe) {
               printWriter.write("bombe" + ((j == g.getLargeur() - 1) ? "" : ";"));
            }
         }
         printWriter.write("\n");
      }

      printWriter.close();
   }

   public Grille getCSV(String fileName) throws CSVNotValidException {
      return getCSV(fileName, ",");
   }

   public static Grille getCSV(String fileName, String separator) throws CSVNotValidException {
      CSVImport csv = new CSVImport(fileName, separator);
      Grille res = new Grille();

      res.setFileName(fileName);
      res.setSeparator(separator);
      res.setCases(csv.getCases());
      res.setNombreLimiteDeCoup(csv.getMaxMoves());
      res.compteAnimaux();

      res.setLongueur(csv.getCases().length);
      res.setLargeur(csv.getCases()[0].length);

      return res;
   }

   public Container[][] getCases() {
      return cases;
   }
}

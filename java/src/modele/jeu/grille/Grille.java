package modele.jeu.grille;

import modele.jeu.animaux.*;
import modele.jeu.bonus.Bombe;
import modele.jeu.bonus.Bonus;
import modele.jeu.bonus.ColorChange;
import modele.jeu.bonus.Firework;
import modele.jeu.grille.blocs.*;
import modele.outils.Joueur;
import modele.outils.erreurs.CSVNotValidException;
import modele.outils.erreurs.Erreur;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;


public class Grille {


   private Container[][] cases;

   private int largeur, longueur;
   private int score = 0;
   private int animauxSauvee = 0;
   private int animauxRestants;

   public void run() {
      Scanner sc = new Scanner(System.in);
      boolean quitter = false;
      do {
         clear();
         scoreboard();
         afficherGrille();

         // Demander l'action à faire.
         String[] option = getOption();

         switch (Integer.parseInt(option[0])) {
            case 0:
               actionOuvertureGrille(option[1]);
               break;
            case 1:
               // TODO : Appeler le bonus correspondant à option[1]
               break;
            case 2:
               afficherAide();
               break;
            case 3:
               quitter = true;
               break;
         }
      } while (!gagne() && !quitter);
   }

   public void actionOuvertureGrille(String s) {
      int i = s.toUpperCase().charAt(0) - 65;
      int j = Integer.parseInt(s.substring(1)) - 1;

      if (i >= 0 && i < cases.length && j >= 0 && j < cases[i].length) {
         ouvrirCase(i, j);
      } else {
         Erreur.afficher("La case n'est pas valide");
      }
   }

   public void nettoyerGrille() {
      // Ajouter le code ici
   }

   public void ouvrirCase(int i, int j) {
      if (cases[i][j].getContent() instanceof BlocCouleur) {
         if (comboPossible(i, j, ((BlocCouleur) cases[i][j].getContent()).getColor())) {
            cases[i][j] = new Case(new BlocADetruire());
            // TODO : nettoyer grille (cf fonction nettoyerGrille() );
            ouvrirCase(i-1, j);
            ouvrirCase(i, j-1);
            ouvrirCase(i, j+1);
            ouvrirCase(i+1, j);
         } else {
            Erreur.afficher("Vous ne pouvez pas détruire ce bloc !");
         }
      }
   }

   public boolean comboPossible(int i, int j, Color c) {
      if (cases[i][j].getContent() instanceof BlocCouleur && ((BlocCouleur) cases[i][j].getContent()).getColor() == c) {
         try {
            if (cases[i][j + 1].getContent() instanceof BlocCouleur && ((BlocCouleur) cases[i][j + 1].getContent()).getColor() == c)
               return true;
         } catch (ArrayIndexOutOfBoundsException ignored) {}
         try {
            if (cases[i][j - 1].getContent() instanceof BlocCouleur && ((BlocCouleur) cases[i][j - 1].getContent()).getColor() == c)
               return true;
         } catch (ArrayIndexOutOfBoundsException ignored) {}
         try {
            if (cases[i - 1][j].getContent() instanceof BlocCouleur && ((BlocCouleur) cases[i][j + 1].getContent()).getColor() == c)
               return true;
         } catch (ArrayIndexOutOfBoundsException ignored) {}
         return cases[i + 1][j].getContent() instanceof BlocCouleur && ((BlocCouleur) cases[i][j - 1].getContent()).getColor() == c;
      }
      return false;
   }

   public boolean gagne() {
      return animauxRestants == 0;
   }

   private String[] getOption() {
      Scanner sc = new Scanner(System.in);
      String s;
      int option = -1;
      do {
         System.out.println("[? pour afficher l'aide]\nQue voulez vous jouer ?");
         s = sc.next();
         if (s.matches("[A-Z][0-9]+")) {
            option = 0; // Une case à détruire.
         } else if (s.matches("[a-z][a-z]")) {
            option = 1; // Ouvrir un bonus.
         } else if (s.equals("?")) {
            option = 2; // Ouvrir l'aide
         } else if (s.equals("q")) {
            option = 3; // Quitter le niveau
         } else {
            s = "";
         }
      } while (s.equals(""));
      return new String[]{Integer.toString(option), s};
   }

   private void afficherAide() {
      clear();
      System.out.println("""
               Entrez : 
               - Le numéro de case pour la détruire (sous la forme LETTRE/NOMBRE)
               - Les lettres du bonus pour l'utiliser   
               
               [Appuyez sur entrée pour quitter l'aide]   
            """);
      new Scanner(System.in).nextLine();
   }

   public void exportToCSV() throws FileNotFoundException {
      PrintWriter printWriter = new PrintWriter("myNewLevel.csv");

      for (int i = 0; i < longueur; i++) {
         for (int j = 0; j < largeur; j++) {
            if (this.cases[i][j] == null) {
               printWriter.write("null" + ((j == largeur - 1) ? "" : ";"));
            } else if (this.cases[i][j].getContent() == null) {
               printWriter.write("" + ((j == largeur - 1) ? "" : ";"));
            } else if (this.cases[i][j].getContent() instanceof Chat) {
               printWriter.write("chat" + ((j == largeur - 1) ? "" : ";"));
            } else if (this.cases[i][j].getContent() instanceof Chien) {
               printWriter.write("chien" + ((j == largeur - 1) ? "" : ";"));
            } else if (this.cases[i][j].getContent() instanceof Panda) {
               printWriter.write("panda" + ((j == largeur - 1) ? "" : ";"));
            } else if (this.cases[i][j].getContent() instanceof Oiseau) {
               printWriter.write("oiseau" + ((j == largeur - 1) ? "" : ";"));
            } else if (this.cases[i][j].getContent() instanceof BlocCouleur) {
               printWriter.write(((BlocCouleur) this.cases[i][j].getContent()).getColorName() + ((j == largeur - 1) ? "" : ";"));
            } else if (this.cases[i][j].getContent() instanceof BlocObstacle) {
               printWriter.write("obstacle" + ((j == largeur - 1) ? "" : ";"));
            } else if (this.cases[i][j].getContent() instanceof BlocBombe) {
               printWriter.write("bombe" + ((j == largeur - 1) ? "" : ";"));
            }
         }
         printWriter.write("\n");
      }

      printWriter.close();
   }

   // TODO : Pouvoir créer des tableaux aléatoirement (sans utiliser de CSV)


   private void compteAnimaux() {
      for (Container[] cont : cases)
         for (Container c : cont)
            if (c != null && c.getContent() instanceof Animal)
               animauxRestants++;
   }

   public void afficherGrille() {
      System.out.println(scoreboard());
      System.out.print("\t");
      for (int i = 0; i < this.cases[0].length; i++) {
         System.out.print((char) (i + 65) + " ");
      }
      System.out.println("\n  ┌─" + "──".repeat((this.largeur)) + "─┐");
      for (int i = 0; i < longueur; i++) {
         System.out.print((i + 1) + " ".repeat(Integer.toString(i + 1).length() % 2) + "│ ");
         for (int j = 0; j < largeur; j++) {
            if (this.cases[i][j] == null) {
               System.out.print("██");
            } else if (this.cases[i][j].getContent() == null) {
               System.out.print("  ");
            } else if (this.cases[i][j].getContent() instanceof Animal) {
               System.out.print(((Animal) this.cases[i][j].getContent()).getInitiale() + " ");
            } else if (this.cases[i][j].getContent() instanceof BlocCouleur) {
               ((BlocCouleur) this.cases[i][j].getContent()).printColorInTerminal();
            } else if (this.cases[i][j].getContent() instanceof BlocObstacle) {
               System.out.print("▓▓");
            } else if (this.cases[i][j].getContent() instanceof BlocBombe) {
               System.out.print("* ");
            }
         }
         System.out.println(" │");
      }
      System.out.println("  └─" + "──".repeat((this.largeur)) + "─┘");
   }

   public String scoreboard() {
      return "┌───────────────────┐\n" +
            "│  Score :  " + "0".repeat(6 - Integer.toString(this.score).length()) + this.score + "  │\n" +
            "│  Animaux :  " + this.animauxSauvee + '/' + this.animauxRestants + "   │\n" +
            "│  Bonus :          │\n" +
            "│  " + Arrays.toString(Joueur.bonus).replace("[", "").replace("]", "").replace(",", "").replaceAll("null", "--") + "   │\n" +
            "│  " + afficherNbrBonus() + "  │\n" +
            "└───────────────────┘\n";

   }

   private String afficherNbrBonus() {
      StringBuilder s = new StringBuilder();
      for (Bonus b : Joueur.bonus) {
         s.append("(").append(b.getNombreRestant()).append(")");
      }
      return s.toString();
   }

   public int getScore() {
      return score;
   }

   public int getAnimauxSauvee() {
      return animauxSauvee;
   }

   public int getAnimauxRestants() {
      return animauxRestants;
   }

   public Grille getCSV(String fileName) throws CSVNotValidException {
      return getCSV(fileName, ",");
   }

   public Grille getCSV(String fileName, String separator) throws CSVNotValidException {
      CSVImport csv = new CSVImport(fileName, separator);

      this.cases = csv.getCases();
      compteAnimaux();

      this.longueur = this.cases.length;
      this.largeur = this.cases[0].length;

      return this;
   }

   public void clear() {
      System.out.print("\033[H\033[2J");
      System.out.flush();
   }


   public static void main(String[] args) {
      Joueur.bonus[0] = new Firework();
      Joueur.bonus[1] = new ColorChange();
      Joueur.bonus[2] = new Bombe();
      Joueur.bonus[3] = new Bombe();
      Joueur.bonus[4] = new ColorChange();
      Grille g = new Grille();
      try {
         g = new Grille().getCSV("niveaux/nv1.csv", ";");
      } catch (CSVNotValidException e) {
         e.printStackTrace();
      }

      g.run();
   }
}

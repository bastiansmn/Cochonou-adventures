package vue;

import modele.jeu.Jeu;
import modele.jeu.Niveau;
import modele.jeu.animaux.Animal;
import modele.jeu.bonus.Bonus;
import modele.jeu.grille.Grille;
import modele.jeu.grille.blocs.BlocBombe;
import modele.jeu.grille.blocs.BlocCouleur;
import modele.jeu.grille.blocs.BlocObstacle;

import java.util.Arrays;
import java.util.Random;

public class BotPlayer {
   public boolean slowMode = Jeu.slowMode;

   public void runBot() throws InterruptedException {
      System.out.println("Le bot va lancer les niveaux dans l'ordre");
      Thread.sleep(1000);
      for (Niveau niveau : Jeu.plateau.getNiveaux()) {
         jouerNiveau(niveau);
         if (slowMode)
            Thread.sleep(500);
      }
   }

   private void jouerNiveau(Niveau n) throws InterruptedException {
      while  (!n.isGagne()) {
         jouerGrille(n.getGrille());
         clear();
      }
   }

   public void jouerGrille(Grille g) throws InterruptedException {
      do {
         clear();
         afficherGrille(g);

         // Générer l'action à faire.
         String[] bonusInit = new String[]{"fw", "bo", "li", "co", "ma"};

         StringBuilder toDo = new StringBuilder();
         boolean bonus = new Random().nextBoolean() && new Random().nextBoolean();

         if (bonus)
            toDo.append(bonusInit[new Random().nextInt(5)]).append(" ");

         toDo.append(Character.valueOf((char) (new Random().nextInt(7) + 65)));
         toDo.append(new Random().nextInt(7) + 1);

         if (toDo.toString().matches("[a-z][a-z] [A-Z][0-9]+"))
            g.actionBonus(toDo.substring(0, 2), Integer.parseInt(toDo.substring(4)) + (g.getLongueur() - 8), toDo.toString().toUpperCase().charAt(3) - 65);
         else
            g.actionOuvertureGrille(Integer.parseInt(toDo.substring(1)) + (g.getLongueur() - 8), toDo.toString().toUpperCase().charAt(0) - 65);

         if (slowMode)
            Thread.sleep(2000);
      } while (!g.isGagne() && !g.isPerdu());
      if (g.isGagne()) {
         clear();
         System.out.println("""
               Votre score :   """ + g.getScore() + """
                           
               Animaux sauvés :    """ + g.getAnimauxSauvee() + """      
                           
                     _____                          _\s
                    / ____|                    //  | |
                   | |  __  __ _  __ _ _ __   ___  | |
                   | | |_ |/ _` |/ _` | '_ \\ / _ \\ | |
                   | |__| | (_| | (_| | | | |  __/ |_|
                    \\_____|\\__,_|\\__, |_| |_|\\___| (_)
                                  __/ |              \s
                                 |___/               \s
                                 
               """);
         if (slowMode)
            Thread.sleep(500);
      } else if (g.isPerdu()) {
         System.out.println("""
                    _____             _         _\s
                   |  __ \\           | |       | |
                   | |__) |__ _ __ __| |_   _  | |
                   |  ___/ _ \\ '__/ _` | | | | | |
                   | |  |  __/ | | (_| | |_| | |_|
                   |_|   \\___|_|  \\__,_|\\__,_| (_)
                                                 \s
                                                 \s
                                                 
               """);
         if (slowMode)
            Thread.sleep(500);
      }
   }

   private void clear() {
      System.out.print("\033[H\033[2J");
      System.out.flush();
   }

   public void afficherGrille(Grille g) {
      System.out.println("┌───────────────────┐\n" +
            "│  Score : " + "0".repeat(7 - Integer.toString(g.getScore()).length()) + g.getScore() + "  │\n" +
            "│  Animaux :  " + g.getAnimauxSauvee() + '/' + g.getAnimauxRestants() + "   │\n" +
            "│  Coups rest. : " + g.getNombreLimiteDeCoup() + " ".repeat(Math.abs(Integer.toString(g.getNombreLimiteDeCoup()).length() - 2)) + " │\n" +
            "│  Bonus :          │\n" +
            "│  " + Arrays.toString(Jeu.joueur.bonus).replace("[", "").replace("]", "").replace(",", "").replaceAll("null", "--") + "   │\n" +
            "│  " + afficherNbrBonus() + "  │\n" +
            "└───────────────────┘\n");
      System.out.print("    ");
      for (int i = 0; i < g.getCases()[0].length; i++) {
         System.out.print((char) (i + 65) + " ");
      }
      System.out.println("\n  ┌─" + "──".repeat((g.getLargeur())) + "─┐");
      for (int i = g.getLongueur() - 7; i < g.getLongueur(); i++) {
         System.out.print((i - (g.getLongueur() - 8)) + " ".repeat(Integer.toString(i - (g.getLongueur() - 8)).length() % 2) + "│ ");
         for (int j = 0; j < 7; j++) {
            if (g.getCases()[i][j] == null) {
               System.out.print("██");
            } else if (g.getCases()[i][j].getContent() == null) {
               System.out.print("  ");
            } else if (g.getCases()[i][j].getContent() instanceof Animal) {
               System.out.print(((Animal) g.getCases()[i][j].getContent()).getInitiale() + " ".repeat(Math.abs(((Animal) g.getCases()[i][j].getContent()).getInitiale().length() - 2)));
            } else if (g.getCases()[i][j].getContent() instanceof BlocCouleur) {
               ((BlocCouleur) g.getCases()[i][j].getContent()).printColorInTerminal();
            } else if (g.getCases()[i][j].getContent() instanceof BlocObstacle) {
               System.out.print("▓▓");
            } else if (g.getCases()[i][j].getContent() instanceof BlocBombe) {
               System.out.print("* ");
            }
         }
         System.out.println(" │");
      }
      System.out.println("  └─" + "──".repeat((g.getLargeur())) + "─┘");
   }

   private String afficherNbrBonus() {
      StringBuilder s = new StringBuilder();
      for (Bonus b : Jeu.joueur.bonus) {
         s.append("(").append(b.getNombreRestant()).append(")");
      }
      return s.toString();
   }
}

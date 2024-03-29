package vue.terminal;

import modele.jeu.Jeu;
import modele.jeu.Niveau;
import modele.jeu.Plateau;
import modele.jeu.animaux.Animal;
import modele.jeu.bonus.Bonus;
import modele.jeu.grille.Grille;
import modele.jeu.grille.blocs.BlocBombe;
import modele.jeu.grille.blocs.BlocCouleur;
import modele.jeu.grille.blocs.BlocObstacle;
import modele.outils.erreurs.Erreur;

import java.util.Arrays;
import java.util.Scanner;

public class Launcher {

   public void runTextual() throws InterruptedException {
      clear();
      afficherTitreJeu();
      System.out.println("\n");
      Thread.sleep(1000);

      System.out.println("Bienvenue sur Cochonou-adventures " + Jeu.joueur.getNom() + " !\n\n");
      Thread.sleep(2000);

      String reponse;
      do {
         clear();
         affichageInfoJoueur();
         afficherPlateau(Jeu.plateau);
         System.out.println("\n[* = niveau déjà gagné]");
         System.out.println("[? pour afficher les règles]\n\n============================\n\nUtilisez z/s pour changer de niveau, d pour lancer le niveau sélectionné ou entrez le numéro d'un niveau :");
         reponse = new Scanner(System.in).next();

         if (reponse.matches("[0-9]+")) {
            Niveau n = Jeu.plateau.jouerNiveauParNumero(Integer.parseInt(reponse) - 1);
            if (n != null)
               jouerNiveau(n);
            else
               Erreur.afficher("Ce niveau n'existe pas " + Jeu.joueur.getNom() + " !");

         } else {
            switch (reponse) {
               case "z" -> Jeu.plateau.deplacerGauche();
               case "s" -> Jeu.plateau.deplacerDroite();
               case "d" -> jouerNiveau(Jeu.plateau.getNiveaux().get(Jeu.plateau.getIndexNiveauActuel()));
               case "?" -> reglesDuJeu();
               default -> Erreur.afficher("Euuuh ... " + Jeu.joueur.getNom() + " ? Je ne comprend pas trop ce que vous cherchez à me dire");
            }
         }

         clear();
      } while (!reponse.equals("q"));
   }

   private void jouerNiveau(Niveau n) {
      if (n.canPlay()) {
         try {
            System.out.println("Le niveau va commencer ...");
            Thread.sleep(1000);
         } catch (InterruptedException e) {
            e.printStackTrace();
         }
         System.out.println("\n");
         jouerGrille(n.getGrille());
         clear();
      } else {
         Erreur.afficher("Vous ne pouvez pas jouer ce niveau");
      }
   }

   public void jouerGrille(Grille g) {
      boolean quitter = false;
      do {
         clear();
         afficherGrille(g);

         // Demander l'action à faire.
         String[] option = getOptionGrille();

         switch (Integer.parseInt(option[0])) {
            case 0 -> g.actionOuvertureGrille(Integer.parseInt(option[1].substring(1)) + (g.getLongueur() - 8), option[1].toUpperCase().charAt(0) - 65);
            case 1 -> g.actionBonus(option[1].substring(0, 2), Integer.parseInt(option[1].substring(4)) + (g.getLongueur() - 8), option[1].toUpperCase().charAt(3) - 65);
            case 2 -> afficherAideGrille();
            case 3 -> quitter = true;
         }
      } while (!g.isGagne() && !quitter && !g.isPerdu());
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
                                 
                 Appuyez sur entrée pour quitter...
               """);
         new Scanner(System.in).nextLine();
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
                                                 
                   Appuyez sur entrée pour quitter ...
               """);
         new Scanner(System.in).nextLine();
      }
   }

   public void afficherPlateau(Plateau p) {
      for (int i = 0; i < p.getNiveaux().size(); i++) {
         if (i == p.getIndexNiveauActuel()) {
            System.out.print(" -> ");
         } else {
            System.out.print("    ");
         }
         afficherNiveau(p.getNiveaux().get(i));
      }
   }

   public void afficherNiveau(Niveau n) {
      System.out.println("Niveau {" + (n.getNumNiveau()+1) + "}" + (n.isGagne() ? " *" : ""));
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

   public String[] getOptionGrille() {
      Scanner sc = new Scanner(System.in);
      String s;
      int option = -1;
      do {
         System.out.println("[? pour afficher l'aide]\nQue voulez vous jouer ?");
         s = sc.nextLine();
         if (s.matches("^([A-Z][0-9]+)")) {
            option = 0; // Une case à détruire.
         } else if (s.matches("[a-z][a-z] [A-Z][0-9]+")) {
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

   public void afficherAideGrille() {
      clear();
      System.out.println("""
               Entrez : 
               - Le numéro de case pour la détruire (sous la forme LETTRE/NOMBRE)
               - Les lettres du bonus pour l'utiliser   
               
               [Appuyez sur entrée pour quitter l'aide]   
            """);
      new Scanner(System.in).nextLine();
   }

   private String afficherNbrBonus() {
      StringBuilder s = new StringBuilder();
      for (Bonus b : Jeu.joueur.bonus) {
         s.append("(").append(b.getNombreRestant()).append(")");
      }
      return s.toString();
   }

   private void afficherTitreJeu() {
      System.out.println("""
              _____           _                                                  _                 _                      \s
             / ____|         | |                                                | |               | |                     \s
            | |     ___   ___| |__   ___  _ __   ___  _   _   ______    __ _  __| |_   _____ _ __ | |_ _   _ _ __ ___  ___\s
            | |    / _ \\ / __| '_ \\ / _ \\| '_ \\ / _ \\| | | | |______|  / _` |/ _` \\ \\ / / _ \\ '_ \\| __| | | | '__/ _ \\/ __|
            | |___| (_) | (__| | | | (_) | | | | (_) | |_| |          | (_| | (_| |\\ V /  __/ | | | |_| |_| | | |  __/\\__ \\
             \\_____\\___/ \\___|_| |_|\\___/|_| |_|\\___/ \\__,_|           \\__,_|\\__,_| \\_/ \\___|_| |_|\\__|\\__,_|_|  \\___||___/
                                                                                                                          \s
                                                                                                                          \s""".indent(1));
   }

   private void affichageInfoJoueur() {
      System.out.printf("""
            ============================
                       
                Votre score : %d
                Votre nombre de vies : %d
                       
            %n""", Jeu.joueur.getScore(), Jeu.joueur.getVie());
   }

   private void clear() {
      System.out.print("\033[H\033[2J");
      System.out.flush();
   }

   public static void displayHelp() {
      System.out.println("""
               Aide du jeu : 
                  Veuillez lancer le jeu avec un mode (graphique ou terminal)
                  Pour cela :
                     - MODE GRAPHIQUE : utilisez l'argument "-g" ou "--graphical"
                     - MODE TERMINAL : utilisez l'argument "-t" ou "--textual"
                     
                  Pour le lien vers le projet : 
                  https://github.com/bastiansmn/Chochonou-adventures
            """);
   }

   public void reglesDuJeu() {
      clear();
      System.out.println("""
            But du jeu :
                - Dans chaque niveau, sauvez tous les animaux.
                - Cassez les blocs pour les faire descendre jusqu'en bas.
                - Utilisez vos bonus lorsque vous êtes coincé. 
                
            Comment jouer :
                - Entrez la position du bloc que vous souhaitez casser, ses blocs adjacents de même
            couleur seront détruits aussi.
                - Chaque animal est représenté par une lettre différente :
                   * Chat : c
                   * Chien : C
                   * Oiseau : o
                   * Panda : p
                   
            Appuyez sur entrée pour quitter...
             """);
      new Scanner(System.in).nextLine();
   }
}

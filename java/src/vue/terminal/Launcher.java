package vue.terminal;

import modele.jeu.Jeu;
import modele.jeu.Niveau;
import modele.jeu.grille.Grille;
import modele.outils.erreurs.Erreur;

import java.util.Scanner;

public class Launcher {

   public int runTextual() throws InterruptedException {

      // Affichage des titre et phrases de bases du jeu
      afficherTitreJeu();
      System.out.println("\n");
      Thread.sleep(3000);

      System.out.println("Bienvenue sur Cochonou-adventures " + Jeu.joueur.getNom() + " !\n\n");
      Thread.sleep(3000);

      String reponse;
      do {
         Jeu.plateau.afficher();
         System.out.println("[? pour afficher les règles]\nUtilisez z/s pour changer de niveau, d pour lancer le niveau sélectionné ou entrez le numéro d'un niveau :");
         reponse = new Scanner(System.in).next();

         if (reponse.matches("[0-9]+")) {
            Niveau n = Jeu.plateau.jouerNiveauParNumero(Integer.parseInt(reponse));
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

      return 0;
   }

   private void jouerNiveau(Niveau n) {
      try {
         System.out.println("Le niveau va commencer ...");
         Thread.sleep(1000);
      } catch (InterruptedException e) {
         e.printStackTrace();
      }
      System.out.println("\n");
      jouerGrille(n.getGrille());
      clear();
   }

   private void jouerGrille(Grille g) {
      boolean quitter = false;
      do {
         clear();
         g.scoreboard();
         g.afficherGrille();

         // Demander l'action à faire.
         String[] option = getOptionGrille();

         switch (Integer.parseInt(option[0])) {
            case 0 -> g.actionOuvertureGrille(option[1]);
            case 1 -> g.actionBonus(option[1]);
            case 2 -> afficherAideGrille();
            case 3 -> quitter = true;
         }
      } while (!g.gagne() && !quitter);
   }

   public String[] getOptionGrille() {
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
      System.out.println("""
           But du jeu :
               - Dans chaques niveau, sauvez tous les animaux.
               - Cassez les blocs pour les faire descendre jusqu'en bas.
               - Utilisez vos bonus lorsque vous êtes coincés. 
               
           Comment jouer :
               - Cliquez ou entrez la position du bloc que vous souhaitez casser, ses blocs adjacents de même
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

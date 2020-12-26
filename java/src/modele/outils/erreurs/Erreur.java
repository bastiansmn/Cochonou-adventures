package modele.outils.erreurs;

public class Erreur {
   public static void afficher(String s) {
      System.out.println("\u001B[31m" + s + "\u001B[0m");
   }
}

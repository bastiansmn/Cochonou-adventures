import modele.jeu.Jeu;

import java.lang.reflect.InvocationTargetException;


public class Jouer {
   public static void main(String[] args) throws InterruptedException, InvocationTargetException {
      if (args.length == 2) {
         System.out.println(new Jeu(args).run());
      } else
         vue.terminal.Launcher.displayHelp();
   }
}

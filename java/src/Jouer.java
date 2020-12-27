import modele.jeu.Jeu;

import java.lang.reflect.InvocationTargetException;


public class Jouer {
   public static void main(String[] args) throws InterruptedException, InvocationTargetException {
      if (args.length != 0) {
         System.out.println(new Jeu(args[0]).run(args));
      } else
         vue.terminal.Launcher.displayHelp();
   }
}

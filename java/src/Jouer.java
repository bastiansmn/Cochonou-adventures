import modele.jeu.Jeu;

import java.lang.reflect.InvocationTargetException;


public class Jouer {
   public static void main(String[] args) throws InterruptedException, InvocationTargetException {
      args = new String [2];
      args[0] = "Dylan";
      args[1] = "-g";
      if (args.length == 2) {
         System.out.println(new Jeu(args).run());
      } else
         vue.terminal.Launcher.displayHelp();
   }
}

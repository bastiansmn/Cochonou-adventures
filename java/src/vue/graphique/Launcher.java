package vue.graphique;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;

public class Launcher {


   public int runGraphical() throws InvocationTargetException, InterruptedException {
      SwingUtilities.invokeAndWait(() -> {
         // TODO : Instancier la fenetre
      });

      return 0;
   }
}

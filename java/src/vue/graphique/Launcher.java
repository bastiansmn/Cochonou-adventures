package vue.graphique;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;

public class Launcher {


   public void runGraphical() throws InvocationTargetException, InterruptedException {
      SwingUtilities.invokeAndWait(() -> {
         Fenetre g = new Fenetre();
         g.setVisible(true);
      });
   }
}

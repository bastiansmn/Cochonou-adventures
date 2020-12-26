package modele.jeu.grille.blocs;

import java.awt.*;

public class BlocCouleur extends Bloc {

   public final String ANSI_RESET = "\u001B[0m";
   private final String termColor;

   private final Color color;
   private final String colorName;

   public BlocCouleur(Color color) {
      this.color = color;
      if (color.equals(Color.GREEN)) {
         termColor = "\u001B[32m";
         colorName = "vert";
      } else if (color.equals(Color.RED)) {
         termColor = "\u001B[31m";
         colorName = "rouge";
      } else if (color.equals(Color.YELLOW)) {
         termColor = "\u001B[33m";
         colorName = "jaune";
      } else if (color.equals(Color.BLUE)) {
         termColor = "\u001B[34m";
         colorName = "bleu";
      } else if (color.equals(Color.PINK)) {
         termColor = "\u001B[35m";
         colorName = "rose";
      } else {
         termColor = "";
         colorName = "";
      }
   }

   public Color getColor() {
      return color;
   }

   public String getColorName() {
      return colorName;
   }

   public void printColorInTerminal() {
      System.out.print(termColor + "██" + ANSI_RESET);
   }


}

package modele.jeu.grille;

import java.io.Serializable;

public class Container implements Serializable {
   private CaseType content;

   public Container(CaseType c) {
      this.content = c;
   }

   public CaseType getContent() {
      return content;
   }
}

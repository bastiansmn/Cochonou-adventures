package modele.jeu.grille;

public class Container {
   private CaseType content;

   public Container(CaseType c) {
      this.content = c;
   }

   public CaseType getContent() {
      return content;
   }
}

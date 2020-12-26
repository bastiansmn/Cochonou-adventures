package modele.outils.erreurs;

public enum CodesErreurs {
   CODE0(0, "Aucun problème d'exécution");
   // TODO : Ajouter les codes erreurs


   public final int code;
   public final String descr;

   CodesErreurs(int i, String descr) {
      this.code = i;
      this.descr = descr;
   }

   public int getCode() {
      return code;
   }

   public String getDescr() {
      return descr;
   }
}

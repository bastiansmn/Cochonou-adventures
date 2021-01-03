package modele.jeu.grille.blocs;

import modele.jeu.Niveau;

public interface Ouvrable {
   int open(Niveau.Grille g, int i, int j);
}

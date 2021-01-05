package modele.jeu.grille.blocs;

import modele.jeu.Niveau;
import modele.jeu.grille.Grille;

public interface Ouvrable {
   int open(Grille g, int i, int j);
}

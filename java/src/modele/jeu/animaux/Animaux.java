package modele.jeu.animaux;

import modele.outils.coordonnees.Coordonnees;

public interface Animaux {

   Coordonnees position = null;

   void deplacer(Coordonnees c);
}

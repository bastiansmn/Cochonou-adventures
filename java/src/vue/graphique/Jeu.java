package vue.graphique;

import modele.jeu.animaux.Animal;
import modele.jeu.grille.Container;
import modele.jeu.grille.blocs.BlocBombe;
import modele.jeu.grille.blocs.BlocCouleur;
import modele.jeu.grille.blocs.BlocObstacle;

import javax.swing.*;
import java.awt.*;

public class Jeu extends vue.graphique.ImagePanel {
    JeuGraphique fenetre;
    private Container[][] cases;

    private int largeur, longueur;
    private int score = 0;
    private int animauxSauvee = 0;
    private int animauxRestants;
    JButton[] boutons = new JButton[longueur*largeur];

    public Jeu(JeuGraphique f) {
        super(new ImageIcon("niveaux.jpg").getImage());
        this.fenetre = f;
        this.setLayout(new GridBagLayout());
        GridBagConstraints niveau = new GridBagConstraints();
        niveau.fill = GridBagConstraints.HORIZONTAL;
        int index = 0;
        for (int i = 0; i < longueur; i++) {
            for (int j = 0; j < largeur; j++) {
                boutons[index] = new JButton();
                niveau.gridx = j;
                niveau.gridy = i;
                this.add(boutons[index], niveau);
                if (this.cases[i][j].getContent() == null) {
                    boutons[index].setIcon(new ImageIcon("case-violet.jpg"));
                } else if (this.cases[i][j].getContent() instanceof Animal) {
                    boutons[index].setIcon(new ImageIcon("case-violet.jpg"));
                } else if (this.cases[i][j].getContent() instanceof BlocCouleur) {
                    boutons[index].setIcon(new ImageIcon("case-violet.jpg"));
                } else if (this.cases[i][j].getContent() instanceof BlocObstacle) {
                    boutons[index].setIcon(new ImageIcon("case-violet.jpg"));
                } else if (this.cases[i][j].getContent() instanceof BlocBombe) {
                    boutons[index].setIcon(new ImageIcon("case-violet.jpg"));
                }
            }
        }
        this.setVisible(true);
    }
}

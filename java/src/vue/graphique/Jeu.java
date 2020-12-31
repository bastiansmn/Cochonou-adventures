package vue.graphique;

import modele.jeu.Niveau;
import modele.jeu.animaux.*;
import modele.jeu.grille.blocs.BlocBombe;
import modele.jeu.grille.blocs.BlocCouleur;
import modele.jeu.grille.blocs.BlocObstacle;
import modele.jeu.grille.*;
import modele.outils.erreurs.Erreur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Jeu extends vue.graphique.ImagePanel implements ActionListener {
    JeuGraphique fenetre;
    Grille grille;
    JButton[] boutons;
    int index = 0;
    GridBagLayout gl = new GridBagLayout();

    public Jeu(JeuGraphique f, Grille g) {
        super(new ImageIcon("niveaux.jpg").getImage());
        this.fenetre = f;
        this.grille = g;
        this.setLayout(gl);
        this.setVisible(true);
        boutons = new JButton[g.getLongueur() * g.getLargeur()];
        Afficher(g);
    }

    public void Afficher(Grille g) {
        GridBagConstraints niveau = new GridBagConstraints();
        niveau.fill = GridBagConstraints.HORIZONTAL;
        for (int i = 0; i < g.getLongueur(); i++) {
            for (int j = 0; j < g.getLargeur(); j++) {
                if (g.getCases()[i][j] != null) {
                    this.boutons[index] = new JButton();
                    boutons[index].setOpaque(false);
                    boutons[index].setContentAreaFilled(false);
                    boutons[index].setBorderPainted(false);
                    niveau.gridx = j;
                    niveau.gridy = i;
                    this.add(boutons[index], niveau);
                    if (g.getCases()[i][j].getContent() instanceof Animal) {
                        if (g.getCases()[i][j].getContent() instanceof Chat) {
                            boutons[index].setIcon(new ImageIcon("case-chat.jpg"));
                        } else if (g.getCases()[i][j].getContent() instanceof Chien) {
                            boutons[index].setIcon(new ImageIcon("case-chien.jpg"));
                        } else if (g.getCases()[i][j].getContent() instanceof Cochon) {
                            boutons[index].setIcon(new ImageIcon("case-cochon.jpg"));
                        } else if (g.getCases()[i][j].getContent() instanceof Panda) {
                            boutons[index].setIcon(new ImageIcon("case-panda.jpg"));
                        }
                    } else if (g.getCases()[i][j].getContent() instanceof BlocCouleur) {
                        if (((BlocCouleur) g.getCases()[i][j].getContent()).getColor().equals(Color.GREEN)) {
                            boutons[index].setIcon(new ImageIcon("case-verte.jpg"));
                        } else if (((BlocCouleur) g.getCases()[i][j].getContent()).getColor().equals(Color.RED)) {
                            boutons[index].setIcon(new ImageIcon("case-rouge.jpg"));
                        } else if (((BlocCouleur) g.getCases()[i][j].getContent()).getColor().equals(Color.YELLOW)) {
                            boutons[index].setIcon(new ImageIcon("case-jaune.jpg"));
                        } else if (((BlocCouleur) g.getCases()[i][j].getContent()).getColor().equals(Color.BLUE)) {
                            boutons[index].setIcon(new ImageIcon("case-bleu.jpg"));
                        } else if (((BlocCouleur) g.getCases()[i][j].getContent()).getColor().equals(Color.PINK)) {
                            boutons[index].setIcon(new ImageIcon("case-violet.jpg"));
                        }
                    } else if (g.getCases()[i][j].getContent() instanceof BlocObstacle) {
                        boutons[index].setIcon(new ImageIcon("case-obstacle.jpg"));
                    } else if (g.getCases()[i][j].getContent() instanceof BlocBombe) {
                        boutons[index].setIcon(new ImageIcon("case-bombe.jpg"));
                    }
                    boutons[index].addActionListener(this);
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        GridBagConstraints test = gl.getConstraints((Component) e.getSource());
        Niveau n = new Niveau(fenetre, grille, grille.);
    }
}

package vue.graphique;

import modele.jeu.Jeu;
import modele.jeu.Niveau;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Menu extends vue.graphique.ImagePanel implements ActionListener {
    Fenetre fenetre;
    JLabel[] etiquettes = new JLabel[10];
    JButton precedent, suivant;
    ArrayList<JButton> boutons = new ArrayList<>();
    modele.jeu.Niveau.Grille g = modele.jeu.Jeu.plateau.getNiveaux().get(0).getGrille();
    JPanel partie = new vue.graphique.Partie(fenetre, g);
    int Niveau;

    public Menu(Fenetre f, int niveauActuel, int niveauMax) {
        super(new ImageIcon("fond.jpg").getImage());
        this.fenetre = f;
        this.Niveau = niveauActuel;
        this.setLayout(null);
        creationBoutons(niveauActuel, niveauMax);
        this.setVisible(true);
    }

    class Niveau extends JButton {
        public int index = 0;
        int niveau;

        Niveau(int i, int x, int y) {
            niveau = i + 1;
            etiquettes[index] = new JLabel("  NIVEAU " + niveau);
            if(Jeu.plateau.getNiveaux().get(i).getGrille().isGagne()) {
                etiquettes[index].setForeground(new Color(0, 0, 0));
                this.setBackground(new Color(243, 202, 32));
            } else {
                etiquettes[index].setForeground(new Color(243, 202, 32));
                this.setBackground(new Color(0, 0, 0));
            }
            this.setBounds(x, y, 100, 40);
            this.add(etiquettes[index]);
            index++;
        }
    }

    void creationBoutons(int niveauActuel, int niveauMax) {
        int max = 10;
        if ((niveauActuel - niveauActuel % 10) != 0) {
            precedent = new JButton("Précedent");
            precedent.setBounds(10, 750, 100, 40);
            precedent.setBackground(new Color(0, 0, 0));
            precedent.setForeground(new Color(255, 255, 255));
            precedent.addActionListener(this);
            this.add(precedent);
        }
        else if ((niveauActuel - niveauActuel % 10) + 10 < niveauMax) {
            suivant = new JButton("Suivant");
            suivant.setBounds(875, 750, 100, 40);
            suivant.setBackground(new Color(0, 0, 0));
            suivant.setForeground(new Color(255, 255, 255));
            suivant.addActionListener(this);
            this.add(suivant);
        } else {
            max = Math.abs((niveauActuel - niveauActuel % 10) - niveauMax);
            etiquettes = new JLabel[max];
        }
        int[][] coords = new int[][]{{450, 10}, {265, 55}, {445, 115}, {335, 190}, {265, 285}, {545, 325}, {665, 465}, {350, 485}, {130, 555}, {295, 670}};
        for (int i = 0; i < max; i++) {
            boutons.add(new Niveau((niveauActuel - niveauActuel % 10) + i, coords[i][0], coords[i][1]));
            boutons.get(i).addActionListener(this);
            this.add(boutons.get(i));
        }
    }

    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        fenetre.general.add(partie, "Partie");

        if (source == precedent) {
            JPanel menuBis = new Menu(fenetre, Niveau = Niveau - 10, 100);
            fenetre.general.add(menuBis, "MenuBis");
            fenetre.cl.show(fenetre.general, "MenuBis");
        } else if (source == suivant) {
            JPanel menuBis = new Menu(fenetre, Niveau = Niveau + 10, 100);
            fenetre.general.add(menuBis, "MenuBis");
            fenetre.cl.show(fenetre.general, "MenuBis");
        } else {
            Niveau = (Niveau - Niveau % 10) + boutons.indexOf(source);
            fenetre.remove(partie);
            modele.jeu.Niveau.Grille g = modele.jeu.Jeu.plateau.getNiveaux().get(Niveau).getGrille();
            partie = new vue.graphique.Partie(fenetre, g);
            fenetre.general.add(partie, "Partie");
            fenetre.cl.show(fenetre.general, "Partie");
            fenetre.validate();
        }
    }
}

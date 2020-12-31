package vue.graphique;

import modele.jeu.Niveau;
import modele.jeu.grille.Grille;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

public class Menu extends vue.graphique.ImagePanel implements ActionListener {
    JeuGraphique fenetre;
    JLabel[] etiquettes = new JLabel[10];
    JButton precedent, suivant;
    ArrayList<JButton> boutons = new ArrayList<>();
    Grille g = modele.jeu.Jeu.plateau.getNiveaux().get(0).getGrille();
    JPanel jeu = new Jeu(fenetre, g);
    int Niveau;

    public Menu(JeuGraphique f, int niveauActuel, int niveauMax) {
        super(new ImageIcon("fond.jpg").getImage());
        this.fenetre = f;
        this.Niveau = niveauActuel;
        this.setLayout(null);
        creationBoutons(niveauActuel,niveauMax);
        this.setVisible(true);
    }

    class Niveau extends JButton {
        public int index = 0;
        int niveau;
        Niveau(int i, int x, int y) {
            niveau = i + 1;
            etiquettes[index] = new JLabel("NIVEAU " + niveau);
            if (i % 10 == 4) {
                etiquettes[index].setForeground(new Color(50, 13, 62));
            } else {
                etiquettes[index].setForeground(new Color(243, 202, 32));
            }
            this.setBounds(x, y, 100, 40);
            this.setBackground(new Color(217, 2, 238, 200));
            this.add(etiquettes[index]);
            index++;
        }
    }

    void creationBoutons(int niveauActuel, int niveauMax) {
        int max = 10;
        if((niveauActuel-niveauActuel%10) != 0) {
            precedent = new JButton("Précedent");
            precedent.setBounds(10,750,100,40);
            precedent.setBackground(new Color(0, 0, 0));
            precedent.setForeground(new Color(255,255,255));
            precedent.addActionListener(this);
            this.add(precedent);
        }
        if((niveauActuel-niveauActuel%10)+10<niveauMax) {
            suivant = new JButton("Suivant");
            suivant.setBounds(875,750,100,40);
            suivant.setBackground(new Color(0, 0, 0));
            suivant.setForeground(new Color(255,255,255));
            suivant.addActionListener(this);
            this.add(suivant);
        } else {
            max = Math.abs((niveauActuel-niveauActuel%10)-niveauMax);
        }
        for(int i = 0; i < max; i++) {
            if(i == 0) {
                boutons.add(new Niveau((niveauActuel-niveauActuel%10), 450, 10));
            }
            if(i == 1) {
                boutons.add(new Niveau((niveauActuel-niveauActuel%10) + 1, 265, 55));
            }
            if(i == 2) {
                boutons.add(new Niveau((niveauActuel-niveauActuel%10) + 2, 445, 115));
            }
            if(i == 3) {
                boutons.add(new Niveau((niveauActuel-niveauActuel%10) + 3, 335, 190));
            }
            if(i == 4) {
                boutons.add(new Niveau((niveauActuel-niveauActuel%10) + 4, 265, 285));
            }
            if(i == 5) {
                boutons.add(new Niveau((niveauActuel-niveauActuel%10) + 5, 545, 325));
            }
            if(i == 6) {
                boutons.add(new Niveau((niveauActuel-niveauActuel%10) + 6, 665, 465));
            }
            if(i == 7) {
                boutons.add(new Niveau((niveauActuel-niveauActuel%10) + 7, 350, 485));
            }
            if(i == 8) {
                boutons.add(new Niveau((niveauActuel-niveauActuel%10) + 8, 130, 555));
            }
            if(i == 9) {
                boutons.add(new Niveau((niveauActuel-niveauActuel%10) + 9, 295, 670));
            }
            boutons.get(i).addActionListener(this);
            this.add(boutons.get(i));
        }
    }

    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        fenetre.general.add(jeu, "Jeu");

        if (source == precedent) {
            JPanel menuBis = new Menu(fenetre, Niveau = Niveau-10, 100);
            fenetre.general.add(menuBis, "MenuBis");
            fenetre.cl.show(fenetre.general, "MenuBis");
        }
        else if (source == suivant) {
            JPanel menuBis = new Menu(fenetre, Niveau = Niveau+10, 100);
            fenetre.general.add(menuBis, "MenuBis");
            fenetre.cl.show(fenetre.general, "MenuBis");
        } else {
            if(source == boutons.get(0)) {
                Niveau = (Niveau-Niveau%10);
            }
            else if(source == boutons.get(1)) {
                Niveau = (Niveau-Niveau%10)+1;
            }
            else if(source == boutons.get(2)) {
                Niveau = (Niveau-Niveau%10)+2;
            }
            else if(source == boutons.get(3)) {
                Niveau = (Niveau-Niveau%10)+3;
            }
            else if(source == boutons.get(4)) {
                Niveau = (Niveau-Niveau%10)+4;
            }
            else if(source == boutons.get(5)) {
                Niveau = (Niveau-Niveau%10)+5;
            }
            else if(source == boutons.get(6)) {
                Niveau = (Niveau-Niveau%10)+6;
            }
            else if(source == boutons.get(7)) {
                Niveau = (Niveau-Niveau%10)+7;
            }
            else if(source == boutons.get(8)) {
                Niveau = (Niveau-Niveau%10)+8;
            }
            else if(source == boutons.get(9)) {
                Niveau = (Niveau-Niveau%10)+9;
            }
            fenetre.remove(jeu);
            Grille g = modele.jeu.Jeu.plateau.getNiveaux().get(Niveau).getGrille();
            jeu = new Jeu(fenetre, g);
            fenetre.general.add(jeu, "Jeu");
            fenetre.cl.show(fenetre.general, "Jeu");
            fenetre.validate();
        }
    }
}

package vue.graphique;

import modele.jeu.Niveau;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Menu extends vue.graphique.ImagePanel implements ActionListener {
    JeuGraphique fenetre;
    JLabel[] etiquettes = new JLabel[10];
    JButton bouton1, bouton2, bouton3, bouton4, bouton5, bouton6, bouton7, bouton8, bouton9, bouton10, suivant, precedent;

    int compterNiveaux() {
        File repertoire = new File("niveaux");
        File[] f = repertoire.listFiles();
        int x = 0;
        for (int i = 0 ; i < f.length ; i++) {
            if (f[i].isFile()) {
                x++;
            }
        }
        return x;
    }

    public Menu(JeuGraphique f, int niveauActuel, int niveauMax) {
        super(new ImageIcon("fond.jpg").getImage());
        this.fenetre = f;
        this.setLayout(null);
        creationBoutons(niveauActuel,niveauMax);
        this.setVisible(true);
    }

    class Niveau extends JButton {
        public int index = 0;
        Niveau(int i, int x, int y) {
            int niveau = i + 1;
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
        for(int i = 0; i <= max; i++) {
            if(i == 1) {
                bouton1 = new Niveau((niveauActuel - niveauActuel % 10), 450, 10);
                bouton1.addActionListener(this);
                this.add(bouton1);
            }
            if(i == 2) {
                bouton2 = new Niveau((niveauActuel - niveauActuel % 10) + 1, 265, 55);
                bouton2.addActionListener(this);
                this.add(bouton2);
            }
            if(i == 3) {
                bouton3 = new Niveau((niveauActuel - niveauActuel % 10) + 2, 445, 115);
                bouton3.addActionListener(this);
                this.add(bouton3);
            }
            if(i == 4) {
                bouton4 = new Niveau((niveauActuel - niveauActuel % 10) + 3, 335, 190);
                bouton4.addActionListener(this);
                this.add(bouton4);
            }
            if(i == 5) {
                bouton5 = new Niveau((niveauActuel - niveauActuel % 10) + 4, 265, 285);
                bouton5.addActionListener(this);
                this.add(bouton5);
            }
            if(i == 6) {
                bouton6 = new Niveau((niveauActuel - niveauActuel % 10) + 5, 545, 325);
                bouton6.addActionListener(this);
                this.add(bouton6);
            }
            if(i == 7) {
                bouton7 = new Niveau((niveauActuel - niveauActuel % 10) + 6, 665, 465);
                bouton7.addActionListener(this);
                this.add(bouton7);
            }
            if(i == 8) {
                bouton8 = new Niveau((niveauActuel - niveauActuel % 10) + 7, 350, 485);
                bouton8.addActionListener(this);
                this.add(bouton8);
            }
            if(i == 9) {
                bouton9 = new Niveau((niveauActuel - niveauActuel % 10) + 8, 130, 555);
                bouton9.addActionListener(this);
                this.add(bouton9);
            }
            if(i == 10) {
                bouton10 = new Niveau((niveauActuel - niveauActuel % 10) + 9, 295, 670);
                bouton10.addActionListener(this);
                this.add(bouton10);
            }
        }
    }

    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if(source == precedent){
            JPanel menuBis = new Menu(fenetre, 1,100);
            fenetre.general.add(menuBis, "MenuBis");
            fenetre.cl.show(fenetre.general, "MenuBis");
        }
        if(source == suivant){
            JPanel menuBis = new Menu(fenetre, 21,100);
            fenetre.general.add(menuBis, "MenuBis");
            fenetre.cl.show(fenetre.general, "MenuBis");
        }
        if(source == bouton1) {
            fenetre.cl.show(fenetre.general, "Jeu");
        }
    }
}

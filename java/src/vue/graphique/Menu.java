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
    JLabel[] etiquettes;
    JButton precedent, suivant, reglements;
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
            if(Jeu.plateau.getNiveaux().get(i).canPlay()) {
                etiquettes[index].setForeground(new Color(0, 0, 0));
                this.setBackground(new Color(243, 202, 32));
                if(Jeu.plateau.getNiveaux().get(i).getGrille().isGagne()) {
                    this.setToolTipText("Score : " + Jeu.joueur.getScore());
                }
            } else {
                etiquettes[index].setForeground(new Color(243, 202, 32));
                this.setBackground(new Color(0, 0, 0));
            }
            this.setBounds(x, y, 100, 40);
            this.setText("" + i);
            this.add(etiquettes[index]);
            index++;
        }
    }

    void creationBoutons(int niveauActuel, int niveauMax) {
        this.removeAll();
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
            boutons.get(i).setBorderPainted(false);
            this.add(boutons.get(i), "i");
        }
        reglements = new JButton("Réglement");
        reglements.setBounds(875, 20, 100, 40);
        reglements.setBackground(new Color(217, 2, 238));
        reglements.setForeground(new Color(0, 0, 0));
        reglements.addActionListener(this);
        this.add(reglements);
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
        } else if (source == reglements) {
            JOptionPane.showMessageDialog(fenetre,
                    "<html>But du jeu : </br>" +
                    "    - Dans chaques niveau, sauvez tous les animaux.<br/>" +
                    "    - Cassez les blocs pour les faire descendre jusqu'en bas.<br/>" +
                    "    - Utilisez vos bonus lorsque vous êtes coincés.<br/>" +
                    "<br>" +
                    "Comment jouer : <br/>" +
                    "    - Cliquez ou entrez la position du bloc que vous souhaitez casser, ses blocs adjacents de même</html>",
                    "Réglement",
                    JOptionPane.QUESTION_MESSAGE);
        }
        else {
            if(Jeu.plateau.getNiveaux().get(Integer.parseInt(((JButton)e.getSource()).getText())).canPlay()) {
                Niveau = (Niveau - Niveau % 10) + boutons.indexOf(source);
                fenetre.remove(partie);
                partie = new vue.graphique.Partie(fenetre, modele.jeu.Jeu.plateau.getNiveaux().get(Niveau).getGrille());
                fenetre.general.add(partie, "Partie");
                fenetre.cl.show(fenetre.general, "Partie");
                fenetre.validate();
            } else {
                JOptionPane.showMessageDialog(fenetre,
                        "Désolé, vous ne pouvez pas encore accéder à ce niveau.",
                        "Niveau inaccessible",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

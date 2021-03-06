package vue.graphique;

import modele.jeu.Jeu;
import modele.jeu.grille.Grille;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Menu extends vue.graphique.ImagePanel implements ActionListener {
    private Fenetre fenetre;
    private JLabel[] etiquettes = new JLabel[10];
    private JButton precedent, suivant, reglements;
    private ArrayList<JButton> boutons = new ArrayList<>();
    private Grille g = modele.jeu.Jeu.plateau.getNiveaux().get(Jeu.plateau.getIndexNiveauActuel()).getGrille();
    private JPanel partie = new vue.graphique.Partie(fenetre, g, Jeu.plateau.getIndexNiveauActuel());
    private int Niveau;
    boolean LastLevel = false;

    public Menu(Fenetre f, int niveauActuel, int niveauMax) {
        super(new ImageIcon("ressources/images/fond.jpg").getImage());
        this.fenetre = f;
        this.Niveau = niveauActuel;
        this.setLayout(null);
        creationBoutons(niveauActuel, niveauMax);
        this.setVisible(true);
    }

    private class Niveau extends JButton {
        public int index = 0;
        int niveau;

        Niveau(int i, int x, int y) {
            niveau = i + 1;
            etiquettes[index] = new JLabel("  NIVEAU " + niveau);
            if(Jeu.plateau.getNiveaux().get(i).canPlay()) {
                etiquettes[index].setForeground(new Color(0, 0, 0));
                this.setBackground(new Color(243, 202, 32));
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

    private void creationBoutons(int niveauActuel, int niveauMax) {
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
        if ((niveauActuel - niveauActuel % 10) + 10 < niveauMax) {
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
            if(LastLevel != true && !Jeu.plateau.getNiveaux().get(i+1).canPlay()) {
                Icon remember = new ImageIcon("ressources/images/joueur.png");
                JLabel joueur = new JLabel();
                joueur.setIcon(remember);
                joueur.setOpaque(false);
                joueur.setBounds(coords[i][0]-75,coords[i][1]-75,200,200);
                this.add(joueur);
                LastLevel = true;
            }
        }
        reglements = new JButton("Règles du jeu");
        reglements.setBounds(825, 20, 120, 40);
        reglements.setBackground(new Color(217, 2, 238));
        reglements.setForeground(new Color(0, 0, 0));
        reglements.addActionListener(this);
        this.add(reglements);
    }

    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        fenetre.general.add(partie, "Partie");

        if (source == precedent) {
            JPanel menuBis = new Menu(fenetre, Niveau = Niveau - 10, Jeu.plateau.getNiveaux().size());
            fenetre.general.add(menuBis, "MenuBis");
            fenetre.cl.show(fenetre.general, "MenuBis");
        } else if (source == suivant) {
            JPanel menuBis = new Menu(fenetre, Niveau = Niveau + 10, Jeu.plateau.getNiveaux().size());
            fenetre.general.add(menuBis, "MenuBis");
            fenetre.cl.show(fenetre.general, "MenuBis");
        } else if (source == reglements) {
            JOptionPane.showMessageDialog(fenetre,
                    "<html>But du jeu : <br/>" +
                    "    - Dans chaque niveau, sauvez tous les animaux.<br/>" +
                    "    - Cassez les blocs pour les faire descendre jusqu'en bas.<br/>" +
                    "    - Utilisez vos bonus lorsque vous êtes coincés.<br/>" +
                    "<br>" +
                    "Comment jouer : <br/>" +
                    "    - Cliquez sur la position du bloc que vous souhaitez casser, les blocs adjacents de même</html>",
                    "Regles du jeu",
                    JOptionPane.QUESTION_MESSAGE);
        }
        else {
            if(Jeu.plateau.getNiveaux().get(Integer.parseInt(((JButton)e.getSource()).getText())).canPlay()) {
                Niveau = (Niveau - Niveau % 10) + boutons.indexOf(source);
                fenetre.remove(partie);
                partie = new vue.graphique.Partie(fenetre, modele.jeu.Jeu.plateau.getNiveaux().get(Niveau).getGrille(), Niveau);
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

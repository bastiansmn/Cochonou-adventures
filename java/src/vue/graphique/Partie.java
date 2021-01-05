package vue.graphique;

import modele.jeu.Jeu;
import modele.jeu.Niveau;
import modele.jeu.animaux.*;
import modele.jeu.grille.blocs.BlocBombe;
import modele.jeu.grille.blocs.BlocCouleur;
import modele.jeu.grille.blocs.BlocObstacle;
import modele.jeu.grille.*;

import javax.sound.midi.SysexMessage;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Partie extends vue.graphique.ImagePanel implements ActionListener {
    Fenetre fenetre;
    modele.jeu.Niveau.Grille grille;
    JButton[] boutons;
    JButton continuer, recommencer, informations;
    int index = 0;
    GridBagLayout gl = new GridBagLayout();

    public Partie(Fenetre f, Niveau.Grille g) {
        super(new ImageIcon("niveaux.jpg").getImage());
        this.fenetre = f;
        this.grille = g;
        this.setLayout(gl);
        this.setVisible(true);
        boutons = new JButton[g.getLongueur() * g.getLargeur()];
        Afficher(g);
    }

    public void Afficher(modele.jeu.Niveau.Grille g) {
        informations = new JButton("Informations niveau");
        informations.setToolTipText("<html>Score : " + Jeu.joueur.getScore() + "<br/>" +
                                    "Animaux sauvés : " + g.getAnimauxSauvee() + "/" + g.getAnimauxRestants() + "<br/>" +
                                    "Nombre de vie : " + Jeu.joueur.getVie() + "</html>");
        this.add(informations);
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
                        } else if (g.getCases()[i][j].getContent() instanceof Oiseau) {
                            boutons[index].setIcon(new ImageIcon("case-oiseau.jpg"));
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
                    } else if (g.getCases()[i][j].getContent() == null) {
                        boutons[index].setIcon(new ImageIcon("case-vide.jpg"));
                    }
                    boutons[index].addActionListener(this);
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        GridBagConstraints updateGrille = gl.getConstraints((Component) source);
        updateGrille.fill = GridBagConstraints.HORIZONTAL;
        this.removeAll();
        grille.actionOuvertureGrille(updateGrille.gridy, updateGrille.gridx);
        if (source == continuer) {
            fenetre.menu.removeAll();
            fenetre.remove(fenetre.menu);
            fenetre.menu = new Menu(fenetre, Jeu.plateau.getIndexNiveauActuel(), Jeu.plateau.getNiveaux().size());
            fenetre.general.add(fenetre.menu, "Menu");
            fenetre.cl.show(fenetre.general, "Menu");
            fenetre.validate();
        } else if (source == recommencer) {
                this.removeAll();
                fenetre.remove(this);
                JPanel partie = new vue.graphique.Partie(fenetre, Jeu.plateau.getNiveaux().get(Jeu.plateau.getIndexNiveauActuel()).getGrille());
                fenetre.general.add(partie, "Partie");
                fenetre.cl.show(fenetre.general, "Partie");
                fenetre.validate();
            }
        else {
            continuer = new JButton("Menu principal");
            continuer.setBackground(new Color(0, 0, 0));
            continuer.setForeground(new Color(255,255,255));
            continuer.addActionListener(this);
            JLabel etat = new JLabel();
            if (grille.isGagne()) {
                this.setLayout(null);
                Icon img = new ImageIcon("gagne.png");
                etat.setIcon(img);
                etat.setBounds(175, -175, 1000, 1000);
                this.add(etat);
                continuer.setBounds(400,500,150,40);
                updateGrille.gridx = 10;
                updateGrille.gridy = 50;
                this.add(continuer, updateGrille);
                updateGrille.gridx = 30;
                updateGrille.gridy = 300;
                this.repaint();
                this.validate();
            } else if (grille.isPerdu()) {
                this.setLayout(null);
                Icon img = new ImageIcon("perte.png");
                etat.setIcon(img);
                etat.setBounds(200, -175, 1000, 1000);
                this.add(etat);
                continuer.setBounds(325,500,150,40);
                updateGrille.gridx = 10;
                updateGrille.gridy = 50;
                this.add(continuer, updateGrille);
                updateGrille.gridx = 30;
                updateGrille.gridy = 300;
                recommencer = new JButton("Recommencer");
                recommencer.setBounds(550, 500, 150, 40);
                recommencer.setBackground(new Color(0, 0, 0));
                recommencer.setForeground(new Color(255, 255, 255));
                recommencer.addActionListener(this);
                this.add(recommencer, updateGrille);
                this.repaint();
                this.validate();
            } else {
                this.repaint();
                this.Afficher(grille);
                this.validate();
            }
        }
    }
}

package vue.graphique;

import modele.jeu.Jeu;
import modele.jeu.animaux.*;
import modele.jeu.grille.blocs.BlocBombe;
import modele.jeu.grille.blocs.BlocCouleur;
import modele.jeu.grille.blocs.BlocObstacle;
import modele.jeu.grille.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Partie extends vue.graphique.ImagePanel implements ActionListener {
    private Fenetre fenetre;
    private Grille grille;
    private JButton[] boutons;
    private JButton continuer, niveauSuivant, recommencer;
    private int index = 0;
    private GridBagLayout gl = new GridBagLayout();
    private Bonus[] bonus = new Bonus[5];
    private Bonus bonuSelected;
    private int niveau;

    public Partie(Fenetre f, Grille g, int n) {
        super(new ImageIcon("ressources/images/niveaux.jpg").getImage());
        this.fenetre = f;
        this.grille = g;
        this.niveau = n;
        this.setLayout(gl);
        this.setVisible(true);
        boutons = new JButton[(g.getLongueur() + 1) * (g.getLargeur() + 1)];
        Afficher(g);
    }

    private class Bonus extends JButton {
        modele.jeu.bonus.Bonus b;

        public Bonus(modele.jeu.bonus.Bonus bonus) {
            this.setIcon(new ImageIcon("ressources/images/case-" + bonus.getInit() + ".png"));
            this.b = bonus;
        }
    }

    private void initBonus(GridBagConstraints n) {
        n.gridx = 1;
        n.gridy = 9;
        for (int i = 0; i < Jeu.joueur.bonus.length; i++) {
            bonus[i] = new Bonus(Jeu.joueur.bonus[i]);
            this.add(bonus[i], n);
            n.gridx++;
            bonus[i].setOpaque(false);
            bonus[i].setContentAreaFilled(false);
            bonus[i].setBorderPainted(false);
            bonus[i].setToolTipText("Bonus restants : " + bonus[i].b.getNombreRestant());
            bonus[i].addActionListener(this);
        }
    }

    private void Afficher(Grille g) {
        GridBagConstraints niveau = new GridBagConstraints();
        niveau.fill = GridBagConstraints.HORIZONTAL;
        JButton score = new JButton("Tableau des scores");
        score.setToolTipText("<html>Score : " + g.getScore() + "<br/>" +
                "Animaux sauvés : " + g.getAnimauxSauvee() + "/" + g.getAnimauxRestants() + "<br/>" +
                "Coups restants : " + g.getNombreLimiteDeCoup() + " ".repeat(Math.abs(Integer.toString(g.getNombreLimiteDeCoup()).length() - 2)) + " <br/>" +
                "Vie restante : " + Jeu.joueur.getVie() + "</html>");
        niveau.gridx = 8;
        niveau.gridy = 0;
        this.add(score, niveau);
        score.setForeground(new Color(0, 0, 0));
        score.setBackground(new Color(243, 202, 32));
        initBonus(niveau);
        for (int i = g.getLongueur() - 7; i < g.getLongueur(); i++) {
            for (int j = 0; j < 7; j++) {
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
                            boutons[index].setIcon(new ImageIcon("ressources/images/case-chat.jpg"));
                        } else if (g.getCases()[i][j].getContent() instanceof Chien) {
                            boutons[index].setIcon(new ImageIcon("ressources/images/case-chien.jpg"));
                        } else if (g.getCases()[i][j].getContent() instanceof Cochon) {
                            boutons[index].setIcon(new ImageIcon("ressources/images/case-cochon.jpg"));
                        } else if (g.getCases()[i][j].getContent() instanceof Panda) {
                            boutons[index].setIcon(new ImageIcon("ressources/ressources/images/case-panda.jpg"));
                        } else if (g.getCases()[i][j].getContent() instanceof Oiseau) {
                            boutons[index].setIcon(new ImageIcon("ressources/ressources/images/case-oiseau.jpg"));
                        }
                    } else if (g.getCases()[i][j].getContent() instanceof BlocCouleur) {
                        if (((BlocCouleur) g.getCases()[i][j].getContent()).getColor().equals(Color.GREEN)) {
                            boutons[index].setIcon(new ImageIcon("ressources/images/case-verte.jpg"));
                        } else if (((BlocCouleur) g.getCases()[i][j].getContent()).getColor().equals(Color.RED)) {
                            boutons[index].setIcon(new ImageIcon("ressources/images/case-rouge.jpg"));
                        } else if (((BlocCouleur) g.getCases()[i][j].getContent()).getColor().equals(Color.YELLOW)) {
                            boutons[index].setIcon(new ImageIcon("ressources/images/case-jaune.jpg"));
                        } else if (((BlocCouleur) g.getCases()[i][j].getContent()).getColor().equals(Color.BLUE)) {
                            boutons[index].setIcon(new ImageIcon("ressources/images/case-bleu.jpg"));
                        } else if (((BlocCouleur) g.getCases()[i][j].getContent()).getColor().equals(Color.PINK)) {
                            boutons[index].setIcon(new ImageIcon("ressources/images/case-violet.jpg"));
                        }
                    } else if (g.getCases()[i][j].getContent() instanceof BlocObstacle) {
                        boutons[index].setIcon(new ImageIcon("ressources/images/case-obstacle.jpg"));
                    } else if (g.getCases()[i][j].getContent() instanceof BlocBombe) {
                        boutons[index].setIcon(new ImageIcon("ressources/images/case-bombe.jpg"));
                    } else if (g.getCases()[i][j].getContent() == null) {
                        boutons[index].setIcon(new ImageIcon("ressources/images/case-vide.jpg"));
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
        if (bonuSelected != null) {
            if (bonuSelected.b.getInit().equals("fw")) {
                if (updateGrille.gridx <= 8 && bonuSelected.b.getNombreRestant() > 0) {
                    grille.actionBonus(bonuSelected.b.getInit(), 8, updateGrille.gridx);
                } else {
                    JOptionPane.showMessageDialog(fenetre,
                            "Désolé, vous avez utilisé tous les bonus de ce type bonus qui étaient à votre disposition.",
                            "Aucun bonus de ce type disponible",
                            JOptionPane.ERROR_MESSAGE);
                }
            } else {
                if (updateGrille.gridy <= 8 && updateGrille.gridx <= 8 && bonuSelected.b.getNombreRestant() > 0) {
                    grille.actionBonus(bonuSelected.b.getInit(), updateGrille.gridy, updateGrille.gridx);
                } else {
                    JOptionPane.showMessageDialog(fenetre,
                            "Désolé, vous avez utilisé tous les bonus de ce type bonus qui étaient à votre disposition.",
                            "Aucun bonus de ce type disponible",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
            bonuSelected = null;
        } else {
            grille.actionOuvertureGrille(updateGrille.gridy, updateGrille.gridx);
        }
        if (source == continuer) {
            fenetre.remove(fenetre.menu);
            fenetre.menu = new Menu(fenetre, Jeu.plateau.getIndexNiveauActuel(), Jeu.plateau.getNiveaux().size());
            fenetre.general.add(fenetre.menu, "Menu");
            fenetre.cl.show(fenetre.general, "Menu");
            fenetre.validate();
        } else if (source == niveauSuivant) {
            this.removeAll();
            fenetre.remove(this);
            Grille g = Jeu.plateau.getNiveaux().get(Jeu.plateau.getIndexNiveauActuel()).getGrille();
            JPanel partie = new vue.graphique.Partie(fenetre, g, Jeu.plateau.getIndexNiveauActuel());
            fenetre.general.add(partie, "Partie");
            fenetre.cl.show(fenetre.general, "Partie");
            fenetre.validate();
        } else if (source == recommencer) {
            this.removeAll();
            fenetre.remove(this);
            Grille g = Jeu.plateau.getNiveaux().get(niveau).getGrille();
            JPanel partie = new vue.graphique.Partie(fenetre, g, niveau);
            fenetre.general.add(partie, "Partie");
            fenetre.cl.show(fenetre.general, "Partie");
            fenetre.validate();
        } else {
            if (source == bonus[0]) {
                bonuSelected = bonus[0];
            }
            if (source == bonus[1]) {
                bonuSelected = bonus[1];
            }
            if (source == bonus[2]) {
                bonuSelected = bonus[2];
            }
            if (source == bonus[3]) {
                bonuSelected = bonus[3];
            }
            if (source == bonus[4]) {
                bonuSelected = bonus[4];
            }
        }
        if (grille.isGagne()) {
            this.setLayout(null);
            Icon img = new ImageIcon("images/gagne.png");
            JLabel victoire = new JLabel();
            victoire.setIcon(img);
            victoire.setBounds(200, -175, 1000, 1000);
            this.add(victoire);
            updateGrille.gridx = 10;
            updateGrille.gridy = 50;
            continuer = new JButton("Menu principal");
            continuer.setBounds(300, 500, 150, 40);
            continuer.setBackground(new Color(0, 0, 0));
            continuer.setForeground(new Color(255, 255, 255));
            continuer.addActionListener(this);
            this.add(continuer, updateGrille);
            updateGrille.gridx = 30;
            updateGrille.gridy = 300;
            niveauSuivant = new JButton("Niveau suivant");
            niveauSuivant.setBounds(550, 500, 125, 40);
            niveauSuivant.setBackground(new Color(0, 0, 0));
            niveauSuivant.setForeground(new Color(255, 255, 255));
            niveauSuivant.addActionListener(this);
            this.add(niveauSuivant, updateGrille);
            this.repaint();
            this.validate();
        } else if (grille.isPerdu()) {
            this.setLayout(null);
            Icon img = new ImageIcon("ressources/images/perte.png");
            JLabel perte = new JLabel();
            perte.setIcon(img);
            perte.setBounds(200, -175, 1000, 1000);
            this.add(perte);
            updateGrille.gridx = 10;
            updateGrille.gridy = 50;
            continuer = new JButton("Menu principal");
            continuer.setBounds(300, 500, 150, 40);
            continuer.setBackground(new Color(0, 0, 0));
            continuer.setForeground(new Color(255, 255, 255));
            continuer.addActionListener(this);
            this.add(continuer, updateGrille);
            updateGrille.gridx = 30;
            updateGrille.gridy = 300;
            recommencer = new JButton("Recommencer");
            recommencer.setBounds(550, 500, 125, 40);
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

package vue.graphique;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import modele.jeu.Niveau;
import modele.jeu.Plateau;
import modele.jeu.grille.Grille;
import modele.outils.Joueur;
import modele.outils.erreurs.CSVNotValidException;

public class JeuGraphique extends JFrame {
    private final Joueur joueur;
    private final Plateau plateau;
    CardLayout cl;
    JPanel general = new JPanel();
    JPanel menu = new Menu(this, 96,98);
    JPanel jeu = new Jeu(this);
    JDialog regles;
    JLabel[] etiquettes = new JLabel[100];

    public CardLayout getCl() {
        return cl;
    }

    public JeuGraphique(Joueur j, Plateau p) {
        super();
        this.joueur = j;
        this.plateau = p;
        this.cl = new CardLayout();
        this.setTitle("COCHONOU-ADVENTURES");
        this.setSize(1000, 835);
        this.setIconImage((new ImageIcon("cochonou.jpg")).getImage());
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.general.setLayout(cl);
        this.general.add(this.menu, "Menu");
        this.general.add(this.jeu,"Jeu");
        this.cl.show(this.general, "Menu");
        this.add(this.general);
        this.setVisible(true);
    }

    public static void main(String[] args) throws CSVNotValidException {
        Joueur j = new Joueur("Dylan");
        Plateau p = new Plateau();
        p.ajouterNiveau(new Niveau(1, new Grille().getCSV("niveaux/nv1.csv", ";")));
        new Plateau();
        new JeuGraphique(j, p);
    }
}

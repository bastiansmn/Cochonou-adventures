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

import modele.jeu.Jeu;
import modele.jeu.Niveau;
import modele.jeu.Plateau;
import modele.jeu.grille.Grille;
import modele.outils.Joueur;
import modele.outils.erreurs.CSVNotValidException;

public class JeuGraphique extends JFrame {
    CardLayout cl;
    JPanel general = new JPanel();
    JPanel menu = new Menu(this, 21, 100);
    JDialog regles;

    public JeuGraphique() {
        super();
        this.cl = new CardLayout();
        this.setTitle("COCHONOU-ADVENTURES");
        this.setSize(1000, 835);
        this.setIconImage((new ImageIcon("cochonou.jpg")).getImage());
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.general.setLayout(cl);
        this.general.add(this.menu, "Menu");
        this.cl.show(this.general, "Menu");
        this.add(this.general);
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

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
}

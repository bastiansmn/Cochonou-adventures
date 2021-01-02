package vue.graphique;

import java.awt.*;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import modele.jeu.Jeu;

public class Fenetre extends JFrame {
    CardLayout cl;
    JPanel general = new JPanel();
    JPanel menu = new Menu(this, Jeu.plateau.getIndexNiveauActuel(), Jeu.plateau.getNiveaux().size());
    JDialog regles;

    public Fenetre() {
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
}

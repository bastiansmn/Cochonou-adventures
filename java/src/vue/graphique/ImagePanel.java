package vue.graphique;

import javax.swing.*;
import java.awt.*;

class ImagePanel extends JPanel {

    private Image image;

    public ImagePanel(Image img){
        this.image = img;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(),this);
    }
}

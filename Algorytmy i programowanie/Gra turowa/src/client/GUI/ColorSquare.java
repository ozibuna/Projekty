package client.GUI;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

public class ColorSquare extends JPanel {
    private Color color;

    public ColorSquare(Color color) {
        this.color = color;
        setPreferredSize(new Dimension(10, 10)); // Set size of the color square
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(color);
        g.fillRect(0, 0, getWidth(), getHeight());
    }

}


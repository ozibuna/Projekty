package client.GUI;

import javax.swing.*;
import java.awt.*;

public class BackgroundLabel extends JLabel {
    private Color backgroundColor;

    public BackgroundLabel(String text, int horizontalAlignment) {
        super(text, horizontalAlignment);
        this.backgroundColor = new Color(255, 255, 255, 128); // Default semi-transparent white background
    }

    public void setBackgroundColor(Color color) {
        this.backgroundColor = color;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(backgroundColor);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }
}
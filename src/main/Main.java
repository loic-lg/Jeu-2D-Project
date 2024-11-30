package main;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();

        ScreenConfig.initialize(screenWidth, screenHeight); // pour stocker les dimensions ecran

        JFrame frame = new JFrame("Survival Game");

        //  couches
        JLayeredPane layeredPane = new JLayeredPane();
        frame.setContentPane(layeredPane);

        // jeu
        GamePanel gamePanel = new GamePanel();
        gamePanel.setBounds(0, 0, screenWidth, screenHeight);
        gamePanel.setGameSpeed(false);
        System.out.println("Jeu démarré en mode slow");
        layeredPane.add(gamePanel, Integer.valueOf(0));

        //  menu
        MenuPanel menuPanel = new MenuPanel(gamePanel);
        menuPanel.setBounds(0, 0, screenWidth, screenHeight);
        menuPanel.setVisible(true);
        layeredPane.add(menuPanel, Integer.valueOf(1));

        frame.setSize(screenWidth, screenHeight);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

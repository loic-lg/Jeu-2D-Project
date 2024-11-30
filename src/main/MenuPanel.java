package main;

import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel {

    private GamePanel gamePanel;
    private JTextArea messageLabel; // Utilisation de JTextArea pour le retour à la ligne
    private JButton startButton;
    private String[] messageWords;
    private int currentWordIndex;
    private JLabel fixedMessageLabel;

    public MenuPanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        setLayout(new GridBagLayout()); // Centrer les composants
        setOpaque(false);

        fixedMessageLabel = new JLabel("Projet Jeu Java 2D réalisé par :");
        fixedMessageLabel.setFont(new Font("Arial", Font.BOLD, 50));
        fixedMessageLabel.setForeground(Color.BLACK);

        // Initialiser le messageLabel avec JTextArea pour gérer les retours à la ligne
        messageLabel = new JTextArea();
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 60)); // Taille réduite pour afficher plus de texte
        messageLabel.setForeground(Color.BLACK);
        messageLabel.setOpaque(false); // Rendre le fond transparent
        messageLabel.setEditable(false); // Empêcher l'édition
        messageLabel.setWrapStyleWord(true); // Retour à la ligne par mot
        messageLabel.setLineWrap(true); // Activer le retour à la ligne
        messageLabel.setPreferredSize(new Dimension(550, 800)); // Largeur et hauteur ajustées pour gérer le texte

        // Initialiser le bouton Start Game
        startButton = new JButton("Start Game");
        startButton.setFont(new Font("Arial", Font.BOLD, 24));
        startButton.setPreferredSize(new Dimension(200, 50));
        startButton.setBackground(new Color(0, 0, 0, 219));
        startButton.setForeground(Color.GRAY);
        startButton.setFocusPainted(false);
        startButton.setBorder(BorderFactory.createLineBorder(Color.white, 1));
        startButton.setVisible(false); // Cacher le bouton au départ
        startButton.addActionListener(e -> startGame());

        // Configurer le layout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(150, 10, 40, 10);
        gbc.anchor = GridBagConstraints.NORTH;

        // Ajouter le label du message
        add(messageLabel, gbc);

        gbc.insets = new Insets(10, 10, 100, 10);
        gbc.anchor = GridBagConstraints.NORTH;
        add(fixedMessageLabel, gbc);

        // Ajouter le bouton Start Game
        gbc.gridheight = 200; // Placer dans une nouvelle ligne
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.CENTER; // Centrer le bouton
        gbc.insets = new Insets(0, 10, 0, 10);gbc.weighty = 0; // Désactiver l'extension verticale
        gbc.fill = GridBagConstraints.NONE;

        add(startButton, gbc);
        messageLabel.setFocusable(false);

        // Texte à afficher progressivement
        messageWords ="Thomas Blachowiak Dorian Bonneau    Le Gal Loïc".split(" ");
        currentWordIndex = 0;

        // Démarrer l'animation de texte
        startMessageAnimation();
    }

    private void startMessageAnimation() {
        Timer timer = new Timer(250, e -> {
            if (currentWordIndex < messageWords.length) {
                // Ajouter le mot suivant
                String currentText = messageLabel.getText();
                messageLabel.setText(currentText + (currentText.isEmpty() ? "" : " ") + messageWords[currentWordIndex]);
                currentWordIndex++;
            } else {
                // Tous les mots ont été affichés
                ((Timer) e.getSource()).stop();
                startButton.setVisible(true); // Afficher le bouton Start Game
                setComponentZOrder(startButton, 0);
            }
        });
        timer.start();
    }

    private void startGame() {
        System.out.println("Bouton Start cliqué");
        System.out.println("Vous jouez avec les dimensions : " + ScreenConfig.screenWidth + "x" + ScreenConfig.screenHeight);

        // Masquer le menu
        this.setVisible(false);

        System.out.println("Passage en mode Normal");
        gamePanel.setGameSpeed(true); // Activer le mode normal de vitesse de jeu

        gamePanel.startGame();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Couleur du menu
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(new Color(83, 83, 83, 131));
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }
}

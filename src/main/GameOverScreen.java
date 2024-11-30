package main;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.*;


public class GameOverScreen {
    private String message;
    private String statsMessage;
    private final String scoreFile = "src/main/scores.txt";

    public GameOverScreen(String message, String statsMessage) {
        this.message = message;
        this.statsMessage = statsMessage;

    }

    public void end(Graphics g, int screenWidth, int screenHeight) {
        Graphics2D g2d = (Graphics2D) g;


        Font gameOverFont = new Font("Impact", Font.BOLD, 200);
        Font statsFont = new Font("Arial", Font.PLAIN, 96);
        Font scoresFont = new Font("Comic Sans MS", Font.PLAIN, 38);


        g2d.setFont(gameOverFont);
        g2d.setColor(Color.DARK_GRAY);

        FontMetrics gameOverMetrics = g2d.getFontMetrics(gameOverFont);
        int gameOverTextWidth = gameOverMetrics.stringWidth(message);
        int gameOverTextHeight = gameOverMetrics.getHeight();
        int xGameOver = (screenWidth - gameOverTextWidth) / 2;
        int yGameOver = (screenHeight / 2 ) - gameOverTextHeight;

        int shadowOffset = 5;

        g2d.drawString(message, xGameOver+ shadowOffset, yGameOver + shadowOffset);

        g2d.setColor(Color.RED);
        g2d.drawString(message, xGameOver, yGameOver);

        g2d.setFont(statsFont);
        g2d.setColor(Color.black);

        FontMetrics statsMetrics = g2d.getFontMetrics(statsFont);
        int statsTextWidth = statsMetrics.stringWidth(statsMessage);
        int xStats = (screenWidth - statsTextWidth) / 2;
        int yStats = yGameOver + gameOverTextHeight ; // Espace sous "Game Over"

        g2d.drawString(statsMessage, xStats, yStats);

        //gerer l'encadrement des scores
        int scoresBoxX = xStats - 50;
        int scoresBoxY = yStats + 75;
        int scoresBoxWidth = statsTextWidth + 100;
        int scoresBoxHeight = 280;

        g2d.setColor(new Color(255, 255, 255, 50));
        g2d.fillRect(scoresBoxX, scoresBoxY, scoresBoxWidth, scoresBoxHeight);

//        g2d.setColor(Color.BLACK);
//        g2d.drawRect(scoresBoxX, scoresBoxY, scoresBoxWidth, scoresBoxHeight);

        g2d.setFont(scoresFont);
        g2d.setColor(Color.black);
        try (BufferedReader reader = new BufferedReader(new FileReader(scoreFile))) {
            String line;
            int scoreY = yStats + 150; // Position horizointal pour les scores
            int count = 0;

            while ((line = reader.readLine()) != null && count < 3) {
                g2d.drawString(line, xStats, scoreY);
                scoreY += 80; // Espacement vertical entre les scores
                count++;
            }
        } catch (IOException e) {
            g2d.drawString("Aucun score disponible.", xStats, yStats + 150);
        }
    }


    public void saveScore(int level, int monstersKilled) {

        List<int[]> scores = loadScores();



        scores.add(new int[]{level, monstersKilled});


        scores.sort((a, b) -> Integer.compare(b[1], a[1]));

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(scoreFile))) {
            for (int[] score : scores) {
                writer.write("Niveau atteint: " + score[0] + ", Monstres tués: " + score[1]);
                writer.newLine();
            }
            System.out.println("Scores sauvegardés et triés.");
        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde des scores : " + e.getMessage());
        }
    }

    public String readScores() {
        StringBuilder scores = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(scoreFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                scores.append(line).append("\n");
            }
        } catch (IOException e) {
            scores.append("Aucun score sauvegardé.");
        }
        return scores.toString();
    }

    public List<int[]> loadScores() {
        List<int[]> scores = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(scoreFile))) {
            String line;
            while ((line = reader.readLine()) != null) {

                String[] parts = line.split(", ");
                int level = Integer.parseInt(parts[0].replace("Niveau atteint: ", "").trim());
                int monstersKilled = Integer.parseInt(parts[1].replace("Monstres tués: ", "").trim());
                scores.add(new int[]{level, monstersKilled});
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture des scores : " + e.getMessage());
        }
        return scores;
    }
}

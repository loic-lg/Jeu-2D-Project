package item;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class SpeedBoost {
    private int x, y;
    private static final int bonus = 5;
    private static final int duration = 5000; // 5 seconds in milliseconds
    private static final String image = "res/SpeedBoost.png";
    private static Image speedBoostImage;

    public SpeedBoost(int x, int y) {
        this.x = x;
        this.y = y;

        if (speedBoostImage == null) {
            try {
                speedBoostImage = ImageIO.read(new File(image));
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Erreur : Impossible de charger l'image de SpeedBoost.");
            }
        }
    }

    public void draw(Graphics g) {
        if (speedBoostImage != null) {
            g.drawImage(speedBoostImage, x, y, null);
        } else {
            g.setColor(Color.BLUE);
            g.fillRect(x, y, 20, 20);
        }
    }

    public int getSpeedIncrease() {
        return bonus;
    }

    public int getDuration() {
        return duration;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}

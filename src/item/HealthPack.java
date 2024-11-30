package item;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class HealthPack {
    private int x, y;
    private static final int HEAL_AMOUNT = 30;
    private static final String HEALTHPACK_IMAGE = "res/HealthPack.png";
    private static Image healthPackImage;


    public HealthPack(int x, int y) {
        this.x = x;
        this.y = y;


        if (healthPackImage == null) {
            try {
                healthPackImage = ImageIO.read(new File(HEALTHPACK_IMAGE));
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Erreur : Impossible de charger l'image du HealthPack.");
            }
        }
    }


    public void draw(Graphics g) {
        if (healthPackImage != null) {
            g.drawImage(healthPackImage, x, y, null);
        } else {

            g.setColor(Color.GREEN);
            g.fillRect(x, y, 20, 20);
        }
    }


    public int getHealAmount() {
        return HEAL_AMOUNT;
    }


    public int getX() { return x; }
    public int getY() { return y; }
}

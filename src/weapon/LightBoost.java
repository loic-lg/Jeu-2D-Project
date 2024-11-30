package weapon;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class LightBoost extends Weapon{

    private int x, y;
    private static final String lightBoostPath = "res/lightBoost.png";
    public Image lightBoostImage;

    public LightBoost(int x, int y) {
        super(lightBoostPath, x, y, 100, 30);
        this.x = x;
        this.y = y;

        if (lightBoostImage == null) {
            try {
                lightBoostImage = ImageIO.read(new File(lightBoostPath));
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Erreur : Impossible de charger l'image du lightBoost");
            }
        }


    }

    public void draw(Graphics g) {
        super.draw_Element(g, lightBoostImage);
    }

    public int getX() { return x; }
    public int getY() { return y; }
}

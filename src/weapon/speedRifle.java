package weapon;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class speedRifle extends Weapon{

    private int x, y;
    private static final String speedRiflePath = "res/speedRifle.png";
    public Image speedRifleImage;

    public speedRifle(int x, int y) {
        super(speedRiflePath, x, y, 100, 30);
        this.x = x;
        this.y = y;
        if (speedRifleImage == null) {
            try {
                speedRifleImage = ImageIO.read(new File(speedRiflePath));
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Erreur : Impossible de charger l'image du HealthPack.");
            }
        }

    }

    public void draw(Graphics g) {
        super.draw_Element(g, speedRifleImage);
    }

    public int getX() { return x; }
    public int getY() { return y; }
}

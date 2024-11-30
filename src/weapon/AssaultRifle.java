// File: weapon/AssaultRifle.java
package weapon;

import java.awt.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class AssaultRifle extends Weapon {
    private int x, y;
    private static final String assaultRiflePath = "res/assaultRifle.png";
    private static Image assaultRifleImage;

    static {
        try {
            assaultRifleImage = ImageIO.read(new File(assaultRiflePath));
        } catch (IOException e) {
            System.err.println("L'image charge pas :( " + e.getMessage());
        }
    }

    public AssaultRifle(int x, int y) {
        super(assaultRiflePath, x, y, 100, 30);
        this.x = x;
        this.y = y;
    }



        public void draw(Graphics g) {
        super.draw_Element(g, assaultRifleImage);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, 40, 10);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}

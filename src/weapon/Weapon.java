package weapon;

import java.awt.*;


public abstract class Weapon {
    private String imagePath;
    private int fireRate;
    private int range;
    private int duration;
    private Image image;
    private int x, y;


    public Weapon(String imagePath, int x, int y, int range, int duration)
    {
        this.x = x;
        this.y = y;

    }

    public void draw_Element(Graphics g, Image image) {
        if (image != null) {
            g.drawImage(image, x, y, null);
        } else {
            g.setColor(Color.GRAY);
            g.fillRect(x, y, 20, 20);
        }
    }

}

package item;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public abstract class DecorationElement {
    protected int x;
    protected int y;
    private int width;
    private int height;
    boolean collision;
    private String imagePath;
    private Image image;

    public DecorationElement(int x, int y,boolean collision, String imagePath, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.imagePath = imagePath;
        this.collision = collision;
        loadImage();
    }

     private void loadImage() {
        if (imagePath != null && !imagePath.isEmpty()) {
            try {
                image = ImageIO.read(new File(imagePath));
            } catch (IOException e) {
                System.out.println("Error loading image: " + e.getMessage());
                image = null;
            }
        }
    }

    public void draw_Element(Graphics g) {
        if (image != null) {
            g.drawImage(image, x, y, null);
        } else {
            g.setColor(Color.GRAY);
            g.fillRect(x, y, 20, 20);
        }
    }
    public Rectangle getCollisionItems() {
        return new Rectangle(x, y, width, height);
    }

}
package item;

import java.awt.*;

public class Rock extends DecorationElement {

    public Rock(int x, int y) {
        super(x, y, true, "res/Rock.png",64,64);
    }
     @Override
    public void draw_Element(Graphics g) {
        super.draw_Element(g);

        // Dessiner un rectangle rouge autour de la zone de collision
//        Graphics2D g2d = (Graphics2D) g;
//        g2d.setColor(Color.RED);
//        g2d.draw(getCollisionItems());
    }


    @Override
public Rectangle getCollisionItems() {
    int deltaX = 10;
    int deltaY = 15;
    return new Rectangle(x + deltaX, y + deltaY, 50, 35);
}

}

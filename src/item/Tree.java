package item;

import java.awt.*;

public class Tree extends DecorationElement {

    public Tree(int x, int y) {
        super(x, y, true, "res/tree_2.png",50,64);
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
    int deltaX = 67;
    int deltaY = 120;
    return new Rectangle(x + deltaX, y + deltaY, 15, 27);
}

}
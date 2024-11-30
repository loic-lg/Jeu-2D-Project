package item;

import java.awt.*;
import javax.swing.ImageIcon;
import player.Hero;

public class Spike extends DecorationElement {
    private static final String SPIKE_IMAGE_PATH = "res/Spike.png";
    private Image spriteSheet;
    private int frameWidth;
    private int frameHeight = 32;
    private int totalFrames = 14;
    private int currentFrame = 0;
    private boolean heroSteppedOn = false;
     private long lastDamageTime = 0;
    private static final int DAMAGE_INTERVAL = 1000;
    private static final int DAMAGE_AMOUNT = 5;

    public Spike(int x, int y) {
        super(x, y, true, SPIKE_IMAGE_PATH, 32, 32);
        loadSpriteSheet();
        frameWidth = spriteSheet.getWidth(null) / totalFrames;
    }

    private void loadSpriteSheet() {
        ImageIcon icon = new ImageIcon(SPIKE_IMAGE_PATH);
        spriteSheet = icon.getImage();
    }

    public void draw_Element(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        if (heroSteppedOn) {
            animateSpike(g2d);  // gestion anim spike
        } else {
            // Draw only the first frame if not triggered
            g2d.drawImage(spriteSheet, x, y, x + frameWidth, y + frameHeight,
                          0, 0, frameWidth, frameHeight, null);
        }

//        g2d.setColor(Color.RED);
//        g2d.draw(getCollisionItems());
    }

    private void animateSpike(Graphics2D g2d) {
        int srcX1 = currentFrame * frameWidth;
        int srcX2 = srcX1 + frameWidth;

        g2d.drawImage(spriteSheet, x, y, x + frameWidth, y + frameHeight,
                      srcX1, 0, srcX2, frameHeight, null);


        currentFrame++;
        if (currentFrame >= totalFrames) {
            currentFrame = 0;
        }
    }

    public Rectangle getCollisionItems() {
        return new Rectangle(x, y, frameWidth, frameHeight);
    }

    public void setHeroSteppedOn(boolean stepped, Hero hero) {
        heroSteppedOn = stepped;
        if (heroSteppedOn) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastDamageTime >= DAMAGE_INTERVAL) {
                hero.takeDamage(DAMAGE_AMOUNT);
                lastDamageTime = currentTime;
                System.out.println("Ouch!");
            }
        }
    }
}

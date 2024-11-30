package player;

import java.awt.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public abstract class Entity {
    protected int x, y, health,speed;
    protected int maxHealth;
    protected Image image;
    protected boolean invincible = false;

    public Entity(int x, int y, int health, int maxHealth, int speed, String imagePath) {
        this.x = x;
        this.y = y;
        this.health = health;
        this.maxHealth = maxHealth;
        this.speed = speed;


        if (imagePath != null) {
            try {
                this.image = ImageIO.read(new File(imagePath));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void draw(Graphics g) {
        if (image != null) {
            g.drawImage(image, x, y, null);
        }
        else {
            g.setColor(Color.GRAY);
           g.fillRect(x, y, 60, 60);
        }
        drawHealthBar(g);
    }

    protected void drawHealthBar(Graphics g) {
        int barWidth = 50;
        int barHeight = 5;
        int healthWidth = (int) ((health / (double) maxHealth) * barWidth);
        int barX = x + (image != null ? image.getWidth(null) / 2 : 10) - (barWidth / 2);
        int barY = y - barHeight - 5;

        g.setColor(Color.GRAY);
        g.fillRect(barX, barY, barWidth, barHeight);

        if (health > maxHealth * 0.6) {
            g.setColor(Color.GREEN);
        } else if (health > maxHealth * 0.3) {
            g.setColor(Color.YELLOW);
        } else {
            g.setColor(Color.RED);
        }
        g.fillRect(barX, barY, healthWidth, barHeight);
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getHealth() { return health; }

    public void takeDamage(int damage) {
        if (!invincible) {
            health -= damage;
            if (health < 0) health = 0;
        }
    }

    protected void activateInvincibility() {
        invincible = true;
    }

    protected void disableInvincibility() {
        invincible = false;
    }

    public boolean isInvincible() {
        return invincible;
    }

    public boolean isAlive() {
        return health > 0;
    }

    public int getCenterX() {
        return x + (image != null ? image.getWidth(null) / 2 : 10);
    }

    public int getCenterY() {
        return y + (image != null ? image.getHeight(null) / 2 : 10);
    }
}

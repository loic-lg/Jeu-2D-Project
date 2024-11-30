package player;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Set;
import javax.swing.*;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import main.ScreenConfig;

/**
 * The Hero class represents the main player character in the game.
 * It extends the Entity class and implements the Player interface.
 */
public class Hero extends Entity implements Player {

    private boolean isAssaultRifleEquipped = false;
    private boolean isSpeedRifleEquipped = false;
    private boolean isLightBoostEquipped = false;

    private static final int HERO_HEALTH = 100;
    // private static final String HERO_IMAGE_PATH = "res/heros.png";
    private static final int INVINCIBILITY_DURATION = 8000;
    private final Timer invincibilityTimer;
    private long invincibilityStartTime;

    private int previousX;
    private int previousY;
    private Timer speedBoostTimer;
    private boolean isSpeedBoosted = false;
    private ArrayList<Point> trail = new ArrayList<>();

    private Image[] runFrames;
    private int currentFrame = 0;
    private long lastFrameChangeTime = 0;
    private int frameDelay = 100;

    private boolean isMovingLeft = false;

    private Image idleImage;


    //Passe a true lorsqu'on équipe l'arme
    public void equipAssaultRifle() {
        isAssaultRifleEquipped = true;
        System.out.println("Assault Rifle equipped!");
    }
    public void equipSpeedAssaultRifle() {
        isSpeedRifleEquipped = true;
        System.out.println("Assault Rifle equipped!");
    }
    public void unequipSpeedRifle() {
        isSpeedRifleEquipped = false;
        System.out.println("Speed Rifle unequipped!");
    }
    public void unequipAssaultRifle() {
        isAssaultRifleEquipped = false;
        System.out.println("Assault Rifle unequipped!");
    }
    public void equipLightBoost() {
        isLightBoostEquipped = true;
        System.out.println("Assault Rifle equipped!");
    }
     public void unequipLightBoost() {
        isLightBoostEquipped = false;
        System.out.println("Assault Rifle unequipped!");
    }

    public boolean isAssaultRifleEquipped() {
        return isAssaultRifleEquipped;
    }
    public boolean isSpeedRifleEquipped() {
        return isSpeedRifleEquipped;
    }
    public boolean isLightBoostEquipped() {
        return isLightBoostEquipped;
    }


    /**
     * Constructs a new Hero with the specified initial position.
     *
     * @param x the initial x-coordinate of the hero
     * @param y the initial y-coordinate of the hero
     */
    public Hero(int x, int y) {
        super(x, y, HERO_HEALTH, HERO_HEALTH, 10, null);

        invincibilityTimer = new Timer(INVINCIBILITY_DURATION, e -> disableInvincibility());
        invincibilityTimer.setRepeats(false);

        runFrames = new Image[] {
                new ImageIcon("res/Hero/heros-0.png").getImage(),
                new ImageIcon("res/Hero/heros-1.png").getImage(),
                new ImageIcon("res/Hero/heros-2.png").getImage(),
                new ImageIcon("res/Hero/heros-3.png").getImage(),
                new ImageIcon("res/Hero/heros-4.png").getImage(),
                new ImageIcon("res/Hero/heros-5.png").getImage(),
                new ImageIcon("res/Hero/heros-6.png").getImage(),
                new ImageIcon("res/Hero/heros-7.png").getImage(),
        };

        // Load the idle image
        idleImage = new ImageIcon("res/Hero/heros-0.png").getImage();
    }

    /**
     * Draws the hero on the screen.
     *
     * @param g the Graphics object used for drawing
     */
    @Override
    public void draw(Graphics g) {
//        super.draw(g);
//        Graphics2D g2d = (Graphics2D) g;
//        g.setColor(Color.RED);
//        g.drawRect(getCenterX(), getCenterY(), 10, 10);

//        Rectangle collisionHero = getCollisionHero();
//        g.setColor(Color.RED);
//        g.drawRect(collisionHero.x, collisionHero.y, collisionHero.width, collisionHero.height);

        Graphics2D g2d = (Graphics2D) g;

        if (isMovingLeft || currentFrame > 0) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastFrameChangeTime >= frameDelay) {
                currentFrame = (currentFrame + 1) % runFrames.length;
                lastFrameChangeTime = currentTime;
            }

            Image currentImage = runFrames[currentFrame];
            if (isMovingLeft) {
                g2d.drawImage(currentImage, x + currentImage.getWidth(null), y, -currentImage.getWidth(null), currentImage.getHeight(null), null);
            } else {
                g2d.drawImage(currentImage, x, y, null);
            }
        } else {
            g2d.drawImage(idleImage, x, y, null);
        }

        drawHealthBar(g2d);

        if (invincible) {
            int shieldRadius = 50;
            int centerX = getCenterX()+35;
            int centerY = getCenterY();


            g.setColor(new Color(3, 90, 115, 255));
            g.drawOval(centerX - shieldRadius, centerY - shieldRadius, shieldRadius * 2, shieldRadius * 2);
        }
        if (isSpeedBoosted) {
            // Graphics2D g2d = (Graphics2D) g;
            for (int i = 0; i < trail.size()-2; i++) {
                Point p = trail.get(i);
                float alpha = (float) (i + 1) / trail.size(); // test d'un transparance croissante
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
                g2d.setColor(new Color(119, 220, 61));
                g2d.fillOval(p.x , p.y , 30, 20);
            }
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f)); // réinitialiser la transparance
        }
    }

    /**
     * Draws the health bar of the hero.
     *
     * @param g the Graphics object used for drawing
     */
    @Override
    protected void drawHealthBar(Graphics g) {
        int barWidth = 50;
        int barHeight = 5;
        int healthWidth = (int) ((health / (double) maxHealth) * barWidth);

        int currentImageWidth = runFrames[currentFrame].getWidth(null); // Get the width of the current frame
        int barX = x + (currentImageWidth / 2) - (barWidth / 2);
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

    /**
     * Moves the hero based on the keys pressed.
     *
     * @param keysPressed the set of keys currently pressed
     */
    public void move(Set<Integer> keysPressed) {
        previousX = x;
        previousY = y;
        boolean moved = false;

        if (keysPressed.contains(KeyEvent.VK_UP) && y > 0) {
            y -= speed;
            moved = true;
        }
        if (keysPressed.contains(KeyEvent.VK_DOWN) && ScreenConfig.screenHeight > y) { // 140 car il y la barre de recherche en bas sur le pc
            y += speed;
            moved = true;
        }
        if (keysPressed.contains(KeyEvent.VK_LEFT) && x > 0) {
            x -= speed;
            isMovingLeft = true;
            moved = true;
        } else if (!keysPressed.contains(KeyEvent.VK_RIGHT)) { // Si ni droite ni gauche n'est pressée, le héros s'arrête
            isMovingLeft = false;
        }
        if (keysPressed.contains(KeyEvent.VK_RIGHT) && ScreenConfig.screenWidth - 64 > x) {
            x += speed;
            isMovingLeft = false;
            moved = true;
        }

        if (moved) {
            updateFrame();
        }

        if (isSpeedBoosted) {
            Point center = new Point(getCenterX(), getCenterY());
            trail.add(center);

            if (trail.size() > 10) { // taille de la trainé avant suppréssion
                trail.remove(0);
            }
        }
    }

    /**
     * Updates the current frame of the hero's animation.
     */
    private void updateFrame() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastFrameChangeTime >= frameDelay) {
            currentFrame = (currentFrame + 1) % runFrames.length;
            lastFrameChangeTime = currentTime;
        }
    }

    /**
     * Applies a speed boost to the hero for a specified duration.
     *
     * @param boost the amount of speed boost
     * @param duration the duration of the speed boost in milliseconds
     */
    public void applySpeedBoost(int boost, int duration) {
        if (!isSpeedBoosted) {
            isSpeedBoosted = true;
            speed += boost;

            if (speedBoostTimer != null) {
                speedBoostTimer.stop(); // Stopper un ancien Timer si existant
            }

            // Création d'un nouveau Timer pour réinitialiser la vitesse
            speedBoostTimer = new Timer(duration, e -> {
                speed -= boost; // Rétablir la vitesse
                isSpeedBoosted = false;
                speedBoostTimer.stop(); // Arrêter le Timer
            });
            speedBoostTimer.setRepeats(false);
            speedBoostTimer.start();
        }
    }

    /**
     * Stops the hero's movement by resetting its position to the previous coordinates.
     */
    public void stopMovement() {
        x = previousX;
        y = previousY;
    }

    /**
     * Returns the collision rectangle of the hero.
     *
     * @return the collision rectangle of the hero
     */
    public Rectangle getCollisionHero() {
    int deltaX = 10;  // Ajustement horizontal
    int deltaY = 5;  // Ajustement vertical
    int collisionWidth = 37 - deltaX * 2;
    int collisionHeight = 64 - deltaY * 2;

    return new Rectangle(x + deltaX, y + deltaY, collisionWidth, collisionHeight);
}

    /**
     * Heals the hero by a specified amount.
     *
     * @param amount the amount of health to restore
     */
    public void heal(int amount) {
        if (this.health >= HERO_HEALTH) {
            activateInvincibility();
        } else {
            this.health = Math.min(this.health + amount, HERO_HEALTH);
        }
    }

    public void setHealth(int health) {
        this.health = Math.min(health, HERO_HEALTH);
    }

    /**
     * Activates invincibility for the hero.
     */
    @Override
    public void activateInvincibility() {
        super.activateInvincibility();
        invincibilityStartTime = System.currentTimeMillis(); // initialiser le Timer
        invincibilityTimer.restart(); // Démarrer le Timer
    }

    /**
     * Returns the remaining time of invincibility in seconds.
     *
     * @return the remaining time of invincibility in seconds
     */
    public int getInvincibilityTimeRemaining() {
        if (invincible) {
            long elapsed = System.currentTimeMillis() - invincibilityStartTime;
            return Math.max(0, INVINCIBILITY_DURATION - (int) elapsed) / 1000;
        }
        return 0;
    }

    /**
     * Returns the x-coordinate of the hero's center.
     *
     * @return the x-coordinate of the hero's center
     */
    public int getCenterX() {
        return x -15 + (image != null ? image.getWidth(null) / 2 : 10);
    }

    /**
     * Returns the y-coordinate of the hero's center.
     *
     * @return the y-coordinate of the hero's center
     */
    public int getCenterY() {
        return y +10 + (image != null ? image.getHeight(null) / 2 : 10);
    }

    /**
     * Checks if the hero is near a specified monster within a certain distance.
     *
     * @param monster the monster to check proximity to
     * @param distance the distance threshold
     * @return true if the hero is near the monster, false otherwise
     */
    public boolean isNear(Monster monster, int distance) {
        return Math.abs(x - monster.getX()) < distance * 20 && Math.abs(y - monster.getY()) < distance * 20;
    }


}

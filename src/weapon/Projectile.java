package weapon;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;

import player.Monster;
import player.Hero;


import player.Monster;

public class Projectile {
    private int x, y;
    private int dx, dy;


    private int speed = 20;
    private Monster target;
    private BufferedImage projectileImage;
    private double rotationAngle = 0;
    private Hero hero = new Hero(0, 0);
    private BufferedImage rifleProjectileImage;
    private BufferedImage defaultProjectileImage;
    private BufferedImage speedRifleProjectileImage;
    private BufferedImage lightBoostImage;


    public Projectile(int startX, int startY, Monster target, Hero hero, int speed) {
        this.x = startX;
        this.y = startY;
        this.target = target;
        this.hero = hero;
        this.speed = speed;

        int targetX = target.getCenterX();
        int targetY = target.getCenterY();
        double distance = Math.sqrt(Math.pow(targetX - startX, 2) + Math.pow(targetY - startY, 2));


        this.dx = (int) ((targetX - startX) / distance * speed);
        this.dy = (int) ((targetY - startY) / distance * speed);

        try{
            this.defaultProjectileImage = ImageIO.read(new File("res/baseProjectile.png"));
            this.rifleProjectileImage = ImageIO.read(new File("res/secondProjectile.png"));
            this.speedRifleProjectileImage = ImageIO.read(new File("res/speedProjectile.png"));
            this.lightBoostImage = ImageIO.read(new File("res/lightBoostProjectile.png"));

        } catch(IOException e){
            e.printStackTrace();
        }

    }

    public void draw(Graphics g) {
        BufferedImage currentImage;
        if (hero.isSpeedRifleEquipped()) {
            currentImage = speedRifleProjectileImage;
        } else if (hero.isAssaultRifleEquipped()) {
            currentImage = rifleProjectileImage;
        }else if (hero.isLightBoostEquipped()) {
            currentImage = lightBoostImage;
        } else {
            currentImage = defaultProjectileImage;
        }

        if (currentImage != null) {
            Graphics2D g2d = (Graphics2D) g;

            int centerX = x - (currentImage.getWidth() / 2);
            int centerY = y - (currentImage.getHeight() / 2);

            g2d.rotate(Math.toRadians(rotationAngle), x, y);
            g2d.drawImage(currentImage, centerX, centerY, null);
            g2d.rotate(-Math.toRadians(rotationAngle), x, y);

            rotationAngle += 10;
        }
    }

    public void move() {
        x += dx;
        y += dy;
    }

    public boolean hasHitTarget(Monster monster) {
        return Math.abs(x - monster.getCenterX()) < 20 && Math.abs(y - monster.getCenterY()) < 20; // pour gérer la hitbox des monstres
    }

    public Monster getTarget() {
        return target;
    }

    public int getX() { return x; }
    public int getY() { return y; }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
    public void setDx(int dx) {
        this.dx = dx;
    }
    public void setDy(int dy) {
        this.dy = dx;
    }
}

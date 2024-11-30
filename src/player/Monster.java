package player;

import java.awt.*;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import item.*;
import weapon.*;


public class Monster extends Entity implements Player {
    private static final AtomicInteger count = new AtomicInteger(0);
    private final int id; // mise en place pour l'ajout d'une futur arme à tête chercheuse
    protected int speed;
    protected int attackDamage = 20;
    private boolean isAlive = true;
    private static final int MONSTER_HEALTH = 1200;
    private static final String MONSTER_IMAGE_PATH = "res/monster_Fly 64x64.png";

    private long lastUpdateTime =0;
    private long lastTargetUpdateTime =0;
    private int targetX;
    private int targetY;
    protected Random random = new Random();

    public Monster(int x, int y) {
        super(x, y, MONSTER_HEALTH, MONSTER_HEALTH, new Random().nextInt(6) + 3, MONSTER_IMAGE_PATH);
        this.id = count.incrementAndGet();
        Random rand = new Random();
        this.speed = 3 + rand.nextInt(6);
        this.health = MONSTER_HEALTH - 100 * speed;
        this.lastUpdateTime = System.currentTimeMillis();
    }

    public int getId() {
        return id;
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);

        drawHealthBar(g);


//        // carré rouge au centre du monstre vous verifier le ciblage
//        g.setColor(Color.RED);
//        g.drawRect(getCenterX(), getCenterY(), 10, 10);

    }

    @Override
    protected void drawHealthBar(Graphics g) {

        int barWidth = 50;
        int barHeight = 5;
        int healthWidth = (int) ((health / (double) (MONSTER_HEALTH - 100 * speed)) * barWidth);
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

    public void moveTowards(Hero hero) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastTargetUpdateTime >= 500) {
            targetX = hero.getX();
            targetY = hero.getY();
            lastTargetUpdateTime = currentTime;
        }


        if (currentTime - lastUpdateTime >= 40) {
            if (x < targetX) x += speed;
            if (x > targetX) x -= speed;
            if (y < targetY) y += speed;
            if (y > targetY) y -= speed;
        }
    }

    @Override
    public void takeDamage(int damage) {
        if (isAlive) {
            super.takeDamage(damage);
            if (health <= 0) {
                isAlive = false;
                generateItem();
            }
        }
    }

    public Object generateItem() {
        double chance = random.nextDouble();
        if (chance < 0.01) {
            System.out.println("HealthPack generated");
            return new HealthPack(x, y);
        } else if (chance < 0.02) {
            System.out.println("SpeedBoost generated");
            return new SpeedBoost(x, y);
        } else if (chance < 0.07) {
            System.out.println("AssaultRifle generated");
            return new speedRifle(x, y);
        }else if (chance < 0.1) {
            System.out.println("lightBoost generated");
            return new LightBoost(x, y);
        }
        return null;
    }

    @Override
    public boolean isAlive() {
        return isAlive;
    }

//    public void attack(Player target) {
//        if (target != null && isAlive()) {
//            target.takeDamage(attackDamage);
//        }
//    }

    @Override
    public int getCenterX() {
        return super.getCenterX() -5;
    }

    @Override
    public int getCenterY() {
        return super.getCenterY() -5;
    }

}

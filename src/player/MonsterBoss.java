package player;

import item.HealthPack;
import item.SpeedBoost;
import weapon.speedRifle;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.ImageIcon;

public class MonsterBoss extends Monster {
    private Image[] runFrames;
    private int currentFrame = 0;
    private long lastFrameChangeTime = 0;
    private int frameDelay = 100;

    public MonsterBoss(int x, int y) {
        super(x, y);

        this.health = 2000;
        this.maxHealth = 2000;
        this.speed = 4;
        this.attackDamage = 50;

        runFrames = new Image[] {
                new ImageIcon("res/Monster/Boss/all/90x_run/Troll_03_1_RUN_000.png").getImage(),
                new ImageIcon("res/Monster/Boss/all/90x_run/Troll_03_1_RUN_001.png").getImage(),
                new ImageIcon("res/Monster/Boss/all/90x_run/Troll_03_1_RUN_002.png").getImage(),
                new ImageIcon("res/Monster/Boss/all/90x_run/Troll_03_1_RUN_003.png").getImage(),
                new ImageIcon("res/Monster/Boss/all/90x_run/Troll_03_1_RUN_004.png").getImage(),
                new ImageIcon("res/Monster/Boss/all/90x_run/Troll_03_1_RUN_005.png").getImage(),
                new ImageIcon("res/Monster/Boss/all/90x_run/Troll_03_1_RUN_006.png").getImage(),
                new ImageIcon("res/Monster/Boss/all/90x_run/Troll_03_1_RUN_007.png").getImage(),
                new ImageIcon("res/Monster/Boss/all/90x_run/Troll_03_1_RUN_008.png").getImage(),
                new ImageIcon("res/Monster/Boss/all/90x_run/Troll_03_1_RUN_009.png").getImage(),
        };
    }

    @Override
    public void draw(Graphics g) {

        drawHealthBar(g);

        Graphics2D g2d = (Graphics2D) g;


        {
            int haloDiameter = (int) (runFrames[currentFrame].getWidth(null) * 0.6);
            int haloX = x + (runFrames[currentFrame].getWidth(null) - haloDiameter) / 2;
            int haloY = y + (runFrames[currentFrame].getHeight(null) - haloDiameter) / 2;

            g.setColor(new Color(248, 201, 0, 144));
            g2d.fillOval(haloX, haloY, haloDiameter, haloDiameter);
        }

        long currentTime = System.currentTimeMillis();
        if (currentTime - lastFrameChangeTime >= frameDelay) {
            currentFrame = (currentFrame + 1) % runFrames.length;
            lastFrameChangeTime = currentTime;
        }


        g.drawImage(runFrames[currentFrame], x, y, null);

//        int hitboxWidth = runFrames[currentFrame].getWidth(null); // pour afficher la cadre de l'image
//        int hitboxHeight = runFrames[currentFrame].getHeight(null);
//        g.setColor(Color.RED);
//        g.drawRect(x, y, hitboxWidth, hitboxHeight);

//        // carré rouge au centre du monstre vous verifier le ciblage
//        g.setColor(Color.RED);
//        g.drawRect(getCenterX(), getCenterY(), 10, 10);
    }

    @Override
    protected void drawHealthBar(Graphics g) {
        int barWidth = 50;
        int barHeight = 5;
        int healthWidth = (int) ((health / (double) maxHealth) * barWidth);

        int currentImageWidth = runFrames[currentFrame].getWidth(null); // neceissaire car la fonction car ici l'image est en 90x90
        int barX = x + (currentImageWidth / 2) - (barWidth / 2);
        int barY = y - barHeight + 5;

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

    @Override
    public Object generateItem() {
        double chance = random.nextDouble();
        if (chance < 0.05) {
            System.out.println("HealthPack generated");
            return new HealthPack(x, y);
        } else if (chance < 0.1) {
            System.out.println("SpeedBoost generated");
            return new SpeedBoost(x, y);
        } else if (chance < 0.3) {
            System.out.println("AssaultRifle generated");
            return new speedRifle(x, y);
        };
        return null;
    }

    @Override
    public int getCenterX() {
        return super.getCenterX() + 20;
    }

    @Override
    public int getCenterY() {
        return super.getCenterY() + 20;
    }

}

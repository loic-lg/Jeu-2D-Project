package main;
import item.DecorationElement;
import item.Bush;
import item.Tree;
import player.Monster;
import player.Hero;
import weapon.LightBoost;
import weapon.Projectile;
import item.SpeedBoost;
import item.Rock;
import item.Spike;
import weapon.AssaultRifle;
import weapon.speedRifle;


import item.HealthPack;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.imageio.ImageIO;
import javax.swing.Timer;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.Iterator;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    private Hero hero;
    private AssaultRifle assaultRifle;
    private speedRifle speedRifle;
    private LightBoost lightBoost;
    private Timer timer;
    private Image backgroundTile;
    private boolean gameOver = false;
    private GameOverScreen gameOverScreen;
    private Random random = new Random();;

    private int currentLevel = 1;

    private Set<Integer> keysPressed = new HashSet<>();
    private int monstersKilled = 0;
    private boolean rifleEquipped = false;
    private boolean speedRifleEquipped = false;
    private boolean lightBoostEquipped = false;
    private int damage = 100;

    private ArrayList<Projectile> projectiles;

    private ArrayList<LightBoost> lightBoosts;
    private ArrayList<HealthPack> healthPacks;
    private ArrayList<SpeedBoost> speedBoosts;
    private ArrayList<speedRifle> speedRifles;
    private long lastShotTime = 0;
    private long rifleEquippedTime = 0;
    private long speedRifleEquippedTime = 0;
    private long lightBoostEquippedTime = 0;


    private Level level;

    private HashMap<Monster, Long> deadMonstersWithTime = new HashMap<>();
    private Image deathImage;



    public GamePanel() {
        hero = new Hero( ScreenConfig.screenWidth/2, ScreenConfig.screenHeight/2);

        //Gestion ajout arme bonus
        assaultRifle = new AssaultRifle(random.nextInt(ScreenConfig.screenWidth),random.nextInt(ScreenConfig.screenHeight ));
        speedRifle = new speedRifle(random.nextInt(ScreenConfig.screenWidth), random.nextInt(ScreenConfig.screenHeight));
        lightBoost = new LightBoost(random.nextInt(ScreenConfig.screenWidth), random.nextInt(ScreenConfig.screenHeight));
        speedRifles = new ArrayList<>();
        projectiles = new ArrayList<>();
        healthPacks = new ArrayList<>();
        speedBoosts = new ArrayList<>();
        lightBoosts = new ArrayList<>();

        speedRifles.add(speedRifle);
        lightBoosts.add(lightBoost);

        level = new Level();
        level.generateRandomMonster(20, 1, 1); // lvl initiale

        timer = new Timer(40, this);
        timer.start();

        setFocusable(true);
        addKeyListener(this);

        try {
            backgroundTile = ImageIO.read(new File("res/grass.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            deathImage = ImageIO.read(new File("res/Monster/Blood64x64.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void startGame() {
        if (timer == null) {
            timer = new Timer(150, this);
            timer.start();
        }
        setFocusable(true);
        requestFocusInWindow();// obliger pour garder les intéractions sur le menu et non le jeu en fond
    }
    public void setGameSpeed(boolean slow) {
        System.out.println("Appel à setGameSpeed avec normalSpeed = " + slow);

        if (timer != null) {
            System.out.println("Arrêt du Timer pour vitesse du jeu");
            timer.stop();
        }

        int delay = slow ? 40 : 120; // Ralenti de 40 à 500ms tant que le bouton start n'est pas cliqué
        System.out.println("vitese du jeu :" + (slow ? "Normal" : "Slow"));

        timer = new Timer(delay, this);
        timer.start();
        System.out.println("Timer redémarré avec délai : " + delay);

    }

    private void handleCollision(Hero hero, DecorationElement element) {
    // Gestion colision
        hero.stopMovement();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);


        if (backgroundTile != null) {
            for (int x = 0; x < getWidth(); x += backgroundTile.getWidth(this)) {
                for (int y = 0; y < getHeight(); y += backgroundTile.getHeight(this)) {
                    g.drawImage(backgroundTile, x, y, this);
                }
            }
        }

        if (gameOver) {
            if (gameOverScreen == null) {
                gameOverScreen = new GameOverScreen("Game Over", "Monstres tués : " + monstersKilled);
            }
            for (Monster monster : deadMonstersWithTime.keySet()) {
                g.drawImage(deathImage, monster.getX(), monster.getY(), null);
            }
            hero.draw(g);
            if (!rifleEquipped && assaultRifle != null) {
                assaultRifle.draw(g);
            }
            for (Spike spike : level.getSpikes()) {
                spike.draw_Element(g);
            }
            for (Rock rock : level.getRocks()) {
                rock.draw_Element(g);
            }
            for (HealthPack healthPack : healthPacks) {
                healthPack.draw(g);
            }
            for (SpeedBoost speedBoost : speedBoosts) {
                speedBoost.draw(g);
            }
            for (speedRifle speedRifle : speedRifles) {
                speedRifle.draw(g);
            }
            for (LightBoost lightBoost : lightBoosts) {
                lightBoost.draw(g);
            }
            for (Monster monster : level.getMonsters()) {
                monster.draw(g);
            }
            for (Projectile projectile : projectiles) {
                projectile.draw(g);
            }
            for (Tree tree : level.getTrees()) {
                tree.draw_Element(g);
            }
            for (Bush bush : level.getBushes()) {
                bush.draw_Element(g);
            }

            gameOverScreen.end(g, getWidth(), getHeight());
            return;
        }
        else {
            for (Monster monster : deadMonstersWithTime.keySet()) {
                g.drawImage(deathImage, monster.getX(), monster.getY(), null);
            }
            hero.draw(g);
            if (!rifleEquipped && assaultRifle != null) {
            assaultRifle.draw(g);
            }
            for (Spike spike : level.getSpikes()) {
                spike.draw_Element(g);
            }
            for (Rock rock : level.getRocks()) {
                rock.draw_Element(g);
            }
            for (HealthPack healthPack : healthPacks) {
                healthPack.draw(g);
            }
            for (SpeedBoost speedBoost : speedBoosts) {
                speedBoost.draw(g);
            }
            for (speedRifle speedRifle : speedRifles) {
                speedRifle.draw(g);
            }
            for (Monster monster : level.getMonsters()) {
                monster.draw(g);
            }
            for (Projectile projectile : projectiles) {
                projectile.draw(g);
            }
            for (Tree tree : level.getTrees()) {
                tree.draw_Element(g);
            }
            for (Bush bush : level.getBushes()) {
                bush.draw_Element(g);
            }
            for (LightBoost lightBoost : lightBoosts) {
                lightBoost.draw(g);
            }

        }

        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Niveau : " + currentLevel, getWidth() - 125, 50);

        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Monstres tués : " + monstersKilled, getWidth() - 180, 20);

        if (hero.isInvincible()) {
            int timeLeft = hero.getInvincibilityTimeRemaining();
            if (timeLeft > 6) {
                g.setColor(Color.GREEN);
            } else if (timeLeft > 3) {
                g.setColor(Color.ORANGE);
            } else {
                g.setColor(Color.RED);
            }

            g.setFont(new Font("Arial", Font.BOLD, 24));
            String text = "Bouclier : " + timeLeft + " s";

            // FontMetrics pour connaitre la largeur du texte
            FontMetrics metrics = g.getFontMetrics(g.getFont());
            int x = (getWidth() - metrics.stringWidth(text)) / 2;

            g.drawString(text, x, 30);
        }
        int maxHealth = 100;
        int currentHealth = hero.getHealth();

        float healthRatio = (float) currentHealth / maxHealth;

        int borderWidth = (int) ((1 - healthRatio) * 300); // Bordure
        int opacity = (int) ((1 - healthRatio) * 100);   // opacité
        Color borderColor = new Color(255, 0, 0, opacity);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(borderColor);

        g2d.fillRect(0, 0, getWidth(), borderWidth);
        g2d.fillRect(0, getHeight() - borderWidth, getWidth(), borderWidth);
        g2d.fillRect(0, 0, borderWidth, getHeight());
        g2d.fillRect(getWidth() - borderWidth, 0, borderWidth, getHeight());

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (hero.getHealth() <= 0) {
            gameOver = true;
            timer.stop();
            gameOverScreen = new GameOverScreen("Game Over", "Monstres tués : " + monstersKilled);

            gameOverScreen.saveScore(currentLevel, monstersKilled);

            String allScores = gameOverScreen.readScores();
        }

        if (!gameOver) {
            hero.move(keysPressed);

            if (!rifleEquipped && assaultRifle != null && hero.getCollisionHero().intersects(assaultRifle.getBounds())) {
            rifleEquipped = true;
            hero.equipAssaultRifle();
            rifleEquippedTime = System.currentTimeMillis();
            assaultRifle = null;
            System.out.println("Arme ramassée");

         }
            if (rifleEquipped && System.currentTimeMillis() - rifleEquippedTime >= 15000) {
            rifleEquipped = false;
            hero.unequipAssaultRifle();
            }
            if (speedRifleEquipped) {
                if (speedRifleEquippedTime == 0) {
                    speedRifleEquippedTime = System.currentTimeMillis();
                }
                long elapsedTime = System.currentTimeMillis() - speedRifleEquippedTime;
                if (elapsedTime >= 7000) {
                    speedRifleEquipped = false;
                    hero.unequipSpeedRifle();
                    speedRifleEquippedTime = 0;
                }
            }
            if (lightBoostEquipped) {
                if (lightBoostEquippedTime == 0) {
                    lightBoostEquippedTime = System.currentTimeMillis();
                }
                long elapsedTime = System.currentTimeMillis() - lightBoostEquippedTime;
                if (elapsedTime >= 7000) {
                    lightBoostEquipped = false;
                    hero.unequipLightBoost();
                    lightBoostEquippedTime = 0;
                }
            }

            if (rifleEquipped) {
                delayBeforeNextShot(1000);
            } else if (speedRifleEquipped) {
                delayBeforeNextShot(200);
            }else if (lightBoostEquipped) {
                delayBeforeNextShot(200);
            }
            else { // Sinon, comportement normal pour ajouter un projectile
                Monster closestMonster = findClosestMonsterWithinRange(15);
                if (closestMonster != null) {
                    projectiles.add(new Projectile(hero.getCenterX(), hero.getCenterY(), closestMonster, hero, 20));
                }
                if (closestMonster != null) {
                    projectiles.add(new Projectile(hero.getCenterX(), hero.getCenterY(), closestMonster, hero, 20));
                }
            }

            for (Monster monster : level.getMonsters()) {
                    monster.moveTowards(hero);
                }

                level.getBigBoss().disableInvincibilityBigBoss(level.getMonsters());
                // methode dans MonsterYouShouldRUN qui verifier si le monstre est le dernier de la liste pour desactiver l'invincibilité


                if(hero.isAssaultRifleEquipped()){
                    gestionDamage(17000);
                } else if (hero.isSpeedRifleEquipped()) {
                    gestionDamage(400);
                } else if (hero.isLightBoostEquipped()) {
                    gestionDamage(150);
                }
                else {
                    gestionDamage(20);
                }


                if (level.lvlup()) {
                    System.out.println("passage au level suivant : "+ currentLevel);
                    nextLevel();
                }

            long currentTime = System.currentTimeMillis();
            deadMonstersWithTime.entrySet().removeIf(entry -> (currentTime - entry.getValue()) > 30000); // timer pour gerer le temps de disparition du sang monstre



            checkCollisions();
            }
            repaint();
        }

    private void nextLevel() {
        currentLevel++;
        level.updateDecorations(hero);
        level.generateRandomMonster(currentLevel*20, currentLevel +2, currentLevel);

        System.out.println("Nouveau niveau généré");
    }

    /*
    private boolean isWithinRange(Monster monster, int range) {
        return hero.isNear(monster, range);
    }

     */

    private Monster findClosestMonsterWithinRange(int range) {
        Monster closestMonster = null;
        double closestDistance = Double.MAX_VALUE;

        for (Monster monster : level.getMonsters()) {
            if (hero.isNear(monster, range)) {
                double distance = Math.sqrt(Math.pow(hero.getX() - monster.getX(), 2) + Math.pow(hero.getY() - monster.getY(), 2));
                if (distance < closestDistance) {
                    closestDistance = distance;
                    closestMonster = monster;
                }
            }
        }

        return closestMonster;
    }

        private void checkCollisions() {
        Rectangle heroBounds = hero.getCollisionHero();

            for (Tree tree : level.getTrees()) {
                    if (heroBounds.intersects(tree.getCollisionItems())) {
                        handleCollision(hero, tree);
                    }
        }
        for (Rock rock : level.getRocks()) {
            if (heroBounds.intersects(rock.getCollisionItems())) {
                handleCollision(hero, rock);
            }
        }
        for (Spike spike : level.getSpikes()) {
        if (heroBounds.intersects(spike.getCollisionItems())) {
            spike.setHeroSteppedOn(true, hero);

        } else {
            spike.setHeroSteppedOn(false, hero);
        }
    }

            for (Monster monster : level.getMonsters()) {
            /* porté du héros pour attaque au corps à corps
            if (hero.isNear(monster, 10)) {
                hero.attack(monster);
            }
             */
                if (hero.isNear(monster, 1)) {
                    hero.takeDamage(5);
                }
            }

            Iterator<LightBoost> lightBoostIterator = lightBoosts.iterator();
            int pickupRange = 10; // Portée pour ramasser un SpeedRifle
            while (lightBoostIterator.hasNext()) {
                LightBoost lightBoost = lightBoostIterator.next();

                Rectangle heroRect = new Rectangle(hero.getX(), hero.getY(), 20, 20);
                Rectangle lightBoostRect = new Rectangle(
                        lightBoost.getX() - pickupRange,
                        lightBoost.getY() - pickupRange,
                        20 + 2 * pickupRange,
                        20 + 2 * pickupRange
                );

                if (heroRect.intersects(lightBoostRect)) {
                    hero.equipLightBoost(); // Active les propriétés associées au SpeedRifle
                    lightBoostEquipped = true;
                    lightBoostIterator.remove(); // Retire le SpeedRifle de la carte
                    System.out.println("SpeedRifle collected and equipped!");
                }
            }
            Iterator<speedRifle> speedAssaultRifleIterator = speedRifles.iterator();

            while (speedAssaultRifleIterator.hasNext()) {
                speedRifle speedRifle = speedAssaultRifleIterator.next();

                Rectangle heroRect = new Rectangle(hero.getX(), hero.getY(), 20, 20);
                Rectangle speedRifleRect = new Rectangle(
                        speedRifle.getX() - pickupRange,
                        speedRifle.getY() - pickupRange,
                        20 + 2 * pickupRange,
                        20 + 2 * pickupRange
                );

                if (heroRect.intersects(speedRifleRect)) {
                    hero.equipSpeedAssaultRifle(); // Active les propriétés associées au SpeedRifle
                    speedRifleEquipped = true;
                    speedAssaultRifleIterator.remove(); // Retire le SpeedRifle de la carte
                    System.out.println("SpeedRifle collected and equipped!");
                }
            }
            Iterator<HealthPack> healthPackIterator = healthPacks.iterator();
            while (healthPackIterator.hasNext()) {
                HealthPack healthPack = healthPackIterator.next();

                Rectangle heroRect = new Rectangle(hero.getX(), hero.getY(), 20, 20);
                Rectangle healthPackRect = new Rectangle(healthPack.getX() - pickupRange, healthPack.getY() - pickupRange,
                        20 + 2 * pickupRange, 20 + 2 * pickupRange);

                if (heroRect.intersects(healthPackRect)) {
                    hero.heal(healthPack.getHealAmount());
                    healthPackIterator.remove(); // retirer le HealthPack ramassé
                }
            }
            Iterator<SpeedBoost> speedBoostIterator = speedBoosts.iterator();
            while (speedBoostIterator.hasNext()) {
                SpeedBoost speedBoost = speedBoostIterator.next();

                Rectangle heroRect = new Rectangle(hero.getX(), hero.getY(), 20, 20);
                Rectangle speedBoostRect = new Rectangle(speedBoost.getX() - pickupRange, speedBoost.getY() - pickupRange,
                        20 + 2 * pickupRange, 20 + 2 * pickupRange);

                if (heroRect.intersects(speedBoostRect)) {
                    hero.applySpeedBoost(speedBoost.getSpeedIncrease(), speedBoost.getDuration());
                    speedBoostIterator.remove(); // Retirer le SpeedBoost ramassé
                }
            }
        }


    /*  methode qui parcourt la liste de projectiles et vérifie si le monstre est la cible de l'un d'eux pour eviter de tirer plusieurs fois sur le même monstre.

    private boolean isTargeted(Monster monster) {
        for (Projectile projectile : projectiles) {
            if (projectile.getTarget() == monster) {
                return true;
            }
        }
        return false;
    }
     */
    public void addMonster(Monster monster) {
        level.getMonsters().add(monster);
    }

    public void gestionDamage(int damage) {
        this.damage = damage;
        ArrayList<Projectile> projectilesToRemove = new ArrayList<>();
        ArrayList<Monster> deadMonsters = new ArrayList<>();
        for (int i = 0; i < projectiles.size(); i++) {
                    Projectile projectile = projectiles.get(i);
                    projectile.move();

                    for (Monster monster : level.getMonsters()) {
                        if (projectile.hasHitTarget(monster) && monster.isAlive()) {
                            monster.takeDamage(damage);
                            projectilesToRemove.add(projectile);


                            if (!monster.isAlive()) {
                                deadMonsters.add(monster);
                                deadMonstersWithTime.put(monster, System.currentTimeMillis());
                                monstersKilled++;

                                Object item = monster.generateItem();
                                if (item instanceof HealthPack) {
                                    healthPacks.add((HealthPack) item);
                                } else if (item instanceof SpeedBoost) {
                                    speedBoosts.add((SpeedBoost) item);
                                } else if (item instanceof speedRifle) {
                                    speedRifles.add((speedRifle) item);
                                }else if (item instanceof LightBoost) {
                                    lightBoosts.add((LightBoost) item);
                                }
                            }
                        }
                    }
                }
        projectiles.removeAll(projectilesToRemove);
        level.getMonsters().removeAll(deadMonsters);

    }

    private void delayBeforeNextShot(int delay) {
        long currentTime = System.currentTimeMillis();
            if (currentTime - lastShotTime >= delay) {
                Monster closestMonster = findClosestMonsterWithinRange(15);
                if (closestMonster != null) {
                    projectiles.add(new Projectile(hero.getCenterX(), hero.getCenterY(), closestMonster, hero, 20));
                    lastShotTime = currentTime; // Mettre à jour le temps du dernier tir
                }
            }

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!gameOver) {
                keysPressed.add(e.getKeyCode());
            }

    }
    @Override
    public void keyReleased(KeyEvent e) {
        keysPressed.remove(e.getKeyCode());
    }
    @Override
    public void keyTyped(KeyEvent e) {}
}

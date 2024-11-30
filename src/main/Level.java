package main;

import item.Bush;
import item.Rock;
import item.Spike;
import item.Tree;
import player.Monster;
import player.MonsterBoss;
import player.MonsterYouShouldRUN;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;
import player.Hero;

public class Level {
    private static final int MAP_WIDTH = ScreenConfig.screenWidth +5;
    private static final int MAP_HEIGHT = ScreenConfig.screenHeight +5;
    private static final int ELEMENT_SIZE = 64;
    private Random random = new Random();
    private Hero Hero;

    private ArrayList<Tree> trees = new ArrayList<>();
    private ArrayList<Bush> bushes = new ArrayList<>();
    private ArrayList<Rock> rocks = new ArrayList<>();
    private ArrayList<Spike> spikes = new ArrayList<>();
    private ArrayList<Monster> monsters = new ArrayList<>();
    private MonsterYouShouldRUN BigBoss;

    // pour ajuster le nombre d'objets en fonction de la taille de l'écran
    private int tree = 12*((MAP_WIDTH*MAP_HEIGHT)/(1440*900));
    private int bush = 10*((MAP_WIDTH*MAP_HEIGHT)/(1440*900));
    private int rock = 8*((MAP_WIDTH*MAP_HEIGHT)/(1440*900));
    private int spike = 5*((MAP_WIDTH*MAP_HEIGHT)/(1440*900));

    public Level() {
        generateBaseDecorations();
    }

    private void generateBaseDecorations() {
        generateRandomDecorations(tree, bush, rock, spike);
    }

    public void updateDecorations(Hero Hero) {
        addRandomDecorations(6,Hero);

        removeRandomDecorations(6);
    }
        // mopdif pour pas que sa poop sur le heros
    public void generateRandomDecorations(int treeCount, int bushCount, int rockCount, int spikeCount) {
        for (int i = 0; i < treeCount; i++) {
            int x = random.nextInt(MAP_WIDTH - ELEMENT_SIZE);
            int y = random.nextInt(MAP_HEIGHT - ELEMENT_SIZE);
            trees.add(new Tree(x, y));
        }
        for (int i = 0; i < rockCount; i++) {
            int x = random.nextInt(MAP_WIDTH - ELEMENT_SIZE);
            int y = random.nextInt(MAP_HEIGHT - ELEMENT_SIZE);
            rocks.add(new Rock(x, y));
        }
        for (int i = 0; i < bushCount; i++) {
            int x = random.nextInt(MAP_WIDTH - ELEMENT_SIZE);
            int y = random.nextInt(MAP_HEIGHT - ELEMENT_SIZE);
            bushes.add(new Bush(x, y));
        }
        for (int i = 0; i < spikeCount; i++) {
            int x = random.nextInt(MAP_WIDTH - ELEMENT_SIZE);
            int y = random.nextInt(MAP_HEIGHT - ELEMENT_SIZE);
            spikes.add(new Spike(x, y));
        }
    }
    private void addRandomDecorations(int count, Hero Hero) {
        int safeDistance = 250; // Distance minimale entre le héros et un objet
        for (int i = 0; i < count; i++) {
            int type = random.nextInt(4); // 0: Tree, 1: Rock, 2: Bush, 3: Spike
            int x, y;

            do {
                x = random.nextInt(MAP_WIDTH);
                y = random.nextInt(MAP_HEIGHT);
            } while (Math.sqrt(Math.pow(x - Hero.getCenterX(), 2) + Math.pow(y - Hero.getCenterY(), 2)) < safeDistance);

            // Ajouter l'objet en fonction du type
            switch (type) {
                case 0 -> trees.add(new Tree(x, y));
                case 1 -> rocks.add(new Rock(x, y));
                case 2 -> bushes.add(new Bush(x, y));
                case 3 -> spikes.add(new Spike(x, y));
            }
        }
    }


    private void removeRandomDecorations(int count) {
        for (int i = 0; i < count; i++) {
            int type = random.nextInt(4); // 0: Tree, 1: Rock, 2: Bush, 3: Spike

            switch (type) {
                case 0 -> { if (!trees.isEmpty()) trees.remove(random.nextInt(trees.size())); }
                case 1 -> { if (!rocks.isEmpty()) rocks.remove(random.nextInt(rocks.size())); }
                case 2 -> { if (!bushes.isEmpty()) bushes.remove(random.nextInt(bushes.size())); }
                case 3 -> { if (!spikes.isEmpty()) spikes.remove(random.nextInt(spikes.size())); }
            }
        }
    }

    public MonsterYouShouldRUN getBigBoss() {
        return BigBoss;
    }
//// attention les monstres ne viennent que dans les coin
public void generateRandomMonster(int monsterCount, int bossCount, int bigBossCount) {
    for (int i = 0; i < monsterCount; i++) {
        Point position = generateMonsterPosition();
        monsters.add(new Monster(position.x, position.y));
    }

    for (int i = 0; i < bossCount; i++) {
        Point position = generateMonsterPosition();
        monsters.add(new MonsterBoss(position.x, position.y));
    }

    if (bigBossCount > 0) {
        Point position = generateMonsterPosition();
        BigBoss = new MonsterYouShouldRUN(position.x, position.y);
        monsters.add(BigBoss);
    }
}

    public Point generateMonsterPosition() {
        int x, y;


        if (random.nextBoolean()) {

            if (random.nextBoolean()) {
                x = random.nextInt(101) - 100;
            } else {
                x = random.nextInt(101) + MAP_WIDTH;
            }

            y = random.nextInt(MAP_HEIGHT + 1);
        } else {

            if (random.nextBoolean()) {
                y = random.nextInt(101) - 100;
            } else {
                y = random.nextInt(101) + MAP_HEIGHT;
            }
            x = random.nextInt(MAP_WIDTH + 1);
        }

        return new Point(x, y);
    }

    public ArrayList<Tree> getTrees() {
        return trees;
    }

    public ArrayList<Bush> getBushes() {
        return bushes;
    }

    public ArrayList<Rock> getRocks() {
        return rocks;
    }

    public ArrayList<Spike> getSpikes() {
        return spikes;
    }

    public ArrayList<Monster> getMonsters() {
        return monsters;
    }

    public boolean lvlup() {
        return monsters.isEmpty();
    }

}

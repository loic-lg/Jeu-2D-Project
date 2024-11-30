package test;

import main.Level;
import item.Tree;
import player.Hero;
import player.Monster;
import player.MonsterYouShouldRUN;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Point;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class LevelTest {
    private Level level;
    private Hero hero = new Hero(0, 0);
    private int test = 6;

    @BeforeEach
    void setUp() {
        level = new Level();
    }

    @Test
    void testGenerateBaseDecorations() {
        ArrayList<Tree> trees = level.getTrees();
        assertFalse(trees.isEmpty(), "Base decorations (trees) should not be empty after initialization.");
    }

    @Test
    void testUpdateDecorations() {
        int initialTreeCount = level.getTrees().size();
        level.updateDecorations(hero);
        assertTrue(level.getTrees().size() > 0, "Tree decorations should still exist after update.");
        assertNotEquals(initialTreeCount, level.getTrees().size(), "Tree count should change after update.");
    }

    @Test
    void testGenerateRandomMonster() {
        int initialMonsterCount = level.getMonsters().size();
        level.generateRandomMonster(3, 2, 1);

        ArrayList<Monster> monsters = level.getMonsters();
        assertEquals(initialMonsterCount + 6, monsters.size(), "The total monster count should be incremented by 6.");

        boolean containsBigBoss = monsters.stream().anyMatch(monster -> monster instanceof MonsterYouShouldRUN);
        assertTrue(containsBigBoss, "The list of monsters should contain the BigBoss.");
    }

    @Test
    void testGenerateMonsterPosition() {
        Point position = level.generateMonsterPosition();
        assertNotNull(position, "Generated monster position should not be null.");
        boolean isOutsideMap = position.x < 0 || position.x > 1605 || position.y < 0 || position.y > 1205;
        assertTrue(isOutsideMap, "Monster position should be outside the map bounds.");
    }

    @Test
    void testLvlup() {
        assertTrue(level.lvlup(), "Level-up should be true if there are no monsters.");

        level.generateRandomMonster(1, 0, 0);
        assertFalse(level.lvlup(), "Level-up should be false if there are monsters.");
    }

    @Test
    void testAddRandomDecorations() {
        int initialBushCount = level.getBushes().size();
        level.generateRandomDecorations(0, 5, 0, 0);
        assertEquals(initialBushCount + 5, level.getBushes().size(), "Bush count should increase by 5.");
    }
}
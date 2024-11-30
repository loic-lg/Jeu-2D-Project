package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import player.Hero;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class HeroTest {

    private Hero hero;

    @BeforeEach
    void setUp() {
        hero = new Hero(100, 100);
    }

    @Test
    void testInitialHeroAttributes() {
        assertEquals(100, hero.getX(), "Hero's initial X position should be 100");
        assertEquals(100, hero.getY(), "Hero's initial Y position should be 100");
        assertEquals(100, hero.getHealth(), "Hero's initial health should be 100");
        assertFalse(hero.isInvincible(), "Hero should not be invincible initially");
    }

    @Test
    void testHeroMovement() {
        Set<Integer> keysPressed = new HashSet<>();
        keysPressed.add(KeyEvent.VK_UP);
        hero.move(keysPressed);

        assertEquals(100, hero.getX(), "X position should not change when moving up");
        assertEquals(90, hero.getY(), "Y position should decrease by speed when moving up");

        keysPressed.clear();
        keysPressed.add(KeyEvent.VK_LEFT);
        hero.move(keysPressed);

        assertEquals(90, hero.getX(), "X position should decrease by speed when moving left");
        assertEquals(90, hero.getY(), "Y position should remain unchanged when moving left");
    }

    @Test
    void testInvincibilityTimer() {
        hero.activateInvincibility();
        assertTrue(hero.isInvincible(), "Hero should be invincible after activation");

        int remainingTime = hero.getInvincibilityTimeRemaining();
        assertTrue(remainingTime > 0 && remainingTime <= 8, "Remaining time should be between 0 and 8 seconds");

        // Simuler l'expiration de l'invincibilité
        try {
            Thread.sleep(8100); // Assurez-vous que la durée dépasse 8000 ms
        } catch (InterruptedException e) {
            fail("Thread sleep interrupted");
        }

        assertFalse(hero.isInvincible(), "Hero should no longer be invincible after timer expiration");
    }

    @Test
    void testCollisionDetection() {
        Rectangle collisionHero = hero.getCollisionHero();

        assertEquals(100 + 10, collisionHero.x, "Collision box X should match the hero's adjusted X position");
        assertEquals(100 + 5, collisionHero.y, "Collision box Y should match the hero's adjusted Y position");
        assertEquals(17, collisionHero.width, "Collision box width should be calculated correctly");
        assertEquals(54, collisionHero.height, "Collision box height should be calculated correctly");
    }
}
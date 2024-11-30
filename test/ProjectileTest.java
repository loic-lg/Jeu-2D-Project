package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import player.Hero;
import player.Monster;
import weapon.Projectile;

import static org.junit.jupiter.api.Assertions.*;

class ProjectileTest {
    private Monster mockMonster;
    private Projectile projectile;
    private Hero hero;

    @BeforeEach
    void setUp() {
        // Crée un monstre fictif avec une position prédéfinie
        mockMonster = new Monster(450, 400) {
            @Override
            public int getCenterX() {
                return 100;
            }

            @Override
            public int getCenterY() {
                return 100;
            }
        };

        // Initialise le projectile avec une position initiale et une cible
        projectile = new Projectile(0, 0, mockMonster,hero,20);
    }

    @Test
    void testProjectileInitialization() {
        // Vérifie que la position initiale du projectile est correcte
        assertEquals(0, projectile.getX());
        assertEquals(0, projectile.getY());

        // Vérifie que la cible est bien le monstre fourni
        assertEquals(mockMonster, projectile.getTarget());
    }

    @Test
    void testProjectileHitTarget() {
        // Place le projectile près de la cible pour simuler un impact
        Projectile closeProjectile = new Projectile(90, 90, mockMonster, hero,20);

        closeProjectile.move();
        assertTrue(closeProjectile.hasHitTarget(mockMonster), "Projectile should hit the target when close enough");

        // Vérifie qu'un projectile éloigné ne touche pas la cible
        Projectile farProjectile = new Projectile(0, 0, mockMonster, hero,20);
        farProjectile.move();
        assertFalse(farProjectile.hasHitTarget(mockMonster), "Projectile should not hit the target when far away");
    }
}
package player;

public interface Player {

    void takeDamage(int damage);

    boolean isAlive();

    int getHealth();

    int getX();

    int getY();

    int getCenterX();

    int getCenterY();
}

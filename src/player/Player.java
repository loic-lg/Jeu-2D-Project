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

 //getHealth à implémenter dans les classes qui implémentent l'interface Player

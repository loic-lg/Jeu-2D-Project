package main;

public class ScreenConfig {
    public static int screenWidth;
    public static int screenHeight;

    public static void initialize(int width, int height) {
        screenWidth = width;
        screenHeight = height;
    }
}

// la classe servira simplement a stocker les dimensions de l'écran pour les utiliser dans
// d'autre class et eviter de les prendre du main
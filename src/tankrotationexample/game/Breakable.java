package tankrotationexample.game;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;

public class Breakable extends Wall{
    private int life = 2;

    public Breakable(float y, float x, BufferedImage img) {
        super(y, x, img);
    }
}

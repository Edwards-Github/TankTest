package tankrotationexample.game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;

public class Wall extends GameObject{

    public Wall(float y, float x, BufferedImage img) {
        super(x,y,img);
    }
}

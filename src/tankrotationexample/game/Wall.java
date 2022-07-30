package tankrotationexample.game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;

public class Wall {
    protected float y,x;
    protected BufferedImage img;

    public Wall(float y, float x, BufferedImage img) {
        this.y = y;
        this.x = x;
        this.img = img;
    }

    @Override
    public String toString() {
        return "Wall{" +
                "y=" + y +
                ", x=" + x +
                '}';
    }

    public void drawImage(Graphics2D buffer){
        buffer.drawImage(img, (int) x, (int) y, null);
    }
}

package tankrotationexample.game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;

public class Wall {
    protected float y,x;
    protected BufferedImage img;
    protected Rectangle hitBox;

    public Wall(float y, float x, BufferedImage img) {
        this.y = y;
        this.x = x;
        this.img = img;
        this.hitBox = new Rectangle((int)x, (int)y, this.img.getWidth(), this.img.getHeight());
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
        buffer.setColor(Color.CYAN);
        buffer.drawRect((int)x, (int)y, this.img.getWidth(), this.img.getHeight());

    }
}

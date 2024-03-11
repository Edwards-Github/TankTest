package tankrotationexample.game;

import tankrotationexample.Resources;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

public class Breakable extends Wall {
    int life = 4;

    public Breakable(float y, float x, BufferedImage img) {
        super(y, x, img);
    }

    void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void handleCollision(Collidable object) {
        if (object instanceof Bullet) {
            if (this.life != 0) {
                this.life -= 1;
            }
            else{
                this.img = null;
                this.setPosition(-100f, -100f);
                this.hitBox.setLocation(-100,-100);
            }
        }
    }
}

package tankrotationexample.game;

import tankrotationexample.Resources;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

public class Breakable extends Wall {
    int life = 2;

    public Breakable(float y, float x, BufferedImage img) {
        super(y, x, img);
    }

    public void handleCollision(Collidable object) {
        if (object instanceof Bullet) {
            if (this.life != 0) {
                this.life -= 1;
            }
            else{
                this.img = null;
                // this.hitBox = null;
                this.x = -30;
                this.y = -30;
            }
        }
    }
}

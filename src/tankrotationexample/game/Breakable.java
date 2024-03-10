package tankrotationexample.game;

import tankrotationexample.Resources;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

public class Breakable extends Wall {
    private int life = 2;

    public Breakable(float y, float x, BufferedImage img) {
        super(y, x, img);
    }

    public void handleCollision(Collidable object) {
        if (object instanceof Bullet) {
            if (this.life != 0) {
                this.life -= 1;
            }
            else{
                System.out.println("Else statement");
                this.img = null;
                this.x = 0;
                this.y = 0;
            }
        }
    }
}

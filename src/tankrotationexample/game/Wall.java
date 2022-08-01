package tankrotationexample.game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;

public class Wall extends GameObject implements Collidable{

    public Wall(float y, float x, BufferedImage img) {
        super(x,y,img);
    }

    @Override
    public Rectangle getHitBox() {
        return this.hitBox.getBounds();
    }

    @Override
    public void handleCollision(Collidable object) {
        System.out.println("obj1 collided with obj2");
    }
}

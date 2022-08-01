package tankrotationexample.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PowerUp extends GameObject{
    public PowerUp(float x, float y, BufferedImage img){
        super(x, y, img);
    }


    public void applyPowerUp(){

    }

    @Override
    public Rectangle getHitBox() {
        return this.hitBox.getBounds();
    }

    @Override
    public void handleCollision(Collidable obj1) {
        System.out.println("Power up has been collided with");
    }
}

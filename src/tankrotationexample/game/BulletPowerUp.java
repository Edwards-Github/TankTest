package tankrotationexample.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BulletPowerUp extends PowerUp{

    public BulletPowerUp(float x, float y, BufferedImage img) {
        super(x, y, img);
    }

    public Rectangle getHitBox() {
        return this.hitBox.getBounds();
    }

    public void handleCollision(Collidable obj) {
        if(obj instanceof Tank){
            for(int index = 0; index < ((Tank) obj).ammo.size(); index++)
            {

            }
        }
    }
}

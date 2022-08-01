package tankrotationexample.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GameObject implements Collidable{
    protected float x, y;
    protected  BufferedImage img;
    protected Rectangle hitBox;
    public GameObject(float x, float y, BufferedImage img) {
        this.x = x;
        this.y = y;
        this.img = img;
        this.hitBox = new Rectangle((int)x, (int)y, this.img.getWidth(), this.img.getHeight());
    }

    public void drawImage(Graphics2D buffer){
        buffer.drawImage(img, (int) x, (int) y, null);
    }

    @Override
    public Rectangle getHitBox(){
        return hitBox.getBounds();
    }

    @Override
    public void handleCollision(Collidable with) {

    }

    @Override
    public boolean isCollidable(){
        return true;
    }
}

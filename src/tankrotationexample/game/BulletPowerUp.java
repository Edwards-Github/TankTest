package tankrotationexample.game;

import tankrotationexample.GameConstants;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class BulletPowerUp extends PowerUp{
    BulletPowerUp(float x, float y, BufferedImage img) {
        super(x,y,img);
    }


    void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void drawImage(Graphics2D g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        // rotation.rotate(this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
        g.drawImage(this.img, rotation, null);
        g.setColor(Color.GREEN);
        g.drawRect((int)this.hitBox.x,(int)this.hitBox.y,(int)this.hitBox.getWidth(), (int)this.hitBox.getHeight()); // draw hitbox
//        g.setColor(Color.RED);
//        g.drawRect((int)x,(int)y,this.img.getWidth(), this.img.getHeight());
    }

    public Rectangle getHitBox(){return this.hitBox.getBounds();}

    public void handleCollision(Collidable obj) {
        if(obj instanceof Tank){
            ((Tank) obj).empoweredBulletBuff = true;
            this.img = null;
            this.setPosition(-100f, -100f);
            this.hitBox.setLocation(-100,-100);
        }
    }
}

package tankrotationexample.game;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class BulletPowerUp extends PowerUp{
    public BulletPowerUp(float x, float y, BufferedImage img) {
        super(x, y, img);
    }

    public Rectangle getHitBox() {
        return this.hitBox.getBounds();
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

    public void handleCollision(Collidable obj) {
        if(obj instanceof Tank){
            System.out.println("Inside if statement");
            // ((Tank)obj).ammo.add(new Bullet(((Tank) obj).x, ((Tank) obj).y));
            if(!((Tank) obj).ammo.isEmpty()){
                for(Bullet bullet: ((Tank) obj).ammo)
                {
                    bullet.damage = 60;
                    bullet.hitBox.x += 50;
                    bullet.hitBox.y += 50;
                    System.out.println("Bullet damage: " + bullet.damage);
                }
                this.img = null;
                this.setPosition(-100f, -100f);
                this.hitBox.setLocation(-100,-100);
            }
        }
    }
}

package tankrotationexample.game;

import tankrotationexample.Resources;

import java.awt.*;
import java.awt.geom.AffineTransform;
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

    void update(){
        if(this.life == 0){
            this.img = null;
            this.setPosition(-100f, -100f);
            this.hitBox.setLocation(-100,-100);
        }
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

    public void handleCollision(Collidable object) {
        if (object instanceof Bullet) {
            if (this.life != 0) {
                this.life -= 1;
            }
        }
    }
}

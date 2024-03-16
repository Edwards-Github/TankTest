package tankrotationexample.game;

import tankrotationexample.GameConstants;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class EmpoweredBullet extends GameObject{
    private float angle;
    private float R = 2f;
    int damage = 25;
    boolean collided = false;

    EmpoweredBullet(float x, float y, float angle, BufferedImage img) {
        super(x,y,img);
        this.angle = angle;
    }

    void setPosition(float x, float y, float angle) {
        this.x = x;
        this.y = y;
        this.angle = angle;
    }

    void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    void update() {
        moveForwards();
        this.hitBox.setLocation((int) this.x, (int) this.y);
    }


    private void moveForwards() {
        x += Math.round(R * Math.cos(Math.toRadians(angle)));
        y += Math.round(R * Math.sin(Math.toRadians(angle)));
        // Uncomment if you don't want bullets to go out of map
        // checkBorder();
    }


    private void checkBorder() {
        if (x < 30) {
            x = 30;
        }
        if (x >= GameConstants.WORLD_WIDTH - 88) {
            x = GameConstants.WORLD_WIDTH - 88;
        }
        if (y < 40) {
            y = 40;
        }
        if (y >= GameConstants.WORLD_HEIGHT - 80) {
            y = GameConstants.WORLD_HEIGHT - 80;
        }
    }

    @Override
    public String toString() {
        return "x=" + x + ", y=" + y + ", angle=" + angle;
    }


    public void drawImage(Graphics2D g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
        g.drawImage(this.img, rotation, null);
        g.setColor(Color.RED);
        g.drawRect((int)this.hitBox.x,(int)this.hitBox.y,(int)this.hitBox.getWidth(), (int)this.hitBox.getHeight()); // draw hitbox
//        g.setColor(Color.RED);
//        g.drawRect((int)x,(int)y,this.img.getWidth(), this.img.getHeight());
    }

    public Rectangle getHitBox(){return this.hitBox.getBounds();}

    public void handleCollision(Collidable object){
        if(object instanceof Tank && !((Tank)object).empoweredAmmo.contains(this)){
            this.setPosition(-30f,-30f);
            this.hitBox.setLocation(-30, -30);
            this.setEmpoweredBulletVelocityToZero();
            this.collided = true;
        }
    }

    public void setEmpoweredBulletVelocityToZero(){this.R = 0;}
}

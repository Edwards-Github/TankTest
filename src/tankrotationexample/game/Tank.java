package tankrotationexample.game;

import tankrotationexample.GameConstants;
import tankrotationexample.Resources;
import tankrotationexample.Sound;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author anthony-pc
 */
public class Tank{
    private float x;
    private float y;
    private float vx;
    private float vy;
    private float angle;

    private float R = 5;
    private float ROTATIONSPEED = 3.0f;

    float fireDelay = 120f;
    float coolDown = 0f;
    float rateOfFire = 1f;

    float charge = 1f;
    float chargeRate = .05f;
    private BufferedImage img;
    private boolean UpPressed;
    private boolean DownPressed;
    private boolean RightPressed;
    private boolean LeftPressed;
    private boolean shootPressed;

    List<Bullet> ammo = new ArrayList<>();
    Bullet b;

    Tank(float x, float y, float vx, float vy, float angle, BufferedImage img) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.img = img;
        this.angle = angle;
    }

    void setX(float x){ this.x = x; }

    void setY(float y) { this. y = y;}

    void toggleUpPressed() {
        this.UpPressed = true;
    }

    void toggleDownPressed() {
        this.DownPressed = true;
    }

    void toggleRightPressed() {
        this.RightPressed = true;
    }

    void toggleLeftPressed() {
        this.LeftPressed = true;
    }

    void toggleShootPressed() { this.shootPressed = true; }

    void unToggleUpPressed() {
        this.UpPressed = false;
    }

    void unToggleDownPressed() {
        this.DownPressed = false;
    }

    void unToggleRightPressed() {
        this.RightPressed = false;
    }

    void unToggleLeftPressed() { this.LeftPressed = false; }

    void unToggleShootPressed() {
        this.shootPressed = false;
        if(b != null){
            b.setScaleFactor(charge);
            b.setPosition(setBulletStartX(), setBulletStartY(), angle);
            this.charge = 0;
            this.ammo.add(b);
            b = null;
        }}

    public float getX() { return x; }

    public float getY() { return y; }


    void update() {
        if (this.UpPressed) {
            this.moveForwards();
        }

        if (this.DownPressed) {
            this.moveBackwards();
        }

        if (this.LeftPressed) {
            this.rotateLeft();
        }

        if (this.RightPressed) {
            this.rotateRight();
        }

        if(this.shootPressed && this.coolDown >= this.fireDelay){
            this.coolDown = 0;
            this.charge += this.chargeRate;
            (new Sound(Resources.getSound("bullet"))).playSound();
            if(b == null){
                b = new Bullet(this.setBulletStartX(), this.setBulletStartY(), angle, charge, Resources.getImage("bullet"));
            }else{
                b.setPosition(setBulletStartX(), setBulletStartY(), angle);
                b.setScaleFactor(charge);
            }
        }
        this.coolDown += this.rateOfFire;

//        if(b != null){
//            b.update();
//        }
        this.ammo.forEach(b -> b.update());
    }

    private int setBulletStartX() {
        float cx = 29f*(float)Math.cos(Math.toRadians(angle));
        return (int)x+this.img.getWidth()/2 + (int)cx-4;
    }

    private int setBulletStartY() {
        float cy = 29f*(float)Math.sin(Math.toRadians(angle));
        return (int)y+this.img.getHeight()/2 + (int)cy-4;
    }

    private void rotateLeft() {
        this.angle -= this.ROTATIONSPEED;
    }

    private void rotateRight() {
        this.angle += this.ROTATIONSPEED;
    }

    private void moveBackwards() {
        vx =  Math.round(R * Math.cos(Math.toRadians(angle)));
        vy =  Math.round(R * Math.sin(Math.toRadians(angle)));
        x -= vx;
        y -= vy;
       checkBorder();
    }

    private void moveForwards() {
        vx = Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = Math.round(R * Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;
        checkBorder();
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


    void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        if(b != null){
            b.drawImage(g2d);
        }
        this.ammo.forEach(b -> b.drawImage(g2d));
        g2d.drawImage(this.img, rotation, null);
        g2d.setColor(Color.RED);
        g2d.drawRect((int)x,(int)y,this.img.getWidth(), this.img.getHeight());
    }
}

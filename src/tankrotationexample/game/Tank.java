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
public class Tank extends GameObject{
    private float vx;
    private float vy;
    private float angle;

    int health = 100;
    int lives = 3;
    private float R = 5;
    private float ROTATIONSPEED = 3.0f;

    float fireDelay = 120f;
    float coolDown = 0f;
    float rateOfFire = 1f;

    private BufferedImage img;
    private boolean UpPressed;
    private boolean DownPressed;
    private boolean RightPressed;
    private boolean LeftPressed;
    private boolean shootPressed;

    private Rectangle hitBox;

    private float screenX;
    private float screenY;

    Bullet b;
    List<Bullet> ammo = new ArrayList<>();
    List<Animation> ba = new ArrayList<>();

    Tank(float x, float y, float vx, float vy, float angle, BufferedImage img) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.img = img;
        this.angle = angle;
        this.health = health;
        this.hitBox = new Rectangle((int)x,(int)y, this.img.getWidth(), this.img.getHeight());
    }

    void setPosition(float x, float y){
        this.x = x;
        this.y = y;
        this.hitBox.setLocation((int)x, (int)y);
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
            b.setPosition(this.setBulletStartX(), this.setBulletStartY(), angle);
            this.ammo.add(b);
            b = null;
        }
    }


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
            (new Sound(Resources.getSound("bullet"))).playSound();
            this.ammo.add(new Bullet(this.setBulletStartX(), this.setBulletStartY(), angle, Resources.getImage("bullet")));
            Animation a = new Animation(setBulletStartX()-13, setBulletStartY()-10, Resources.getAnimation("bullet"));
            a.start();
            ba.add(a);
        }
        this.coolDown += this.rateOfFire;
        this.ammo.forEach(b -> b.update());

        centerScreen();
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
        this.hitBox.setLocation((int)x, (int)y);
    }

    private void moveForwards() {
        vx = Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = Math.round(R * Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;
        checkBorder();
        this.hitBox.setLocation((int)x, (int)y);
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

    private void centerScreen(){
        this.screenX = this.x - GameConstants.GAME_SCREEN_WIDTH / 4f;
        this.screenY = this.y - GameConstants.GAME_SCREEN_HEIGHT / 2f;

        if (this.screenX < 0){
            this.screenX = 0;
        }
        if (this.screenY < 0){
            this.screenY = 0;
        }

        // keep camera still at right border
        if (screenX > GameConstants.WORLD_WIDTH - GameConstants.GAME_SCREEN_WIDTH / 2f){
            screenX = GameConstants.WORLD_WIDTH - GameConstants.GAME_SCREEN_WIDTH / 2f;
        }

        // keep camera still at bottom border
        if (screenY > GameConstants.WORLD_HEIGHT - GameConstants.GAME_SCREEN_HEIGHT){
            screenY = GameConstants.WORLD_HEIGHT - GameConstants.GAME_SCREEN_HEIGHT;
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
        g2d.drawImage(this.img, rotation, null);
        if(b != null){
            b.drawImage(g2d);
        }
        this.ammo.forEach(b -> b.drawImage(g2d));

        g2d.setColor(Color.MAGENTA);
        g2d.drawRect((int)x, (int)y, this.img.getWidth(), this.img.getHeight());

        // draw animation
        this.ba.forEach(a -> a.drawImage(g2d));

        // Health bar
        g2d.setColor(Color.BLUE);
        g2d.drawRect((int)x,(int)y - 30, 100, 25);
        // set color of health bar
        if(this.health >= 70){
            g2d.setColor(Color.GREEN);
        }else if(this.health >= 50){
            g2d.setColor(Color.YELLOW);
        }else{
            g2d.setColor(Color.RED);
        }
        g2d.fillRect((int)x,(int)y - 30, health, 25);
        g2d.drawString("" + this.health, (int)x, (int)y-35);

        // Lives
        for (int i = 0; i < this.lives; i++) {
            g2d.setColor(Color.RED);
            g2d.drawOval((int) x + (i * 20), (int) y + 55, 15, 15);
            g2d.fillOval((int) x + (i * 20), (int) y + 55, 15, 15);
        }
    }

    public Rectangle getHitBox(){
        return this.hitBox.getBounds();
    }

    public float getScreenX() {
        return this.screenX;
    }

    public float getScreenY() {
        return this.screenY;
    }
}

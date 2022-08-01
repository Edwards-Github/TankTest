package tankrotationexample.game;

import tankrotationexample.GameConstants;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Bullet {
    private float x;
    private float y;
    private float angle;
    private float R = 2f;
    private BufferedImage img;
    private Rectangle hitBox;

    Bullet(float x, float y, float angle, BufferedImage img) {
        this.x = x;
        this.y = y;
        this.img = img;
        this.angle = angle;
        this.hitBox = new Rectangle((int)x,(int)y, this.img.getWidth(), this.img.getHeight());
    }


    void setPosition(float x, float y, float angle) {
        this.x = x;
        this.y = y;
        this.angle = angle;
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


    void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.img, rotation, null);
        g2d.setColor(Color.RED);
        g2d.rotate(angle);
        g2d.drawRect((int)x,(int)y,this.img.getWidth(), this.img.getHeight());
        g2d.rotate(-angle);
    }

    public Rectangle getHitBox(){
        return this.hitBox.getBounds();
    }
}


package tankrotationexample.game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class Animation extends Thread {
    float x, y;
    int delay = 60;
    List<BufferedImage> frames;
    boolean isRunning;
    int currentFrame = 0;

    public Animation(float x, float y, List<BufferedImage> frames) {
        this.x = x;
        this.y = y;
        this.frames = frames;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void drawImage(Graphics2D g2){
        if(isRunning){
            g2.drawImage(this.frames.get(currentFrame),(int)x,(int)y, null);
        }
    }

    @Override
    public void run() {
        this.isRunning = true;
        try {
            while (isRunning) {
                this.currentFrame = (this.currentFrame + 1) % this.frames.size();
                if (this.currentFrame == this.frames.size() - 1) {
                    isRunning = false;
                }
                Thread.sleep(delay);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tankrotationexample.game;


import tankrotationexample.GameConstants;
import tankrotationexample.Launcher;
import tankrotationexample.Resources;
import tankrotationexample.Sound;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.ArrayList;
import java.util.List;

/**
 * @author anthony-pc
 */
public class GameWorld extends JPanel implements Runnable {

    private BufferedImage world;

    private Tank t1;
    private Tank t2;
    private Launcher lf;
    private long tick = 0;
    List<Wall> walls = new ArrayList<>();
//    List<PowerUp> powerUps = new ArrayList<>();

    /**
     * 
     * @param lf
     */
    public GameWorld(Launcher lf) {
        this.lf = lf;
    }

    @Override
    public void run() {
        Thread t;
        try {
            this.resetGame();
            t = new Thread(new Sound(Resources.getSound("music")));
            t.start();
            while (true) {
                this.tick++;
                this.t1.update(); // update tank
                this.t2.update(); // update tank

                // checking hitboxes for tank 1 against walls
                for (int i = 0; i < walls.size(); i++) {
                    Wall w = this.walls.get(i);
                    if(w.getHitBox().intersects(this.t1.getHitBox())){
                        System.out.println("t1 has hit a wall");
                    }
                }

                // checking hitboxes for tank 2 against walls
                for (int i = 0; i < walls.size(); i++) {
                    Wall w = this.walls.get(i);
                    if(w.getHitBox().intersects(this.t2.getHitBox())){
                        System.out.println("t2 has hit a wall");
                    }
                }

//                // checking hitboxes for tank 1 against bullets from tank 2
                if (this.t2.ammo.size() > 0) {
                    for (int i = 0; i < this.t2.ammo.size(); i++) {
                        Bullet b = this.t2.ammo.get(i);
                        b.update();
                        if (b.getHitBox().intersects(this.t1.getHitBox())) {
                            System.out.println("t1 was hit by t2 bullet");
                        }
                    }
                }
//
//                // checking hitboxes for tank 2 against bullets
                if (this.t1.ammo.size() > 0) {
                    for (int i = 0; i < this.t1.ammo.size(); i++) {
                        Bullet b = this.t1.ammo.get(i);
                        b.update();
                        if (b.getHitBox().intersects(this.t2.getHitBox())) {
                            System.out.println("t2 was hit by a t1 bullet");
                        }
                    }
                }

//                // checking hitboxes for tank 1
//                for (int i = 0; i < this.powerUps.size(); i++) {
//                    PowerUp p = this.powerUps.get(i);
//                    if(p.getHitBox().intersects(this.t1.getHitBox())){
//                        System.out.println("t1 has hit a power up");
//                    }
//                }
//
//                // checking hitboxes for tank 1
//                for (int i = 0; i < this.powerUps.size(); i++) {
//                    PowerUp p = this.powerUps.get(i);
//                    if(p.getHitBox().intersects(this.t2.getHitBox())){
//                        System.out.println("t2 has hit a power up");
//                    }
//                }
                while(true){
                    tick++;
                    this.t1.update(); // update tank
                    this.t2.update(); // update tank
                    this.repaint(); // redraw game

                    Thread.sleep(1000 / 144);
                    //                if (this.tick >= 144 * 8) {
//                    t.interrupt();
//                    this.lf.setFrame("end");
//                    return;
//                }
                }
                
                /*
                 * Sleep for 1000/144 ms (~6.9ms). This is done to have our 
                 * loop run at a fixed rate per/sec. 
                */

                /*
                 * simulate an end game event
                 * we will do this with by ending the game when ~8 seconds has passed.
                 * This will need to be changed since the will always close after 8 seconds
                 */
            }
        } catch (InterruptedException ignored) {
            System.out.println(ignored);
        }
    }

    /**
     * Reset game to its initial state.
     */
    public void resetGame() {
        this.tick = 0;
        this.t1.setX(300);
        this.t1.setY(300);
    }

    /**
     * Load all resources for Tank Wars Game. Set all Game Objects to their
     * initial state as well.
     */
    public void InitializeGame() {
        Resources.loadResourceMaps();
        this.world = new BufferedImage(GameConstants.WORLD_WIDTH,
                GameConstants.WORLD_HEIGHT,
                BufferedImage.TYPE_INT_RGB);

        t1 = new Tank(300, 300, 0, 0, (short) 0, Resources.getImage("tank1"));
        TankControl tc1 = new TankControl(t1, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_SPACE);
        t2 = new Tank(300, 300, 0, 0, (short) 0, Resources.getImage("tank2"));
        TankControl tc2 = new TankControl(t2, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_RIGHT, KeyEvent.VK_LEFT, KeyEvent.VK_ENTER);
        this.lf.getJf().addKeyListener(tc1);
        this.lf.getJf().addKeyListener(tc2);

        try (BufferedReader mapReader = new BufferedReader(new InputStreamReader(GameWorld.class.getClassLoader().getResourceAsStream("maps/map1.txt")))) {
            String[] size = mapReader.readLine().split(",");
            int numberOfRows = Integer.parseInt(size[0]);
            int numberOfColumns = Integer.parseInt(size[1]);

            for(int i = 0; mapReader.ready(); i++){
                String[] items = mapReader.readLine().split("");
                for (int j = 0; j < items.length; j++) {
                    switch (items[j]) {
                        case "3", "9" -> {
                            Wall w = new Wall(i * 30, j * 30, Resources.getImage("unbreak"));
                            walls.add(w);
                        }
                        case "2" -> {
                            Breakable bw = new Breakable(i * 30, j * 30, Resources.getImage("break1"));
                            walls.add(bw);
                        }
                        case "4" -> {
//                            ShieldPowerUp spu = new ShieldPowerUp( i * 30, j * 30, Resources.getImage("shield"));
//                            this.powerUps.add(spu);
                        } // powerups
                        case "5" -> {
//                            HealthPowerUp spu = new HealthPowerUp( i * 30, j * 30, Resources.getImage("health"));
//                            this.powerUps.add(hpu);
                        } // powerups
                        case "6" -> {
//                            SpeedPowerUp speed = new SpeedPowerUp( i * 30, j * 30, Resources.getImage("speed"));
//                            this.powerUps.add(speed);
                        } // powerups
                        case "7" -> {} // powerups
                        case "8" -> {} // powerups
                    }
                }
            }
        }catch(IOException e){
            System.out.println(e);
            System.exit(-2);
        }
    }


    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Graphics2D buffer = world.createGraphics();
        buffer.setColor(Color.BLACK);
        buffer.fillRect(0,0, GameConstants.WORLD_WIDTH, GameConstants.WORLD_HEIGHT);
        drawFloor(buffer);
        //this.powerUps.forEach(p -> p.drawImage(buffer));
        this.walls.forEach(w -> w.drawImage(buffer));
        this.t1.drawImage(buffer);
        this.t2.drawImage(buffer);
        drawSplitScreen(g2, world);
        drawMiniMap(g2, world);
    }

    void drawMiniMap(Graphics2D g2, BufferedImage world){
        BufferedImage mm = world.getSubimage(0, 0, GameConstants.WORLD_WIDTH - 360, GameConstants.WORLD_HEIGHT);
        AffineTransform at = new AffineTransform();
        at.translate(GameConstants.GAME_SCREEN_WIDTH/2f - (GameConstants.WORLD_WIDTH*.2)/2f, GameConstants.GAME_SCREEN_HEIGHT - GameConstants.WORLD_HEIGHT*.2 - 30);
        at.scale(.2,.2);
        g2.drawImage(mm, at, null);
    }
    void drawSplitScreen(Graphics2D g2, BufferedImage world){
        BufferedImage lh = world.getSubimage((int)this.t1.getScreenX(), (int)this.t1.getScreenY(), GameConstants.GAME_SCREEN_WIDTH/2, GameConstants.GAME_SCREEN_HEIGHT);
        BufferedImage rh = world.getSubimage((int)this.t2.getScreenX(), (int)this.t2.getScreenY(), GameConstants.GAME_SCREEN_WIDTH/2, GameConstants.GAME_SCREEN_HEIGHT);

        g2.drawImage(lh,0,0,null);
        g2.drawImage(rh, GameConstants.GAME_SCREEN_WIDTH/2,0,null);
    }
    void drawFloor(Graphics2D buffer){
        for (int i = 0; i < GameConstants.WORLD_WIDTH; i+=320) {
            for (int j = 0; j < GameConstants.WORLD_HEIGHT; j+=240) {
                buffer.drawImage(Resources.getImage("floor"),i,j, null);
            }
        }
    }

}

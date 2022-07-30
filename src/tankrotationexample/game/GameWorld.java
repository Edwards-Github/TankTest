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
                this.repaint();   // redraw game
                
                /*
                 * Sleep for 1000/144 ms (~6.9ms). This is done to have our 
                 * loop run at a fixed rate per/sec. 
                */
                Thread.sleep(1000 / 144);

                /*
                 * simulate an end game event
                 * we will do this with by ending the game when ~8 seconds has passed.
                 * This will need to be changed since the will always close after 8 seconds
                 */
//                if (this.tick >= 144 * 8) {
//                    t.interrupt();
//                    this.lf.setFrame("end");
//                    return;
//                }

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
                        case "4" -> {} // powerups
                        case "5" -> {} // powerups
                        case "6" -> {} // powerups
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
        walls.forEach(w -> w.drawImage(buffer));

        this.t1.drawImage(buffer);
        this.t2.drawImage(buffer);
        g2.drawImage(world, 0, 0, null);
//        BufferedImage lh = world.getSubimage((int)t1.getX(), (int)t1.getY(), GameConstants.GAME_SCREEN_WIDTH/2, GameConstants.GAME_SCREEN_HEIGHT);
//        g2.drawImage(lh,0,0, null);
        BufferedImage mm = world.getSubimage(0, 0, GameConstants.WORLD_WIDTH - 360, GameConstants.WORLD_HEIGHT);
        g2.scale(.2, .2);
        g2.drawImage(mm, 2000, 2000, null);
    }

}

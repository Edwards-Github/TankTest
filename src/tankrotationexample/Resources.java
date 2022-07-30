package tankrotationexample;

import tankrotationexample.game.GameWorld;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import java.awt.color.ICC_ColorSpace;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

import static javax.imageio.ImageIO.read;

public class Resources {
    private static Map<String, BufferedImage> images = new HashMap<>();
    private static Map<String, Clip> sounds = new HashMap<>();
    private static Map<String, List<BufferedImage>> animations = new HashMap();

    public static BufferedImage getImage(String key){ return Resources.images.get(key); }

    public static Clip getSound(String key){ return Resources.sounds.get(key); }

    public static List<BufferedImage> getAnimation(String key){ return Resources.animations.get(key); }

    public static void initImages(){

        try {
            Resources.images.put("tank1", read(Objects.requireNonNull(GameWorld.class.getClassLoader().getResource("tank/tank1.png"))));
            Resources.images.put("tank2", read(Objects.requireNonNull(GameWorld.class.getClassLoader().getResource("tank/tank2.png"))));
            Resources.images.put("floor", read(Objects.requireNonNull(GameWorld.class.getClassLoader().getResource("floor/bg.bmp"))));
            Resources.images.put("unbreak", read(Objects.requireNonNull(GameWorld.class.getClassLoader().getResource("walls/unbreak.jpg"))));
            Resources.images.put("break1", read(Objects.requireNonNull(GameWorld.class.getClassLoader().getResource("walls/break1.jpg"))));
            Resources.images.put("break2", read(Objects.requireNonNull(GameWorld.class.getClassLoader().getResource("walls/break2.jpg"))));
            Resources.images.put("title", read(Objects.requireNonNull(GameWorld.class.getClassLoader().getResource("menu/title.png"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void initSounds(){
        try {
            AudioInputStream as;
            Clip clip;

            as = AudioSystem.getAudioInputStream(Resources.class.getClassLoader().getResource("sounds/bullet.wav"));
            clip = AudioSystem.getClip();
            clip.open(as);
            Resources.sounds.put("bullet", clip);

            as = AudioSystem.getAudioInputStream(Resources.class.getClassLoader().getResource("sounds/Music.mid"));
            clip = AudioSystem.getClip();
            clip.open(as);
            Resources.sounds.put("music", clip);

        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            System.err.println(e);
            e.printStackTrace();
            System.exit(-2);
        }
    }
    public static void initAnimations(){
            try {
                String baseName = "expl_08_%04d.png";
                List<BufferedImage> temp = new ArrayList<>();
                for(int i =0; i < 5; i++) {
                    String fName = String.format(baseName, i);
                    String fullPath = "animations/bullet/" + fName;
                    temp.add(read(GameWorld.class.getClassLoader().getResource(fullPath)));
                }
                Resources.animations.put("bullet", temp);
                baseName = "expl_08_%04d.png";
                temp = new ArrayList<>();
                for(int i = 0; i < 7; i++){
                    String fName = String.format(baseName, i);
                    String fullPath = "animations/nuke/" + fName;
                    temp.add(read(GameWorld.class.getClassLoader().getResource(fullPath)));
                }
                Resources.animations.put("nuke", temp);
            } catch (IOException e) {
                System.out.println(e);
                System.exit(-2);
                //throw new RuntimeException(e);
            }
        }

    public static void loadResourceMaps() {
        Resources.initImages();
        Resources.initSounds();
    }
}

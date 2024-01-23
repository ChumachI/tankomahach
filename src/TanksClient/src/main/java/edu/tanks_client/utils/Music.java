package edu.tanks_client.utils;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.BufferedInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Music {

    private static  Clip menuMusic;
    private static  Clip battleMusic;

    private static  Clip finalMusic;
    private static final List<Clip> shootSoundPool = new ArrayList<>(Params.CLIP_POOL_SIZE);

    static {
        try {
            for (int i = 0; i < Params.CLIP_POOL_SIZE; i++) {
                Clip clip = AudioSystem.getClip();
                BufferedInputStream bufferedIn = new BufferedInputStream(Objects.requireNonNull(Music.class.getResourceAsStream("/music/shot.wav")));
                AudioInputStream inputStream = AudioSystem.getAudioInputStream(bufferedIn);
                clip.open(inputStream);
                shootSoundPool.add(clip);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void playMenuMusic() {
        try {

            BufferedInputStream bufferedIn = new BufferedInputStream(Objects.requireNonNull(Music.class.getResourceAsStream("/music/main_menu.wav")));
            AudioInputStream ais = AudioSystem.getAudioInputStream(bufferedIn);
            menuMusic = AudioSystem.getClip();
            menuMusic.open(ais);
            menuMusic.setFramePosition(0);
            menuMusic.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void playBattleMusic() {
        try {
            BufferedInputStream bufferedIn = new BufferedInputStream(Objects.requireNonNull(Music.class.getResourceAsStream("/music/battle.wav")));
            AudioInputStream ais = AudioSystem.getAudioInputStream(bufferedIn);
            battleMusic = AudioSystem.getClip();
            battleMusic.open(ais);
            battleMusic.setFramePosition(0);
            battleMusic.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void shoot() {
        Clip clip = getAvailableClip();
        clip.start();
    }

    private static Clip getAvailableClip() {
        for (Clip clip : shootSoundPool) {
            if (!clip.isRunning()) {
                clip.setMicrosecondPosition(0);
                return clip;
            }
        }
        Clip firstClip = shootSoundPool.getFirst();
        firstClip.stop();
        firstClip.setMicrosecondPosition(0);
        return firstClip;
    }

    public static void stopMenuMusic(){
        if(menuMusic != null && menuMusic.isRunning()){
            menuMusic.stop();
            menuMusic.close();
        }
    }

    public static void stopBattleMusic(){
        if(battleMusic != null && battleMusic.isRunning()){
            battleMusic.stop();
            battleMusic.close();
        }
    }

    public static void playVictory(){
        playFinalMusic("/music/victory.wav");
    }

    public static void playDefeat(){
        playFinalMusic("/music/defeat.wav");
    }

    private static void playFinalMusic(String filename){
        try {
            BufferedInputStream bufferedIn = new BufferedInputStream(Objects.requireNonNull(Music.class.getResourceAsStream(filename)));
            AudioInputStream ais = AudioSystem.getAudioInputStream(bufferedIn);
            battleMusic = AudioSystem.getClip();
            battleMusic.open(ais);
            battleMusic.setFramePosition(0);
            battleMusic.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void stop() {
        stopMenuMusic();
        stopBattleMusic();
        stopFinalMusic();
    }

    private static void stopFinalMusic() {
        if(finalMusic != null && finalMusic.isRunning()){
            finalMusic.stop();
            finalMusic.close();
        }
    }
}

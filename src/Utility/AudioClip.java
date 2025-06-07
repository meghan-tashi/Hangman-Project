package Utility;

import javax.sound.sampled.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Random;

public class AudioClip {

    public static final HashMap<String, AudioClip> AUDIO_CLIPS = new HashMap<>();
    private final HashMap<String, Clip> clips;
    private final Random random;
    private String currentPlaying;
    private Thread thread;

    public AudioClip() {
        clips = new HashMap<>();
        random = new Random();
    }

    public static void init() {
        addClips("Press", "chalk1", "chalk2");
        addClips("Over", "over");
    }

    public static AudioClip getAudioClip(String name, String... files) {
        AudioClip clip = AUDIO_CLIPS.get(name);

        if(clip == null)
            addClips(name, files);

        return AUDIO_CLIPS.get(name);
    }

    public static void addClips(String name, String... files) {
        AudioClip audioClip = AUDIO_CLIPS.get(name);
        if (audioClip == null) {
            audioClip = new AudioClip();
            AUDIO_CLIPS.put(name, audioClip);
        }

        for(String file : files) {
            try {
                if (audioClip.clips.containsKey(file))
                    return;

                InputStream is = AudioClip.class.getResourceAsStream("/audio/" + file + ".wav");
                System.out.println(is);
                assert is != null;
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(is);
                DataLine.Info info = new DataLine.Info(Clip.class, audioInputStream.getFormat());
                Clip clip = (Clip) AudioSystem.getLine(info);

                clip.open(audioInputStream);
                audioClip.clips.put(file, clip);

            } catch (IOException | LineUnavailableException | UnsupportedAudioFileException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void play() {
        play(getRandom());
    }

    private void resetClip(Clip clip) {
        clip.stop();
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        clip.setFramePosition(0);
        clip.setMicrosecondPosition(0);
        clip.start();
        thread = null;
    }

    public void play(String file) {
        Clip clip = clips.get(file);
        if(clip.isRunning()) {
            if(thread != null)
                return;

            thread = new Thread(() -> resetClip(clip));
            thread.start();
            return;
        }

        clip.setFramePosition(0);
        clip.setMicrosecondPosition(0);
        clip.start();
        currentPlaying = file;
    }

    private String getRandom() {
        int r = random.nextInt(clips.size());
        return (String) clips.keySet().toArray()[r];
    }

    public void loop(int count) {
        loop(getRandom(), count);
    }

    public void loop(String file, int count) {
        clips.get(file).loop(count);
        currentPlaying = file;
    }

    public void stop(String file) {
        clips.get(file).stop();
        clips.get(file).setFramePosition(0);
        clips.get(file).flush();
    }

    public void stop() {
        if(currentPlaying.isEmpty())
            return;
        stop(currentPlaying);
        currentPlaying = "";
    }

    public boolean isPlaying() {
        return currentPlaying != null && clips.get(currentPlaying).isRunning();
    }

}
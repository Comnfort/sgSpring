package ru.game.sg.spaceGame;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sound.sampled.*;
import java.io.IOException;
import java.util.Objects;


@Component
class Sound {
    private Main fr;

    @Autowired
    public Sound(Main fr) {
    }



    public void runSound(String pathSound) {
        String path = pathSound;
        try (AudioInputStream ais = AudioSystem.getAudioInputStream(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream(path)))) {                  //try with source
            Clip c = AudioSystem.getClip();
            c.open(ais);
            c.loop(0);
            c.addLineListener(event -> {
                if (event.getFramePosition() == c.getFrameLength()) {
                    c.close();
                }
            });
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
    }
}
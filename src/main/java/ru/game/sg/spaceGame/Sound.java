package ru.game.sg.spaceGame;

import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.sound.sampled.*;
import java.io.IOException;
import java.util.Objects;


@Component
@Scope("prototype")
@Lazy
class Sound{
    public Sound(GameField gf) {
        String path=gf.getPathToSound();
        try (AudioInputStream ais = AudioSystem.getAudioInputStream(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream(path)))) {                  //try with source
            Clip c = AudioSystem.getClip();
            c.open(ais);
            c.loop(0);
            c.addLineListener(event -> {if (event.getFramePosition()== c.getFrameLength()) { c.close();}});
        }
        catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
    }
}
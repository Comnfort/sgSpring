package ru.game.sg.spaceGame;


import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public enum Control
{
    LEFT,RIGHT,UP,DOWN, LEFT_ROTATE, RIGHT_ROTATE;

    private boolean pressed =false;

    public boolean isPressed() {
        return pressed;
    }

    public void setPressed(boolean pressed) {
        this.pressed = pressed;
    }
}

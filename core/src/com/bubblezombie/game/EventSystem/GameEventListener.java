package com.bubblezombie.game.EventSystem;


import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.bubblezombie.game.Bubbles.Bubble;
import com.bubblezombie.game.Bubbles.BubbleColor;
import com.bubblezombie.game.Bubbles.BubbleType;
import com.bubblezombie.game.EventSystem.GameEvent.Type;
import com.bubblezombie.game.Util.Scene2dSprite;

import java.util.ArrayList;

/**
 * Created by Alex on 28/12/15.
 */
public class GameEventListener implements EventListener {

    // return true means we handled event
    public boolean handle (Event e) {
        if (!(e instanceof GameEvent)) return false;
        GameEvent event = (GameEvent)e;

        switch (event.getType()) {
            case ALL_ENEMIES_KILLED:
                allEnemiesKilled(event);
                break;
            case COMBO:
                combo(event, event.getKilledBubbles());
                break;
            case END_FLY:
                endFly(event);
                break;
            case END_LAST_WAVE:
                endLastWave(event);
                break;
            case NEW_ROW:
                newRow(event);
                break;
            case LAST_WAVE:
                lastWave(event);
                break;
            case BUBBLE_CONNECTED:
                bubbleConnected(event, event.getConnectedBubble());
                break;
            case SHOOT:
                shoot(event, event.getNextBubble(), event.getNowShootedBubble());
                break;
            case MOVED:
                moved(event, event.getAngle());
                break;
        }

        return true;
    }

    // general
    public void allEnemiesKilled(GameEvent event) {}

    // combo
    public void combo(GameEvent event, ArrayList<Bubble> killedBubbles) {}

    // airplane
    public void endFly(GameEvent event) {}
    public void endLastWave(GameEvent event) {}

    // mesh
    public void newRow(GameEvent event) {}
    public void lastWave(GameEvent event) {}
    public void bubbleConnected(GameEvent event, Bubble connectedBubble) {}

    // gun
    public void shoot(GameEvent event, Bubble nextBubble, Bubble nowShootedBubble) {}
    public void moved(GameEvent event, int angle) {}
}

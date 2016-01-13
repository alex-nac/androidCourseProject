package com.bubblezombie.game.EventSystem;

import com.badlogic.gdx.scenes.scene2d.Event;
import com.bubblezombie.game.Bubbles.Bubble;
import com.bubblezombie.game.Bubbles.BubbleColor;
import com.bubblezombie.game.Bubbles.BubbleType;
import com.bubblezombie.game.Bubbles.ColorBomb;
import com.bubblezombie.game.Bubbles.SimpleBubble;
import com.bubblezombie.game.Util.Scene2dSprite;

import java.util.ArrayList;

/**
 * Created by Alex on 28/12/15.
 * General event system
 * Long story short: I decided not to divide events in many classes
 * There is only one class handling all the game events
 */
public class GameEvent extends Event {

    private Type _type;

    // combo event data
    private ArrayList<Bubble> _killedBubbles;

    // mesh events data
    private Bubble _connectedBubble;

    // gun events data
    private Bubble _nextBubble, _nowShootedBubble;
    private int _angle;

    // getters
    public Type getType() { return _type; }
    public ArrayList<Bubble> getKilledBubbles() { return _killedBubbles; };
    public Bubble getConnectedBubble() { return _connectedBubble; }
    public Bubble getNextBubble() { return _nextBubble; }
    public Bubble getNowShootedBubble() { return _nowShootedBubble; }
    public int getAngle() { return _angle; }

    // general
    public GameEvent(Type type) throws IncorrentGameEventDataException {
        if (type == Type.COMBO || type == Type.SHOOT || type == Type.MOVED || type == Type.BUBBLE_CONNECTED)
            throw new IncorrentGameEventDataException("Not enough data provided for this kind of event");

        _type = type;
    }

    // combo
    public GameEvent(Type type, ArrayList<Bubble> killedBubbles) throws IncorrentGameEventDataException {
        if (type != Type.COMBO)
            throw new IncorrentGameEventDataException("Combo event overload, when you are using " + type.toString() + "event ctor");

        _type = type;
        _killedBubbles = killedBubbles;
    }

    // mesh
    public GameEvent(Type type, Bubble bubble) throws IncorrentGameEventDataException{ // connected_bubble
        if (type != Type.BUBBLE_CONNECTED)
            throw new IncorrentGameEventDataException("Bubble connected event overload, when you are using " + type.toString() + "event ctor");
        _type = type;
        _connectedBubble = bubble;
    }

    // gun
    public GameEvent(Type type, Bubble nextBullet, Bubble nowShootedBubble) throws IncorrentGameEventDataException { // shoot
        if (type != Type.SHOOT)
            throw new IncorrentGameEventDataException("Shoot event overload, when you are using " + type.toString() + "event ctor");
        _type = type;
        _nextBubble = nextBullet;
        _nowShootedBubble = nowShootedBubble;
    }

    public GameEvent(Type type, int angle) throws IncorrentGameEventDataException { // moved
        if (type != Type.MOVED)
            throw new IncorrentGameEventDataException("Gun moved event overload, when you are using " + type.toString() + "event ctor");

        _type = type;
        _angle = angle;
    }

    // event types
    static public enum Type {
        // general
        ALL_ENEMIES_KILLED,

        // combo
        COMBO,

        // airplane
        END_FLY,
        END_LAST_WAVE,

        // mesh
        NEW_ROW,
        LAST_WAVE,
        BUBBLE_CONNECTED,

        // gun
        SHOOT,
        MOVED;
    }
}

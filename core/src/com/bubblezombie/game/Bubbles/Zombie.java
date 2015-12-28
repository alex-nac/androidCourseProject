package com.bubblezombie.game.Bubbles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Timer;
import com.bubblezombie.game.BubbleZombieGame;
import com.bubblezombie.game.Util.Scene2dSprite;

/**
 * Created by artem on 02.12.15.
 */
public class Zombie extends SimpleBubble {

    private static final String pink = "game/bubbles/zombie_pink.png";
    private static final String black = "game/bubbles/zombie_black.png";
    private static final String blue = "game/bubbles/zombie_blue.png";
    private static final String green = "game/bubbles/zombie_green.png";
    private static final String purple = "game/bubbles/zombie_purple.png";
    private static final String red = "game/bubbles/zombie_red.png";
    private static final String yellow = "game/bubbles/zombie_yellow.png";

    public boolean canOverlay = true;
    private final int NORMAL_ZM = 1;
    private final int EVIL1_ZM = 2;
    private final int  EVIL2_ZM = 3;
    private final int EVIL3_ZM = 4;

    //help value for creating prefab


    //private var _tw:GTween; 						//current animation tween
    private int _currentMode = 0;
    private Scene2dSprite _currAnim;				//current animation playing
    private int _repeatCnt = 1;					//sometime we play animation several times, this is how much times elapsed
    private int _infectionState = 0; 			//how much zombie infected by steam machine
    private boolean _animationActive = true;	//if we play animation for this zombie or not
    private Timer _delayTimer;
//    private var _flickTween:GTween;

    private Scene2dSprite m_MC;

    public Zombie(BubbleColor color) {
        super(color);
        SetView();
    }

    public Zombie() {
        this(BubbleColor.NONE);
    }

    @Override
    public void setColor(BubbleColor newColor) {
        _color = newColor;
        SetView();
    }

//    public override function Update():void {
//        if (_currAnim.currentFrame == _currAnim.totalFrames) SetNextAnimation();
//        if (_delayTimer) _delayTimer.Update();
//        super.Update();
//    }


    private void SetView() {

        m_MC = GetBubbleImage();

        setView(m_MC);

        setScale( DIAMETR / m_MC.getWidth());

        m_MC.setHeight(DIAMETR);
        m_MC.setWidth(DIAMETR);
//        m_MC.setWidth(getScale() * m_MC.getWidth());
//        m_MC.setHeight(getScale() * m_MC.getHeight());

//        if (_color == SimpleBubble.UBER_BLACK) {
//            var axe:axe_mc = new axe_mc();
//            axe.width *= scale;
//            axe.height *= scale;
//            _effects.addChild(new axe_mc());
//        }

    }


    @Override
    public Scene2dSprite GetBubbleImage() {
        Scene2dSprite bubbleMC = null;
        if (!BubbleZombieGame.INSTANCE.assetManager.isLoaded(pink)) {
            // ОСТОРОЖНО ГОВНОКОД
            BubbleZombieGame.INSTANCE.assetManager.load(pink, Texture.class);
            BubbleZombieGame.INSTANCE.assetManager.load(black, Texture.class);
            BubbleZombieGame.INSTANCE.assetManager.load(blue, Texture.class);
            BubbleZombieGame.INSTANCE.assetManager.load(green, Texture.class);
            BubbleZombieGame.INSTANCE.assetManager.load(purple, Texture.class);
            BubbleZombieGame.INSTANCE.assetManager.load(red, Texture.class);
            BubbleZombieGame.INSTANCE.assetManager.load(yellow, Texture.class);
            BubbleZombieGame.INSTANCE.assetManager.finishLoading();
        }

        switch(_color) {
            case PINK:
                bubbleMC = new Scene2dSprite(BubbleZombieGame.INSTANCE.assetManager.get(pink, Texture.class));
                break;
            case YELLOW:
                bubbleMC = new Scene2dSprite(BubbleZombieGame.INSTANCE.assetManager.get(yellow, Texture.class));
                break;
            case RED:
                bubbleMC = new Scene2dSprite(BubbleZombieGame.INSTANCE.assetManager.get(red, Texture.class));
                break;
            case GREEN:
                bubbleMC = new Scene2dSprite(BubbleZombieGame.INSTANCE.assetManager.get(green, Texture.class));
                break;
            case BLUE:
                bubbleMC = new Scene2dSprite(BubbleZombieGame.INSTANCE.assetManager.get(blue, Texture.class));
                break;
            case VIOLETT:
                bubbleMC = new Scene2dSprite(BubbleZombieGame.INSTANCE.assetManager.get(purple, Texture.class));
                break;
            case UBER_BLACK:
                bubbleMC = new Scene2dSprite(BubbleZombieGame.INSTANCE.assetManager.get(black, Texture.class));
                break;
        }

        return bubbleMC;
    }
//
//    public function Infect():void {
//        if (isFrozen) return; //if it is frozened illnes doesn't affect to it
//
//        _infectionState++;
//        if (_flickTween) {
//            _flickTween.beginning();
//            return;
//        }
//
//
//        //else flick it a bit and slow brightness by 20%
//        _flickTween = new GTween(view, 0.05, { alpha:0 }, { reflect:true, repeatCount:8 });
//        _flickTween.onComplete = function(e:GTween):void {
//            _flickTween.onComplete = null;
//            _flickTween = null;
//
//            var colorTransform:ColorTransform = view.transform.colorTransform;
//
//            //if it is the last state of infection turn zombie to uber
//            if (_infectionState == 3) {
//                color = SimpleBubble.UBER_BLACK;
//
//                //set brightness to 1
//                colorTransform.redMultiplier = 1
//                colorTransform.greenMultiplier = 1
//                colorTransform.blueMultiplier = 1
//            }
//            else {
//                colorTransform.redMultiplier *= 0.8;
//                colorTransform.greenMultiplier *= 0.8;
//                colorTransform.blueMultiplier *= 0.8;
//            }
//
//            view.transform.colorTransform = colorTransform;
//
//        }
//
//    }
//
//    override public function Delete(withPlane:Boolean = false):void {
//        var deathAnim:Animation = new Animation(new death_animation_mc(), 0.6 * scale);
//        deathAnim.x = x;
//        deathAnim.y = y;
//        _mesh.AddEffect(deathAnim);
//
//        Main.SM.PlaySound(new shot_02_snd());
//
//        if (withPlane) var score:String = Score.AIRPLANE_SCORE.toString();
//        else if (color != UBER_BLACK) score = Score.BASIC_SCORE.toString();
//        else score = Score.UBER_SCORE.toString();
//        var floatingText:FloatingText = new FloatingText(score, new point_mc(), 13);
//        floatingText.x = view.x;
//        floatingText.y = view.y - 15;
//        if (_mesh) _mesh.AddEffect(floatingText, true);
//
//        _currAnim.stop();
//        if (_flickTween) _flickTween.end();
//
//        super.Delete(withPlane);
//    }
//
//    //handling game pause/resume
//    override public function onGameStateChanged(e:Event):void {
//        if (e.type == State.PAUSE) _currAnim.stop();
//        else if (_animationActive && !isFrozen) _currAnim.play();
//
//        super.onGameStateChanged(e);
//    }
//
}

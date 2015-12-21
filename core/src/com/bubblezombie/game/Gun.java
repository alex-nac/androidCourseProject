package com.bubblezombie.game;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Timer;
import com.bubblezombie.game.Bubbles.Bubble;
import com.bubblezombie.game.Util.GameConfig;
import com.bubblezombie.game.Util.Scene2dSprite;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;

/**
 * Created by Alex on 20/12/15.
 *
 * Class for SWAT gun-machine
 * We always have 2 bullet - one in a basket
 * and one in the gun
 *
 */

public class Gun {

    private static final String RES_GUN_DOWN = "game/gun_down.png";
    private static final String RES_GUN_TOP = "game/gun_top.png";

    //private static final int GUN_LENGTH = 34;
    private static final int SHOOTING_VEL = 300;
    private static final float BULLET_DIAMETR = 27.0f;
    private static final float SHOOTING_DELAY = 0.2f;
    private static final int VIEW_X = BubbleZombieGame.width / 2;
    private static final int VIEW_Y = -13;
    private static final int GUN_X = 13;
    private static final int GUN_Y = 32;
    private static final int TOP_GUN_X = 27;
    private static final int TOP_GUN_Y = -7;



    private Group _view = new Group();
    private Scene2dSprite _gun;
    private Group _bulletPlace = new Group();
    private Body _gunBody;
    private float _angle = 0;               //angle of gun's rotation
    private Group _nextBulletSprite;
    private GameConfig _cfg;
    private World _space;
    private Boolean _canShoot = true;
    private Timer _canShootTimer;           //timer for delay in shooting
    private Boolean _repeatBulletsEnabled;
    private BubbleMesh _mesh;

    //2 bullets
    private Bubble _nextBullet;
    private Bubble _basketBullet;

    public Group getView() { return _view; }
    public void setCanShoot(Boolean value) {
        _canShoot = value;
        // TODO: здесь какая-то херня - это нужно чекнуть
        startCanShootTimer();
    }

    public Gun(GameConfig cfg, World space, Boolean repeateBulletsEnabled, BubbleMesh mesh) {
        BubbleZombieGame.INSTANCE.assetManager.load(RES_GUN_DOWN, Texture.class);
        BubbleZombieGame.INSTANCE.assetManager.load(RES_GUN_TOP, Texture.class);
        BubbleZombieGame.INSTANCE.assetManager.finishLoading();

        _mesh = mesh;
        _repeatBulletsEnabled = repeateBulletsEnabled;
        _space = space;

        _gun = new Scene2dSprite(BubbleZombieGame.INSTANCE.assetManager.get(RES_GUN_DOWN, Texture.class));
        _gun.addActor(_bulletPlace);
        Image topGun = new Image(BubbleZombieGame.INSTANCE.assetManager.get(RES_GUN_TOP, Texture.class));
        topGun.setPosition(TOP_GUN_X, TOP_GUN_Y);
        _gun.addActor(topGun);
        _gun.setX(GUN_X);
        _gun.setY(GUN_Y);
        _view.addActor(_gun);

        _cfg = cfg;
        _view.setPosition(VIEW_X, VIEW_Y);

        //_gunBody = new Body(BodyType.KINEMATIC, new Vec2(_view.x + _gun.x + 1, _view.y + _gun.y));
        //_gunBody.shapes.add(new Polygon(Polygon.box(_gun.width * 2, _gun.height / 4)));
        //_gunBody.shapes.at(0).sensorEnabled = true;
        //_gunBody.space = space;

        //bullet in a basket
        PutBullet();

        //bullet in a gun
        //_nextBullet = GetNextBullet();
        //_nextBullet.getView().setAlpha(0);
        //_gun.downgun.addChild(_nextBullet.view);
        float scale = BULLET_DIAMETR / Bubble.DIAMETR;
        //_nextBullet.getView().setScale(scale);
        //_nextBullet.setX(-4);
        //_nextBullet.getView().addAction(fadeIn(0.4f));

        // pause shooting timer
        _canShootTimer = new Timer();

        //dispatchEvent( new GunEvent(GunEvent.SHOOT, _nextBullet));
    }

    // rotate gun
    public void setGunRotation(float degrees) {
        _gun.setRotation(degrees);

        //_gunBody.setTransform(0, 0, _angle);
        //dispatchEvent(new GunEvent(GunEvent.MOVED, null, _angle * 180 / Math.PI));
    }

    // after shooting a bullet user should wait some time to shoot another one
    private void startCanShootTimer() {
        _canShootTimer.schedule(new Timer.Task() {
            @Override
            public void run() { _canShoot = true; }
        }, SHOOTING_DELAY);
    }

    //putting new bullet to the basket
    private void PutBullet() { }

    private Bubble GetNextBullet() { return null; }
}

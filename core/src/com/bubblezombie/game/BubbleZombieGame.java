package com.bubblezombie.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bubblezombie.game.Bubbles.Bubble;
import com.bubblezombie.game.Screen.MainMenuScreen;
import com.bubblezombie.game.Util.FontFactory;

public class BubbleZombieGame extends Game {
	public static final int width = 640;
	public static final int height = 480;
	public static FontFactory factory = null;

	SpriteBatch batch;
	Texture img;
	
	@Override
	public void create () {
		BubbleZombieGame.factory = new FontFactory("fonts/EuropeExt_Bold.ttf");
		setScreen(new MainMenuScreen(this));
	}
}

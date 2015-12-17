package com.bubblezombie.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bubblezombie.game.Screen.MainMenuScreen;
import com.bubblezombie.game.Util.Factory.FontFactory;
import com.bubblezombie.game.Util.Managers.SaveManager;

public class BubbleZombieGame extends Game {
	public static final int width = 640;
	public static final int height = 480;
	public static AssetManager assetManager;
	SpriteBatch batch;
	Texture img;
	
	@Override
	public void create () {
		assetManager = new AssetManager(new InternalFileHandleResolver());
		SaveManager.initialize("data.save");
		FontFactory.initialize("fonts/EuropeExt_Bold.ttf", "fonts/EuropeExt_Bold.ttf");
		setScreen(new MainMenuScreen(this));
	}
}

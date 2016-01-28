package com.bubblezombie.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.bubblezombie.game.Screen.GameScreen;
import com.bubblezombie.game.Screen.LevelCompleteScreen;
import com.bubblezombie.game.Screen.MainMenuScreen;
import com.bubblezombie.game.Screen.TestScreen;
import com.bubblezombie.game.Social.Shareable;
import com.bubblezombie.game.Util.Factory.ButtonFactory;
import com.bubblezombie.game.Util.Factory.FontFactory;
import com.bubblezombie.game.Util.GameConfig;
import com.bubblezombie.game.Util.LevelContainer;
import com.bubblezombie.game.Util.Managers.SaveManager;

public class BubbleZombieGame extends Game {
	public static final int width = 640;
	public static final int height = 480;
	public static Shareable VkSharingSender;

	public static int score = 765;

	public static LevelContainer LVLC = new LevelContainer();
	public static final BubbleZombieGame INSTANCE = new BubbleZombieGame();

	public AssetManager assetManager;

	public void setVkSender(Shareable vkSender) {
		VkSharingSender = vkSender;
	}

	@Override
	public void create () {
		Gdx.input.setCatchBackKey(true);
		assetManager = new AssetManager(new InternalFileHandleResolver());
		SaveManager.initialize("data.save");
		GameConfig.initialize();
		LevelContainer.initialize();
		FontFactory.initialize("fonts/EuropeExt_Bold.ttf", "fonts/EuropeExt_Bold.ttf");
		ButtonFactory.initialize(this);
		Box2D.init();
		setScreen(new MainMenuScreen(this));
		//setScreen(new TestScreen(this));
		//setScreen(new LevelCompleteScreen(this, 1));
	}
}

package com.bubblezombie.game.android;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.bubblezombie.game.BubbleZombieGame;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;

public class AndroidLauncher extends AndroidApplication {
	private static final String TAG = "Launcher";

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApp.Vk.setActivity(this);
		BubbleZombieGame.INSTANCE.setVkSender(AndroidApp.Vk);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(BubbleZombieGame.INSTANCE, config);
	}



	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.d(TAG, "RESULT");
		AndroidApp.Vk.onResult(requestCode, resultCode, data);
	}

}

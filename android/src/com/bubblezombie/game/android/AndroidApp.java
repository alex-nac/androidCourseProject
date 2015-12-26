package com.bubblezombie.game.android;

import android.app.Application;
import android.os.Bundle;

import com.bubblezombie.game.BubbleZombieGame;
import com.bubblezombie.game.Social.Shareable;
import com.vk.sdk.VKSdk;

/**
 * Created by artem on 26.12.15.
 */
public class AndroidApp extends Application {
    public static VkShare Vk;
    @Override
    public void onCreate() {
        super.onCreate();
        VKSdk.initialize(this);
        Vk = new VkShare(this.getApplicationContext());
    }
}

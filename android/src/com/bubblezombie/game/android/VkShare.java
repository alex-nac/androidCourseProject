package com.bubblezombie.game.android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.bubblezombie.game.Social.Shareable;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKScopes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class VkShare implements Shareable {
    private static final String TAG = "VkShare";

    public VKAccessToken token;
    public Context applicationContext;
    public Activity attachedActivity;
    private JSONObject response;
    int score;

    static final String key = "n9G1XSpzE2nBFttfwGdF";

    public VkShare(Context context) {
        applicationContext = context;
    }

    public void setActivity(Activity activity) {
        this.attachedActivity = activity;
    }

    @Override
    public void postScore(int score) {
        this.score = score;
        loginVk();
    }

    private void loginVk() {
        Gdx.app.log(TAG, "logging in");
        VKSdk.login(attachedActivity, VKScopes.STATS, VKScopes.WALL);
//        VKAccessToken token = VKAccessToken.tokenFromSharedPreferences(attachedActivity, key);
//        if (token != null) {
//            Log.d(TAG, "using saved token");
//            onLoggedIn(token);
//        } else {
//            Log.d(TAG, "token is missing, performing login...");
//            VKSdk.login(attachedActivity, VKScopes.STATS, VKScopes.WALL, VKScopes.AUDIO);
//        }
    }

    public void onResult(int requestCode, int resultCode, Intent data) {
        VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken token) {
                onLoggedIn(token);
            }

            @Override
            public void onError(VKError error) {
                onLoginFailed(error);
            }
        });
    }

    protected void onLoggedIn(VKAccessToken token) {
        Log.d(TAG, "successfully logged in");
        VKRequest request = new VKRequest("users.get", VKParameters.from("fields", "first name,last name"));
        performRequest(request);
        int id = 0;
        try {
            JSONArray ar = response.getJSONArray("response");
            JSONObject o = (JSONObject) ar.get(0);
            id = o.getInt("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        request = new VKRequest("wall.post", VKParameters.from(
                "owner_id", id,
                "friends_only", 0,
                "message", "i've reached " + score + " points in Buuble Zombie! Try to beat my score!"));
        performRequest(request);
    }

    protected void onLoginFailed(VKError error) {
        Log.d(TAG, "login failed");
    }

    protected void performRequest(VKRequest request) {
        request.executeSyncWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);
                VkShare.this.response = response.json;
                Log.d(TAG, response.json.toString());
                Log.d(TAG, "request completed");
            }

            @Override
            public void onError(VKError error) {
                super.onError(error);
                Log.d(TAG, error.toString());
            }
        });
    }
}

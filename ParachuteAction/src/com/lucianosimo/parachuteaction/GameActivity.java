package com.lucianosimo.parachuteaction;

import java.io.IOException;

import org.andengine.engine.Engine;
import org.andengine.engine.LimitedFPSEngine;
import org.andengine.engine.camera.BoundCamera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.input.sensor.acceleration.AccelerationData;
import org.andengine.input.sensor.acceleration.IAccelerationListener;
import org.andengine.ui.activity.BaseGameActivity;

import android.view.KeyEvent;

import com.chartboost.sdk.Chartboost;
import com.lucianosimo.parachuteaction.manager.ResourcesManager;
import com.lucianosimo.parachuteaction.manager.SceneManager;

public class GameActivity extends BaseGameActivity implements IAccelerationListener{

	private BoundCamera camera;
	public static float mGravityX = 0;
	private final static float SPLASH_DURATION = 5f;
	private Chartboost cb;
	
	@Override
	public EngineOptions onCreateEngineOptions() {
		
		// Configure Chartboost
		this.cb = Chartboost.sharedChartboost();
		String appId = "53c57c8289b0bb3697c25124";
		String appSignature = "3f0a28521b32648044a33f149570570df81c89c6";
		this.cb.onCreate(this, appId, appSignature, null);
		
		camera = new BoundCamera(0, 0, 480, 854);
		EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED, new FillResolutionPolicy(), this.camera);
		engineOptions.getAudioOptions().setNeedsMusic(true).setNeedsSound(true);
		engineOptions.setWakeLockOptions(WakeLockOptions.SCREEN_ON);
		return engineOptions;
	}
	
	
	@Override
	protected void onPause() {
		super.onPause();
		mEngine.getSoundManager().setMasterVolume(0);
		mEngine.getMusicManager().setMasterVolume(0);
	}
	
	@Override
	protected synchronized void onResume() {
		super.onResume();
		mEngine.getSoundManager().setMasterVolume(1);
		mEngine.getMusicManager().setMasterVolume(1);
	}

	@Override
	public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback)	throws IOException {
		ResourcesManager.prepareManager(mEngine, this, camera, getVertexBufferObjectManager());
		pOnCreateResourcesCallback.onCreateResourcesFinished();
	}

	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback)	throws IOException {
		SceneManager.getInstance().createSplashScene(pOnCreateSceneCallback);		
	}

	@Override
	public void onPopulateScene(Scene pScene, OnPopulateSceneCallback pOnPopulateSceneCallback) throws IOException {
		mEngine.registerUpdateHandler(new TimerHandler(SPLASH_DURATION, new ITimerCallback() {
			
			@Override
			public void onTimePassed(TimerHandler pTimerHandler) {
				mEngine.unregisterUpdateHandler(pTimerHandler);
				SceneManager.getInstance().createMenuScene();
			}
		}));
		pOnPopulateSceneCallback.onPopulateSceneFinished();
	}
	
	@Override
	public Engine onCreateEngine(EngineOptions pEngineOptions) {
		return new LimitedFPSEngine(pEngineOptions, 60);
	}
	
	@Override
	protected void onDestroy() {
		this.cb.onDestroy(this); 
		super.onDestroy();
		System.exit(0);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			SceneManager.getInstance().getCurrentScene().onBackKeyPressed();
		}
		return false;
	}


	@Override
	public void onAccelerationAccuracyChanged(AccelerationData pAccelerationData) {
		
	}
	
	@Override
	protected void onStart() {
		super.onStart();
        this.cb.onStart(this);
        //this.cb.startSession();
        //this.cb.showInterstitial();
        this.cb.cacheInterstitial();
	}
	
	@Override
	public void onBackPressed() {
	    if (this.cb.onBackPressed())
	    	return;
	    else
	    	super.onBackPressed();
	}
	
	public void showAd() {
		/*if (this.cb.hasCachedInterstitial("map")) {
			Log.e("parachute", "hasCached: " + this.cb.hasCachedInterstitial());
			this.cb.showInterstitial();
		} else {
			Log.e("parachute", "no cached");
			this.cb.cacheInterstitial("map");
			this.cb.showInterstitial("map");
		}*/
		this.cb.showInterstitial();
	}

	@Override
	protected void onStop() { 
	    super.onStop();
	    this.cb.onStop(this); 
	}

	@Override
	public void onAccelerationChanged(AccelerationData pAccelerationData) {
		GameActivity.mGravityX = pAccelerationData.getX()*5;
		
	}
	
	@Override
    public void onResumeGame() {
            super.onResumeGame();
            this.enableAccelerationSensor(this);
    }

    @Override
    public void onPauseGame() {
            super.onPauseGame();
            this.disableAccelerationSensor();
    }


}

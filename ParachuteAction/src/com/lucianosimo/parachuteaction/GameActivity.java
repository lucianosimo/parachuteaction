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

import android.os.Bundle;
import android.view.KeyEvent;

import com.chartboost.sdk.CBLocation;
import com.chartboost.sdk.Chartboost;
import com.lucianosimo.parachuteaction.manager.ResourcesManager;
import com.lucianosimo.parachuteaction.manager.SceneManager;

public class GameActivity extends BaseGameActivity implements IAccelerationListener{

	private BoundCamera camera;
	public static float mGravityX = 0;
	private final static float SPLASH_DURATION = 7f;
	String appId = "53c57c8289b0bb3697c25124";
	String appSignature = "3f0a28521b32648044a33f149570570df81c89c6";
	
	@Override
	protected void onCreate(Bundle pSavedInstanceState) {
		super.onCreate(pSavedInstanceState);
		Chartboost.startWithAppId(this, appId, appSignature);
	    Chartboost.onCreate(this);
	}
	
	@Override
	public EngineOptions onCreateEngineOptions() {
		camera = new BoundCamera(0, 0, 720, 1280);
		EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED, new FillResolutionPolicy(), this.camera);
		engineOptions.getAudioOptions().setNeedsMusic(true).setNeedsSound(true);
		engineOptions.getRenderOptions().setDithering(true);
		engineOptions.setWakeLockOptions(WakeLockOptions.SCREEN_ON);
		return engineOptions;
	}
	
	
	@Override
	protected void onPause() {
		super.onPause();
		Chartboost.onPause(this);
		SceneManager.getInstance().getCurrentScene().handleOnPause();
		mEngine.getSoundManager().setMasterVolume(0);
		mEngine.getMusicManager().setMasterVolume(0);
	}
	
	@Override
	protected synchronized void onResume() {
		super.onResume();
		Chartboost.onResume(this);
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
		super.onDestroy();
		Chartboost.onDestroy(this);
		System.exit(0);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (Chartboost.onBackPressed()) {
				return false;
			} else {
				SceneManager.getInstance().getCurrentScene().onBackKeyPressed(); 
			}			
		}
		return false;
	}


	@Override
	public void onAccelerationAccuracyChanged(AccelerationData pAccelerationData) {
		
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

    @Override
	protected void onStart() {
		super.onStart();
		Chartboost.onStart(this);
		Chartboost.cacheInterstitial(CBLocation.LOCATION_DEFAULT);
	}
    
    public void cacheAd() {
    	Chartboost.cacheInterstitial(CBLocation.LOCATION_DEFAULT);
    }

	@Override
	protected void onStop() {
		super.onStop();
		Chartboost.onStop(this);
	}
	
	public void showAd() {
		Chartboost.showInterstitial(CBLocation.LOCATION_DEFAULT);
	}
	
	/*private ChartboostDelegate chartBoostDelegate = new ChartboostDelegate() {

		@Override
		public boolean shouldDisplayInterstitial(String location) {
			//Log.i("parachute", "SHOULD DISPLAY INTERSTITIAL '"+location+ "'?");
			return true;
		}

		@Override
		public boolean shouldRequestInterstitial(String location) {
			//Log.i("parachute", "SHOULD REQUEST INSTERSTITIAL '"+location+ "'?");
			return true;
		}

		@Override
		public void didCacheInterstitial(String location) {
			Log.i("parachute", "INTERSTITIAL '"+location+"' CACHED");
		}

		@Override
		public void didFailToLoadInterstitial(String location, CBImpressionError error) {
		    // Show a house ad or do something else when a chartboost interstitial fails to load
			
			Log.i("parachute", "INTERSTITIAL '"+location+"' REQUEST FAILED - " + error.name());
			//Toast.makeText(GameActivity.this, "Interstitial '"+location+"' Load Failed",	Toast.LENGTH_SHORT).show();
		}

		@Override
		public void didDismissInterstitial(String location) {
			
			// Immediately re-caches an interstitial
			cb.cacheInterstitial(location);
			
			//Log.i("parachute", "INTERSTITIAL '"+location+"' DISMISSED");
			//Toast.makeText(GameActivity.this, "Dismissed Interstitial '"+location+"'",	Toast.LENGTH_SHORT).show();
		}

		@Override
		public void didCloseInterstitial(String location) {
			//Log.i("parachute", "INSTERSTITIAL '"+location+"' CLOSED");
			//Toast.makeText(GameActivity.this, "Closed Interstitial '"+location+"'", Toast.LENGTH_SHORT).show();
		}

		@Override
		public void didClickInterstitial(String location) {
			//Log.i("parachute", "DID CLICK INTERSTITIAL '"+location+"'");
			//Toast.makeText(GameActivity.this, "Clicked Interstitial '"+location+"'",Toast.LENGTH_SHORT).show();
		}

		@Override
		public void didShowInterstitial(String location) {
			//Log.i("parachute", "INTERSTITIAL '" + location + "' SHOWN");
		}

		@Override
		public void didFailToRecordClick(String uri, CBClickError error) {

			//Log.i("parachute", "FAILED TO RECORD CLICK " + (uri != null ? uri : "null") + ", error: " + error.name());
			//Toast.makeText(GameActivity.this, "URL '"+uri+"' Click Failed",
					//Toast.LENGTH_SHORT).show();
		}

		@Override
		public boolean shouldDisplayLoadingViewForMoreApps() {
			return true;
		}

		@Override
		public boolean shouldRequestMoreApps() {

			return true;
		}

		@Override
		public boolean shouldDisplayMoreApps() {
			//Log.i("parachute", "SHOULD DISPLAY MORE APPS?");
			return true;
		}

		@Override
		public void didFailToLoadMoreApps(CBImpressionError error) {
			//Log.i("parachute", "MORE APPS REQUEST FAILED - " + error.name());
			//Toast.makeText(GameActivity.this, "More Apps Load Failed",
					//Toast.LENGTH_SHORT).show();
			
		}

		@Override
		public void didCacheMoreApps() {
			//Log.i("parachute", "MORE APPS CACHED");
		}

		@Override
		public void didDismissMoreApps() {
			//Log.i("parachute", "MORE APPS DISMISSED");
			//Toast.makeText(GameActivity.this, "Dismissed More Apps",
					//Toast.LENGTH_SHORT).show();
		}

		@Override
		public void didCloseMoreApps() {
			//Log.i("parachute", "MORE APPS CLOSED");
			//Toast.makeText(GameActivity.this, "Closed More Apps",
					//Toast.LENGTH_SHORT).show();
		}

		@Override
		public void didClickMoreApps() {
			//Log.i("parachute", "MORE APPS CLICKED");
			//Toast.makeText(GameActivity.this, "Clicked More Apps",
					//Toast.LENGTH_SHORT).show();
		}

		@Override
		public void didShowMoreApps() {
			//Log.i("parachute", "MORE APPS SHOWED");
		}

		@Override
		public boolean shouldRequestInterstitialsInFirstSession() {
			return true;
		}

		@Override
		public boolean shouldPauseClickForConfirmation(
				CBAgeGateConfirmation callback) {
			return false;
		}
	};*/

}

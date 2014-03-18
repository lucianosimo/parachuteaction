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
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.input.sensor.acceleration.AccelerationData;
import org.andengine.input.sensor.acceleration.IAccelerationListener;
import org.andengine.ui.activity.BaseGameActivity;

import android.view.KeyEvent;

import com.lucianosimo.parachuteaction.manager.ResourcesManager;
import com.lucianosimo.parachuteaction.manager.SceneManager;

public class GameActivity extends BaseGameActivity implements IAccelerationListener{

	private BoundCamera camera;
	private ResourcesManager resourcesManager;
	
	//public static PhysicsWorld mPhysicsWorld;
	
	public static float mGravityX = 0;
    //private float mGravityY = 0;

	
	@Override
	public EngineOptions onCreateEngineOptions() {
		
		camera = new BoundCamera(0, 0, 480, 854);
		EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED, new RatioResolutionPolicy(480, 854), this.camera);
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
		resourcesManager = ResourcesManager.getInstance();
		pOnCreateResourcesCallback.onCreateResourcesFinished();
	}

	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback)	throws IOException {
		SceneManager.getInstance().createSplashScene(pOnCreateSceneCallback);		
	}

	@Override
	public void onPopulateScene(Scene pScene, OnPopulateSceneCallback pOnPopulateSceneCallback) throws IOException {
		mEngine.registerUpdateHandler(new TimerHandler(2f, new ITimerCallback() {
			
			@Override
			public void onTimePassed(TimerHandler pTimerHandler) {
		        // Show an interstitial
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
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onAccelerationChanged(AccelerationData pAccelerationData) {
		GameActivity.mGravityX = pAccelerationData.getX()*5;
        //this.mGravityY = SensorManager.GRAVITY_EARTH;
        //GameActivity.mPhysicsWorld = new FixedStepPhysicsWorld(60, new Vector2(mGravityX, -mGravityY), false);
		
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

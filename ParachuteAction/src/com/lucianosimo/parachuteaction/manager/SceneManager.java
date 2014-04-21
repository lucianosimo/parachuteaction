package com.lucianosimo.parachuteaction.manager;

import org.andengine.engine.Engine;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.ui.IGameInterface.OnCreateSceneCallback;

import com.lucianosimo.parachuteaction.base.BaseScene;
import com.lucianosimo.parachuteaction.scene.AchivementsScene;
import com.lucianosimo.parachuteaction.scene.GameScene;
import com.lucianosimo.parachuteaction.scene.LoadingScene;
import com.lucianosimo.parachuteaction.scene.MainMenuScene;
import com.lucianosimo.parachuteaction.scene.ShopScene;
import com.lucianosimo.parachuteaction.scene.SplashScene;
import com.lucianosimo.parachuteaction.scene.StatisticsScene;

public class SceneManager {

	private BaseScene splashScene;
	private BaseScene menuScene;
	private BaseScene statisticsScene;
	private BaseScene achivementsScene;
	private BaseScene loadingScene;
	private BaseScene gameScene;
	private BaseScene shopScene;
	
	private static final SceneManager INSTANCE = new SceneManager();
	private SceneType currentSceneType = SceneType.SCENE_SPLASH;
	private BaseScene currentScene;
	private Engine engine = ResourcesManager.getInstance().engine;
	
	public enum SceneType {
		SCENE_SPLASH,
		SCENE_MENU,
		SCENE_STATISTICS,
		SCENE_ACHIVEMENTS,
		SCENE_LOADING,
		SCENE_GAME,
		SCENE_SHOP,
	}
	
	public void setScene(BaseScene scene) {
		engine.setScene(scene);
		currentScene = scene;
		currentSceneType = scene.getSceneType();
	}
	
	public void setScene(SceneType sceneType) {
		switch(sceneType) {
			case SCENE_SPLASH:
				setScene(splashScene);
				break;
			case SCENE_MENU:
				setScene(menuScene);
				break;
			case SCENE_STATISTICS:
				setScene(statisticsScene);
				break;
			case SCENE_ACHIVEMENTS:
				setScene(achivementsScene);
				break;
			case SCENE_GAME:
				setScene(gameScene);
				break;
			case SCENE_LOADING:
				setScene(loadingScene);
				break;
			case SCENE_SHOP:
				setScene(shopScene);
				break;
			default:
				break;
		}
	}
	
	public static SceneManager getInstance() {
		return INSTANCE;
	}
	
	public SceneType getCurrentSceneType() {
		return currentSceneType;
	}
	
	public BaseScene getCurrentScene() {
		return currentScene;
	}
	
	public void createSplashScene(OnCreateSceneCallback pOnCreateSceneCallback) {
		ResourcesManager.getInstance().loadSplashScreen();
		splashScene = new SplashScene();
		currentScene = splashScene;
		pOnCreateSceneCallback.onCreateSceneFinished(splashScene);
	}
	
	private void disposeSplashScene() {
		ResourcesManager.getInstance().unloadSplashScreen();
		splashScene.disposeScene();
		splashScene = null;
	}
	
	public void createMenuScene() {
		ResourcesManager.getInstance().loadMenuResources();
		menuScene = new MainMenuScene();
		setScene(menuScene);
		disposeSplashScene();
	}
	
	public void loadGameScene(final Engine mEngine, final BaseScene scene) {
		loadingScene = new LoadingScene();
		setScene(loadingScene);
		scene.disposeScene();
		ResourcesManager.getInstance().unloadMenuResources();
		mEngine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {
			@Override
			public void onTimePassed(final TimerHandler pTimerHandler) {
				mEngine.unregisterUpdateHandler(pTimerHandler);
				ResourcesManager.getInstance().loadGameResources();
				gameScene = new GameScene();
				setScene(gameScene);
			}
		}));
	}
	
	public void loadShopScene(final Engine mEngine, final BaseScene scene) {
		loadingScene = new LoadingScene();
		setScene(loadingScene);
		scene.disposeScene();
		ResourcesManager.getInstance().unloadMenuResources();
		mEngine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {
			@Override
			public void onTimePassed(final TimerHandler pTimerHandler) {
				mEngine.unregisterUpdateHandler(pTimerHandler);
				ResourcesManager.getInstance().loadShopResources();
				shopScene = new ShopScene();
				setScene(shopScene);
			}
		}));
	}
	
	public void loadMenuScene(final Engine mEngine, final BaseScene scene) {
		loadingScene = new LoadingScene();
		setScene(loadingScene);
		scene.disposeScene();
		switch (scene.getSceneType()) {
			case SCENE_STATISTICS:
				ResourcesManager.getInstance().unloadStatisticsResources();
				break;
			case SCENE_ACHIVEMENTS:
				ResourcesManager.getInstance().unloadAchievementsResources();
				break;
			case SCENE_SHOP:
				ResourcesManager.getInstance().unloadShopResources();
				break;
			case SCENE_GAME:
				ResourcesManager.getInstance().unloadGameResources();
				break;
			default:
				break;
		}
		mEngine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {
			
			@Override
			public void onTimePassed(final TimerHandler pTimerHandler) {
				mEngine.unregisterUpdateHandler(pTimerHandler);
				ResourcesManager.getInstance().loadMenuResources();
				menuScene = new MainMenuScene();
				setScene(menuScene);
			}
		}));
	}
	
	public void loadStatisticsScene(final Engine mEngine, final BaseScene scene) {
		loadingScene = new LoadingScene();
		setScene(loadingScene);
		scene.disposeScene();
		switch (scene.getSceneType()) {
			case SCENE_MENU:
				ResourcesManager.getInstance().unloadMenuResources();
				break;
			case SCENE_ACHIVEMENTS:
				ResourcesManager.getInstance().unloadAchievementsResources();
				break;
			default:
				break;
		}
		mEngine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {
			
			@Override
			public void onTimePassed(final TimerHandler pTimerHandler) {
				mEngine.unregisterUpdateHandler(pTimerHandler);
				ResourcesManager.getInstance().loadStatisticsResources();
				statisticsScene = new StatisticsScene();
				setScene(statisticsScene);
			}
		}));
	}
	
	public void loadAchivementsScene(final Engine mEngine, final BaseScene scene) {
		loadingScene = new LoadingScene();
		setScene(loadingScene);
		scene.disposeScene();
		ResourcesManager.getInstance().unloadStatisticsResources();
		mEngine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {
			
			@Override
			public void onTimePassed(final TimerHandler pTimerHandler) {
				mEngine.unregisterUpdateHandler(pTimerHandler);
				ResourcesManager.getInstance().loadAchievementsResources();
				achivementsScene = new AchivementsScene();
				setScene(achivementsScene);
			}
		}));
	}
}

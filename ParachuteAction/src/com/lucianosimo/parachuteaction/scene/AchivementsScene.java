package com.lucianosimo.parachuteaction.scene;

import org.andengine.entity.scene.background.AutoParallaxBackground;
import org.andengine.entity.scene.background.ParallaxBackground.ParallaxEntity;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.lucianosimo.parachuteaction.base.BaseScene;
import com.lucianosimo.parachuteaction.manager.SceneManager;
import com.lucianosimo.parachuteaction.manager.SceneManager.SceneType;

public class AchivementsScene extends BaseScene implements IOnMenuItemClickListener{
	
	private MenuScene menuChildScene;
	private final int ACHIVEMENTS_MENU = 0;
	private final int ACHIVEMENTS_STATISTICS = 1;
	
	private int upperImpulse;
	private Sprite upperAchivement;
	
	@Override
	public void createScene() {
		createBackground();
		createMenuChildScene();
	}

	@Override
	public void onBackKeyPressed() {
		SceneManager.getInstance().loadStatisticsScene(engine, this);
	}

	@Override
	public SceneType getSceneType() {
		return SceneType.SCENE_ACHIVEMENTS;
	}

	@Override
	public void disposeScene() {
	}
	
	public void createBackground() {
		AutoParallaxBackground background = new AutoParallaxBackground(0, 0, 0, 12);
		background.attachParallaxEntity(new ParallaxEntity(0, new Sprite(240, 427, resourcesManager.menu_background_region, vbom)));
		this.setBackground(background);
	}
	
	private void createMenuChildScene() {
		float screenWidth = resourcesManager.camera.getWidth();
		float screenHeight = resourcesManager.camera.getHeight();
		
		menuChildScene = new MenuScene(camera);
		menuChildScene.setPosition(screenWidth/2, screenHeight/2);
		
		loadAchievements();
		
		final IMenuItem menuButtonItem = new ScaleMenuItemDecorator(new SpriteMenuItem(ACHIVEMENTS_MENU, resourcesManager.menu_button, vbom), 1.2f, 1);
		final IMenuItem statisticsButtonItem = new ScaleMenuItemDecorator(new SpriteMenuItem(ACHIVEMENTS_STATISTICS, resourcesManager.statistics_button, vbom), 1.2f, 1);
				
				
		menuChildScene.addMenuItem(menuButtonItem);
		menuChildScene.addMenuItem(statisticsButtonItem);
		
		menuChildScene.attachChild(upperAchivement);
		
		menuChildScene.buildAnimations();
		menuChildScene.setBackgroundEnabled(false);
		
		statisticsButtonItem.setPosition(-180, -350);
		menuButtonItem.setPosition(180, -350);
		upperAchivement.setPosition(-150, 350);
		
		menuChildScene.setOnMenuItemClickListener(this);
		setChildScene(menuChildScene);
	}
	
	private void loadAchievements() {
		loadSavedPreferences();
		//UPPER IMPULSE ACHIEVEMENT
		if (upperImpulse < 14) {
			upperAchivement = new Sprite(-200, 400, resourcesManager.upperAchivementLocked, vbom);
		} else {
			upperAchivement = new Sprite(-200, 400, resourcesManager.upperAchivementUnlocked, vbom);
		}
	}
	
	private void loadSavedPreferences() {
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
		upperImpulse = sharedPreferences.getInt("upperImpulseCounter", 0);
	}
	
	
	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,	float pMenuItemLocalX, float pMenuItemLocalY) {
		switch (pMenuItem.getID()) {
			case ACHIVEMENTS_MENU:
				SceneManager.getInstance().loadMenuScene(engine, this);
				return true;
			case ACHIVEMENTS_STATISTICS:
				SceneManager.getInstance().loadStatisticsScene(engine, this);
				return true;
			default:
				return false;
		}
	}

}

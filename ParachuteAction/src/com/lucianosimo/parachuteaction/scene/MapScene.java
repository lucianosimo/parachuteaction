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

public class MapScene extends BaseScene implements IOnMenuItemClickListener{
	
	private MenuScene menuChildScene;

	private final int MAP_MENU = 0;
	private final int MAP_BEACH = 1;
	private final int MAP_CITY = 2;
	private final int MAP_FOREST = 3;
	private final int MAP_DESERT = 4;
	private final int MAP_MOUNTAIN = 5;
	private final int MAP_SHIP = 6;
	
	private static int level;
	
	private boolean unlockedDesert = false;
	private boolean unlockedMountain = false;
	private boolean unlockedShip = false;
	
	@Override
	public void createScene() {
		createBackground();
		createMenuChildScene();
	}

	@Override
	public void onBackKeyPressed() {
		SceneManager.getInstance().loadMenuScene(engine, this);
	}

	@Override
	public SceneType getSceneType() {
		return SceneType.SCENE_SHOP;
	}

	@Override
	public void disposeScene() {
	}
	
	public void createBackground() {
		AutoParallaxBackground background = new AutoParallaxBackground(0, 0, 0, 12);
		background.attachParallaxEntity(new ParallaxEntity(0, new Sprite(240, 427, resourcesManager.map_background_region, vbom)));
		this.setBackground(background);
	}
	
	private void createMenuChildScene() {
		float screenWidth = resourcesManager.camera.getWidth();
		float screenHeight = resourcesManager.camera.getHeight();
		
		menuChildScene = new MenuScene(camera);
		menuChildScene.setPosition(screenWidth/2, screenHeight/2);

		loadUnlockedLocations();	
		
		final IMenuItem menuButtonItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MAP_MENU, resourcesManager.map_menu_region, vbom), 1.2f, 1);				
		final IMenuItem beachButtonItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MAP_BEACH, resourcesManager.map_beach_region, vbom), 1.2f, 1);
		final IMenuItem cityButtonItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MAP_CITY, resourcesManager.map_city_region, vbom), 1.2f, 1);
		final IMenuItem forestButtonItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MAP_FOREST, resourcesManager.map_forest_region, vbom), 1.2f, 1);
		final IMenuItem desertButtonItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MAP_DESERT, resourcesManager.map_desert_region, vbom), 1.2f, 1);
		final IMenuItem mountainButtonItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MAP_MOUNTAIN, resourcesManager.map_mountain_region, vbom), 1.2f, 1);
		final IMenuItem shipButtonItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MAP_SHIP, resourcesManager.map_ship_region, vbom), 1.2f, 1);
				
		menuChildScene.addMenuItem(menuButtonItem);
		menuChildScene.addMenuItem(beachButtonItem);
		menuChildScene.addMenuItem(cityButtonItem);
		menuChildScene.addMenuItem(forestButtonItem);
		if (unlockedDesert) {
			menuChildScene.addMenuItem(desertButtonItem);
			desertButtonItem.setPosition(-140, -120);
		}
		if (unlockedMountain) {
			menuChildScene.addMenuItem(mountainButtonItem);
			mountainButtonItem.setPosition(0, -120);
		}
		if (unlockedShip) {
			menuChildScene.addMenuItem(shipButtonItem);
			shipButtonItem.setPosition(140, -120);
		}
		
		menuChildScene.buildAnimations();
		menuChildScene.setBackgroundEnabled(false);
		
		menuButtonItem.setPosition(-150, -350);
		beachButtonItem.setPosition(-140, 120);
		cityButtonItem.setPosition(0, 120);
		forestButtonItem.setPosition(140, 120);
		
		menuChildScene.setOnMenuItemClickListener(this);
		setChildScene(menuChildScene);
	}	
	
	
	private void loadUnlockedLocations() {
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
		unlockedDesert = sharedPreferences.getBoolean("desert", false);
		unlockedMountain = sharedPreferences.getBoolean("mountain", false);
		unlockedShip = sharedPreferences.getBoolean("ship", false);
	}
	
	public static int getLevel() {
		return level;
	}
	
	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,	float pMenuItemLocalX, float pMenuItemLocalY) {
		switch (pMenuItem.getID()) {
			case MAP_MENU:
				SceneManager.getInstance().loadMenuScene(engine, this);
				return true;
			case MAP_BEACH:
				level = MAP_BEACH;
				SceneManager.getInstance().loadGameScene(engine, this);
				return true;
			case MAP_CITY:
				level = MAP_CITY;
				SceneManager.getInstance().loadGameScene(engine, this);
				return true;
			case MAP_FOREST:
				level = MAP_FOREST;
				SceneManager.getInstance().loadGameScene(engine, this);
				return true;
			case MAP_DESERT:
				level = MAP_DESERT;
				SceneManager.getInstance().loadGameScene(engine, this);
				return true;
			case MAP_MOUNTAIN:
				level = MAP_MOUNTAIN;
				SceneManager.getInstance().loadGameScene(engine, this);
				return true;
			case MAP_SHIP:
				level = MAP_SHIP;
				SceneManager.getInstance().loadGameScene(engine, this);
				return true;
			default:
				return false;
		}
	}
	
}

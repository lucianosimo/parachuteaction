package com.lucianosimo.parachuteaction.scene;

import org.andengine.entity.scene.background.AutoParallaxBackground;
import org.andengine.entity.scene.background.ParallaxBackground.ParallaxEntity;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.util.adt.align.HorizontalAlign;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.lucianosimo.parachuteaction.base.BaseScene;
import com.lucianosimo.parachuteaction.manager.SceneManager;
import com.lucianosimo.parachuteaction.manager.SceneManager.SceneType;

public class StatisticsScene extends BaseScene implements IOnMenuItemClickListener{
	
	private MenuScene menuChildScene;	
	private Text numberOfSuccessfulJumpsText;
	private Text numberOfUnsuccessfulJumpsText;
	private Text maxFliedMetersText;
	private Text freeFliedMetersText;
	private Text parachuteFliedMetersText;
	
	@Override
	public void createScene() {
		createBackground();
		createMenuChildScene();
	}

	@Override
	public void onBackKeyPressed() {
		SceneManager.getInstance().loadMenuScene(engine, StatisticsScene.this);
	}

	@Override
	public SceneType getSceneType() {
		return SceneType.SCENE_MENU;
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
		
		numberOfSuccessfulJumpsText = new Text(20, 430, resourcesManager.numberOfSuccessfulJumpsFont, "Successfuljumps :0123456789", new TextOptions(HorizontalAlign.LEFT), vbom);
		numberOfUnsuccessfulJumpsText = new Text(20, 430, resourcesManager.numberOfUnsuccessfulJumpsFont, "Unsuccessfuljumps :0123456789", new TextOptions(HorizontalAlign.LEFT), vbom);
		maxFliedMetersText = new Text(20, 430, resourcesManager.maxFliedMetersFont, "Longest fly:0123456789", new TextOptions(HorizontalAlign.LEFT), vbom);
		freeFliedMetersText = new Text(20, 430, resourcesManager.freeFliedMetersFont, "Free flied meters:0123456789", new TextOptions(HorizontalAlign.LEFT), vbom);
		parachuteFliedMetersText = new Text(20, 430, resourcesManager.parachuteFliedMetersFont, "Parachute flied meters:0123456789", new TextOptions(HorizontalAlign.LEFT), vbom);
		loadSavedPreferences();

		menuChildScene = new MenuScene(camera);
		menuChildScene.setPosition(screenWidth/2, screenHeight/2);
		
		menuChildScene.buildAnimations();
		menuChildScene.setBackgroundEnabled(false);
		
		menuChildScene.attachChild(numberOfSuccessfulJumpsText);
		menuChildScene.attachChild(numberOfUnsuccessfulJumpsText);
		menuChildScene.attachChild(maxFliedMetersText);
		menuChildScene.attachChild(freeFliedMetersText);
		menuChildScene.attachChild(parachuteFliedMetersText);
		
		numberOfSuccessfulJumpsText.setPosition(0, 220);
		numberOfUnsuccessfulJumpsText.setPosition(0, 180);
		maxFliedMetersText.setPosition(0, 140);
		freeFliedMetersText.setPosition(0, 100);
		parachuteFliedMetersText.setPosition(0, 60);
		
		menuChildScene.setOnMenuItemClickListener(this);
		setChildScene(menuChildScene);
	}
	
	private void loadSavedPreferences() {
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
		int maxFliedMeters = sharedPreferences.getInt("fliedMeters", 0);
		int numberOfSuccessfulJumps = sharedPreferences.getInt("successfulJumps", 0);
		int numberOfUnsuccessfulJumps = sharedPreferences.getInt("unsuccessfulJumps", 0);
		int freeFliedMeters = sharedPreferences.getInt("freeFliedMeters", 0);
		int parachuteFliedMeters = sharedPreferences.getInt("parachuteFliedMeters", 0);
		numberOfSuccessfulJumpsText.setText("Successful jumps: " + numberOfSuccessfulJumps);
		numberOfUnsuccessfulJumpsText.setText("Unsuccessful jumps: " + numberOfUnsuccessfulJumps);
		maxFliedMetersText.setText("Longest fly: " + maxFliedMeters);
		freeFliedMetersText.setText("Free flied meters: " + freeFliedMeters);
		parachuteFliedMetersText.setText("Parachute flied meters: " + parachuteFliedMeters);
	}

	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
			float pMenuItemLocalX, float pMenuItemLocalY) {
		// TODO Auto-generated method stub
		return false;
	}

}

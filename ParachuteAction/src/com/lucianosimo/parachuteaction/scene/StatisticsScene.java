package com.lucianosimo.parachuteaction.scene;

import org.andengine.entity.scene.background.AutoParallaxBackground;
import org.andengine.entity.scene.background.ParallaxBackground.ParallaxEntity;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.util.adt.align.HorizontalAlign;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.lucianosimo.parachuteaction.base.BaseScene;
import com.lucianosimo.parachuteaction.manager.SceneManager;
import com.lucianosimo.parachuteaction.manager.SceneManager.SceneType;

public class StatisticsScene extends BaseScene implements IOnMenuItemClickListener{
	
	private MenuScene statisticsChildScene;	
	private Text numberOfSuccessfulJumpsText;
	private Text numberOfUnsuccessfulJumpsText;
	private Text maxFliedMetersText;
	private Text freeFliedMetersText;
	private Text parachuteFliedMetersText;
	private Text upperImpulseText;
	private Text antigravityText;
	private Text shieldText;
	private Text slowText;
	
	private final int STATISTICS_MENU = 0;
	private final int STATISTICS_ACHIVEMENTS = 1;
	private final int RESET_STATISTICS = 2;
	
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
		return SceneType.SCENE_STATISTICS;
	}

	@Override
	public void disposeScene() {
	}
	
	public void createBackground() {
		AutoParallaxBackground background = new AutoParallaxBackground(0, 0, 0, 12);
		background.attachParallaxEntity(new ParallaxEntity(0, new Sprite(240, 427, resourcesManager.statistics_background_region, vbom)));
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
		upperImpulseText = new Text(20, 430, resourcesManager.upperImpulseCounterFont, "Upperimpulsescollected: 0123456789", new TextOptions(HorizontalAlign.LEFT), vbom);
		antigravityText = new Text(20, 430, resourcesManager.antigravityCounterFont, "Antigravitycollected: 0123456789", new TextOptions(HorizontalAlign.LEFT), vbom);
		shieldText = new Text(20, 430, resourcesManager.shieldCounterFont, "Shields collected:0123456789", new TextOptions(HorizontalAlign.LEFT), vbom);
		slowText = new Text(20, 430, resourcesManager.slowCounterFont, "Slowobjectscollected: 0123456789", new TextOptions(HorizontalAlign.LEFT), vbom);
		loadSavedPreferences();
		
		final IMenuItem menuButtonItem = new ScaleMenuItemDecorator(new SpriteMenuItem(STATISTICS_MENU, resourcesManager.menu_from_statistics_region, vbom), 1.2f, 1);
		final IMenuItem achivementsButtonItem = new ScaleMenuItemDecorator(new SpriteMenuItem(STATISTICS_ACHIVEMENTS, resourcesManager.achievements_region, vbom), 1.2f, 1);
		final IMenuItem resetStatisticsItem = new ScaleMenuItemDecorator(new SpriteMenuItem(RESET_STATISTICS, resourcesManager.resetStatistics_region, vbom), 1.2f, 1);

		statisticsChildScene = new MenuScene(camera);
		statisticsChildScene.setPosition(screenWidth/2, screenHeight/2);
		
		statisticsChildScene.buildAnimations();
		statisticsChildScene.setBackgroundEnabled(false);
		
		statisticsChildScene.addMenuItem(menuButtonItem);
		statisticsChildScene.addMenuItem(achivementsButtonItem);
		statisticsChildScene.addMenuItem(resetStatisticsItem);
		
		statisticsChildScene.attachChild(numberOfSuccessfulJumpsText);
		statisticsChildScene.attachChild(numberOfUnsuccessfulJumpsText);
		statisticsChildScene.attachChild(maxFliedMetersText);
		statisticsChildScene.attachChild(freeFliedMetersText);
		statisticsChildScene.attachChild(parachuteFliedMetersText);
		statisticsChildScene.attachChild(upperImpulseText);
		statisticsChildScene.attachChild(antigravityText);
		statisticsChildScene.attachChild(shieldText);
		statisticsChildScene.attachChild(slowText);
		
		menuButtonItem.setPosition(-150, -350);
		achivementsButtonItem.setPosition(150, -350);
		resetStatisticsItem.setPosition(0, -210);
		numberOfSuccessfulJumpsText.setPosition(0, 220);
		numberOfUnsuccessfulJumpsText.setPosition(0, 180);
		maxFliedMetersText.setPosition(0, 140);
		freeFliedMetersText.setPosition(0, 100);
		parachuteFliedMetersText.setPosition(0, 60);
		upperImpulseText.setPosition(0, 20);
		antigravityText.setPosition(0, -20);
		shieldText.setPosition(0, -60);
		slowText.setPosition(0, -100);		
		
		statisticsChildScene.setOnMenuItemClickListener(this);
		setChildScene(statisticsChildScene);
	}
	
	private void loadSavedPreferences() {
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
		int maxFliedMeters = sharedPreferences.getInt("fliedMeters", 0);
		int numberOfSuccessfulJumps = sharedPreferences.getInt("successfulJumps", 0);
		int numberOfUnsuccessfulJumps = sharedPreferences.getInt("unsuccessfulJumps", 0);
		int freeFliedMeters = sharedPreferences.getInt("freeFliedMeters", 0);
		int parachuteFliedMeters = sharedPreferences.getInt("parachuteFliedMeters", 0);
		int upperImpulseCounter = sharedPreferences.getInt("upperImpulseCounter", 0);
		int antigravityCounter = sharedPreferences.getInt("antigravityCounter", 0);
		int shieldCounter = sharedPreferences.getInt("shieldCounter", 0);
		int slowCounter = sharedPreferences.getInt("slowCounter", 0);
		numberOfSuccessfulJumpsText.setText("Successful jumps: " + numberOfSuccessfulJumps);
		numberOfUnsuccessfulJumpsText.setText("Unsuccessful jumps: " + numberOfUnsuccessfulJumps);
		maxFliedMetersText.setText("Longest fly: " + maxFliedMeters);
		freeFliedMetersText.setText("Free flied meters: " + freeFliedMeters);
		parachuteFliedMetersText.setText("Parachute flied meters: " + parachuteFliedMeters);
		upperImpulseText.setText("Upper impulses collected: " + upperImpulseCounter);
		antigravityText.setText("Antigravity collected: " + antigravityCounter);
		shieldText.setText("Shields collected: " + shieldCounter);
		slowText.setText("Slow objects collected: " + slowCounter);
	}

	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,	float pMenuItemLocalX, float pMenuItemLocalY) {
		switch (pMenuItem.getID()) {
			case STATISTICS_MENU:
				SceneManager.getInstance().loadMenuScene(engine, StatisticsScene.this);
				return true;
			case STATISTICS_ACHIVEMENTS:
				SceneManager.getInstance().loadAchivementsScene(engine, this);
				return true;
			case RESET_STATISTICS:
				resetStatistics();
				return true;
			default:
				return false;
		}
	}
	
	private void resetStatistics() {
		StatisticsScene.this.activity.runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				new AlertDialog.Builder(StatisticsScene.this.activity)
				.setMessage("Do you really want to reset your statistics")
				.setPositiveButton("Of course!!!", new DialogInterface.OnClickListener() {

				    public void onClick(DialogInterface dialog, int whichButton) {
				    	SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
						Editor editor = sharedPreferences.edit();
						editor.putInt("fliedMeters", 0);
						editor.putInt("successfulJumps", 0);
						editor.putInt("unsuccessfulJumps", 0);
						editor.putInt("freeFliedMeters", 0);
						editor.putInt("parachuteFliedMeters", 0);
						editor.putInt("upperImpulseCounter", 0);
						editor.putInt("antigravityCounter", 0);
						editor.putInt("shieldCounter", 0);
						editor.putInt("slowCounter", 0);
						editor.putBoolean("desert", false);
						editor.putBoolean("mountain", false);
						editor.putBoolean("western", false);
						editor.commit();
				        Toast.makeText(activity, "Statistics restarted", Toast.LENGTH_LONG).show();
				        numberOfSuccessfulJumpsText.setText("Successful jumps: " + 0);
						numberOfUnsuccessfulJumpsText.setText("Unsuccessful jumps: " + 0);
						maxFliedMetersText.setText("Longest fly: " + 0);
						freeFliedMetersText.setText("Free flied meters: " + 0);
						parachuteFliedMetersText.setText("Parachute flied meters: " + 0);
						upperImpulseText.setText("Upper impulses collected: " + 0);
						antigravityText.setText("Antigravity collected: " + 0);
						shieldText.setText("Shields collected: " + 0);
						slowText.setText("Slow objects collected: " + 0);
				    }})
				 .setNegativeButton("Mmmm, not really", null).show();	
				
			}
		});
	}

}

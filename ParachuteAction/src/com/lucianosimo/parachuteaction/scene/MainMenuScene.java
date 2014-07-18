package com.lucianosimo.parachuteaction.scene;

import java.util.Random;

import org.andengine.engine.handler.physics.PhysicsHandler;
import org.andengine.entity.scene.background.AutoParallaxBackground;
import org.andengine.entity.scene.background.ParallaxBackground.ParallaxEntity;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.preference.PreferenceManager;

import com.lucianosimo.parachuteaction.base.BaseScene;
import com.lucianosimo.parachuteaction.manager.SceneManager;
import com.lucianosimo.parachuteaction.manager.SceneManager.SceneType;

public class MainMenuScene extends BaseScene implements IOnMenuItemClickListener{
	
	private MenuScene menuChildScene;
	private final int MENU_PLAY = 0;
	private final int MENU_STATISTICS = 1;
	private final int MENU_SHOP = 2;
	private final int MENU_RATE_US = 3;
	
	private static final int LEFT_MARGIN = -240;
	private static final int RIGHT_MARGIN = 240;
	private static final int CLOUD_SPEED = -40;
	private static final int FAR_CLOUD_SPEED = -15;

	@Override
	public void createScene() {
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
		int played = sharedPreferences.getInt("played", 0);
		//Rated: 0 = no, 1 = yes, 2 = no and don't want to rate
		int rated = sharedPreferences.getInt("rated", 0);		
		if (rated == 0) {
			if (played == 5 || played == 15 || played == 30) {
				displayRateUsWindow();
			}
		}
		createBackground();
		createMenuChildScene();
	}

	@Override
	public void onBackKeyPressed() {
		System.exit(0);
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
		
		Sprite title = new Sprite(-240, 0, resourcesManager.menu_title_region, vbom);
		
		Sprite cloud = new Sprite(-240, 0, resourcesManager.menu_cloud_region.deepCopy(), vbom) {
			protected void onManagedUpdate(float pSecondsElapsed) {
				super.onManagedUpdate(pSecondsElapsed);
				if (this.getX() < LEFT_MARGIN - 166) {
					Random rand = new Random();
					int randY = rand.nextInt(855) - 427;
					this.setPosition(RIGHT_MARGIN + 166, randY);
				}
			};
		};
		PhysicsHandler handler = new PhysicsHandler(cloud);
		cloud.registerUpdateHandler(handler);
		handler.setVelocity(CLOUD_SPEED,0);
		
		Sprite farCloud = new Sprite(-240, 0, resourcesManager.menu_far_cloud_region.deepCopy(), vbom) {
			protected void onManagedUpdate(float pSecondsElapsed) {
				super.onManagedUpdate(pSecondsElapsed);
				if (this.getX() < LEFT_MARGIN - 166) {
					Random rand = new Random();
					int randY = rand.nextInt(855) - 427;
					this.setPosition(RIGHT_MARGIN + 166, randY);
				}
			};
		};
		PhysicsHandler handler3 = new PhysicsHandler(farCloud);
		farCloud.registerUpdateHandler(handler3);
		handler3.setVelocity(FAR_CLOUD_SPEED,0);
		
		menuChildScene = new MenuScene(camera);
		menuChildScene.setPosition(screenWidth/2, screenHeight/2);
		
		final IMenuItem playMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_PLAY, resourcesManager.play_region, vbom), 1.2f, 1);
		final IMenuItem statisticsMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_STATISTICS, resourcesManager.statistics_region, vbom), 1.2f, 1);
		final IMenuItem shopMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_SHOP, resourcesManager.shop_region, vbom), 1.2f, 1);
		final IMenuItem rateUsItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_RATE_US, resourcesManager.rate_us_region, vbom), 1.2f, 1);

		menuChildScene.attachChild(farCloud);
		menuChildScene.attachChild(cloud);		
		menuChildScene.attachChild(title);
		
		
		menuChildScene.addMenuItem(playMenuItem);
		menuChildScene.addMenuItem(statisticsMenuItem);
		menuChildScene.addMenuItem(shopMenuItem);
		menuChildScene.addMenuItem(rateUsItem);

		menuChildScene.buildAnimations();
		menuChildScene.setBackgroundEnabled(false);
		
		playMenuItem.setPosition(0, 25);
		statisticsMenuItem.setPosition(-120, -200);
		shopMenuItem.setPosition(120, -200);
		rateUsItem.setPosition(0, -325);
		
		cloud.setPosition(100, 150);
		farCloud.setPosition(-100, -150);
		title.setPosition(0, 300);
		
		menuChildScene.setOnMenuItemClickListener(this);
		setChildScene(menuChildScene);
	}
	
	private void displayRateUsWindow() {
		MainMenuScene.this.activity.runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				new AlertDialog.Builder(MainMenuScene.this.activity)
				.setMessage("Do you want to rate us")
				.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

				    public void onClick(DialogInterface dialog, int whichButton) {
				    	activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + "com.lucianosimo.parachuteaction")));
				    	SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
						int rated = sharedPreferences.getInt("rated", 0);
						Editor editor = sharedPreferences.edit();
						rated = 1;
						editor.putInt("rated", rated);
						editor.putBoolean("western", true);
						editor.commit();
				    }})
				.setNegativeButton("Never", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
						int rated = sharedPreferences.getInt("rated", 0);
						Editor editor = sharedPreferences.edit();
						rated = 2;
						editor.putInt("rated", rated);
						editor.commit();						
					}
				})
				.setNeutralButton("Maybe later", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
						int played = sharedPreferences.getInt("played", 0);
						Editor editor = sharedPreferences.edit();
						played++;
						editor.putInt("played", played);
						editor.commit();						
					}
				})
				.show();
			}
		});
	}
	

	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,	float pMenuItemLocalX, float pMenuItemLocalY) {
		switch (pMenuItem.getID()) {
			case MENU_PLAY:
				SceneManager.getInstance().loadMapScene(engine, this);
				return true;
			case MENU_STATISTICS:
				SceneManager.getInstance().loadStatisticsScene(engine, this);
				return true;
			case MENU_SHOP:
				SceneManager.getInstance().loadShopScene(engine, this);
				return true;
			case MENU_RATE_US:
				activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + "com.lucianosimo.parachuteaction")));
				SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
				int rated = sharedPreferences.getInt("rated", 0);
				Editor editor = sharedPreferences.edit();
				rated = 1;
				editor.putInt("rated", rated);
				editor.putBoolean("western", true);
				editor.commit();
				return true;
			default:
				return false;
		}
	}

	@Override
	public void handleOnPause() {
		// TODO Auto-generated method stub
		
	}

}

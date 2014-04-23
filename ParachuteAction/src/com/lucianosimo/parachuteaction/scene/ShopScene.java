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
import org.andengine.input.touch.TouchEvent;
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

public class ShopScene extends BaseScene implements IOnMenuItemClickListener{
	
	private MenuScene menuChildScene;

	private int coins;
	private final int SHOP_MENU = 0;
	
	private static final int DESERT_UNLOCK_VALUE = 1000;
	private static final int MOUNTAIN_UNLOCK_VALUE = 1000;
	private static final int SHIP_UNLOCK_VALUE = 1000;
	
	private Text coinsText;
	
	private boolean unlockedDesert = false;
	private boolean unlockedMountain = false;
	private boolean unlockedShip = false;
	
	private Sprite beach;
	private Sprite city;
	private Sprite desert;
	private Sprite forest;
	private Sprite mountain;
	private Sprite ship;
	
	private Sprite lockedDesert;
	private Sprite lockedMountain;
	private Sprite lockedShip;
	
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
		background.attachParallaxEntity(new ParallaxEntity(0, new Sprite(240, 427, resourcesManager.shop_background_region, vbom)));
		this.setBackground(background);
	}
	
	private void createMenuChildScene() {
		float screenWidth = resourcesManager.camera.getWidth();
		float screenHeight = resourcesManager.camera.getHeight();
		
		menuChildScene = new MenuScene(camera);
		menuChildScene.setPosition(screenWidth/2, screenHeight/2);

		coinsText = new Text(20, 720, resourcesManager.shopCoinsFont, "Coins: 0123456789", new TextOptions(HorizontalAlign.LEFT), vbom);
		
		loadCoins();
		loadUnlockedLocations();
		loadLocations();		
		
		final IMenuItem menuButtonItem = new ScaleMenuItemDecorator(new SpriteMenuItem(SHOP_MENU, resourcesManager.shop_menu_region, vbom), 1.2f, 1);				
				
		menuChildScene.addMenuItem(menuButtonItem);
		menuChildScene.attachChild(coinsText);
		
		menuChildScene.buildAnimations();
		menuChildScene.setBackgroundEnabled(false);
		
		menuButtonItem.setPosition(-150, -350);
		coinsText.setPosition(150, 400);
		
		menuChildScene.setOnMenuItemClickListener(this);
		setChildScene(menuChildScene);
	}	
	
	private void loadCoins() {
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
		coins = sharedPreferences.getInt("coins", 0);
		coinsText.setText("Coins: " + coins);
	}
	
	private void loadUnlockedLocations() {
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
		unlockedDesert = sharedPreferences.getBoolean("desert", false);
		unlockedMountain = sharedPreferences.getBoolean("mountain", false);
		unlockedShip = sharedPreferences.getBoolean("ship", false);
	}
	
	private void unlockDesert() {
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
		Editor editor = sharedPreferences.edit();
		editor.putBoolean("desert", true);
		editor.commit();
		coins = coins - DESERT_UNLOCK_VALUE;
		coinsText.setText("Coins: " + coins);
		saveCoins("coins", coins);
	}
	
	private void unlockMountain() {
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
		Editor editor = sharedPreferences.edit();
		editor.putBoolean("mountain", true);
		editor.commit();
		coins = coins - MOUNTAIN_UNLOCK_VALUE;
		coinsText.setText("Coins: " + coins);
		saveCoins("coins", coins);
	}
	
	private void unlockShip() {
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
		Editor editor = sharedPreferences.edit();
		editor.putBoolean("ship", true);
		editor.commit();
		coins = coins - SHIP_UNLOCK_VALUE;
		coinsText.setText("Coins: " + coins);
		saveCoins("coins", coins);
	}
	
	private void loadLocations() {
		beach = new Sprite(-140, 120, resourcesManager.shop_beach_region, vbom);
		city = new Sprite(0, 120, resourcesManager.shop_city_region, vbom);
		forest = new Sprite(140, 120, resourcesManager.shop_forest_region, vbom);
		desert = new Sprite(-140, -120, resourcesManager.shop_desert_region, vbom);		
		mountain = new Sprite(0, -120, resourcesManager.shop_mountain_region, vbom);
		ship = new Sprite(140, -120, resourcesManager.shop_ship_region, vbom);
		menuChildScene.attachChild(beach);
		menuChildScene.attachChild(city);
		menuChildScene.attachChild(forest);
		menuChildScene.attachChild(desert);
		menuChildScene.attachChild(mountain);
		menuChildScene.attachChild(ship);
		if (!unlockedDesert) {
			lockedDesert = new Sprite(62, 87, resourcesManager.shop_locked_location_region, vbom) {
				@Override
				public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
					if (pSceneTouchEvent.isActionDown()) {
						if (coins >= DESERT_UNLOCK_VALUE) {
							confirmMessage("Desert", DESERT_UNLOCK_VALUE);						
						} else {
							int coinsToUnlock = DESERT_UNLOCK_VALUE - coins;
							noEnoughCoins("desert", coinsToUnlock);
						}
					}
					return true;
				}
			};
			desert.attachChild(lockedDesert);
			menuChildScene.registerTouchArea(lockedDesert);
		}
		if (!unlockedMountain) {
			lockedMountain = new Sprite(62, 87, resourcesManager.shop_locked_location_region, vbom) {
				@Override
				public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
					if (pSceneTouchEvent.isActionDown()) {
						if (coins >= MOUNTAIN_UNLOCK_VALUE) {
							confirmMessage("Mountain", MOUNTAIN_UNLOCK_VALUE);						
						} else {
							int coinsToUnlock = MOUNTAIN_UNLOCK_VALUE - coins;
							noEnoughCoins("mountain", coinsToUnlock);
						}
					}
					return true;
				}
			};
			mountain.attachChild(lockedMountain);
			menuChildScene.registerTouchArea(lockedMountain);
		}
		if (!unlockedShip) {
			lockedShip = new Sprite(62, 87, resourcesManager.shop_locked_location_region, vbom) {
				@Override
				public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
					if (pSceneTouchEvent.isActionDown()) {
						if (coins >= SHIP_UNLOCK_VALUE) {
							confirmMessage("Ship", SHIP_UNLOCK_VALUE);						
						} else {
							int coinsToUnlock = SHIP_UNLOCK_VALUE - coins;
							noEnoughCoins("ship", coinsToUnlock);
						}
					}
					return true;
				}
			};
			ship.attachChild(lockedShip);
			menuChildScene.registerTouchArea(lockedShip);
		}
	}
	
	private void confirmMessage(final String location, final int coins) {
		ShopScene.this.activity.runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				new AlertDialog.Builder(ShopScene.this.activity)
				.setMessage("Do you want to unlock " + location + " for " + coins + " coins")
				.setPositiveButton("Of course!!!", new DialogInterface.OnClickListener() {

				    public void onClick(DialogInterface dialog, int whichButton) {
				    	if (location.equals("Desert")) {
				    		unlockDesert();
				    		desert.detachChild(lockedDesert);
							menuChildScene.unregisterTouchArea(lockedDesert);
				    	} else if (location.equals("Mountain")) {
				    		unlockMountain();
				    		mountain.detachChild(lockedMountain);
							menuChildScene.unregisterTouchArea(lockedMountain);
				    	} else if (location.equals("Ship")) {
				    		unlockShip();
				    		ship.detachChild(lockedShip);
							menuChildScene.unregisterTouchArea(lockedShip);
				    	}
				        Toast.makeText(activity, location + " unlocked", Toast.LENGTH_LONG).show();
				        loadUnlockedLocations();
				    }})
				 .setNegativeButton("Mmmm, not really", null).show();	
			}
		});
	}
	
	private void noEnoughCoins(final String location, final int coins) {
		ShopScene.this.activity.runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				Toast.makeText(activity, "No enough coins. Collect " + coins + " more to unlock " + location, Toast.LENGTH_LONG).show();	
			}
		});
	}
	
	private void saveCoins(String key, int coins) {
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
		Editor editor = sharedPreferences.edit();
		editor.putInt("coins", coins);
		editor.commit();
	}
	
	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,	float pMenuItemLocalX, float pMenuItemLocalY) {
		switch (pMenuItem.getID()) {
			case SHOP_MENU:
				SceneManager.getInstance().loadMenuScene(engine, this);
				return true;
			default:
				return false;
		}
	}
	
}

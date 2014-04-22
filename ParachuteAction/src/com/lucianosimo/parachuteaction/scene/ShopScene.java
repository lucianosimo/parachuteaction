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

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.lucianosimo.parachuteaction.base.BaseScene;
import com.lucianosimo.parachuteaction.manager.SceneManager;
import com.lucianosimo.parachuteaction.manager.SceneManager.SceneType;

public class ShopScene extends BaseScene implements IOnMenuItemClickListener{
	
	private MenuScene menuChildScene;
	private final int SHOP_MENU = 0;
	private Text coinsText;
	private int coins;
	
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
		
		final IMenuItem menuButtonItem = new ScaleMenuItemDecorator(new SpriteMenuItem(SHOP_MENU, resourcesManager.shop_menu_region, vbom), 1.2f, 1);				
				
		menuChildScene.addMenuItem(menuButtonItem);
		menuChildScene.attachChild(coinsText);
		
		menuChildScene.buildAnimations();
		menuChildScene.setBackgroundEnabled(false);
		
		menuButtonItem.setPosition(-180, -350);
		coinsText.setPosition(0, 0);
		
		menuChildScene.setOnMenuItemClickListener(this);
		setChildScene(menuChildScene);
	}	
	
	private void loadCoins() {
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
		coins = sharedPreferences.getInt("coins", 0);
		coinsText.setText("Coins: " + coins);
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

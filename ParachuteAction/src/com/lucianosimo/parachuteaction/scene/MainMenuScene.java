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

import com.lucianosimo.parachuteaction.base.BaseScene;
import com.lucianosimo.parachuteaction.manager.SceneManager;
import com.lucianosimo.parachuteaction.manager.SceneManager.SceneType;

public class MainMenuScene extends BaseScene implements IOnMenuItemClickListener{
	
	private MenuScene menuChildScene;
	private final int MENU_PLAY = 0;
	private final int MENU_STATISTICS = 1;
	private final int MENU_SHOP = 2;
	
	private static final int LEFT_MARGIN = -240;
	private static final int RIGHT_MARGIN = 240;
	private static final int CLOUD_SPEED = -40;
	private static final int FAR_CLOUD_SPEED = -15;

	@Override
	public void createScene() {
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

		menuChildScene.attachChild(cloud);
		menuChildScene.attachChild(title);
		menuChildScene.attachChild(farCloud);
		
		menuChildScene.addMenuItem(playMenuItem);
		menuChildScene.addMenuItem(statisticsMenuItem);
		menuChildScene.addMenuItem(shopMenuItem);

		menuChildScene.buildAnimations();
		menuChildScene.setBackgroundEnabled(false);
		
		playMenuItem.setPosition(0, 25);
		statisticsMenuItem.setPosition(-120, -200);
		shopMenuItem.setPosition(120, -200);
		
		cloud.setPosition(100, 150);
		farCloud.setPosition(-100, -150);
		title.setPosition(0, 300);
		
		menuChildScene.setOnMenuItemClickListener(this);
		setChildScene(menuChildScene);
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
			default:
				return false;
		}
	}

}

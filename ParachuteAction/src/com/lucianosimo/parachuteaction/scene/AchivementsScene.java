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
import com.lucianosimo.parachuteaction.helper.AchievementsHelper;
import com.lucianosimo.parachuteaction.manager.SceneManager;
import com.lucianosimo.parachuteaction.manager.SceneManager.SceneType;

public class AchivementsScene extends BaseScene implements IOnMenuItemClickListener{
	
	private MenuScene menuChildScene;
	private final int ACHIVEMENTS_MENU = 0;
	private final int ACHIVEMENTS_STATISTICS = 1;
	private final int ACHIVEMENTS_ONE_BUTTON = 2;
	private final int ACHIVEMENTS_TWO_BUTTON = 3;
	private final int ACHIVEMENTS_THREE_BUTTON = 4;
	
	private boolean fromMenu = true;
	private boolean from1 = false;
	private boolean from2 = false;
	private boolean from3 = false;
	
	private int jumpsCounter;
	private int freeFliedCounter;
	private int parachuteFliedCounter;
	private int failedJumpsCounter;
	private int uiCounter;
	private int antigravityCounter;
	private int shieldCounter;
	
	private Sprite firstJumpAward;
	private Sprite jumps10award;
	private Sprite jumps50award;
	private Sprite jumps100award;
	private Sprite jumps500award;
	public Sprite freeFly5000award;
	public Sprite freeFly15000award;
	public Sprite freeFly50000award;
	public Sprite freeFly100000award;
	public Sprite parachuteFly2500award;
	public Sprite parachuteFly15000award;
	public Sprite parachuteFly50000award;
	
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
		
		loadAchievements(1);
		
		final IMenuItem menuButtonItem = new ScaleMenuItemDecorator(new SpriteMenuItem(ACHIVEMENTS_MENU, resourcesManager.menu_button, vbom), 1.2f, 1);
		final IMenuItem statisticsButtonItem = new ScaleMenuItemDecorator(new SpriteMenuItem(ACHIVEMENTS_STATISTICS, resourcesManager.statistics_button, vbom), 1.2f, 1);
		final IMenuItem oneButtonItem = new ScaleMenuItemDecorator(new SpriteMenuItem(ACHIVEMENTS_ONE_BUTTON, resourcesManager.one_button, vbom), 1.2f, 1);
		final IMenuItem twoButtonItem = new ScaleMenuItemDecorator(new SpriteMenuItem(ACHIVEMENTS_TWO_BUTTON, resourcesManager.two_button, vbom), 1.2f, 1);
		final IMenuItem threeButtonItem = new ScaleMenuItemDecorator(new SpriteMenuItem(ACHIVEMENTS_THREE_BUTTON, resourcesManager.three_button, vbom), 1.2f, 1);
				
				
		menuChildScene.addMenuItem(menuButtonItem);
		menuChildScene.addMenuItem(statisticsButtonItem);
		menuChildScene.addMenuItem(oneButtonItem);
		menuChildScene.addMenuItem(twoButtonItem);
		menuChildScene.addMenuItem(threeButtonItem);
		
		menuChildScene.buildAnimations();
		menuChildScene.setBackgroundEnabled(false);
		
		statisticsButtonItem.setPosition(-180, -350);
		menuButtonItem.setPosition(180, -350);
		oneButtonItem.setPosition(-100, -250);
		twoButtonItem.setPosition(0, -250);
		threeButtonItem.setPosition(100, -250);
		
		menuChildScene.setOnMenuItemClickListener(this);
		setChildScene(menuChildScene);
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
			case ACHIVEMENTS_ONE_BUTTON:
				loadAchievements(1);
				from1 = true;
				from2 = false;
				from3 = false;
				fromMenu = false;
				return true;
			case ACHIVEMENTS_TWO_BUTTON:
				loadAchievements(2);
				from2 = true;
				from1 = false;
				from3 = false;
				fromMenu = false;
				return true;
			case ACHIVEMENTS_THREE_BUTTON:
				loadAchievements(3);
				from3 = true;
				from2 = false;
				from1 = false;
				fromMenu = false;
				return true;
			default:
				return false;
		}
	}
	
	private void loadSavedPreferences() {
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
		jumpsCounter = sharedPreferences.getInt("successfulJumps", 0);
		
	}
	
	private void loadAchievements(int page) {
		loadSavedPreferences();
		firstJumpAward = new Sprite (-115, 245, resourcesManager.firstJumpAward, vbom);
		jumps10award = new Sprite (115, 245, resourcesManager.jumps10award, vbom);
		jumps50award = new Sprite (-115, 165, resourcesManager.jumps50award, vbom);
		jumps100award = new Sprite (115, 165, resourcesManager.jumps100award, vbom);
		jumps500award = new Sprite (-115, 85, resourcesManager.jumps500award, vbom);
		freeFly5000award = new Sprite (115, 85, resourcesManager.freeFly5000award, vbom);
		freeFly15000award = new Sprite (-115, 5, resourcesManager.freeFly5000award, vbom);
		freeFly50000award = new Sprite (115, 5, resourcesManager.freeFly5000award, vbom);
		freeFly100000award = new Sprite (-115, -75, resourcesManager.freeFly5000award, vbom);
		parachuteFly2500award = new Sprite (115, -75, resourcesManager.parachuteFly2500award, vbom);
		parachuteFly15000award = new Sprite (-115, -155, resourcesManager.parachuteFly15000award, vbom);
		parachuteFly50000award = new Sprite (115, -155, resourcesManager.parachuteFly50000award, vbom);
		if (from1 || !fromMenu) {
			menuChildScene.detachChild(firstJumpAward);
			menuChildScene.detachChild(jumps10award);
			menuChildScene.detachChild(jumps50award);
			menuChildScene.detachChild(jumps100award);
			menuChildScene.detachChild(jumps500award);
			menuChildScene.detachChild(freeFly5000award);
			menuChildScene.detachChild(freeFly15000award);
			menuChildScene.detachChild(freeFly50000award);
			menuChildScene.detachChild(freeFly100000award);
			menuChildScene.detachChild(parachuteFly2500award);
			menuChildScene.detachChild(parachuteFly15000award);
			menuChildScene.detachChild(parachuteFly50000award);
		} else if (from2) {
			
		} else if (from3) {
			
		}
		if (page == 1) {
			menuChildScene.attachChild(firstJumpAward);
			menuChildScene.attachChild(jumps10award);
			menuChildScene.attachChild(jumps50award);
			menuChildScene.attachChild(jumps100award);
			menuChildScene.attachChild(jumps500award);
			menuChildScene.attachChild(freeFly5000award);
			menuChildScene.attachChild(freeFly15000award);
			menuChildScene.attachChild(freeFly50000award);
			menuChildScene.attachChild(freeFly100000award);
			menuChildScene.attachChild(parachuteFly2500award);
			menuChildScene.attachChild(parachuteFly15000award);
			menuChildScene.attachChild(parachuteFly50000award);
		}
		
		if (!AchievementsHelper.firstJumpAchievement(jumpsCounter)) {
			Sprite locked = new Sprite(95, 35, resourcesManager.lockedAward, vbom);
			firstJumpAward.attachChild(locked);
		}
		if (!AchievementsHelper.jumps10Achievement(jumpsCounter)) {
			Sprite locked = new Sprite(95, 35, resourcesManager.lockedAward, vbom);
			jumps10award.attachChild(locked);
		}
		if (!AchievementsHelper.jumps50Achievement(jumpsCounter)) {
			Sprite locked = new Sprite(95, 35, resourcesManager.lockedAward, vbom);
			jumps50award.attachChild(locked);
		}
		if (!AchievementsHelper.jumps100Achievement(jumpsCounter)) {
			Sprite locked = new Sprite(95, 35, resourcesManager.lockedAward, vbom);
			jumps100award.attachChild(locked);
		}
		if (!AchievementsHelper.jumps500Achievement(jumpsCounter)) {
			Sprite locked = new Sprite(95, 35, resourcesManager.lockedAward, vbom);
			jumps500award.attachChild(locked);
		}
		if (!AchievementsHelper.freeFly5000Achievement(freeFliedCounter)) {
			Sprite locked = new Sprite(95, 35, resourcesManager.lockedAward, vbom);
			freeFly5000award.attachChild(locked);
		}
		if (!AchievementsHelper.freeFly15000Achievement(freeFliedCounter)) {
			Sprite locked = new Sprite(95, 35, resourcesManager.lockedAward, vbom);
			freeFly15000award.attachChild(locked);
		}
		if (!AchievementsHelper.freeFly50000Achievement(freeFliedCounter)) {
			Sprite locked = new Sprite(95, 35, resourcesManager.lockedAward, vbom);
			freeFly50000award.attachChild(locked);
		}
		if (!AchievementsHelper.freeFly100000Achievement(freeFliedCounter)) {
			Sprite locked = new Sprite(95, 35, resourcesManager.lockedAward, vbom);
			freeFly100000award.attachChild(locked);
		}
		if (!AchievementsHelper.parachuteFly2500Achievement(parachuteFliedCounter)) {
			Sprite locked = new Sprite(95, 35, resourcesManager.lockedAward, vbom);
			parachuteFly2500award.attachChild(locked);
		}
		if (!AchievementsHelper.parachuteFly15000Achievement(parachuteFliedCounter)) {
			Sprite locked = new Sprite(95, 35, resourcesManager.lockedAward, vbom);
			parachuteFly15000award.attachChild(locked);
		}
		if (!AchievementsHelper.parachuteFly50000Achievement(parachuteFliedCounter)) {
			Sprite locked = new Sprite(95, 35, resourcesManager.lockedAward, vbom);
			parachuteFly50000award.attachChild(locked);
		}
	}	
	

}

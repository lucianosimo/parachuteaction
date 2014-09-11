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
	
	private int jumpsCounter;
	private int freeFliedCounter;
	private int parachuteFliedCounter;
	private int failedJumpsCounter;
	private int uiCounter;
	private int antigravityCounter;
	private int shieldCounter;
	private int slowCounter;
	private int helicoptersCounter;
	private int balloonsCounter;
	private int birdsCounter;
	
	private Sprite firstJumpAward;
	private Sprite jumps10award;
	private Sprite jumps50award;
	private Sprite jumps100award;
	private Sprite jumps500award;
	private Sprite freeFly5000award;
	private Sprite freeFly15000award;
	private Sprite freeFly50000award;
	private Sprite freeFly100000award;
	private Sprite parachuteFly2500award;
	private Sprite parachuteFly15000award;
	private Sprite parachuteFly50000award;
	private Sprite failJumps5award;
	private Sprite failJumps10award;
	private Sprite failJumps25award;
	private Sprite ui10award;
	private Sprite ui50award;
	private Sprite ui100award;
	private Sprite antigravity10award;
	private Sprite antigravity50award;
	private Sprite antigravity100award;
	private Sprite shield10award;
	private Sprite shield50award;
	private Sprite shield100award;
	private Sprite slow10award;
	private Sprite slow50award;
	private Sprite slow100award;
	private Sprite destroy5helicoptersAward;
	private Sprite destroy25helicoptersAward;
	private Sprite destroy100helicoptersAward;
	private Sprite destroy5balloonsAward;
	private Sprite destroy25balloonsAward;
	private Sprite destroy100balloonsAward;
	private Sprite destroy5birdsAward;
	private Sprite destroy25birdsAward;
	private Sprite destroy100birdsAward;
	
	
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
		background.attachParallaxEntity(new ParallaxEntity(0, new Sprite(resourcesManager.camera.getWidth() / 2, resourcesManager.camera.getHeight() / 2, resourcesManager.achievements_background_region, vbom)));
		this.setBackground(background);
	}
	
	private void createMenuChildScene() {
		float screenWidth = resourcesManager.camera.getWidth();
		float screenHeight = resourcesManager.camera.getHeight();
		
		menuChildScene = new MenuScene(camera);
		menuChildScene.setPosition(screenWidth/2, screenHeight/2);
		
		loadAchievements(1);
		
		final IMenuItem menuButtonItem = new ScaleMenuItemDecorator(new SpriteMenuItem(ACHIVEMENTS_MENU, resourcesManager.achievements_menu_button_region, vbom), 1.2f, 1);
		final IMenuItem statisticsButtonItem = new ScaleMenuItemDecorator(new SpriteMenuItem(ACHIVEMENTS_STATISTICS, resourcesManager.achievements_statistics_button_region, vbom), 1.2f, 1);
		final IMenuItem oneButtonItem = new ScaleMenuItemDecorator(new SpriteMenuItem(ACHIVEMENTS_ONE_BUTTON, resourcesManager.achievements_one_button_region, vbom), 1.2f, 1);
		final IMenuItem twoButtonItem = new ScaleMenuItemDecorator(new SpriteMenuItem(ACHIVEMENTS_TWO_BUTTON, resourcesManager.achievements_two_button_region, vbom), 1.2f, 1);
		final IMenuItem threeButtonItem = new ScaleMenuItemDecorator(new SpriteMenuItem(ACHIVEMENTS_THREE_BUTTON, resourcesManager.achievements_three_button_region, vbom), 1.2f, 1);
				
				
		menuChildScene.addMenuItem(menuButtonItem);
		menuChildScene.addMenuItem(statisticsButtonItem);
		menuChildScene.addMenuItem(oneButtonItem);
		menuChildScene.addMenuItem(twoButtonItem);
		menuChildScene.addMenuItem(threeButtonItem);
		
		menuChildScene.buildAnimations();
		menuChildScene.setBackgroundEnabled(false);
		
		statisticsButtonItem.setPosition(-200, -500);
		menuButtonItem.setPosition(200, -500);
		oneButtonItem.setPosition(-150, -325);
		twoButtonItem.setPosition(0, -325);
		threeButtonItem.setPosition(150, -325);
		
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
				return true;
			case ACHIVEMENTS_TWO_BUTTON:
				loadAchievements(2);
				return true;
			case ACHIVEMENTS_THREE_BUTTON:
				loadAchievements(3);
				return true;
			default:
				return false;
		}
	}
	
	private void loadSavedPreferences() {
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
		jumpsCounter = sharedPreferences.getInt("successfulJumps", 0);
		failedJumpsCounter = sharedPreferences.getInt("unsuccessfulJumps", 0);
		freeFliedCounter = sharedPreferences.getInt("freeFliedMeters", 0);
		parachuteFliedCounter = sharedPreferences.getInt("parachuteFliedMetersCounter", 0);		
		uiCounter = sharedPreferences.getInt("upperImpulseCounter", 0);
		antigravityCounter = sharedPreferences.getInt("antigravityCounter", 0);
		shieldCounter = sharedPreferences.getInt("shieldCounter", 0);
	}
	
	private void loadAchievements(int page) {
		loadSavedPreferences();
		int x1 = -150;
		int x2 = 150;
		
		int y1 = 350;
		int y2 = 250;
		int y3 = 150;
		int y4 = 50;
		int y5 = -50;
		int y6 = -150;
		
		int achievementX = 115;
		int achievementY = 40;
		
		firstJumpAward = new Sprite (x1, y1, resourcesManager.firstJumpAward, vbom);
		jumps10award = new Sprite (x2, y1, resourcesManager.jumps10award, vbom);
		jumps50award = new Sprite (x1, y2, resourcesManager.jumps50award, vbom);
		jumps100award = new Sprite (x2, y2, resourcesManager.jumps100award, vbom);
		jumps500award = new Sprite (x1, y3, resourcesManager.jumps500award, vbom);
		freeFly5000award = new Sprite (x2, y3, resourcesManager.freeFly5000award, vbom);
		freeFly15000award = new Sprite (x1, y4, resourcesManager.freeFly15000award, vbom);
		freeFly50000award = new Sprite (x2, y4, resourcesManager.freeFly50000award, vbom);
		freeFly100000award = new Sprite (x1, y5, resourcesManager.freeFly100000award, vbom);
		parachuteFly2500award = new Sprite (x2, y5, resourcesManager.parachuteFly2500award, vbom);
		parachuteFly15000award = new Sprite (x1, y6, resourcesManager.parachuteFly15000award, vbom);
		parachuteFly50000award = new Sprite (x2, y6, resourcesManager.parachuteFly50000award, vbom);
		failJumps5award = new Sprite (x1, y1, resourcesManager.failJumps5award, vbom);
		failJumps10award = new Sprite (x2, y1, resourcesManager.failJumps10award, vbom);
		failJumps25award = new Sprite (x1, y2, resourcesManager.failJumps25award, vbom);
		ui10award = new Sprite (x2, y2, resourcesManager.ui10award, vbom);
		ui50award = new Sprite (x1, y3, resourcesManager.ui50award, vbom);
		ui100award = new Sprite (x2, y3, resourcesManager.ui100award, vbom);
		antigravity10award = new Sprite (x1, y4, resourcesManager.antigravity10award, vbom);
		antigravity50award = new Sprite (x2, y4, resourcesManager.antigravity50award, vbom);
		antigravity100award = new Sprite (x1, y5, resourcesManager.antigravity100award, vbom);
		shield10award = new Sprite (x2, y5, resourcesManager.shield10award, vbom);
		shield50award = new Sprite (x1, y6, resourcesManager.shield50award, vbom);
		shield100award = new Sprite (x2, y6, resourcesManager.shield100award, vbom);
		slow10award = new Sprite (x1, y1, resourcesManager.slow10award, vbom);
		slow50award = new Sprite (x2, y1, resourcesManager.slow50award, vbom);
		slow100award = new Sprite (x1, y2, resourcesManager.slow100award, vbom);
		destroy5helicoptersAward = new Sprite (x2, y2, resourcesManager.destroy5helicoptersAward, vbom);
		destroy25helicoptersAward = new Sprite (x1, y3, resourcesManager.destroy25helicoptersAward, vbom);
		destroy100helicoptersAward = new Sprite (x2, y3, resourcesManager.destroy100helicoptersAward, vbom);
		destroy5balloonsAward = new Sprite (x1, y4, resourcesManager.destroy5balloonsAward, vbom);
		destroy25balloonsAward = new Sprite (x2, y4, resourcesManager.destroy25balloonsAward, vbom);
		destroy100balloonsAward = new Sprite (x1, y5, resourcesManager.destroy100balloonsAward, vbom);
		destroy5birdsAward = new Sprite (x2, y5, resourcesManager.destroy5birdsAward, vbom);
		destroy25birdsAward = new Sprite (x1, y6, resourcesManager.destroy25birdsAward, vbom);
		destroy100birdsAward = new Sprite (x2, y6, resourcesManager.destroy100birdsAward, vbom);
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
			if (!AchievementsHelper.firstJumpAchievement(jumpsCounter)) {
				Sprite locked = new Sprite(achievementX, achievementY, resourcesManager.achievements_locked_award, vbom);
				firstJumpAward.attachChild(locked);
			}
			if (!AchievementsHelper.jumps10Achievement(jumpsCounter)) {
				Sprite locked = new Sprite(achievementX, achievementY, resourcesManager.achievements_locked_award, vbom);
				jumps10award.attachChild(locked);
			}
			if (!AchievementsHelper.jumps50Achievement(jumpsCounter)) {
				Sprite locked = new Sprite(achievementX, achievementY, resourcesManager.achievements_locked_award, vbom);
				jumps50award.attachChild(locked);
			}
			if (!AchievementsHelper.jumps100Achievement(jumpsCounter)) {
				Sprite locked = new Sprite(achievementX, achievementY, resourcesManager.achievements_locked_award, vbom);
				jumps100award.attachChild(locked);
			}
			if (!AchievementsHelper.jumps500Achievement(jumpsCounter)) {
				Sprite locked = new Sprite(achievementX, achievementY, resourcesManager.achievements_locked_award, vbom);
				jumps500award.attachChild(locked);
			}
			if (!AchievementsHelper.freeFly5000Achievement(freeFliedCounter)) {
				Sprite locked = new Sprite(achievementX, achievementY, resourcesManager.achievements_locked_award, vbom);
				freeFly5000award.attachChild(locked);
			}
			if (!AchievementsHelper.freeFly15000Achievement(freeFliedCounter)) {
				Sprite locked = new Sprite(achievementX, achievementY, resourcesManager.achievements_locked_award, vbom);
				freeFly15000award.attachChild(locked);
			}
			if (!AchievementsHelper.freeFly50000Achievement(freeFliedCounter)) {
				Sprite locked = new Sprite(achievementX, achievementY, resourcesManager.achievements_locked_award, vbom);
				freeFly50000award.attachChild(locked);
			}
			if (!AchievementsHelper.freeFly100000Achievement(freeFliedCounter)) {
				Sprite locked = new Sprite(achievementX, achievementY, resourcesManager.achievements_locked_award, vbom);
				freeFly100000award.attachChild(locked);
			}
			if (!AchievementsHelper.parachuteFly2500Achievement(parachuteFliedCounter)) {
				Sprite locked = new Sprite(achievementX, achievementY, resourcesManager.achievements_locked_award, vbom);
				parachuteFly2500award.attachChild(locked);
			}
			if (!AchievementsHelper.parachuteFly15000Achievement(parachuteFliedCounter)) {
				Sprite locked = new Sprite(achievementX, achievementY, resourcesManager.achievements_locked_award, vbom);
				parachuteFly15000award.attachChild(locked);
			}
			if (!AchievementsHelper.parachuteFly50000Achievement(parachuteFliedCounter)) {
				Sprite locked = new Sprite(achievementX, achievementY, resourcesManager.achievements_locked_award, vbom);
				parachuteFly50000award.attachChild(locked);
			}
		} else if (page == 2) {
			menuChildScene.attachChild(failJumps5award);
			menuChildScene.attachChild(failJumps10award);
			menuChildScene.attachChild(failJumps25award);
			menuChildScene.attachChild(ui10award);
			menuChildScene.attachChild(ui50award);
			menuChildScene.attachChild(ui100award);
			menuChildScene.attachChild(antigravity10award);
			menuChildScene.attachChild(antigravity50award);
			menuChildScene.attachChild(antigravity100award);
			menuChildScene.attachChild(shield10award);
			menuChildScene.attachChild(shield50award);
			menuChildScene.attachChild(shield100award);
			if (!AchievementsHelper.failJumps5Achievement(failedJumpsCounter)) {
				Sprite locked = new Sprite(achievementX, achievementY, resourcesManager.achievements_locked_award, vbom);
				failJumps5award.attachChild(locked);
			}
			if (!AchievementsHelper.failJumps10Achievement(failedJumpsCounter)) {
				Sprite locked = new Sprite(achievementX, achievementY, resourcesManager.achievements_locked_award, vbom);
				failJumps10award.attachChild(locked);
			}
			if (!AchievementsHelper.failJumps25Achievement(failedJumpsCounter)) {
				Sprite locked = new Sprite(achievementX, achievementY, resourcesManager.achievements_locked_award, vbom);
				failJumps25award.attachChild(locked);
			}
			if (!AchievementsHelper.upper10AchievementUnlocked(uiCounter)) {
				Sprite locked = new Sprite(achievementX, achievementY, resourcesManager.achievements_locked_award, vbom);
				ui10award.attachChild(locked);
			}
			if (!AchievementsHelper.upper50AchievementUnlocked(uiCounter)) {
				Sprite locked = new Sprite(achievementX, achievementY, resourcesManager.achievements_locked_award, vbom);
				ui50award.attachChild(locked);
			}
			if (!AchievementsHelper.upper100AchievementUnlocked(uiCounter)) {
				Sprite locked = new Sprite(achievementX, achievementY, resourcesManager.achievements_locked_award, vbom);
				ui100award.attachChild(locked);
			}
			if (!AchievementsHelper.antigravity10AchievementUnlocked(antigravityCounter)) {
				Sprite locked = new Sprite(achievementX, achievementY, resourcesManager.achievements_locked_award, vbom);
				antigravity10award.attachChild(locked);
			}
			if (!AchievementsHelper.antigravity50AchievementUnlocked(antigravityCounter)) {
				Sprite locked = new Sprite(achievementX, achievementY, resourcesManager.achievements_locked_award, vbom);
				antigravity50award.attachChild(locked);
			}
			if (!AchievementsHelper.antigravity100AchievementUnlocked(antigravityCounter)) {
				Sprite locked = new Sprite(achievementX, achievementY, resourcesManager.achievements_locked_award, vbom);
				antigravity100award.attachChild(locked);
			}
			if (!AchievementsHelper.shield10AchievementUnlocked(shieldCounter)) {
				Sprite locked = new Sprite(achievementX, achievementY, resourcesManager.achievements_locked_award, vbom);
				shield10award.attachChild(locked);
			}
			if (!AchievementsHelper.shield50AchievementUnlocked(shieldCounter)) {
				Sprite locked = new Sprite(achievementX, achievementY, resourcesManager.achievements_locked_award, vbom);
				shield50award.attachChild(locked);
			}
			if (!AchievementsHelper.shield100AchievementUnlocked(shieldCounter)) {
				Sprite locked = new Sprite(achievementX, achievementY, resourcesManager.achievements_locked_award, vbom);
				shield100award.attachChild(locked);
			}
		} else if (page == 3) {
			menuChildScene.attachChild(slow10award);
			menuChildScene.attachChild(slow50award);
			menuChildScene.attachChild(slow100award);
			menuChildScene.attachChild(destroy5helicoptersAward);
			menuChildScene.attachChild(destroy25helicoptersAward);
			menuChildScene.attachChild(destroy100helicoptersAward);
			menuChildScene.attachChild(destroy5balloonsAward);
			menuChildScene.attachChild(destroy25balloonsAward);
			menuChildScene.attachChild(destroy100balloonsAward);
			menuChildScene.attachChild(destroy5birdsAward);
			menuChildScene.attachChild(destroy25birdsAward);
			menuChildScene.attachChild(destroy100birdsAward);
			if (!AchievementsHelper.slow10AchievementUnlocked(slowCounter)) {
				Sprite locked = new Sprite(achievementX, achievementY, resourcesManager.achievements_locked_award, vbom);
				slow10award.attachChild(locked);
			}
			if (!AchievementsHelper.slow50AchievementUnlocked(slowCounter)) {
				Sprite locked = new Sprite(achievementX, achievementY, resourcesManager.achievements_locked_award, vbom);
				slow50award.attachChild(locked);
			}
			if (!AchievementsHelper.slow100AchievementUnlocked(slowCounter)) {
				Sprite locked = new Sprite(achievementX, achievementY, resourcesManager.achievements_locked_award, vbom);
				slow100award.attachChild(locked);
			}
			if (!AchievementsHelper.destroy5helicoptersAchievementUnlocked(helicoptersCounter)) {
				Sprite locked = new Sprite(achievementX, achievementY, resourcesManager.achievements_locked_award, vbom);
				destroy5helicoptersAward.attachChild(locked);
			}
			if (!AchievementsHelper.destroy25helicoptersAchievementUnlocked(helicoptersCounter)) {
				Sprite locked = new Sprite(achievementX, achievementY, resourcesManager.achievements_locked_award, vbom);
				destroy25helicoptersAward.attachChild(locked);
			}
			if (!AchievementsHelper.destroy100helicoptersAchievementUnlocked(helicoptersCounter)) {
				Sprite locked = new Sprite(achievementX, achievementY, resourcesManager.achievements_locked_award, vbom);
				destroy100helicoptersAward.attachChild(locked);
			}
			if (!AchievementsHelper.destroy5balloonsAchievementUnlocked(balloonsCounter)) {
				Sprite locked = new Sprite(achievementX, achievementY, resourcesManager.achievements_locked_award, vbom);
				destroy5balloonsAward.attachChild(locked);
			}
			if (!AchievementsHelper.destroy25balloonsAchievementUnlocked(balloonsCounter)) {
				Sprite locked = new Sprite(achievementX, achievementY, resourcesManager.achievements_locked_award, vbom);
				destroy25balloonsAward.attachChild(locked);
			}
			if (!AchievementsHelper.destroy100balloonsAchievementUnlocked(balloonsCounter)) {
				Sprite locked = new Sprite(achievementX, achievementY, resourcesManager.achievements_locked_award, vbom);
				destroy100balloonsAward.attachChild(locked);
			}
			if (!AchievementsHelper.destroy5birdsAchievementUnlocked(birdsCounter)) {
				Sprite locked = new Sprite(achievementX, achievementY, resourcesManager.achievements_locked_award, vbom);
				destroy5birdsAward.attachChild(locked);
			}
			if (!AchievementsHelper.destroy25birdsAchievementUnlocked(birdsCounter)) {
				Sprite locked = new Sprite(achievementX, achievementY, resourcesManager.achievements_locked_award, vbom);
				destroy25birdsAward.attachChild(locked);
			}
			if (!AchievementsHelper.destroy100birdsAchievementUnlocked(birdsCounter)) {
				Sprite locked = new Sprite(achievementX, achievementY, resourcesManager.achievements_locked_award, vbom);
				destroy100birdsAward.attachChild(locked);
			}
		}
		
	}

	@Override
	public void handleOnPause() {
		// TODO Auto-generated method stub
		
	}	
	

}

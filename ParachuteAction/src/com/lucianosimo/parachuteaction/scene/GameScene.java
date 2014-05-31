package com.lucianosimo.parachuteaction.scene;

import java.io.IOException;
import java.util.Iterator;
import java.util.Random;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.handler.physics.PhysicsHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.scene.background.ParallaxBackground;
import org.andengine.entity.scene.background.ParallaxBackground.ParallaxEntity;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.extension.physics.box2d.FixedStepPhysicsWorld;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.SAXUtils;
import org.andengine.util.adt.align.HorizontalAlign;
import org.andengine.util.adt.color.Color;
import org.andengine.util.debug.Debug;
import org.andengine.util.level.EntityLoader;
import org.andengine.util.level.constants.LevelConstants;
import org.andengine.util.level.simple.SimpleLevelEntityLoaderData;
import org.andengine.util.level.simple.SimpleLevelLoader;
import org.andengine.util.modifier.IModifier;
import org.xml.sax.Attributes;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.lucianosimo.parachuteaction.base.BaseScene;
import com.lucianosimo.parachuteaction.helper.AchievementsHelper;
import com.lucianosimo.parachuteaction.manager.ResourcesManager;
import com.lucianosimo.parachuteaction.manager.SceneManager;
import com.lucianosimo.parachuteaction.manager.SceneManager.SceneType;
import com.lucianosimo.parachuteaction.object.Balloon;
import com.lucianosimo.parachuteaction.object.Bird;
import com.lucianosimo.parachuteaction.object.Helicopter;
import com.lucianosimo.parachuteaction.object.LeftHelicopter;
import com.lucianosimo.parachuteaction.object.Player;

public class GameScene extends BaseScene{
	
	//Scene indicators
	private HUD gameHud;
	
	//Counters
	private int fliedMeters = 0;
	private int freeFliedMeters = 0;
	private int parachuteFliedMeters = 0;
	private int distanceToFloor = 0;
	private int oldDistanceToFloor = 0;
	private int distanceToFloorAtOpenParachute = 0;
	private int meterCounterForReduceSpeed = 0;
	private int maxSpeed = 0;
	
	private int upperImpulseCounter = 0;
	private int antigravityCounter = 0;
	private int shieldCounter = 0;
	private int slowCounter = 0;
	private int helicopterCounter = 0;
	private int balloonCounter = 0;
	private int birdCounter = 0;
	
	private int coins = 0;

	//Booleans
	private boolean firstFall = true;
	private boolean openParachute = false;
	private boolean shield = false;
	private boolean openParachuteDistanceSaved = false;
	private boolean loadedCountersBefore = false;
	private boolean availablePause = true;
	private boolean startMoving = false;
	private boolean dead = false;
	
	//Texts variables
	private Text altimeterText;
	private Text levelStartText;
	private Text coinsText;
	
	//Instances
	private Player player;
	private Helicopter helicopter;
	private LeftHelicopter leftHelicopter;
	private Balloon balloon;
	private Bird bird;
	
	//Windows
	private Sprite gameOverWindow;
	private Sprite levelCompleteWindow;
	private Sprite pauseWindow;
	
	//Achievements
	private int antigravityCounterBefore = 0;
	private int antigravityCounterAfter = 0;
	private int upperImpulseCounterBefore = 0;
	private int upperImpulseCounterAfter = 0;
	private int shieldCounterBefore = 0;
	private int shieldCounterAfter = 0;
	private int slowCounterBefore = 0;
	private int slowCounterAfter = 0;
	private int helicoptersCounterBefore = 0;
	private int helicoptersCounterAfter = 0;
	private int balloonsCounterBefore = 0;
	private int balloonsCounterAfter = 0;
	private int birdsCounterBefore = 0;
	private int birdsCounterAfter = 0;
	private int jumpsCounterBefore = 0;
	private int jumpsCounterAfter = 0;
	private int failedJumpsCounterBefore = 0;
	private int failedJumpsCounterAfter = 0;
	private int freeFlyCounterBefore = 0;
	private int freeFlyCounterAfter = 0;
	private int parachuteFlyCounterBefore = 0;
	private int parachuteFlyCounterAfter = 0;	
	
	//Physics world variable
	private PhysicsWorld physicsWorld;
	
	//HUD Buttons
	private Sprite openButton;
	private Sprite greenArrow;
	private Sprite leftHelicopterRedArrow;
	private Sprite helicopterRedArrow;
	private Sprite balloonRedArrow;
	private Sprite birdRedArrow;
	
	//Shield Halo
	private Sprite shieldHalo;
	
	//Parachute
	private Sprite parachute;
	
	//Explosions
	private AnimatedSprite explosion;
	
	//Coins
	private AnimatedSprite coin;
	
	//Constants	
	//16 pixels == 1 meter
	private static final int PIXEL_METER_RATE = 16;
	private static final int LEFT_MARGIN = 0;
	private static final int RIGHT_MARGIN = 480;
	private static final int CLOSER_CLOUD_SPEED = -70;
	private static final int FAR_CLOUD_SPEED = -15;
	private static final int CLOUD_SPEED = -40;
	private static final int PLANE_SPEED = -65;
	private static final int SHIELD_DURATION = 10;
	private static final int ANTIGRAVITY_DURATION = 5;
	private static final int COINS_VALUE = 100;
	
	private static final String TAG_ENTITY = "entity";
	private static final String TAG_ENTITY_ATTRIBUTE_X = "x";
	private static final String TAG_ENTITY_ATTRIBUTE_Y = "y";
	private static final String TAG_ENTITY_ATTRIBUTE_TYPE = "type";
	private static final Object TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_PLAYER = "player";
	private static final Object TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_COIN = "coin";
	private static final Object TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_HELICOPTER = "helicopter";
	private static final Object TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_LEFT_HELICOPTER = "leftHelicopter";
	private static final Object TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_BIRD = "bird";
	private static final Object TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_BALLOON = "balloon";
	private static final Object TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_CLOSER_CLOUD = "closerCloud";
	private static final Object TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_FAR_CLOUD = "farCloud";
	private static final Object TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_CLOUD = "cloud";
	private static final Object TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_SHIELD = "shield";
	private static final Object TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_SLOW = "slow";
	private static final Object TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_UPPER_IMPULSE = "upperImpulse";
	private static final Object TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_ANTIGRAVITY = "antigravity";
	private static final Object TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_PLANE = "plane";
	private static final Object TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_BACK_LOCATION = "backLocation";
	private static final Object TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_LANDING_PLATFORM = "landingPlatform";

	@Override
	public void createScene() {
		//n = rand.nextInt(max - min + 1) + min;
		Random rand = new Random();
		int level = rand.nextInt(3) + 1;
		createBackground();
		createWindows();
		createHud();
		createPhysics();
		loadLevel(level);
		loadCoins();
	}
	
	private void createBackground() {
		ParallaxBackground background = new ParallaxBackground(0, 0, 0);
		background.attachParallaxEntity(new ParallaxEntity(0, new Sprite(240, 427, resourcesManager.background_region, vbom)));
		this.setBackground(background);
	}
	
	private void createWindows() {
		gameOverWindow = new Sprite(camera.getCenterX(), camera.getCenterY(), resourcesManager.game_over_window_region, vbom);
		levelCompleteWindow = new Sprite(camera.getCenterX(), camera.getCenterY(), resourcesManager.level_complete_window_region, vbom);
		pauseWindow = new Sprite(camera.getCenterX(), camera.getCenterY(), resourcesManager.pause_window_region, vbom);
	}
	
	private void createHud() {
		gameHud = new HUD();
		
		altimeterText = new Text(20, 820, resourcesManager.altimeterFont, "Meters to go: 0123456789", new TextOptions(HorizontalAlign.LEFT), vbom);
		coinsText = new Text(20, 770, resourcesManager.coinsFont, "Coins: 0123456789", new TextOptions(HorizontalAlign.LEFT), vbom);
		levelStartText = new Text(100, 420, resourcesManager.levelStartFont, "ForestBeachCityDesertMountainShip - 15:00Hs", new TextOptions(HorizontalAlign.LEFT), vbom);
		
		
		openButton = new Sprite(400, 780, resourcesManager.openButton, vbom){
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.isActionDown()) {
					if (startMoving) {
						openParachute = true;
						destroySprite(openButton);
					}					
				}
				return true;
			}
		};
		
		greenArrow = new Sprite(1000, 0, resourcesManager.green_arrow_region, vbom);
		leftHelicopterRedArrow = new Sprite(1000, 0, resourcesManager.red_arrow_region.deepCopy(), vbom);
		helicopterRedArrow = new Sprite(1000, 0, resourcesManager.red_arrow_region.deepCopy(), vbom);
		balloonRedArrow = new Sprite(1000, 0, resourcesManager.red_arrow_region.deepCopy(), vbom);
		birdRedArrow = new Sprite(1000, 0, resourcesManager.red_arrow_region.deepCopy(), vbom);

		altimeterText.setAnchorCenter(0, 0);
		levelStartText.setAnchorCenter(0, 0);
		coinsText.setAnchorCenter(0, 0);

		altimeterText.setText("Meters to go: ");
		coinsText.setText("Coins: " + coins);
		
		if (MapScene.getLevel() == 1) {
			if (MapScene.getDayOrNight()) {
				coinsText.setColor(Color.BLACK_ARGB_PACKED_INT);
				altimeterText.setColor(Color.BLACK_ARGB_PACKED_INT);
				levelStartText.setText("Beach - 15:00 hs");
			} else {
				levelStartText.setText("Beach - 22:00 hs");
			}			
		} else if (MapScene.getLevel() == 2) {
			if (MapScene.getDayOrNight()) {
				coinsText.setColor(Color.BLACK_ARGB_PACKED_INT);
				altimeterText.setColor(Color.BLACK_ARGB_PACKED_INT);
				levelStartText.setText("City - 15:00 hs");
			} else {
				levelStartText.setText("City - 22:00 hs");
			}
		} else if (MapScene.getLevel() == 3) {
			if (MapScene.getDayOrNight()) {
				coinsText.setColor(Color.BLACK_ARGB_PACKED_INT);
				altimeterText.setColor(Color.BLACK_ARGB_PACKED_INT);
				levelStartText.setText("Forest - 15:00 hs");
			} else {
				levelStartText.setText("Forest - 22:00 hs");
			}
		} else if (MapScene.getLevel() == 4) {
			if (MapScene.getDayOrNight()) {
				coinsText.setColor(Color.BLACK_ARGB_PACKED_INT);
				altimeterText.setColor(Color.BLACK_ARGB_PACKED_INT);
				levelStartText.setText("Desert - 15:00 hs");
			} else {
				levelStartText.setText("Desert - 22:00 hs");
			}
		} else if (MapScene.getLevel() == 5) {
			if (MapScene.getDayOrNight()) {
				coinsText.setColor(Color.BLACK_ARGB_PACKED_INT);
				altimeterText.setColor(Color.BLACK_ARGB_PACKED_INT);
				levelStartText.setText("Mountain - 15:00 hs");
			} else {
				levelStartText.setText("Mountain - 22:00 hs");
			}
		} else if (MapScene.getLevel() == 6) {
			if (MapScene.getDayOrNight()) {;
				coinsText.setColor(Color.BLACK_ARGB_PACKED_INT);
				altimeterText.setColor(Color.BLACK_ARGB_PACKED_INT);
				levelStartText.setText("Ship - 15:00 hs");
			} else {
				levelStartText.setText("Ship - 22:00 hs");
			}
		}

		gameHud.attachChild(altimeterText);
		gameHud.attachChild(levelStartText);
		gameHud.attachChild(coinsText);
		gameHud.attachChild(openButton);
		gameHud.attachChild(greenArrow);
		gameHud.attachChild(leftHelicopterRedArrow);
		gameHud.attachChild(helicopterRedArrow);
		gameHud.attachChild(balloonRedArrow);
		gameHud.attachChild(birdRedArrow);
		
		gameHud.registerTouchArea(openButton);
		
		camera.setHUD(gameHud);
	}
	
	private void createPhysics() {
		physicsWorld = new FixedStepPhysicsWorld(60, new Vector2(0, -3), false);
		physicsWorld.setContactListener(contactListener());
		registerUpdateHandler(physicsWorld);
	}
	
	private void saveUpperImpulseCounter(String key, int upperImpulseCounter) {
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
		Editor editor = sharedPreferences.edit();
		int uiCounter = sharedPreferences.getInt("upperImpulseCounter", 0);
		uiCounter += upperImpulseCounter;
		editor.putInt("upperImpulseCounter", uiCounter);
		editor.commit();
	}
	
	private void saveAntigravityCounter(String key, int antigravityCounter) {
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
		Editor editor = sharedPreferences.edit();
		int agCounter = sharedPreferences.getInt("antigravityCounter", 0);
		agCounter += antigravityCounter;
		editor.putInt("antigravityCounter", agCounter);
		editor.commit();
	}
	
	private void saveShieldCounter(String key, int shieldCounter) {
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
		Editor editor = sharedPreferences.edit();
		int shCounter = sharedPreferences.getInt("shieldCounter", 0);
		shCounter += shieldCounter;
		editor.putInt("shieldCounter", shCounter);
		editor.commit();
	}
	
	private void saveSlowCounter(String key, int slowCounter) {
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
		Editor editor = sharedPreferences.edit();
		int sCounter = sharedPreferences.getInt("slowCounter", 0);
		sCounter += slowCounter;
		editor.putInt("slowCounter", sCounter);
		editor.commit();
	}
	
	private void saveHelicopterCounter(String key, int helicopterCounter) {
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
		Editor editor = sharedPreferences.edit();
		int heliCounter = sharedPreferences.getInt("helicopterCounter", 0);
		heliCounter += helicopterCounter;
		editor.putInt("helicopterCounter", heliCounter);
		editor.commit();
	}
	
	private void saveBalloonCounter(String key, int balloonCounter) {
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
		Editor editor = sharedPreferences.edit();
		int ballCounter = sharedPreferences.getInt("balloonCounter", 0);
		ballCounter += balloonCounter;
		editor.putInt("slowCounter", ballCounter);
		editor.commit();
	}
	
	private void saveBirdCounter(String key, int birdCounter) {
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
		Editor editor = sharedPreferences.edit();
		int bCounter = sharedPreferences.getInt("birdCounter", 0);
		bCounter += birdCounter;
		editor.putInt("slowCounter", bCounter);
		editor.commit();
	}
	
	private void saveFreeFliedMeters(String key, int freeFliedCounter) {
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
		Editor editor = sharedPreferences.edit();
		int ffCounter = sharedPreferences.getInt("freeFliedMetersCounter", 0);
		ffCounter += freeFliedCounter;
		editor.putInt("freeFliedMetersCounter", ffCounter);
		editor.commit();
	}
	
	private void saveParachuteFliedMeters(String key, int parachuteFliedCounter) {
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
		Editor editor = sharedPreferences.edit();
		int pfCounter = sharedPreferences.getInt("parachuteFliedMetersCounter", 0);
		pfCounter += parachuteFliedCounter;
		editor.putInt("parachuteFliedMetersCounter", pfCounter);
		editor.commit();
	}
	
	private void saveUnsuccessfulJumps(String key) {
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
		Editor editor = sharedPreferences.edit();
		int numberOfUnsuccessfulJumps = sharedPreferences.getInt("unsuccessfulJumps", 0);
		numberOfUnsuccessfulJumps++;
		editor.putInt("unsuccessfulJumps", numberOfUnsuccessfulJumps);
		editor.commit();
	}
	
	private void saveSuccessfulJumps(String key) {
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
		Editor editor = sharedPreferences.edit();
		int numberOfSuccessfulJumps = sharedPreferences.getInt("successfulJumps", 0);
		numberOfSuccessfulJumps++;
		editor.putInt("successfulJumps", numberOfSuccessfulJumps);
		editor.commit();
	}
	
	private void saveMaxFliedMeters(String key, int fliedMeters) {
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
		Editor editor = sharedPreferences.edit();
		if (sharedPreferences.getInt("fliedMeters", 0) < fliedMeters) {
			editor.putInt("fliedMeters", fliedMeters);
		}		
		editor.commit();
	}
	
	private void saveMaxSpeed(String key, int maxSpeed) {
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
		Editor editor = sharedPreferences.edit();
		if (sharedPreferences.getInt("maxSpeed", 0) < maxSpeed) {
			editor.putInt("maxSpeed", maxSpeed);
		}		
		editor.commit();
	}
	
	private void saveMaxFreeFliedMeters(String key, int freeFliedMeters) {
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
		Editor editor = sharedPreferences.edit();
		if (sharedPreferences.getInt("freeFliedMeters", 0) < freeFliedMeters) {
			editor.putInt("freeFliedMeters", freeFliedMeters);
		}		
		editor.commit();
	}
	
	private void saveMaxParachuteFliedMeters(String key, int parachuteFliedMeters) {
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
		Editor editor = sharedPreferences.edit();
		if (sharedPreferences.getInt("parachuteFliedMeters", 0) < parachuteFliedMeters) {
			editor.putInt("parachuteFliedMeters", parachuteFliedMeters);
		}		
		editor.commit();
	}
	
	private void saveCoins(String key, int coins) {
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
		Editor editor = sharedPreferences.edit();
		editor.putInt("coins", coins);
		editor.commit();
	}
	
	//Parse level from XML file
		private void loadLevel (int level) {
			final SimpleLevelLoader levelLoader = new SimpleLevelLoader(vbom);
			//final FixtureDef FIXTURE_DEF= PhysicsFactory.createFixtureDef(0, 0f, 0.5f);
			levelLoader.registerEntityLoader(new EntityLoader<SimpleLevelEntityLoaderData>(LevelConstants.TAG_LEVEL) {
				
				@Override
				public IEntity onLoadEntity(String pEntityName, IEntity pParent, Attributes pAttributes, SimpleLevelEntityLoaderData pEntityLoaderData) throws IOException {
					final int width = SAXUtils.getIntAttributeOrThrow(pAttributes, LevelConstants.TAG_LEVEL_ATTRIBUTE_WIDTH);
					final int height = SAXUtils.getIntAttributeOrThrow(pAttributes, LevelConstants.TAG_LEVEL_ATTRIBUTE_HEIGHT);
					camera.setBounds(0, 0, width, height);
					camera.setBoundsEnabled(true);
					return GameScene.this;
				}
			});
			levelLoader.registerEntityLoader(new EntityLoader<SimpleLevelEntityLoaderData>(TAG_ENTITY) {

				@Override
				public IEntity onLoadEntity(String pEntityName, IEntity pParent, Attributes pAttributes, SimpleLevelEntityLoaderData pEntityLoaderData)	throws IOException {
					final int x = SAXUtils.getIntAttributeOrThrow(pAttributes, TAG_ENTITY_ATTRIBUTE_X);
					final int y = SAXUtils.getIntAttributeOrThrow(pAttributes, TAG_ENTITY_ATTRIBUTE_Y);
					final String type = SAXUtils.getAttributeOrThrow(pAttributes, TAG_ENTITY_ATTRIBUTE_TYPE);
					
					final Sprite levelObject;
					
					if (type.equals(TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_CLOUD)) {
						Random rand = new Random();
						int randX = rand.nextInt(441) - 220;
						int randY = rand.nextInt(201) - 100;
						levelObject = new Sprite(x + randX, y + randY, resourcesManager.cloud_region, vbom) {
							protected void onManagedUpdate(float pSecondsElapsed) {
								super.onManagedUpdate(pSecondsElapsed);
								if (this.getX() < LEFT_MARGIN - 166) {
									this.setPosition(RIGHT_MARGIN + 166, y);
								}
							};
						};
						PhysicsHandler handler = new PhysicsHandler(levelObject);
						levelObject.registerUpdateHandler(handler);
						handler.setVelocity(CLOUD_SPEED,0);
					} else if (type.equals(TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_CLOSER_CLOUD)) {
						Random rand = new Random();
						int randX = rand.nextInt(441) - 220;
						int randY = rand.nextInt(201) - 100;
						levelObject = new Sprite(x + randX, y + randY, resourcesManager.closerCloud_region, vbom) {
							protected void onManagedUpdate(float pSecondsElapsed) {
								super.onManagedUpdate(pSecondsElapsed);
								if (this.getX() < LEFT_MARGIN - 166) {
									this.setPosition(RIGHT_MARGIN + 166, y);
								}
							};
						};
						PhysicsHandler handler = new PhysicsHandler(levelObject);
						levelObject.registerUpdateHandler(handler);
						handler.setVelocity(CLOSER_CLOUD_SPEED,0);
					} 
					else if (type.equals(TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_FAR_CLOUD)) {
						Random rand = new Random();
						int randX = rand.nextInt(441) - 220;
						int randY = rand.nextInt(201) - 100;
						levelObject = new Sprite(x + randX, y + randY, resourcesManager.farCloud_region, vbom) {
							protected void onManagedUpdate(float pSecondsElapsed) {
								super.onManagedUpdate(pSecondsElapsed);
								if (this.getX() < LEFT_MARGIN - 166) {
									this.setPosition(RIGHT_MARGIN + 166, y);
								}
							};
						};
						PhysicsHandler handler = new PhysicsHandler(levelObject);
						levelObject.registerUpdateHandler(handler);
						handler.setVelocity(FAR_CLOUD_SPEED,0);
					} else if (type.equals(TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_PLANE)) {
						levelObject = new Sprite(x, y, resourcesManager.plane_region, vbom) {
							protected void onManagedUpdate(float pSecondsElapsed) {
								super.onManagedUpdate(pSecondsElapsed);
								if (this.getX() < 200) {
									if (!startMoving) {
										startMoving = true;
										player.setVisible(true);
										gameHud.detachChild(levelStartText);
									}									
								}
							};
						};
						PhysicsHandler handler = new PhysicsHandler(levelObject);
						levelObject.registerUpdateHandler(handler);
						handler.setVelocity(PLANE_SPEED,0);
					} else if (type.equals(TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_BACK_LOCATION)) {
						levelObject = new Sprite(x, y, resourcesManager.back_location_region, vbom);
					} else if (type.equals(TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_COIN)) {
						coin = new AnimatedSprite(x, y, resourcesManager.coin_region, vbom) {
							protected void onManagedUpdate(float pSecondsElapsed) {
								super.onManagedUpdate(pSecondsElapsed);
								if (player.collidesWith(this)) {
									destroySprite(this);
									addCoins(COINS_VALUE);
								}
							};
						};
						final long[] COIN_ANIMATE = new long[] {100, 100, 100, 100};
						coin.animate(COIN_ANIMATE, 0, 3, true);
						levelObject = coin;
					} else if (type.equals(TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_SHIELD)) {
						Random rand = new Random();
						int randX = rand.nextInt(441) - 220;
						levelObject = new Sprite(x + randX, y, resourcesManager.shield_region, vbom) {
							protected void onManagedUpdate(float pSecondsElapsed) {
								super.onManagedUpdate(pSecondsElapsed);
								if ((player.getY() - this.getY()) < 1500 && (player.getY() - this.getY()) > 427) {
									greenArrow.setPosition(this.getX(), 75);
								} else if ((player.getY() - this.getY()) < 427 && (player.getY() - this.getY()) > 0) {
									greenArrow.setPosition(1000, 0);
								}
								if (player.collidesWith(this)) {
									shieldCounter++;
									destroySprite(this);
									player.registerEntityModifier(new DelayModifier(SHIELD_DURATION, new IEntityModifierListener() {
										
										@Override
										public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
											shield = true;											
										}
										
										@Override
										public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
											shield = false;
											
										}
									}));
								}
							};
						};
					} else if (type.equals(TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_UPPER_IMPULSE)) {
						//n = rand.nextInt(max - min + 1) + min;
						Random rand = new Random();
						int randX = rand.nextInt(441) - 220;
						int randY = rand.nextInt(5001) - 2500;
						levelObject = new Sprite(x + randX, y + randY, resourcesManager.upperImpulse_region, vbom) {
							protected void onManagedUpdate(float pSecondsElapsed) {
								super.onManagedUpdate(pSecondsElapsed);
								if ((player.getY() - this.getY()) < 1500 && (player.getY() - this.getY()) > 427) {
									greenArrow.setPosition(this.getX(), 75);
								} else if ((player.getY() - this.getY()) < 427 && (player.getY() - this.getY()) > 0) {
									greenArrow.setPosition(1000, 0);
								}
								if (player.collidesWith(this)) {
									upperImpulseCounter++;
									player.upperImpulse();
									destroySprite(this);
								}
							};
						};
					} else if (type.equals(TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_ANTIGRAVITY)) {
						Random rand = new Random();
						int randX = rand.nextInt(441) - 220;
						int randY = rand.nextInt(5001) - 2500;
						levelObject = new Sprite(x + randX, y + randY, resourcesManager.antiGravity_region, vbom) {
							protected void onManagedUpdate(float pSecondsElapsed) {
								super.onManagedUpdate(pSecondsElapsed);
								if ((player.getY() - this.getY()) < 1500 && (player.getY() - this.getY()) > 427) {
									greenArrow.setPosition(this.getX(), 75);
								} else if ((player.getY() - this.getY()) < 427 && (player.getY() - this.getY()) > 0) {
									greenArrow.setPosition(1000, 0);
								}
								if (player.collidesWith(this)) {
									antigravityCounter++;
									destroySprite(this);
									player.registerEntityModifier(new DelayModifier(ANTIGRAVITY_DURATION, new IEntityModifierListener() {
										
										@Override
										public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
											physicsWorld.setGravity(new Vector2(0, 10));										
										}
										
										@Override
										public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
											physicsWorld.setGravity(new Vector2(0, -3));											
										}
									}));
																		
								}
							};
						};
					} else if (type.equals(TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_SLOW)) {
						Random rand = new Random();
						int randX = rand.nextInt(441) - 220;
						int randY = rand.nextInt(5001) - 2500;
						levelObject = new Sprite(x + randX, y + randY, resourcesManager.slow_region, vbom) {
							protected void onManagedUpdate(float pSecondsElapsed) {
								super.onManagedUpdate(pSecondsElapsed);
								if ((player.getY() - this.getY()) < 1500 && (player.getY() - this.getY()) > 427) {
									greenArrow.setPosition(this.getX(), 75);
								} else if ((player.getY() - this.getY()) < 427 && (player.getY() - this.getY()) > 0) {
									greenArrow.setPosition(1000, 0);
								}
								if (player.collidesWith(this)) {
									slowCounter++;
									destroySprite(this);
									player.slowDownPlayer();
								}
							};
						};
					} else if (type.equals(TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_HELICOPTER)) {
						//n = rand.nextInt(max - min + 1) + min;
						explosion = new AnimatedSprite(0, 0, resourcesManager.explosion_region.deepCopy(), vbom);
						explosion.setVisible(false);
						helicopter = new Helicopter(x, y, vbom, camera, physicsWorld, resourcesManager.helicopter_region.deepCopy()) {
							protected void onManagedUpdate(float pSecondsElapsed) {
								super.onManagedUpdate(pSecondsElapsed);
								if ((player.getY() - this.getY()) < 1000 && (player.getY() - this.getY()) > 427) {
									helicopterRedArrow.setPosition(this.getX(), 75);
								} else if ((player.getY() - this.getY()) < 427 && (player.getY() - this.getY()) > 0) {
									helicopterRedArrow.setPosition(1000, 0);
								}
								if ((player.getY() - this.getY()) < 500) {
									this.startMoving();
								}
								if (player.collidesWith(this)) {
									if (shield) {
										final Sprite helicopterRef = this; 
										this.setVisible(false);
										explosion.setPosition(helicopterRef.getX(),helicopterRef.getY());
										explosion.setVisible(true);
										final long[] EXPLOSION_ANIMATE = new long[] {100, 100, 100, 100, 100, 100};
										explosion.animate(EXPLOSION_ANIMATE, 0, 5, false);
										destroyBodyWithSprite(helicopterRef);										
									}									
								}
							};
						};
						GameScene.this.attachChild(explosion);
						levelObject = helicopter;
					} else if (type.equals(TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_LEFT_HELICOPTER)) {
						explosion = new AnimatedSprite(0, 0, resourcesManager.explosion_region.deepCopy(), vbom);
						explosion.setVisible(false);
						leftHelicopter = new LeftHelicopter(x, y, vbom, camera, physicsWorld, resourcesManager.leftHelicopter_region.deepCopy()) {
							protected void onManagedUpdate(float pSecondsElapsed) {
								super.onManagedUpdate(pSecondsElapsed);
								if ((player.getY() - this.getY()) < 1000 && (player.getY() - this.getY()) > 427) {
									leftHelicopterRedArrow.setPosition(this.getX(), 75);
								} else if ((player.getY() - this.getY()) < 427 && (player.getY() - this.getY()) > 0) {
									leftHelicopterRedArrow.setPosition(1000, 0);
								}
								if ((player.getY() - this.getY()) < 500) {
									this.startMoving();
								}
								if (player.collidesWith(this)) {
									if (shield) {
										final Sprite helicopterRef = this; 
										this.setVisible(false);
										explosion.setPosition(helicopterRef.getX(),helicopterRef.getY());
										explosion.setVisible(true);
										final long[] EXPLOSION_ANIMATE = new long[] {100, 100, 100, 100, 100, 100};
										explosion.animate(EXPLOSION_ANIMATE, 0, 5, false);
										destroyBodyWithSprite(helicopterRef);
									}									
								}
							};
						};
						GameScene.this.attachChild(explosion);
						levelObject = leftHelicopter;
					} else if (type.equals(TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_BIRD)) {
						bird = new Bird(x, y, vbom, camera, physicsWorld, resourcesManager.bird_region.deepCopy()) {
							
							protected void onManagedUpdate(float pSecondsElapsed) {
								super.onManagedUpdate(pSecondsElapsed);
								if ((player.getY() - this.getY()) < 1000 && (player.getY() - this.getY()) > 427) {
									birdRedArrow.setPosition(this.getX(), 75);
								} else if ((player.getY() - this.getY()) < 427 && (player.getY() - this.getY()) > 0) {
									birdRedArrow.setPosition(1000, 0);
								}
								if ((player.getY() - this.getY()) < 400) {
									this.startMoving();
								}
								if (player.collidesWith(this)) {
									if (shield) {
										final Sprite birdRef = this; 
										this.setVisible(false);
										destroyBodyWithSprite(birdRef);
									}									
								}
							};
						};
						levelObject = bird;
					} else if (type.equals(TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_BALLOON)) {
						explosion = new AnimatedSprite(0, 0, resourcesManager.explosion_region.deepCopy(), vbom);
						explosion.setVisible(false);
						Random rand = new Random();
						int randX = rand.nextInt(441) - 220;
						balloon = new Balloon(x + randX, y, vbom, camera, physicsWorld, resourcesManager.balloon_region.deepCopy()) {
							
							protected void onManagedUpdate(float pSecondsElapsed) {
								super.onManagedUpdate(pSecondsElapsed);
								if ((player.getY() - this.getY()) < 1500) {
									this.startMoving();
								}
								if ((player.getY() - this.getY()) < 1000 && (player.getY() - this.getY()) > 427) {
									balloonRedArrow.setPosition(this.getX(), 75);
								} else if ((player.getY() - this.getY()) < 427 && (player.getY() - this.getY()) > 0) {
									balloonRedArrow.setPosition(1000, 0);
								}
								if (player.collidesWith(this)) {
									if (shield) {
										final Sprite balloonRef = this; 
										this.setVisible(false);
										explosion.setPosition(balloonRef.getX(),balloonRef.getY());
										explosion.setVisible(true);
										final long[] EXPLOSION_ANIMATE = new long[] {100, 100, 100, 100, 100, 100};
										explosion.animate(EXPLOSION_ANIMATE, 0, 5, false);
										destroyBodyWithSprite(balloonRef);
									}									
								}
							};
						};
						GameScene.this.attachChild(explosion);
						levelObject = balloon;
					} else if (type.equals(TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_PLAYER)) {
						parachute = new Sprite(18, 110, ResourcesManager.getInstance().parachute_region, vbom);
						shieldHalo = new Sprite(20, 50, resourcesManager.shield_region, vbom);
						parachute.setVisible(false);
						shieldHalo.setVisible(false);
						player = new Player(x, y, vbom, camera, physicsWorld) {
							
							@Override
							protected void onManagedUpdate(float pSecondsElapsed) {
								super.onManagedUpdate(pSecondsElapsed);
								engine.runOnUpdateThread(new Runnable() {
									
									@Override
									public void run() {
										if (!startMoving) {
											player.stopPlayer();
											player.setVisible(false);
										}
										if (shield) {
											shieldHalo.setVisible(true);
											if (openParachute) {
												shieldHalo.setPosition(20, 77);
											}
										} else {
											shieldHalo.setVisible(false);
										}
										if (player.getPlayerSpeed() > maxSpeed) {
											maxSpeed = (int) player.getPlayerSpeed();
										}
										distanceToFloor = (int) player.getY() / PIXEL_METER_RATE;
										if (firstFall) {
											oldDistanceToFloor = distanceToFloor;
										}
										firstFall = false;
										altimeterText.setText("Meters to go: " + distanceToFloor);
										if (player.getFallVelocity() < 0) {
											fliedMeters = fliedMeters + (oldDistanceToFloor - distanceToFloor);
										}
										if (distanceToFloor < 1500 && distanceToFloor > 1000 && !openParachute) {
											openButton.setVisible(true);
										} else {
											openButton.setVisible(false);
										}
										if (openParachute) {
											parachute.setVisible(true);
											player.openParachute();
											parachuteFliedMeters = fliedMeters - freeFliedMeters;
											if (!openParachuteDistanceSaved) {
												openParachuteDistanceSaved = true;
												distanceToFloorAtOpenParachute = distanceToFloor;
												meterCounterForReduceSpeed = distanceToFloor;
											}
											if (distanceToFloor == meterCounterForReduceSpeed - 100) {
												meterCounterForReduceSpeed -= 100;
												player.reduceParachuteSpeed();
											}
										} else {
											distanceToFloorAtOpenParachute = 0;
											freeFliedMeters = fliedMeters;
										}
										oldDistanceToFloor = distanceToFloor;
									}
								});
							};
							
							@Override
							public void onDie() {
								engine.runOnUpdateThread(new Runnable() {
									
									@Override
									public void run() {
										saveUnsuccessfulJumps("unsuccessfulJumps");
										GameScene.this.setIgnoreUpdate(true);
								        camera.setChaseEntity(null);
								        availablePause = false;
								        dead = true;
								        gameOverWindow.setPosition(camera.getCenterX(), camera.getCenterY());
										GameScene.this.attachChild(gameOverWindow);
									    final Sprite retryButton = new Sprite(345, 45, resourcesManager.retry_button_region, vbom){
									    	public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
									    		if (pSceneTouchEvent.isActionDown()) {
									    			gameHud.dispose();
													gameHud.setVisible(false);
													detachChild(gameHud);
													myGarbageCollection();
													SceneManager.getInstance().loadGameScene(engine, GameScene.this);
												}
									    		return true;
									    	};
									    };
									    final Sprite mapButton = new Sprite(220, 45, resourcesManager.map_button_region, vbom){
									    	public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
									    		if (pSceneTouchEvent.isActionDown()) {
									    			gameHud.dispose();
													gameHud.setVisible(false);
													detachChild(gameHud);
													myGarbageCollection();
													SceneManager.getInstance().loadMapScene(engine, GameScene.this);
												}
									    		return true;
									    	};
									    };
									    final Sprite quitButton = new Sprite(95, 45, resourcesManager.quit_button_region, vbom){
									    	public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
									    		if (pSceneTouchEvent.isActionDown()) {
									    			gameHud.dispose();
													gameHud.setVisible(false);
													detachChild(gameHud);
													myGarbageCollection();
													SceneManager.getInstance().loadMenuScene(engine, GameScene.this);
									    		}
									    		return true;
									    	};
									    };
									    GameScene.this.registerTouchArea(retryButton);
									    GameScene.this.registerTouchArea(quitButton);
									    GameScene.this.registerTouchArea(mapButton);
									    gameOverWindow.attachChild(quitButton);
									    gameOverWindow.attachChild(retryButton);
									    gameOverWindow.attachChild(mapButton);
									}
								});
								
							}
							
						};
						player.attachChild(parachute);
						player.attachChild(shieldHalo);
						levelObject = player;
					} else if (type.equals(TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_LANDING_PLATFORM)) {
						levelObject = new Sprite(x, y, resourcesManager.landing_platfom_region, vbom) {
							protected void onManagedUpdate(float pSecondsElapsed) {
								super.onManagedUpdate(pSecondsElapsed);
								if (player.collidesWith(this)) {
								engine.runOnUpdateThread(new Runnable() {
									@Override
									public void run() {
										if (distanceToFloorAtOpenParachute >= 1000) {
											loadCounters();
											loadedCountersBefore = true;
											saveScoreData();
											loadCounters();
											availablePause = false;
											displayLevelCompleted();
										}
										if (distanceToFloorAtOpenParachute < 1000){
											player.killPlayer();
										}
										if (!openParachute) {
											player.killPlayer();
										}
									}
								});
								}
							};
						};
					} else {
						throw new IllegalArgumentException();
					}
					levelObject.setCullingEnabled(true);				
					return levelObject;
				}
			});
			levelLoader.loadLevelFromAsset(activity.getAssets(), "level/" + level + ".xml");
		}
	
	private ContactListener contactListener() {
		ContactListener contactListener = new ContactListener() {
			
			@Override
			public void preSolve(Contact contact, Manifold oldManifold) {
			}
			
			@Override
			public void postSolve(Contact contact, ContactImpulse impulse) {
			}
			
			@Override
			public void endContact(Contact contact) {
				
			}
			
			@Override
			public void beginContact(Contact contact) {
				final Fixture x1 = contact.getFixtureA();
				final Fixture x2 = contact.getFixtureB();
				
				if (x1.getBody().getUserData().equals("bird") && x2.getBody().getUserData().equals("player")) {
					if (shield) {
						engine.runOnUpdateThread(new Runnable() {
							@Override
							public void run() {
								physicsWorld.destroyBody(x1.getBody());	
							}
						});
					} else {
						player.killPlayer();
						setInactiveBody(x1.getBody());
					}
					
				}
				
				if (x1.getBody().getUserData().equals("helicopter") && x2.getBody().getUserData().equals("player")) {
					if (shield) {
						engine.runOnUpdateThread(new Runnable() {
							@Override
							public void run() {
								physicsWorld.destroyBody(x1.getBody());	
							}
						});
					} else {
						player.killPlayer();
						setInactiveBody(x1.getBody());
					}
					
				}
				
				if (x1.getBody().getUserData().equals("leftHelicopter") && x2.getBody().getUserData().equals("player")) {
					if (shield) {
						engine.runOnUpdateThread(new Runnable() {
							@Override
							public void run() {
								physicsWorld.destroyBody(x1.getBody());	
							}
						});
					} else {
						player.killPlayer();
						setInactiveBody(x1.getBody());
					}
					
				}
				
				if (x1.getBody().getUserData().equals("balloon") && x2.getBody().getUserData().equals("player")) {
					if (shield) {
						engine.runOnUpdateThread(new Runnable() {
							@Override
							public void run() {
								physicsWorld.destroyBody(x1.getBody());															
							}
						});
					} else {
						player.killPlayer();
						setInactiveBody(x1.getBody());
					}
				}
				
			}
		};
		return contactListener;
	}

	@Override
	public SceneType getSceneType() {
		return SceneType.SCENE_GAME;
	}

	@Override
	public void disposeScene() {
		
	}
	
	private void setInactiveBody(final Body body) {
		engine.runOnUpdateThread(new Runnable() {
			
			@Override
			public void run() {
				body.setActive(false);
			}
		});
	}
	
	private void destroyBodyWithSprite(final Sprite sp) {
		engine.runOnUpdateThread(new Runnable() {
			
			@Override
			public void run() {
				final PhysicsConnector pc = physicsWorld.getPhysicsConnectorManager().findPhysicsConnectorByShape(sp);
				physicsWorld.unregisterPhysicsConnector(pc);
				Body body = pc.getBody();
				body.setActive(false);
				physicsWorld.destroyBody(body);
				GameScene.this.unregisterTouchArea(sp);
				GameScene.this.detachChild(sp);
			}
		});
	}
	
	private void destroySprite(final Sprite sp) {
		engine.runOnUpdateThread(new Runnable() {
			
			@Override
			public void run() {
				sp.setVisible(false);
				GameScene.this.detachChild(sp);
			}
		});
	}
	
	private void myGarbageCollection() {
		Iterator<Body> allMyBodies = physicsWorld.getBodies();
        while(allMyBodies.hasNext()) {
        	try {
        		final Body myCurrentBody = allMyBodies.next();
                	physicsWorld.destroyBody(myCurrentBody);                
            } catch (Exception e) {
            	Debug.e(e);
            }
        }
               
        this.clearChildScene();
        this.detachChildren();
        this.reset();
        this.detachSelf();
        physicsWorld.clearForces();
        physicsWorld.clearPhysicsConnectors();
        physicsWorld.reset();
 
        System.gc();
	}
	
	private void loadCoins() {
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
		this.coins = sharedPreferences.getInt("coins", 0);
		coinsText.setText("Coins: " + this.coins);
		player.addPlayerCoins(this.coins);
	}
	
	private void addCoins(int coins) {
		player.addPlayerCoins(coins);
		coinsText.setText("Coins: " + player.getPlayerCoins());
	}
	
	private void loadCounters() {
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
		int antigravityCounter = sharedPreferences.getInt("antigravityCounter", 0);
		int shieldCounter = sharedPreferences.getInt("shieldCounter", 0);
		int slowCounter = sharedPreferences.getInt("slowCounter", 0);
		int helicopterCounter = sharedPreferences.getInt("helicopterCounter", 0);
		int balloonCounter = sharedPreferences.getInt("balloonCounter", 0);
		int birdCounter = sharedPreferences.getInt("birdCounter", 0);
		int jumpsCounter = sharedPreferences.getInt("successfulJumps", 0);
		int freeFliedCounter = sharedPreferences.getInt("freeFliedMetersCounter", 0);
		int parachuteFliedCounter = sharedPreferences.getInt("parachuteFliedMeters", 0);
		int failedJumpsCounter = sharedPreferences.getInt("unsuccessfulJumps", 0);
		int upperImpulseCounter = sharedPreferences.getInt("upperImpulseCounter", 0);
		if (!loadedCountersBefore) {
			upperImpulseCounterBefore = upperImpulseCounter;
			antigravityCounterBefore = antigravityCounter;
			shieldCounterBefore = shieldCounter;
			slowCounterBefore = slowCounter;
			helicoptersCounterBefore = helicopterCounter;
			balloonsCounterBefore = balloonCounter;
			birdsCounterBefore = birdCounter;
			jumpsCounterBefore = jumpsCounter;
			failedJumpsCounterBefore = failedJumpsCounter;
			freeFlyCounterBefore = freeFliedCounter;
			parachuteFlyCounterBefore = parachuteFliedCounter;
		} else {
			upperImpulseCounterAfter = upperImpulseCounter;
			antigravityCounterAfter = antigravityCounter;
			shieldCounterAfter = shieldCounter;
			slowCounterAfter = slowCounter;
			helicoptersCounterAfter = helicopterCounter;
			balloonsCounterAfter = balloonCounter;
			birdsCounterAfter = birdCounter;
			jumpsCounterAfter = jumpsCounter;
			failedJumpsCounterAfter = failedJumpsCounter;
			freeFlyCounterAfter = freeFliedCounter;
			parachuteFlyCounterAfter = parachuteFliedCounter;
		}			
	}
	
	@Override
	public void onBackKeyPressed() {
		engine.runOnUpdateThread(new Runnable() {
			
			@Override
			public void run() {
				if (availablePause) {
					availablePause = false;
					GameScene.this.setIgnoreUpdate(true);
					camera.setChaseEntity(null);
					pauseWindow.setPosition(camera.getCenterX(), camera.getCenterY());
					GameScene.this.attachChild(pauseWindow);
					gameHud.setVisible(false);
					final Sprite resumeButton = new Sprite(345, 45, resourcesManager.resume_button_region, vbom){
				    	public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				    		if (pSceneTouchEvent.isActionDown()) {
				    			gameHud.setVisible(true);
				    			availablePause = true;
				    			GameScene.this.detachChild(pauseWindow);
				    			GameScene.this.setIgnoreUpdate(false);
				    			camera.setChaseEntity(player);
				    		}
				    		return true;
				    	};
				    };
				    final Sprite mapButton = new Sprite(220, 45, resourcesManager.map_button_region, vbom){
				    	public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				    		if (pSceneTouchEvent.isActionDown()) {
				    			gameHud.dispose();
								gameHud.setVisible(false);
								detachChild(gameHud);
								myGarbageCollection();
								SceneManager.getInstance().loadMapScene(engine, GameScene.this);
							}
				    		return true;
				    	};
				    };
				    final Sprite quitButton = new Sprite(95, 45, resourcesManager.quit_button_region, vbom){
				    	public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				    		if (pSceneTouchEvent.isActionDown()) {
				    			gameHud.dispose();
								gameHud.setVisible(false);
								detachChild(gameHud);
								myGarbageCollection();
								SceneManager.getInstance().loadMenuScene(engine, GameScene.this);
				    		}
				    		return true;
				    	};
				    };
				    GameScene.this.registerTouchArea(resumeButton);
				    GameScene.this.registerTouchArea(quitButton);
				    GameScene.this.registerTouchArea(mapButton);
				    pauseWindow.attachChild(quitButton);
				    pauseWindow.attachChild(resumeButton);
				    pauseWindow.attachChild(mapButton);
				} else {
					if (!dead) {
						availablePause = true;
						gameHud.setVisible(true);
						GameScene.this.detachChild(pauseWindow);
		    			GameScene.this.setIgnoreUpdate(false);
		    			camera.setChaseEntity(player);
					}					
				}				
			}
		});
		
	}
	
	private void displayLevelCompleted() {
		GameScene.this.setIgnoreUpdate(true);
        camera.setChaseEntity(null);
        availablePause = false;
        levelCompleteWindow.setPosition(camera.getCenterX(), camera.getCenterY());
		GameScene.this.attachChild(levelCompleteWindow);
	    final Sprite flyAgainButton = new Sprite(345, 45, resourcesManager.fly_again_button_region, vbom){
	    	public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
	    		if (pSceneTouchEvent.isActionDown()) {
	    			gameHud.dispose();
					gameHud.setVisible(false);
					detachChild(gameHud);
					myGarbageCollection();
					SceneManager.getInstance().loadGameScene(engine, GameScene.this);
				}
	    		return true;
	    	};
	    };
	    final Sprite mapButton = new Sprite(220, 45, resourcesManager.map_button_region, vbom){
	    	public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
	    		if (pSceneTouchEvent.isActionDown()) {
	    			gameHud.dispose();
					gameHud.setVisible(false);
					detachChild(gameHud);
					myGarbageCollection();
					SceneManager.getInstance().loadMapScene(engine, GameScene.this);
				}
	    		return true;
	    	};
	    };
	    final Sprite quitButton = new Sprite(95, 45, resourcesManager.quit_button_region, vbom){
	    	public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
	    		if (pSceneTouchEvent.isActionDown()) {
	    			gameHud.dispose();
					gameHud.setVisible(false);
					detachChild(gameHud);
					myGarbageCollection();
					SceneManager.getInstance().loadMenuScene(engine, GameScene.this);
	    		}
	    		return true;
	    	};
	    };
	    Text levelCompleted = new Text(camera.getCenterX(), camera.getCenterY() + 50, resourcesManager.levelCompletedFont, "Youlandedsafely: 0123456789 Youfreefliedmeters", new TextOptions(HorizontalAlign.LEFT), vbom);
		Text maxSpeed = new Text(camera.getCenterX(), camera.getCenterY(), resourcesManager.maxSpeedFont, "Yourmaxspeedwas: 0123456789", new TextOptions(HorizontalAlign.LEFT), vbom);
		levelCompleted.setText("You landed safely!! You flied " + freeFliedMeters + " meters");
		maxSpeed.setText("Your max speed was: " + GameScene.this.maxSpeed);
		GameScene.this.registerTouchArea(flyAgainButton);
	    GameScene.this.registerTouchArea(quitButton);
	    GameScene.this.registerTouchArea(mapButton);
	    levelCompleteWindow.attachChild(quitButton);
	    levelCompleteWindow.attachChild(flyAgainButton);
	    levelCompleteWindow.attachChild(mapButton);
	    GameScene.this.attachChild(levelCompleted);
	    GameScene.this.attachChild(maxSpeed);
	    displayAchievements();
	}
	
	private void displayAchievements() {
		boolean newAchievement = false;
		
		if (AchievementsHelper.firstJumpAchievementUnlockedInLevel(jumpsCounterBefore, jumpsCounterAfter)) {
			newAchievement = true;
		} else if (AchievementsHelper.jumps10AchievementUnlockedInLevel(jumpsCounterBefore, jumpsCounterAfter)) {
			newAchievement = true;
		} else if (AchievementsHelper.jumps50AchievementUnlockedInLevel(jumpsCounterBefore, jumpsCounterAfter)) {
			newAchievement = true;
		} else if (AchievementsHelper.jumps100AchievementUnlockedInLevel(jumpsCounterBefore, jumpsCounterAfter)) {
			newAchievement = true;
		} else if (AchievementsHelper.jumps500AchievementUnlockedInLevel(jumpsCounterBefore, jumpsCounterAfter)) {
			newAchievement = true;
		} else if (AchievementsHelper.freeFly5000AchievementUnlockedInLevel(freeFlyCounterBefore, freeFlyCounterAfter)) {
			newAchievement = true;
		} else if (AchievementsHelper.freeFly15000AchievementUnlockedInLevel(freeFlyCounterBefore, freeFlyCounterAfter)) {
			newAchievement = true;
		} else if (AchievementsHelper.freeFly50000AchievementUnlockedInLevel(freeFlyCounterBefore, freeFlyCounterAfter)) {
			newAchievement = true;
		} else if (AchievementsHelper.freeFly100000AchievementUnlockedInLevel(freeFlyCounterBefore, freeFlyCounterAfter)) {
			newAchievement = true;
		} else if (AchievementsHelper.parachuteFly2500AchievementUnlockedInLevel(parachuteFlyCounterBefore, parachuteFlyCounterAfter)) {
			newAchievement = true;
		} else if (AchievementsHelper.parachuteFly15000AchievementUnlockedInLevel(parachuteFlyCounterBefore, parachuteFlyCounterAfter)) {
			newAchievement = true;
		} else if (AchievementsHelper.parachuteFly50000AchievementUnlockedInLevel(parachuteFlyCounterBefore, parachuteFlyCounterAfter)) {
			newAchievement = true;
		} else if (AchievementsHelper.failJumps5AchievementUnlockedInLevel(failedJumpsCounterBefore, failedJumpsCounterAfter)) {
			newAchievement = true;
		} else if (AchievementsHelper.failJumps10AchievementUnlockedInLevel(failedJumpsCounterBefore, failedJumpsCounterAfter)) {
			newAchievement = true;
		} else if (AchievementsHelper.failJumps25AchievementUnlockedInLevel(failedJumpsCounterBefore, failedJumpsCounterAfter)) {
			newAchievement = true;
		} else if (AchievementsHelper.upper10AchievementUnlockedInLevel(upperImpulseCounterBefore, upperImpulseCounterAfter)) {
			newAchievement = true;
		} else if (AchievementsHelper.upper50AchievementUnlockedInLevel(upperImpulseCounterBefore, upperImpulseCounterAfter)) {
			newAchievement = true;
		} else if (AchievementsHelper.upper100AchievementUnlockedInLevel(upperImpulseCounterBefore, upperImpulseCounterAfter)) {
			newAchievement = true;
		} else if (AchievementsHelper.antigravity10AchievementUnlockedInLevel(antigravityCounterBefore, antigravityCounterAfter)) {
			newAchievement = true;
		} else if (AchievementsHelper.antigravity50AchievementUnlockedInLevel(antigravityCounterBefore, antigravityCounterAfter)) {
			newAchievement = true;
		} else if (AchievementsHelper.antigravity100AchievementUnlockedInLevel(antigravityCounterBefore, antigravityCounterAfter)) {
			newAchievement = true;
		} else if (AchievementsHelper.shield10AchievementUnlockedInLevel(shieldCounterBefore, shieldCounterAfter)) {
			newAchievement = true;
		} else if (AchievementsHelper.shield50AchievementUnlockedInLevel(shieldCounterBefore, shieldCounterAfter)) {
			newAchievement = true;
		} else if (AchievementsHelper.shield100AchievementUnlockedInLevel(shieldCounterBefore, shieldCounterAfter)) {
			newAchievement = true;
		} else if (AchievementsHelper.slow10AchievementUnlockedInLevel(slowCounterBefore, slowCounterAfter)) {
			newAchievement = true;
		} else if (AchievementsHelper.slow50AchievementUnlockedInLevel(slowCounterBefore, slowCounterAfter)) {
			newAchievement = true;
		} else if (AchievementsHelper.slow100AchievementUnlockedInLevel(slowCounterBefore, slowCounterAfter)) {
			newAchievement = true;
		} else if (AchievementsHelper.destroy5helicoptersAchievementUnlockedInLevel(helicoptersCounterBefore, helicoptersCounterAfter)) {
			newAchievement = true;
		} else if (AchievementsHelper.destroy25helicoptersAchievementUnlockedInLevel(helicoptersCounterBefore, helicoptersCounterAfter)) {
			newAchievement = true;
		} else if (AchievementsHelper.destroy100helicoptersAchievementUnlockedInLevel(helicoptersCounterBefore, helicoptersCounterAfter)) {
			newAchievement = true;
		} else if (AchievementsHelper.destroy5balloonsAchievementUnlockedInLevel(balloonsCounterBefore, balloonsCounterAfter)) {
			newAchievement = true;
		} else if (AchievementsHelper.destroy25balloonsAchievementUnlockedInLevel(balloonsCounterBefore, balloonsCounterAfter)) {
			newAchievement = true;
		} else if (AchievementsHelper.destroy100balloonsAchievementUnlockedInLevel(balloonsCounterBefore, balloonsCounterAfter)) {
			newAchievement = true;
		} else if (AchievementsHelper.destroy5birdsAchievementUnlockedInLevel(birdsCounterBefore, birdsCounterAfter)) {
			newAchievement = true;
		} else if (AchievementsHelper.destroy25birdsAchievementUnlockedInLevel(birdsCounterBefore, birdsCounterAfter)) {
			newAchievement = true;
		} else if (AchievementsHelper.destroy100birdsAchievementUnlockedInLevel(birdsCounterBefore, birdsCounterAfter)) {
			newAchievement = true;
		}
		
		if (newAchievement) {
			Text achievementsUnlocked = new Text(camera.getCenterX(), camera.getCenterY() - 40, resourcesManager.achievementsUnlockedFont, "New achievement unlocked!!!", new TextOptions(HorizontalAlign.LEFT), vbom);
			achievementsUnlocked.setText("New achievement unlocked!!!");
			GameScene.this.attachChild(achievementsUnlocked);
		}
	}
	
	private void saveScoreData() {
		saveUpperImpulseCounter("upperImpulseCounter", upperImpulseCounter);
		saveShieldCounter("shieldCounter", shieldCounter);
		saveAntigravityCounter("antigravityCounter", antigravityCounter);
		saveSlowCounter("slowCounter", slowCounter);
		saveHelicopterCounter("helicopterCounter", helicopterCounter);
		saveBalloonCounter("balloonCounter", balloonCounter);
		saveBirdCounter("birdCounter", birdCounter);
		saveSuccessfulJumps("successfulJumps");
		saveFreeFliedMeters("freeFliedMetersCounter", freeFliedMeters);
		saveParachuteFliedMeters("parachuteFliedMetersCounter", parachuteFliedMeters);
		saveMaxFliedMeters("fliedMeters", fliedMeters);
		saveMaxFreeFliedMeters("freeFliedMeters", freeFliedMeters);
		saveMaxParachuteFliedMeters("parachuteFliedMeters", parachuteFliedMeters);
		saveMaxSpeed("maxSpeed", maxSpeed);
		saveCoins("coins", player.getPlayerCoins());
	}
}

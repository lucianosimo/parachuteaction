package com.lucianosimo.parachuteaction.scene;

import java.util.Iterator;
import java.util.Random;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.handler.physics.PhysicsHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.background.ParallaxBackground;
import org.andengine.entity.scene.background.ParallaxBackground.ParallaxEntity;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.extension.physics.box2d.FixedStepPhysicsWorld;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.extension.physics.box2d.util.constants.PhysicsConstants;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.util.adt.align.HorizontalAlign;
import org.andengine.util.adt.color.Color;
import org.andengine.util.debug.Debug;
import org.andengine.util.modifier.IModifier;

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
import com.lucianosimo.parachuteaction.object.Missile;
import com.lucianosimo.parachuteaction.object.Player;

public class GameScene extends BaseScene{
	
	private float playerSpeed;
	
	//Scene indicators
	private HUD gameHud;
	
	//Shared Preferences
	private SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
	
	//Counters
	private int fliedMeters = 0;
	private int freeFliedMeters = 0;
	private int parachuteFliedMeters = 0;
	private int distanceToFloor = 0;
	private int oldDistanceToFloor = 0;
	private int distanceToFloorAtOpenParachute = 0;
	private int meterCounterForReduceSpeed = 0;
	
	private int upperImpulseCounter = 0;
	private int antigravityCounter = 0;
	private int shieldCounter = 0;
	private int slowCounter = 0;
	private int helicopterCounter = 0;
	private int balloonCounter = 0;
	private int birdCounter = 0;
	private int coins = 0;
	
	private int firstGame = 0;
	private int coinsCounter = 0;
	private int randCoinsX = 0;
	
	//Constants	
	private float screenWidth;
	private float screenHeight;

	//Booleans
	private boolean firstFall = true;
	private boolean openParachute = false;
	private boolean shield = false;
	private boolean openParachuteDistanceSaved = false;
	private boolean loadedCountersBefore = false;
	private boolean availablePause = true;
	private boolean startMoving = false;
	private boolean dead = false;
	private boolean levelCompleteBoolean = false;
	
	//Texts variables
	private Text altimeterText;
	private Text levelStartText;
	private Text coinsText;
	
	//Instances
	private Player player;
	private Helicopter helicopter;
	private Balloon balloon;
	private Missile missile;
	private Bird bird;
	
	//Windows
	private Sprite gameOverWindow;
	private Sprite levelCompleteWindow;
	private Sprite helpWindow;
	private Sprite pauseWindow;
	
	//Rectangles
	private Rectangle shieldBar;
	private Rectangle shieldBarBackground;
	
	//Shield bar
	private Sprite shieldBarFrame;
	private Sprite shieldBarLogo;
	
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
	private Sprite helicopterRedArrow;
	private Sprite missileRedArrow;
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
	
	//Balloon Basket
	private Sprite basket;
	
	//Constants	
	//16 pixels == 1 meter
	private static final int PIXEL_METER_RATE = 16;
	private static final int LEFT_MARGIN = 0;
	private static final int RIGHT_MARGIN = 720;
	private static final int CLOSER_CLOUD_SPEED = -70;
	private static final int FAR_CLOUD_SPEED = -15;
	private static final int CLOUD_SPEED = -40;
	private static final int PLANE_SPEED = -85;
	private static final int SHIELD_DURATION = 10;
	private static final int ANTIGRAVITY_DURATION = 5;
	private static final int COINS_VALUE = 100;
	
	private static final int HELICOPTER_MOVE_SENSOR = 750;
	private static final int HELICOPTER_SOUND_SENSOR = 500;
	private static final int BIRD_MOVE_SENSOR = 750;
	private static final int BIRD_SOUND_SENSOR = 400;
	private static final int BALLOON_MOVE_SENSOR = 1750;
	
	private static final int PLAYER_INITIAL_Y = 64000;
	
	private static final int PLANE_X = 1000;
	private static final int PLANE_Y = 64000;
	private static final int PLANE_LAUNCH_SENSOR_X = -175;
	private static final int PLANE_SOUND_SENSOR_X = 240;
	
	private static final int CLOUD_INITIAL_X = 360;
	private static final int FAR_CLOUD_INITIAL_Y = 63000;
	private static final int CLOUD_INITIAL_Y = 62000;
	private static final int CLOSER_CLOUD_INITIAL_Y = 63000;
	private static final int FAR_CLOUD_REGENERATE_DISTANCE_Y = 2250;
	private static final int CLOUD_REGENERATE_DISTANCE_Y = 2750;
	private static final int CLOSER_CLOUD_REGENERATE_DISTANCE_Y = 2250;
	
	private static final int FAR_CLOUD_LATERAL_LIMIT = 125;
	private static final int CLOUD_LATERAL_LIMIT = 200;
	private static final int CLOSER_CLOUD_LATERAL_LIMIT = 200;
	private static final int FAR_CLOUD_TOP_LIMIT = 700;
	private static final int CLOUD_TOP_LIMIT = 700;
	private static final int CLOSER_CLOUD_TOP_LIMIT = 700;
	
	private static final int CLOUD_MAX_X = 360;
	private static final int CLOUD_MIN_X = -360;
	private static final int CLOUD_MAX_Y = 250;
	private static final int CLOUD_MIN_Y = -250;
	
	private static final long[] EXPLOSION_ANIMATE = new long[] {75, 75, 75, 75, 75, 100};
	
	private static final int HELICOPTER_SHOW_ARROW_DISTANCE = 1500;
	private static final int HELICOPTER_HIDE_ARROW_DISTANCE = 640;
	private static final int HELICOPTER_ARROW_DISTANCE_TO_BOTTOM = 75;
	
	private static final int HELICOPTER_INITIAL_Y = 60000;	
	private static final int HELICOPTER_REGENERATE_DISTANCE_Y = 7500;
	private static final int HELICOPTER_MAX_X = 650;
	private static final int HELICOPTER_MIN_X = 450;
	
	private static final int LEFT_HELICOPTER_INITIAL_Y = 57500;	
	private static final int LEFT_HELICOPTER_REGENERATE_DISTANCE_Y = 7500;
	private static final int LEFT_HELICOPTER_MAX_X = 270;
	private static final int LEFT_HELICOPTER_MIN_X = 70;
	
	private static final int BALLOON_SHOW_ARROW_DISTANCE = 1500;
	private static final int BALLOON_HIDE_ARROW_DISTANCE = 640;
	private static final int BALLOON_ARROW_DISTANCE_TO_BOTTOM = 75;
	
	private static final int BALLOON_INITIAL_Y = 55000;	
	private static final int BALLOON_REGENERATE_DISTANCE_Y = 7500;
	private static final int BALLOON_MAX_X = 670;
	private static final int BALLOON_MIN_X = 50;
	private static final int BALLOON_BASKET_X = 97;
	private static final int BALLOON_BASKET_Y = -30;
	
	private static final int MISSILE_MOVE_SENSOR = 750;
	private static final int MISSILE_SOUND_SENSOR = 500;
	private static final int MISSILE_SHOW_ARROW_DISTANCE = 1500;
	private static final int MISSILE_HIDE_ARROW_DISTANCE = 640;
	private static final int MISSILE_ARROW_DISTANCE_TO_BOTTOM = 75;
	
	private static final int MISSILE_INITIAL_Y = 50000;	
	private static final int MISSILE_REGENERATE_DISTANCE_Y = 7500;
	private static final int MISSILE_MAX_X = 670;
	private static final int MISSILE_MIN_X = 50;
	
	private static final int BIRD_SHOW_ARROW_DISTANCE = 1500;
	private static final int BIRD_HIDE_ARROW_DISTANCE = 640;
	private static final int BIRD_ARROW_DISTANCE_TO_BOTTOM = 75;
	
	private static final int BIRD_INITIAL_Y = 50000;	
	private static final int BIRD_REGENERATE_DISTANCE_Y = 7500;
	private static final int BIRD_MAX_X = 650;
	private static final int BIRD_MIN_X = 450;
	
	private static final int BIRD_X = 700;
	private static final int LEFT_BIRD_X = 20;
	
	private static final int LEFT_BIRD_SHOW_ARROW_DISTANCE = 1500;
	private static final int LEFT_BIRD_HIDE_ARROW_DISTANCE = 640;
	private static final int LEFT_BIRD_ARROW_DISTANCE_TO_BOTTOM = 75;
	
	private static final int LEFT_BIRD_INITIAL_Y = 45000;	
	private static final int LEFT_BIRD_REGENERATE_DISTANCE_Y = 7500;
	private static final int LEFT_BIRD_MAX_X = 650;
	private static final int LEFT_BIRD_MIN_X = 450;

	@Override
	public void createScene() {
		activity.cacheAd();
		screenWidth = resourcesManager.camera.getWidth();
		screenHeight = resourcesManager.camera.getHeight();
		camera.setBounds(0, 0, screenWidth, 65000);
		camera.setBoundsEnabled(true);
		resourcesManager.wind.play();
		createBackground();
		createWindows();
		createHud();
		createPhysics();
		createFarClouds();
		createClouds();
		createPlayer();
		createPlane();
		createHelicopters();
		createBalloons();
		createMissiles();
		createBirds();
		createShield();
		//createCloserClouds();
		firstGame();
		//DebugRenderer debug = new DebugRenderer(physicsWorld, vbom);
        //GameScene.this.attachChild(debug);
	}
	
	private void createFarClouds() {
		int randX = generateRandomPosition(CLOUD_MAX_X, CLOUD_MIN_X);
		int randY = generateRandomPosition(CLOUD_MAX_Y, CLOUD_MIN_Y);
		Sprite farCloud = new Sprite(CLOUD_INITIAL_X + randX, FAR_CLOUD_INITIAL_Y + randY, resourcesManager.game_far_cloud_region, vbom) {
			protected void onManagedUpdate(float pSecondsElapsed) {
				super.onManagedUpdate(pSecondsElapsed);
				if (this.getX() < LEFT_MARGIN - FAR_CLOUD_LATERAL_LIMIT) {
					this.setPosition(RIGHT_MARGIN + FAR_CLOUD_LATERAL_LIMIT, this.getY());
				}
				if ((this.getY() - player.getY()) > FAR_CLOUD_TOP_LIMIT) {
					int randX = generateRandomPosition(CLOUD_MAX_X, CLOUD_MIN_X);
					this.setX(CLOUD_INITIAL_X + randX);
					this.setY(this.getY() - FAR_CLOUD_REGENERATE_DISTANCE_Y);
				}
			};
		};
		PhysicsHandler handler = new PhysicsHandler(farCloud);
		farCloud.registerUpdateHandler(handler);
		handler.setVelocity(FAR_CLOUD_SPEED,0);
		GameScene.this.attachChild(farCloud);
	}
	
	private void createClouds() {
		int randX = generateRandomPosition(CLOUD_MAX_X, CLOUD_MIN_X);
		int randY = generateRandomPosition(CLOUD_MAX_Y, CLOUD_MIN_Y);
		Sprite cloud = new Sprite(CLOUD_INITIAL_X + randX, CLOUD_INITIAL_Y + randY, resourcesManager.game_cloud_region, vbom) {
			protected void onManagedUpdate(float pSecondsElapsed) {
				super.onManagedUpdate(pSecondsElapsed);
				if (this.getX() < LEFT_MARGIN - CLOUD_LATERAL_LIMIT) {
					this.setPosition(RIGHT_MARGIN + CLOUD_LATERAL_LIMIT, this.getY());
				}
				if ((this.getY() - player.getY()) > CLOUD_TOP_LIMIT) {
					int randX = generateRandomPosition(CLOUD_MAX_X, CLOUD_MIN_X);
					this.setX(CLOUD_INITIAL_X + randX);
					this.setY(this.getY() - CLOUD_REGENERATE_DISTANCE_Y);
				}
			};
		};
		PhysicsHandler handler = new PhysicsHandler(cloud);
		cloud.registerUpdateHandler(handler);
		handler.setVelocity(CLOUD_SPEED,0);
		GameScene.this.attachChild(cloud);
	}
	
	private void createPlayer() {
		int parachuteX = 27;
		int parachuteY = 140;
		int shieldX = 20;
		int shieldY = 50;
		
		parachute = new Sprite(parachuteX, parachuteY, ResourcesManager.getInstance().game_parachute_region, vbom);
		shieldHalo = new Sprite(shieldX, shieldY, resourcesManager.game_shield_region, vbom);
		
		parachute.setVisible(false);
		shieldHalo.setVisible(false);
		
		oldDistanceToFloor = PLAYER_INITIAL_Y / PIXEL_METER_RATE;
		
		player = new Player(screenWidth/2, PLAYER_INITIAL_Y, vbom, camera, physicsWorld) {
			
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
						
						activateShield();
						calculateDistanceToFloor();
						calculateFliedMeters();
						displayOpenParachuteButton();
						
						if (openParachute && player.getFallVelocity() > 0) {
							player.slowDownPlayer();
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
							
							//Each 100 px reduce parachute fall speed
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
						saveScoreDataOnDie();
						GameScene.this.setIgnoreUpdate(true);
				        camera.setChaseEntity(null);
				        availablePause = false;
				        dead = true;
				        gameOverWindow.setPosition(camera.getCenterX(), camera.getCenterY());
						GameScene.this.attachChild(gameOverWindow);
					    final Sprite retryButton = new Sprite(510, 75, resourcesManager.game_retry_button_region, vbom){
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
					    final Sprite mapButton = new Sprite(310, 75, resourcesManager.game_map_button_region, vbom){
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
					    final Sprite quitButton = new Sprite(110, 75, resourcesManager.game_quit_button_region, vbom){
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
					    activity.showAd();
					}
				});
				
			}
			
		};
		player.attachChild(parachute);
		player.attachChild(shieldHalo);
		GameScene.this.attachChild(player);
	}
	
	private void createCloserClouds() {
		int randX = generateRandomPosition(CLOUD_MAX_X, CLOUD_MIN_X);
		int randY = generateRandomPosition(CLOUD_MAX_Y, CLOUD_MIN_Y);
		Sprite farCloud = new Sprite(CLOUD_INITIAL_X + randX, CLOSER_CLOUD_INITIAL_Y + randY, resourcesManager.game_closer_cloud_region, vbom) {
			protected void onManagedUpdate(float pSecondsElapsed) {
				super.onManagedUpdate(pSecondsElapsed);
				if (this.getX() < LEFT_MARGIN - CLOSER_CLOUD_LATERAL_LIMIT) {
					this.setPosition(RIGHT_MARGIN + CLOSER_CLOUD_LATERAL_LIMIT, this.getY());
				}
				if ((this.getY() - player.getY()) > CLOSER_CLOUD_TOP_LIMIT) {
					int randX = generateRandomPosition(CLOUD_MAX_X, CLOUD_MIN_X);
					this.setX(CLOUD_INITIAL_X + randX);
					this.setY(this.getY() - CLOSER_CLOUD_REGENERATE_DISTANCE_Y);
				}
			};
		};
		PhysicsHandler handler = new PhysicsHandler(farCloud);
		farCloud.registerUpdateHandler(handler);
		handler.setVelocity(FAR_CLOUD_SPEED,0);
		GameScene.this.attachChild(farCloud);
	}
	
	private void activateShield() {
		if (shield) {
			shieldHalo.setVisible(true);
			player.disablePlayerCollision();
		} else {
			shieldHalo.setVisible(false);
			player.enablePlayerCollision();
		}	
	}
	
	private void calculateDistanceToFloor() {
		distanceToFloor = (int) player.getY() / PIXEL_METER_RATE;
		altimeterText.setText("Meters to go: " + distanceToFloor);
	}
	
	private void calculateFliedMeters() {
		if (player.getFallVelocity() < 0) {
			fliedMeters = fliedMeters + (oldDistanceToFloor - distanceToFloor);
		}
	}
	
	private void displayOpenParachuteButton() {
		if (distanceToFloor < 1500 && distanceToFloor > 1000 && !openParachute) {
			openButton.setVisible(true);
			gameHud.registerTouchArea(openButton);
		} else {
			openButton.setVisible(false);
			gameHud.unregisterTouchArea(openButton);
		}
	}
	
	private void createPlane() {
		final Rectangle launchSensor = new Rectangle(PLANE_LAUNCH_SENSOR_X, PLANE_Y, 0.1f, 1f, vbom);
		final Rectangle soundSensor = new Rectangle(PLANE_SOUND_SENSOR_X, PLANE_Y, 0.1f, 1f, vbom);
		
		Sprite plane = new Sprite(PLANE_X, PLANE_Y, resourcesManager.game_plane_region, vbom) {
			protected void onManagedUpdate(float pSecondsElapsed) {
				super.onManagedUpdate(pSecondsElapsed);
				if (this.collidesWith(launchSensor)) {
					if (!startMoving) {
						startMoving = true;
						player.setVisible(true);
						player.getPlayerBody().setLinearVelocity(new Vector2(player.getPlayerBody().getLinearVelocity().x, -5));
						gameHud.detachChild(levelStartText);
						launchSensor.setPosition(280, 70000);
					}									
				}
				if (this.collidesWith(soundSensor)) {
					resourcesManager.plane.play();
					soundSensor.setPosition(240, 70000);
				}
			};
		};
		PhysicsHandler handler = new PhysicsHandler(plane);
		plane.registerUpdateHandler(handler);
		handler.setVelocity(PLANE_SPEED,0);
		GameScene.this.attachChild(plane);
		plane.setCullingEnabled(true);
	}
	
	private boolean isRightHelicopter(int index) {
		return index == 0 ? true : false;
	}
	
	private boolean isRightBird(int index) {
		return index == 0 ? true : false;
	}
	
	private void createExplosion() {
		explosion = new AnimatedSprite(0, 0, resourcesManager.game_explosion_region.deepCopy(), vbom);
		explosion.setVisible(false);
		GameScene.this.attachChild(explosion);
	}
	
	private int generateRandomPosition(int max, int min) {
		Random rand = new Random();
		return rand.nextInt(max - min + 1) + min;
	}
	
	private void createHelicopters() {
		//i = 0: Right helicopter, i = 1: leftHelicopter
		for (int i = 0; i < 2; i++) {
			int helicopterX = isRightHelicopter(i) ? generateRandomPosition(HELICOPTER_MAX_X, HELICOPTER_MIN_X) : generateRandomPosition(LEFT_HELICOPTER_MAX_X, LEFT_HELICOPTER_MIN_X);
			int helicopterY = isRightHelicopter(i) ? HELICOPTER_INITIAL_Y : LEFT_HELICOPTER_INITIAL_Y;
			ITiledTextureRegion helicopterRegion = isRightHelicopter(i) ? resourcesManager.game_helicopter_region : resourcesManager.game_left_helicopter_region;
			
			final Rectangle moveSensor = new Rectangle(screenWidth/2, HELICOPTER_INITIAL_Y + HELICOPTER_MOVE_SENSOR, screenWidth, 0.1f, vbom);
			final Rectangle soundSensor = new Rectangle(screenWidth/2, HELICOPTER_INITIAL_Y + HELICOPTER_SOUND_SENSOR, screenWidth, 0.1f, vbom);
			
			if (!isRightHelicopter(i)) {
				moveSensor.setPosition(moveSensor.getX(), LEFT_HELICOPTER_INITIAL_Y + HELICOPTER_MOVE_SENSOR);
				soundSensor.setPosition(soundSensor.getX(), LEFT_HELICOPTER_INITIAL_Y + HELICOPTER_SOUND_SENSOR);
			}
			
			createExplosion();
			
			helicopter = new Helicopter(helicopterX, helicopterY, vbom, camera, physicsWorld, helicopterRegion, isRightHelicopter(i)) {
				protected void onManagedUpdate(float pSecondsElapsed) {
					super.onManagedUpdate(pSecondsElapsed);
					helicopterBehaviour(this, moveSensor, soundSensor);				 
				};
			};
			GameScene.this.attachChild(helicopter);
			helicopter.setCullingEnabled(true);
		}		
	}

	private void helicopterBehaviour(Helicopter helicopter, Rectangle moveSensor, Rectangle soundSensor) {
		if ((player.getY() - helicopter.getY()) < HELICOPTER_SHOW_ARROW_DISTANCE && (player.getY() - helicopter.getY()) > HELICOPTER_HIDE_ARROW_DISTANCE) {
			helicopterRedArrow.setPosition(helicopter.getX(), HELICOPTER_ARROW_DISTANCE_TO_BOTTOM);
		} else if ((player.getY() - helicopter.getY()) < HELICOPTER_HIDE_ARROW_DISTANCE && (player.getY() - helicopter.getY()) > 0) {
			helicopterRedArrow.setPosition(1500, 0);
		}
		
		if (player.collidesWith(moveSensor)) {
			helicopter.startMoving();
			moveSensor.setPosition(1000, 1000);
		}
		
		if (player.collidesWith(soundSensor)) {
			resourcesManager.chopper.play();
			soundSensor.setPosition(1000, 1000);
		}
		
		if ((helicopter.getY() - player.getY()) > screenHeight/2 ) {
			regenerateHelicopter(helicopter, moveSensor, soundSensor);
		}
		
		if (player.collidesWith(helicopter) && shield) {
			resourcesManager.chopper.stop();
			resourcesManager.explosion.play();
			
			explosion.setPosition(helicopter.getX(),helicopter.getY());
			explosion.setVisible(true);
			explosion.animate(EXPLOSION_ANIMATE, 0, 5, false);
			
			regenerateHelicopter(helicopter, moveSensor, soundSensor);
		}
	}
	
	private void regenerateHelicopter(Helicopter helicopter, Rectangle moveSensor, Rectangle soundSensor) {
		int helicopterX = helicopter.isRightHelicopter() ? generateRandomPosition(HELICOPTER_MAX_X, HELICOPTER_MIN_X) 
				: generateRandomPosition(LEFT_HELICOPTER_MAX_X, LEFT_HELICOPTER_MIN_X);
		int regenerateY = helicopter.isRightHelicopter() ? HELICOPTER_REGENERATE_DISTANCE_Y : LEFT_HELICOPTER_REGENERATE_DISTANCE_Y;
		
		helicopter.getBody().setTransform(helicopterX / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT, 
				(helicopter.getY() - regenerateY) / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT, helicopter.getBody().getAngle());
		helicopter.setPosition(helicopterX, helicopter.getY() - regenerateY);
		helicopter.stopMoving();
		moveSensor.setPosition(screenWidth / 2, helicopter.getY() + HELICOPTER_MOVE_SENSOR);
		soundSensor.setPosition(screenWidth / 2, helicopter.getY() + HELICOPTER_SOUND_SENSOR);
	}
	
	private void createBalloons() {
		final Rectangle moveSensor = new Rectangle(screenWidth/2, BALLOON_INITIAL_Y + BALLOON_MOVE_SENSOR, screenWidth, 0.1f, vbom);
		basket = new Sprite(BALLOON_BASKET_X, BALLOON_BASKET_Y, resourcesManager.game_balloon_basket_region, vbom);
		
		createExplosion();
		
		int randX = generateRandomPosition(BALLOON_MAX_X, BALLOON_MIN_X);		
		balloon = new Balloon(randX, BALLOON_INITIAL_Y, vbom, camera, physicsWorld, resourcesManager.game_balloon_region) {
			
			protected void onManagedUpdate(float pSecondsElapsed) {
				super.onManagedUpdate(pSecondsElapsed);
				balloonBehaviour(this, moveSensor);
			};
		};

		GameScene.this.attachChild(balloon);
		balloon.attachChild(basket);
		balloon.setCullingEnabled(true);
	}
	
	private void balloonBehaviour(Balloon balloon, Rectangle moveSensor) {
		if (player.collidesWith(moveSensor)) {
			balloon.startMoving();
			moveSensor.setPosition(1000, 1000);
		}
		if ((player.getY() - balloon.getY()) < BALLOON_SHOW_ARROW_DISTANCE && (player.getY() - balloon.getY()) > BALLOON_HIDE_ARROW_DISTANCE) {
			balloonRedArrow.setPosition(balloon.getX(), BALLOON_ARROW_DISTANCE_TO_BOTTOM);
		} else if ((player.getY() - balloon.getY()) < BALLOON_HIDE_ARROW_DISTANCE && (player.getY() - balloon.getY()) > 0) {
			balloonRedArrow.setPosition(1500, 0);
		}
		
		if ((balloon.getY() - player.getY()) > screenHeight/2 ) {
			balloon.stopMoving();
			regenerateBalloon(balloon, moveSensor);
		}
		
		if (player.collidesWith(balloon) && shield) {
			balloon.stopMoving();
			resourcesManager.explosion.play();
			
			explosion.setPosition(balloon.getX(),balloon.getY());
			explosion.setVisible(true);
			explosion.animate(EXPLOSION_ANIMATE, 0, 5, false);
			
			regenerateBalloon(balloon, moveSensor);
		}
	}
	
	private void regenerateBalloon(Balloon balloon, Rectangle moveSensor) {
		int balloonX = generateRandomPosition(BALLOON_MAX_X, BALLOON_MIN_X); 
		
		balloon.getBody().setTransform(balloonX / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT, 
				(balloon.getY() - BALLOON_REGENERATE_DISTANCE_Y) / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT, balloon.getBody().getAngle());
		balloon.setPosition(balloonX, balloon.getY() - BALLOON_REGENERATE_DISTANCE_Y);
		
		moveSensor.setPosition(screenWidth / 2, balloon.getY() + BALLOON_MOVE_SENSOR);
	}
	
	private void createMissiles() {
		final Rectangle moveSensor = new Rectangle(screenWidth/2, MISSILE_INITIAL_Y + MISSILE_MOVE_SENSOR, screenWidth, 0.1f, vbom);
		final Rectangle soundSensor = new Rectangle(screenWidth/2, MISSILE_INITIAL_Y + MISSILE_SOUND_SENSOR, screenWidth, 0.1f, vbom);

		createExplosion();
		
		int randX = generateRandomPosition(MISSILE_MAX_X, MISSILE_MIN_X);		
		missile = new Missile(randX, MISSILE_INITIAL_Y, vbom, camera, physicsWorld, resourcesManager.game_missile_region) {
			
			protected void onManagedUpdate(float pSecondsElapsed) {
				super.onManagedUpdate(pSecondsElapsed);
				missileBehaviour(this, moveSensor, soundSensor);
			};
		};

		GameScene.this.attachChild(missile);
		missile.setCullingEnabled(true);
	}
	
	private void missileBehaviour(Missile missile, Rectangle moveSensor, Rectangle soundSensor) {
		if ((player.getY() - missile.getY()) < MISSILE_SHOW_ARROW_DISTANCE && (player.getY() - missile.getY()) > MISSILE_HIDE_ARROW_DISTANCE) {
			missileRedArrow.setPosition(missile.getX(), MISSILE_ARROW_DISTANCE_TO_BOTTOM);
		} else if ((player.getY() - missile.getY()) < MISSILE_HIDE_ARROW_DISTANCE && (player.getY() - missile.getY()) > 0) {
			missileRedArrow.setPosition(1500, 0);
		}
		
		if (player.collidesWith(moveSensor)) {
			missile.startMoving();
			moveSensor.setPosition(1000, 1000);
		}
		
		if (player.collidesWith(soundSensor)) {
			//resourcesManager.chopper.play();
			soundSensor.setPosition(1000, 1000);
		}
		
		if ((missile.getY() - player.getY()) > screenHeight/2 ) {
			regenerateMissile(missile, moveSensor, soundSensor);
		}
		
		if (player.collidesWith(missile) && shield) {
			//resourcesManager.chopper.stop();
			resourcesManager.explosion.play();
			
			explosion.setPosition(missile.getX(),missile.getY());
			explosion.setVisible(true);
			explosion.animate(EXPLOSION_ANIMATE, 0, 5, false);
			
			regenerateMissile(missile, moveSensor, soundSensor);
		}
	}
	
	private void regenerateMissile(Missile missile, Rectangle moveSensor, Rectangle soundSensor) {
		int missileX = generateRandomPosition(MISSILE_MAX_X, MISSILE_MIN_X); 
		
		missile.getBody().setTransform(missileX / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT, 
				(missile.getY() - MISSILE_REGENERATE_DISTANCE_Y) / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT, missile.getBody().getAngle());
		missile.setPosition(missileX, missile.getY() - MISSILE_REGENERATE_DISTANCE_Y);

		moveSensor.setPosition(screenWidth / 2, missile.getY() + MISSILE_MOVE_SENSOR);
		soundSensor.setPosition(screenWidth / 2, missile.getY() + MISSILE_SOUND_SENSOR);
	}
	
	private void createBirds() {
		//i = 0: Right bird, i = 1: Left bird
		for (int i = 0; i < 2; i++) {
			//int birdX = isRightBird(i) ? generateRandomPosition(BIRD_MAX_X, BIRD_MIN_X) : generateRandomPosition(LEFT_BIRD_MAX_X, LEFT_BIRD_MIN_X);
			int birdX = isRightBird(i) ? BIRD_X : LEFT_BIRD_X;
			int birdY = isRightBird(i) ? BIRD_INITIAL_Y : LEFT_BIRD_INITIAL_Y;
			ITiledTextureRegion birdRegion = isRightBird(i) ? resourcesManager.game_bird_region : resourcesManager.game_left_bird_region;
			
			final Rectangle moveSensor = new Rectangle(screenWidth/2, BIRD_INITIAL_Y + BIRD_MOVE_SENSOR, screenWidth, 0.1f, vbom);
			final Rectangle soundSensor = new Rectangle(screenWidth/2, BIRD_INITIAL_Y + BIRD_SOUND_SENSOR, screenWidth, 0.1f, vbom);
			
			if (!isRightBird(i)) {
				moveSensor.setPosition(moveSensor.getX(), LEFT_BIRD_INITIAL_Y + BIRD_MOVE_SENSOR);
				soundSensor.setPosition(soundSensor.getX(), LEFT_BIRD_INITIAL_Y + BIRD_SOUND_SENSOR);
			}
			
			bird = new Bird(birdX, birdY, vbom, camera, physicsWorld, birdRegion, isRightBird(i)) {
				protected void onManagedUpdate(float pSecondsElapsed) {
					super.onManagedUpdate(pSecondsElapsed);
					birdBehaviour(this, moveSensor, soundSensor);				 
				};
			};
			GameScene.this.attachChild(bird);
			bird.setCullingEnabled(true);
		}
	}
	
	private void birdBehaviour(Bird bird, Rectangle moveSensor, Rectangle soundSensor) {
		if ((player.getY() - bird.getY()) < BIRD_SHOW_ARROW_DISTANCE && (player.getY() - bird.getY()) > BIRD_HIDE_ARROW_DISTANCE) {
			birdRedArrow.setPosition(bird.getX(), BIRD_ARROW_DISTANCE_TO_BOTTOM);
		} else if ((player.getY() - bird.getY()) < BIRD_HIDE_ARROW_DISTANCE && (player.getY() - bird.getY()) > 0) {
			birdRedArrow.setPosition(1500, 0);
		}
		
		if (player.collidesWith(moveSensor)) {
			bird.startMoving();
			moveSensor.setPosition(1000, 1000);
		}
		
		if (player.collidesWith(soundSensor)) {
			resourcesManager.bird.play();
			soundSensor.setPosition(1000, 1000);
		}
		
		if ((bird.getY() - player.getY()) > screenHeight/2 ) {
			regenerateBird(bird, moveSensor, soundSensor);
		}
		
		if (player.collidesWith(bird) && shield) {
			resourcesManager.bird.play();
			regenerateBird(bird, moveSensor, soundSensor);
		}
	}
	
	private void regenerateBird(Bird bird, Rectangle moveSensor, Rectangle soundSensor) {
		int birdX = bird.isRightBird() ? BIRD_X : LEFT_BIRD_X;
		int birdRegenerateY = bird.isRightBird() ? BIRD_REGENERATE_DISTANCE_Y : LEFT_BIRD_REGENERATE_DISTANCE_Y;
		
		bird.getBody().setTransform(birdX / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT, 
				(bird.getY() - birdRegenerateY) / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT, bird.getBody().getAngle());
		bird.setPosition(birdX, bird.getY() - birdRegenerateY);
		bird.stopMoving();
		moveSensor.setPosition(screenWidth / 2, bird.getY() + BIRD_MOVE_SENSOR);
		soundSensor.setPosition(screenWidth / 2, bird.getY() + BIRD_SOUND_SENSOR);
	}
	
	private void createShield() {
		Random rand = new Random();
		int randX = rand.nextInt(601) - 300;
		
		Sprite shieldSprite = new Sprite(screenWidth/2 + randX, 62000, resourcesManager.game_shield_region, vbom) {
			protected void onManagedUpdate(float pSecondsElapsed) {
				super.onManagedUpdate(pSecondsElapsed);
				if ((player.getY() - this.getY()) < 2250 && (player.getY() - this.getY()) > 640) {
					greenArrow.setPosition(this.getX(), 75);
				} else if ((player.getY() - this.getY()) < 640 && (player.getY() - this.getY()) > 0) {
					greenArrow.setPosition(1500, 0);
				}
				if (player.collidesWith(this)) {
					shieldCounter++;
					destroySprite(this);
					player.registerEntityModifier(new DelayModifier(SHIELD_DURATION, new IEntityModifierListener() {
						
						@Override
						public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
							shield = true;
							
							shieldBar.setWidth(200);
							
							shieldBar.setColor(0.259f, 0.541f, 0.78f);
							shieldBarBackground.setColor(Color.WHITE_ARGB_PACKED_INT);

							shieldBar.setVisible(true);
							shieldBarBackground.setVisible(true);
							shieldBarFrame.setVisible(true);
							shieldBarLogo.setVisible(true);
																	
							engine.registerUpdateHandler(new IUpdateHandler() {
								final IUpdateHandler upd = this;
								
								@Override
								public void reset() {
									
								}
								
								@Override
								public void onUpdate(float pSecondsElapsed) {
									if (availablePause) {
										if (shieldBar.getWidth() > 0) {
											shieldBar.setSize(shieldBar.getWidth() - pSecondsElapsed * 20, shieldBar.getHeight());
										} else {
											engine.unregisterUpdateHandler(upd);
										}
										shieldBar.setPosition((screenWidth/2 + screenWidth/4 - 100) + shieldBar.getWidth() / 2, shieldBar.getY());
									}
								}
							});
						}
						
						@Override
						public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
							shield = false;
							shieldBar.setVisible(false);
							shieldBarBackground.setVisible(false);
							shieldBarFrame.setVisible(false);
							shieldBarLogo.setVisible(false);
						}
					}));
				}
			};
		};
		GameScene.this.attachChild(shieldSprite);
	}
	
	public void firstGame() {
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
		firstGame = sharedPreferences.getInt("firstGame", 0);
		if (firstGame == 0) {
			Editor editor = sharedPreferences.edit();
			firstGame++;
			editor.putInt("firstGame", firstGame);
			editor.commit();
			displayHelpWindow();			
		}
	}
	
	private void createBackground() {
		ParallaxBackground background = new ParallaxBackground(0, 0, 0);
		background.attachParallaxEntity(new ParallaxEntity(0, new Sprite(resourcesManager.camera.getWidth() / 2, resourcesManager.camera.getHeight() / 2, resourcesManager.game_background_region, vbom)));
		this.setBackground(background);
	}
	
	private void createWindows() {
		gameOverWindow = new Sprite(camera.getCenterX(), camera.getCenterY(), resourcesManager.game_over_window_region, vbom);
		levelCompleteWindow = new Sprite(camera.getCenterX(), camera.getCenterY(), resourcesManager.game_level_complete_window_region, vbom);
		pauseWindow = new Sprite(camera.getCenterX(), camera.getCenterY(), resourcesManager.game_pause_window_region, vbom);
		helpWindow = new Sprite(camera.getCenterX(), camera.getCenterY(), resourcesManager.game_help_window_region, vbom) {
        	public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
        		if (pSceneTouchEvent.isActionDown()) {
        			gameHud.setVisible(true);
	    			availablePause = true;
	    			GameScene.this.detachChild(this);
	    			GameScene.this.setIgnoreUpdate(false);
	    			GameScene.this.unregisterTouchArea(this);
	    			camera.setChaseEntity(player);
        		}
        		return true;
        	};
        };
	}
	
	private void createHud() {
		gameHud = new HUD();
		Random rand = new Random();
		
		altimeterText = new Text(30, 1225, resourcesManager.altimeterFont, "Meters to go: 0123456789", new TextOptions(HorizontalAlign.LEFT), vbom);
		coinsText = new Text(30, 1175, resourcesManager.coinsFont, "Coins: 0123456789", new TextOptions(HorizontalAlign.LEFT), vbom);
		levelStartText = new Text(200, 600, resourcesManager.levelStartFont, "ForestBeachCityDesertMountainwestern - 15:00Hs", new TextOptions(HorizontalAlign.LEFT), vbom);
		
		
		openButton = new Sprite(625, 1200, resourcesManager.game_open_button_region, vbom){
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
		
		greenArrow = new Sprite(1000, 0, resourcesManager.game_green_arrow_region, vbom);
		helicopterRedArrow = new Sprite(1000, 0, resourcesManager.game_red_arrow_region.deepCopy(), vbom);
		missileRedArrow = new Sprite(1000, 0, resourcesManager.game_red_arrow_region.deepCopy(), vbom);
		balloonRedArrow = new Sprite(1000, 0, resourcesManager.game_red_arrow_region.deepCopy(), vbom);
		birdRedArrow = new Sprite(1000, 0, resourcesManager.game_red_arrow_region.deepCopy(), vbom);

		shieldBar = new Rectangle(screenWidth/2 + screenWidth/4 + 100, screenHeight - 30, 200, 15, vbom);
		shieldBarBackground = new Rectangle(screenWidth/2 + screenWidth/4, screenHeight - 30, 200, 15, vbom);
		shieldBarFrame = new Sprite(screenWidth/2 + screenWidth/4, screenHeight - 30, resourcesManager.game_shield_bar_frame_region, vbom);
		shieldBarLogo = new Sprite(screenWidth/2 + screenWidth/4 - 150, screenHeight - 30, resourcesManager.game_shield_bar_logo_region, vbom);
		
		shieldBar.setVisible(false);
		shieldBarBackground.setVisible(false);
		shieldBarFrame.setVisible(false);
		shieldBarLogo.setVisible(false);
		
		altimeterText.setAnchorCenter(0, 0);
		levelStartText.setAnchorCenter(0, 0);
		coinsText.setAnchorCenter(0, 0);

		altimeterText.setText("Meters to go: ");
		coinsText.setText("Coins: " + coins);
		
		if (MapScene.getLevel() == 1) {
			if (MapScene.getDayOrNight()) {
				//n = rand.nextInt(max - min + 1) + min;
				int hour = rand.nextInt(12) + 8;
				levelStartText.setText("Beach - " + hour + ":00 hs");
				coinsText.setColor(Color.BLACK_ARGB_PACKED_INT);
				altimeterText.setColor(Color.BLACK_ARGB_PACKED_INT);
			} else {
				int hour = rand.nextInt(5) + 20;
				coinsText.setColor(Color.WHITE_ARGB_PACKED_INT);
				altimeterText.setColor(Color.WHITE_ARGB_PACKED_INT);
				levelStartText.setText("Beach - " + hour + ":00 hs");
			}			
		} else if (MapScene.getLevel() == 2) {
			if (MapScene.getDayOrNight()) {
				int hour = rand.nextInt(12) + 8;
				coinsText.setColor(Color.BLACK_ARGB_PACKED_INT);
				altimeterText.setColor(Color.BLACK_ARGB_PACKED_INT);
				levelStartText.setText("City - " + hour + ":00 hs");
			} else {
				int hour = rand.nextInt(5) + 20;
				coinsText.setColor(Color.WHITE_ARGB_PACKED_INT);
				altimeterText.setColor(Color.WHITE_ARGB_PACKED_INT);
				levelStartText.setText("City - " + hour + ":00 hs");
			}
		} else if (MapScene.getLevel() == 3) {
			if (MapScene.getDayOrNight()) {
				int hour = rand.nextInt(12) + 8;
				coinsText.setColor(Color.BLACK_ARGB_PACKED_INT);
				altimeterText.setColor(Color.BLACK_ARGB_PACKED_INT);
				levelStartText.setText("Forest - " + hour + ":00 hs");
			} else {
				int hour = rand.nextInt(5) + 20;
				coinsText.setColor(Color.WHITE_ARGB_PACKED_INT);
				altimeterText.setColor(Color.WHITE_ARGB_PACKED_INT);
				levelStartText.setText("Forest - " + hour + ":00 hs");
			}
		} else if (MapScene.getLevel() == 4) {
			if (MapScene.getDayOrNight()) {
				int hour = rand.nextInt(12) + 8;
				coinsText.setColor(Color.BLACK_ARGB_PACKED_INT);
				altimeterText.setColor(Color.BLACK_ARGB_PACKED_INT);
				levelStartText.setText("Desert - " + hour + ":00 hs");
			} else {
				int hour = rand.nextInt(5) + 20;
				coinsText.setColor(Color.WHITE_ARGB_PACKED_INT);
				altimeterText.setColor(Color.WHITE_ARGB_PACKED_INT);
				levelStartText.setText("Desert - " + hour + ":00 hs");
			}
		} else if (MapScene.getLevel() == 5) {
			if (MapScene.getDayOrNight()) {
				int hour = rand.nextInt(12) + 8;
				coinsText.setColor(Color.BLACK_ARGB_PACKED_INT);
				altimeterText.setColor(Color.BLACK_ARGB_PACKED_INT);
				levelStartText.setText("Mountain - " + hour + ":00 hs");
			} else {
				int hour = rand.nextInt(5) + 20;
				coinsText.setColor(Color.WHITE_ARGB_PACKED_INT);
				altimeterText.setColor(Color.WHITE_ARGB_PACKED_INT);
				levelStartText.setText("Mountain - " + hour + ":00 hs");
			}
		} else if (MapScene.getLevel() == 6) {
			if (MapScene.getDayOrNight()) {
				int hour = rand.nextInt(12) + 8;
				coinsText.setColor(Color.BLACK_ARGB_PACKED_INT);
				altimeterText.setColor(Color.BLACK_ARGB_PACKED_INT);
				levelStartText.setText("Western - " + hour + ":00 hs");
			} else {
				int hour = rand.nextInt(5) + 20;
				coinsText.setColor(Color.WHITE_ARGB_PACKED_INT);
				altimeterText.setColor(Color.WHITE_ARGB_PACKED_INT);
				levelStartText.setText("Western - " + hour + ":00 hs");
			}
		}

		gameHud.attachChild(altimeterText);
		gameHud.attachChild(levelStartText);
		gameHud.attachChild(coinsText);
		gameHud.attachChild(openButton);
		gameHud.attachChild(greenArrow);
		gameHud.attachChild(helicopterRedArrow);
		gameHud.attachChild(missileRedArrow);
		gameHud.attachChild(balloonRedArrow);
		gameHud.attachChild(birdRedArrow);
		
		gameHud.attachChild(shieldBarBackground);
		gameHud.attachChild(shieldBar);
		gameHud.attachChild(shieldBarFrame);
		gameHud.attachChild(shieldBarLogo);
		
		gameHud.registerTouchArea(openButton);
		
		camera.setHUD(gameHud);
	}
	
	private void createPhysics() {
		physicsWorld = new FixedStepPhysicsWorld(60, new Vector2(0, -3), false);
		physicsWorld.setContactListener(contactListener());
		registerUpdateHandler(physicsWorld);
	}
	
	private void saveUpperImpulseCounter(String key, int upperImpulseCounter) {
		Editor editor = sharedPreferences.edit();
		int uiCounter = sharedPreferences.getInt(key, 0);
		uiCounter += upperImpulseCounter;
		editor.putInt(key, uiCounter);
		editor.commit();
	}
	
	private void saveAntigravityCounter(String key, int antigravityCounter) {
		Editor editor = sharedPreferences.edit();
		int agCounter = sharedPreferences.getInt(key, 0);
		agCounter += antigravityCounter;
		editor.putInt(key, agCounter);
		editor.commit();
	}
	
	private void saveShieldCounter(String key, int shieldCounter) {
		Editor editor = sharedPreferences.edit();
		int shCounter = sharedPreferences.getInt(key, 0);
		shCounter += shieldCounter;
		editor.putInt(key, shCounter);
		editor.commit();
	}
	
	private void saveSlowCounter(String key, int slowCounter) {
		Editor editor = sharedPreferences.edit();
		int sCounter = sharedPreferences.getInt(key, 0);
		sCounter += slowCounter;
		editor.putInt(key, sCounter);
		editor.commit();
	}
	
	private void saveHelicopterCounter(String key, int helicopterCounter) {
		Editor editor = sharedPreferences.edit();
		int heliCounter = sharedPreferences.getInt(key, 0);
		heliCounter += helicopterCounter;
		editor.putInt(key, heliCounter);
		editor.commit();
	}
	
	private void saveBalloonCounter(String key, int balloonCounter) {
		Editor editor = sharedPreferences.edit();
		int ballCounter = sharedPreferences.getInt(key, 0);
		ballCounter += balloonCounter;
		editor.putInt(key, ballCounter);
		editor.commit();
	}
	
	private void saveBirdCounter(String key, int birdCounter) {
		Editor editor = sharedPreferences.edit();
		int bCounter = sharedPreferences.getInt(key, 0);
		bCounter += birdCounter;
		editor.putInt(key, bCounter);
		editor.commit();
	}
	
	private void saveFreeFliedMeters(String key, int freeFliedCounter) {
		Editor editor = sharedPreferences.edit();
		int ffCounter = sharedPreferences.getInt(key, 0);
		ffCounter += freeFliedCounter;
		editor.putInt(key, ffCounter);
		editor.commit();
	}
	
	private void saveParachuteFliedMeters(String key, int parachuteFliedCounter) {
		Editor editor = sharedPreferences.edit();
		int pfCounter = sharedPreferences.getInt(key, 0);
		pfCounter += parachuteFliedCounter;
		editor.putInt(key, pfCounter);
		editor.commit();
	}
	
	private void saveUnsuccessfulJumps(String key) {
		Editor editor = sharedPreferences.edit();
		int numberOfUnsuccessfulJumps = sharedPreferences.getInt(key, 0);
		numberOfUnsuccessfulJumps++;
		editor.putInt(key, numberOfUnsuccessfulJumps);
		editor.commit();
	}
	
	private void saveSuccessfulJumps(String key) {
		Editor editor = sharedPreferences.edit();
		int numberOfSuccessfulJumps = sharedPreferences.getInt(key, 0);
		numberOfSuccessfulJumps++;
		editor.putInt(key, numberOfSuccessfulJumps);
		editor.commit();
	}
	
	private void saveMaxFliedMeters(String key, int fliedMeters) {
		Editor editor = sharedPreferences.edit();
		if (sharedPreferences.getInt(key, 0) < fliedMeters) {
			editor.putInt(key, fliedMeters);
		}		
		editor.commit();
	}
	
	private void saveMaxFreeFliedMeters(String key, int freeFliedMeters) {
		Editor editor = sharedPreferences.edit();
		if (sharedPreferences.getInt(key, 0) < freeFliedMeters) {
			editor.putInt(key, freeFliedMeters);
		}		
		editor.commit();
	}
	
	private void saveMaxParachuteFliedMeters(String key, int parachuteFliedMeters) {
		Editor editor = sharedPreferences.edit();
		if (sharedPreferences.getInt(key, 0) < parachuteFliedMeters) {
			editor.putInt(key, parachuteFliedMeters);
		}		
		editor.commit();
	}
	
	private void saveCoins(String key, int coins) {
		Editor editor = sharedPreferences.edit();
		int coinsCounter = sharedPreferences.getInt(key, 0);
		coinsCounter += coins;
		editor.putInt(key, coinsCounter);
		editor.commit();
	}
	
	/*//Parse level from XML file
	private void loadLevel (int level) {
		final SimpleLevelLoader levelLoader = new SimpleLevelLoader(vbom);
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
				
				if (type.equals(TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_BACK_LOCATION)) {
					levelObject = new Sprite(x, y, resourcesManager.game_back_location_region, vbom);
				} else if (type.equals(TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_COIN)) {
					//n = rand.nextInt(max - min + 1) + min;
					Random rand = new Random();
					if (coinsCounter == 0) {
						//randCoinsX = rand.nextInt(441) - 220;
						randCoinsX = rand.nextInt(601) - 300;
					}
					coinsCounter++;
					if (coinsCounter == 5) {
						coinsCounter = 0;
					}
					coin = new AnimatedSprite(x + randCoinsX, y, resourcesManager.coin_region, vbom) {
						protected void onManagedUpdate(float pSecondsElapsed) {
							super.onManagedUpdate(pSecondsElapsed);
							if (player.collidesWith(this)) {
								resourcesManager.coinSound.play();
								destroySprite(this);
								addCoins(COINS_VALUE);
							}
						};
					};
					final long[] COIN_ANIMATE = new long[] {100, 100, 100, 100};
					coin.animate(COIN_ANIMATE, 0, 3, true);
					levelObject = coin;
				} else if (type.equals(TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_UPPER_IMPULSE)) {
					//n = rand.nextInt(max - min + 1) + min;
					Random rand = new Random();
					int randX = rand.nextInt(601) - 300;
					int randY = rand.nextInt(5001) - 2500;
					levelObject = new Sprite(x + randX, y + randY, resourcesManager.upperImpulse_region, vbom) {
						protected void onManagedUpdate(float pSecondsElapsed) {
							super.onManagedUpdate(pSecondsElapsed);
							if ((player.getY() - this.getY()) < 2250 && (player.getY() - this.getY()) > 640) {
								greenArrow.setPosition(this.getX(), 75);
							} else if ((player.getY() - this.getY()) < 640 && (player.getY() - this.getY()) > 0) {
								greenArrow.setPosition(1500, 0);
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
					int randX = rand.nextInt(601) - 300;
					int randY = rand.nextInt(5001) - 2500;
					levelObject = new Sprite(x + randX, y + randY, resourcesManager.antiGravity_region, vbom) {
						protected void onManagedUpdate(float pSecondsElapsed) {
							super.onManagedUpdate(pSecondsElapsed);
							if ((player.getY() - this.getY()) < 2250 && (player.getY() - this.getY()) > 640) {
								greenArrow.setPosition(this.getX(), 75);
							} else if ((player.getY() - this.getY()) < 640 && (player.getY() - this.getY()) > 0) {
								greenArrow.setPosition(1500, 0);
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
					int randX = rand.nextInt(601) - 300;
					int randY = rand.nextInt(5001) - 2500;
					levelObject = new Sprite(x + randX, y + randY, resourcesManager.slow_region, vbom) {
						protected void onManagedUpdate(float pSecondsElapsed) {
							super.onManagedUpdate(pSecondsElapsed);
							if ((player.getY() - this.getY()) < 2250 && (player.getY() - this.getY()) > 640) {
								greenArrow.setPosition(this.getX(), 75);
							} else if ((player.getY() - this.getY()) < 640 && (player.getY() - this.getY()) > 0) {
								greenArrow.setPosition(1500, 0);
							}
							if (player.collidesWith(this)) {
								slowCounter++;
								destroySprite(this);
								player.slowDownPlayer();
							}
						};
					};
				} else if (type.equals(TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_BIRD)) {
					final Rectangle moveSensor = new Rectangle(360, y + BIRD_MOVE_SENSOR, 720, 0.1f, vbom);
					final Rectangle soundSensor = new Rectangle(360, y + BIRD_SOUND_SENSOR, 720, 0.1f, vbom);
					bird = new Bird(x, y, vbom, camera, physicsWorld, resourcesManager.bird_region.deepCopy()) {
						protected void onManagedUpdate(float pSecondsElapsed) {
							super.onManagedUpdate(pSecondsElapsed);
							if ((player.getY() - this.getY()) < 1500 && (player.getY() - this.getY()) > 640) {
								birdRedArrow.setPosition(this.getX(), 75);
							} else if ((player.getY() - this.getY()) < 640 && (player.getY() - this.getY()) > 0) {
								birdRedArrow.setPosition(1500, 0);
							}
							if (player.collidesWith(moveSensor)) {
								this.startMoving();
								moveSensor.setPosition(1000, 1000);
							}
							if (player.collidesWith(soundSensor)) {
								resourcesManager.bird.play();
								soundSensor.setPosition(1000, 1000);
							}							
							if (player.collidesWith(this)) {
								if (shield) {
									playerSpeed = player.getFallVelocity();
									player.getPlayerBody().setLinearVelocity(new Vector2(player.getPlayerBody().getLinearVelocity().x, playerSpeed));
									this.setVisible(false);
									this.setIgnoreUpdate(true);
									physicsWorld.unregisterPhysicsConnector(physicsWorld.getPhysicsConnectorManager().findPhysicsConnectorByShape(this));
								}								
							}
						};
					};
					levelObject = bird;
				} else if (type.equals(TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_LEFT_BIRD)) {
					final Rectangle moveSensor = new Rectangle(360, y + BIRD_MOVE_SENSOR, 720, 0.1f, vbom);
					final Rectangle soundSensor = new Rectangle(360, y + BIRD_SOUND_SENSOR, 720, 0.1f, vbom);
					leftBird = new LeftBird(x, y, vbom, camera, physicsWorld, resourcesManager.left_bird_region.deepCopy()) {
						protected void onManagedUpdate(float pSecondsElapsed) {
							super.onManagedUpdate(pSecondsElapsed);
							if ((player.getY() - this.getY()) < 1500 && (player.getY() - this.getY()) > 640) {
								birdRedArrow.setPosition(this.getX(), 75);
							} else if ((player.getY() - this.getY()) < 640 && (player.getY() - this.getY()) > 0) {
								birdRedArrow.setPosition(1500, 0);
							}
							if (player.collidesWith(moveSensor)) {
								this.startMoving();
								moveSensor.setPosition(1000, 1000);
							}
							if (player.collidesWith(soundSensor)) {
								resourcesManager.bird.play();
								soundSensor.setPosition(1000, 1000);
							}
							if (player.collidesWith(this)) {
								if (shield) {
									playerSpeed = player.getFallVelocity();
									player.getPlayerBody().setLinearVelocity(new Vector2(player.getPlayerBody().getLinearVelocity().x, playerSpeed));
									this.setVisible(false);
									this.setIgnoreUpdate(true);
									physicsWorld.unregisterPhysicsConnector(physicsWorld.getPhysicsConnectorManager().findPhysicsConnectorByShape(this));
								}								
							}
						};
					};
					levelObject = leftBird;
				} else if (type.equals(TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_LANDING_PLATFORM)) {
					levelObject = new Sprite(x, y, resourcesManager.game_landing_platfom_region, vbom) {
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
									
									//Cambiar esto sino el personaje traspasa la base
									if (distanceToFloorAtOpenParachute < 1000 || !openParachute){
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
	}*/
	
	private ContactListener contactListener() {
		ContactListener contactListener = new ContactListener() {
			
			@Override
			public void preSolve(Contact contact, Manifold oldManifold) {
			}
			
			@Override
			public void postSolve(Contact contact, ContactImpulse impulse) {
				player.getPlayerBody().setLinearVelocity(new Vector2(player.getPlayerBody().getLinearVelocity().x, playerSpeed));
			}
			
			@Override
			public void endContact(Contact contact) {
				
			}
			
			@Override
			public void beginContact(Contact contact) {
				final Fixture x1 = contact.getFixtureA();
				final Fixture x2 = contact.getFixtureB();
				
				if (x1.getBody().getUserData().equals("bird") && x2.getBody().getUserData().equals("player")) {
					if (!shield) {
						player.killPlayer();
						setInactiveBody(x1.getBody());
					}
				}
				
				if (x1.getBody().getUserData().equals("player") && x2.getBody().getUserData().equals("bird")) {
					if (!shield) {
						player.killPlayer();
						setInactiveBody(x2.getBody());
					}
				}
				
				if (x1.getBody().getUserData().equals("leftBird") && x2.getBody().getUserData().equals("player")) {
					if (!shield) {
						player.killPlayer();
						setInactiveBody(x1.getBody());
					}
				}
				
				if (x1.getBody().getUserData().equals("player") && x2.getBody().getUserData().equals("leftBird")) {
					if (!shield) {
						player.killPlayer();
						setInactiveBody(x2.getBody());
					}
				}
				
				if (x1.getBody().getUserData().equals("helicopter") && x2.getBody().getUserData().equals("player")) {
					if (!shield) {
						player.killPlayer();
						setInactiveBody(x1.getBody());
					}
				}
				
				if (x1.getBody().getUserData().equals("player") && x2.getBody().getUserData().equals("helicopter")) {
					if (!shield) {
						player.killPlayer();
						setInactiveBody(x2.getBody());
					}
				}
				
				if (x1.getBody().getUserData().equals("balloon") && x2.getBody().getUserData().equals("player")) {
					if (!shield) {
						player.killPlayer();
						setInactiveBody(x1.getBody());
					}
				}
				
				if (x1.getBody().getUserData().equals("player") && x2.getBody().getUserData().equals("balloon")) {
					if (!shield) {
						player.killPlayer();
						setInactiveBody(x2.getBody());
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
	
	private void addCoins(int coins) {
		player.addPlayerCoins(coins);
		coinsText.setText("Coins: " + player.getPlayerCoins());
	}
	
	private void loadCounters() {
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
	public void handleOnPause() {
		if (availablePause) {
			displayPauseWindow();
		}		
	}
	
	@Override
	public void onBackKeyPressed() {
		engine.runOnUpdateThread(new Runnable() {
			
			@Override
			public void run() {
				if (availablePause) {
					displayPauseWindow();
				} else {
					if (!dead && !levelCompleteBoolean) {
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
	
	public void displayPauseWindow() {
		availablePause = false;
		GameScene.this.setIgnoreUpdate(true);
		camera.setChaseEntity(null);
		pauseWindow.setPosition(camera.getCenterX(), camera.getCenterY());

		gameHud.setVisible(false);
		
		final Sprite resumeButton = new Sprite(510, 75, resourcesManager.game_resume_button_region, vbom){
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
	    final Sprite mapButton = new Sprite(310, 75, resourcesManager.game_map_button_region, vbom){
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
	    final Sprite quitButton = new Sprite(110, 75, resourcesManager.game_quit_button_region, vbom){
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
		GameScene.this.attachChild(pauseWindow);
	}
	
	private void displayHelpWindow() {
		GameScene.this.setIgnoreUpdate(true);
		
		camera.setChaseEntity(null);
        availablePause = false;
		gameHud.setVisible(false);
		helpWindow.setPosition(camera.getCenterX(), camera.getCenterY());

		GameScene.this.attachChild(helpWindow);
        GameScene.this.registerTouchArea(helpWindow);
	}
	
	private void displayLevelCompleted() {
		GameScene.this.setIgnoreUpdate(true);
		levelCompleteBoolean = true;
        camera.setChaseEntity(null);
        availablePause = false;
        levelCompleteWindow.setPosition(camera.getCenterX(), camera.getCenterY());
		GameScene.this.attachChild(levelCompleteWindow);
	    final Sprite flyAgainButton = new Sprite(510, 75, resourcesManager.game_fly_again_button_region, vbom){
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
	    final Sprite mapButton = new Sprite(310, 75, resourcesManager.game_map_button_region, vbom){
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
	    final Sprite quitButton = new Sprite(110, 75, resourcesManager.game_quit_button_region, vbom){
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
		levelCompleted.setText("You landed safely!! You freeflied " + freeFliedMeters + " mts");
		GameScene.this.registerTouchArea(flyAgainButton);
	    GameScene.this.registerTouchArea(quitButton);
	    GameScene.this.registerTouchArea(mapButton);
	    levelCompleteWindow.attachChild(quitButton);
	    levelCompleteWindow.attachChild(flyAgainButton);
	    levelCompleteWindow.attachChild(mapButton);
	    GameScene.this.attachChild(levelCompleted);
	    displayAchievements();
	    activity.showAd();
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
			Text achievementsUnlocked = new Text(camera.getCenterX(), camera.getCenterY(), resourcesManager.achievementsUnlockedFont, "New achievement unlocked!!!", new TextOptions(HorizontalAlign.LEFT), vbom);
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
		saveCoins("coins", player.getPlayerCoins());
	}
	
	private void saveScoreDataOnDie() {
		saveUpperImpulseCounter("upperImpulseCounter", upperImpulseCounter);
		saveShieldCounter("shieldCounter", shieldCounter);
		saveAntigravityCounter("antigravityCounter", antigravityCounter);
		saveSlowCounter("slowCounter", slowCounter);
		saveHelicopterCounter("helicopterCounter", helicopterCounter);
		saveBalloonCounter("balloonCounter", balloonCounter);
		saveBirdCounter("birdCounter", birdCounter);
		saveUnsuccessfulJumps("unsuccessfulJumps");
		saveFreeFliedMeters("freeFliedMetersCounter", freeFliedMeters);
		saveParachuteFliedMeters("parachuteFliedMetersCounter", parachuteFliedMeters);
		saveMaxFliedMeters("fliedMeters", fliedMeters);
		saveMaxFreeFliedMeters("freeFliedMeters", freeFliedMeters);
		saveMaxParachuteFliedMeters("parachuteFliedMeters", parachuteFliedMeters);
		saveCoins("coins", player.getPlayerCoins());
	}
}

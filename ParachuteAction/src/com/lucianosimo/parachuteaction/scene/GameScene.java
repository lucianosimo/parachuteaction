package com.lucianosimo.parachuteaction.scene;

import java.io.IOException;
import java.util.Iterator;
import java.util.Random;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.IEntity;
import org.andengine.entity.scene.background.AutoParallaxBackground;
import org.andengine.entity.scene.background.ParallaxBackground.ParallaxEntity;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.extension.physics.box2d.FixedStepPhysicsWorld;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.SAXUtils;
import org.andengine.util.adt.align.HorizontalAlign;
import org.andengine.util.debug.Debug;
import org.andengine.util.level.EntityLoader;
import org.andengine.util.level.constants.LevelConstants;
import org.andengine.util.level.simple.SimpleLevelEntityLoaderData;
import org.andengine.util.level.simple.SimpleLevelLoader;
import org.xml.sax.Attributes;

import android.util.Log;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.lucianosimo.parachuteaction.base.BaseScene;
import com.lucianosimo.parachuteaction.manager.SceneManager;
import com.lucianosimo.parachuteaction.manager.SceneManager.SceneType;
import com.lucianosimo.parachuteaction.object.Helicopter;
import com.lucianosimo.parachuteaction.object.Player;

public class GameScene extends BaseScene{
	
	//Scene indicators
	private HUD gameHud;
	
	//Counters
	private int fliedMeters = 0;
	private int distanceToFloor = 0;
	private int oldDistanceToFloor = 0;
	
	//Booleans
	private Boolean firstFall = true;
	
	//Texts variables
	private Text meterCounterText;
	private Text altimeterText;
	
	//Instances
	private Player player;
	private Helicopter helicopter;
	
	//Physics world variable
	private PhysicsWorld physicsWorld;
	
	//Constants	
	private static final int PIXEL_METER_RATE = 16;
	
	private static final String TAG_ENTITY = "entity";
	private static final String TAG_ENTITY_ATTRIBUTE_X = "x";
	private static final String TAG_ENTITY_ATTRIBUTE_Y = "y";
	private static final String TAG_ENTITY_ATTRIBUTE_TYPE = "type";
	private static final Object TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_PLAYER = "player";
	private static final Object TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_HELICOPTER = "helicopter";
	private static final Object TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_CLOUD = "cloud";
	private static final Object TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_UPPER_IMPULSE = "upperImpulse";
	private static final Object TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_LANDING_PLATFORM = "landingPlatform";

	@Override
	public void createScene() {
		createBackground();
		createHud();
		createPhysics();
		loadLevel(1);
	}
	
	private void createBackground() {
		AutoParallaxBackground background = new AutoParallaxBackground(0, 0, 0, 12);
		background.attachParallaxEntity(new ParallaxEntity(0, new Sprite(240, 427, resourcesManager.background_region, vbom)));
		this.setBackground(background);
	}
	
	private void createHud() {
		gameHud = new HUD();
		
		meterCounterText = new Text(20, 820, resourcesManager.meterCounterFont, "Meter Counter: 0123456789", new TextOptions(HorizontalAlign.LEFT), vbom);
		altimeterText = new Text(275, 820, resourcesManager.altimeterFont, "Meters to go: 0123456789", new TextOptions(HorizontalAlign.LEFT), vbom);
		
		meterCounterText.setAnchorCenter(0, 0);
		altimeterText.setAnchorCenter(0, 0);
		
		meterCounterText.setText("Flied Meters: " + fliedMeters);
		altimeterText.setText("Meters to go: ");
		
		gameHud.attachChild(meterCounterText);
		gameHud.attachChild(altimeterText);
		
		camera.setHUD(gameHud);
	}
	
	private void createPhysics() {
		physicsWorld = new FixedStepPhysicsWorld(60, new Vector2(0, -3), false);
		physicsWorld.setContactListener(contactListener());
		registerUpdateHandler(physicsWorld);
	}
	
	//Parse level from XML file
		private void loadLevel (int level) {
			final SimpleLevelLoader levelLoader = new SimpleLevelLoader(vbom);
			final FixtureDef FIXTURE_DEF= PhysicsFactory.createFixtureDef(0, 0f, 0.5f);
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
						levelObject = new Sprite(x, y, resourcesManager.cloud_region, vbom);
					} else if (type.equals(TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_UPPER_IMPULSE)) {
						//n = rand.nextInt(max - min + 1) + min;
						Random rand = new Random();
						int n = rand.nextInt(481) - 240;
						levelObject = new Sprite(x + n, y, resourcesManager.upperImpulse_region, vbom) {
							protected void onManagedUpdate(float pSecondsElapsed) {
								super.onManagedUpdate(pSecondsElapsed);
								if (player.collidesWith(this)) {
									player.upperImpulse();
									this.setVisible(false);
									this.setIgnoreUpdate(true);
								}
							};
						};
					} else if (type.equals(TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_HELICOPTER)) {
						helicopter = new Helicopter(x, y, vbom, camera, physicsWorld, resourcesManager.helicopter_region.deepCopy()) {
							protected void onManagedUpdate(float pSecondsElapsed) {
								super.onManagedUpdate(pSecondsElapsed);
								this.startMoving();
							};
							public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
								if (pSceneTouchEvent.isActionDown()) {
									final Sprite helicopterRef = this; 
									this.setVisible(false);
									destroySprite(helicopterRef);					
								}
								return true;
							};
						};
						levelObject = helicopter;
						GameScene.this.registerTouchArea(levelObject);
					} else if (type.equals(TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_PLAYER)) {
						player = new Player(x, y, vbom, camera, physicsWorld) {
							
							@Override
							protected void onManagedUpdate(float pSecondsElapsed) {
								super.onManagedUpdate(pSecondsElapsed);
								engine.runOnUpdateThread(new Runnable() {
									
									@Override
									public void run() {
										//bug..corregir
										int levelHeight = (int) (camera.getBoundsHeight() / PIXEL_METER_RATE);
										distanceToFloor = (int) player.getY() / PIXEL_METER_RATE;
										if (firstFall) {
											oldDistanceToFloor = distanceToFloor;
										}
										firstFall = false;
										altimeterText.setText("Meters to go: " + distanceToFloor);
										if (player.getFallVelocity() < 0) {
											fliedMeters = fliedMeters + (oldDistanceToFloor - distanceToFloor);
											meterCounterText.setText("Flied Meters: " + fliedMeters);
										}
										Log.e("parachute", "level/rate " + levelHeight/PIXEL_METER_RATE);
										if (distanceToFloor < (levelHeight/4)) {
											player.openParachute();
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
										Text gameOver = new Text(camera.getCenterX(), camera.getCenterY(), resourcesManager.gameOverFont, "Game over!", new TextOptions(HorizontalAlign.LEFT), vbom);
								        gameOver.setText("Game over!!");
										GameScene.this.attachChild(gameOver);
										GameScene.this.setIgnoreUpdate(true);
								        camera.setChaseEntity(null);
								        
									}
								});
								
							}
							
						};
						levelObject = player;
					} else if (type.equals(TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_LANDING_PLATFORM)) {
						levelObject = new Sprite(x, y, resourcesManager.landing_platfom_region, vbom) {
							protected void onManagedUpdate(float pSecondsElapsed) {
								super.onManagedUpdate(pSecondsElapsed);
								if (player.collidesWith(this)) {
									engine.runOnUpdateThread(new Runnable() {
										
										@Override
										public void run() {
											GameScene.this.setIgnoreUpdate(true);
											camera.setChaseEntity(null);
											Text levelCompleted = new Text(camera.getCenterX(), camera.getCenterY(), resourcesManager.levelCompletedFont, "Youlandedsafely: 0123456789 Youfliedmeters", new TextOptions(HorizontalAlign.LEFT), vbom);
											levelCompleted.setText("You landed safely!! You flied " + fliedMeters + " meters");
											GameScene.this.attachChild(levelCompleted);											
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
				final Fixture x1 = contact.getFixtureA();
				final Fixture x2 = contact.getFixtureB();
				
			}
			
			@Override
			public void beginContact(Contact contact) {
				final Fixture x1 = contact.getFixtureA();
				final Fixture x2 = contact.getFixtureB();
				
				if (x1.getBody().getUserData().equals("helicopter") && x2.getBody().getUserData().equals("player")) {
					/*player.reduceLifes();
					player.reduceLifes();
					player.reduceLifes();*/
					player.killPlayer();
					setInactiveBody(x1.getBody());
				}
			}
		};
		return contactListener;
	}

	@Override
	public void onBackKeyPressed() {
		engine.runOnUpdateThread(new Runnable() {
			
			@Override
			public void run() {
				GameScene.this.setIgnoreUpdate(true);
				camera.setChaseEntity(null);
				gameHud.dispose();
				gameHud.setVisible(false);
				detachChild(gameHud);
				myGarbageCollection();
				SceneManager.getInstance().loadMenuScene(engine, GameScene.this);
			}
		});
		
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
	
}

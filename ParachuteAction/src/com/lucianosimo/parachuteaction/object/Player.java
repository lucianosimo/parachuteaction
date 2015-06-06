package com.lucianosimo.parachuteaction.object;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.lucianosimo.parachuteaction.GameActivity;
import com.lucianosimo.parachuteaction.manager.ResourcesManager;

public abstract class Player extends AnimatedSprite{

	private Body body;
	private Boolean openParachute = false;
	private int parachuteSpeed = -17;
	private int playerCoins = 0;
	private FixtureDef fixture;
	
	private static final int MAX_FREEFALL_SPEED = -25;
	
	private static final int LEFT_MARGIN = 0;
	private static final int RIGHT_MARGIN = 720;
	
	public Player(float pX, float pY, VertexBufferObjectManager vbom, Camera camera, PhysicsWorld physicsWorld) {
		super(pX, pY, ResourcesManager.getInstance().game_player_region, vbom);
		startAnimation();
		createPhysics(camera, physicsWorld);
		camera.setChaseEntity(this);
	}
	
	public abstract void onDie();
	
	private void createPhysics(final Camera camera, PhysicsWorld physicsWorld) {
		fixture = PhysicsFactory.createFixtureDef(1.0f, 0, 0);
		fixture.filter.groupIndex = 1;
		
		body = PhysicsFactory.createBoxBody(physicsWorld, this, BodyType.DynamicBody, fixture);
		body.setUserData("player");
		body.setFixedRotation(true);
		
		physicsWorld.registerPhysicsConnector(new PhysicsConnector(this, body, true, false) {
			@Override
			public void onUpdate(float pSecondsElapsed) {
				super.onUpdate(pSecondsElapsed);
				camera.onUpdate(0.1f);
				if (getY() <= 0) {
					onDie();
				}
				if (getX() < (LEFT_MARGIN + 30)) {
					body.setLinearVelocity(new Vector2( 1, body.getLinearVelocity().y));
				} else if (getX() > RIGHT_MARGIN - 30) {
					body.setLinearVelocity(new Vector2(-1, body.getLinearVelocity().y));
				} else {
					body.setLinearVelocity(new Vector2(GameActivity.mGravityX, body.getLinearVelocity().y));
				}
				if (body.getLinearVelocity().y < MAX_FREEFALL_SPEED) {
					body.setLinearVelocity(body.getLinearVelocity().x, MAX_FREEFALL_SPEED);
				}
				if (openParachute) {
					body.setLinearVelocity(new Vector2(body.getLinearVelocity().x, parachuteSpeed));
				} else {
					body.setLinearVelocity(new Vector2(body.getLinearVelocity().x, body.getLinearVelocity().y));
				}				
			}
		});
	}
	
	public void disablePlayerCollision() {
		body.getFixtureList().get(0).setSensor(true);
	}
	
	public void enablePlayerCollision() {
		body.getFixtureList().get(0).setSensor(false);
	}
	
	public void addPlayerCoins(int coins) {
		playerCoins += coins;
	}
	
	public void startAnimation(){
		final long[] PLAYER_ANIMATE = new long[] {100, 100};
		animate(PLAYER_ANIMATE, 0, 1, true);
	}
	
	public int getPlayerCoins() {
		return playerCoins;
	}
	
	public void openParachute() {
		openParachute = true;		
		final long[] PLAYER_ANIMATE = new long[] {100, 100};
		animate(PLAYER_ANIMATE, 2, 3, true);
		if (body.getLinearVelocity().y > parachuteSpeed) {
			parachuteSpeed = (int) body.getLinearVelocity().y;
		}
	}
	
	public void stopPlayer() {
		body.setLinearVelocity(new Vector2(0, 0));
	}
	
	public int getParachuteSpeed() {
		return parachuteSpeed;
	}
	
	public void setParachuteSpeed(int ps) {
		parachuteSpeed = ps;
	}
	
	public void reduceParachuteSpeed() {
		if (parachuteSpeed < -10) {
			parachuteSpeed++;
		}		
	}
	
	public void upperImpulse() {
		body.setLinearVelocity(new Vector2(0, 25));
	}
	
	public int getFallVelocity() {
		return (int) body.getLinearVelocity().y;
	}
	
	public void killPlayer() {
		onDie();
	}
	
	public void slowDownPlayer() {
		body.setLinearVelocity(new Vector2(body.getLinearVelocity().x, -6));
	}
	
	public float getPlayerSpeed() {
		return -body.getLinearVelocity().y;
	}
	
	public Body getPlayerBody() {
		return body;
	}

}

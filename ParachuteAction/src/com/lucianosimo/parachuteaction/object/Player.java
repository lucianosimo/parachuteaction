package com.lucianosimo.parachuteaction.object;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.util.Log;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.lucianosimo.parachuteaction.GameActivity;
import com.lucianosimo.parachuteaction.manager.ResourcesManager;

public abstract class Player extends AnimatedSprite{

	private Body body;
	private Boolean openParachute = false;
	private int parachuteSpeed = -15;
	
	private static final int LEFT_MARGIN = 0;
	private static final int RIGHT_MARGIN = 480;
	//private static final int BOTTOM_MARGIN = 0;
	//private static final int TOP_MARGIN = 854;
	
	public Player(float pX, float pY, VertexBufferObjectManager vbom, Camera camera, PhysicsWorld physicsWorld) {
		super(pX, pY, ResourcesManager.getInstance().player_region, vbom);
		createPhysics(camera, physicsWorld);
		camera.setChaseEntity(this);
	}
	
	public abstract void onDie();
	
	private void createPhysics(final Camera camera, PhysicsWorld physicsWorld) {
		body = PhysicsFactory.createBoxBody(physicsWorld, this, BodyType.DynamicBody, PhysicsFactory.createFixtureDef(0, 0, 0));
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
				if (openParachute) {
					body.setLinearVelocity(new Vector2(body.getLinearVelocity().x, parachuteSpeed));
				} else {
					body.setLinearVelocity(new Vector2(body.getLinearVelocity().x, body.getLinearVelocity().y));
				}				
			}
		});
	}
	
	public void openParachute() {
		openParachute = true;
	}
	
	public int getParachuteSpeed() {
		return parachuteSpeed;
	}
	
	public void setParachuteSpeed(int ps) {
		parachuteSpeed = ps;
	}
	
	public void reduceParachuteSpeed() {
		if (parachuteSpeed < -6) {
			parachuteSpeed++;
		}		
		Log.e("parachute", "speed " + parachuteSpeed);
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

}

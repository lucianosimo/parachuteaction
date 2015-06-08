package com.lucianosimo.parachuteaction.object;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public class Ufo extends Sprite{

	private Body body;
	private FixtureDef fixture;
	private boolean isAttacking = false;
	
	private static final int DOWN_SPEED = -28;
	private static final int UP_SPEED = 10;
	
	public Ufo(float pX, float pY, VertexBufferObjectManager vbom, Camera camera, PhysicsWorld physicsWorld, ITextureRegion region) {
		super(pX, pY, region, vbom);
		createPhysics(camera, physicsWorld, region, vbom);
	}
	
	private void createPhysics(final Camera camera, PhysicsWorld physicsWorld, ITextureRegion region, VertexBufferObjectManager vbom) {
		fixture = PhysicsFactory.createFixtureDef(0, 0, 0);
		fixture.filter.groupIndex = -1;
		body = PhysicsFactory.createBoxBody(physicsWorld, this, BodyType.KinematicBody, fixture);
		body.setFixedRotation(true);
		body.setUserData("ufo");
		physicsWorld.registerPhysicsConnector(new PhysicsConnector(this, body, true, false));
	}
	
	public Body getBody() {
		return body;
	}
	
	public boolean isAttacking() {
		return isAttacking;
	}
	
	public void setAttacking(boolean attack) {
		isAttacking = attack;
	}
	
	public void moveDown() {
		body.setLinearVelocity(new Vector2(0, DOWN_SPEED));
	}
	
	public void moveUp() {
		body.setLinearVelocity(new Vector2(0, UP_SPEED));
	}
	
	public void stopMoving() {
		body.setLinearVelocity(new Vector2(0, 0));
	}
	
	public void sameSpeedAsPlayer(float speed) {
		body.setLinearVelocity(new Vector2(0, speed));
	}
	
}

package com.lucianosimo.parachuteaction.object;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.extension.physics.box2d.util.constants.PhysicsConstants;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public class Missile extends Sprite{

	private Body body;
	private FixtureDef fixture;
	
	private static final int SPEED = 13;
	private static final float MISSILE_WIDTH = 175;
	private static final float MISSILE_HEIGHT = 228;
	
	public Missile(float pX, float pY, VertexBufferObjectManager vbom, Camera camera, PhysicsWorld physicsWorld, ITextureRegion region) {
		super(pX, pY, region, vbom);
		createPhysics(camera, physicsWorld, region, vbom);
	}
	
	private void createPhysics(final Camera camera, PhysicsWorld physicsWorld, ITextureRegion region, VertexBufferObjectManager vbom) {
		final float width = MISSILE_WIDTH / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT;
		final float height = MISSILE_HEIGHT / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT;
		final Vector2[] vector = {
			new Vector2(-0.10952f*width, +0.50146f*height),
			new Vector2(+0.09048f*width, +0.51023f*height),
			new Vector2(+0.14762f*width, -0.19591f*height),
			new Vector2(+0.51333f*width, -0.42398f*height),
			new Vector2(-0.00095f*width, -0.50731f*height),
			new Vector2(-0.50952f*width, -0.41959f*height),
			new Vector2(-0.14952f*width, -0.18713f*height),
		};
		fixture = PhysicsFactory.createFixtureDef(0, 0, 0);
		fixture.filter.groupIndex = -1;
		body = PhysicsFactory.createPolygonBody(physicsWorld, this, vector, BodyType.KinematicBody, fixture);
		body.setFixedRotation(true);
		body.setUserData("missile");
		physicsWorld.registerPhysicsConnector(new PhysicsConnector(this, body, true, false));
	}
	
	public Body getBody() {
		return body;
	}
	
	public void startMoving() {
		body.setLinearVelocity(new Vector2(0, SPEED));
	}
	
	public void stopMoving() {
		body.setLinearVelocity(new Vector2(0, 0));
	}
	
}

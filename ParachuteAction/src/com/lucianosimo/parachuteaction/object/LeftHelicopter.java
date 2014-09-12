package com.lucianosimo.parachuteaction.object;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.extension.physics.box2d.util.constants.PhysicsConstants;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public class LeftHelicopter extends AnimatedSprite{

	private Body body;
	private FixtureDef fixture;
	
	public LeftHelicopter(float pX, float pY, VertexBufferObjectManager vbom, Camera camera, PhysicsWorld physicsWorld, ITiledTextureRegion region) {
		super(pX, pY, region, vbom);
		startAnimation();
		createPhysics(camera, physicsWorld);
	}
		
	private void createPhysics(final Camera camera, PhysicsWorld physicsWorld) {
		final float height = 201 / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT;
		final float width = 324 / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT;
		final Vector2[] v = {
			new Vector2(-0.17791f*width, -0.30348f*height),
			new Vector2(+0.19018f*width, -0.50746f*height),
			new Vector2(+0.40798f*width, -0.28358f*height),
			new Vector2(+0.50920f*width, +0.11443f*height),
			new Vector2(-0.44172f*width, +0.52736f*height),
			new Vector2(-0.52147f*width, +0.40796f*height),
		};
		fixture = PhysicsFactory.createFixtureDef(0, 0, 0);
		fixture.filter.groupIndex = -1;
		body = PhysicsFactory.createPolygonBody(physicsWorld, this, v, BodyType.KinematicBody, fixture);
		body.setFixedRotation(true);
		body.setUserData("leftHelicopter");
		physicsWorld.registerPhysicsConnector(new PhysicsConnector(this, body, true, false));
	}
	
	public void startAnimation() {
		final long[] HELICOPTER_ANIMATE = new long[] {100, 100};
		animate(HELICOPTER_ANIMATE, 0, 1, true);
	}
	
	public void startMoving() {
		body.setLinearVelocity(new Vector2(6f, body.getLinearVelocity().y));
	}
	
}

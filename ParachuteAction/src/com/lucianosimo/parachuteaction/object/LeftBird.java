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

public class LeftBird extends AnimatedSprite{

	private Body body;
	private FixtureDef fixture;
	
	public LeftBird(float pX, float pY, VertexBufferObjectManager vbom, Camera camera, PhysicsWorld physicsWorld, ITiledTextureRegion region) {
		super(pX, pY, region, vbom);
		startAnimation();
		createPhysics(camera, physicsWorld);
	}
	
	private void createPhysics(final Camera camera, PhysicsWorld physicsWorld) {
		final float height = 86 / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT;
		final float width = 110 / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT;
		final Vector2[] v = {
			new Vector2(-0.31818f*width, -0.27907f*height),
			new Vector2(-0.01818f*width, -0.52326f*height),
			new Vector2(+0.30909f*width, -0.26744f*height),
			new Vector2(+0.52727f*width, +0.02326f*height),
			new Vector2(+0.27273f*width, +0.51163f*height),
			new Vector2(-0.29091f*width, +0.51163f*height),
			new Vector2(-0.56364f*width, +0.01163f*height),
		};
		fixture = PhysicsFactory.createFixtureDef(0, 0, 0);
		fixture.filter.groupIndex = -1;
		body = PhysicsFactory.createPolygonBody(physicsWorld, this, v, BodyType.KinematicBody, fixture);
		body.setFixedRotation(true);
		body.setUserData("leftBird");
		physicsWorld.registerPhysicsConnector(new PhysicsConnector(this, body, true, false));
	}
	
	public void startAnimation() {
		final long[] BIRD_ANIMATE = new long[] {100, 100};
		animate(BIRD_ANIMATE, 0, 1, true);
	}
	
	public void startMoving() {
		body.setLinearVelocity(new Vector2(10, 4));
	}
	
}

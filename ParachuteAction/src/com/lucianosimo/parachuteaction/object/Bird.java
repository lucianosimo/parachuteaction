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

public class Bird extends AnimatedSprite{

	private Body body;
	private FixtureDef fixture;
	
	private boolean isRightBird;
	
	private final static float SPEED_X = 10f;
	private final static float SPEED_Y = 4f;
	
	private final static int BIRD_WIDTH = 86;
	private final static int BIRD_HEIGHT = 110;
	
	private final float width = BIRD_WIDTH / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT;
	private final float height = BIRD_HEIGHT / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT;
	
	private final Vector2[] vectorRight = {
		new Vector2(-0.31818f*width, -0.27907f*height),
		new Vector2(+0.01818f*width, -0.51163f*height),
		new Vector2(+0.48182f*width, -0.16279f*height),
		new Vector2(+0.51818f*width, +0.18605f*height),
		new Vector2(-0.00909f*width, +0.52326f*height),
		new Vector2(-0.29091f*width, +0.51163f*height),
		new Vector2(-0.52727f*width, +0.02326f*height),
	};
	
	private final Vector2[] vectorLeft = {
		new Vector2(-0.31818f*width, -0.27907f*height),
		new Vector2(-0.01818f*width, -0.52326f*height),
		new Vector2(+0.30909f*width, -0.26744f*height),
		new Vector2(+0.52727f*width, +0.02326f*height),
		new Vector2(+0.27273f*width, +0.51163f*height),
		new Vector2(-0.29091f*width, +0.51163f*height),
		new Vector2(-0.56364f*width, +0.01163f*height),
	};
	
	public Bird(float pX, float pY, VertexBufferObjectManager vbom, Camera camera, PhysicsWorld physicsWorld, ITiledTextureRegion region, boolean isRight) {
		super(pX, pY, region, vbom);
		startAnimation();
		createPhysics(camera, physicsWorld);
		isRightBird = isRight;
	}
	
	private void createPhysics(final Camera camera, PhysicsWorld physicsWorld) {
		final Vector2[] localVector = isRightBird ? vectorRight : vectorLeft;
		fixture = PhysicsFactory.createFixtureDef(0, 0, 0);
		fixture.filter.groupIndex = -1;
		body = PhysicsFactory.createPolygonBody(physicsWorld, this, localVector, BodyType.KinematicBody, fixture);
		body.setFixedRotation(true);
		body.setUserData("bird");
		physicsWorld.registerPhysicsConnector(new PhysicsConnector(this, body, true, false));
	}
	
	public Body getBody() {
		return body;
	}
	
	public void startAnimation() {
		final long[] BIRD_ANIMATE = new long[] {100, 100};
		animate(BIRD_ANIMATE, 0, 1, true);
	}
	
	public void startMoving() {
		float speedX = isRightBird ? -SPEED_X : SPEED_X;
		body.setLinearVelocity(new Vector2(speedX, SPEED_Y));
	}
	
	public void stopMoving() {
		body.setLinearVelocity(new Vector2(0, 0));
	}
	
	public boolean isRightBird() {
		return isRightBird;
	}
	
}

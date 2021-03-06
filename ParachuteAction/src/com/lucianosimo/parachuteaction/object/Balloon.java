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

public class Balloon extends Sprite{

	private Body body;
	private FixtureDef fixture;
	
	private static final int SPEED = 11;
	private static final float BALLOON_WIDTH = 200;
	private static final float BALLOON_HEIGHT = 206;
	
	public Balloon(float pX, float pY, VertexBufferObjectManager vbom, Camera camera, PhysicsWorld physicsWorld, ITextureRegion region) {
		super(pX, pY, region, vbom);
		createPhysics(camera, physicsWorld, region, vbom);
	}
	
	private void createPhysics(final Camera camera, PhysicsWorld physicsWorld, ITextureRegion region, VertexBufferObjectManager vbom) {
		final float width = BALLOON_WIDTH / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT;
		final float height = BALLOON_HEIGHT / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT;
		final Vector2[] vector = {
			new Vector2(-0.54500f*width, +0.01942f*height),
			new Vector2(-0.25000f*width, -0.49515f*height),
			new Vector2(+0.20000f*width, -0.50000f*height),
			new Vector2(+0.54000f*width, +0.00485f*height),
			new Vector2(+0.40000f*width, +0.40777f*height),
			new Vector2(-0.00500f*width, +0.53398f*height),
			new Vector2(-0.43000f*width, +0.41262f*height),
		};
		fixture = PhysicsFactory.createFixtureDef(0, 0, 0);
		fixture.filter.groupIndex = -1;
		body = PhysicsFactory.createPolygonBody(physicsWorld, this, vector, BodyType.KinematicBody, fixture);
		body.setFixedRotation(true);
		body.setUserData("balloon");
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

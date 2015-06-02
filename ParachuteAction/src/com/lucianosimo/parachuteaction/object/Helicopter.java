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

public class Helicopter extends AnimatedSprite{

	private Body body;
	private FixtureDef fixture;
	
	public Helicopter(float pX, float pY, VertexBufferObjectManager vbom, Camera camera, PhysicsWorld physicsWorld, ITiledTextureRegion region) {
		super(pX, pY, region, vbom);
		startAnimation();
		createPhysics(camera, physicsWorld);
	}
	
	private void createPhysics(final Camera camera, PhysicsWorld physicsWorld) {
		//final float width = pShape.getWidthScaled() / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT;
		//final float height = pShape.getHeightScaled() / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT;
		final float height = 201 / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT;
		final float width = 324 / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT;
		final Vector2[] v = {
			new Vector2(-0.41975f*width, -0.27363f*height),
			new Vector2(-0.19753f*width, -0.51244f*height),
			new Vector2(+0.17284f*width, -0.30846f*height),
			new Vector2(+0.51235f*width, +0.40796f*height),
			new Vector2(+0.44444f*width, +0.52239f*height),
			new Vector2(-0.50926f*width, +0.12438f*height),
		};
		fixture = PhysicsFactory.createFixtureDef(0, 0, 0);
		fixture.filter.groupIndex = -1;
		body = PhysicsFactory.createPolygonBody(physicsWorld, this, v, BodyType.KinematicBody, fixture);
		body.setFixedRotation(true);
		body.setUserData("helicopter");
		physicsWorld.registerPhysicsConnector(new PhysicsConnector(this, body, true, false));
	}
	
	public Body getBody() {
		return body;
	}
	
	public void startAnimation() {
		final long[] HELICOPTER_ANIMATE = new long[] {100, 100};
		animate(HELICOPTER_ANIMATE, 0, 1, true);
	}
	
	public void startMoving() {
		body.setLinearVelocity(new Vector2(-6f, body.getLinearVelocity().y));
	}
	
	public void stopMoving() {
		body.setLinearVelocity(new Vector2(0, 0));
	}
	
}

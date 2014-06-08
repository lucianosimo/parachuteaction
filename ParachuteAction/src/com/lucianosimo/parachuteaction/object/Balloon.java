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

public class Balloon extends Sprite{

	private Body body;
	
	public Balloon(float pX, float pY, VertexBufferObjectManager vbom, Camera camera, PhysicsWorld physicsWorld, ITextureRegion region) {
		super(pX, pY, region, vbom);
		createPhysics(camera, physicsWorld, region, vbom);
	}
	
	private void createPhysics(final Camera camera, PhysicsWorld physicsWorld, ITextureRegion region, VertexBufferObjectManager vbom) {
		body = PhysicsFactory.createCircleBody(physicsWorld, this, BodyType.DynamicBody, PhysicsFactory.createFixtureDef(0, 0, 0));
		body.setFixedRotation(true);
		body.setUserData("balloon");
		physicsWorld.registerPhysicsConnector(new PhysicsConnector(this, body, true, false));
	}
	
	public void startMoving() {
		body.setLinearVelocity(new Vector2(body.getLinearVelocity().x, 4));
	}
	
}

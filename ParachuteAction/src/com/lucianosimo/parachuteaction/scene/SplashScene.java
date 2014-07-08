package com.lucianosimo.parachuteaction.scene;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.util.GLState;
import org.andengine.util.adt.color.Color;

import com.lucianosimo.parachuteaction.base.BaseScene;
import com.lucianosimo.parachuteaction.manager.SceneManager.SceneType;

public class SplashScene extends BaseScene{

	private Sprite splash;
	
	@Override
	public void createScene() {
		setBackground(new Background(Color.WHITE));
		splash = new Sprite(0, 0, resourcesManager.splash_region, vbom) {
			@Override
			protected void preDraw(GLState pGLState, Camera pCamera) {
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}
		};
		float screenWidth = resourcesManager.camera.getWidth();
		float screenHeight = resourcesManager.camera.getHeight();
		splash.setPosition(screenWidth/2, screenHeight/2);
		final Rectangle fade = new Rectangle(screenWidth/2, screenHeight/2, 480, 854, vbom);
		fade.setColor(Color.BLACK);
		fade.setAlpha(1.0f);
		attachChild(splash);
		attachChild(fade);
		fade.registerEntityModifier(new AlphaModifier(1.5f, 1.0f, 0.0f));
		fade.setAlpha(0.0f);
		engine.registerUpdateHandler(new IUpdateHandler() {
			private int updates = 0;
			
			@Override
			public void reset() {

			}
			
			@Override
			public void onUpdate(float pSecondsElapsed) {
				updates++;
				if (updates > 150) {
					fade.registerEntityModifier(new AlphaModifier(1.5f, 0.0f, 1.0f));
				}
			}
		});
	}

	@Override
	public void onBackKeyPressed() {
	}

	@Override
	public SceneType getSceneType() {
		return SceneType.SCENE_SPLASH;
	}

	@Override
	public void disposeScene() {
		splash.detachSelf();
		splash.dispose();
		this.detachSelf();
		this.dispose();
	}

}
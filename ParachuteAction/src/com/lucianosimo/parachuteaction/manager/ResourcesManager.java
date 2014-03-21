package com.lucianosimo.parachuteaction.manager;

import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.Engine;
import org.andengine.engine.camera.BoundCamera;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.color.Color;
import org.andengine.util.debug.Debug;

import com.lucianosimo.parachuteaction.GameActivity;

public class ResourcesManager {

	private static final ResourcesManager INSTANCE = new ResourcesManager();
	
	public Engine engine;
	public BoundCamera camera;
	public GameActivity activity;
	public VertexBufferObjectManager vbom;
	
	//Splash items
	public ITextureRegion splash_region;
	private BitmapTextureAtlas splashTextureAtlas;
	
	//Menu items
	public ITextureRegion menu_background_region;
	public ITextureRegion play_region;
	private BuildableBitmapTextureAtlas menuTextureAtlas;
	private BuildableBitmapTextureAtlas backgroundMenuTextureAtlas;
	
	//Menu y loading fonts 
	public Font loadingFont;
	
	//Game fonts
	public Font meterCounterFont;
	public Font altimeterFont;
	public Font levelCompletedFont;
	public Font gameOverFont;
	
	//Game items
	
	//Platforms
	public ITextureRegion landing_platfom_region;
	
	//Decoration

	//Backgrounds
	public ITextureRegion background_region;

	//Objects
	public ITextureRegion cloud_region;
	public ITextureRegion upperImpulse_region;
	public ITextureRegion antiGravity_region;
	
	//Shield
	public ITextureRegion shield_region;
	public ITextureRegion shieldHalo_region;

	//Animated
	public ITiledTextureRegion player_region;
	public ITiledTextureRegion helicopter_region;
	
	//Game Textures
	private BuildableBitmapTextureAtlas animatedTextureAtlas;
	private BuildableBitmapTextureAtlas backgroundTextureAtlas;
	private BuildableBitmapTextureAtlas objectsTextureAtlas;
	
	//Splash Methods
	public void loadSplashScreen() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		splashTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 480, 854, TextureOptions.BILINEAR);
		splash_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(splashTextureAtlas, activity, "splash.png", 0, 0);
		splashTextureAtlas.load();
	}
	
	public void unloadSplashScreen() {
		splashTextureAtlas.unload();
		splash_region = null;
	}
	
	//Menu methods
	public void loadMenuResources() {
		loadMenuGraphics();
		loadMenuAudio();
		loadMenuFonts();
	}

	private void loadMenuGraphics() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/menu/");
		menuTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 250, 250, TextureOptions.BILINEAR);
		backgroundMenuTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 480, 854, TextureOptions.BILINEAR);
		menu_background_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(backgroundMenuTextureAtlas, activity, "menu_background.png");
		play_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "play.png");
		try {
			this.menuTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
			this.backgroundMenuTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
			this.menuTextureAtlas.load();
			this.backgroundMenuTextureAtlas.load();
		} catch (final TextureAtlasBuilderException e) {
			org.andengine.util.debug.Debug.e(e);
		}
	}
	
	private void loadMenuAudio() {
	}
	
	private void loadMenuFonts() {
		FontFactory.setAssetBasePath("font/menu/");
		final ITexture loadingTexture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		loadingFont = FontFactory.createStrokeFromAsset(activity.getFontManager(), loadingTexture, activity.getAssets(), "inky.ttf", 30, true, Color.BLACK_ARGB_PACKED_INT, 0.1f, Color.BLACK_ARGB_PACKED_INT);
		loadingFont.load();
	}
	
	public void unloadMenuTextures() {
		this.menuTextureAtlas.unload();
		this.backgroundMenuTextureAtlas.unload();
	}
	
	/*public void loadMenuTextures() {
		
	}*/
	
	public void unloadMenuAudio() {
		
	}
	
	//Game Methods
	public void loadGameResources() {
		loadGameGraphics();
		loadGameAudio();
		loadGameFonts();
	}
	
	private void loadGameGraphics() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/game/");
		
		animatedTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1000, 1000, TextureOptions.BILINEAR);
		backgroundTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 480, 854, TextureOptions.BILINEAR);
		objectsTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1000, 1000, TextureOptions.BILINEAR);
		
		background_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(backgroundTextureAtlas, activity, "background.png");
		cloud_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(objectsTextureAtlas, activity, "cloud.png");
		upperImpulse_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(objectsTextureAtlas, activity, "upperImpulse.png");
		antiGravity_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(objectsTextureAtlas, activity, "antiGravity.png");
		landing_platfom_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(objectsTextureAtlas, activity, "landing_platform.png");
		shield_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(objectsTextureAtlas, activity, "shield.png");
		shieldHalo_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(objectsTextureAtlas, activity, "shieldHalo.png");
		
		player_region = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(animatedTextureAtlas, activity, "player.png", 2, 1);
		helicopter_region = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(animatedTextureAtlas, activity, "helicopter.png", 2, 1);
		
		try {
			this.animatedTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
			this.backgroundTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
			this.objectsTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
			this.animatedTextureAtlas.load();
			this.backgroundTextureAtlas.load();
			this.objectsTextureAtlas.load();
		} catch (final TextureAtlasBuilderException e) {
			Debug.e(e);
		}
	}

	private void loadGameAudio() {
		MusicFactory.setAssetBasePath("music/game/");
		SoundFactory.setAssetBasePath("sound/game/");
	}
	
	public void unloadGameAudio() {
		
	}
	
	private void loadGameFonts() {
		FontFactory.setAssetBasePath("font/game/");
		final ITexture meterCounterTexture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		final ITexture altimeterTexture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		final ITexture levelCompletedTexture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		final ITexture gameOverTexture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		meterCounterFont = FontFactory.createStrokeFromAsset(activity.getFontManager(), meterCounterTexture, activity.getAssets(), "inky.ttf", 23, true, Color.BLACK_ARGB_PACKED_INT, 0.1f, Color.BLACK_ARGB_PACKED_INT);
		altimeterFont = FontFactory.createStrokeFromAsset(activity.getFontManager(), altimeterTexture, activity.getAssets(), "inky.ttf", 23, true, Color.BLACK_ARGB_PACKED_INT, 0.1f, Color.BLACK_ARGB_PACKED_INT);
		levelCompletedFont = FontFactory.createStrokeFromAsset(activity.getFontManager(), levelCompletedTexture, activity.getAssets(), "inky.ttf", 25, true, Color.BLACK_ARGB_PACKED_INT, 0.1f, Color.BLACK_ARGB_PACKED_INT);
		gameOverFont = FontFactory.createStrokeFromAsset(activity.getFontManager(), gameOverTexture, activity.getAssets(), "inky.ttf", 35, true, Color.BLACK_ARGB_PACKED_INT, 0.1f, Color.BLACK_ARGB_PACKED_INT);
		meterCounterFont.load();
		altimeterFont.load();
		levelCompletedFont.load();
		gameOverFont.load();
	}
	
	public void unloadGameTextures() {
		this.animatedTextureAtlas.unload();
		this.backgroundTextureAtlas.unload();
		this.objectsTextureAtlas.unload();
	}
	
	//Manager Methods
	public static void prepareManager(Engine engine, GameActivity activity, BoundCamera camera, VertexBufferObjectManager vbom) {
		getInstance().engine = engine;
		getInstance().activity = activity;
		getInstance().camera = camera;
		getInstance().vbom = vbom;		
	}
	
	public static ResourcesManager getInstance() {
		return INSTANCE;
	}
	


}


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
	
	//Game fonts
	public Font scoreFont;
	public Font altimeterFont;
	
	//Game items
	
	//Platforms
	public ITextureRegion landing_platfom_region;
	
	//Decoration

	//Backgrounds
	public ITextureRegion background_region;

	//Objects
	public ITextureRegion cloud_region;
	public ITextureRegion coin_region;

	//Animated
	public ITiledTextureRegion player_region;
	
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
		menuTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
		menu_background_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "menu_background.png");
		play_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "play.png");
		try {
			this.menuTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
			this.menuTextureAtlas.load();
		} catch (final TextureAtlasBuilderException e) {
			org.andengine.util.debug.Debug.e(e);
		}
	}
	
	private void loadMenuAudio() {
	}
	
	private void loadMenuFonts() {
		FontFactory.setAssetBasePath("font/");
	}
	
	public void unloadMenuTextures() {
		menuTextureAtlas.unload();
	}
	
	public void loadMenuTextures() {
		
	}
	
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
		
		animatedTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 400, 400, TextureOptions.BILINEAR);
		backgroundTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 480, 854, TextureOptions.BILINEAR);
		objectsTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 250, 250, TextureOptions.BILINEAR);
		
		background_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(backgroundTextureAtlas, activity, "background.png");
		cloud_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(objectsTextureAtlas, activity, "cloud.png");
		coin_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(objectsTextureAtlas, activity, "coin.png");
		landing_platfom_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(objectsTextureAtlas, activity, "landing_platform.png");
		
		player_region = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(animatedTextureAtlas, activity, "player.png", 2, 1);
		
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
		final ITexture scoreTexture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		final ITexture altimeterTexture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		scoreFont = FontFactory.createStrokeFromAsset(activity.getFontManager(), scoreTexture, activity.getAssets(), "inky.ttf", 30, true, Color.BLACK_ARGB_PACKED_INT, 0.1f, Color.BLACK_ARGB_PACKED_INT);
		altimeterFont = FontFactory.createStrokeFromAsset(activity.getFontManager(), altimeterTexture, activity.getAssets(), "inky.ttf", 30, true, Color.BLACK_ARGB_PACKED_INT, 0.1f, Color.BLACK_ARGB_PACKED_INT);
		scoreFont.load();
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


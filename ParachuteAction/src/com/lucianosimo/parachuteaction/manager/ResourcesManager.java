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
	public ITextureRegion statistics_region;
	
	private BuildableBitmapTextureAtlas menuTextureAtlas;
	private BuildableBitmapTextureAtlas backgroundMenuTextureAtlas;

	//Menu y loading fonts 
	public Font loadingFont;
	
	//Statistics items
	public ITextureRegion statistics_background_region;
	public ITextureRegion menu_from_statistics_region;
	public ITextureRegion achivements_region;
	public ITextureRegion resetStatistics_region;	
	private BuildableBitmapTextureAtlas statisticsTextureAtlas;
	private BuildableBitmapTextureAtlas backgroundStatisticsTextureAtlas;
	
	//Statistics fonts
	public Font numberOfSuccessfulJumpsFont;
	public Font numberOfUnsuccessfulJumpsFont;
	public Font maxFliedMetersFont;
	public Font freeFliedMetersFont;
	public Font parachuteFliedMetersFont;
	public Font upperImpulseCounterFont;
	public Font antigravityCounterFont;
	public Font shieldCounterFont;
	public Font slowCounterFont;
	
	//Achievements items
	public ITextureRegion statistics_from_achievements_region;
	public ITextureRegion menu_from_achievements_button;
	public ITextureRegion achievements_background_region;	
	public ITextureRegion one_button;
	public ITextureRegion two_button;
	public ITextureRegion three_button;
	public ITextureRegion firstJumpAward;
	public ITextureRegion jumps10award;
	public ITextureRegion jumps50award;
	public ITextureRegion jumps100award;
	public ITextureRegion jumps500award;
	public ITextureRegion failJumps5award;
	public ITextureRegion failJumps10award;
	public ITextureRegion failJumps25award;
	public ITextureRegion freeFly5000award;
	public ITextureRegion freeFly15000award;
	public ITextureRegion freeFly50000award;
	public ITextureRegion freeFly100000award;
	public ITextureRegion parachuteFly2500award;
	public ITextureRegion parachuteFly15000award;
	public ITextureRegion parachuteFly50000award;
	public ITextureRegion ui10award;
	public ITextureRegion ui50award;
	public ITextureRegion ui100award;
	public ITextureRegion antigravity10award;
	public ITextureRegion antigravity50award;
	public ITextureRegion antigravity100award;
	public ITextureRegion shield10award;
	public ITextureRegion shield50award;
	public ITextureRegion shield100award;
	public ITextureRegion slow10award;
	public ITextureRegion slow50award;
	public ITextureRegion slow100award;
	public ITextureRegion destroy5helicoptersAward;
	public ITextureRegion destroy25helicoptersAward;
	public ITextureRegion destroy100helicoptersAward;
	public ITextureRegion destroy5balloonsAward;
	public ITextureRegion destroy25balloonsAward;
	public ITextureRegion destroy100balloonsAward;
	public ITextureRegion destroy5birdsAward;
	public ITextureRegion destroy25birdsAward;
	public ITextureRegion destroy100birdsAward;
	public ITextureRegion lockedAward;
	
	private BuildableBitmapTextureAtlas achivementsTextureAtlas;
	private BuildableBitmapTextureAtlas achivementsBackgroundTextureAtlas;
	
	//Game fonts
	public Font meterCounterFont;
	public Font altimeterFont;
	public Font levelCompletedFont;
	public Font gameOverFont;
	public Font maxSpeedFont;
	public Font achievementsUnlockedFont;
	
	//Game items
	
	//Game HUD
	public ITextureRegion openButton;
	
	//Platforms
	public ITextureRegion landing_platfom_region;

	//Backgrounds
	public ITextureRegion background_region;
	
	//Signs
	public ITextureRegion upperImpulseSign_region;
	public ITextureRegion antigravitySign_region;
	public ITextureRegion shieldSign_region;
	public ITextureRegion green_arrow_region;
	public ITextureRegion red_arrow_region;

	//Objects
	public ITextureRegion cloud_region;
	public ITextureRegion upperImpulse_region;
	public ITextureRegion antiGravity_region;
	public ITextureRegion slow_region;
	
	//Shield
	public ITextureRegion shield_region;
	public ITextureRegion shieldHalo_region;

	//Animated
	public ITiledTextureRegion player_region;
	public ITiledTextureRegion helicopter_region;
	public ITiledTextureRegion balloon_region;
	public ITiledTextureRegion bird_region;
	
	//Windows
	public ITextureRegion level_complete_window_region;
	public ITextureRegion pause_window_region;
	public ITextureRegion game_over_window_region;
	
	public ITextureRegion quit_button_region;
	public ITextureRegion fly_again_button_region;
	public ITextureRegion resume_button_region;
	public ITextureRegion retry_button_region;
	
	//Game Textures
	private BuildableBitmapTextureAtlas animatedTextureAtlas;
	private BuildableBitmapTextureAtlas backgroundTextureAtlas;
	private BuildableBitmapTextureAtlas objectsTextureAtlas;
	private BuildableBitmapTextureAtlas windowsTextureAtlas;
	
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
	
	public void unloadMenuResources() {
		unloadMenuTextures();
		unloadMenuFonts();
		unloadMenuAudio();
	}

	private void loadMenuGraphics() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/menu/");
		menuTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 500, 500, TextureOptions.BILINEAR);
		backgroundMenuTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 480, 854, TextureOptions.BILINEAR);
		
		menu_background_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(backgroundMenuTextureAtlas, activity, "menu_background.png");
		play_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "play.png");
		statistics_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "statistics.png");
		
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
	
	private void unloadMenuTextures() {
		this.menuTextureAtlas.unload();
		this.backgroundMenuTextureAtlas.unload();
	}
	
	private void unloadMenuFonts() {
		
	}
	
	private void unloadMenuAudio() {
		
	}
	
	//Game Methods
	public void loadGameResources() {
		loadGameGraphics();
		loadGameAudio();
		loadGameFonts();
	}
	
	public void unloadGameResources() {
		unloadGameTextures();
		unloadGameFonts();	
		unloadGameAudio();
	}
	
	private void loadGameGraphics() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/game/");
		
		animatedTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1500, 1500, TextureOptions.BILINEAR);
		backgroundTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 480, 854, TextureOptions.BILINEAR);
		objectsTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1500, 1500, TextureOptions.BILINEAR);
		windowsTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1000, 1000, TextureOptions.BILINEAR);
		
		//Background texture objects
		background_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(backgroundTextureAtlas, activity, "background.png");
		
		//Objects texture objects
		cloud_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(objectsTextureAtlas, activity, "cloud.png");
		upperImpulse_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(objectsTextureAtlas, activity, "upperImpulse.png");
		antiGravity_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(objectsTextureAtlas, activity, "antigravity.png");
		slow_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(objectsTextureAtlas, activity, "slow.png");
		landing_platfom_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(objectsTextureAtlas, activity, "landing_platform.png");
		shield_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(objectsTextureAtlas, activity, "shield.png");
		shieldHalo_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(objectsTextureAtlas, activity, "shieldHalo.png");
		openButton = BitmapTextureAtlasTextureRegionFactory.createFromAsset(objectsTextureAtlas, activity, "openButton.png");
		antigravitySign_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(objectsTextureAtlas, activity, "antigravitySign.png");
		shieldSign_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(objectsTextureAtlas, activity, "shieldSign.png");
		upperImpulseSign_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(objectsTextureAtlas, activity, "upperImpulseSign.png");
		red_arrow_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(objectsTextureAtlas, activity, "redArrow.png");
		green_arrow_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(objectsTextureAtlas, activity, "greenArrow.png");
		
		//Windows texture objects
		level_complete_window_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(windowsTextureAtlas, activity, "level_complete_window.png");
		pause_window_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(windowsTextureAtlas, activity, "pause_window.png");
		game_over_window_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(windowsTextureAtlas, activity, "game_over_window.png");
		quit_button_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(windowsTextureAtlas, activity, "quit_button.png");
		fly_again_button_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(windowsTextureAtlas, activity, "fly_again_button.png");
		resume_button_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(windowsTextureAtlas, activity, "resume_button.png");
		retry_button_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(windowsTextureAtlas, activity, "retry_button.png");
		
		//Animated texture objects
		player_region = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(animatedTextureAtlas, activity, "player.png", 2, 1);
		helicopter_region = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(animatedTextureAtlas, activity, "helicopter.png", 2, 1);
		balloon_region = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(animatedTextureAtlas, activity, "balloon.png", 2, 1);
		bird_region = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(animatedTextureAtlas, activity, "bird.png", 2, 1);
		
		try {
			this.animatedTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
			this.backgroundTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
			this.objectsTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
			this.windowsTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
			this.animatedTextureAtlas.load();
			this.backgroundTextureAtlas.load();
			this.objectsTextureAtlas.load();
			this.windowsTextureAtlas.load();
		} catch (final TextureAtlasBuilderException e) {
			Debug.e(e);
		}
	}

	private void loadGameAudio() {
		MusicFactory.setAssetBasePath("music/game/");
		SoundFactory.setAssetBasePath("sound/game/");
	}
	
	private void unloadGameAudio() {
		
	}
	
	private void loadGameFonts() {
		FontFactory.setAssetBasePath("font/game/");
		final ITexture meterCounterTexture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		final ITexture altimeterTexture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		final ITexture levelCompletedTexture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		final ITexture gameOverTexture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		final ITexture maxSpeedTexture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		final ITexture achievementsUnlockedTexture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		meterCounterFont = FontFactory.createStrokeFromAsset(activity.getFontManager(), meterCounterTexture, activity.getAssets(), "inky.ttf", 23, true, Color.BLACK_ARGB_PACKED_INT, 0.1f, Color.BLACK_ARGB_PACKED_INT);
		altimeterFont = FontFactory.createStrokeFromAsset(activity.getFontManager(), altimeterTexture, activity.getAssets(), "inky.ttf", 23, true, Color.BLACK_ARGB_PACKED_INT, 0.1f, Color.BLACK_ARGB_PACKED_INT);
		levelCompletedFont = FontFactory.createStrokeFromAsset(activity.getFontManager(), levelCompletedTexture, activity.getAssets(), "inky.ttf", 25, true, Color.BLACK_ARGB_PACKED_INT, 0.1f, Color.BLACK_ARGB_PACKED_INT);
		maxSpeedFont = FontFactory.createStrokeFromAsset(activity.getFontManager(), maxSpeedTexture, activity.getAssets(), "inky.ttf", 25, true, Color.BLACK_ARGB_PACKED_INT, 0.1f, Color.BLACK_ARGB_PACKED_INT);
		gameOverFont = FontFactory.createStrokeFromAsset(activity.getFontManager(), gameOverTexture, activity.getAssets(), "inky.ttf", 35, true, Color.BLACK_ARGB_PACKED_INT, 0.1f, Color.BLACK_ARGB_PACKED_INT);
		achievementsUnlockedFont = FontFactory.createStrokeFromAsset(activity.getFontManager(), achievementsUnlockedTexture, activity.getAssets(), "inky.ttf", 35, true, Color.BLACK_ARGB_PACKED_INT, 0.1f, Color.BLACK_ARGB_PACKED_INT); 
		maxSpeedFont.load();
		meterCounterFont.load();
		altimeterFont.load();
		levelCompletedFont.load();
		gameOverFont.load();
		achievementsUnlockedFont.load();
	}
	
	private void unloadGameTextures() {
		this.animatedTextureAtlas.unload();
		this.backgroundTextureAtlas.unload();
		this.objectsTextureAtlas.unload();
		this.windowsTextureAtlas.unload();
	}
	
	private void unloadGameFonts() {
		maxSpeedFont.unload();
		meterCounterFont.unload();
		altimeterFont.unload();
		levelCompletedFont.unload();
		gameOverFont.unload();
		achievementsUnlockedFont.unload();
	}
	
	//Statistics methods
	public void loadStatisticsResources() {
		loadStatisticsGraphics();
		loadStatisticsAudio();
		loadStatisticsFonts();
	}
	
	public void unloadStatisticsResources() {
		unloadStatisticsTextures();
		unloadStatisticsFonts();		
	}
	
	private void loadStatisticsGraphics() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/statistics/");
		backgroundStatisticsTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 480, 854, TextureOptions.BILINEAR);
		statisticsTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 500, 500, TextureOptions.BILINEAR);
		
		statistics_background_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(backgroundStatisticsTextureAtlas, activity, "statistics_background.png");
		menu_from_statistics_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(statisticsTextureAtlas, activity, "menu_button.png");
		achivements_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(statisticsTextureAtlas, activity, "achivements_button.png");
		resetStatistics_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(statisticsTextureAtlas, activity, "reset_statistics.png");
		
		try {
			this.statisticsTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
			this.backgroundStatisticsTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
			this.statisticsTextureAtlas.load();
			this.backgroundStatisticsTextureAtlas.load();
		} catch (final TextureAtlasBuilderException e) {
			org.andengine.util.debug.Debug.e(e);
		}
	}
	
	private void loadStatisticsAudio() {
		
	}
	
	private void loadStatisticsFonts() {
		FontFactory.setAssetBasePath("font/statistics/");
		final ITexture maxFliedMetersTexture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		final ITexture numberOfSuccessfulJumpsTexture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		final ITexture numberOfUnsuccessfulJumpsTexture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		final ITexture freeFliedMetersTexture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		final ITexture parachuteFliedMetersTexture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		final ITexture upperImpulseCounterTexture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		final ITexture antigravityCounterTexture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		final ITexture shieldCounterTexture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		final ITexture slowCounterTexture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		numberOfSuccessfulJumpsFont = FontFactory.createStrokeFromAsset(activity.getFontManager(), numberOfSuccessfulJumpsTexture, activity.getAssets(), "inky.ttf", 30, true, Color.BLACK_ARGB_PACKED_INT, 0.1f, Color.BLACK_ARGB_PACKED_INT);
		numberOfUnsuccessfulJumpsFont = FontFactory.createStrokeFromAsset(activity.getFontManager(), numberOfUnsuccessfulJumpsTexture, activity.getAssets(), "inky.ttf", 30, true, Color.BLACK_ARGB_PACKED_INT, 0.1f, Color.BLACK_ARGB_PACKED_INT);
		maxFliedMetersFont = FontFactory.createStrokeFromAsset(activity.getFontManager(), maxFliedMetersTexture, activity.getAssets(), "inky.ttf", 30, true, Color.BLACK_ARGB_PACKED_INT, 0.1f, Color.BLACK_ARGB_PACKED_INT);
		freeFliedMetersFont  = FontFactory.createStrokeFromAsset(activity.getFontManager(), freeFliedMetersTexture, activity.getAssets(), "inky.ttf", 30, true, Color.BLACK_ARGB_PACKED_INT, 0.1f, Color.BLACK_ARGB_PACKED_INT);
		parachuteFliedMetersFont = FontFactory.createStrokeFromAsset(activity.getFontManager(), parachuteFliedMetersTexture, activity.getAssets(), "inky.ttf", 30, true, Color.BLACK_ARGB_PACKED_INT, 0.1f, Color.BLACK_ARGB_PACKED_INT);
		upperImpulseCounterFont = FontFactory.createStrokeFromAsset(activity.getFontManager(), upperImpulseCounterTexture, activity.getAssets(), "inky.ttf", 30, true, Color.BLACK_ARGB_PACKED_INT, 0.1f, Color.BLACK_ARGB_PACKED_INT); 
		antigravityCounterFont = FontFactory.createStrokeFromAsset(activity.getFontManager(), antigravityCounterTexture, activity.getAssets(), "inky.ttf", 30, true, Color.BLACK_ARGB_PACKED_INT, 0.1f, Color.BLACK_ARGB_PACKED_INT);
		shieldCounterFont = FontFactory.createStrokeFromAsset(activity.getFontManager(), shieldCounterTexture, activity.getAssets(), "inky.ttf", 30, true, Color.BLACK_ARGB_PACKED_INT, 0.1f, Color.BLACK_ARGB_PACKED_INT);
		slowCounterFont = FontFactory.createStrokeFromAsset(activity.getFontManager(), slowCounterTexture, activity.getAssets(), "inky.ttf", 30, true, Color.BLACK_ARGB_PACKED_INT, 0.1f, Color.BLACK_ARGB_PACKED_INT);
		maxFliedMetersFont.load();
		numberOfSuccessfulJumpsFont.load();
		numberOfUnsuccessfulJumpsFont.load();
		freeFliedMetersFont.load();
		parachuteFliedMetersFont.load();
		upperImpulseCounterFont.load();
		antigravityCounterFont.load();
		shieldCounterFont.load();
		slowCounterFont.load();
	}
	
	private void unloadStatisticsTextures() {
		this.statisticsTextureAtlas.unload();
		this.backgroundStatisticsTextureAtlas.unload();
	}
	
	private void unloadStatisticsFonts() {
		maxFliedMetersFont.unload();
		numberOfSuccessfulJumpsFont.unload();
		numberOfUnsuccessfulJumpsFont.unload();
		freeFliedMetersFont.unload();
		parachuteFliedMetersFont.unload();
		upperImpulseCounterFont.unload();
		antigravityCounterFont.unload();
		shieldCounterFont.unload();
		slowCounterFont.unload();
	}
	
	//Achievements methods
	public void loadAchievementsResources() {
		loadAchievementsGraphics();
		loadAchievementsAudio();
		loadAchievementsFonts();
	}
	
	public void unloadAchievementsResources() {
		unloadAchievementsTextures();
		unloadAchievementsFonts();		
	}
	
	private void loadAchievementsGraphics() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/achievements/");
		achivementsTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
		achivementsBackgroundTextureAtlas  = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 480, 854, TextureOptions.BILINEAR);
		
		achievements_background_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(achivementsBackgroundTextureAtlas, activity, "achievements_background.png");
		menu_from_achievements_button = BitmapTextureAtlasTextureRegionFactory.createFromAsset(achivementsTextureAtlas, activity, "menu_button.png");
		statistics_from_achievements_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(achivementsTextureAtlas, activity, "statistics_button.png");		
		one_button = BitmapTextureAtlasTextureRegionFactory.createFromAsset(achivementsTextureAtlas, activity, "1button.png");
		two_button = BitmapTextureAtlasTextureRegionFactory.createFromAsset(achivementsTextureAtlas, activity, "2button.png");
		three_button = BitmapTextureAtlasTextureRegionFactory.createFromAsset(achivementsTextureAtlas, activity, "3button.png");
		lockedAward = BitmapTextureAtlasTextureRegionFactory.createFromAsset(achivementsTextureAtlas, activity, "lockedAward.png");		
		firstJumpAward = BitmapTextureAtlasTextureRegionFactory.createFromAsset(achivementsTextureAtlas, activity, "firstJumpAward.png");
		jumps10award = BitmapTextureAtlasTextureRegionFactory.createFromAsset(achivementsTextureAtlas, activity, "jumps10award.png");
		jumps50award = BitmapTextureAtlasTextureRegionFactory.createFromAsset(achivementsTextureAtlas, activity, "jumps50award.png");
		jumps100award = BitmapTextureAtlasTextureRegionFactory.createFromAsset(achivementsTextureAtlas, activity, "jumps100award.png");
		jumps500award = BitmapTextureAtlasTextureRegionFactory.createFromAsset(achivementsTextureAtlas, activity, "jumps500award.png");
		failJumps5award = BitmapTextureAtlasTextureRegionFactory.createFromAsset(achivementsTextureAtlas, activity, "failJumps5award.png");
		failJumps10award = BitmapTextureAtlasTextureRegionFactory.createFromAsset(achivementsTextureAtlas, activity, "failJumps10award.png");
		failJumps25award = BitmapTextureAtlasTextureRegionFactory.createFromAsset(achivementsTextureAtlas, activity, "failJumps25award.png");
		freeFly5000award = BitmapTextureAtlasTextureRegionFactory.createFromAsset(achivementsTextureAtlas, activity, "freeFly5000award.png");
		freeFly15000award = BitmapTextureAtlasTextureRegionFactory.createFromAsset(achivementsTextureAtlas, activity, "freeFly15000award.png");
		freeFly50000award = BitmapTextureAtlasTextureRegionFactory.createFromAsset(achivementsTextureAtlas, activity, "freeFly50000award.png");
		freeFly100000award = BitmapTextureAtlasTextureRegionFactory.createFromAsset(achivementsTextureAtlas, activity, "freeFly100000award.png");
		parachuteFly2500award = BitmapTextureAtlasTextureRegionFactory.createFromAsset(achivementsTextureAtlas, activity, "parachuteFly2500award.png");
		parachuteFly15000award = BitmapTextureAtlasTextureRegionFactory.createFromAsset(achivementsTextureAtlas, activity, "parachuteFly15000award.png");
		parachuteFly50000award = BitmapTextureAtlasTextureRegionFactory.createFromAsset(achivementsTextureAtlas, activity, "parachuteFly50000award.png");
		ui10award = BitmapTextureAtlasTextureRegionFactory.createFromAsset(achivementsTextureAtlas, activity, "ui10award.png");
		ui50award = BitmapTextureAtlasTextureRegionFactory.createFromAsset(achivementsTextureAtlas, activity, "ui50award.png");
		ui100award = BitmapTextureAtlasTextureRegionFactory.createFromAsset(achivementsTextureAtlas, activity, "ui100award.png");
		antigravity10award = BitmapTextureAtlasTextureRegionFactory.createFromAsset(achivementsTextureAtlas, activity, "antigravity10award.png");
		antigravity50award = BitmapTextureAtlasTextureRegionFactory.createFromAsset(achivementsTextureAtlas, activity, "antigravity50award.png");
		antigravity100award = BitmapTextureAtlasTextureRegionFactory.createFromAsset(achivementsTextureAtlas, activity, "antigravity100award.png");
		shield10award = BitmapTextureAtlasTextureRegionFactory.createFromAsset(achivementsTextureAtlas, activity, "shield10award.png");
		shield50award = BitmapTextureAtlasTextureRegionFactory.createFromAsset(achivementsTextureAtlas, activity, "shield50award.png");
		shield100award = BitmapTextureAtlasTextureRegionFactory.createFromAsset(achivementsTextureAtlas, activity, "shield100award.png");
		slow10award = BitmapTextureAtlasTextureRegionFactory.createFromAsset(achivementsTextureAtlas, activity, "slow10award.png");
		slow50award = BitmapTextureAtlasTextureRegionFactory.createFromAsset(achivementsTextureAtlas, activity, "slow50award.png");
		slow100award = BitmapTextureAtlasTextureRegionFactory.createFromAsset(achivementsTextureAtlas, activity, "slow100award.png");
		destroy5helicoptersAward = BitmapTextureAtlasTextureRegionFactory.createFromAsset(achivementsTextureAtlas, activity, "destroy5helicoptersAward.png");
		destroy25helicoptersAward = BitmapTextureAtlasTextureRegionFactory.createFromAsset(achivementsTextureAtlas, activity, "destroy25helicoptersAward.png");
		destroy100helicoptersAward = BitmapTextureAtlasTextureRegionFactory.createFromAsset(achivementsTextureAtlas, activity, "destroy100helicoptersAward.png");
		destroy5balloonsAward = BitmapTextureAtlasTextureRegionFactory.createFromAsset(achivementsTextureAtlas, activity, "destroy5balloonsAward.png");
		destroy25balloonsAward = BitmapTextureAtlasTextureRegionFactory.createFromAsset(achivementsTextureAtlas, activity, "destroy25balloonsAward.png");
		destroy100balloonsAward = BitmapTextureAtlasTextureRegionFactory.createFromAsset(achivementsTextureAtlas, activity, "destroy100balloonsAward.png");
		destroy5birdsAward = BitmapTextureAtlasTextureRegionFactory.createFromAsset(achivementsTextureAtlas, activity, "destroy5birdsAward.png");
		destroy25birdsAward = BitmapTextureAtlasTextureRegionFactory.createFromAsset(achivementsTextureAtlas, activity, "destroy25birdsAward.png");
		destroy100birdsAward = BitmapTextureAtlasTextureRegionFactory.createFromAsset(achivementsTextureAtlas, activity, "destroy100birdsAward.png");
		try {
			this.achivementsTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
			this.achivementsBackgroundTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
			this.achivementsTextureAtlas.load();
			this.achivementsBackgroundTextureAtlas.load();
		} catch (final TextureAtlasBuilderException e) {
			org.andengine.util.debug.Debug.e(e);
		}
	}
	
	private void loadAchievementsAudio() {
		
	}
	
	private void loadAchievementsFonts() {
		
	}
	
	private void unloadAchievementsTextures() {
		this.achivementsTextureAtlas.unload();
		this.achivementsBackgroundTextureAtlas.unload();
	}
	
	private void unloadAchievementsFonts() {
		
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



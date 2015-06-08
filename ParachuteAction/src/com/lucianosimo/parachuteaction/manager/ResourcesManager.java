package com.lucianosimo.parachuteaction.manager;

import java.io.IOException;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.Sound;
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
import com.lucianosimo.parachuteaction.scene.MapScene;

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
	public ITextureRegion loading_background_region;
	
	public ITextureRegion menu_background_region;
	public ITextureRegion menu_play_button_region;
	public ITextureRegion menu_statistics_button_region;
	public ITextureRegion menu_shop_button_region;
	public ITextureRegion menu_rate_us_button_region;
	public ITextureRegion menu_cloud_region;
	public ITextureRegion menu_far_cloud_region;
	public ITextureRegion menu_title_region;
	
	private BuildableBitmapTextureAtlas menuTextureAtlas;
	private BuildableBitmapTextureAtlas backgroundMenuTextureAtlas;
	private BuildableBitmapTextureAtlas backgroundLoadingTextureAtlas;
	
	//Menu y loading fonts 
	public Font loadingFont;
	
	//Shop items
	public ITextureRegion shop_background_region;
	public ITextureRegion shop_menu_button_region;
	public ITextureRegion shop_desert_region;
	public ITextureRegion shop_mountain_region;
	public ITextureRegion shop_western_region;
	public ITextureRegion shop_locked_location_region;
	private BuildableBitmapTextureAtlas shopTextureAtlas;
	private BuildableBitmapTextureAtlas shopBackgroundTextureAtlas;
	
	//Shop fonts
	public Font shopCoinsFont;
	
	//Map items
	public ITextureRegion map_background_region;
	public ITextureRegion map_random_button_region;
	public ITextureRegion map_menu_button_region;
	public ITextureRegion map_beach_region;
	public ITextureRegion map_city_region;
	public ITextureRegion map_desert_region;
	public ITextureRegion map_forest_region;
	public ITextureRegion map_mountain_region;
	public ITextureRegion map_western_region;
	private BuildableBitmapTextureAtlas mapTextureAtlas;
	private BuildableBitmapTextureAtlas mapBackgroundTextureAtlas;
	
	//Statistics items
	public ITextureRegion statistics_background_region;
	public ITextureRegion statistics_menu_button_region;
	public ITextureRegion statistics_achievements_button_region;
	public ITextureRegion statistics_reset_statistics_button_region;	
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
	public ITextureRegion achievements_statistics_button_region;
	public ITextureRegion achievements_menu_button_region;
	public ITextureRegion achievements_background_region;	
	public ITextureRegion achievements_one_button_region;
	public ITextureRegion achievements_two_button_region;
	public ITextureRegion achievements_three_button_region;
	public ITextureRegion achievements_locked_award;
	
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
	
	private BuildableBitmapTextureAtlas achivementsTextureAtlas;
	private BuildableBitmapTextureAtlas achivementsBackgroundTextureAtlas;
	
	//Game fonts
	public Font meterCounterFont;
	public Font altimeterFont;
	public Font levelCompletedFont;
	public Font gameOverFont;
	public Font maxSpeedFont;
	public Font achievementsUnlockedFont;
	public Font levelStartFont;
	public Font coinsFont;
	
	//Game audio
	public Music wind;
	public Sound plane;
	public Sound explosion;
	public Sound chopper;
	public Sound bird;
	public Sound coinSound;
	
	//Game HUD
	public ITextureRegion game_open_button_region;
	
	//Platforms
	public ITextureRegion game_landing_platfom_region;

	//Backgrounds
	public ITextureRegion game_background_region;
	public ITextureRegion game_darkBackground_region;
	public ITextureRegion game_back_location_region;
	
	//Signs
	public ITextureRegion game_green_arrow_region;
	public ITextureRegion game_red_arrow_region;

	//Objects
	public ITextureRegion game_cloud_region;
	public ITextureRegion game_closer_cloud_region;
	public ITextureRegion game_far_cloud_region;
	public ITextureRegion game_upper_impulse_region;
	public ITextureRegion game_anti_gravity_region;
	public ITextureRegion game_slow_region;
	public ITextureRegion game_plane_region;
	public ITextureRegion game_parachute_region;
	public ITextureRegion game_balloon_region;
	public ITextureRegion game_balloon_basket_region;
	public ITextureRegion game_missile_region;
	public ITextureRegion game_ufo_region;
	public ITextureRegion game_ufo_halo_region;
	
	//Shield
	public ITextureRegion game_shield_region;
	public ITextureRegion game_shield_bar_frame_region;
	public ITextureRegion game_shield_bar_logo_region;

	//Animated
	public ITiledTextureRegion game_player_region;
	public ITiledTextureRegion game_explosion_region;
	public ITiledTextureRegion game_helicopter_region;
	public ITiledTextureRegion game_left_helicopter_region;
	public ITiledTextureRegion game_bird_region;
	public ITiledTextureRegion game_left_bird_region;
	public ITiledTextureRegion game_coin_region;
	
	//Windows
	public ITextureRegion game_level_complete_window_region;
	public ITextureRegion game_pause_window_region;
	public ITextureRegion game_over_window_region;
	public ITextureRegion game_help_window_region;
	
	public ITextureRegion game_map_button_region;
	public ITextureRegion game_quit_button_region;
	public ITextureRegion game_fly_again_button_region;
	public ITextureRegion game_resume_button_region;
	public ITextureRegion game_retry_button_region;
	
	//Game Textures
	private BuildableBitmapTextureAtlas animatedTextureAtlas;
	private BuildableBitmapTextureAtlas backgroundTextureAtlas;
	private BuildableBitmapTextureAtlas backgroundLocationTextureAtlas;
	private BuildableBitmapTextureAtlas objectsTextureAtlas;
	private BuildableBitmapTextureAtlas windowsTextureAtlas;
	
	//Splash Methods
	public void loadSplashScreen() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		splashTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 720, 1280, TextureOptions.BILINEAR);
		splash_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(splashTextureAtlas, activity, "splash.png", 0, 0);
		splashTextureAtlas.load();
	}
	
	public void unloadSplashScreen() {
		splashTextureAtlas.unload();
		splash_region = null;
	}
	
	//Menu methods
	public void loadMenuResources() {
		loadLoadingGraphics();
		loadMenuGraphics();
		loadMenuAudio();
		loadMenuFonts();
	}
	
	public void unloadMenuResources() {
		unloadMenuTextures();
		unloadMenuFonts();
		unloadMenuAudio();
	}
	
	private void loadLoadingGraphics() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/menu/");
		backgroundLoadingTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 720, 1280, TextureOptions.BILINEAR);
		
		loading_background_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(backgroundLoadingTextureAtlas, activity, "loading_background.png");
		
		try {
			this.backgroundLoadingTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
			this.backgroundLoadingTextureAtlas.load();
		} catch (final TextureAtlasBuilderException e) {
			org.andengine.util.debug.Debug.e(e);
		}
	}

	private void loadMenuGraphics() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/menu/");
		menuTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
		backgroundMenuTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 720, 1280, TextureOptions.BILINEAR);
		
		menu_background_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(backgroundMenuTextureAtlas, activity, "menu_background.png");
		menu_play_button_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "menu_play_button.png");
		menu_statistics_button_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "menu_statistics_button.png");
		menu_shop_button_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "menu_shop_button.png");
		menu_rate_us_button_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "menu_rateus_button.png");
		menu_cloud_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "menu_cloud.png");
		menu_far_cloud_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "menu_far_cloud.png");
		menu_title_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "menu_title.png");
		
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
		/*final ITexture loadingTexture = new BitmapTextureAtlas(activity.getTextureManager(), 64, 64, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		loadingFont = FontFactory.createStrokeFromAsset(activity.getFontManager(), loadingTexture, activity.getAssets(), "simple.ttf", 35, true, Color.BLACK_ARGB_PACKED_INT, 0.1f, Color.BLACK_ARGB_PACKED_INT);
		loadingFont.load();*/
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
		
		animatedTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1150, 1150, TextureOptions.BILINEAR);
		backgroundTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 720, 1280, TextureOptions.BILINEAR);
		backgroundLocationTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 720, 750, TextureOptions.BILINEAR);
		objectsTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 2048, 2048, TextureOptions.BILINEAR);
		windowsTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 2048, 2048, TextureOptions.BILINEAR);
		
		//Background texture objects
		if (MapScene.getDayOrNight()) {
			game_background_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(backgroundTextureAtlas, activity, "game_background_day.png");
		} else {
			game_background_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(backgroundTextureAtlas, activity, "game_background_night.png");
		}
		
		if (MapScene.getLevel() == 1) {
			game_back_location_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(backgroundLocationTextureAtlas, activity, "game_back_beach.png");
			game_landing_platfom_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(objectsTextureAtlas, activity, "game_landing_platform_beach.png");
		} else if (MapScene.getLevel() == 2) {
			game_back_location_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(backgroundLocationTextureAtlas, activity, "game_back_city.png");
			game_landing_platfom_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(objectsTextureAtlas, activity, "game_landing_platform_city.png");
		} else if (MapScene.getLevel() == 3) {
			game_back_location_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(backgroundLocationTextureAtlas, activity, "game_back_forest.png");
			game_landing_platfom_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(objectsTextureAtlas, activity, "game_landing_platform_forest.png");
		} else if (MapScene.getLevel() == 4) {
			game_back_location_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(backgroundLocationTextureAtlas, activity, "game_back_desert.png");
			game_landing_platfom_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(objectsTextureAtlas, activity, "game_landing_platform_desert.png");
		} else if (MapScene.getLevel() == 5) {
			game_back_location_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(backgroundLocationTextureAtlas, activity, "game_back_mountain.png");
			game_landing_platfom_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(objectsTextureAtlas, activity, "game_landing_platform_mountain.png");
		} else if (MapScene.getLevel() == 6) {
			game_back_location_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(backgroundLocationTextureAtlas, activity, "game_back_western.png");
			game_landing_platfom_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(objectsTextureAtlas, activity, "game_landing_platform_western.png");
		}
		
		
		//Objects texture objects
		game_closer_cloud_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(objectsTextureAtlas, activity, "game_closer_cloud.png");
		game_far_cloud_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(objectsTextureAtlas, activity, "game_far_cloud.png");
		game_cloud_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(objectsTextureAtlas, activity, "game_cloud.png");
		game_plane_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(objectsTextureAtlas, activity, "game_plane.png");
		game_open_button_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(objectsTextureAtlas, activity, "game_open_button.png");
		game_shield_bar_frame_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(objectsTextureAtlas, activity, "game_shield_bar_frame.png");
		game_shield_bar_logo_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(objectsTextureAtlas, activity, "game_shield_bar_logo.png");
		
		//PowerUps
		game_upper_impulse_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(objectsTextureAtlas, activity, "game_upper_impulse.png");
		game_anti_gravity_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(objectsTextureAtlas, activity, "game_antigravity.png");
		game_slow_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(objectsTextureAtlas, activity, "game_slow.png");
		game_shield_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(objectsTextureAtlas, activity, "game_shield.png");
		
		//Parachute
		game_parachute_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(objectsTextureAtlas, activity, "game_parachute.png");
		
		//Arrows
		game_red_arrow_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(objectsTextureAtlas, activity, "game_red_arrow.png");
		game_green_arrow_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(objectsTextureAtlas, activity, "game_green_arrow.png");
		
		//Enemies
		game_balloon_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(objectsTextureAtlas, activity, "game_balloon.png");
		game_balloon_basket_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(objectsTextureAtlas, activity, "game_balloon_basket.png");
		game_missile_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(objectsTextureAtlas, activity, "game_missile.png");
		game_ufo_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(objectsTextureAtlas, activity, "game_ufo.png");
		game_ufo_halo_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(objectsTextureAtlas, activity, "game_ufo_halo.png");
		
		//Windows texture objects
		game_level_complete_window_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(windowsTextureAtlas, activity, "game_level_complete_window.png");
		game_pause_window_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(windowsTextureAtlas, activity, "game_pause_window.png");
		game_over_window_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(windowsTextureAtlas, activity, "game_over_window.png");
		game_help_window_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(windowsTextureAtlas, activity, "game_help_window.png");
		game_map_button_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(windowsTextureAtlas, activity, "game_map_button.png");
		game_quit_button_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(windowsTextureAtlas, activity, "game_quit_button.png");
		game_fly_again_button_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(windowsTextureAtlas, activity, "game_fly_again_button.png");
		game_resume_button_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(windowsTextureAtlas, activity, "game_resume_button.png");
		game_retry_button_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(windowsTextureAtlas, activity, "game_retry_button.png");
		
		//Animated texture objects
		game_player_region = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(animatedTextureAtlas, activity, "game_player.png", 4, 1);
		game_explosion_region = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(animatedTextureAtlas, activity, "game_explosion.png", 6, 1);
		game_helicopter_region = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(animatedTextureAtlas, activity, "game_helicopter.png", 2, 1);
		game_left_helicopter_region = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(animatedTextureAtlas, activity, "game_left_helicopter.png", 2, 1);
		game_bird_region = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(animatedTextureAtlas, activity, "game_bird.png", 2, 1);
		game_left_bird_region = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(animatedTextureAtlas, activity, "game_left_bird.png", 2, 1);
		game_coin_region = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(animatedTextureAtlas, activity, "game_coin.png", 4, 1);
		
		try {
			this.animatedTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
			this.backgroundTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
			this.backgroundLocationTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
			this.objectsTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
			this.windowsTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
			this.animatedTextureAtlas.load();
			this.backgroundTextureAtlas.load();
			this.backgroundLocationTextureAtlas.load();
			this.objectsTextureAtlas.load();
			this.windowsTextureAtlas.load();
		} catch (final TextureAtlasBuilderException e) {
			Debug.e(e);
		}
	}

	private void loadGameAudio() {
		MusicFactory.setAssetBasePath("music/");
		SoundFactory.setAssetBasePath("sound/");
		try {
			wind = MusicFactory.createMusicFromAsset(activity.getMusicManager(), activity, "wind.mp3");
			wind.setLooping(true);
			plane = SoundFactory.createSoundFromAsset(activity.getSoundManager(), activity, "plane.mp3");
			bird = SoundFactory.createSoundFromAsset(activity.getSoundManager(), activity, "bird.mp3");
			chopper = SoundFactory.createSoundFromAsset(activity.getSoundManager(), activity, "chopper.mp3");
			explosion = SoundFactory.createSoundFromAsset(activity.getSoundManager(), activity, "explosion.mp3");
			coinSound = SoundFactory.createSoundFromAsset(activity.getSoundManager(), activity, "coin.mp3");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void unloadGameAudio() {
		wind.stop();
		plane.stop();
		bird.stop();
		chopper.stop();
		explosion.stop();
		coinSound.stop();
		activity.getMusicManager().remove(wind);
		activity.getSoundManager().remove(plane);
		activity.getSoundManager().remove(bird);
		activity.getSoundManager().remove(chopper);
		activity.getSoundManager().remove(explosion);
		//activity.getSoundManager().remove(coinSound);
		System.gc();
	}
	
	private void loadGameFonts() {
		FontFactory.setAssetBasePath("font/game/");
		final ITexture meterCounterTexture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		final ITexture altimeterTexture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		final ITexture levelCompletedTexture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		final ITexture gameOverTexture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		final ITexture maxSpeedTexture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		final ITexture achievementsUnlockedTexture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		final ITexture levelStartTexture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		final ITexture coinsTexture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		meterCounterFont = FontFactory.createStrokeFromAsset(activity.getFontManager(), meterCounterTexture, activity.getAssets(), "simple.ttf", 35, true, Color.WHITE_ARGB_PACKED_INT, 0.5f, Color.WHITE_ARGB_PACKED_INT);
		altimeterFont = FontFactory.createStrokeFromAsset(activity.getFontManager(), altimeterTexture, activity.getAssets(), "simple.ttf", 35, true, Color.WHITE_ARGB_PACKED_INT, 0.5f, Color.WHITE_ARGB_PACKED_INT);
		levelCompletedFont = FontFactory.createStrokeFromAsset(activity.getFontManager(), levelCompletedTexture, activity.getAssets(), "simple.ttf", 35, true, Color.BLACK_ARGB_PACKED_INT, 0.1f, Color.BLACK_ARGB_PACKED_INT);
		maxSpeedFont = FontFactory.createStrokeFromAsset(activity.getFontManager(), maxSpeedTexture, activity.getAssets(), "simple.ttf", 35, true, Color.BLACK_ARGB_PACKED_INT, 0.1f, Color.BLACK_ARGB_PACKED_INT);
		gameOverFont = FontFactory.createStrokeFromAsset(activity.getFontManager(), gameOverTexture, activity.getAssets(), "simple.ttf", 45, true, Color.BLACK_ARGB_PACKED_INT, 0.1f, Color.BLACK_ARGB_PACKED_INT);
		achievementsUnlockedFont = FontFactory.createStrokeFromAsset(activity.getFontManager(), achievementsUnlockedTexture, activity.getAssets(), "simple.ttf", 40, true, Color.BLACK_ARGB_PACKED_INT, 0.1f, Color.BLACK_ARGB_PACKED_INT); 
		levelStartFont = FontFactory.createStrokeFromAsset(activity.getFontManager(), levelStartTexture, activity.getAssets(), "simple.ttf", 55, true, Color.BLACK_ARGB_PACKED_INT, 1f, Color.BLACK_ARGB_PACKED_INT);
		coinsFont = FontFactory.createStrokeFromAsset(activity.getFontManager(), coinsTexture, activity.getAssets(), "simple.ttf", 35, true, Color.WHITE_ARGB_PACKED_INT, 0.5f, Color.WHITE_ARGB_PACKED_INT);
		maxSpeedFont.load();
		meterCounterFont.load();
		altimeterFont.load();
		levelCompletedFont.load();
		gameOverFont.load();
		achievementsUnlockedFont.load();
		levelStartFont.load();
		coinsFont.load();
	}
	
	private void unloadGameTextures() {
		this.animatedTextureAtlas.unload();
		this.backgroundTextureAtlas.unload();
		this.backgroundLocationTextureAtlas.unload();
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
		levelStartFont.unload();
		coinsFont.unload();
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
		backgroundStatisticsTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 720, 1280, TextureOptions.BILINEAR);
		statisticsTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
		
		statistics_background_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(backgroundStatisticsTextureAtlas, activity, "statistics_background.png");
		statistics_menu_button_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(statisticsTextureAtlas, activity, "statistics_menu_button.png");
		statistics_achievements_button_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(statisticsTextureAtlas, activity, "statistics_achievements_button.png");
		statistics_reset_statistics_button_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(statisticsTextureAtlas, activity, "statistics_reset_statistics_button.png");
		
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
		numberOfSuccessfulJumpsFont = FontFactory.createStrokeFromAsset(activity.getFontManager(), numberOfSuccessfulJumpsTexture, activity.getAssets(), "simple.ttf", 45, true, Color.BLACK_ARGB_PACKED_INT, 0.1f, Color.BLACK_ARGB_PACKED_INT);
		numberOfUnsuccessfulJumpsFont = FontFactory.createStrokeFromAsset(activity.getFontManager(), numberOfUnsuccessfulJumpsTexture, activity.getAssets(), "simple.ttf", 45, true, Color.BLACK_ARGB_PACKED_INT, 0.1f, Color.BLACK_ARGB_PACKED_INT);
		maxFliedMetersFont = FontFactory.createStrokeFromAsset(activity.getFontManager(), maxFliedMetersTexture, activity.getAssets(), "simple.ttf", 45, true, Color.BLACK_ARGB_PACKED_INT, 0.1f, Color.BLACK_ARGB_PACKED_INT);
		freeFliedMetersFont  = FontFactory.createStrokeFromAsset(activity.getFontManager(), freeFliedMetersTexture, activity.getAssets(), "simple.ttf", 45, true, Color.BLACK_ARGB_PACKED_INT, 0.1f, Color.BLACK_ARGB_PACKED_INT);
		parachuteFliedMetersFont = FontFactory.createStrokeFromAsset(activity.getFontManager(), parachuteFliedMetersTexture, activity.getAssets(), "simple.ttf", 45, true, Color.BLACK_ARGB_PACKED_INT, 0.1f, Color.BLACK_ARGB_PACKED_INT);
		upperImpulseCounterFont = FontFactory.createStrokeFromAsset(activity.getFontManager(), upperImpulseCounterTexture, activity.getAssets(), "simple.ttf", 45, true, Color.BLACK_ARGB_PACKED_INT, 0.1f, Color.BLACK_ARGB_PACKED_INT); 
		antigravityCounterFont = FontFactory.createStrokeFromAsset(activity.getFontManager(), antigravityCounterTexture, activity.getAssets(), "simple.ttf", 45, true, Color.BLACK_ARGB_PACKED_INT, 0.1f, Color.BLACK_ARGB_PACKED_INT);
		shieldCounterFont = FontFactory.createStrokeFromAsset(activity.getFontManager(), shieldCounterTexture, activity.getAssets(), "simple.ttf", 45, true, Color.BLACK_ARGB_PACKED_INT, 0.1f, Color.BLACK_ARGB_PACKED_INT);
		slowCounterFont = FontFactory.createStrokeFromAsset(activity.getFontManager(), slowCounterTexture, activity.getAssets(), "simple.ttf", 45, true, Color.BLACK_ARGB_PACKED_INT, 0.1f, Color.BLACK_ARGB_PACKED_INT);
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
		achivementsBackgroundTextureAtlas  = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 720, 1280, TextureOptions.BILINEAR);
		
		achievements_background_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(achivementsBackgroundTextureAtlas, activity, "achievements_background.png");
		achievements_menu_button_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(achivementsTextureAtlas, activity, "achievements_menu_button.png");
		achievements_statistics_button_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(achivementsTextureAtlas, activity, "achievements_statistics_button.png");		
		achievements_one_button_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(achivementsTextureAtlas, activity, "achievements_1_button.png");
		achievements_two_button_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(achivementsTextureAtlas, activity, "achievements_2_button.png");
		achievements_three_button_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(achivementsTextureAtlas, activity, "achievements_3_button.png");
		achievements_locked_award = BitmapTextureAtlasTextureRegionFactory.createFromAsset(achivementsTextureAtlas, activity, "achievements_locked_award.png");
		
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
	
	//Shop methods
	public void loadShopResources() {
		loadShopGraphics();
		loadShopAudio();
		loadShopFonts();
	}
	
	public void unloadShopResources() {
		unloadShopTextures();
		unloadShopFonts();		
	}
	
	private void loadShopGraphics() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/shop/");
		shopTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
		shopBackgroundTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 720, 1280, TextureOptions.BILINEAR);
		
		shop_menu_button_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(shopTextureAtlas, activity, "shop_menu_button.png");
		shop_desert_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(shopTextureAtlas, activity, "shop_desert_button.png");
		shop_mountain_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(shopTextureAtlas, activity, "shop_mountain_button.png");
		shop_western_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(shopTextureAtlas, activity, "shop_western_button.png");
		shop_locked_location_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(shopTextureAtlas, activity, "shop_locked_location.png");
		shop_background_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(shopBackgroundTextureAtlas, activity, "shop_background.png");
		try {
			this.shopTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
			this.shopBackgroundTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
			this.shopTextureAtlas.load();
			this.shopBackgroundTextureAtlas.load();
		} catch (final TextureAtlasBuilderException e) {
			org.andengine.util.debug.Debug.e(e);
		}
	}
	
	private void loadShopAudio() {
		
	}
	
	private void loadShopFonts() {
		FontFactory.setAssetBasePath("font/shop/");
		final ITexture coinsTexture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		shopCoinsFont = FontFactory.createStrokeFromAsset(activity.getFontManager(), coinsTexture, activity.getAssets(), "simple.ttf", 45, true, Color.BLACK_ARGB_PACKED_INT, 0.1f, Color.BLACK_ARGB_PACKED_INT);
		shopCoinsFont.load();
	}
	
	private void unloadShopTextures() {
		this.shopTextureAtlas.unload();
		this.shopBackgroundTextureAtlas.unload();
	}
	
	private void unloadShopFonts() {
		shopCoinsFont.unload();
	}
	
	//Map methods
	public void loadMapResources() {
		loadMapGraphics();
		loadMapAudio();
		loadMapFonts();
	}
	
	public void unloadMapResources() {
		unloadMapTextures();
		unloadMapFonts();		
	}
	
	private void loadMapGraphics() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/map/");
		mapTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1500, 1500, TextureOptions.BILINEAR);
		mapBackgroundTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 720, 1280, TextureOptions.BILINEAR);
		
		map_random_button_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mapTextureAtlas, activity, "map_random_button.png");
		map_menu_button_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mapTextureAtlas, activity, "map_menu_button.png");
		map_beach_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mapTextureAtlas, activity, "map_beach_button.png");
		map_city_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mapTextureAtlas, activity, "map_city_button.png");
		map_desert_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mapTextureAtlas, activity, "map_desert_button.png");
		map_forest_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mapTextureAtlas, activity, "map_forest_button.png");
		map_mountain_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mapTextureAtlas, activity, "map_mountain_button.png");
		map_western_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mapTextureAtlas, activity, "map_western_button.png");
		map_background_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mapBackgroundTextureAtlas, activity, "map_background.png");
		
		try {
			this.mapTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
			this.mapBackgroundTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
			this.mapTextureAtlas.load();
			this.mapBackgroundTextureAtlas.load();
		} catch (final TextureAtlasBuilderException e) {
			org.andengine.util.debug.Debug.e(e);
		}
	}
	
	private void loadMapAudio() {
		
	}
	
	private void loadMapFonts() {
		FontFactory.setAssetBasePath("font/map/");
	}
	
	private void unloadMapTextures() {
		this.mapTextureAtlas.unload();
		this.mapBackgroundTextureAtlas.unload();
	}
	
	private void unloadMapFonts() {
		
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



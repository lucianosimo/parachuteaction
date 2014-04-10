package com.lucianosimo.parachuteaction.helper;

public class AchievementsHelper {

	private final static int FIRST_JUMP_ACHIEVEMENT = 1;
	private final static int JUMPS_10_ACHIEVEMENT = 10;
	private final static int JUMPS_50_ACHIEVEMENT = 50;
	private final static int JUMPS_100_ACHIEVEMENT = 100;
	private final static int JUMPS_500_ACHIEVEMENT = 500;
	private final static int FREE_FLY_5000_ACHIEVEMENT = 5000;
	private final static int FREE_FLY_15000_ACHIEVEMENT = 15000;
	private final static int FREE_FLY_50000_ACHIEVEMENT = 50000;
	private final static int FREE_FLY_100000_ACHIEVEMENT = 100000;
	private final static int PARACHUTE_FLY_2500_ACHIEVEMENT = 2500;
	private final static int PARACHUTE_FLY_15000_ACHIEVEMENT = 15000;
	private final static int PARACHUTE_FLY_50000_ACHIEVEMENT = 50000;
	private final static int FAIL_JUMPS_5_ACHIEVEMENT = 5;
	private final static int FAIL_JUMPS_10_ACHIEVEMENT = 10;
	private final static int FAIL_JUMPS_25_ACHIEVEMENT = 25;
	private final static int UI_10_ACHIEVEMENT = 10;
	private final static int UI_50_ACHIEVEMENT = 50;
	private final static int UI_100_ACHIEVEMENT = 100;
	private final static int ANTIGRAVITY_10_ACHIEVEMENT = 10;
	private final static int ANTIGRAVITY_50_ACHIEVEMENT = 50;
	private final static int ANTIGRAVITY_100_ACHIEVEMENT = 100;
	private final static int SHIELD_10_ACHIEVEMENT = 10;
	private final static int SHIELD_50_ACHIEVEMENT = 50;
	private final static int SHIELD_100_ACHIEVEMENT = 100;
	private final static int SLOW_10_ACHIEVEMENT = 10;
	private final static int SLOW_50_ACHIEVEMENT = 50;
	private final static int SLOW_100_ACHIEVEMENT = 100;
	private final static int DESTROY_5_HELICOPTERS_ACHIEVEMENT = 5;
	private final static int DESTROY_25_HELICOPTERS_ACHIEVEMENT = 25;
	private final static int DESTROY_100_HELICOPTERS_ACHIEVEMENT = 100;
	private final static int DESTROY_5_BALLOONS_ACHIEVEMENT = 5;
	private final static int DESTROY_25_BALLOONS_ACHIEVEMENT = 25;
	private final static int DESTROY_100_BALLOONS_ACHIEVEMENT = 100;
	private final static int DESTROY_5_BIRDS_ACHIEVEMENT = 5;
	private final static int DESTROY_25_BIRDS_ACHIEVEMENT = 25;
	private final static int DESTROY_100_BIRDS_ACHIEVEMENT = 100;
	
	//SUCCESSFUL JUMPS METHODS
	public static boolean firstJumpAchievement(int jumpsCounter) {
		if (jumpsCounter >= FIRST_JUMP_ACHIEVEMENT) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean firstJumpAchievementUnlockedInLevel(int before, int after) {
		if (before < FIRST_JUMP_ACHIEVEMENT && after >= FIRST_JUMP_ACHIEVEMENT) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean jumps10Achievement(int jumpsCounter) {
		if (jumpsCounter >= JUMPS_10_ACHIEVEMENT) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean jumps10AchievementUnlockedInLevel(int before, int after) {
		if (before < JUMPS_10_ACHIEVEMENT && after >= JUMPS_10_ACHIEVEMENT) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean jumps50Achievement(int jumpsCounter) {
		if (jumpsCounter >= JUMPS_50_ACHIEVEMENT) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean jumps50AchievementUnlockedInLevel(int before, int after) {
		if (before < JUMPS_50_ACHIEVEMENT && after >= JUMPS_50_ACHIEVEMENT) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean jumps100Achievement(int jumpsCounter) {
		if (jumpsCounter >= JUMPS_100_ACHIEVEMENT) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean jumps100AchievementUnlockedInLevel(int before, int after) {
		if (before < JUMPS_100_ACHIEVEMENT && after >= JUMPS_100_ACHIEVEMENT) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean jumps500Achievement(int jumpsCounter) {
		if (jumpsCounter >= JUMPS_500_ACHIEVEMENT) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean jumps500AchievementUnlockedInLevel(int before, int after) {
		if (before < JUMPS_500_ACHIEVEMENT && after >= JUMPS_500_ACHIEVEMENT) {
			return true;
		} else {
			return false;
		}
	}
	
	
	//FREE FLY ACHIEVEMENTS METHODS
	public static boolean freeFly5000Achievement(int freeFlyCounter) {
		if (freeFlyCounter >= FREE_FLY_5000_ACHIEVEMENT) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean freeFly5000AchievementUnlockedInLevel(int before, int after) {
		if (before < FREE_FLY_5000_ACHIEVEMENT && after >= FREE_FLY_5000_ACHIEVEMENT) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean freeFly15000Achievement(int freeFlyCounter) {
		if (freeFlyCounter >= FREE_FLY_15000_ACHIEVEMENT) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean freeFly15000AchievementUnlockedInLevel(int before, int after) {
		if (before < FREE_FLY_15000_ACHIEVEMENT && after >= FREE_FLY_15000_ACHIEVEMENT) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean freeFly50000Achievement(int freeFlyCounter) {
		if (freeFlyCounter >= FREE_FLY_50000_ACHIEVEMENT) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean freeFly50000AchievementUnlockedInLevel(int before, int after) {
		if (before < FREE_FLY_50000_ACHIEVEMENT && after >= FREE_FLY_50000_ACHIEVEMENT) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean freeFly100000Achievement(int freeFlyCounter) {
		if (freeFlyCounter >= FREE_FLY_100000_ACHIEVEMENT) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean freeFly100000AchievementUnlockedInLevel(int before, int after) {
		if (before < FREE_FLY_100000_ACHIEVEMENT && after >= FREE_FLY_100000_ACHIEVEMENT) {
			return true;
		} else {
			return false;
		}
	}
	
	//PARACHUTE FLY ACHIEVEMENTS METHODS
	public static boolean parachuteFly2500Achievement(int parachuteFlyCounter) {
		if (parachuteFlyCounter >= PARACHUTE_FLY_2500_ACHIEVEMENT) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean parachuteFly2500AchievementUnlockedInLevel(int before, int after) {
		if (before < PARACHUTE_FLY_2500_ACHIEVEMENT && after >= PARACHUTE_FLY_2500_ACHIEVEMENT) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean parachuteFly15000Achievement(int parachuteFlyCounter) {
		if (parachuteFlyCounter >= PARACHUTE_FLY_15000_ACHIEVEMENT) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean parachuteFly15000AchievementUnlockedInLevel(int before, int after) {
		if (before < PARACHUTE_FLY_15000_ACHIEVEMENT && after >= PARACHUTE_FLY_15000_ACHIEVEMENT) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean parachuteFly50000Achievement(int parachuteFlyCounter) {
		if (parachuteFlyCounter >= PARACHUTE_FLY_50000_ACHIEVEMENT) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean parachuteFly50000AchievementUnlockedInLevel(int before, int after) {
		if (before < PARACHUTE_FLY_50000_ACHIEVEMENT && after >= PARACHUTE_FLY_50000_ACHIEVEMENT) {
			return true;
		} else {
			return false;
		}
	}
	
	//FAIL JUMPS ACHIEVEMENTS METHODS
	public static boolean failJumps5Achievement(int failJumpsCounter) {
		if (failJumpsCounter >= FAIL_JUMPS_5_ACHIEVEMENT) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean failJumps5AchievementUnlockedInLevel(int before, int after) {
		if (before < FAIL_JUMPS_5_ACHIEVEMENT && after >= FAIL_JUMPS_5_ACHIEVEMENT) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean failJumps10Achievement(int failJumpsCounter) {
		if (failJumpsCounter >= FAIL_JUMPS_10_ACHIEVEMENT) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean failJumps10AchievementUnlockedInLevel(int before, int after) {
		if (before < FAIL_JUMPS_10_ACHIEVEMENT && after >= FAIL_JUMPS_10_ACHIEVEMENT) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean failJumps25Achievement(int failJumpsCounter) {
		if (failJumpsCounter >= FAIL_JUMPS_25_ACHIEVEMENT) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean failJumps25AchievementUnlockedInLevel(int before, int after) {
		if (before < FAIL_JUMPS_25_ACHIEVEMENT && after >= FAIL_JUMPS_25_ACHIEVEMENT) {
			return true;
		} else {
			return false;
		}
	}
	
	//UPPER ACHIEVEMENTS METHODS
	public static boolean upper10AchievementUnlocked(int upperCounter) {
		if (upperCounter >= UI_10_ACHIEVEMENT) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean upper10AchievementUnlockedInLevel(int before, int after) {
		if (before < UI_10_ACHIEVEMENT && after >= UI_10_ACHIEVEMENT) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean upper50AchievementUnlocked(int upperCounter) {
		if (upperCounter >= UI_50_ACHIEVEMENT) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean upper50AchievementUnlockedInLevel(int before, int after) {
		if (before < UI_50_ACHIEVEMENT && after >= UI_50_ACHIEVEMENT) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean upper100AchievementUnlocked(int upperCounter) {
		if (upperCounter >= UI_100_ACHIEVEMENT) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean upper100AchievementUnlockedInLevel(int before, int after) {
		if (before < UI_100_ACHIEVEMENT && after >= UI_100_ACHIEVEMENT) {
			return true;
		} else {
			return false;
		}
	}
	
	//ANTIGRAVITY ACHIEVEMENTS METHODS
	public static boolean antigravity10AchievementUnlocked(int antigravityCounter) {
		if (antigravityCounter >= ANTIGRAVITY_10_ACHIEVEMENT) {
			return true;
		} else {
			return false;
		}
	}
		
	public static boolean antigravity10AchievementUnlockedInLevel(int before, int after) {
		if (before < ANTIGRAVITY_10_ACHIEVEMENT && after >= ANTIGRAVITY_10_ACHIEVEMENT) {
			return true;
		} else {
			return false;
		}
	}
		
	public static boolean antigravity50AchievementUnlocked(int antigravityCounter) {
		if (antigravityCounter >= ANTIGRAVITY_50_ACHIEVEMENT) {
			return true;
		} else {
			return false;
		}
	}
		
	public static boolean antigravity50AchievementUnlockedInLevel(int before, int after) {
		if (before < ANTIGRAVITY_50_ACHIEVEMENT && after >= ANTIGRAVITY_50_ACHIEVEMENT) {
			return true;
		} else {
			return false;
		}
	}
		
	public static boolean antigravity100AchievementUnlocked(int antigravityCounter) {
		if (antigravityCounter >= ANTIGRAVITY_100_ACHIEVEMENT) {
			return true;
		} else {
			return false;
		}
	}
		
	public static boolean antigravity100AchievementUnlockedInLevel(int before, int after) {
		if (before < ANTIGRAVITY_100_ACHIEVEMENT && after >= ANTIGRAVITY_100_ACHIEVEMENT) {
			return true;
		} else {
			return false;
		}
	}
	
	//SHIELD ACHIEVEMENTS METHODS
	public static boolean shield10AchievementUnlocked(int shieldCounter) {
		if (shieldCounter >= SHIELD_10_ACHIEVEMENT) {
			return true;
		} else {
			return false;
		}
	}
		
	public static boolean shield10AchievementUnlockedInLevel(int before, int after) {
		if (before < SHIELD_10_ACHIEVEMENT && after >= SHIELD_10_ACHIEVEMENT) {
			return true;
		} else {
			return false;
		}
	}
		
	public static boolean shield50AchievementUnlocked(int shieldCounter) {
		if (shieldCounter >= SHIELD_50_ACHIEVEMENT) {
			return true;
		} else {
			return false;
		}
	}
		
	public static boolean shield50AchievementUnlockedInLevel(int before, int after) {
		if (before < SHIELD_50_ACHIEVEMENT && after >= SHIELD_50_ACHIEVEMENT) {
			return true;
		} else {
			return false;
		}
	}
		
	public static boolean shield100AchievementUnlocked(int shieldCounter) {
		if (shieldCounter >= SHIELD_100_ACHIEVEMENT) {
			return true;
		} else {
			return false;
		}
	}
		
	public static boolean shield100AchievementUnlockedInLevel(int before, int after) {
		if (before < SHIELD_100_ACHIEVEMENT && after >= SHIELD_100_ACHIEVEMENT) {
			return true;
		} else {
			return false;
		}
	}
	
	//SLOW ACHIEVEMENTS METHODS
	public static boolean slow10AchievementUnlocked(int slowCounter) {
		if (slowCounter >= SLOW_10_ACHIEVEMENT) {
			return true;
		} else {
			return false;
		}
	}
		
	public static boolean slow10AchievementUnlockedInLevel(int before, int after) {
		if (before < SLOW_10_ACHIEVEMENT && after >= SLOW_10_ACHIEVEMENT) {
			return true;
		} else {
			return false;
		}
	}
		
	public static boolean slow50AchievementUnlocked(int slowCounter) {
		if (slowCounter >= SLOW_50_ACHIEVEMENT) {
			return true;
		} else {
			return false;
		}
	}
		
	public static boolean slow50AchievementUnlockedInLevel(int before, int after) {
		if (before < SLOW_50_ACHIEVEMENT && after >= SLOW_50_ACHIEVEMENT) {
			return true;
		} else {
			return false;
		}
	}
		
	public static boolean slow100AchievementUnlocked(int slowCounter) {
		if (slowCounter >= SLOW_100_ACHIEVEMENT) {
			return true;
		} else {
			return false;
		}
	}
		
	public static boolean slow100AchievementUnlockedInLevel(int before, int after) {
		if (before < SLOW_100_ACHIEVEMENT && after >= SLOW_100_ACHIEVEMENT) {
			return true;
		} else {
			return false;
		}
	}
	
	//DESTROY HELICOPTERS ACHIEVEMENTS METHODS
	public static boolean destroy5helicoptersAchievementUnlocked(int helicoptersCounter) {
		if (helicoptersCounter >= DESTROY_5_HELICOPTERS_ACHIEVEMENT) {
			return true;
		} else {
			return false;
		}
	}
		
	public static boolean destroy5helicoptersAchievementUnlockedInLevel(int before, int after) {
		if (before < DESTROY_5_HELICOPTERS_ACHIEVEMENT && after >= DESTROY_5_HELICOPTERS_ACHIEVEMENT) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean destroy25helicoptersAchievementUnlocked(int helicoptersCounter) {
		if (helicoptersCounter >= DESTROY_25_HELICOPTERS_ACHIEVEMENT) {
			return true;
		} else {
			return false;
		}
	}
		
	public static boolean destroy25helicoptersAchievementUnlockedInLevel(int before, int after) {
		if (before < DESTROY_25_HELICOPTERS_ACHIEVEMENT && after >= DESTROY_25_HELICOPTERS_ACHIEVEMENT) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean destroy100helicoptersAchievementUnlocked(int helicoptersCounter) {
		if (helicoptersCounter >= DESTROY_100_HELICOPTERS_ACHIEVEMENT) {
			return true;
		} else {
			return false;
		}
	}
		
	public static boolean destroy100helicoptersAchievementUnlockedInLevel(int before, int after) {
		if (before < DESTROY_100_HELICOPTERS_ACHIEVEMENT && after >= DESTROY_100_HELICOPTERS_ACHIEVEMENT) {
			return true;
		} else {
			return false;
		}
	}
	
	//DESTROY BALLOONS ACHIEVEMENTS METHODS 
	public static boolean destroy5balloonsAchievementUnlocked(int balloonsCounter) {
		if (balloonsCounter >= DESTROY_5_BALLOONS_ACHIEVEMENT) {
			return true;
		} else {
			return false;
		}
	}
		
	public static boolean destroy5balloonsAchievementUnlockedInLevel(int before, int after) {
		if (before < DESTROY_5_BALLOONS_ACHIEVEMENT && after >= DESTROY_5_BALLOONS_ACHIEVEMENT) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean destroy25balloonsAchievementUnlocked(int balloonsCounter) {
		if (balloonsCounter >= DESTROY_25_BALLOONS_ACHIEVEMENT) {
			return true;
		} else {
			return false;
		}
	}
		
	public static boolean destroy25balloonsAchievementUnlockedInLevel(int before, int after) {
		if (before < DESTROY_25_BALLOONS_ACHIEVEMENT && after >= DESTROY_25_BALLOONS_ACHIEVEMENT) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean destroy100balloonsAchievementUnlocked(int balloonsCounter) {
		if (balloonsCounter >= DESTROY_100_BALLOONS_ACHIEVEMENT) {
			return true;
		} else {
			return false;
		}
	}
		
	public static boolean destroy100balloonsAchievementUnlockedInLevel(int before, int after) {
		if (before < DESTROY_100_BALLOONS_ACHIEVEMENT && after >= DESTROY_100_BALLOONS_ACHIEVEMENT) {
			return true;
		} else {
			return false;
		}
	}
	
	//DESTROY BIRDS ACHIEVEMENTS METHODS
	public static boolean destroy5birdsAchievementUnlocked(int birdsCounter) {
		if (birdsCounter >= DESTROY_5_BIRDS_ACHIEVEMENT) {
			return true;
		} else {
			return false;
		}
	}
		
	public static boolean birds5balloonsAchievementUnlockedInLevel(int before, int after) {
		if (before < DESTROY_5_BIRDS_ACHIEVEMENT && after >= DESTROY_5_BIRDS_ACHIEVEMENT) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean destroy25birdsAchievementUnlocked(int birdsCounter) {
		if (birdsCounter >= DESTROY_25_BIRDS_ACHIEVEMENT) {
			return true;
		} else {
			return false;
		}
	}
		
	public static boolean birds25balloonsAchievementUnlockedInLevel(int before, int after) {
		if (before < DESTROY_25_BIRDS_ACHIEVEMENT && after >= DESTROY_25_BIRDS_ACHIEVEMENT) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean destroy100birdsAchievementUnlocked(int birdsCounter) {
		if (birdsCounter >= DESTROY_100_BIRDS_ACHIEVEMENT) {
			return true;
		} else {
			return false;
		}
	}
		
	public static boolean birds100balloonsAchievementUnlockedInLevel(int before, int after) {
		if (before < DESTROY_100_BIRDS_ACHIEVEMENT && after >= DESTROY_100_BIRDS_ACHIEVEMENT) {
			return true;
		} else {
			return false;
		}
	}
	
}

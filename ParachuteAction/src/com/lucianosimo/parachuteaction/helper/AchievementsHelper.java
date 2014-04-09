package com.lucianosimo.parachuteaction.helper;

public class AchievementsHelper {

	private final static int UPPER_ACHIEVEMENT = 2;
	
	//UPPER ACHIEVEMENTS METHODS
	public static boolean upperAchievementUnlocked(int upperCounter) {
		if (upperCounter >= UPPER_ACHIEVEMENT) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean upperAchievementUnlockedInLevel(int before, int after) {
		if (before < UPPER_ACHIEVEMENT && after >= UPPER_ACHIEVEMENT) {
			return true;
		} else {
			return false;
		}
	}
}

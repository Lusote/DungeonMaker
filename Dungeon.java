package Retardliek;

public class Dungeon{

	private static int numOfLevels = 6;
	private int currentLevel;
	private Level[] allLevels;

	public Dungeon(){
		this.allLevels = inicializeDungeon();
	}

	public Level[] inicializeDungeon(){
		System.out.println("Dungeon started. WINK.");
		allLevels = new Level[1];
		return allLevels;
	}
}
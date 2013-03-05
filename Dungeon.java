//package Retardliek;

public class Dungeon{

	/* TODO
	*	GENERAL:
	*		Save and Load level on change
	*
	*	dungeonLevelDown & dungeonLevelUp:
	*		Check limits.
	*		Works for now, but it won't work on game (with manyy levels and such)
	*
	*/

	private static int numOfLevels = 1;
	private int indexCurrentLevel;
	private Level currentLevel;
	private Level[] allLevels = new Level[numOfLevels];

	public Dungeon(){
		this.allLevels[0] = inicializeOneLevel(0);
		this.indexCurrentLevel = 0;
		this.currentLevel = allLevels[0];
	}

	public Level inicializeOneLevel(int index){
		System.out.println("Dungeon started. WINK.");
		Level oneLevel = new Level();
		int sizeGridX = oneLevel.getGridHeight();
		int sizeGridY = oneLevel.getGridWidth();
		for(int i=0; i<sizeGridX; i++){
			for(int j=0; j<sizeGridY; j++){
				oneLevel.getTile(i,j).setSymbol('#');
			}
		}
		this.allLevels[index] = oneLevel;
		this.currentLevel = oneLevel;
		this.indexCurrentLevel = index;
		return oneLevel;
	}

	public Level getLevel(int lev){
		return this.allLevels[lev];
	}

	public Level getCurrentLevel(){
		return this.currentLevel;
	}

	public void setCurrentLevel(int index){
		this.currentLevel = allLevels[index];
	}

	public void dungeonLevelDown(){
		this.indexCurrentLevel = indexCurrentLevel++;
		this.currentLevel = allLevels[indexCurrentLevel];
	}

	public void dungeonLevelUp(){
		this.indexCurrentLevel = indexCurrentLevel--;
		this.currentLevel = allLevels[indexCurrentLevel];
	}

}
import java.util.Random;

	/* TODO
	*	GENERAL:
	*		Save and Load level on change
	*
	*	dungeonLevelDown & dungeonLevelUp:
	*		Check limits.
	*		Works for now, but it won't work on game (with many levels and such)
	*
	*	initializeOneLevel:
	*		The final version shall create the rooms and corridors.
	*
	*/

	public class Dungeon{

	private static int numOfLevels = 1;
	private int levelWidth = 79;
	private int levelHeight = 24;
	private int indexCurrentLevel, lHeight,lWidth;
	private Level currentLevel;
	private Level[] allLevels = new Level[numOfLevels];

	public Dungeon(){
		this.indexCurrentLevel = 0;
		this.currentLevel = allLevels[0];
		this.lWidth = levelWidth;
		this.lHeight = levelHeight;
		this.allLevels[0] = initializeOneLevel(0);
	}

	// Initialize a Level
	public Level initializeOneLevel(int index){
		Level oneLevel = new Level(index, this.getLevelHeight(), this.getLevelWidth());
		int sizeGridX = oneLevel.getGridWidth();
		int sizeGridY = oneLevel.getGridHeight();
		Position p;
		for(int i=0; i<=sizeGridX; i++){
			for(int j=0; j<=sizeGridY; j++){
				p = new Position(i,j);
				oneLevel.getTile(p).setSymbol('#');
				oneLevel.getTile(p).setPosition(p);
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

	public int getLevelWidth(){
		return this.lWidth;
	}

	public int getLevelHeight(){
		return this.lHeight;
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

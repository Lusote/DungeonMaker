import java.util.ArrayList;
import java.util.Random;

/*
*	TODO:
*		getTile();
*/

public class Level{

	private Tile[][] grid; 
	private int gridHeight;
	private int gridWidth;
	private ArrayList<Tile> validTiles = new ArrayList<Tile>();

	public Level(){
		this.gridHeight = 80;
		this.gridWidth = 25;
		this.grid = new Tile[gridHeight][gridWidth];
		 for(int i=0;i<80;i++){
		 	for(int j=0;j<25;j++){
		 		this.grid[i][j] = new Tile();
		 	}
		 }
	}

	// TODO: Return en caso de caldeiro?
	//		 Exception?
	public Tile getTile(Position p){
		Tile toReturn = new Tile();
		if(0<=p.getX()<=this.gridHeight &&
			0<=p.getY()<=this.gridHeight &&){
				return this.grid[indexX][indexY];
		}
		else{
			System.out.println("ERROR: Requesting invalid tile.");
			return null;
		}
	}

	// Returns a random valid tile (allegedly)
	public Tile getRandomValidTile(Level l){
		Random randomGen = new Random();
		int indexValidTile = randomGen.nextInt(validTiles.size());
		Tile tileReturn = validTiles.get(indexValidTile);
		while(!tileReturn.isTileEmpty()){
			indexValidTile = randomGen.nextInt(validTiles.size());
			tileReturn = validTiles.get(indexValidTile);
		}
		return tileReturn;
	}

	public void setStairsUp(){
		Tile stairsUp = this.getValidTile(this);
		stairsUp.setDistStairsUp(0);
	}

	public void setStairsDown(){
		Tile stairsDown = this.getValidTile(this);
		stairsDown.setDistStairsUp(0);
	}

	public int getGridHeight(){
		return this.gridHeight;
	}

	public int getGridWidth(){
		return this.gridWidth;
	}

}
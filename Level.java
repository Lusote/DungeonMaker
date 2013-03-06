import java.util.ArrayList;
import java.util.Random;

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
	public Tile getTile(int indexX, int indexY){
		Tile toReturn = new Tile();
		if(indexX>=0 && indexX<this.gridHeight &&
			indexY>=0 && indexY<this.gridWidth){
				return this.grid[indexX][indexY];
		}
		else{
			System.out.println("ERROR: Requesting invalid tile.");
			return null;
		}
	}

	// Returns a valid tile (allegedly)
	public Tile getValidTile(Level l){
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
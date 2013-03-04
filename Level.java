package Retardliek;

import java.util.ArrayList;
import java.util.Random;

public class Level{

	private Tile[][] grid; 
	private static int gridHeight = 80;
	private static int gridWidth  = 25;
	private ArrayList<Tile> validTiles = new ArrayList<Tile>();

	public Level(){
		this.grid = new Tile[gridHeight][gridWidth];
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

}
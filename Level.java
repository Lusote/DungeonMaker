import java.util.ArrayList;
import java.util.Random;

/*
*	TODO:
*		createRoom() Has to complete doors?
*/

public class Level{

	private int index;
	private int gridHeight;
	private int gridWidth;
	private Tile[][] grid; 
	private ArrayList<Room> rooms;
	private ArrayList<Position> floorTiles;
	private ArrayList<Position> floorAndWallsTiles;
    private static Random randomGenerator = new Random();


	public Level(int ind, int gH, int gW){
		this.rooms = new ArrayList<Room>();
		this.floorTiles = new ArrayList<Position>();
		this.floorAndWallsTiles = new ArrayList<Position>();
		this.index = ind;
		this.gridHeight = gH;
		this.gridWidth = gW;
		this.grid = new Tile[gW+1][gH+1];
		for(int i = 0; i <=gW ;i++){
		 	for(int j=0;j<=gH;j++){
		 		this.grid[i][j] = new Tile();
		 	}
		 }
	}

	public Room createRoom(){

		int randomIndexULX = randomGenerator.nextInt(this.getGridWidth()-2)+1;
		int randomIndexULY = randomGenerator.nextInt(this.getGridHeight()-2)+1;
		int randomIndexBRX = randomIndexULX + randomGenerator.nextInt(6)+4;		
		int randomIndexBRY = randomIndexULY  + randomGenerator.nextInt(5)+2;
		Position uL = new Position(randomIndexULX,randomIndexULY);
		Position bR = new Position(randomIndexBRX, randomIndexBRY);
		while(!uL.isValidPositionForRoom() ||
			!bR.isValidPositionForRoom()){
				randomIndexULX = randomGenerator.nextInt(this.getGridWidth()-2)+1;
				randomIndexULY = randomGenerator.nextInt(this.getGridHeight()-2)+1;
				randomIndexBRX = randomIndexULX + randomGenerator.nextInt(6)+4;		
				randomIndexBRY = randomIndexULY  + randomGenerator.nextInt(5)+2;
				uL = new Position(randomIndexULX,randomIndexULY);
				bR = new Position(randomIndexBRX, randomIndexBRY);
		}
		try{
			Room r = new Room(uL, bR, gridHeight, gridWidth);
			boolean isValidR = r.isValidRoom(this.floorAndWallsTiles);
			int numFloor = 1;
			if(isValidR){
				for(Position  p : r.getWalls()){
					this.floorAndWallsTiles.add(p);					
				}
				int startX = r.getRoomUpperLeft().getX();
				int endX = r.getRoomBottomRight().getX();
				int startY = r.getRoomUpperLeft().getY();
				int endY = r.getRoomBottomRight().getY();
				Tile t;
				for(int i = startX; i<=endX; i++){
					for(int j=startY; j<=endY; j++){
						Position p = new Position(i,j);
						t = this.getTile(p);
						if(t!=null){
							t.setSymbol('.');
							this.floorTiles.add(p);
							System.out.println("Adding to floorTiles "+
								numFloor+": "+p.getX()+", "+p.getY());
							numFloor++;
							this.floorAndWallsTiles.add(p);
						}
					}
				}
				this.floorAndWallsTiles.addAll(r.getWalls());
				this.floorAndWallsTiles.addAll(r.getDoors());
				for(Position p : r.getDoors()){
					this.getTile(p).setSymbol('.');
				}
				return r;
			}
			else {
				System.out.println("Invalid room.");
				return null;
			}
		}
		catch(Exception e){
			System.out.println("Ex: ERROR creating room. Invalid Tile.");
			return null;
		}
	}

	public void addRoom(Room r){
		this.rooms.add(r);
	}

	public Tile[][] getGrid(){
		return this.grid;
	}

	public ArrayList<Room> getRooms(){
		return this.rooms;
	}	

	public ArrayList<Position> getRoomAndWallTiles(){
		return this.floorAndWallsTiles;
	}

	public ArrayList<Position> getFloorTiles(){
		return this.floorTiles;
	}

	public Tile getTile(Position p){
		return this.grid[p.getX()][p.getY()];
	}

	public int getNumLevel(){
		return this.index;
	}

/*	public void addRandomRoom(){

		int randomIndexULX = randomGenerator.nextInt(this.getGridWidth()-2)+1;
		int randomIndexULY = randomGenerator.nextInt(this.getGridHeight()-2)+1;
		int randomIndexBRX = randomIndexULX + randomGenerator.nextInt(6)+4;		
		int randomIndexBRY = randomIndexULY  + randomGenerator.nextInt(5)+2;
		Position uL = new Position(randomIndexULX,randomIndexULY);
		Position bR = new Position(randomIndexBRX, randomIndexBRY);
		while(!uL.isValidPositionForRoom() ||
			!bR.isValidPositionForRoom()){
				randomIndexULX = randomGenerator.nextInt(this.getGridWidth()-2)+1;
				randomIndexULY = randomGenerator.nextInt(this.getGridHeight()-2)+1;
				randomIndexBRX = randomIndexULX + randomGenerator.nextInt(6)+4;		
				randomIndexBRY = randomIndexULY  + randomGenerator.nextInt(5)+2;
				uL = new Position(randomIndexULX,randomIndexULY);
				bR = new Position(randomIndexBRX, randomIndexBRY);
		}

		Room r = createRoom(uL, bR);
	}*/


	// Gets a random empty floor Tile from floorTiles
	public Position getRandomFloorTile(Level l){
	 	Random randomGen = new Random();
	 	int indexValidTile = randomGen.nextInt(l.getFloorTiles().size()-1);
	 	Position posTileReturn = l.getFloorTiles().get(indexValidTile);
	 	// If is not empty, get another random floor tile
	 	while(!this.getTile(posTileReturn).isTileEmpty()){
	 		indexValidTile = randomGen.nextInt(floorTiles.size());
	 		posTileReturn = floorTiles.get(indexValidTile);
	 	}
	 	return posTileReturn;
	 }

	public void setStairsUp(){
		Position stairsUp = this.getRandomFloorTile(this);
		this.getTile(stairsUp).setDistStairsUp(0);
		this.getTile(stairsUp).setSymbol('<');
	}

	public void setStairsDown(){
		Position stairsDown = this.getRandomFloorTile(this);
		this.getTile(stairsDown).setDistStairsUp(0);
		this.getTile(stairsDown).setSymbol('>');
	}

	public int getGridHeight(){
		return this.gridHeight;
	}

	public int getGridWidth(){
		return this.gridWidth;
	}

}

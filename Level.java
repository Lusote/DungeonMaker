import java.util.ArrayList;
import java.util.Random;

/*
*	TODO:
*		getTile();
*/

public class Level{

	private int index;
	private int gridHeight;
	private int gridWidth;
	private Tile[][] grid; 
	private ArrayList<Room> rooms;
	private ArrayList<Tile> floorTiles;
	private ArrayList<Tile> floorAndWallsTiles;


	public Level(int i, int gH, int gW){
		this.rooms = new ArrayList<Room>();
		this.floorTiles = new ArrayList<Tile>();
		this.floorAndWallsTiles = new ArrayList<Tile>();
		this.index = i;
		this.gridHeight = gH;
		this.gridWidth = gW;
		this.grid = new Tile[gW][gH];
		for(int in = 0; in <gW ;in++){
		 	for(int j=0;j<gH;j++){
		 		this.grid[in][j] = new Tile();
		 	}
		 }
	}

	public Room createRoom(Tile upLeft, Tile upRight, 
							Tile downLeft, Tile downRight){
		try{
		boolean isValidR = (upLeft.isValidTileForRoom() &&
							upRight.isValidTileForRoom() &&
							downLeft.isValidTileForRoom() &&
							downRight.isValidTileForRoom());
		ArrayList<Tile>doors = new ArrayList<Tile>();
		Room r = new Room(upLeft, upRight, downLeft, downRight, 
							doors, this.getNumLevel(), this.getGrid());
		if(isValidR){
			int startX = upLeft.getPosition().getX();
			int endX = downRight.getPosition().getX();
			int startY = upLeft.getPosition().getY();
			int endY = downRight.getPosition().getY();
			Tile t;
			for(int i = startX; i<=endX; i++){
				for(int j=startY; j<=endY; j++){
					Position p = new Position(i,j);
					t = this.getTile(p);
					t.setSymbol('.');
					this.floorTiles.add(t);
					this.floorAndWallsTiles.add(t);
					// Already checked on the If condition.
					// if(t.isValidTileForRoom()){	
					// 	t.setSymbol('.');
					// }
					// else{
					// 	System.out.println("Level.createRoom: Invalid Tile for Room.");
					// 	break;
					// }
				}
			}
			this.floorAndWallsTiles.addAll(r.getWalls());
			return r;
		}
		else {
		System.out.println("Invalid room.");
		return null;
		}
		}
		catch(Exception e){
			System.out.println("ERROR creating room. Invalid Tile.");
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

	public ArrayList<Tile> getRoomAndWallTiles(){
		return this.floorAndWallsTiles;
	}

	// TODO: Return on fail?
	public Tile getTile(Position p){
		Tile toReturn = new Tile();
		if( 0 <= p.getX() && p.getX() < this.gridWidth &&
			0 <= p.getY() && p.getY() < this.gridHeight){
				return this.grid[p.getX()][p.getY()];
		}
		else{
			System.out.println("ERROR: Requesting invalid tile.");
			return null;
		}
	}

	public int getNumLevel(){
		return this.index;
	}

	// Returns a random floor tile (allegedly)
	 public Tile getRandomValidTile(Level l){
	 	Random randomGen = new Random();
	 	int indexValidTile = randomGen.nextInt(floorTiles.size());
	 	Tile tileReturn = floorTiles.get(indexValidTile);
	 	while(!tileReturn.isTileEmpty()){
	 		indexValidTile = randomGen.nextInt(floorTiles.size());
	 		tileReturn = floorTiles.get(indexValidTile);
	 	}
	 	return tileReturn;
	 }

	public void setStairsUp(){
		Tile stairsUp = this.getRandomValidTile(this);
		stairsUp.setDistStairsUp(0);
	}

	public void setStairsDown(){
		Tile stairsDown = this.getRandomValidTile(this);
		stairsDown.setDistStairsUp(0);
	}

	public int getGridHeight(){
		return this.gridHeight;
	}

	public int getGridWidth(){
		return this.gridWidth;
	}

}
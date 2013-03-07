import java.util.ArrayList;
import java.util.Random;

/*
*	TODO:
*		getTile();
*		createRoom(): Fail case. INCOMPLETE.
*/

public class Level{

	private Tile[][] grid; 
	private int gridHeight;
	private int gridWidth;
	private int index;
	private ArrayList<Tile> validTiles = new ArrayList<Tile>();
	private ArrayList<Room> rooms = new ArrayList<Room>();

	public Level(){
		this.rooms = new ArrayList<Room>();
		this.index = 0;
		this.gridHeight = 80;
		this.gridWidth = 25;
		this.grid = new Tile[gridHeight][gridWidth];
		 for(int i=0;i<80;i++){
		 	for(int j=0;j<25;j++){
		 		this.grid[i][j] = new Tile();
		 	}
		 }
	}

	public Level(ArrayList<Room> r, int i, int gH, int gW, Tile[][] tiles){
		this.rooms = r;
		this.index = i;
		this.gridHeight = gH;
		this.gridWidth = gW;
		this.grid = tiles;
	}

	// Check in case of invalid tiles.
	// Deprecated and incomplete. Needs to return a Room
	public void createRoom(Position upLeft, Position downRight){
		ArrayList<Position>doors = new ArrayList<Position>();
		Room r = new Room(upLeft, downRight, doors, this.getNumLevel());
		if(r.isValidRoom()){
			this.rooms.add(r);
			int startX = upLeft.getX();
			int endX= downRight.getX();
			int startY = upLeft.getY();
			int endY = downRight.getY();
			Tile t;
			boolean isValidR = r.isValidRoom();
			for(int i = startX; i<=endX; i++){
				for(int j=startY; j<=endY; j++){
					Position p = new Position(i,j);
					t = this.getTile(p);
					if(t.isValidTileForRoom()){	
						t.setSymbol('.');
					}
					else{
						System.out.println("Level.createRoom: Invalid Tile for Room.");
						break;
					}
				}
			}
		}
		else {
		System.out.println("Invalid room.");
		}
	}

	// TODO: Return on fail?
	public Tile getTile(Position p){
		Tile toReturn = new Tile();
		if( 0 <= p.getX() && p.getX() <= this.gridHeight &&
			0 <= p.getY() && p.getY() <= this.gridHeight){
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
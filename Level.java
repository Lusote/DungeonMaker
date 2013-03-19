import java.util.ArrayList;
import java.util.Random;

/*
*	TODO:
*		getTile();
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


	public Level(int i, int gH, int gW){
		System.out.println("Creating level: "+i);
		System.out.println("GH : "+gH);
		System.out.println("GW : "+gW);
		System.out.println("");
		this.rooms = new ArrayList<Room>();
		this.floorTiles = new ArrayList<Position>();
		this.floorAndWallsTiles = new ArrayList<Position>();
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

	public Room createRoom(Tile upLeft, Tile upRight, Tile downLeft, Tile downRight){
		try{
			//Needs to complete doors.
			Room r = new Room(upLeft.getPosition(), upRight.getPosition(),
							downLeft.getPosition(), downRight.getPosition(),
							this.getNumLevel());
			boolean isValidR = r.isValidRoom(this.floorAndWallsTiles);
			int numFloor = 1;
			if(isValidR){
				for(Position  p : r.getWalls()){
					this.floorAndWallsTiles.add(p);					
				}
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
						this.floorTiles.add(p);
						System.out.println("Adding to floorTiles "+numFloor+": "+p.getX()+", "+p.getY());
						numFloor++;
						this.floorAndWallsTiles.add(p);
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

	public int printRandomRoom(){

		int randomIndexULX = randomGenerator.nextInt(this.getGridWidth()-2)+1;
		int randomIndexULY = randomGenerator.nextInt(this.getGridHeight()-2)+1;
		int randomIndexBRX = randomIndexULX + randomGenerator.nextInt(6)+4;		
		int randomIndexBRY = randomIndexULY  + randomGenerator.nextInt(5)+2;
		Position uL = new Position(randomIndexULX,randomIndexULY);
		Position bR = new Position(randomIndexBRX, randomIndexBRY);
		Position bL = new Position(randomIndexULX, randomIndexBRY);
		Position uR = new Position(randomIndexBRX, randomIndexULY);

			Room r = createRoom(getTile(uL), getTile(uR), getTile(bL), getTile(bR)
					);
		return 1;
	}

	// Returns a random empty floor tile (allegedly)
	public Position getRandomValidTile(Level l){
	 	Random randomGen = new Random();
	 	int indexValidTile = randomGen.nextInt(floorTiles.size());
	 	Position posTileReturn = floorTiles.get(indexValidTile);
	 	while(!this.getTile(posTileReturn).isTileEmpty()){
	 		indexValidTile = randomGen.nextInt(floorTiles.size());
	 		posTileReturn = floorTiles.get(indexValidTile);
	 	}
	 	return posTileReturn;
	 }

	public void setStairsUp(){
		Position stairsUp = this.getRandomValidTile(this);
		this.getTile(stairsUp).setDistStairsUp(0);
		this.getTile(stairsUp).setSymbol('<');
	}

	public void setStairsDown(){
		Position stairsDown = this.getRandomValidTile(this);
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

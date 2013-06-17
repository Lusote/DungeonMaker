import java.util.*;
import java.util.Random;

/*
*	TODO:
*		With corridors it shall work like this:
*			1.- Create Room
*			2.- Connect with previous room (if any)
*				2.1.- If it can't, delete room and previous room. 
*				2.2.- GOTO 1
*			3.- Repeat until we have X rooms.
*			4.- Connect last room with first room.
*/

public class Level{

	private int index;
	private int gridHeight;
	private int gridWidth;
	private Tile[][] grid; 
	private ArrayList<Room> rooms;
	private Set<Position> floorPositions;
	private Set<Position> floorAndWallsPositions;
    private static Random randomGenerator = new Random();


	public Level(int ind, int gH, int gW){
		this.rooms = new ArrayList<Room>();
		this.floorPositions = new HashSet<Position>();
		this.floorAndWallsPositions = new HashSet<Position>();
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
		Tile t;
		//int numFloor=0;
		// Positions for room creation.
		ArrayList<Position> roomPos = getRoomPositions();
		// Now we check the tiles the room will use.
		//System.out.println("Level.createRoom calling getSolidSquare");
		ArrayList<Position> roomWallsAndFloor  = roomPos.get(0).getPositionNW().getPositionNW().getSolidSquare(roomPos.get(1).getPositionSE().getPositionSE());
		for(Position p : roomWallsAndFloor){
			if(p.isPositionUsed(this.getFloorAndWallsPosition())
				){
				System.out.println("ERROR Creating Room: Position already used.");
				return null;
			}
		}
		// At this point, the Room is valid.
		Room r = new Room(roomPos.get(0),roomPos.get(1));
		// We set the symbol on the floor tiles.
		for(Position p : r.getFloor()){
			t = getTile(p);
			if(t!=null){
				t.setSymbol('.');
				/*System.out.println("Adding to floorTiles "+
				numFloor+": "+p.getX()+", "+p.getY());
				numFloor++;*/
				addFloorPosition(p);
				addFloorOrWallsPosition(p);
			}
		}
		// We add the tiles to the Sets
		this.floorAndWallsPositions.addAll(r.getWalls());
		this.floorAndWallsPositions.addAll(r.getDoors());
		for(Position p : r.getWalls()){
			this.getTile(p).setSymbol('W');
		}
		// Change the doors tiles symbols.
		for(Position p : r.getDoors()){
			this.getTile(p).setSymbol('d');
		}
		addRoom(r);
		return r;
	}

	// Returns uL and bR
	public ArrayList<Position> getRoomPositions(){
		ArrayList<Position> toReturn = new ArrayList<Position>();
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
		toReturn.add(uL);
		toReturn.add(bR);
		return toReturn;
	}

	public void addRoom(Room r){
		this.rooms.add(r);
	}

	public int getNumRooms(){
		return this.getRooms().size();
	}

	public Tile[][] getGrid(){
		return this.grid;
	}

	public ArrayList<Room> getRooms(){
		return this.rooms;
	}	

	public Set<Position> getFloorAndWallsPosition(){
		return this.floorAndWallsPositions;
	}

	public void addFloorOrWallsPosition(Position p){
		this.floorAndWallsPositions.add(p);
	}

	public Set<Position> getFloorPositions(){
		return this.floorPositions;
	}

	public void addFloorPosition(Position p){
		this.floorPositions.add(p);
	}

	public Tile getTile(Position p){
		return this.grid[p.getX()][p.getY()];
	}

/*	public ArrayList<Tile> getTileFourNeighbours(Tile t){
		Position p = t.getPosition();
		ArrayList<Tile> toReturn = new ArrayList<Tile>();
		for(Position pos : p.getFourNeighbours()){
			if(pos.isValidPosition())
				toReturn.add(getTile(pos));
		}
		return toReturn;
	}
	
	public ArrayList<Tile> getTileEightNeighbours(Tile t){
		Position p = t.getPosition();
		ArrayList<Tile> toReturn = new ArrayList<Tile>();
		for(Position pos : p.getEightNeighbours()){
			if(pos.isValidPosition())
				toReturn.add(getTile(pos));
		}
		return toReturn;
	}
*/
	public int getNumLevel(){
		return this.index;
	}

	// Gets a random empty floor Tile from floorTiles
	public Position getRandomFloorPosition(Level l){
		int size = l.getFloorPositions().size();
		int item = new Random().nextInt(size);
		int i = 0;
		for(Position p : l.getFloorPositions()){
			if (i == item){
				if(!this.getTile(p).isTileEmpty()){
					return p;
				}
				i=i++;
			}
		}
		return null;
	 }

	public void setStairsUp(){
		Position stairsUp = this.getRandomFloorPosition(this);
		this.getTile(stairsUp).setDistStairsUp(0);
		this.getTile(stairsUp).setSymbol('<');
	}

	public void setStairsDown(){
		Position stairsDown = this.getRandomFloorPosition(this);
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

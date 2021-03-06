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
	private HashSet<Position> floorPositions;
	private HashSet<Position> floorAndWallsPositions;
    private static Random randomGenerator = new Random();
    private Position stairsUp;
    private Position stairsDown;


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
		 this.stairsDown = null;
		 this.stairsUp = null;
	}

	public Room createRoom(){
		Tile t;
		// Positions for room creation.
		HashSet<Position> used = this.getFloorAndWallsPosition();
		ArrayList<Position> roomPos = this.getRoomPositions(used);
		Room r = new Room(roomPos.get(0),roomPos.get(1));
		// We set the symbol on the floor tiles.
		for(Position p : r.getFloor()){
			t = getTile(p);
			if(t!=null){
				t.setSymbol('.');
				addFloorPosition(p);
				addFloorOrWallsPosition(p);
			}
		}
		// We add the tiles to the Sets
		this.floorAndWallsPositions.addAll(r.getWalls());
	//	this.floorAndWallsPositions.addAll(r.getDoors());
		for(Position p : r.getWalls()){
			this.getTile(p).setSymbol('#');
		}
		// Change the doors tiles symbols.
		for(Position p : r.getDoors()){
			this.getTile(p).setSymbol('d');
		}
		addRoom(r);
		return r;
	}

	public void deleteRoom(int indexRoom){
		Room toDelete = this.getRooms().get(indexRoom);
		ArrayList<Position> floor = toDelete.getFloor();
		ArrayList<Position> walls = toDelete.getWalls();
		this.getFloorPositions().removeAll(floor);
		this.getFloorAndWallsPosition().removeAll(floor);
		this.getFloorAndWallsPosition().removeAll(walls);
		this.getRooms().remove(indexRoom);
	}

	// Returns uL and bR
	public ArrayList<Position> getRoomPositions(HashSet<Position> invalid){
		boolean finded = false;
		ArrayList<Position> toReturn = new ArrayList<Position>();
		while(!finded){
			toReturn.clear();
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

			// Now we check the tiles the room will use.
			// roomWallsAndFloor is the room plus two extra tiles on every direction
			ArrayList<Position> roomWallsAndFloor  = uL.getPositionNW().getPositionNW().getPositionNW().getSolidSquare(bR.getPositionSE().getPositionSE().getPositionSE());
			finded = true;
			for(Position p : roomWallsAndFloor){
				// Checks with ALL the positions used on the level.
				if(invalid.contains(p)){
					System.out.println("ERROR Creating Room: Position already used.");
					finded = false;
					break;
				}
			}
		}
			return toReturn;
	}

	// Returns the path between two positions
	public ArrayList<Position> getPath(Position start, Position end, int numNeighbors){
		ArrayList<Position> toReturn = Astar.getPath(start, end, numNeighbors, 
			this.getFloorAndWallsPosition());
		for(Position p : toReturn){
			this.addFloorPosition(p);
			this.getFloorAndWallsPosition().addAll(p.getNeighbors(8));
		}
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

	public HashSet<Position> getFloorAndWallsPosition(){
		return this.floorAndWallsPositions;
	}

	public void addFloorOrWallsPosition(Position p){
		this.floorAndWallsPositions.add(p);
	}

	public HashSet<Position> getFloorPositions(){
		return this.floorPositions;
	}

	public void addFloorPosition(Position p){
		this.floorPositions.add(p);
	}

	public Tile getTile(Position p){
		return this.grid[p.getX()][p.getY()];
	}

	public int getNumLevel(){
		return this.index;
	}

	// Gets a random empty floor Tile from floorTiles
	public Position getRandomFloorPosition(){
		ArrayList<Position> tiles = new ArrayList<Position>(this.getFloorPositions());
		int numPos = tiles.size();
		int randomPos = randomGenerator.nextInt(numPos);
		return tiles.get(randomPos);

	/*	HashSet<Position> avaliablePos = this.getFloorPositions();
		int numPos = avaliablePos.size();
		int randomPos = randomGenerator.nextInt(numPos);
		Iterator iter = avaliablePos.iterator();
		int i=0;
		while(iter.hasNext()){
			if(i == randomPos) 
				return (Position)iter.next();
			i++;
		}
		return null;*/
	}

	public Position getStairsUp(){
		return this.stairsUp;
	}

	// Needs to check for used
	public void setStairsUp(){
		if(this.getStairsUp() == null){
			Position stairsUp = this.getRandomFloorPosition();
			this.getTile(stairsUp).setSymbol('<');
		}
	}

	public Position getStairsDown(){
		return this.stairsDown;
	}

	// Needs to check for used
	public void setStairsDown(){
		if(this.getStairsDown() == null){
			Position stairsDown = this.getRandomFloorPosition();
			this.getTile(stairsDown).setSymbol('>');
		}
	}

	public int getGridHeight(){
		return this.gridHeight;
	}

	public int getGridWidth(){
		return this.gridWidth;
	}

}

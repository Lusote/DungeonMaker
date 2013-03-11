import java.util.ArrayList;
import java.util.Set;

/**
*	TODO:
*		addWalls(): I need a more efficient way to do that.
*/

public class Room{

	private int level;
	private Tile upLeft;
	private Tile bottomLeft;
	private Tile upRight;
	private Tile bottomRight;
	private ArrayList<Tile> doors = new ArrayList<Tile>();
	private ArrayList<Tile> walls = new ArrayList<Tile>();	

	public Room(Tile uL, Tile uR, Tile bL, Tile bR,
				 ArrayList<Tile> d, int lev, Tile[][] grid){	
		this.upLeft = uL;
		this.bottomLeft = bL;
		this.upRight = uR;
		this.bottomRight = bR;
		this.doors = d;
		this.level = lev;
		this.walls = addWalls(uL,uR,bL,bR, grid);
	}

	// I need a more efficient way. For now, this will do the trick.
	public ArrayList<Tile> addWalls(Tile uL,Tile uR,
										Tile bL,Tile bR, Tile [][] grid){
		int i;
		int ini;
		int fin;
		int j;
		Tile aux;
		ArrayList<Tile> toReturn = new ArrayList<Tile>();

		ini = uL.getPosition().getPositionNW().getX();
		fin = uR.getPosition().getPositionNE().getX();
		j = uL.getPosition().getPositionNW().getY();
		for(i = ini; i <= fin ; i++){
			aux = grid[i][j];
			toReturn.add(aux);
		}
		ini = uR.getPosition().getPositionE().getY();
		fin = bR.getPosition().getPositionSE().getY();
		j = uR.getPosition().getPositionE().getX();
		for(i = ini; i <= fin; i++){
			aux = grid[j][i];
			toReturn.add(aux);
		}
		ini = bR.getPosition().getPositionS().getX();
		fin = bL.getPosition().getPositionSW().getX();
		j = bR.getPosition().getPositionS().getY();
		for(i = ini; i >= fin; i--){
			aux = grid[i][j];
			toReturn.add(aux);
		}
		ini = bL.getPosition().getPositionW().getY();
		fin = uL.getPosition().getPositionW().getY();
		j = bL.getPosition().getPositionW().getX();
		for(i = ini; i >= fin; i--){
			aux = grid[j][i];
			toReturn.add(aux);
		}
		return toReturn;
	}

	// More efficient?
	// otherrooms == all tiles aalready used
	public boolean isValidRoom(ArrayList<Tile> otherRooms){
		// if(this.isRoomOverlapping(otherRooms)){

		// }
		if(this.getRoomUpperLeft().isValidTileForRoom() &&
			this.getRoomUpperRight().isValidTileForRoom() &&
			this.getRoomBottonLeft().isValidTileForRoom() &&
			this.getRoomBottonRight().isValidTileForRoom()
			){
			return true;
		}
		return false;
	}

	public ArrayList<Tile> getWalls(){
		return this.walls;
	}

	public int getRoomLevel(){
		return this.level;
	}

	public Tile getRoomUpperLeft(){
		return this.upLeft;
	}

	public Tile getRoomUpperRight(){
		return this.upRight;
	}

	public Tile getRoomBottonLeft(){
		return this.bottomLeft;
	}

	public Tile getRoomBottonRight(){
		return this.bottomRight;
	}

}
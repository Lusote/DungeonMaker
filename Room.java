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
		Tile aux ;
		ArrayList<Tile> toReturn = new ArrayList<Tile>();

		ini = uL.getPosition().getPositionNW().getX();
		fin = uR.getPosition().getPositionNE().getX();
		for(i = ini; i <= fin ; i++){
			aux = grid[i][uL.getPosition().getY()+1];
			toReturn.add(aux);
		}
		ini = uR.getPosition().getPositionE().getY();
		fin = bR.getPosition().getPositionSE().getY();
		for(i = ini; i <= fin; i++){
			aux = grid[uR.getPosition().getX()+1][i];
			toReturn.add(aux);
		}
		ini = bR.getPosition().getPositionS().getX();
		fin = bL.getPosition().getPositionSW().getX();
		for(i = ini; i <= fin; i--){
			aux = grid[i][bR.getPosition().getY()+1];
			toReturn.add(aux);
		}
		ini = bL.getPosition().getPositionW().getY();
		fin = uL.getPosition().getPositionW().getY();
		for(i = ini; i <= fin; i--){
			aux = grid[uL.getPosition().getX()][i];
			toReturn.add(aux);
		}
		return toReturn;
	}

	// More efficient?
	// otherrooms == all tiles aalready used
	public boolean isValidRoom(ArrayList<Tile> otherRooms){
		// if(this.isRoomOverlapping(otherRooms)){

		// }
		if(!this.getRoomUpperLeft().isValidTileForRoom()){
			return false;
		}
		if(!this.getRoomUpperRight().isValidTileForRoom()){
			return false;
		}
		if(!this.getRoomBottonLeft().isValidTileForRoom()){
			return false;
		}
		if(!this.getRoomBottonRight().isValidTileForRoom()){
			return false;
		}
		return true;
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
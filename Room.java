import java.util.ArrayList;
import java.util.Set;

/**
*	TODO:
*		addWalls(): I need a more efficient way to do that.
*/

public class Room{

	private Position upLeft;
	private Position bottomLeft;
	private Position upRight;
	private Position bottomRight;
	private int level;
	private ArrayList<Position> doors = new ArrayList<Position>();
	private ArrayList<Position> walls = new ArrayList<Position>();	

	public Room(Position uL, Position uR, Position bL, Position bR,
				 ArrayList<Position> d, int lev){	
		this.upLeft = uL;
		this.bottomLeft = bL;
		this.upRight = uR;
		this.bottomRight = bR;
		this.doors = d;
		this.level = lev;
		walls = addWalls(uL,uR,bL,bR);
	}

	public Room(Position upLeft, Position downRight, ArrayList<Position> d, int lev){
		this.upLeft = upLeft;
		this.bottomLeft = new Position(upLeft.getX(), downRight.getY());
		this.upRight = new Position(downRight.getX(), upLeft.getY());
		this.bottomRight = downRight;
		this.doors = d;
		this.level = lev;
	}

	// I need a more efficient way. For now, this will do the trick.
	public ArrayList<Position> addWalls(Position uL,Position uR,
										Position bL,Position bR){
		Position northWest = uL.getPositionNW();
		Position northEast = uR.getPositionNE();
		Position southWest = bL.getPositionSW();
		Position southEast = bR.getPositionSE();
		Position p;
		ArrayList<Position> toReturn = new ArrayList<Position>();
		for (int i = northWest.getX(); i <= northEast.getX() ; i++) {
			p = new Position(i, northWest.getY());
			toReturn.add(p);
		}
		for (int i = northEast.getPositionS().getY(); i <= southEast.getY() ; i++) {
			p = new Position(northEast.getX(), i);
			toReturn.add(p);
		}
		for (int i = southEast.getPositionW().getX(); i <= southWest.getX() ; i++) {
			p = new Position(i, southWest.getY());
			toReturn.add(p);
		}
		for (int i = southWest.getPositionN().getY(); i <= northWest.getY() ; i++) {
			p = new Position(southWest.getX(), i);
			toReturn.add(p);
		}
		return toReturn;
	}

	public boolean isValidRoom(){
		if(!this.getRoomUpperLeft().isValidPositionForRoom()){
			return false;
		}
		if(!this.getRoomUpperRight().isValidPositionForRoom()){
			return false;
		}
		if(!this.getRoomBottonLeft().isValidPositionForRoom()){
			return false;
		}
		if(!this.getRoomBottonRight().isValidPositionForRoom()){
			return false;
		}
		return true;
	}

	public int getRoomLevel(){
		return this.level;
	}

	public Position getRoomUpperLeft(){
		return this.upLeft;
	}

	public Position getRoomUpperRight(){
		return this.upRight;
	}

	public Position getRoomBottonLeft(){
		return this.bottomLeft;
	}

	public Position getRoomBottonRight(){
		return this.bottomRight;
	}

}
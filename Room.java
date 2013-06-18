import java.util.*;
   
/*
*	TODO:
*/

public class Room{

	private Position upLeft;
	private Position bottomLeft;
	private Position upRight;
	private Position bottomRight;
	private static Random randomGenerator = new Random();
	private ArrayList<Position> doors = new ArrayList<Position>();
	private ArrayList<Position> walls = new ArrayList<Position>();


	public Room(Position uL, Position bR){
		this.upLeft  = uL;
		this.bottomRight = bR;
		this.bottomLeft = new Position(uL.getX(),bR.getY());
		this.upRight = new Position(bR.getX(),uL.getY());
		this.walls = uL.getPositionNW().getSquare(bR.getPositionSE());
		//addDoors() adds to this.walls the tiles needed
		this.doors = addDoors(this.walls);
	}

	public ArrayList<Position> addDoors(ArrayList<Position> walls){
		ArrayList<Position> toReturn = new ArrayList<Position>();
		Position p,p2;
		int randomIndex = randomGenerator.nextInt(walls.size()-1);
		while(toReturn.size()!=2){
			p = walls.get(randomIndex);
			// Is valid and it's not a corner
			//System.out.println("Checking for corners...");
			if(p.isValidPositionForDoor()	  &&
				((walls.contains(p.getPositionN()) &&
				 walls.contains(p.getPositionS())) 
				||	
				(walls.contains(p.getPositionE()) &&
				 walls.contains(p.getPositionW())))				 
			  ){
				if(toReturn.size()==0){
					if(p.isValidPositionForDoor()){
						toReturn.add(p);
					}
				}
				else{
					// Avoids having two doors together.
					p2 = toReturn.get(0);
					if(!p2.getNeighbors(4).contains(p) && p!=p2){
						if(p.isValidPositionForDoor()){
							toReturn.add(p);
						}
					}
				}
			}
			randomIndex = randomGenerator.nextInt(walls.size()-1);
		}
		return toReturn;
	}

	public ArrayList<Position> getFloor(){
		ArrayList<Position> toReturn = new ArrayList<Position>();
		toReturn.addAll(this.getRoomUpperLeft().getSolidSquare(this.getRoomBottomRight()));
		return toReturn;
	}

	public void addWallPosition(Position p){
		ArrayList<Position> walls = this.getWalls();
		walls.add(p);
	}

	public ArrayList<Position> getWalls(){
		return this.walls;
	}

	public ArrayList<Position> getDoors(){
		return this.doors;
	}

	public Position getRoomUpperLeft(){
		return this.upLeft;
	}

	public Position getRoomUpperRight(){
		return this.upRight;
	}

	public Position getRoomBottomLeft(){
		return this.bottomLeft;
	}

	public Position getRoomBottomRight(){
		return this.bottomRight;
	}

}

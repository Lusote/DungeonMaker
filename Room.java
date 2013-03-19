import java.util.ArrayList;
import java.util.Random;

/**
*	TODO:
*		addWalls(): I need a more efficient way to do that.
*/

public class Room{

	private int level;
	private Position upLeft;
	private Position bottomLeft;
	private Position upRight;
	private Position bottomRight;
    private static Random randomGenerator = new Random();
	private ArrayList<Position> doors = new ArrayList<Position>();
	private ArrayList<Position> walls = new ArrayList<Position>();	


	// The corner Positions MUST be corrrect. 
	//	That is, this  method doesn't check it.
	public Room(Position uL, Position uR, Position bL, Position bR, int lev){	
		this.upLeft = uL;
		this.bottomLeft = bL;
		this.upRight = uR;
		this.bottomRight = bR;
		this.level = lev;
		this.walls = addWalls(uL,uR,bL,bR);
		this.doors = addDoors(this.walls);
	}

	public ArrayList<Position> addDoors(ArrayList<Position> walls){
		ArrayList<Position> toReturn = new ArrayList<Position>();
		Position p,p2;
		int randomIndex = randomGenerator.nextInt(walls.size()-1);
		while(toReturn.size()!=2){
			p = walls.get(randomIndex);
			if(p.isValidPositionForDoor()	  &&
				(walls.contains(p.getPositionN()) &&
				 walls.contains(p.getPositionS())) 
				||	
				(walls.contains(p.getPositionE()) &&
				 walls.contains(p.getPositionW()))				 
			  ){
				if(toReturn.size()==0){
					toReturn.add(walls.get(randomIndex));
				}
				else{
					p2 = toReturn.get(0);
					if(!p2.getFourNeighbours().contains(p)){
						toReturn.add(walls.get(randomIndex));
					}
				}
			}
			randomIndex = randomGenerator.nextInt(walls.size()-1);
		}
		return toReturn;
	}

	// I need a more efficient way. For now, this will do the trick.
	public ArrayList<Position> addWalls(Position uL,Position uR,Position bL,Position bR){
		int i;
		int ini;
		int fin;
		int j;
		int numWalls = 1;
		ArrayList<Position> toReturn = new ArrayList<Position>();

		ini = uL.getPositionNW().getX();
		fin = uR.getPositionNE().getX();
		j = uL.getPositionNW().getY();
		for(i = ini; i <= fin ; i++){
			//System.out.println("Adding Wall "+numWalls+": "+i+", "+j);
			numWalls++;
			toReturn.add(new Position(i,j));
		}
		ini = uR.getPositionE().getY();
		fin = bR.getPositionSE().getY();
		j = uR.getPositionE().getX();
		for(i = ini; i <= fin; i++){
			numWalls++;
			toReturn.add(new Position(j,i));
		}
		ini = bR.getPositionS().getX();
		fin = bL.getPositionSW().getX();
		j = bR.getPositionS().getY();
		for(i = ini; i >= fin; i--){
			numWalls++;
			toReturn.add(new Position(i,j));
		}
		ini = bL.getPositionW().getY();
		fin = uL.getPositionW().getY();
		j = bL.getPositionW().getX();
		for(i = ini; i >= fin; i--){
			numWalls++;
			toReturn.add(new Position(j,i));
		}
		return toReturn;
	}

	// More efficient?
	public boolean isValidRoom(ArrayList<Position> floorAndWallsUsed){
		boolean ret = false;
		if(this.getRoomUpperLeft().isValidPositionForRoom() &&
			this.getRoomUpperRight().isValidPositionForRoom() &&
			this.getRoomBottomLeft().isValidPositionForRoom() &&
			this.getRoomBottomRight().isValidPositionForRoom() &&
			!this.isRoomOverlapping(floorAndWallsUsed)
			){
				ret = true;
		}
		return ret;
	}

	// Needs a better way. But works.
	public boolean isRoomOverlapping(ArrayList<Position> floorAndWallsUsed){
		int ini, fin, i, j;
		ArrayList<Position> borderFloor = new ArrayList<Position>();

		ini = this.getRoomUpperLeft().getX();
		fin = this.getRoomUpperRight().getX();
		j   = this.getRoomUpperLeft().getY();
		for(i = ini; i<= fin; i++){
			borderFloor.add(new Position(i,j));
		}

		ini = this.getRoomUpperRight().getY();
		fin = this.getRoomBottomRight().getY();
		j   = this.getRoomUpperRight().getX();
		for(i = ini; i <= fin; i++){
			borderFloor.add(new Position(j,i));
		}

		ini = this.getRoomBottomRight().getX();
		fin = this.getRoomBottomLeft().getX();
		j = this.getRoomBottomRight().getY();
		for(i = ini; i >= fin; i--){
			borderFloor.add(new Position(i,j));
		}

		ini = this.getRoomBottomLeft().getY();
		fin = this.getRoomUpperLeft().getY();
		j = this.getRoomBottomLeft().getX();
		for(i = ini; i >= fin; i--){
			borderFloor.add(new Position(j,i));
		}

		for (Position p : floorAndWallsUsed) {
			for (Position p2 : borderFloor){
				if( p.equals(p2)){
					System.out.println("ERROR: Invalid room: overlaps.");
					return true;
				}
			}
		}
		return false;
	}

	public ArrayList<Position> getWalls(){
		return this.walls;
	}

	public ArrayList<Position> getDoors(){
		return this.doors;
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

	public Position getRoomBottomLeft(){
		return this.bottomLeft;
	}

	public Position getRoomBottomRight(){
		return this.bottomRight;
	}

}
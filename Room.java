import java.util.ArrayList;
import java.util.Random;
   
/*
*	TODO:
*		addWalls(): I need a more efficient way to do that.
*/

public class Room{

	private Position upLeft;
	private Position bottomLeft;
	private Position upRight;
	private Position bottomRight;
	private static Random randomGenerator = new Random();
	private ArrayList<Position> doors = new ArrayList<Position>();
	private ArrayList<Position> walls = new ArrayList<Position>();

	private static int gridWidth = 79;
	private static int gridHeight = 24;	


	public Room(Position uL, Position bR){	
		int randomIndexULX = randomGenerator.nextInt(gridWidth-3)+3;
		int randomIndexULY = randomGenerator.nextInt(gridHeight-3)+3;
		int randomIndexBRX = randomIndexULX + randomGenerator.nextInt(6)+4;		
		int randomIndexBRY = randomIndexULY  + randomGenerator.nextInt(5)+2;
		this.upLeft  = uL;
		this.bottomRight = bR;
		this.bottomLeft = new Position(uL.getX(),bR.getY());
		this.upRight = new Position(bR.getX(),uL.getY());
		this.walls = this.upLeft.getSquare(bottomRight);
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
					if(walls.get(randomIndex).isValidPositionForDoor()){
						toReturn.add(walls.get(randomIndex));
					}
				}
				else{
					p2 = toReturn.get(0);
					if(!p2.getFourNeighbours().contains(p)){
						if(walls.get(randomIndex).isValidPositionForDoor()){
							toReturn.add(walls.get(randomIndex));
						}
					}
				}
			}
			randomIndex = randomGenerator.nextInt(walls.size()-1);
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

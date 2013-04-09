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


	public Room(Position uL, Position bR, int gridH, int gridW){
		this.upLeft  = uL;
		this.bottomRight = bR;
		this.bottomLeft = new Position(uL.getX(),bR.getY());
		this.upRight = new Position(bR.getX(),uL.getY());
		this.doors = addDoors(this.upLeft.getSquare(bottomRight));
		this.walls = addWalls();
	}

	public ArrayList<Position> addWalls(){
		ArrayList<Position> toReturn = new ArrayList<Position>();
		toReturn.addAll(this.upLeft.getSquare(bottomRight));
		for(Position p : this.getDoors()){
			toReturn.addAll(p.getEightNeighbours());
		}
		return toReturn;
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
				(walls.contains(p.getPositionN()) &&
				 walls.contains(p.getPositionS())) 
				||	
				(walls.contains(p.getPositionE()) &&
				 walls.contains(p.getPositionW()))				 
			  ){
				if(toReturn.size()==0){
					if(walls.get(randomIndex).isValidPositionForDoor()){
						//System.out.println("We got a first door.");
						toReturn.add(walls.get(randomIndex));
					}
				}
				else{
					// Avoids having two doors together.
					p2 = toReturn.get(0);
					if(!p2.getFourNeighbours().contains(p) && walls.get(randomIndex)!=p2){
						if(walls.get(randomIndex).isValidPositionForDoor()){
							//System.out.println("We got a second door.");
							toReturn.add(walls.get(randomIndex));
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
		int startX = this.getRoomUpperLeft().getX();
		int endX   = this.getRoomBottomRight().getX();
		int startY = this.getRoomUpperLeft().getY();
		int endY   = this.getRoomBottomRight().getY();
		Tile t;
		for(int i = startX; i<=endX; i++){
			for(int j=startY; j<=endY; j++){
				toReturn.add(new Position(i,j));
			}
		}
		return toReturn;
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

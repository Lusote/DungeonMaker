import java.util.ArrayList;
import java.util.Set;

public class Room{

	private int upX;
	private int bottomX;
	private int leftY;
	private int rightY;
	private int level;
	private ArrayList<Tile> doors = new ArrayList<Tile>();	

	public Room(int uX, int bX, int lY, int rY, ArrayList<Tile> d, int lev){	
		this.upX = uX;
		this.bottomX = bX;
		this.leftY = lY;
		this.rightY = rY;
		this.doors = d;
		this.level = lev;
	}
	public Room(Position upLeft, Position downRight, ArrayList<Tile> d, int lev){
		this.upX = upLeft.getX();
		this.bottomX = downRight.getX();
		this.leftY = upLeft.getY();
		this.rightY = downRight.getY();
		this.doors = d;
		this.level = lev;
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
		Position p = new Position(this.upX, this.leftY);
		return p;
	}

	public Position getRoomUpperRight(){
		Position p = new Position(this.upX, this.leftY);
		return p;
	}

	public Position getRoomBottonLeft(){
		Position p = new Position(this.bottomX, this.leftY);
		return p;
	}

	public Position getRoomBottonRight(){
		Position p = new Position(this.bottomX, this.rightY);
		return p;
	}

}
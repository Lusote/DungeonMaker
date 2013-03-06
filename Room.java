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
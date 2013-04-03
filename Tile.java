import java.util.ArrayList;

/*
*	TODO:
*		Another constructor, complete, if possible.
*		Test getNeighbours.
*		Validtile: adapt methods to Position methods
*/

public class Tile{

	private boolean isExplored;
	private boolean isOnview;
	private ArrayList<Object> thingsOnTile; 
	private int distStairsUp;
	private int distStairsDown;
	private char symbol;
	private Position pos;
	private int level;

	// Test constructor. Not really viable
	public Tile(){
		this.isExplored = false;
		this.isOnview = false;
		this.thingsOnTile = new ArrayList<Object>();
		this.distStairsDown = 0;
		this.distStairsUp = 0;
		this.symbol = 'E';
		this.pos = null;
		this.level = 0;
	}

	// The actual constructor.
	public Tile(boolean explored, boolean view, ArrayList<Object> things,
				int distDown, int distUp, char symb, Position p, int lev){
		this.isExplored = explored;
		this.isOnview = view;
		this.thingsOnTile = new ArrayList<Object>();
		this.distStairsDown = distDown;
		this.distStairsUp = distUp;
		this.symbol = symb;
		this.pos = p;
		this.level = lev;
	}

	public Position getPosition(){
		return this.pos;
	}

	public void setPosition(Position p){
		this.pos = p;
	}

	public boolean getIsExplored(){
		return this.isExplored;
	}

	public void setExplored(boolean value){
		this.isExplored = value;
	}

	public char getSymbol(){
		return this.symbol;
	}

	public void setSymbol(char givenSymbol){
		this.symbol=givenSymbol;
	}

	public boolean getIsOnView(){
		return this.isOnview;
	}

	public void setOnView(boolean value){
		this.isOnview = value;
	}

	public void putOnTile(Object thing){
		this.thingsOnTile.add(thing);
	}

	public ArrayList getThingsOnTile(){
		return this.thingsOnTile;
	}

	public boolean isTileEmpty(){
		return this.thingsOnTile.isEmpty();
	}

	public int getDistStairsUp(){
		return this.distStairsUp;
	}

	public void setDistStairsUp(int dist){
		this.distStairsUp = dist;
	}

	public int getDistStairsDown(){
		return this.distStairsDown;
	}

	public void setDistStairsDown(int dist){
		this.distStairsDown = dist;
	}

}

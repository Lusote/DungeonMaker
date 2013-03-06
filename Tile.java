import java.util.ArrayList;

/*
*	TODO:
*		Another constructor, complete, if possible.
*		Test getNeighbours.
*/

public class Tile{

	private boolean isExplored;
	private boolean isOnview;
	private ArrayList<Object> thingsOnTile; 
	private int distStairsUp;
	private int distStairsDown;
	private char symbol;
	private Position pos;

	// This needs to be completed
	public Tile(){
		this.isExplored = false;
		this.isOnview = false;
		this.thingsOnTile = new ArrayList<Object>();
		this.distStairsDown = 0;
		this.distStairsUp = 0;
		this.symbol = 'E';
		this.pos = null,
	}

	// UNTESTED
	// Keep an eye on this
	public ArrayList<Tile> getNeighbours(Dungeon dun){
		ArrayList<Tile> n = new ArrayList<Tile>();
		int i = this.getPosition().getX();
		int j = this.getPosition().getY();
		Tile tileToAdd = new Tile();
		for(int altI = i-1; altI <= i+1 && altI != i ; altI++){
			for(int altJ = j-1; altJ <= j+1 && altJ != j; altJ++){
				tileToAdd = dun.getLevel(dun.getCurrentLevel()).getTile(i,j);
				if(tileToAdd.isValidTile()){
					System.out.println("Tile.getNeighbours add: ",+altI+" "+altJ);
					n.add(tileToAdd);					
				}
			}
		}
		return n;				
	}

	// Only checks for map limits, it does not care for walls.
	public boolean isValidTile(){
		if( 0 <= this.pos.getX() <= 79 &&
			0 <= this.pos.getY() <= 24 ){
			return true;
		}
		return false;
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
